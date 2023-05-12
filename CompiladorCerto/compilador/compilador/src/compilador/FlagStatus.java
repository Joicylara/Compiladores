package compilador;

import static compilador.Flag.*;

public class FlagStatus {

    public void verificaTk(String[] cmd) {

        for (String s : cmd) {

            if (s.equals("-lt")) {
                TOKENS.setStatus(true);
            } else if (s.equals("-ls")) {
                SINTATICO.setStatus(true);
            }else  if (s.equals("-lse")) {
                SEMANTICO.setStatus(true);
            }
        }
    }

    public String validatePath(String[] cmd) {
        for (String arg : cmd) {
            if (arg.contains(".txt")) return arg;
        }
        throw new IllegalArgumentException("Arquivo não reconhecido");
    }

}