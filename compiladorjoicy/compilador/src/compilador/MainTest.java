package compilador;

//import compilador.codigo.Final;
//import javax.swing.text.html.HTMLDocument.Iterator;
import compilador.codigo.FinalException;
import compilador.codigo.Final;
import compilador.codigo.Intermediario;
import java.io.IOException;

public class MainTest {
    public static void main(String[] args) throws FinalException, IOException {

        new FlagStatus().verificaTk(args);

        String path = new FlagStatus().validatePath(args);

        var listaDeTokens = new Lexico().splitTk(new arquivo().lerAquivo(path));
        System.out.println("Analise Léxica Concluida\n");

        new Sintatico(listaDeTokens).AnalisadorSintatico();
        System.out.println("Analise Sintática Concluida\n");

        var Semantico = new Semantico(listaDeTokens);
        Semantico.analiseSemantica();
        System.out.println("Analise Semantica concluida\n");

        var intermed = new Intermediario(listaDeTokens, Semantico.getDeclarados());
        var listIntermed = intermed.gerador();
        //System.out.println("\nCodigo intermediario concluido com sucesso\n");

        var codigoFinal = new Final();
        var compilador= codigoFinal.codFinal(listIntermed);
        new arquivo().criarArquivo(compilador); 

    

    }
}
