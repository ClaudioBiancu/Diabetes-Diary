import java.io.*;
import java.nio.file.*;
import javafx.application.*;
import javafx.scene.*;
import javafx.stage.*;


public class DiarioDiabete extends Application {
    private final static String FILE_CONFIGURAZIONE_XML="configurazione.xml";
    private final static String SCHEMA_CONFIGURAZIONE_XML="configurazione.xsd";
    private final static String FILE_CACHE="cache.bin";
    
    private InterfacciaDiarioDiabete interfacciaDiarioDiabete;
    
    @Override
    public void start(Stage stage){
        DatiConfigurazioneXML dcxml;
        if(ValidatoreXML.valida(FILE_CONFIGURAZIONE_XML, SCHEMA_CONFIGURAZIONE_XML, true)){//00
            String xml= null;
            try{
                xml = new String(Files.readAllBytes(Paths.get(FILE_CONFIGURAZIONE_XML)));
            }catch(IOException e){ 
                System.err.println(e.getMessage());
            }
            dcxml= new DatiConfigurazioneXML(xml);
        }
        else{
            dcxml= new DatiConfigurazioneXML();//01
        }
        InvioLogAttivita.creaLog("Avvio applicazione", dcxml);//02
        interfacciaDiarioDiabete = new InterfacciaDiarioDiabete(dcxml, FILE_CACHE);//03
        
        Scene scene = new Scene(interfacciaDiarioDiabete.getInterfacciaDiabete());
        
        stage.setOnCloseRequest((WindowEvent we) -> {//04
            SalvaCaricaDatiCache.salvaDatiCache(interfacciaDiarioDiabete, dcxml, FILE_CACHE);
            InvioLogAttivita.creaLog("Chiusura applicazione", dcxml);
        });
        
        
        stage.setTitle("MySweetStory");
        stage.setScene(scene);
        stage.setHeight(dcxml.altezzaFinestra);
        stage.setWidth(dcxml.larghezzaFinestra);
        stage.show();
        
    }
}


/* Commenti al Codice
00)Richiamo il validatore XML , lo conmfronto con lo schema, il terzo paramentro indica che passo file.
01)Se il validatore da risultato negativo, inizializzo le variabili di configurazione con parametri predefiniti.
02)Scrivo sul Log che l'applicazione è stata avviata.
03)Istanzio l'interfaccia e passo i file di cache e i parametri di configurazione.
04) Alla chisura dell'applicativo, salvo i dati in cache per poterli avere alla riapertura.
*/