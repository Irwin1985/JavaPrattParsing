package ast;
import token.Token;
import java.util.ArrayList;

public class FunctionLiteral implements Expression {
	public Token token;
	public ArrayList<Identifier> parameters;
	public BlockStatement body;
	
	public FunctionLiteral(Token token) {
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
		out.append("fn(");
		
		if (parameters.size() > 0) {
			var params = new ArrayList<String>();
			for (var identifier : parameters) {
				params.add(identifier.string());
			}
			out.append(String.join(",", params));			
		}
		
		out.append(")");
		out.append(body.string());
		
		return out.toString();
	}
}
