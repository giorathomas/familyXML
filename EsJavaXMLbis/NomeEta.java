public class NomeEta {

    private String nome; //nome
    private String cognome; //cognome
    private int eta; //eta

    /**
     * Costruttore completo
     * @param nome Nome
     * @param cognome Cognome
     * @param eta Eta
     */
    public NomeEta(String nome, String cognome, int eta) {
        this.nome = nome;
        this.cognome = cognome;
        this.eta = eta;
    }//NomeEta

    /**
     * getNome
     * @return Nome
     */
    public String getNome() {
        return nome;
    }//getNome

    /**
     * getCognome
     * @return Cognome
     */
    public String getCognome() {
        return cognome;
    }//getCognome

    /**
     * getEta
     * @return eta
     */
    public int getEta() {
        return eta;
    }//getEta

    public String toString() {
        return "\n"+nome+" "+cognome+", eta': "+eta+" anni";
    }
}//NomeEta
