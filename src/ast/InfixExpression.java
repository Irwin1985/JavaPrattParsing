package ast;
import token.Token;

public class InfixExpression implements Expression {
	public Token token;
	public Expression left;
	public String operator;
	public Expression right;
	
	public InfixExpression(Token token, String operator, Expression left) {
		this.token = token;
		this.operator = operator;
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
		var out = new StringBuilder();
		out.append("(");
		out.append(left.string());
		out.append(" " + operator + " ");
		out.append(right.string());
		out.append(")");
		return out.toString();
	}
	@Override
	public NodeType type() {
		return NodeType.INFIX_EXPR;
	}
}
