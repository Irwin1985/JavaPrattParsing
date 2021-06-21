package compiler;
import java.util.HashMap;
import java.util.ArrayList;

public class SymbolTable {
	// Definimos los tipos de �mbitos
	public static final String GlobalScope = "GLOBAL"; 
	public static final String LocalScope = "LOBAL";
	public static final String FreeScope = "FREE";
	public static final String FunctionScope = "FUNCTION";
	
	// Definimos los atributos de la tabla de s�mbolos
	public SymbolTable outer;
	HashMap<String, Symbol> store;
	int numDefinitions;
	ArrayList<Symbol> freeSymbols;
	
	public SymbolTable() {
		store = new HashMap<String, Symbol>();
		freeSymbols = new ArrayList<Symbol>();
	}
	/**
	 * Crea una tabla de s�mbolos nueva y le asocia la anterior 
	 * como su padre. Este caso se da cuando se compilan funciones.
	 */
	public SymbolTable(SymbolTable outer) {
		var newSt = new SymbolTable();
		newSt.outer = outer;
	}
	/**
	 * Define un S�mbolo y lo agrega al diccionario de s�mbolos.
	 * B�sicamente es crear una instancia de Symbol() y definir
	 * sus propiedades:
	 * name: el nombre del s�mbolo. Ej: foo
	 * index: el �ndice que tiene la tabla de s�mbolos en ese momento.
	 * scope: si la tabla de s�mbolos tiene padre entonces el �mbito
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
	 * Crea un s�mbolo pero conociendo su scope de antemano.
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
			// si el s�mbolo es local (dentro de una funci�n) entonces lo convertimos a libre.
			var freeSymbol = defineFree(symbol);
			return freeSymbol;
		}
		return symbol;
	}
	/**
	 * Define un s�mbolo libre partiendo de otro Local. Esto tiene que ser as� porque
	 * hay funciones internas que usan los argumentos de sus funciones externas
	 * entonces la m�quina virtual tiene que cargar estas variables antes de ejecutar
	 * la funci�n interna que tambi�n se conoce como closure. 
	 */
	private Symbol defineFree(Symbol symbol) {
		// agregamos el s�mbolo al array
		freeSymbols.add(symbol);
		
		var newSymbol = new Symbol(symbol.name, freeSymbols.size()-1, FreeScope);
		// almacenamos el s�mbolo
		store.put(symbol.name, symbol);
		
		return newSymbol;
	}
}
