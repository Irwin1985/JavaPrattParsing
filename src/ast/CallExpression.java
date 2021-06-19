package ast;
import token.Token;
import java.util.ArrayList;

public class CallExpression implements Expression {
	public Token token;
	public Expression function;
	public ArrayList<Expression> arguments;
	
	public CallExpression(Token token, Expression function) {
		this.token = token;
		this.function = function;
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
		out.append(function.string());
		out.append("(");
		
		if (arguments.size() > 0) {
			var args = new ArrayList<String>();
			for (var arg : arguments) {
				args.add(arg.string());
			}
			out.append(String.join(",", args));
		}
		
		out.append(")");
		return out.toString();
	}
}
