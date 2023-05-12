package compilador;


public class MainTest {
    public static void main(String[] args) {

        new FlagStatus().verificaTk(args);

        String path = new FlagStatus().validatePath(args);

        var listaDeTokens = new lexicoToken().splitTk(new arquivo().lerAquivo(path));
        System.out.println("Analise Léxica Concluida\n");

        new Sintatico(listaDeTokens).AnalisadorSintatico();
        System.out.println("Analise Sintática Concluida\n");

        var Semantico = new Semantico(listaDeTokens);
        Semantico.analiseSemantica();
        System.out.println("Analise Semantica concluida");
    


    }
}
