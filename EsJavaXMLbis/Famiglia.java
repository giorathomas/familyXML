import java.io.File;
import java.io.FileNotFoundException;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.w3c.dom.Node;
import org.xml.sax.SAXParseException;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.DocumentBuilder;
import java.util.ArrayList;

public class Famiglia{

    private Element radice;
    private ArrayList<String> straniere=new ArrayList<>();
    private ArrayList<String> nazionalita=new ArrayList<>();

    /**
     * Costruttore completo
     * @param nomeFile nome del file xml
     */
    public Famiglia(String nomeFile) {

        try {
            DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
            DocumentBuilder domParser = dbf.newDocumentBuilder();
            Document documento = domParser.parse(new File(nomeFile));
            radice = documento.getDocumentElement();
        }//try
        catch (SAXParseException e) {
            System.out.println("Errore di parsing: " + e.getMessage());
            System.exit(1);
        }//catch
        catch (FileNotFoundException e) {
            System.out.println("File " + nomeFile + " non trovato");
            System.exit(1);
        }//catch
        catch (Exception e) {
            e.printStackTrace();
        }//catch
    }//Famiglia

    /**
     * Metodo che, dato un nominativo, dice se è ancora vivo
     * @param nome Nome della persona
     * @param cognome Cognome della Persona
     * @return true se vivo, false se morto, false se non trovato
     */
    public boolean isVivo(String nome, String cognome){

        int nP=radice.getChildNodes().item(0).getChildNodes().getLength()-1+radice.getChildNodes().item(1).getChildNodes().item(0).getChildNodes().getLength()-1+radice.getChildNodes().item(1).getChildNodes().item(1).getChildNodes().getLength()-1+radice.getChildNodes().item(2).getChildNodes().item(0).getChildNodes().getLength()-1+radice.getChildNodes().item(2).getChildNodes().item(1).getChildNodes().getLength()-1;

        //genitori
        for(int i=0; i<radice.getChildNodes().getLength(); i++){
            for(int y=0; y<radice.getChildNodes().item(i).getChildNodes().getLength(); y++){
                if(radice.getChildNodes().item(i).getChildNodes().item(y).getFirstChild().getTextContent().equals(nome) && radice.getChildNodes().item(i).getChildNodes().item(y).getFirstChild().getNextSibling().getTextContent().equals(cognome)){
                  if(radice.getChildNodes().item(i).getChildNodes().item(y).getLastChild().getTextContent().equals("0")) return false;
                    else return true;
                }

            }

        }
        //nonni e bisnonni
        for(int i=1; i<radice.getChildNodes().getLength(); i++){
            for(int y=0; y<radice.getChildNodes().item(i).getChildNodes().getLength(); y++){
                for(int z=0; z<radice.getChildNodes().item(i).getChildNodes().item(y).getChildNodes().getLength();z++) {
                    if (radice.getChildNodes().item(i).getChildNodes().item(y).getChildNodes().item(z).getFirstChild().getTextContent().equals(nome) && radice.getChildNodes().item(i).getChildNodes().item(y).getChildNodes().item(z).getFirstChild().getNextSibling().getTextContent().equals(cognome)) {
                        if (radice.getChildNodes().item(i).getChildNodes().item(y).getChildNodes().item(z).getLastChild().getTextContent().equals("0") || radice.getChildNodes().item(i).getChildNodes().item(y).getChildNodes().item(z).getLastChild().getPreviousSibling().getTextContent().equals("0"))
                            return false;
                        else return true;
                    }
                }
            }

        }

        return false;
    }//isVivo

    /**
     * Metodo che, dato un anno, ritorna quanti sono nati in quell'anno
     * @param anno Anno di nascita
     * @return Stringa con i nati
     */
    public String isNato(String anno){

        String nati="";

        //genitori
        for(int i=0; i<radice.getChildNodes().getLength(); i++){
            for(int y=0; y<radice.getChildNodes().item(i).getChildNodes().getLength()-1; y++){
                if(radice.getChildNodes().item(i).getChildNodes().item(y).getChildNodes().item(2).getLastChild().getTextContent().equals(anno)){
                    nati+=radice.getChildNodes().item(i).getChildNodes().item(y).getFirstChild().getTextContent()+" "+ radice.getChildNodes().item(i).getChildNodes().item(y).getFirstChild().getNextSibling().getTextContent() + " ";
                }

            }

        }

        //nonni e bisnonni
        for(int i=1; i<radice.getChildNodes().getLength(); i++){
            for(int y=0; y<radice.getChildNodes().item(i).getChildNodes().getLength(); y++){
                for(int z=0; z<radice.getChildNodes().item(i).getChildNodes().item(y).getChildNodes().getLength()-1;z++) {
                    if (radice.getChildNodes().item(i).getChildNodes().item(y).getChildNodes().item(z).getChildNodes().item(2).getLastChild().getTextContent().equals(anno)) {
                        nati+=radice.getChildNodes().item(i).getChildNodes().item(y).getChildNodes().item(z).getFirstChild().getTextContent()+" "+ radice.getChildNodes().item(i).getChildNodes().item(y).getChildNodes().item(z).getFirstChild().getNextSibling().getTextContent() + ", ";
                    }
                }
            }

        }

        return "Nati nel "+anno+ " sono: "+nati;
    }//isNato

    /**
     * Metodo che, dato un anno, ritorna quanti sono morti in quell'anno
     * @param anno Anno di morte
     * @return Le persone morte in quell'anno
     */
    public String isMorto(String anno){

        String morti="";

        //genitori
        for(int i=0; i<radice.getChildNodes().getLength(); i++){
            for(int y=0; y<radice.getChildNodes().item(i).getChildNodes().getLength()-1; y++){
                if(radice.getChildNodes().item(i).getChildNodes().item(y).getLastChild().getTextContent().equals(anno)){
                    morti+=radice.getChildNodes().item(i).getChildNodes().item(y).getFirstChild().getTextContent()+" "+ radice.getChildNodes().item(i).getChildNodes().item(y).getFirstChild().getNextSibling().getTextContent() + " ";
                }

            }

        }

        //nonni e bisnonni
        for(int i=1; i<radice.getChildNodes().getLength(); i++){
            for(int y=0; y<radice.getChildNodes().item(i).getChildNodes().getLength(); y++){
                for(int z=0; z<radice.getChildNodes().item(i).getChildNodes().item(y).getChildNodes().getLength()-1;z++) {
                    if (radice.getChildNodes().item(i).getChildNodes().item(y).getChildNodes().item(z).getLastChild().getTextContent().equals(anno)) {
                        morti+=radice.getChildNodes().item(i).getChildNodes().item(y).getChildNodes().item(z).getFirstChild().getTextContent()+" "+ radice.getChildNodes().item(i).getChildNodes().item(y).getChildNodes().item(z).getFirstChild().getNextSibling().getTextContent() + ", ";
                    }
                }
            }

        }

        return "Morti nel "+anno+ " sono: "+morti;
    }//isMorto

    /**
     * Metodo che, dato un nominativo, ritorna il consorte
     * @param nome Nome persona
     * @param cognome Cognome persona
     * @return Il consorte
     */
    public String consorte(String nome, String cognome){

        //genitori
        for(int i=0; i<radice.getChildNodes().getLength(); i++){
            for(int y=0; y<radice.getChildNodes().item(i).getChildNodes().getLength(); y++){
                if(radice.getChildNodes().item(i).getChildNodes().item(y).getFirstChild().getTextContent().equals(nome) && radice.getChildNodes().item(i).getChildNodes().item(y).getFirstChild().getNextSibling().getTextContent().equals(cognome)){
                    if(radice.getChildNodes().item(i).getChildNodes().item(y).getNextSibling().getNodeName().equals("padre")){
                        return "Consorte: "+ radice.getChildNodes().item(i).getChildNodes().item(y).getNextSibling().getFirstChild().getTextContent()+ " "+ radice.getChildNodes().item(i).getChildNodes().item(y).getNextSibling().getFirstChild().getNextSibling().getTextContent();
                    } else if(radice.getChildNodes().item(i).getChildNodes().item(y).getPreviousSibling().getNodeName().equals("madre")){
                        return "Consorte: "+ radice.getChildNodes().item(i).getChildNodes().item(y).getPreviousSibling().getFirstChild().getTextContent()+ " "+ radice.getChildNodes().item(i).getChildNodes().item(y).getPreviousSibling().getFirstChild().getNextSibling().getTextContent();
                    }
                }

            }

        }

        //nonni e bisnonni
        for(int i=1; i<radice.getChildNodes().getLength(); i++){
            for(int y=0; y<radice.getChildNodes().item(i).getChildNodes().getLength(); y++){
                for(int z=0; z<radice.getChildNodes().item(i).getChildNodes().item(y).getChildNodes().getLength();z++) {
                    if(radice.getChildNodes().item(i).getChildNodes().item(y).getChildNodes().item(z).getFirstChild().getTextContent().equals(nome) && radice.getChildNodes().item(i).getChildNodes().item(y).getChildNodes().item(z).getFirstChild().getNextSibling().getTextContent().equals(cognome)){
                        if(radice.getChildNodes().item(i).getChildNodes().item(y).getChildNodes().item(z).getNextSibling().getNodeName().equals("nonna") || radice.getChildNodes().item(i).getChildNodes().item(y).getChildNodes().item(z).getNextSibling().getNodeName().equals("bisnonna")){
                            return "Consorte: "+ radice.getChildNodes().item(i).getChildNodes().item(y).getChildNodes().item(z).getNextSibling().getFirstChild().getTextContent()+ " "+ radice.getChildNodes().item(i).getChildNodes().item(y).getChildNodes().item(z).getNextSibling().getFirstChild().getNextSibling().getTextContent();
                        } else if(radice.getChildNodes().item(i).getChildNodes().item(y).getChildNodes().item(z).getPreviousSibling().getNodeName().equals("nonno") || radice.getChildNodes().item(i).getChildNodes().item(y).getChildNodes().item(z).getPreviousSibling().getNodeName().equals("bisnonno")){
                            return "Consorte: "+ radice.getChildNodes().item(i).getChildNodes().item(y).getChildNodes().item(z).getPreviousSibling().getFirstChild().getTextContent()+ " "+ radice.getChildNodes().item(i).getChildNodes().item(y).getChildNodes().item(z).getPreviousSibling().getFirstChild().getNextSibling().getTextContent();
                        }
                    }
                }
            }

        }

        return "Consorte non trovato";
    }//consorte

    /**
     * Metodo che, dato un nominativo, ritorna il consorte
     * @param nome Nome persona
     * @param cognome Cognome persona
     * @return Il consorte
     */
    private String consorteNoCons(String nome, String cognome){

        //genitori
        for(int i=0; i<radice.getChildNodes().getLength(); i++){
            for(int y=0; y<radice.getChildNodes().item(i).getChildNodes().getLength(); y++){
                if(radice.getChildNodes().item(i).getChildNodes().item(y).getFirstChild().getTextContent().equals(nome) && radice.getChildNodes().item(i).getChildNodes().item(y).getFirstChild().getNextSibling().getTextContent().equals(cognome)){
                    if(radice.getChildNodes().item(i).getChildNodes().item(y).getNextSibling().getNodeName().equals("padre")){
                        return radice.getChildNodes().item(i).getChildNodes().item(y).getNextSibling().getFirstChild().getTextContent()+ " "+ radice.getChildNodes().item(i).getChildNodes().item(y).getNextSibling().getFirstChild().getNextSibling().getTextContent();
                    } else if(radice.getChildNodes().item(i).getChildNodes().item(y).getPreviousSibling().getNodeName().equals("madre")){
                        return radice.getChildNodes().item(i).getChildNodes().item(y).getPreviousSibling().getFirstChild().getTextContent()+ " "+ radice.getChildNodes().item(i).getChildNodes().item(y).getPreviousSibling().getFirstChild().getNextSibling().getTextContent();
                    }
                }

            }

        }

        //nonni e bisnonni
        for(int i=1; i<radice.getChildNodes().getLength(); i++){
            for(int y=0; y<radice.getChildNodes().item(i).getChildNodes().getLength(); y++){
                for(int z=0; z<radice.getChildNodes().item(i).getChildNodes().item(y).getChildNodes().getLength();z++) {
                    if(radice.getChildNodes().item(i).getChildNodes().item(y).getChildNodes().item(z).getFirstChild().getTextContent().equals(nome) && radice.getChildNodes().item(i).getChildNodes().item(y).getChildNodes().item(z).getFirstChild().getNextSibling().getTextContent().equals(cognome)){
                        if(radice.getChildNodes().item(i).getChildNodes().item(y).getChildNodes().item(z).getNextSibling().getNodeName().equals("nonna") || radice.getChildNodes().item(i).getChildNodes().item(y).getChildNodes().item(z).getNextSibling().getNodeName().equals("bisnonna")){
                            return radice.getChildNodes().item(i).getChildNodes().item(y).getChildNodes().item(z).getNextSibling().getFirstChild().getTextContent()+ " "+ radice.getChildNodes().item(i).getChildNodes().item(y).getChildNodes().item(z).getNextSibling().getFirstChild().getNextSibling().getTextContent();
                        } else if(radice.getChildNodes().item(i).getChildNodes().item(y).getChildNodes().item(z).getPreviousSibling().getNodeName().equals("nonno") || radice.getChildNodes().item(i).getChildNodes().item(y).getChildNodes().item(z).getPreviousSibling().getNodeName().equals("bisnonno")){
                            return radice.getChildNodes().item(i).getChildNodes().item(y).getChildNodes().item(z).getPreviousSibling().getFirstChild().getTextContent()+ " "+ radice.getChildNodes().item(i).getChildNodes().item(y).getChildNodes().item(z).getPreviousSibling().getFirstChild().getNextSibling().getTextContent();
                        }
                    }
                }
            }

        }

        return "Consorte non trovato";
    }//consorte


    /**
     * Ritorna gli avi diretti (genitori) di un nominativo
     * @param nome Nome della persona
     * @param cognome Cognome della persona
     * @return Gli avi diretti di una persona
     */
    public String avi(String nome, String cognome){

        //genitori
        for(int i=0; i<radice.getChildNodes().getLength(); i++){
            for(int y=0; y<radice.getChildNodes().item(i).getLastChild().getChildNodes().getLength(); y++){
                    if (radice.getChildNodes().item(i).getLastChild().getChildNodes().item(y).getFirstChild().getTextContent().equals(nome) && radice.getChildNodes().item(i).getLastChild().getChildNodes().item(y).getFirstChild().getNextSibling().getTextContent().equals(cognome)) {
                        return "Avi diretti: " + radice.getChildNodes().item(i).getFirstChild().getFirstChild().getTextContent() + " " + radice.getChildNodes().item(i).getFirstChild().getFirstChild().getNextSibling().getTextContent() +", " +consorteNoCons(radice.getChildNodes().item(i).getFirstChild().getFirstChild().getTextContent(), radice.getChildNodes().item(i).getFirstChild().getFirstChild().getNextSibling().getTextContent());
                    }

            }

        }

        //nonni e bisnonni
        for(int i=1; i<radice.getChildNodes().getLength(); i++){
            for(int y=0; y<radice.getChildNodes().item(i).getChildNodes().getLength(); y++){
                for(int z=0; z<radice.getChildNodes().item(i).getChildNodes().item(y).getLastChild().getChildNodes().getLength();z++) {
                    for(int n=0; n<radice.getChildNodes().item(i).getChildNodes().item(y).getLastChild().getChildNodes().getLength();n++){
                        if (radice.getChildNodes().item(i).getChildNodes().item(y).getLastChild().getChildNodes().item(n).getFirstChild().getTextContent().equals(nome) && radice.getChildNodes().item(i).getChildNodes().item(y).getLastChild().getChildNodes().item(n).getFirstChild().getNextSibling().getTextContent().equals(cognome)) {
                            return "Avi diretti: " + radice.getChildNodes().item(i).getChildNodes().item(y).getFirstChild().getFirstChild().getTextContent() + " " + radice.getChildNodes().item(i).getChildNodes().item(y).getFirstChild().getFirstChild().getNextSibling().getTextContent() + ", " + consorteNoCons(radice.getChildNodes().item(i).getChildNodes().item(y).getFirstChild().getFirstChild().getTextContent(), radice.getChildNodes().item(i).getChildNodes().item(y).getFirstChild().getFirstChild().getNextSibling().getTextContent());
                        }
                    }

                }
            }

        }

        return "";
    }//avi

    /**
     * Metodo che ritorna i nominativi della coppia più prolifica
     * @return ritorna i nominativi della coppia più prolifica
     */
    public String prolifica(){

        int max=0;
        int indexI=0;
        int indexZ=-1;

        for(int i=0; i<radice.getChildNodes().getLength(); i++){
            if(radice.getChildNodes().item(i).getLastChild().getChildNodes().getLength()>max) {
                max = radice.getChildNodes().item(i).getLastChild().getChildNodes().getLength();
                indexI=i;
            }
        }

        for(int i=0; i<radice.getChildNodes().getLength(); i++){
            for(int z=0; z<radice.getChildNodes().item(i).getChildNodes().getLength();z++) {
                if(radice.getChildNodes().item(i).getChildNodes().item(z).getLastChild().getChildNodes().getLength()>max){
                    max = radice.getChildNodes().item(i).getChildNodes().item(z).getLastChild().getChildNodes().getLength();
                    indexI=i;
                    indexZ=z;
                }

            }
        }

        if(indexZ!=-1){
            return "Coppia piu' prolifica: "+radice.getChildNodes().item(indexI).getChildNodes().item(indexZ).getFirstChild().getFirstChild().getTextContent() + " "+radice.getChildNodes().item(indexI).getChildNodes().item(indexZ).getFirstChild().getFirstChild().getNextSibling().getTextContent() + ", "+consorteNoCons(radice.getChildNodes().item(indexI).getChildNodes().item(indexZ).getFirstChild().getFirstChild().getTextContent(), radice.getChildNodes().item(indexI).getChildNodes().item(indexZ).getFirstChild().getFirstChild().getNextSibling().getTextContent());
        }

        System.out.println(max);

        return "";
    }//prolifica

    /**
     * Metodo che ritorna l'elenco delle persone straniere
     * @return l'elenco delle persone straniere
     */
    public ArrayList<String> getStraniere(){

        //genitori
        for(int i=0; i<radice.getChildNodes().getLength(); i++){

                for(int n=0; n<radice.getChildNodes().item(i).getLastChild().getChildNodes().getLength();n++){
                    if (radice.getChildNodes().item(i).getLastChild().getChildNodes().item(n).hasAttributes()) {
                        String nome= radice.getChildNodes().item(i).getLastChild().getChildNodes().item(n).getFirstChild().getTextContent()+" "+ radice.getChildNodes().item(i).getLastChild().getChildNodes().item(n).getFirstChild().getNextSibling().getTextContent();
                        straniere.add(nome);
                    }

                }

        }


        //nonni e bisnonni
        for(int i=1; i<radice.getChildNodes().getLength(); i++){
            for(int y=0; y<radice.getChildNodes().item(i).getChildNodes().getLength(); y++){
                    for(int n=0; n<radice.getChildNodes().item(i).getChildNodes().item(y).getLastChild().getChildNodes().getLength();n++){
                        if (radice.getChildNodes().item(i).getChildNodes().item(y).getLastChild().getChildNodes().item(n).hasAttributes()) {
                            String nome= radice.getChildNodes().item(i).getChildNodes().item(y).getLastChild().getChildNodes().item(n).getFirstChild().getTextContent()+" "+ radice.getChildNodes().item(i).getChildNodes().item(y).getLastChild().getChildNodes().item(n).getFirstChild().getNextSibling().getTextContent();
                            straniere.add(nome);
                        }

                    }
            }
        }

        return straniere;
    }//getStraniere

    /**
     * Metodo che ritorna l'elenco delle nazionalita
     * @return l'elenco delle nazionalita
     */
    public ArrayList<String> getNazionalita(){
        //genitori
        for(int i=0; i<radice.getChildNodes().getLength(); i++){

            for(int n=0; n<radice.getChildNodes().item(i).getLastChild().getChildNodes().getLength();n++){
                if (radice.getChildNodes().item(i).getLastChild().getChildNodes().item(n).hasAttributes()) {
                    String stato= radice.getChildNodes().item(i).getLastChild().getChildNodes().item(n).getAttributes().item(0).getTextContent();
                    nazionalita.add(stato);
                }

            }

        }


        //nonni e bisnonni
        for(int i=1; i<radice.getChildNodes().getLength(); i++){
            for(int y=0; y<radice.getChildNodes().item(i).getChildNodes().getLength(); y++){
                for(int n=0; n<radice.getChildNodes().item(i).getChildNodes().item(y).getLastChild().getChildNodes().getLength();n++){
                    if (radice.getChildNodes().item(i).getChildNodes().item(y).getLastChild().getChildNodes().item(n).hasAttributes()) {
                        String stato= radice.getChildNodes().item(i).getChildNodes().item(y).getLastChild().getChildNodes().item(n).getAttributes().item(0).getTextContent();
                        nazionalita.add(stato);
                    }

                }
            }
        }

        return nazionalita;
    }

    /**
     * Metodo che ritorna per l'elenco (con l'età) di tutte le persone morte
     * @return l'elenco (con l'età) di tutte le persone morte
     */
    public ArrayList<NomeEta> morti(){
        ArrayList<NomeEta> temp=new ArrayList<>();
        //genitori
        for(int i=0; i<radice.getChildNodes().getLength(); i++){
            for(int y=0; y<radice.getChildNodes().item(i).getChildNodes().getLength(); y++){

                    if(radice.getChildNodes().item(i).getChildNodes().item(y).getLastChild().getTextContent().equals("0")) {
                        String nome=radice.getChildNodes().item(i).getChildNodes().item(y).getFirstChild().getTextContent();
                        String cognome=radice.getChildNodes().item(i).getChildNodes().item(y).getFirstChild().getNextSibling().getTextContent();
                        int eta=Integer.parseInt(radice.getChildNodes().item(i).getChildNodes().item(y).getLastChild().getTextContent())-Integer.parseInt(radice.getChildNodes().item(i).getChildNodes().item(y).getChildNodes().item(2).getLastChild().getTextContent());
                        temp.add(new NomeEta(nome, cognome, eta));
                    }



            }

        }
        //nonni e bisnonni
        for(int i=1; i<radice.getChildNodes().getLength(); i++){
            for(int y=0; y<radice.getChildNodes().item(i).getChildNodes().getLength(); y++){
                for(int z=0; z<radice.getChildNodes().item(i).getChildNodes().item(y).getChildNodes().getLength();z++) {

                        if (radice.getChildNodes().item(i).getChildNodes().item(y).getChildNodes().item(z).getLastChild().getTextContent().equals("0") || radice.getChildNodes().item(i).getChildNodes().item(y).getChildNodes().item(z).getLastChild().getPreviousSibling().getTextContent().equals("0")){
                            String nome=radice.getChildNodes().item(i).getChildNodes().item(y).getChildNodes().item(z).getFirstChild().getTextContent();
                            String cognome= radice.getChildNodes().item(i).getChildNodes().item(y).getChildNodes().item(z).getFirstChild().getNextSibling().getTextContent();
                            int eta=Integer.parseInt(radice.getChildNodes().item(i).getChildNodes().item(y).getChildNodes().item(z).getLastChild().getTextContent())-Integer.parseInt(radice.getChildNodes().item(i).getChildNodes().item(y).getChildNodes().item(z).getChildNodes().item(2).getLastChild().getTextContent());
                            temp.add(new NomeEta(nome, cognome, eta));
                        }


                }
            }

        }

        return temp;
    }

    /**
     * Metodo che ritorna per l'elenco (con l'età) di tutte le persone del documento
     * @return l'elenco (con l'età) di tutte le persone
     */
    public ArrayList<NomeEta> persone(){
        ArrayList<NomeEta> temp=new ArrayList<>();
        //genitori
        for(int i=0; i<1; i++){
            for(int y=0; y<radice.getChildNodes().item(i).getChildNodes().getLength(); y++){

                if(radice.getChildNodes().item(i).getChildNodes().item(y).getLastChild().getTextContent().equals("0")) {
                    String nome=radice.getChildNodes().item(i).getChildNodes().item(y).getFirstChild().getTextContent();
                    String cognome=radice.getChildNodes().item(i).getChildNodes().item(y).getFirstChild().getNextSibling().getTextContent();
                    int eta=Integer.parseInt(radice.getChildNodes().item(i).getChildNodes().item(y).getLastChild().getTextContent())-Integer.parseInt(radice.getChildNodes().item(i).getChildNodes().item(y).getChildNodes().item(2).getLastChild().getTextContent());
                    temp.add(new NomeEta(nome, cognome, eta));
                } else if(!radice.getChildNodes().item(i).getChildNodes().item(y).getNodeName().equals("figli")) {
                    String nome=radice.getChildNodes().item(i).getChildNodes().item(y).getFirstChild().getTextContent();
                    String cognome=radice.getChildNodes().item(i).getChildNodes().item(y).getFirstChild().getNextSibling().getTextContent();
                    int eta=2020-Integer.parseInt(radice.getChildNodes().item(i).getChildNodes().item(y).getChildNodes().item(2).getLastChild().getTextContent());
                    temp.add(new NomeEta(nome, cognome, eta));
                }



            }

        }
        //nonni e bisnonni
        for(int i=1; i<radice.getChildNodes().getLength(); i++){
            for(int y=0; y<radice.getChildNodes().item(i).getChildNodes().getLength(); y++){
                for(int z=0; z<radice.getChildNodes().item(i).getChildNodes().item(y).getChildNodes().getLength();z++) {

                    if (radice.getChildNodes().item(i).getChildNodes().item(y).getChildNodes().item(z).getLastChild().getTextContent().equals("0") || radice.getChildNodes().item(i).getChildNodes().item(y).getChildNodes().item(z).getLastChild().getPreviousSibling().getTextContent().equals("0")){
                        String nome=radice.getChildNodes().item(i).getChildNodes().item(y).getChildNodes().item(z).getFirstChild().getTextContent();
                        String cognome= radice.getChildNodes().item(i).getChildNodes().item(y).getChildNodes().item(z).getFirstChild().getNextSibling().getTextContent();
                        int eta=Integer.parseInt(radice.getChildNodes().item(i).getChildNodes().item(y).getChildNodes().item(z).getLastChild().getTextContent())-Integer.parseInt(radice.getChildNodes().item(i).getChildNodes().item(y).getChildNodes().item(z).getChildNodes().item(2).getLastChild().getTextContent());
                        temp.add(new NomeEta(nome, cognome, eta));
                    } else if((!radice.getChildNodes().item(i).getChildNodes().item(y).getChildNodes().item(z).getNodeName().equals("figli"))){
                        String nome=radice.getChildNodes().item(i).getChildNodes().item(y).getChildNodes().item(z).getFirstChild().getTextContent();
                        String cognome= radice.getChildNodes().item(i).getChildNodes().item(y).getChildNodes().item(z).getFirstChild().getNextSibling().getTextContent();
                        int eta=2020-Integer.parseInt(radice.getChildNodes().item(i).getChildNodes().item(y).getChildNodes().item(z).getChildNodes().item(2).getLastChild().getTextContent());
                        temp.add(new NomeEta(nome, cognome, eta));
                    }


                }
            }

        }

        return temp;
    }

}//Famiglia

