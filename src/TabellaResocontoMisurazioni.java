
import java.util.*;
import javafx.collections.*;
import javafx.event.*;
import javafx.scene.control.*;
import javafx.scene.control.cell.*;
import javafx.util.converter.*;


public class TabellaResocontoMisurazioni extends TableView<MisurazioneGlicemia>{//00
    private ObservableList<MisurazioneGlicemia> listaMisurazioni;
    private ObservableList<String> primaDopo;
    private ObservableList<String> listaPasti;
    
    public TabellaResocontoMisurazioni(DatiConfigurazioneXML dcxml){
        
        setEditable(true);//01
        
        TableColumn<MisurazioneGlicemia, String> colonnaData = new TableColumn("Data");
        colonnaData.setCellValueFactory(cellData -> cellData.getValue().getMarcaTemporaleProperty()); 
        colonnaData.setMinWidth(150);
        
        
        listaPasti= FXCollections.observableArrayList();
        List<String> valorilistaPasti = new ArrayList<>();
        valorilistaPasti.add("Colazione");
        valorilistaPasti.add("Pranzo");
        valorilistaPasti.add("Cena");
        listaPasti.addAll(valorilistaPasti);
        
        TableColumn<MisurazioneGlicemia, String> colonnaPasto = new TableColumn("Pasto");//02
        colonnaPasto.setCellValueFactory(cellData -> cellData.getValue().getNomePastoProperty());
        colonnaPasto.setCellFactory(ComboBoxTableCell.forTableColumn(listaPasti));
        colonnaPasto.setOnEditCommit( 
            new EventHandler<TableColumn.CellEditEvent<MisurazioneGlicemia,String>>(){
                @Override
                public void handle(TableColumn.CellEditEvent<MisurazioneGlicemia,String> p){
                        InvioLogAttivita.creaLog("Modifica cella Pasto riga " + (p.getTablePosition().getRow()+1) + ": sostituito " + p.getOldValue() + " con " + p.getNewValue() , dcxml);
                        ( (MisurazioneGlicemia) p.getTableView().getItems().get(p.getTablePosition().getRow())).setPasto(p.getNewValue());
                }
            }
        );
        colonnaPasto.setMinWidth(150);
        
        
        primaDopo= FXCollections.observableArrayList();
        List<String> valoriprimaDopo = new ArrayList<>();
        valoriprimaDopo.add("Prima");
        valoriprimaDopo.add("Dopo");
        primaDopo.addAll(valoriprimaDopo);
        
        TableColumn<MisurazioneGlicemia, String> colonnaPrimaDopo = new TableColumn("Prima/Dopo");//02
        colonnaPrimaDopo.setCellValueFactory(cellData -> cellData.getValue().getPrimaDopoProperty());
        colonnaPrimaDopo.setCellFactory(ComboBoxTableCell.forTableColumn(primaDopo));
        colonnaPrimaDopo.setOnEditCommit( 
            new EventHandler<TableColumn.CellEditEvent<MisurazioneGlicemia,String>>(){
                @Override
                public void handle(TableColumn.CellEditEvent<MisurazioneGlicemia,String> p){
                        InvioLogAttivita.creaLog("Modifica cella Prima/Dopo riga " + (p.getTablePosition().getRow()+1) + ": sostituito " + p.getOldValue() + " con " + p.getNewValue() , dcxml);
                        ( (MisurazioneGlicemia) p.getTableView().getItems().get(p.getTablePosition().getRow())).setPrimaDopoPasto(p.getNewValue());
                    
                }
            }
        );
        colonnaPrimaDopo.setMinWidth(150);
        
        TableColumn colonnaValoreGlicemia = new TableColumn("Valore");//03
        colonnaValoreGlicemia.setCellValueFactory(new PropertyValueFactory<>("valoreGlicemia"));
        colonnaValoreGlicemia.setCellFactory(TextFieldTableCell.forTableColumn(new IntegerStringConverter()));
        colonnaValoreGlicemia.setOnEditCommit( new EventHandler<TableColumn.CellEditEvent<MisurazioneGlicemia, Integer>>(){
            public void handle(TableColumn.CellEditEvent<MisurazioneGlicemia, Integer> p){
                        InvioLogAttivita.creaLog("Modifica cella Valore riga " + (p.getTablePosition().getRow()+1) + ": sostituito " + p.getOldValue() + " con " + p.getNewValue() , dcxml);
                        ( (MisurazioneGlicemia) p.getTableView().getItems().get(p.getTablePosition().getRow())).setValoreGlicemia(p.getNewValue());
                    
            }
        });
        colonnaValoreGlicemia.setMinWidth(150);
        
        listaMisurazioni=FXCollections.observableArrayList();
        setItems(listaMisurazioni);
        getColumns().addAll(colonnaData, colonnaPasto, colonnaPrimaDopo, colonnaValoreGlicemia);
        listaMisurazioni.addAll(new MisurazioneGlicemia(0,"","Inserisci Pasto","Inserisci Prima/Dopo",-1));
    }
    
    public void aggiornaContenutoTabella(List<MisurazioneGlicemia> nuoveMisurazioni){
        nuoveMisurazioni.add(new MisurazioneGlicemia(0,"","Inserisci Pasto","Inserisci Prima/Dopo",-1));//04
        listaMisurazioni.clear();
        listaMisurazioni.addAll(nuoveMisurazioni);  
    }
    
    
    public void setStileTabella(DatiConfigurazioneXML dcxml){
        setFixedCellSize(40);
        prefHeightProperty().set((dcxml.numeroRigheTabella +1) * getFixedCellSize());
        prefWidthProperty().set(600);
    }
    
    //05
    public String ottieniCellaMarcaTemporale(){return getSelectionModel().getSelectedItem().getMarcaTemporale();}
    public String ottieniCellaPasto(){return getSelectionModel().getSelectedItem().getPasto();}
    public String ottieniCellaPrimaDopo(){return getSelectionModel().getSelectedItem().getPrimaDopoPasto();}
    public int ottieniCellaValoreGlicemia(){return getSelectionModel().getSelectedItem().getValoreGlicemia();}
    
}


/* Commenti al codice
00) Classe che implementa e gestisce la tabella modificabile
    https://docs.oracle.com/javase/8/javafx/api/javafx/scene/control/TableView.html
01) Rende la tabella editabile
02) Celle della tabella che sono combo box. Prendono il primo valore grazie ad una funzione
    nella classe bean che restituisce il valore richiesto dell'oggetto. Alla modifica di un valore dalla combo box,
    anche il relativo valore nell'oggetto bean deve cambiare.
    https://docs.oracle.com/javase/8/javafx/api/javafx/scene/control/cell/ComboBoxTableCell.html
03) Cella della tabella che e' un textfield grazie a TextFieldTableCell.
    La modifica del valore e' legata ad un evento che modifica lo stesso valore anche nel relativo oggetto bean.
    https://docs.oracle.com/javase/8/javafx/api/javafx/scene/control/cell/TextFieldTableCell.html
04) Riga che permettera' l'inserimento di nuovi pasti.
05) Sono un insieme di metodi che permettono di ottenere il valore delle celle modificabili
    della riga selezionata. Utilizzata per memorizzare nella cache le modifiche non salvata alla
    riga selezionata
*/