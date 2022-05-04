
import java.time.*;
import java.util.*;
import javafx.event.*;
import javafx.geometry.*;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.text.*;

public class PeriodoVisualizzazione {//00 
    private final Label labelTitoloPeriodo;
    private final Label labelDataInizio;
    private final Label labelDataFine;
    private  DatePicker dataInizio;
    private  DatePicker dataFine;
    private final EventHandler<ActionEvent> cambioData;
    private final VBox contenitorePeriodoVisualizzazione;
    
    
    public PeriodoVisualizzazione(DatiConfigurazioneXML dcxml,InterfacciaDiarioDiabete interfaccia){
        labelTitoloPeriodo= new Label("Seleziona il periodo");
        labelDataInizio= new Label("Dal");
        labelDataFine=new Label("Al");
        dataFine= new DatePicker(LocalDate.now());//01
        dataInizio=new DatePicker(LocalDate.now().minusDays(1));//02
        cambioData= new EventHandler<ActionEvent>(){
            public void handle(ActionEvent e){//03
                LocalDate nuovaDataInizio = dataInizio.getValue();
                LocalDate nuovaDataFine = dataFine.getValue();
                if(nuovaDataInizio.isBefore(nuovaDataFine)|| nuovaDataInizio.isEqual(nuovaDataFine)){//04
                    List<MisurazioneGlicemia> listaNuoveMisurazioni = AzioniSuDatabaseDiarioDiabete.riceviMisurazione(nuovaDataInizio, nuovaDataFine, interfaccia.getEmail(), dcxml);
                    interfaccia.ottieniResocontoMisurazioni().ottieniTabellaResocontoMisurazioni().aggiornaContenutoTabella(listaNuoveMisurazioni);
                    interfaccia.ottieniAndamentoGlicemia().ottieniGraficoAndamento().aggiornaGrafico(nuovaDataInizio, nuovaDataFine, interfaccia.getEmail(), dcxml);
                    interfaccia.ottieniAndamentoGlicemia().ottieniGlicemiaMedia().setValoreGlicemia(nuovaDataInizio, nuovaDataFine, interfaccia.getEmail(),dcxml);
                }
                InvioLogAttivita.creaLog("Selezionato periodo: da " + dataInizio.getValue().toString() +" a " + dataFine.getValue().toString()  , dcxml);
            }
        };
            dataInizio.setOnAction(cambioData); 
            dataFine.setOnAction(cambioData); 
        contenitorePeriodoVisualizzazione= new VBox(10);
        contenitorePeriodoVisualizzazione.getChildren().addAll(labelTitoloPeriodo, labelDataInizio, dataInizio, labelDataFine, dataFine);
    }

    
    //05
    public VBox getPeriodoVisualizzazione(){
        return contenitorePeriodoVisualizzazione;
    }
    
    public LocalDate getDataInizio(){
        return dataInizio.getValue();
    }
    
    public LocalDate getDataFine(){
        return dataFine.getValue();
    }
    
    public void setDataInizio(LocalDate datainizio){
        dataInizio.setValue(datainizio);
    }
    
    public void setDataFine(LocalDate datafine){
        dataFine.setValue(datafine);
    }
    
    public void setStilePeriodoVisualizzazione(DatiConfigurazioneXML dcxml){
        labelTitoloPeriodo.setAlignment(Pos.CENTER);
        labelTitoloPeriodo.setFont(Font.font(dcxml.font, 22));
    }
}


/*Commenti al Codice
00)La classe permette la selezione del periodo di visualizazione. Il periodo viene utilizzato dalla tabella, dal grafico e dalla glicemiamedia
01)Per Default imposto la dataFine al giorno attuale(oggi)
02) Per Default imposto la dataInizio al giorno precedente l'attuale(ieri)
03) Gestione dell'evento del cambio di data di uno dei due date-Pickers
04)Controllo sempre prima di aggiornare l'interfaccia che il periodo selezionato sia corretto. Data di inzio < Data di fine
05)Ho bisogno di settare la data in presenza di dati di cache o di cambio di utente. In questo ultimo caso la data viene ripristinata.
*/