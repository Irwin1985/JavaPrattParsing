package compiler;
/**
 * �Qu� es un s�mbolo?
 * 
 * Guarda el nombre del identificador (s�mbolo) con un �ndice y un �mbito (scope).
 * 
 * IDENTIFICADOR: es el nombre de la variable. Ej: foo, bar, etc.
 * INDICE: un entero autoincremental que define la Tabla de Simbolos.
 * �MBITO: puede ser GLOBAL, LOCAL, FUNCTION O FREE.
 * 
 * Uso del �ndice seg�n el �mbito:
 * SIMBOLOS GLOBALES: el �ndice se usa como �ndice pero para la tabla global.
 * SIMBOLOS LOCALES: la m�quina virtual toma el �ndice de referencia para
 * desplazar el puntero de la pila a una zona libre previamente reservada para
 * cargar los s�mbolos de la funci�n.
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
