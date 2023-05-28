package compilador;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import static compilador.Lexico.isId;

public class Semantico {

    List<Tokens> tokens; //cria uma lista do tipo Tokens
    HashMap<String, Boolean> declarados;//tipo logico para analisar as variaveis declaradas

    //sintatico declara uma variavel que recebe a lista de tokens e a lista do que foi declarado
    public Semantico(List<Tokens> tokens) {
        this.tokens = new ArrayList<>(tokens);
        this.declarados = new HashMap<>();
    }

    //aonde a analise semantica acontece
    public void analiseSemantica() {

        int i = 0; // contador
        //enqunto for menor que o numero de tokens existente na tokenInfo
        while (i < tokens.size()) {
            var Tok = tokens.get(i);
            //verifica se é um inteiro se reconhecer que foram declarados com "var"
            if (Tok.getToken().equals("tipo")) {
                var tokProximo = tokens.get(i + 1); // vai passando variavel por variavel
                if (declarados.containsKey(tokProximo.getLexeme())) {
                    // verifica se a variavel ja foi declarada
                    erro("variavel " + tokProximo.getLexeme() + " ja foi declarada", tokProximo.getLinha(), tokProximo.getColuna());
                }
                //se foi declarada retorna a mensagem abaixo confirmando a declaração
                declarados.put(tokProximo.getLexeme(), false);
                log("Variavel " + tokProximo.getLexeme() + " declarada");

            } else if (Tok.getToken().equals("if") || Tok.getToken().equals("while")) {
                //vai "varrendo" o programa verificando se as variaveis já foram declaradas
                while (!tokens.get(i++).getToken().equals(")")) { // verifica se a variavel dentro do escopo ja foi declarado
                    if (tokens.get(i).getToken().equals("var")) {
                        if (!declarados.containsKey(tokens.get(i).getLexeme())) {
                            erro("Variavel " + tokens.get(i).getLexeme() + " nao foi declarada",
                                    tokens.get(i).getLinha(), tokens.get(i).getColuna());
                        }
                    }
                }
            } else if (Tok.getToken().equals("var")) { //verifica se é um variavel
                if (!declarados.containsKey(Tok.getLexeme())) {
                    erro("variavel " + Tok.getLexeme() + " nao foi declarada", Tok.getLinha(), Tok.getColuna());
                }

                var buffer = new StringBuilder();
                if (tokens.get(++i).getToken().equals(":")) {
                    i++;
                    while (!tokens.get(i).getToken().equals(";")) {
                        String valor = tokens.get(i).getLexeme();
                        if (isId(valor)) {
                            if (!declarados.containsKey(Tok.getLexeme())) {
                                erro("variavel " + Tok.getLexeme() + " nao foi declarada",
                                        Tok.getLinha(), Tok.getColuna());
                            }
                            if (declarados.get(valor)) {
                                buffer.append(valor);
                            } else {
                                buffer.append("0");
                            }
                        } else {
                            buffer.append(valor);
                        }
                        i++;
                    }
                    if (buffer.toString().equals("0")) {
                        declarados.put(Tok.getLexeme(), false);
                    } else if (buffer.toString().contains("/0")) {
                        erro("divisão por zero ", Tok.getLinha(), Tok.getColuna());
                    } else {
                        declarados.put(Tok.getLexeme(), true);
                    }
                    log(Tok.getLexeme() + " recebeu : " + buffer.toString());
                }
            } else if (Tok.getToken().equals("read")) {
                var tokProximo = tokens.get(i + 2);
                // log("entrou no read " + tokProximo);
                declarados.put(tokProximo.getLexeme(), true);
            }
            i++;
        }
    }


    private void log(String msg) {
        if (Flag.SEMANTICO.getStatus()) {
            System.out.println(msg);
        }
    }

    private void erro(String erro, Integer Linha, Integer Coluna) {
        throw new SemanticoException("[" + Linha + ", " + Coluna + "] " + erro);
    }

    public List<String> getDeclarados() {

        return new ArrayList<>(declarados.keySet());
    }
}

