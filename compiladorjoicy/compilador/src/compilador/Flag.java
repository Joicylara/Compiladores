package compilador;

public enum Flag {

    TOKENS("-lt", false),
    SINTATICO("-ls", false),
    SEMANTICO("-lse", false),
    CODIGO_INTER("-li", false);
 
    
    private Boolean status;

    Flag(String value, boolean status) {
       
        this.status = status;
    }

    public Boolean getStatus() {

        return this.status;
    }

    public void setStatus(Boolean status) {

        this.status = status;
    }
}
