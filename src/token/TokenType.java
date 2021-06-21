package token;

public enum TokenType {
	ILLEGAL,
	EOF,
	
	// Identifiers + literals
	IDENT,	// add, foobar, x, y, ...
	INT,	// 1243456
	STRING,	// "hello", 'bye'
	NULL,	// 'null'
	
	// Operators
	ASSIGN,		// '='
	PLUS,		// '+'
	MINUS,		// '-'
	BANG,		// '!'
	ASTERISK,	// '*'
	SLASH,		// '/'
	
	LT,		// '<'
	LT_EQ,	// '<='
	GT,		// '>'
	GT_EQ,	// '>='
	EQ,		// '=='
	NOT_EQ,	// '!='
	POW, 	// '^'
	
	// Delimiters
	COMMA,		// ','
	SEMICOLON,	// ';'
	COLON,		// ':'
	
	LPAREN,		// '('
	RPAREN,		// ')'
	LBRACE,		// '{'
	RBRACE,		// '}'
	LBRACKET,	// '['
	RBRACKET,	// ']'
	
	// keywords
	FUNCTION,	// 'fn'
	LET,		// 'let'
	TRUE,		// 'true'
	FALSE,		// 'false'
	IF,			// 'if'
	ELSE,		// 'else'
	RETURN,		// 'return'
	OR,			// '||'
	AND,		// '&&'
}
