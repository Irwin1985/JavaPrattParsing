package ast;
import java.util.ArrayList;
import token.Token;

public class BlockStatement implements Statement {
	public Token token;
	public ArrayList<Statement> statements;

	public BlockStatement(Token token) {
		this.token = token;
	}

	@Override
	public void statementNode() {};
	@Override
	public String tokenLiteral() {
		return token.literal;
	}
	@Override
	public String string() {
		StringBuilder out = new StringBuilder();
		out.append("{\n");
		if (statements.size() > 0) {
			for (var stmt : statements) {
				out.append("  " + stmt.string());
			}
		}
		out.append("\n}\n");
		return out.toString();
	}
	@Override
	public NodeType type() {
		return NodeType.BLOCK;
	}
}
