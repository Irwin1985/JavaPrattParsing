package ast;
import token.Token;

public class LetStatement implements Statement {
	public Token token;
	public Identifier name;
	public Expression value;

	public LetStatement(Token token) {
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
		var out = new StringBuilder();
		out.append("let");
		out.append(name.string());
		out.append(" = ");
		out.append(value.string());
		out.append(";");
		
		return out.toString();
	}
	
}
