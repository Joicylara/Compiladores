package compilador;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class arquivo {

    public List<String> lerAquivo(String url) {
        //esta classe irá criar uma variavel que irá ler o arquivo através de seu endereco
        //e vai colocar o codigo em posicao na matriz de string para ser organizado e verificado
        List<String> codigo = new ArrayList<>();
        try (var arquivo = new BufferedReader(new FileReader(url))) {

            while (arquivo.ready()) {
                codigo.add(arquivo.readLine());
            }

        } catch (FileNotFoundException e) {
            System.err.println("Arquivo não encontrado " + e.getMessage());
        } catch (IOException e) {
            System.err.println("Não foi possível ler o arquivo" + e.getMessage());
        }
        return codigo;
    }

    public void criarArquivo(String codigo) throws IOException {
        // caminho para gerar o código asm

        File file = new File("C:\\Users\\JOICE\\OneDrive\\Compiladores\\Compilador\\compiladorjoicy\\compilador\\src\\compilador\\codigo\\TesteTriangulo.asm");
        //File file = new File("C:\\Users\\JOICE\\OneDrive\\Compiladores\\Compilador\\compiladorjoicy\\compilador\\src\\compilador\\codigo\\TestePascal.asm");
        file.createNewFile();

        FileWriter fw = new FileWriter(file.getAbsolutePath());
        var bw = new BufferedWriter(fw);

        bw.write(codigo);
        bw.close();
    } 


}