package ast;
import token.Token;

public class ExpressionStatement implements Statement {
	public Token token;
	public Expression expression;
	public ExpressionStatement(Token token) {
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
		return expression.string() + ";";
	}
}
