import java.time.*;
import java.util.*;
import javafx.event.*;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.text.*;

public class InterfacciaDiarioDiabete {//00
    
    private final AnchorPane contenitore;
    private PeriodoVisualizzazione periodoVisualizzazione;
    private VBox sezionePeriodoVisualizzazione;
    private ResocontoMisurazioni resocontoMisurazioni;
    private VBox sezioneResocontoMisurazioni;
    private AndamentoGlicemia andamentoGlicemia;
    private VBox sezioneAndamentoGlicemia;
    private Label labelEmail;
    private TextField textFieldEmail;
    private HBox sezioneEmail;
    private EventHandler<ActionEvent> cambioEmailEvent;
    private VBox sezioneTitoloUtente;
    private Label labelNomeApplicazione;
    
    public InterfacciaDiarioDiabete(DatiConfigurazioneXML dcxml, String fileCache){
        contenitore= new AnchorPane();    
        labelNomeApplicazione=new Label("MySweetStory");
        labelEmail= new Label("Inserisci la tua Email");
        textFieldEmail= new TextField();
        cambiaUtenteEvento(dcxml); 
        
        periodoVisualizzazione= new PeriodoVisualizzazione(dcxml, this);
        sezionePeriodoVisualizzazione= periodoVisualizzazione.getPeriodoVisualizzazione();
        
        resocontoMisurazioni= new ResocontoMisurazioni(dcxml, this);
        sezioneResocontoMisurazioni= resocontoMisurazioni.ottieniResocontoMisurazioni();
        
        andamentoGlicemia= new AndamentoGlicemia(dcxml, this);
        sezioneAndamentoGlicemia= andamentoGlicemia.ottieniAndamentoGlicemia();
        
        sezioneEmail= new HBox(20);
        sezioneTitoloUtente= new VBox(15);
        impostaTitoloUtente();
        
        contenitore.getChildren().addAll(sezioneTitoloUtente, sezioneResocontoMisurazioni, sezionePeriodoVisualizzazione, sezioneAndamentoGlicemia);
        impostaStileInterfaccia(dcxml);
        inizializzaInterfaccia(fileCache, dcxml);
    }

    
    
    private void impostaTitoloUtente(){//01
        sezioneEmail.getChildren().addAll(labelEmail, textFieldEmail);
        sezioneTitoloUtente.getChildren().addAll(labelNomeApplicazione, sezioneEmail);
    }
    
    private void cambiaUtenteEvento(DatiConfigurazioneXML dcxml){//02
        cambioEmailEvent= new EventHandler<ActionEvent>(){
            public void handle(ActionEvent e){
                periodoVisualizzazione.setDataFine(LocalDate.now());
                periodoVisualizzazione.setDataInizio(LocalDate.now().minusDays(1));
                List<MisurazioneGlicemia> listaNuoveMisurazioni = AzioniSuDatabaseDiarioDiabete.riceviMisurazione(periodoVisualizzazione.getDataInizio(), periodoVisualizzazione.getDataFine(), getEmail(), dcxml);
                resocontoMisurazioni.ottieniTabellaResocontoMisurazioni().aggiornaContenutoTabella(listaNuoveMisurazioni);
                andamentoGlicemia.ottieniGraficoAndamento().aggiornaGrafico(periodoVisualizzazione.getDataInizio(), periodoVisualizzazione.getDataFine(), getEmail(), dcxml);
                andamentoGlicemia.ottieniGlicemiaMedia().setValoreGlicemia(periodoVisualizzazione.getDataInizio(), periodoVisualizzazione.getDataFine(),getEmail(), dcxml);
            }
        };
        textFieldEmail.setOnAction(cambioEmailEvent);
    }
    
     private void inizializzaInterfaccia(String fileCache, DatiConfigurazioneXML pcxml){//03
        DatiCacheInterfaccia cache = SalvaCaricaDatiCache.caricaDatiCache(fileCache);
        if(cache != null){//04
            textFieldEmail.setText(cache.getCacheUtente());
            periodoVisualizzazione.setDataInizio(cache.getCacheDataInizio());
            periodoVisualizzazione.setDataFine(cache.getCacheDataFine());
            resocontoMisurazioni.aggiornaInterfaccia(this, pcxml);
            resocontoMisurazioni.ripristinaCacheTabella(cache.getIndexRiga(), cache.getCachePasto(), cache.getCachePrimaDopoPasto(), cache.getCacheValoreGlicemia());
        } else {
            resocontoMisurazioni.aggiornaInterfaccia(this, pcxml);
        }
    }
    
    private void impostaStileInterfaccia(DatiConfigurazioneXML dcxml){//05
        contenitore.setStyle("-fx-background-color: " + dcxml.coloreBackground);
        labelNomeApplicazione.setStyle("-fx-font-family: Dancing Script; -fx-font-size: 30;");
        textFieldEmail.setMinWidth(250);
        sezioneTitoloUtente.setAlignment(Pos.CENTER);
        labelEmail.setFont(Font.font(dcxml.font, dcxml.dimensioneFont));
        resocontoMisurazioni.setStileResocontoMisurazione(dcxml);
        andamentoGlicemia.impostaStileAndamento(dcxml);
        periodoVisualizzazione.setStilePeriodoVisualizzazione(dcxml);
        AnchorPane.setTopAnchor(sezioneTitoloUtente, 10.0);
        AnchorPane.setLeftAnchor(sezioneTitoloUtente, 350.0);
        AnchorPane.setTopAnchor(sezioneResocontoMisurazioni, 120.0);
        AnchorPane.setLeftAnchor(sezioneResocontoMisurazioni, 170.0);
        AnchorPane.setLeftAnchor(sezioneAndamentoGlicemia, 50.0);
        AnchorPane.setTopAnchor(sezioneAndamentoGlicemia, 500.0);
        AnchorPane.setTopAnchor(sezionePeriodoVisualizzazione, 120.0);
        AnchorPane.setRightAnchor(sezionePeriodoVisualizzazione, 150.0);
    }

    public String getEmail(){
        return textFieldEmail.getText();
    }
    
    public ResocontoMisurazioni ottieniResocontoMisurazioni(){
        return resocontoMisurazioni;
    }
    
    public AndamentoGlicemia ottieniAndamentoGlicemia(){
        return andamentoGlicemia;
    }
    
    public PeriodoVisualizzazione ottieniPeriodoVisualizzazione(){
        return periodoVisualizzazione;
    }
    
    
    public AnchorPane getInterfacciaDiabete(){
        return contenitore;
    }
    
    
}


/*Commenti al Codice
00) Classe che implementa l'interfaccia grafica dell'applicazione unendo tutti gli elementi
    forniti dalle altre classi.
01) Metodo che realizza l'intestazione dell'applicazione che comprende il titolo e la sezione utente.
02) Metodo che imposta l'evento legato alla modifica del nome dell'utente. Dopo aver premuto invio
    l'applicazione aggiornera' i dati degli elementi nell'interfaccia.
03) Metodo che inizializza i contenuti dell'interfaccia con i dati della cache se disponibili, altrimenti 
    con i dati estratti dal database in base alla data odierna.
04) Nel caso in cui la cache fosse vuota, inizializzo con altri valori
05) Metodo che si occupa della gestione dello stile grafico dei vari elementi dell'interfaccia
    basandosi sui parametri di configurazione.
*/