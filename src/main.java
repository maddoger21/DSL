import java.util.Scanner;

public class main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        String expression = scanner.nextLine();
        Lexer lexer = new Lexer(expression);
        //передаем в парсер найденные токены
//        (Parser parser = new Parser(lexer.analyse());
//        //парсер делит токены на ноды
//        RootTreeNode root = parser.parse();
//        Interpreter interpreter = new Interpreter();
//        //проходим по всем нашим сохраненным нодам
//        for (int i = 0; i < root.codeStrings.size(); i++) {
//            interpreter.run(root.codeStrings.get(i));
//        }
        System.out.println(PolishNotationCalculator.convertInfixToPolish(expression));
        System.out.println(PolishNotationCalculator.returnExpressionAnswer(PolishNotationCalculator.convertInfixToPolish(expression)));
    }
}
