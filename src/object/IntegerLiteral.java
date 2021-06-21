package object;

public class IntegerLiteral implements Object {
	public Integer value;
	
	public IntegerLiteral(Integer value) {
		this.value = value;
	}
	@Override
	public ObjectType type() {
		return ObjectType.INTEGER_OBJ;
	}
	@Override
	public String inspect() {
		return value.toString(); 
	}
}
