package ast;
import token.Token;

public class PrefixExpression implements Expression {
	public Token token;
	public char operator;
	public Expression right;
	
	public PrefixExpression(Token token) {
		this.token = token;
	}
	
	@Override
	public void expressionNode() {};
	@Override
	public String tokenLiteral() {
		return token.literal;
	}
	@Override
	public String string() {
		var out = new StringBuilder();
		
		out.append("(");
		out.append(" " + operator + " ");
		out.append(right.string());
		out.append(")");
		
		return out.toString();
	}
}
