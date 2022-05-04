import javafx.beans.property.*;


public class MisurazioneGlicemia { //00
    private final SimpleStringProperty marcaTemporale;
    private final SimpleStringProperty pasto;
    private final SimpleIntegerProperty valoreGlicemia;
    private final SimpleStringProperty primaDopoPasto;
    private final SimpleIntegerProperty idMisurazione;
    
    public MisurazioneGlicemia( int valoreGlicemia, String marcaTemporale, String pasto, String primaDopoPasto, int id){
        this.valoreGlicemia=new SimpleIntegerProperty(valoreGlicemia);
        this.marcaTemporale= new SimpleStringProperty(marcaTemporale);
        this.pasto= new SimpleStringProperty(pasto);
        this.primaDopoPasto= new SimpleStringProperty(primaDopoPasto);
        this.idMisurazione= new SimpleIntegerProperty(id);
    }
    
    public int getValoreGlicemia(){
        return valoreGlicemia.get();
    }
    
    public String getMarcaTemporale(){
        return marcaTemporale.get();
    }
    
    public String getPasto(){
        return pasto.get();
    }
    
    public String getPrimaDopoPasto(){
        return primaDopoPasto.get();
    }
    public int getId(){
        return idMisurazione.get();
    }
    
    public MisurazioneGlicemia getMisurazioneGlicemia(){//01
        return this;
    }
    //02
    public void setValoreGlicemia(int valore){
        valoreGlicemia.set(valore);
    }
    
    public void setPasto(String nuovopasto){
        pasto.set(nuovopasto);
    }
    
    public void setPrimaDopoPasto(String primadopo){
        primaDopoPasto.set(primadopo);
    }
    //03
    public SimpleStringProperty getNomePastoProperty() { 
        return this.pasto; 
    }
    public SimpleStringProperty getPrimaDopoProperty() {
        return this.primaDopoPasto; 
    }
    
    public SimpleIntegerProperty getValoreGlicemiaProperty() {
        return this.valoreGlicemia; 
    }
    
     public SimpleStringProperty getMarcaTemporaleProperty() {
        return this.marcaTemporale; 
    }
}


/* Commenti al codice
00) Classe bean per inserire elementi nella tabella.
01) Metodo che restituisce l'oggetto bean stesso. Utilizzato per salvataggio 
    delle modifiche da tabella, o per l'aggiunta di nuovi pasti da tabella.
02) Meotdi utilizzati per impostare i nuovi valori scelti nella tabella tramite
    Combo Box o TextField.
03) Metodi che restituiscono i valori non come primitive o stringhe ma come
    SimpleTypeProperty per la selezione del primo elemento nelle combo box.
*/