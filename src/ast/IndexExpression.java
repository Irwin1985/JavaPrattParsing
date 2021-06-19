package ast;
import token.Token;

public class IndexExpression implements Expression {
	public Token token;
	public Expression left;
	public Expression index;
	
	public IndexExpression(Token token, Expression left) {
		this.token = token;
		this.left = left;
	}
	
	@Override
	public void expressionNode() {};
	@Override
	public String tokenLiteral() {
		return token.literal;
	}
	@Override
	public String string() {
		return left.string() + "[" + index.string() + "]";
	}
}
