package ast;
import token.Token;

public class Identifier implements Expression {
	public Token token;
	public String value;
	
	public Identifier(Token token, String value) {
		this.token = token;
		this.value = value;
	}
	
	@Override
	public void expressionNode() {};
	@Override
	public String tokenLiteral() {
		return token.literal;
	}
	@Override
	public String string() {
		return token.literal;
	}
}
