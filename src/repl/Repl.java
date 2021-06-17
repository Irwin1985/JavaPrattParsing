package repl;
import java.util.Scanner;
import token.*;
import lexer.*;

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
			
			Lexer l = new Lexer(line);
			
			Token tok = l.nextToken();
			while (tok.type != TokenType.EOF) {
				System.out.println(tok.toString());
				tok = l.nextToken();
			}
		}
		scanner.close();
	}
}
