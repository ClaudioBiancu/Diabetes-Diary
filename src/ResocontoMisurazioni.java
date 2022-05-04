
import java.util.*;
import javafx.event.*;
import javafx.geometry.*;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.text.*;




public class ResocontoMisurazioni {//00
    private final VBox sezioneResocontoMisurazioni;
    private TabellaResocontoMisurazioni tabellaResocontoMisurazioni;
    private final Button pulsanteElimina;
    private final Button pulsanteAggiorna;
    private final Button pulsanteSalva;
    private final HBox sezionePulsanti;
    private final EventHandler<ActionEvent> aggiornaPremuto;
    private final EventHandler<ActionEvent> eliminaPremuto;
    private final EventHandler<ActionEvent> salvaPremuto;
    private final Label labelResocontoMisurazioni;
    
    
    public ResocontoMisurazioni(DatiConfigurazioneXML dcxml, InterfacciaDiarioDiabete interfacciaDiarioDiabete){
        labelResocontoMisurazioni= new Label ("Resoconto Misurazioni");
        
        tabellaResocontoMisurazioni= new TabellaResocontoMisurazioni(dcxml);
        
        pulsanteElimina = new Button("Elimina");
        eliminaPremuto = (ActionEvent ae) -> {//01
            int index = tabellaResocontoMisurazioni.getSelectionModel().getSelectedIndex();
            if(tabellaResocontoMisurazioni.getItems().size() != 0 || index < 0 || tabellaResocontoMisurazioni.getItems().size() < index){
                int idEliminare = tabellaResocontoMisurazioni.getSelectionModel().getSelectedItem().getId();
                if(idEliminare!=-1){//04
                    AzioniSuDatabaseDiarioDiabete.eliminaMisurazione(idEliminare, dcxml);
                    aggiornaInterfaccia(interfacciaDiarioDiabete, dcxml);
                    InvioLogAttivita.creaLog("Pressione pulsante ELIMINA per riga " + (index+1), dcxml);
                }
            }
        };
        pulsanteElimina.setOnAction(eliminaPremuto);
        
        pulsanteAggiorna = new Button("Aggiorna");
        aggiornaPremuto = (ActionEvent ae) -> {//02
            int index = tabellaResocontoMisurazioni.getSelectionModel().getSelectedIndex();
            if(tabellaResocontoMisurazioni.getItems().size() != 0 || index < 0 || tabellaResocontoMisurazioni.getItems().size() < index){
                MisurazioneGlicemia nuovaMisurazione = tabellaResocontoMisurazioni.getSelectionModel().getSelectedItem().getMisurazioneGlicemia();
                if(nuovaMisurazione.getMarcaTemporale().equals("")&& !nuovaMisurazione.getPasto().equals("Inserisci Pasto")&& !interfacciaDiarioDiabete.getEmail().equals("")){
                    AzioniSuDatabaseDiarioDiabete.inserisciMisurazione(interfacciaDiarioDiabete.getEmail(), nuovaMisurazione, dcxml );
                    aggiornaInterfaccia(interfacciaDiarioDiabete,dcxml);
                    InvioLogAttivita.creaLog("Pressione bottone AGGIORNA", dcxml);
                }
            }
        };
        pulsanteAggiorna.setOnAction(aggiornaPremuto);
        
         pulsanteSalva = new Button("Salva");
        salvaPremuto = (ActionEvent ae) -> {//02
            int index = tabellaResocontoMisurazioni.getSelectionModel().getSelectedIndex();
            if(tabellaResocontoMisurazioni.getItems().size() != 0 || index < 0 || tabellaResocontoMisurazioni.getItems().size() < index){
                MisurazioneGlicemia nuovaMisurazione = tabellaResocontoMisurazioni.getSelectionModel().getSelectedItem().getMisurazioneGlicemia();
                AzioniSuDatabaseDiarioDiabete.aggiornaMisurazione(nuovaMisurazione,  interfacciaDiarioDiabete.getEmail(), dcxml);
                aggiornaInterfaccia(interfacciaDiarioDiabete,dcxml);
                InvioLogAttivita.creaLog("Pressione bottone SALVA per riga " + (index+1), dcxml);
            }
        };
        pulsanteSalva.setOnAction(salvaPremuto);
        
        
        sezionePulsanti = new HBox(15);
        sezionePulsanti.getChildren().addAll(pulsanteAggiorna, pulsanteElimina, pulsanteSalva);
        sezioneResocontoMisurazioni= new VBox(10);
        sezioneResocontoMisurazioni.getChildren().addAll(labelResocontoMisurazioni,  tabellaResocontoMisurazioni, sezionePulsanti);
        
    }
    
    public TabellaResocontoMisurazioni ottieniTabellaResocontoMisurazioni(){
        return tabellaResocontoMisurazioni;
    }
    public VBox ottieniResocontoMisurazioni(){
        return sezioneResocontoMisurazioni;
    }
    
    public void aggiornaInterfaccia(InterfacciaDiarioDiabete interfaccia, DatiConfigurazioneXML dcxml){//03
        if(!interfaccia.getEmail().isEmpty()){
        List<MisurazioneGlicemia> nuovaListaMisurazioni = AzioniSuDatabaseDiarioDiabete.riceviMisurazione(interfaccia.ottieniPeriodoVisualizzazione().getDataInizio(),interfaccia.ottieniPeriodoVisualizzazione().getDataFine(), interfaccia.getEmail(), dcxml);
        tabellaResocontoMisurazioni.aggiornaContenutoTabella(nuovaListaMisurazioni);
        interfaccia.ottieniAndamentoGlicemia().ottieniGraficoAndamento().aggiornaGrafico(interfaccia.ottieniPeriodoVisualizzazione().getDataInizio(),interfaccia.ottieniPeriodoVisualizzazione().getDataFine(), interfaccia.getEmail(), dcxml);
        interfaccia.ottieniAndamentoGlicemia().ottieniGlicemiaMedia().setValoreGlicemia(interfaccia.ottieniPeriodoVisualizzazione().getDataInizio(),interfaccia.ottieniPeriodoVisualizzazione().getDataFine(), interfaccia.getEmail(), dcxml);
        }
    }
    
    public void ripristinaCacheTabella(int indexRigaSelezionata, String pasto, String primadopo, int valore){//04
        if(indexRigaSelezionata != -1 ){
            tabellaResocontoMisurazioni.requestFocus();
            tabellaResocontoMisurazioni.getSelectionModel().select(indexRigaSelezionata);
            MisurazioneGlicemia nuovoMisurazione = tabellaResocontoMisurazioni.getSelectionModel().getSelectedItem().getMisurazioneGlicemia();
            nuovoMisurazione.setPasto(pasto);
            nuovoMisurazione.setPrimaDopoPasto(primadopo);
            nuovoMisurazione.setValoreGlicemia(valore);
        }
    }
    
    public void setStileResocontoMisurazione(DatiConfigurazioneXML dcxml){
        sezionePulsanti.setAlignment(Pos.CENTER);
        labelResocontoMisurazioni.setAlignment(Pos.CENTER);
        sezioneResocontoMisurazioni.setAlignment(Pos.CENTER);
        tabellaResocontoMisurazioni.setStileTabella(dcxml);
        labelResocontoMisurazioni.setFont(Font.font(dcxml.font, 22));
        pulsanteElimina.setStyle("-fx-background-color: #9DADFA; -fx-text-fill: white;");
        pulsanteElimina.setMinWidth(100);
        pulsanteAggiorna.setMinWidth(100);
        pulsanteSalva.setMinWidth(100);
        pulsanteAggiorna.setStyle("-fx-background-color: #9DADFA; -fx-text-fill: white;");
        pulsanteSalva.setStyle("-fx-background-color: #9DADFA; -fx-text-fill: white;");
        
    }
}


/* Commenti al Codice
00) Classe che realizza la grafica dell'intera sezione ResocontoMisurazioni, inserendo tabella e bottoni
01) Al click del bottone elimina, viene eliminata la riga selezionata dalla tabella del database
02) Al click del bottone aggiungi, l'ultima riga della tabella, identificata da marcaTemporale=" ", viene memorizzata
    nel database e vengono aggiornati i dati della tabella e del grafico
03) Metodo che aggiorna tutti i contenuti presenti nella sezione resocontoMisurazioni, ovvero tabella
    e anche il relativo grafico
04) Metodo per ripristinare i valori quali data e modifiche alla riga selezionata della tabella nel caso
    siano presenti all'interno del file di cache
*/