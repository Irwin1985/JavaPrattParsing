package compiler;
import java.util.HashMap;
import java.util.ArrayList;

public class SymbolTable {
	// Definimos los tipos de ámbitos
	public static final String GlobalScope = "GLOBAL"; 
	public static final String LocalScope = "LOBAL";
	public static final String FreeScope = "FREE";
	public static final String FunctionScope = "FUNCTION";
	
	// Definimos los atributos de la tabla de símbolos
	public SymbolTable outer;
	HashMap<String, Symbol> store;
	int numDefinitions;
	ArrayList<Symbol> freeSymbols;
	
	public SymbolTable() {
		store = new HashMap<String, Symbol>();
		freeSymbols = new ArrayList<Symbol>();
	}
	/**
	 * Crea una tabla de símbolos nueva y le asocia la anterior 
	 * como su padre. Este caso se da cuando se compilan funciones.
	 */
	public SymbolTable(SymbolTable outer) {
		var newSt = new SymbolTable();
		newSt.outer = outer;
	}
	/**
	 * Define un Símbolo y lo agrega al diccionario de símbolos.
	 * Básicamente es crear una instancia de Symbol() y definir
	 * sus propiedades:
	 * name: el nombre del símbolo. Ej: foo
	 * index: el índice que tiene la tabla de símbolos en ese momento.
	 * scope: si la tabla de símbolos tiene padre entonces el ámbito
	 * tiene que ser local porque estamos dentro de un bloque. De lo
	 * contrario es Global porque estamos a nivel de programa.
	 */
	public Symbol define(String name) {
		var symbol = new Symbol(name, numDefinitions, GlobalScope);
		numDefinitions += 1;
		
		if (outer != null) {
			symbol.scope = LocalScope;
		}
		
		store.put(name, symbol);
		
		return symbol;
	}
	/**
	 * Crea un símbolo pero conociendo su scope de antemano.
	 */
	public Symbol define(String name, String scope) {
		var symbol = define(name);
		symbol.scope = scope;
		return symbol;
	}
	
	public Symbol resolve(String name) {
		var symbol = store.get(name);
		if (symbol == null && outer != null) {
			symbol = outer.resolve(name);
			if (symbol == null) {
				return symbol;
			}
			if (symbol.scope == GlobalScope) {
				return symbol;
			}
			// si el símbolo es local (dentro de una función) entonces lo convertimos a libre.
			var freeSymbol = defineFree(symbol);
			return freeSymbol;
		}
		return symbol;
	}
	/**
	 * Define un símbolo libre partiendo de otro Local. Esto tiene que ser así porque
	 * hay funciones internas que usan los argumentos de sus funciones externas
	 * entonces la máquina virtual tiene que cargar estas variables antes de ejecutar
	 * la función interna que también se conoce como closure. 
	 */
	private Symbol defineFree(Symbol symbol) {
		// agregamos el símbolo al array
		freeSymbols.add(symbol);
		
		var newSymbol = new Symbol(symbol.name, freeSymbols.size()-1, FreeScope);
		// almacenamos el símbolo
		store.put(symbol.name, symbol);
		
		return newSymbol;
	}
}
