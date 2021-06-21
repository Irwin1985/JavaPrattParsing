package ast;

public interface Node {
	String tokenLiteral();
	String string();
	public NodeType type();
}
