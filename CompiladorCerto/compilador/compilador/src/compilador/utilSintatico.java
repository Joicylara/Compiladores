package compilador;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static java.util.Arrays.asList;

public class utilSintatico {

    public static Map<String, Integer> Terminal;
    public static Map<String, Integer> naoTerminal;
    public static Map<String, String> palavraChave;

    static {
        Terminal = new HashMap<>(); // horizontal
        Terminal.put("$", 0);
        Terminal.put("inicio", 1);
        Terminal.put("fim", 2);
        Terminal.put("num", 3);
        Terminal.put("read", 4);
        Terminal.put("write", 5);
        Terminal.put("while", 6);
        Terminal.put("tipo", 7); 
        Terminal.put("string",8);
        Terminal.put("se", 9);
        Terminal.put("senao", 10);
        Terminal.put("fimse", 11);
        Terminal.put("var", 12);
        Terminal.put(":", 13);
        Terminal.put("+", 14);
        Terminal.put("-", 15); 
        Terminal.put("*", 16);
        Terminal.put("/", 17); 
        Terminal.put(";", 18);
        Terminal.put("(", 19); 
        Terminal.put(")", 20);
        Terminal.put("!", 21);
        Terminal.put("<=", 22);
        Terminal.put("=", 23);
        Terminal.put(">", 24);
        Terminal.put("<", 25);
        Terminal.put("/=", 26);
        Terminal.put("}", 27);
        Terminal.put("{", 28);

        naoTerminal = new HashMap<>(); // vertical
        naoTerminal.put("PROGRAM", 0);
        naoTerminal.put("CODIGO", 1);
        naoTerminal.put("COM", 2);
        naoTerminal.put("WRITE", 3);
        naoTerminal.put("READ", 4);
        naoTerminal.put("DECL", 5);
        naoTerminal.put("CONDICIONAL", 6);
        naoTerminal.put("REPETICAO", 7);
        naoTerminal.put("EXP", 8);
        naoTerminal.put("WHILE", 9);
        naoTerminal.put("ATRIBUICAO", 10);
        naoTerminal.put("IF", 11);
        naoTerminal.put("ELSE", 12);
        naoTerminal.put("OP_LOGICA", 13);
        naoTerminal.put("FATOR_TAIL", 14);
        naoTerminal.put("FATOR", 15);
        naoTerminal.put("TERM", 16);
        naoTerminal.put("TERM_TAIL", 17);
        naoTerminal.put("SOMA_SUB", 18);
        naoTerminal.put("SOMA", 19);
        naoTerminal.put("SUB", 20);
        naoTerminal.put("MULT_DIV", 21);
        naoTerminal.put("MULT", 22);
        naoTerminal.put("DIV", 23);
        naoTerminal.put("COMP", 24);
        naoTerminal.put("ATR", 25);


    }


    public static Map<String, Integer> getTerminal() {
        return Terminal;
    }

    public static Map<String, Integer> getNaoTerminal() {
        return naoTerminal;
    }

    public static List<List<String>> getRegrasProducao() {
        return asList(

                    //0 - 	<PROGRAM> ::= inicio <CODIGO> fim
                    asList("inicio","CODIGO","fim"),

                    //1 - <PROGRAM> ::= î
                    List.of(),

                    //2 -<CODIGO> ::= <COM> ";" <CODIGO>
                    asList("COM",";","CODIGO"),

                    //3 - 	<CODIGO> ::= î
                    List.of(),

                    //4 - 	<COM> ::= <DECL>
                    asList("DECL"),

                    //5 - 	<COM> ::= <WRITE>
                    asList("WRITE"),

                    //6 - 	<COM> ::= <READ>
                    asList("READ"),

                    //7 - 	<COM> ::= <CONDICIONAL>
                    asList("CONDICIONAL"),

                    //8 - 	<COM> ::= <REPETICAO>
                    asList("REPETICAO"),

                    //9 - 	<COM> ::= <ATRIBUICAO>
                    asList("ATRIBUICAO"),

                    //10 - 	<WRITE> ::= write <EXP>
                    asList("write","EXP"),

                    //11 - 	<READ> ::= read "(" var ")"
                    asList("read","(","var",")"),

                    //12 - 	<CONDICIONAL> ::= <IF>
                    asList("IF"),

                    //13 - 	<IF> ::= se <COMP> "{" <CODIGO> <ELSE> "}"
                    asList("se","COMP","{","CODIGO","ELSE","}"),

                    //14 - 	<ELSE> ::= senao <CODIGO>
                    asList("senao","CODIGO"),

                    //15 - 	<ELSE> ::= î
                    List.of(),

                    //16 - 	<REPETICAO> ::= <WHILE>
                    asList("WHILE"),

                    //17 - 	<WHILE> ::= while <COMP> "{" <CODIGO> "}"
                    asList("while","COMP","{","CODIGO","}"),

                    //18 - 	<DECL> ::= tipo <ATRIBUICAO>
                    asList("tipo","ATRIBUICAO"),

                    //19 - 	<ATRIBUICAO> ::= var <ATR>
                    asList("var","ATR"),

                    //20 - 	<ATR> ::= "=" <EXP>
                    asList(":","EXP"),

                    //21 - 	<ATR> ::= î
                    List.of(),

                    //22 - 	<EXP> ::= <TERM> <TERM_TAIL>
                    asList("TERM","TERM_TAIL"),

                    //23 - 	<TERM_TAIL> ::= <SOMA_SUB> <TERM> <TERM_TAIL>
                    asList("SOMA_SUB","TERM","TERM_TAIL"),
                 
                    //24 - 	<TERM_TAIL> ::= î
                    List.of(),

                    //25 - 	<TERM> ::= <FATOR> <FATOR_TAIL>
                    asList("FATOR","FATOR_TAIL"),

                    //26 - 	<FATOR_TAIL> ::= <MULT_DIV> <FATOR> <FATOR_TAIL>
                    asList("MULT_DIV","FATOR","FATOR_TAIL"),

                    //27 - 	<FATOR_TAIL> ::= î
                    List.of(),

                    //28 - 	<FATOR> ::= "(" <EXP> ")"
                    asList("(","EXP",")"),

                    //29 - 	<FATOR> ::= var
                    asList("var"),

                    //30 - 	<FATOR> ::= num
                    asList("num"),

                    //31 - 	<FATOR> ::= string
                    asList("string"),

                    //32 - 	<SOMA_SUB> ::= <SOMA>
                    asList("SOMA"),

                    //33 - 	<SOMA_SUB> ::= <SUB>
                    asList("SUB"),

                    //34 - 	<SOMA> ::= "+"
                    asList("+"),

                    //35 - 	<SUB> ::= "-"
                    asList("-"),

                    //36 - 	<MULT_DIV> ::= <MULT>
                    asList("MULT"),

                    //37 - 	<MULT_DIV> ::= <DIV>
                     asList("DIV"),

                    //38 - 	<MULT> ::= "*"
                     asList("*"),

                    //39 - 	<DIV> ::= "/"
                     asList("/"),

                    //40 - 	<COMP> ::= "(" <EXP> <OP_LOGICA> <EXP> ")"
                     asList("(","EXP","OP_LOGICA","EXP",")"),

                    //41 - 	<OP_LOGICA> ::= ">="
                     //asList(">="),
                     asList("!"),

                    //42 - 	<OP_LOGICA> ::= "<="
                     asList("<="),

                    //43 - 	<OP_LOGICA> ::= "=="
                     asList("="),

                    //44 - 	<OP_LOGICA> ::= ">"
                     asList(">"),

                    //45 - 	<OP_LOGICA> ::= "<"
                     asList("<"),

                    //46 - 	<OP_LOGICA> ::= "/="
                     asList("/=")

         );
    }

    public static List<List<Integer>> getTabelaSintatica() {

        return asList(
            asList(1,0,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1),
            asList(-1,-1,3,-1,2,2,2,2,-1,2,3,-1,2,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,3,-1),
            asList(-1,-1,-1,-1,6,5,8,4,-1,7,-1,-1,9,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1),
            asList(-1,-1,-1,-1,-1,10,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1),
            asList(-1,-1,-1,-1,11,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1),
            asList(-1,-1,-1,-1,-1,-1,-1,18,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1),
            asList(-1,-1,-1,-1,-1,-1,-1,-1,-1,12,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1),
            asList(-1,-1,-1,-1,-1,-1,16,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1),
            asList(-1,-1,-1,22,-1,-1,-1,-1,22,-1,-1,-1,22,-1,-1,-1,-1,-1,-1,22,-1,-1,-1,-1,-1,-1,-1,-1,-1),
            asList(-1,-1,-1,-1,-1,-1,17,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1),
            asList(-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,19,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1),
            asList(-1,-1,-1,-1,-1,-1,-1,-1,-1,13,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1),
            asList(-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,14,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,15,-1),
            asList(-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,41,42,43,44,45,46,-1,-1),
            asList(-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,27,27,26,26,27,-1,27,27,27,27,27,27,27,-1,-1),
            asList(-1,-1,-1,30,-1,-1,-1,-1,31,-1,-1,-1,29,-1,-1,-1,-1,-1,-1,28,-1,-1,-1,-1,-1,-1,-1,-1,-1),
            asList(-1,-1,-1,25,-1,-1,-1,-1,25,-1,-1,-1,25,-1,-1,-1,-1,-1,-1,25,-1,-1,-1,-1,-1,-1,-1,-1,-1),
            asList(-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,23,23,-1,-1,24,-1,24,24,24,24,24,24,24,-1,-1),
            asList(-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,32,33,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1),
            asList(-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,34,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1),
            asList(-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,35,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1),
            asList(-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,36,37,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1),
            asList(-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,38,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1),
            asList(-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,39,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1),
            asList(-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,40,-1,-1,-1,-1,-1,-1,-1,-1,-1),
            asList(-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,20,-1,-1,-1,-1,21,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1));


    }

}
