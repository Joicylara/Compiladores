package compilador;

public enum Flag {

    TOKENS("-lt", false),
    SINTATICO("-ls", false),
    SEMANTICO("-lse", false);


    //private String value;
    private Boolean status;

    Flag(String value, boolean status) {
       // this.value = value;
        this.status = status;
    }

    public Boolean getStatus() {

        return this.status;
    }

    public void setStatus(Boolean status) {

        this.status = status;
    }
}
