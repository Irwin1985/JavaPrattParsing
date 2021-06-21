package ast;
import token.Token;

public class IfExpression implements Expression {
	public Token token;
	public Expression condition;
	public BlockStatement consequence;
	public BlockStatement alternative;
	
	public IfExpression(Token token) {
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
		
		out.append("if(");
		out.append(condition.string());
		out.append(")");
		out.append(consequence.string());
		if (alternative != null) {
			out.append("else");
			out.append(alternative.string());
		}
		
		return out.toString();
	}
	@Override
	public NodeType type() {
		return NodeType.IF_EXPR;
	}
}
