package ast;
import java.util.ArrayList;

public class Program implements Statement {	
	public ArrayList<Statement> statements;

	@Override
	public void statementNode() {}
	@Override
	public String tokenLiteral() {
		if (statements.size() > 0) {			
			return statements.get(0).string();
		}
		return "";
	}
	@Override
	public String string() {
		StringBuilder out = new StringBuilder();
		
		for (var stmt : statements) {
			out.append(stmt.string());
		}
		
		return out.toString();
	}
	@Override
	public NodeType type() {
		return NodeType.PROGRAM;
	}
}
