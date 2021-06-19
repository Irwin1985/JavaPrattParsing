package parser;
import token.*;
import lexer.Lexer;
import ast.*;
import java.util.ArrayList;
import java.util.HashMap;

public class Parser {
	static final int LOWEST = 1;
	static final int EQUALITY = 2;
	static final int COMPARISON = 3;
	static final int TERM = 4;
	static final int FACTOR = 5;
	static final int PREFIX = 6;
	static final int CALL = 7;
	static final int INDEX = 8;
	
	Lexer l;
	Token curToken;
	Token peekToken;
	ArrayList<String> errors;
	HashMap<TokenType, Integer> precedences;
		
	// constructor
	public Parser(Lexer l) {
		this.l = l;
		nextToken();  // avanza curToken
		nextToken(); // avanza peekToken
		loadPrecedences();
		errors = new ArrayList<String>();
	}
	
	// carga las funciones semanticas prefijas
	ast.Expression getPrefixFunction() {
		switch (curToken.type) {
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
		case MINUS:
		case BANG:
			return parsePrefixExpression();
		default:
			var msg = "no prefix parse function for " + curToken.type;
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
			return parseInfixExpression(leftExp);
		default:
			var msg = "no infix function found for token: " + type;
			errors.add(msg);
			return null;
		}
	}
	
	// carga el orden de precedencia que tiene cada token
	void loadPrecedences() {
		precedences = new HashMap<TokenType, Integer>();
		
		precedences.put(TokenType.EQ, EQUALITY);		// '=='
		precedences.put(TokenType.NOT_EQ, EQUALITY);	// '!='
		precedences.put(TokenType.LT, COMPARISON); 		// '<'
		precedences.put(TokenType.GT, COMPARISON);		// '>'
		precedences.put(TokenType.PLUS, TERM);			// '+'
		precedences.put(TokenType.MINUS, TERM);			// '-'
		precedences.put(TokenType.ASTERISK, FACTOR); 	// '*'
		precedences.put(TokenType.SLASH, FACTOR);		// '/'
		precedences.put(TokenType.LPAREN, CALL);		// foo()
		precedences.put(TokenType.LBRACKET, INDEX);		// foo[bar]		
	}
	
	// devuelve la precedencia de curToken
	int curPrecedence() {
		return precedences.getOrDefault(curToken.type, LOWEST);
	}
	
	// devuelve el array de errores encontrados
	public ArrayList<String> errors() {
		return errors;
	}
	
	// verifica que el token sea igual a peekToken
	boolean advance(TokenType type) {
		if (curToken.type == type) {
			nextToken();
			return true;
		}
		var msg = "expected token to be " + type + ", got " + curToken.type + " instead.";
		errors.add(msg);
		return false;		
	}
	
	// verifica si el token es igual a curToken
	boolean curTokenIs(TokenType type) {
		return curToken.type == type;
	}
	
	// verifica que el token sea igual a peekToken sin avanzarlo
	boolean peekTokenIs(TokenType type) {
		return peekToken.type == type;
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
			if (curTokenIs(TokenType.SEMICOLON)) {
				advance(TokenType.SEMICOLON);
			}
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
		
		advance(TokenType.LET);
		stmt.name = (Identifier)parseIdentifier();		
		advance(TokenType.ASSIGN);
		
		stmt.value = parseExpression(LOWEST);
		
		return stmt;
	}
	
	// parseReturnStatement ::= RETURN parseExpression
	ast.ReturnStatement parseReturnStatement() {
		var stmt = new ast.ReturnStatement(curToken);
		advance(TokenType.RETURN);
		
		stmt.returnValue = parseExpression(LOWEST);		
		
		return stmt;
	}
	
	// parseExpressionStatement ::= parseExpression
	ast.ExpressionStatement parseExpressionStatement() {
		var exp = new ast.ExpressionStatement(curToken);
		exp.expression = parseExpression(LOWEST);
		
		return exp;
	}
	
	// parseExpression
	ast.Expression parseExpression(int precedence) {
		var leftExp = getPrefixFunction();
		if (leftExp == null) {
			return null;
		}
		
		while (precedence < curPrecedence()) {
			var infix = getInfixFunction(leftExp, curToken.type);
			if (infix == null) {
				return leftExp;
			}
			leftExp = infix;			
		}

		return leftExp;		
	}
	
	// parsePrefixExpression
	ast.Expression parsePrefixExpression() {
		var prefix = new ast.PrefixExpression(curToken, curToken.literal.charAt(0));		
		advance(curToken.type);
		
		prefix.right = parseExpression(PREFIX);
		
		return prefix;
	}
	
	// parseInfixExpression
	ast.Expression parseInfixExpression(ast.Expression leftExp) {		
		var expression = new ast.InfixExpression
				(
					curToken, 
					curToken.literal, 
					leftExp
				);
		
		var precedence = curPrecedence();
		nextToken();
		
		expression.right = parseExpression(precedence);
		
		return expression;
	}
	
	// parseGroupedExpression
	ast.Expression parseGroupedExpression() {
		advance(TokenType.LPAREN);
		var exp = parseExpression(LOWEST);		
		advance(TokenType.RPAREN);
		
		return exp;
	}
	
	// parseIdentifier
	ast.Expression parseIdentifier() {
		var ident = new ast.Identifier(curToken, curToken.literal);
		advance(TokenType.IDENT);
		return ident;
	}
	
	// parseIntegerLiteral
	ast.Expression parseIntegerLiteral() {
		var lit = new ast.IntegerLiteral(curToken, Integer.parseInt(curToken.literal));
		advance(TokenType.INT);
		return lit;
	}
	
	// parseStringLiteral
	ast.Expression parseStringLiteral() {
		var lit = new ast.StringLiteral(curToken, curToken.literal);
		advance(TokenType.STRING);
		return lit;
	}
	
	// parseNullLiteral
	ast.Expression parseNullLiteral() {
		advance(TokenType.NULL);
		return new ast.Null();
	}
	
	// parseBoolean
	ast.Expression parseBoolean() {
		var bool = new ast.Boolean(curToken, curTokenIs(TokenType.TRUE));
		nextToken();
		return bool;
	}
}
