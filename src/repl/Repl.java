package repl;
import java.util.Scanner;
import lexer.Lexer;
import parser.Parser;
import java.util.ArrayList;

public class Repl {
	static String PROMPT = ">>";	
	static String MONKEY_FACE = "";
	
	public static void start() {
		Scanner scanner = new Scanner(System.in);		
		while (true) {
			System.out.print(PROMPT);
			String line = scanner.nextLine();
			if (line.length() == 0) {
				break;
			}
			
			lexer.Lexer l = new Lexer(line);
			parser.Parser p = new Parser(l);
			
			ast.Program program = p.parseProgram();
			if (p.errors().size() > 0) {
				printParserErrors(p.errors());
				continue;
			}
			
			if (program != null) {
				System.out.println(program.string());
			}
			
		}
		scanner.close();
	}
	// imprime los errores del parser
	static void printParserErrors(ArrayList<String> errors) {
		System.out.println("Whoops! We ran into some monkey business here!");
		System.out.println("parser errors:");
		for (var msg : errors) {
			System.out.println(msg);
		}
	}
}
