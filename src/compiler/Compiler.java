package compiler;
import java.util.ArrayList;
import code.*;
import ast.*;

public class Compiler {
	
	ArrayList<Object> constants;
	SymbolTable symbolTable;
	ArrayList<CompiledScope> scopes;
	int scopeIndex;

	//**************DEBUG**************//
	StringBuilder output;
	//**************DEBUG**************//
	
	public Compiler(SymbolTable symbolTable, ArrayList<Object> constants) {
		// mainScope es el �mbito del programa principal.
		var mainScope = new CompiledScope();

		// recibimos la tabla de s�mbolos y las constantes
		// porque dentro de la REPL hay varias vueltas y el 
		// usuario puede recuperar datos de la vuelta anterior.
		this.symbolTable = symbolTable;
		this.constants = constants;
		
		// por supuesto agregamos el mainScope al array de scopes.
		scopes = new ArrayList<CompiledScope>();
		scopes.add(mainScope);
		
		//**************DEBUG**************//
		output.append("==========MAIN PROGRAM============\n");
		OpCode.loadDefinitions();
		//**************DEBUG**************//		
	}
	/**
	 * Crea una tabla de s�mbolos y le asigna un padre que es la tabla de s�mbolos actual.
	 */
	void enterScope() {
		var newScope = new CompiledScope();
		scopes.add(newScope);
		scopeIndex += 1;
		//**************DEBUG**************//
		output.append("==========FUNC BLOCK============");
		//**************DEBUG**************//
		// enclose symbolTable
		symbolTable = new SymbolTable(symbolTable);
	}
	/**
	 * Abandona el scope actual y vuelve al anterior. Teoricamente es que acaba de compilar
	 * todo el blockStatement de una funci�n.
	 * Devuelve el scope compilado.
	 */
	CompiledScope leaveScope() {
		var scope = scopes.get(scopeIndex);
		scopes.remove(scopeIndex);
		scopeIndex -= 1;
		
		//**************DEBUG**************//
		output.append("============END FUNC============\n");
		//**************DEBUG**************//
		
		// unclose symbol table
		symbolTable = symbolTable.outer;
		
		return scope;
	}
	/**
	 * Carga el opCode correspondiente al �mbito del s�mbolo actual.
	 */
	void loadSymbol(Symbol symbol) {
		byte opCode = 0; 
		switch (symbol.scope) {
		case SymbolTable.GlobalScope:
			opCode = OpCode.opGetGlobal;
			break;
		case SymbolTable.LocalScope:
			opCode = OpCode.opGetLocal;
			break;
		case SymbolTable.FreeScope:
			opCode = OpCode.opGetFree;
			break;
		case SymbolTable.FunctionScope:
			opCode = OpCode.opCurrentClosure;
		default:
			opCode = 0;
		}
		emit(opCode, symbol.index);		
	}
	/**
	 * Obtiene la lista de instrucciones del CompiledScope actual.
	 */
	ArrayList<Instructions> getInstructions() {
		return scopes.get(scopeIndex).instructions;
	}
	/**
	 * Compila un nodo AST.
	 */
	boolean Compile(ast.Node node) {
		switch (node.type()) {
		case PROGRAM:
			for (var stmt : ((Program)node).statements) {
				if (!Compile(stmt)) return false;
			}
			break;
		case BLOCK:
			for (var stmt : ((BlockStatement)node).statements) {
				if (!Compile(stmt)) return false;
			}
		case EXPR_STMT:
			var err = Compile(((ExpressionStatement)node).expression);
			if (!err) return err;
		case LET:
			var letNode = (LetStatement)node;
			var symbol = symbolTable.define(letNode.name.value);
			if (!Compile(letNode.value)) return false;
			var opCode = OpCode.opSetGlobal;
			if (symbol.scope == SymbolTable.LocalScope) {
				opCode = OpCode.opSetLocal;
			}
			emit(opCode, symbol.index);
			//*************DEBUG*************//
			addInstructionDebug(String.valueOf(scopes.get(scopeIndex).ic), OpCode.getDefinition(opCode), letNode.name.value);
			//*************DEBUG*************//
			break;
		case IDENT:			
			break;
		default:
				return false;
		}
		return true;
	}
	/**
	 * emite una instrucci�n para la m�quina virtual y devuelve
	 * el n�mero de la instrucci�n emitida.
	 */
	int emit(byte opCode, int...operands) {
		var ins = getInstructions();
		var instruction = OpCode.make(opCode, operands);
		var index = ins.size();
		
		// agregamos la instrucci�n en el array de instrucciones
		scopes.get(scopeIndex).instructions.add(instruction);
		
		return index;
	}
	//*************DEBUG*************//
	void addInstructionDebug(String...args) {
		for (var str : args) {
			output.append(str);
		}
	}
	//*************DEBUG*************//
}
