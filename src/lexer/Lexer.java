package lexer;
import token.*;
import java.util.HashMap;

public class Lexer {
	String input;
	int curPosition = 0;
	int peekPosition = 0;
	char ch = 0;	
	HashMap<String, token.TokenType> keywords;

	// constructor
	public Lexer(String input) {
		this.input = input;		
		fillKeywords();
		readChar();
	}
	
	// crea un token
	Token newToken(token.TokenType type, String literal) {
		return new Token(type, literal);
	}
	
	// avanza un caracter en el input
	void readChar() {
		if (peekPosition >= input.length()) {
			ch = 0;
		} else {
			ch = input.charAt(peekPosition);
		}
		curPosition = peekPosition;
		peekPosition += 1;
	}
	
	// echa una mirada al caracter siguiente sin consumirlo
	char peekChar() {
		if (peekPosition >= input.length()) {
			return 0;
		}
		return input.charAt(peekPosition);
	}
	
	// se salta todos los espacios en blanco
	void skipWhitespace() {
		while (ch != 0 && isSpace(ch)) {
			readChar();
		}
	}
	
	// lee un número entero
	String readNumber() {
		int position = curPosition;
		while (ch != 0 && isDigit(ch)) {
			readChar();
		}
		return input.substring(position, curPosition);
	}
	
	// lee un string
	String readString() {
		char strDelim = ch;
		int position = curPosition + 1;
		while (true) {
			readChar();
			if (ch == 0 || ch == strDelim) {
				break;
			}
		}
		return input.substring(position, curPosition);
	}
	
	// lee un identificador
	String readIdentifier() {
		int position = curPosition;
		while (isLetter(ch)) {
			readChar();
		}
		return input.substring(position, curPosition);
	}
	
	// obtiene un nuevo token
	public Token nextToken() {
		Token tok;
		
		skipWhitespace();
		
		switch (ch) {
		case '=':
			if (peekChar() == '=') {
				readChar();
				tok = newToken(TokenType.EQ, "==");
			} else {
				tok = newToken(TokenType.ASSIGN, "=");
			}
			break;
		case '+':
			tok = newToken(TokenType.PLUS, "+");
			break;
		case '-':
			tok = newToken(TokenType.MINUS, "-");
			break;
		case '*':
			tok = newToken(TokenType.ASTERISK, "*");
			break;
		case '/':
			tok = newToken(TokenType.SLASH, "/");
			break;
		case '!':
			if (peekChar() == '=') {
				readChar();
				tok = newToken(TokenType.NOT_EQ, "!=");
			} else {
				tok = newToken(TokenType.BANG, "!");
			}
			break;
		case '<':
			tok = newToken(TokenType.LT, "<");
			break;
		case '>':
			tok = newToken(TokenType.GT, ">");
			break;
		case ';':
			tok = newToken(TokenType.SEMICOLON, ";");
			break;
		case ',':
			tok = newToken(TokenType.COMMA, ",");
			break;
		case ':':
			tok = newToken(TokenType.COLON, ":");
			break;
		case '(':
			tok = newToken(TokenType.LPAREN, "(");
			break;
		case ')':
			tok = newToken(TokenType.RPAREN, ")");
			break;
		case '{':
			tok = newToken(TokenType.LBRACE, "{");
			break;
		case '}':
			tok = newToken(TokenType.RBRACE, "}");
			break;
		case '[':
			tok = newToken(TokenType.RBRACKET, "[");
			break;
		case ']':
			tok = newToken(TokenType.RBRACKET, "]");
			break;
		case '"':
		case '\'':
			tok = newToken(TokenType.STRING, readString());
			break;
		case 0:
			tok = newToken(TokenType.EOF, "");
			break;
		default:
			if (isLetter(ch)) {
				tok = new Token();
				tok.literal = readIdentifier();
				tok.type = lookupIdent(tok.literal);
				return tok; // para que no avance el caracter
			} else if (isDigit(ch)) {
				tok = new Token();
				tok.type = TokenType.INT;
				tok.literal = readNumber();
				return tok;
			} else {
				tok = newToken(TokenType.ILLEGAL, String.valueOf(ch));
			}
		}
		readChar();
		return tok;
	}

	// determina si el caracter es un número
	boolean isDigit(char ch) {
		return '0' <= ch && ch <= '9';
	}
	// determina si el caracter es una letra
	boolean isLetter(char ch) {
		return 'a' <= ch && ch <= 'z' || 'A' <= ch && ch <= 'Z' || ch == '_';
	}
	// determina si el caracter es un espacio
	boolean isSpace(char ch) {
		return ch == ' ' || ch == '\t' || ch == '\n';
	}
	
	// Llena el diccionario de palabras reservadas
	void fillKeywords() {
		keywords = new HashMap<String, token.TokenType>();
		keywords.put("fn", TokenType.FUNCTION);
		keywords.put("let", TokenType.LET);
		keywords.put("true", TokenType.TRUE);
		keywords.put("false", TokenType.FALSE);
		keywords.put("null", TokenType.NULL);
		keywords.put("if", TokenType.IF);
		keywords.put("else", TokenType.ELSE);
		keywords.put("return", TokenType.RETURN);
	}
	
	// Busca una palabra reservada o identificador
	TokenType lookupIdent(String ident) {
		return keywords.getOrDefault(ident, TokenType.IDENT); 
	}
}
