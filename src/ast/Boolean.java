package ast;
import token.Token;

public class Boolean implements Expression {
	public Token token;
	public boolean value;
	
	public Boolean(Token token, boolean value) {
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
	@Override
	public NodeType type() {
		return NodeType.BOOLEAN;
	}	
}
