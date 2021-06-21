package code;
import java.util.HashMap;

public class OpCode {
	/**
	 * LISTA DE OPCODES PARA LA MÁQUINA VIRTUAL
	 */
	public static final byte opConstant = 1;
	public static final byte opSetGlobal = 2;
	public static final byte opSetLocal = 3;
	public static final byte opGetGlobal = 4;
	public static final byte opGetLocal = 5;
	public static final byte opGetFree = 6;
	public static final byte opTrue = 7;
	public static final byte opFalse = 8;
	public static final byte opNull = 9;
	public static final byte opPop = 10;
	public static final byte opClosure = 11;
	public static final byte opCurrentClosure = 12;
	public static final byte opCall = 13;
	public static final byte opReturnValue = 14;
	public static final byte opReturn = 15;
	public static final byte opJumpNotTrue = 16;
	public static final byte opJump = 17;		
	public static final byte opAdd = 18;
	public static final byte opSub = 19;
	public static final byte opMul = 20;
	public static final byte opDiv = 21;
	public static final byte opPow = 22;
	public static final byte opLess = 23;
	public static final byte opLessEq = 24;
	public static final byte opGreater = 25;
	public static final byte opGreaterEq = 29;
	public static final byte opEqual = 26;
	public static final byte opNotEqual = 27;
	public static final byte opAnd = 28;
	public static final byte opOr = 29;
	public static HashMap<Byte, String> definitions = new HashMap<Byte, String>();
	/**
	 * Crea una instancia de Instructions y le llena sus propiedades:
	 * ins.opCode = opCode
	 * ins.operands = operands
	 */
	public static Instructions make(byte opCode, int...operands) {
		return new Instructions(opCode, operands);
	}
	public static void loadDefinitions() {
		definitions.put(opConstant, "opConstant");
		definitions.put(opSetGlobal, "opSetGlobal");
		definitions.put(opSetLocal, "opSetLocal");
		definitions.put(opGetGlobal, "opGetGlobal");
		definitions.put(opGetLocal, "opGetLocal");
		definitions.put(opGetFree, "opGetFree");
		definitions.put(opTrue, "opTrue");
		definitions.put(opFalse, "opFalse");
		definitions.put(opNull, "opNull");
		definitions.put(opPop, "opPop");
		definitions.put(opClosure, "opClosure");
		definitions.put(opCurrentClosure, "opCurrentClosure");
		definitions.put(opCall, "opCall");
		definitions.put(opReturnValue, "opReturnValue");
		definitions.put(opReturn, "opReturn");
		definitions.put(opJumpNotTrue, "opJumpNotTrue");
		definitions.put(opJump, "opJump");		
		definitions.put(opAdd, "opAdd");
		definitions.put(opSub, "opSub");
		definitions.put(opMul, "opMul");
		definitions.put(opDiv, "opDiv");
		definitions.put(opPow, "opPow");
		definitions.put(opLess, "opLess");
		definitions.put(opLessEq, "opLessEq");
		definitions.put(opGreater, "opGreater");
		definitions.put(opGreaterEq, "opGreaterEq");
		definitions.put(opEqual, "opEqual");
		definitions.put(opNotEqual, "opNotEqual");
		definitions.put(opAnd, "opAnd");
		definitions.put(opOr, "opOr");			
	}
	public static String getDefinition(byte opCode) {
		return definitions.get(opCode);
	}
}
