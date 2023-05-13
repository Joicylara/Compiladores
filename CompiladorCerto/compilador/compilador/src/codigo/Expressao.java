package codigo;

import java.util.*;
import static codigo.Delimitador.splitBy;

public class Expressao {
 
    private static int anterior(String c){
        switch (c) {
            case "+":
            case "-":
                return 1;
            case "*":
            case "/":
                return 2;
            default:
                return -1;
        }
    }

    private static boolean isOperator(String str) {

        return Arrays.asList("+", "-", "*", "/", ")", "(").contains(str);
    }

    public static List<String> infixToPostFix(String entradaExpress) {
        var parsedExpression = splitBy("\\*")
                .andThen(splitBy("\\+"))
                .andThen(splitBy("\\-"))
                .andThen(splitBy("\\/"))
                .andThen(splitBy("\\)"))
                .andThen(splitBy("\\("))
                .andThen(splitBy("\\s"))
                .apply(Collections.singletonList(entradaExpress));

        Stack<String> operadores = new Stack<>();
        LinkedList<String> result = new LinkedList<>();
        var it = parsedExpression.listIterator();
        String popped;

        while (it.hasNext()) {
            var curr = it.next();

            if (curr.chars().allMatch(Character::isWhitespace)) {
                continue;
            } else if (!isOperator(curr)) {
                result.addLast(curr);
            } else if (curr.equals(")")) {
                while (!(popped = operadores.pop()).equals("("))
                    result.addLast(popped);
            } else {
                while (!operadores.isEmpty() && !curr.equals("(") && anterior(operadores.peek()) >= anterior(curr))
                    result.addLast(operadores.pop());
                operadores.push(curr);
            }
        }

        while (!operadores.isEmpty())
            result.addLast(operadores.pop());

        return result;
    }
}
