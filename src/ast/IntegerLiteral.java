package ast;
import token.Token;

public class IntegerLiteral implements Expression {
	public Token token;
	public int value;
	
	public IntegerLiteral(Token token, int value) {
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
		return NodeType.INTEGER;
	}
}
