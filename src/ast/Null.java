package ast;

public class Null implements Expression {
	public Null() {}
	@Override
	public void expressionNode() {};
	@Override
	public String tokenLiteral() {
		return "null";
	}
	@Override
	public String string() {
		return "null";
	}
	@Override
	public NodeType type() {
		return NodeType.NULL;
	}
}
