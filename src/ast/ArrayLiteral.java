package ast;
import token.Token;
import java.util.ArrayList;

public class ArrayLiteral implements Expression {
	public Token token;
	public ArrayList<Expression> elements;
	
	public ArrayLiteral(Token token) {
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
		var expressions = new ArrayList<String>();
		
		for (var exp : elements) {
			expressions.add(exp.string());
		}
		
		return "[" + String.join(",", expressions) + "]";
	}
	@Override
	public NodeType type() {
		return NodeType.ARRAY;
	}
}
