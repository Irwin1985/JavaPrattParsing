package compiler;
import java.util.ArrayList;

import code.Instructions;

public class CompiledScope {
	public ArrayList<Instructions> instructions;
	//**************DEBUG**************//
	int ic;
	//**************DEBUG**************//
	public CompiledScope() {
		instructions = new ArrayList<Instructions>();
		ic = 0;
	}
}
