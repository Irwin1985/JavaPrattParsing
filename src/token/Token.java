package token;

public class Token {	
	public TokenType type;
	public String literal;
	
	public Token() {}	
	public Token(TokenType type, String literal) {
		this.type = type;
		this.literal = literal;
	}
	
	// imprime el token en formato legible
	public String toString() {
		return "Type: " + type + ", Literal: " + literal;
	}
}