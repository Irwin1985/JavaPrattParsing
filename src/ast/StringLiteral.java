package ast;
import token.Token;

public class StringLiteral implements Expression {
	public Token token;
	public String value;
	
	public StringLiteral(Token token, String value) {
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
		return "\"" + token.literal + "\"";
	}
	@Override
	public NodeType type() {
		return NodeType.STRING;
	}
}
