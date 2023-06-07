package compilador.codigo;

import compilador.Tokens;
import compilador.Flag;


import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Stack;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import static java.util.Arrays.asList;

//Gerado facilitar a tradução para a linguagem de baixo nível
public class Intermediario {

    public List<Tokens> listaTokens;
    public int contadorLabel;
    public List<String> variaveis;
    public LinkedList<String> codIntermediario;
    public Stack<String> pilhaLabel;
    public int countVar;

    public Intermediario(List<Tokens> listOfToken, List<String> variaveis) {
        this.listaTokens = listOfToken;
        this.variaveis = variaveis;
        this.codIntermediario = new LinkedList<>();
        this.pilhaLabel = new Stack<>();

    }

    public List<String> gerador() {
        int i = 0;
        Stack<Boolean> whileFlag = new Stack<>();
    
        //pegar as variaveis
        for (var var : variaveis) {
            
            codIntermediario.addLast("INT");
            codIntermediario.addLast(" ");
            codIntermediario.addLast(var);
            codIntermediario.addLast("\n");
        }

        //verificação na lista de tokens
        for (i = 0; i < listaTokens.size(); i++) {

            if (listaTokens.get(i).getLexeme().equals("read")) {
                codIntermediario.addLast("READ");
                codIntermediario.addLast(" ");
                codIntermediario.addLast(listaTokens.get(i + 2).getLexeme());
                codIntermediario.addLast("\n");

            } else if (listaTokens.get(i).getLexeme().equals("write")) {
                codIntermediario.addLast("WRITE");
                codIntermediario.addLast(" ");
                codIntermediario.addLast(listaTokens.get(i + 2).getLexeme());
                codIntermediario.addLast("\n");
            } else if (listaTokens.get(i).getLexeme().equals("while")) {
                codIntermediario.addLast("_L" + (++contadorLabel) + ":");
                codIntermediario.addLast("\n");
                codIntermediario.addLast("IF");
                codIntermediario.addLast(" ");
                List<String> condicao = new ArrayList<>();
                i = i + 2;
                while (!listaTokens.get(i).getLexeme().equals(")")) {
                    condicao.add(listaTokens.get(i++).getLexeme());
                    condicao.add(" ");
                }
                convertCond(condicao);
                codIntermediario.addLast(convertLista(condicao));
                codIntermediario.addLast("GOTO");
                codIntermediario.addLast(" ");

                codIntermediario.addLast("_L" + (contadorLabel + 1));
                codIntermediario.addLast("\n");

                pilhaLabel.push("_L" + (contadorLabel + 1) + ":");
                pilhaLabel.push("_L" + (contadorLabel++));
                pilhaLabel.push("GOTO ");  
                
                whileFlag.add(true);
              } else if (listaTokens.get(i).getLexeme().equals("}")) {
                if(whileFlag.peek()){
                    codIntermediario.addLast(pilhaLabel.peek());
                    pilhaLabel.pop();

                    codIntermediario.addLast(pilhaLabel.peek());
                    pilhaLabel.pop();
                    codIntermediario.addLast("\n");
                    codIntermediario.addLast(pilhaLabel.peek());
                    pilhaLabel.pop();
                    codIntermediario.addLast("\n");
                }else{
                    codIntermediario.addLast(pilhaLabel.peek());
                    pilhaLabel.pop();
                    codIntermediario.addLast("\n");
                }
                whileFlag.pop();

            } else if (listaTokens.get(i).getLexeme().equals("int")) {
                while (!listaTokens.get(++i).getLexeme().equals(";")) ;
            } else if (listaTokens.get(i).getLexeme().equals("if")) {
                List<String> condicao = new ArrayList<>();
                codIntermediario.addLast("IF");
                codIntermediario.addLast(" ");
                i = i + 2;
                while (!listaTokens.get(i).getLexeme().equals(")")) {
                    condicao.add(listaTokens.get(i++).getLexeme());
                    condicao.add(" ");
                }
                convertCond(condicao);
                codIntermediario.addLast(convertLista(condicao));
                pilhaLabel.push("_L" + (++contadorLabel) + ":");
                codIntermediario.addLast("GOTO");
                codIntermediario.addLast(" ");
                codIntermediario.addLast("_L" + contadorLabel);
                codIntermediario.addLast("\n");
                whileFlag.add(false);
            } else if (listaTokens.get(i).getLexeme().equals("else")) {
                codIntermediario.addLast("GOTO");
                codIntermediario.addLast(" ");
                codIntermediario.addLast("_L" + (++contadorLabel));
                codIntermediario.addLast("\n");
                codIntermediario.addLast(pilhaLabel.peek());
                codIntermediario.addLast(" ");
                codIntermediario.addLast("\n");
                pilhaLabel.pop();
                pilhaLabel.push("_L" + contadorLabel + ":");
            } else if (listaTokens.get(i).getToken().equals("var")) {
                String comando = listaTokens.get(i++).getLexeme();
                if (listaTokens.get(i).getLexeme().equals(":")) {
                    comando += " = ";
                    StringBuilder exp = new StringBuilder();
                    while (!(listaTokens.get(++i).getLexeme().equals(";"))) {
                        exp.append(listaTokens.get(i).getLexeme());
                    }
                    var posfixo = Expressao.infixToPostFix(exp.toString());
                    var arr = codTresEnds(posfixo);

                    for (int j = 0; j < (arr.size() - 1); j++) {
                        codIntermediario.addLast("_t" + ((countVar++) % 2) + " = " + arr.get(j));
                    }
                    codIntermediario.addLast(comando + arr.get(arr.size() - 1));
                }
                codIntermediario.addLast("\n");
            }
            
        }
        //imprime tudo na mesma linha
         this.codIntermediario.forEach(System.out::print);
        var finalCode = codIntermediario.stream()
                .filter(str -> !str.equals(" "))
                .filter(str -> !str.equals("\n"))
                .collect(Collectors.toList());
            if(Flag.CODIGO_INTER.getStatus() ) {
          // pula linha pra imprimir
               // finalCode.forEach(f -> System.out.println("\t" + f));
        }
        return (finalCode);
    }

    // converte condição para sair do método
    private void convertCond(List<String> cond) {
        for (int i = 0; i < cond.size(); i++) {
            var temp = cond.get(i);
            cond.set(i, switch (temp) {
                case ">" -> "<=";
                case "<" -> ">=";
                case "=" -> "==";
                case "!" -> "!=";
                default -> temp;
            });
        }
    }
    
    //transformação da infixa para posfixa
    private List<String> codTresEnds(List<String> posfixo) {
        LinkedList<String> result = new LinkedList<>();
        Stack<String> stack = new Stack<>();

        Predicate<String> isOperator = (str) -> asList("+", "-", "*", "/").contains(str);

        var temp = this.countVar;

        for (String token : posfixo) {
            if (isOperator.test(token)) {
                var b = stack.peek();
                stack.pop();
                var a = stack.peek();
                stack.pop();
                result.addLast(a + " " + b + " " + token);
                stack.push("_t" + (countVar++) % 2);
            } else {
                stack.push(token);
            }
        }

        if (result.isEmpty()) {
            result.addLast(stack.peek());
        }

        countVar = temp;
        return result;
    }

    private String convertLista(List<String> lista) {
        StringBuilder builder = new StringBuilder();

        lista.forEach(builder::append);

        return builder.toString();
    }
}