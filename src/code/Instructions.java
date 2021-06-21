package code;
/**
 * Instructions es una clase que contiene lo siguiente:
 * opCode: un byte que sirve para almacenar los comandos para la máquina virtual.
 * operands[]: un array de enteros (no de bytes) que sirve para pasar parámetros
 * adicionales a la máquina virtual. Con 3 es más que suficiente.
 *
 * Funcionamiento: el compilador va a tener un array de instrucciones llamados
 * bytecode. Cada elemento del array contendrá una instancia de esta clase.
 * La máquina virtual recorre el bytecode y en cada vuelta obtiene las 
 * instrucciones empaquetadas aquí en esta clase. Problema resuelto!
 */
public class Instructions {
	byte opCode;
	int[] operands = new int[3]; 
	
	public Instructions(byte opCode, int...operands) {
		this.opCode = opCode;
		this.operands = operands;
	}
}
