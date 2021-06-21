package compiler;
import java.util.ArrayList;

import code.Instructions;

public class ByteCode {
	ArrayList<Instructions> instructions;
	ArrayList<Object> constants;
	
	public ByteCode() {
		instructions = new ArrayList<Instructions>();
		constants = new ArrayList<Object>();
	}
}
