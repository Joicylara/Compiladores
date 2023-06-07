package compilador;

import java.util.LinkedList;
import java.util.List;
import java.util.Stack;

import static compilador.ListaSintatico.*;

public class Sintatico {

    private LinkedList<Tokens> filaToken;//cria um privado pra fila
    private Stack<String> pilha; // cria um privado pra pilha

    public Sintatico(List<Tokens> listofTokens) {
        this.filaToken = new LinkedList<>(listofTokens);// aqui ele iniciará a fila
        this.pilha = new Stack<>();// inicia a pilha

        //add $ para mostrar que a analise foi realizada com sucesso
        pilha.push("$");
        pilha.push("PROGRAM");
    }

    // estrutura de repetição onde fará a analise sintatica
    public void AnalisadorSintatico() {
        while (!pilha.empty()) { //verifica se a pilha não esta vazia
            var Tok = filaToken.getFirst().getToken(); //recebe o valor na primeira posição
            var Linha = filaToken.getFirst().getLinha(); //recebe o que esta na primeira posição na linha
            var Coluna = filaToken.getFirst().getColuna(); //recebe  o que está na primeira posição na coluna
            var Topo = pilha.peek(); // verifica o que esta no topo
            if (isTerminal(Topo)) { // se terminal estiver no topo, terminaltopo recebe as instruções
                terminalTopo(Tok, Linha, Coluna);
            } else {
                naoTerminalTopo(Tok, Linha, Coluna);  // senão naoterminal recebe instrução
            }
        }
    }

    //
    private void naoTerminalTopo(String Tok, int Linha, int Coluna) {
        var Topo = pilha.peek();
        var IdTopo = naoTerminal.get(Topo);
        var IdToken = Terminal.get(Tok);
        //vai fazer a verificação da tabela vendo o id do token e do topo
        var Ind = getTabelaSintatica().get(IdTopo).get(IdToken);

        // se o indice 'Ind' estiver menor que 0 é porque não existe a condição
        // terminal e nãoterminal, neste caso ocorrera erro sintatico
        if (Ind < 0) {
            erro(Topo, Tok, Linha, Coluna);
        }
        var RegrasProducao = new LinkedList<>(getRegrasProducao().get(Ind));
        log((!isTerminal(Topo) ? "Nao terminal \"" : "token \"") + Topo + "\" foi removido na pilha");
        pilha.pop();

        while (!RegrasProducao.isEmpty()) {
            var TipoRegra = RegrasProducao.removeLast();
            log((!isTerminal(TipoRegra) ? "Nao terminal \"" : "token \"") + TipoRegra + "\" foi colocada na pilha");
            pilha.push(TipoRegra);
        }
    }

    private void terminalTopo(String Tok, int Linha, int Coluna) {
        var Topo = pilha.peek();
        var IdTopo = Terminal.get(Topo);
        var IdToken = Terminal.get(Tok);
        //fixme: não reconhece o terminal 'id', procurar no gals e no html
        if (IdToken.equals(IdTopo)) {
            filaToken.removeFirst();
            pilha.pop();
            log("Token \"" + Tok + "\" foi removido da Lista de tokens");
            log("Token \"" + Topo + "\" foi removido da Pilha de tokens");
        } else {
            erro(Topo, Tok, Linha, Coluna);
        }

    }

    private boolean isTerminal(String s) {
        return Terminal.containsKey(s);
    }

    private void log(String msg) {
        if (Flag.SINTATICO.getStatus()) {   //flag para printar
            System.out.println(msg);
        }
    }


    //emite essa mensagem caso ocorra um erro durante o processo de análise
    private void erro(String Topo, String Tok, int Linha, int Coluna) {
        throw new SintaticException("[" + Linha + ", " + Coluna + "]" +
                " foi encontrado o token: \"" + Tok + "\", porem era esperado: \"" + Topo + "\"");
    }


}
