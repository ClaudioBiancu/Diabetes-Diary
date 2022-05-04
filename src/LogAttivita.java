import com.thoughtworks.xstream.*;
import java.io.*;
import java.text.*;
import java.util.*;


public class LogAttivita implements Serializable {//00
    private final String applicazione;
    private final String qualeAttivita;
    private final String ipClient;
    private final String marcaTemporale;
    
    public LogAttivita(String tipoEvento, String indirizzoIPClient){
        applicazione = "DiarioDiabete";
        this.qualeAttivita = tipoEvento;
        this.ipClient = indirizzoIPClient;
        marcaTemporale = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
    }
    
    public String toString(){//01
        XStream xs = new XStream();
        xs.useAttributeFor(LogAttivita.class, "applicazione");
        return xs.toXML(this);
    } 
}

/* Commenti al Codice
00) Classe serializzabile che implementa oggetti per il log degli eventi
01) Trasforma in stringa l'oggetto LogEventi in formato XML. Le tre variabili
    sono state utilizzate in XML come elementi, poiche' sono valori che possono 
    cambiare spesso, tranne il nome dell'applicazione che rimane costante.
*/
