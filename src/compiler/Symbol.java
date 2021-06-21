package compiler;
/**
 * ¿Qué es un símbolo?
 * 
 * Guarda el nombre del identificador (símbolo) con un índice y un ámbito (scope).
 * 
 * IDENTIFICADOR: es el nombre de la variable. Ej: foo, bar, etc.
 * INDICE: un entero autoincremental que define la Tabla de Simbolos.
 * ÁMBITO: puede ser GLOBAL, LOCAL, FUNCTION O FREE.
 * 
 * Uso del índice según el ámbito:
 * SIMBOLOS GLOBALES: el índice se usa como índice pero para la tabla global.
 * SIMBOLOS LOCALES: la máquina virtual toma el índice de referencia para
 * desplazar el puntero de la pila a una zona libre previamente reservada para
 * cargar los símbolos de la función.
 * 
 * @author irwin
 *
 */

public class Symbol {
	public String name;
	public int index;
	public String scope;
	
	public Symbol(String name, int index, String scope) {
		this.name = name;
		this.index = index;
		this.scope = scope;
	}
}
