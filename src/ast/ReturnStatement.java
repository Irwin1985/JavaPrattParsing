package ast;
import token.Token;

public class ReturnStatement implements Statement {
	public Token token;
	public Expression returnValue;
	
	public ReturnStatement(Token token) {
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
		return "return " + returnValue.string();
	}
}
