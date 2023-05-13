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


}