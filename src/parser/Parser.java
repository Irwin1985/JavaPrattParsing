package parser;
import token.*;
import lexer.Lexer;
import ast.*;
import java.util.ArrayList;
import java.util.HashMap;

public class Parser {
	Lexer l;
	Token curToken;
	Token peekToken;
	ArrayList<String> errors;
	HashMap<TokenType, OrderOp> precedences;

	// constructor
	public Parser(Lexer l) {
		this.l = l;
		nextToken();  // avanza curToken
		nextToken(); // avanza peekToken
		loadPrecedences();
		errors = new ArrayList<String>();
	}
	
	// carga las funciones semanticas prefijas
	ast.Expression getPrefixFunction(TokenType type) {
		switch (type) {
		case INT:
			return parseIntegerLiteral();
		case IDENT:
			return parseIdentifier();
		case STRING:
			return parseStringLiteral();
		case NULL:
			return parseNullLiteral();
		case TRUE:
		case FALSE:
			return parseBoolean();
		case LPAREN:
			return parseGroupedExpression();			
		default:
			var msg = "no prefix parse function for " + type;
			errors.add(msg);
			return null;
		}
	}
	
	// carga las funciones semanticas infijas
	ast.Expression getInfixFunction(ast.Expression leftExp, TokenType type) {
		switch (type) {
		case PLUS:
		case MINUS:
		case ASTERISK:
		case SLASH:
		case EQ:
		case NOT_EQ:
		case LT:
		case GT:
			nextToken(); // adelantamos el token
			return parseInfixExpression(leftExp);
		default:
			return null;
		}
	}
	
	// carga el orden de precedencia que tiene cada token
	void loadPrecedences() {
		precedences = new HashMap<TokenType, OrderOp>();
		precedences.put(TokenType.EQ, OrderOp.EQUALITY);		// '='
		precedences.put(TokenType.NOT_EQ, OrderOp.EQUALITY);	// '!='
		precedences.put(TokenType.LT, OrderOp.COMPARISON); 		// '<'
		precedences.put(TokenType.GT, OrderOp.COMPARISON);		// '>'
		precedences.put(TokenType.PLUS, OrderOp.TERM);			// '+'
		precedences.put(TokenType.MINUS, OrderOp.TERM);			// '-'
		precedences.put(TokenType.ASTERISK, OrderOp.FACTOR); 	// '*'
		precedences.put(TokenType.SLASH, OrderOp.FACTOR);		// '/'
		precedences.put(TokenType.LPAREN, OrderOp.CALL);		// foo()
		precedences.put(TokenType.LBRACKET, OrderOp.INDEX);		// foo[bar]		
	}
	
	// devuelve la precedencia de curToken
	OrderOp curPrecedence() {
		return precedences.getOrDefault(curToken.type, OrderOp.LOWEST);
	}
	
	// devuelve la precedencia de peekToken
	OrderOp peekPrecedence() {
		return precedences.getOrDefault(peekToken.type, OrderOp.LOWEST);
	}
	
	// devuelve el array de errores encontrados
	public ArrayList<String> errors() {
		return errors;
	}
	
	// avanza los tokens
	void nextToken() {
		curToken = peekToken;
		peekToken = l.nextToken();
	}
	
	// punto de entrada del parser
	public ast.Program parseProgram() {
		var program = new ast.Program();
		program.statements = new ArrayList<Statement>();
		
		while (!curTokenIs(TokenType.EOF)) {
			var stmt = parseStatement();
			if (stmt != null) {
				program.statements.add(stmt);
			}
			nextToken();
		}
		
		return program;
	}
	
	// parseStatement ::= parseLetStatement | parseReturnStatement | parseExpressionStatement
	ast.Statement parseStatement() {
		switch (curToken.type) {
		case LET:
			return parseLetStatement();
		case RETURN:
			return parseReturnStatement();
		default:
			return parseExpressionStatement();
		}
	}
	
	// parseLetStatement ::= LET ASSIGN parseExpression
	ast.LetStatement parseLetStatement() {
		var stmt = new ast.LetStatement(curToken);
		
		if (!expectPeek(TokenType.IDENT)) {
			return null;
		}
		
		stmt.name = new Identifier(curToken, curToken.literal);
		if (!expectPeek(TokenType.ASSIGN)) {
			return null;
		}
		nextToken();
		stmt.value = parseExpression(OrderOp.LOWEST);
		
		if (peekTokenIs(TokenType.SEMICOLON)) {
			nextToken();
		}
		return stmt;
	}
	
	// parseReturnStatement ::= RETURN parseExpression
	ast.ReturnStatement parseReturnStatement() {
		var stmt = new ast.ReturnStatement(curToken);
		nextToken();
		
		stmt.returnValue = parseExpression(OrderOp.LOWEST);
		
		if (peekTokenIs(TokenType.SEMICOLON)) {
			nextToken();
		}
		
		return stmt;
	}
	
	// parseExpressionStatement ::= parseExpression
	ast.ExpressionStatement parseExpressionStatement() {
		var exp = new ast.ExpressionStatement(curToken);
		
		exp.expression = parseExpression(OrderOp.LOWEST);
		
		if (peekTokenIs(TokenType.SEMICOLON)) {
			nextToken(); // cada statement revisa si hay un ';' al final
		}
		
		return exp;
	}
	
	// parseExpression
	ast.Expression parseExpression(OrderOp precedence) {
		var leftExp = getPrefixFunction(curToken.type);
		if (leftExp == null) {
			return null;
		}
		
		while (!curTokenIs(TokenType.EOF) && precedence.ordinal() < peekPrecedence().ordinal()) {
			var infix = getInfixFunction(leftExp, peekToken.type);
			if (infix == null) {
				return leftExp;
			}
			//nextToken();
			leftExp = infix;			
		}

		return leftExp;		
	}
	
	// parseInfixExpression
	ast.Expression parseInfixExpression(ast.Expression leftExp) {
		var expression = new ast.InfixExpression(curToken, curToken.literal.charAt(0), leftExp);
		
		var precedence = curPrecedence();
		nextToken();
		expression.right = parseExpression(precedence);
		
		return expression;
	}
	
	// parseGroupedExpression
	ast.Expression parseGroupedExpression() {
		nextToken(); // skip '('
		var exp = parseExpression(OrderOp.LOWEST);
		
		if (!expectPeek(TokenType.RPAREN)) {
			return null;
		}
		
		return exp;
	}
	
	// parseIdentifier
	ast.Expression parseIdentifier() {
		return new ast.Identifier(curToken, curToken.literal);		
	}
	
	// parseIntegerLiteral
	ast.Expression parseIntegerLiteral() {
		return new ast.IntegerLiteral(curToken, Integer.parseInt(curToken.literal));		
	}
	
	// parseStringLiteral
	ast.Expression parseStringLiteral() {
		return new ast.StringLiteral(curToken, curToken.literal);
	}
	
	// parseNullLiteral
	ast.Expression parseNullLiteral() {
		return new ast.Null();
	}
	
	// parseBoolean
	ast.Expression parseBoolean() {
		return new ast.Boolean(curToken, curTokenIs(TokenType.TRUE));
	}
	
	// verifica que el token sea igual a peekToken sin avanzarlo
	boolean peekTokenIs(TokenType type) {
		return peekToken.type == type;
	}
	
	// verifica que el token sea igual a peekToken
	boolean expectPeek(TokenType type) {
		if (peekToken.type == type) {
			nextToken();
			return true;
		}

		peekError(type);
		return false;		
	}
	
	// verifica si el token es igual a curToken
	boolean curTokenIs(TokenType type) {
		return curToken.type == type;
	}
	
	// agrega un error al chequear el expectPeek
	void peekError(TokenType type) {
		var msg = "expected next token to be " + type + ", got " + peekToken.type + " instead.";
		errors.add(msg);
	}
}
