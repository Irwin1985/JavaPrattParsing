package token;

public enum TokenType {
	ILLEGAL,
	EOF,
	
	// Identifiers + literals
	IDENT,	// add, foobar, x, y, ...
	INT,	// 1243456
	STRING,	// "hello", 'bye'
	NULL,
	
	// Operators
	ASSIGN,
	PLUS,
	MINUS,
	BANG,
	ASTERISK,
	SLASH,
	
	LT,
	GT,
	EQ,
	NOT_EQ,
	
	// Delimiters
	COMMA,
	SEMICOLON,
	COLON,
	
	LPAREN,
	RPAREN,
	LBRACE,
	RBRACE,
	LBRACKET,
	RBRACKET,
	
	// keywords
	FUNCTION,
	LET,
	TRUE,
	FALSE,
	IF,
	ELSE,
	RETURN,
}
