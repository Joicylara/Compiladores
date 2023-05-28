package compilador;

public class Tokens {

    private String token;
    private String lexeme;
    private int Linha;
    private int Coluna;

    public Tokens(String token, String lexeme, int Linha, int Coluna) {
        this.token = token;
        this.lexeme = lexeme;
        this.Linha = Linha;
        this.Coluna = Coluna;
    }

    public String getToken() {

        return token;
    }

    public void setToken(String token) {

        this.token = token;
    }

    public String getLexeme() {

        return lexeme;
    }

    public void setLexeme(String lexeme) {

        this.lexeme = lexeme;
    }

    public int getLinha() {

        return Linha;
    }

    public void setLinha(int Linha) {

        this.Linha = Linha;
    }

    public int getColuna() {

        return Coluna;
    }

    public void setColuna(int Coluna) {

        this.Coluna = Coluna;
    }

    @Override
    public String toString() {
        return "[" + Linha + ", " +
                Coluna + "] " +
                "token ='" + token + "'" +
                ", lexeme ='" + lexeme + "'";
    }
}
