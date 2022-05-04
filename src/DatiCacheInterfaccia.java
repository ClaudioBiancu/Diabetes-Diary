import java.io.Serializable;
import java.time.LocalDate;

public class DatiCacheInterfaccia implements Serializable{//00
    private final LocalDate salvataggioDataInizio;
    private final LocalDate salvataggioDataFine;
    private final String salvataggioPasto;
    private final String salvataggioUtente;
    private final String salvataggioPrimaDopoPasto;
    private final int salvataggioValoreGlicemia;
    private final int qualeRiga;
    
    public DatiCacheInterfaccia(InterfacciaDiarioDiabete interfacciaDiarioDiabete){
        salvataggioUtente= interfacciaDiarioDiabete.getEmail();
        salvataggioDataInizio= interfacciaDiarioDiabete.ottieniPeriodoVisualizzazione().getDataInizio();
        salvataggioDataFine= interfacciaDiarioDiabete.ottieniPeriodoVisualizzazione().getDataFine();
       if(interfacciaDiarioDiabete.ottieniResocontoMisurazioni().ottieniTabellaResocontoMisurazioni().getSelectionModel().isEmpty()){//01
            qualeRiga = -1;//02
            salvataggioPasto = "Inserisci Pasto";
            salvataggioPrimaDopoPasto = "Inserisci Prima/Dopo";
            salvataggioValoreGlicemia = 0;
        } else {
            qualeRiga = interfacciaDiarioDiabete.ottieniResocontoMisurazioni().ottieniTabellaResocontoMisurazioni().getSelectionModel().getSelectedIndex();
            salvataggioPasto  = interfacciaDiarioDiabete.ottieniResocontoMisurazioni().ottieniTabellaResocontoMisurazioni().ottieniCellaPasto();
            salvataggioPrimaDopoPasto = interfacciaDiarioDiabete.ottieniResocontoMisurazioni().ottieniTabellaResocontoMisurazioni().ottieniCellaPrimaDopo();
            salvataggioValoreGlicemia = interfacciaDiarioDiabete.ottieniResocontoMisurazioni().ottieniTabellaResocontoMisurazioni().ottieniCellaValoreGlicemia();
        }
    }
    
    public LocalDate getCacheDataInizio(){
        return salvataggioDataInizio;
    }
    
    public LocalDate getCacheDataFine(){
        return salvataggioDataFine;
    }
    
    public String getCachePasto(){
        return salvataggioPasto;
    }
    
    public String getCacheUtente(){
        return salvataggioUtente;
    }
    
    public String getCachePrimaDopoPasto(){
        return salvataggioPrimaDopoPasto;
    }
    
    public int getCacheValoreGlicemia(){
        return salvataggioValoreGlicemia;
    }
    
    public int getIndexRiga(){
        return qualeRiga;
    }
}


/* Commenti al Codice
00) Classe serializzabile che istanzia oggetti che fungono da cache, inizializzati
    con i valori presenti negli input dell'interfaccia
01) Se nessuna riga della tabella viene selezionata, inizializzo alcune variabili
    con valori nulli, e rigaTabellaSelezionata a -1
02) La riga della tabella selelzionata viene inizializzata a -1 perche' nel momento
    in cui vado a ripristinare i valori che erano stati modificati sulla tabella
    e non salvati, effettuo un controllo sull'indice, se questo e' -1 nessuna riga era
    selezionata e nessun valore deve essere riportato
*/