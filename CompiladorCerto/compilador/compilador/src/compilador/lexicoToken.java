package compilador;


import java.util.*;

public class lexicoToken {
    //declação de metodo de atribuição dos tokens
    //map tipo estatico para armazenar os tokens
    public final static Map<String, String> palavraChave;

    static {
        palavraChave = new HashMap<>();
        palavraChave.put("star", "inicio");
        palavraChave.put("finish", "fim");
        palavraChave.put("read", "read");
        palavraChave.put("write", "write");
        palavraChave.put("while", "while");
        palavraChave.put("int", "tipo");
        palavraChave.put ("if","se");
        palavraChave.put("else","senao");
        palavraChave.put("fimse","fimse");
      
    }

    int lineCounter = 1;
    int columnCounter = 1;
    List<String> line = new ArrayList<>();
    List<Tokens> listOfTokens = new ArrayList<>();

    public static boolean isKeyword(String s) {
        switch (s) {
       
            case "star":
            case "finish":
            case "if":
            case "else":
            case "fimse":
            case "while":
            case "write":
            case "read":
            case "int":
                return true;
        }

        return false;
    }

    public static boolean isSymbol(String s) {
        switch (s) {
            case ";":
            case "(":
            case ")":
            case ":":
            case "+":
            case "-":
            case "*":
            case "/":
            case "<":
            case ">":
            case "=":
            case "{":
            case "}":
            case "!":
            //case ">=":
            case "<=":
            case "/=":
                return true;
        }

        return false;
    }

    // o booleano que verifica o caractere a ser recebido será identificado como letra ou numero
    public static boolean isId(String s) {
        if (!Character.isLetter(s.charAt(0))) {
            return false;
        }
        return s.chars().allMatch(Character::isLetterOrDigit);
    }

    public static boolean isNumber(String s) {

        return s.chars().allMatch(Character::isDigit);
    }

    public List<Tokens> splitTk(List<String> codigo) {
    //Aqui usamos o regex para quebrar espaços nas leituras do codigo onde ele aceita somente os simbolos necessário
        String splitter = "[+*\\-<>/=\\s)(\";:!]"; //criterios de divisao REGEX

        String regex = "((?<=" + splitter + ")|"  /*lookahead*/ + "(?=" + splitter + "))"; /*lookbehind*/

        for (String s : codigo) {

            if (s.isEmpty()) { // tratamento para linha vazia
                lineCounter++;
                continue;
            }
            //linha recebe o regex como parametro uma divisão dentro da lista de string
            line = Arrays.asList(s.split(regex));

            // o for vai percorre as linhas do codigo fazendo a verificação do lexema
            for (int i = 0; i < line.size(); i++) { //percorre a lista de linha quebradas pelo regex
                String lexeme = line.get(i);

                if (lexeme.isBlank()) continue; // tratamento para espaços no codigo

                if (isKeyword(lexeme)) { //add caso for um simbolo ou palavra chave e ir organizando na lista
                    var terminal = palavraChave.get(lexeme);
                    listOfTokens.add(new Tokens(terminal,lexeme, lineCounter, columnCounter));
                    columnCounter += lexeme.length();
                } else if (isSymbol(lexeme)) {//simbolos que foram declarados no programa
                    listOfTokens.add(new Tokens(lexeme, lexeme, lineCounter, columnCounter));
                    columnCounter += lexeme.length();
                } else if (isId(lexeme)) { // add caso for um id
                    listOfTokens.add(new Tokens("var", lexeme, lineCounter, columnCounter));
                    columnCounter += lexeme.length();
                } else if (isNumber(lexeme)) { // add caso for um numeral
                    listOfTokens.add(new Tokens("num", lexeme, lineCounter, columnCounter));
                    columnCounter += lexeme.length();
                } else if (lexeme.equals("\"")) { // add strings
                    StringBuilder string = new StringBuilder();

                    do {
                        string.append(line.get(i));

                        if (i + 1 == line.size()) { // caso chegue no final de linha sem encontrar o fecha aspas
                            System.err.println("Erro lexico na [" + lineCounter + ", " + columnCounter + "] " +
                                    "Faltou aspas");
                            System.exit(-1);
                        }

                    } while (!line.get(++i).equals("\"")); //enquanto o proximo lexema for diferente do fecha aspas

                    string.append('"');
                    listOfTokens.add(new Tokens("string", string.toString(), lineCounter, columnCounter));
                    columnCounter += string.length();

                } else { // caso seja  um lexema que não esta na gramatica
                    System.err.println("Erro lexico na [linha " + lineCounter + " , coluna " + columnCounter + "] " +
                            lexeme + " nao foi reconhecido.");
                    System.exit(-1);
                }
            }
            lineCounter++;
            columnCounter = 1;
        }
        listOfTokens.add(new Tokens("$", "$", lineCounter, columnCounter));
        if (Flag.TOKENS.getStatus()) {
            for (Tokens listOfToken : listOfTokens) { //printa a lista de tokens
                System.out.println(listOfToken.toString());
            }
        }
        return listOfTokens;
    }
}
