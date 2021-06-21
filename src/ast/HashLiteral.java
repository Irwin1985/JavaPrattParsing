package ast;
import token.Token;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class HashLiteral implements Expression {
	public Token token;
	public HashMap<Expression, Expression> pairs;
	
	public HashLiteral(Token token) {
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
		var keyValues = new ArrayList<String>();
		
		for (Map.Entry<Expression, Expression> entry : pairs.entrySet()) {
			keyValues.add(entry.getKey().string() + ":" + entry.getValue().string());
		}
		
		return "{" + String.join(",", keyValues) + "}";
	}
	@Override
	public NodeType type() {
		return NodeType.HASH;
	}
}
