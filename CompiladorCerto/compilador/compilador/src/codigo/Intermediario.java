package codigo;

import compilador.Tokens;
import compilador.Flag;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Stack;
import java.util.stream.Collectors;
import java.util.function.Predicate;

import static java.util.Arrays.asList;

public class Intermediario {

    public List<Tokens> listaTokens;
    public int contLabel;
    public List<String> variavel;
    public LinkedList<String> codigoInt;
    public Stack<String> pilhaLabel;
    public int contVar;

    public Intermediario(List<Tokens> listofToken, List<String> variavel){
        this.listaTokens = listofToken;
        this.variavel = variavel;
        this.codigoInt = new LinkedList<>();
        this.pilhaLabel = new Stack<>();

    }

    public List <String> gerador(){
        int i = 0;

        for (var var : variavel){
            codigoInt.addLast("INT");
            codigoInt.addLast(" ");
            codigoInt.addLast(var);
            codigoInt.addLast("\n");

        }

        for (i = 0; i < listaTokens.size(); i++) {

            if (listaTokens.get(i).getLexeme().equals("read")) {
                codigoInt.addLast("READ");
                codigoInt.addLast(" ");
                codigoInt.addLast(listaTokens.get(i + 2).getLexeme());
                codigoInt.addLast("\n");

            } else if (listaTokens.get(i).getLexeme().equals("write")) {
                codigoInt.addLast("PRINT");
                codigoInt.addLast(" ");
                codigoInt.addLast(listaTokens.get(i + 2).getLexeme());
                codigoInt.addLast("\n");
            } else if (listaTokens.get(i).getLexeme().equals("while")) {
                List<String> condicao = new ArrayList<>();
                codigoInt.addLast("_L" + (++contLabel) + ":");
                codigoInt.addLast("\n");
                codigoInt.addLast("IF");
                codigoInt.addLast(" ");
                i = i + 2;
                while (!listaTokens.get(i).getLexeme().equals(")")) {
                    condicao.add(listaTokens.get(i++).getLexeme());
                    condicao.add(" ");
                }
                convertCondicao(condicao);
                codigoInt.addLast(convertLista(condicao));
                codigoInt.addLast("GOTO");
                codigoInt.addLast(" ");
                codigoInt.addLast("_L" + (contLabel + 1));
                codigoInt.addLast("\n");
                pilhaLabel.push("_L" + (contLabel + 1) + ":");
                pilhaLabel.push("_L" + (contLabel++));
                pilhaLabel.push("GOTO");
            } else if (listaTokens.get(i).getLexeme().equals("endwhile")) {
                codigoInt.addLast(pilhaLabel.peek());
                pilhaLabel.pop();
                codigoInt.addLast(pilhaLabel.peek());
                pilhaLabel.pop();
                codigoInt.addLast("\n");
                codigoInt.addLast(pilhaLabel.peek());
                pilhaLabel.pop();
                codigoInt.addLast("\n");
            } else if (listaTokens.get(i).getLexeme().equals("var")) {
                while (!listaTokens.get(++i).getLexeme().equals(";")) ;
            } else if (listaTokens.get(i).getLexeme().equals("if")) {
                List<String> condicao = new ArrayList<>();
                codigoInt.addLast("IF");
                codigoInt.addLast(" ");
                i = i + 2;
                while (!listaTokens.get(i).getLexeme().equals(")")) {
                    condicao.add(listaTokens.get(i++).getLexeme());
                    condicao.add(" ");
                }
                convertCondicao(condicao);
                codigoInt.addLast(convertLista(condicao));
                pilhaLabel.push("_L" + (++contLabel) + ":");
                codigoInt.addLast("GOTO");
                codigoInt.addLast(" ");
                codigoInt.addLast("_L" + contLabel);
                codigoInt.addLast("\n");

            } else if (listaTokens.get(i).getLexeme().equals("else")) {
                codigoInt.addLast("GOTO");
                codigoInt.addLast(" ");
                codigoInt.addLast("_L" + (++contLabel));
                codigoInt.addLast("\n");
                codigoInt.addLast(pilhaLabel.peek());
                codigoInt.addLast(" ");
                codigoInt.addLast("\n");
                pilhaLabel.pop();
                pilhaLabel.push("_L" + contLabel + ":");
            } else if (listaTokens.get(i).getLexeme().equals("endif")) {
                codigoInt.addLast(pilhaLabel.peek());
                pilhaLabel.pop();
                codigoInt.add("\n");
            } else if (listaTokens.get(i).getToken().equals("id")) {
                String comando = listaTokens.get(i++).getLexeme();
                if (listaTokens.get(i).getLexeme().equals("=")) {
                    comando += " = ";
                    StringBuilder exp = new StringBuilder();
                    while (!(listaTokens.get(++i).getLexeme().equals(";"))) {
                        exp.append(listaTokens.get(i).getLexeme());
                    }
                    var posfixo =Expressao.infixToPostFix(exp.toString());
                    var arr = codTresEnds(posfixo);

                    for (int j = 0; j < (arr.size() - 1); j++) {
                        codigoInt.add("_V" + ((contVar++) % 2) + " = " + arr.get(j));
                    }
                    codigoInt.addLast(comando + arr.get(arr.size() - 1));
                }
                codigoInt.addLast("\n");
            }
            //  i++;
        }
    
        var codFinal = codigoInt.stream()
                .filter(str -> !str.equals(" "))
                .filter(str -> !str.equals("\n"))
                .collect(Collectors.toList());

        if(Flag.CODIGO_INTER.getStatus()) {
            codFinal.forEach(f -> System.out.println("\t" + f));
        }
        return (codFinal);
    }


    private void convertCondicao(List<String> condicao) {
        for (int i = 0; i < condicao.size(); i++) {
            var temp = condicao.get(i);
            condicao.set(i, switch (temp) {
                case ">" -> "<=";
                case "<" -> ">=";
                default -> temp;
            });
        }
    }

    private List<String> codTresEnds(List<String> posfixo) {

        LinkedList<String> result = new LinkedList<>();
        Stack<String> stack = new Stack<>();

        Predicate<String> isOperator = (str) -> asList("+", "-", "*", "/").contains(str);

        var temp = this.contVar;

        for (String token : posfixo) {
            if (isOperator.test(token)) {
                var b = stack.peek();
                stack.pop();
                var a = stack.peek();
                stack.pop();
                result.addLast(a + " " + b + " " + token);
                stack.push("_V" + (contVar++) % 2);
            } else {
                stack.push(token);
            }
        }

        if (result.isEmpty()) {
            result.addLast(stack.peek());
        }

        contVar = temp;
        return result;
    }


    private String convertLista(List<String> lista) {
        StringBuilder construtora = new StringBuilder();

        lista.forEach(construtora::append);

        return construtora.toString();
    }
}
