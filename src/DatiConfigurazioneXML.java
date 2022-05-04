import com.thoughtworks.xstream.*;

public class DatiConfigurazioneXML {
    //00
    private static final double LARGEHZZA_FINESTRA_PREDEFINITA=1250;
    private static final double ALTEZZA_FINESTRA_PREDEFINITA=850;
    private static final int NUMERO_RIGHE_TABELLA_PREDEFINITO=5;
    private static final int PORTA_LOG_PREDEFINITA=6979;
    private static final int PORTA_DATABASE_PREDEFINITA=3306;
    private static final int DIMENSIONE_FONT_PREDEFINITA=12;
    private static final String IP_LOG_PREDEFINITO="127.0.0.1";
    private static final String IP_DATABASE_PREDEFINITO="localhost";
    private static final String NOMEUTENTE_DATABASE_PREDEFINITO="root";
    private static final String PASSWORD_DATABASE_PREDEFINITA="";
    private static final String IP_CLIENT_PREDEFINITO="127.0.0.1";
    private static final String FONT_PREDEFINITO="Arial";
    private static final String COLORE_BACKGROUND_PREDEFINITO="white";
    
    
    public final double larghezzaFinestra;
    public final double altezzaFinestra;
    public final int numeroRigheTabella;
    public final int portaLog;
    public final int portaDatabase;
    public final int dimensioneFont;
    public final String ipLog;
    public final String ipDataBase;
    public final String nomeUtenteDatabase;
    public final String passwordDatabase;
    public final String ipClient;
    public final String font;
    public final String coloreBackground;
    
    public DatiConfigurazioneXML(){
        coloreBackground= COLORE_BACKGROUND_PREDEFINITO;
        font=FONT_PREDEFINITO;
        ipClient=IP_CLIENT_PREDEFINITO;
        passwordDatabase=PASSWORD_DATABASE_PREDEFINITA;
        nomeUtenteDatabase=NOMEUTENTE_DATABASE_PREDEFINITO;
        ipDataBase=IP_DATABASE_PREDEFINITO;
        ipLog=IP_LOG_PREDEFINITO;
        dimensioneFont= DIMENSIONE_FONT_PREDEFINITA;
        portaDatabase=PORTA_DATABASE_PREDEFINITA;
        portaLog=PORTA_LOG_PREDEFINITA;
        numeroRigheTabella=NUMERO_RIGHE_TABELLA_PREDEFINITO;
        altezzaFinestra=ALTEZZA_FINESTRA_PREDEFINITA;
        larghezzaFinestra=LARGEHZZA_FINESTRA_PREDEFINITA;
    }
    
    public DatiConfigurazioneXML(String datiXML){//02
        DatiConfigurazioneXML dcxml= (DatiConfigurazioneXML)nuovoXStream().fromXML(datiXML);
        
        coloreBackground= dcxml.coloreBackground;
        font=dcxml.font;
        ipClient=dcxml.ipClient;
        passwordDatabase=dcxml.passwordDatabase;
        nomeUtenteDatabase=dcxml.nomeUtenteDatabase;
        ipDataBase=dcxml.ipDataBase;
        ipLog=dcxml.ipLog;
        dimensioneFont= dcxml.dimensioneFont;
        portaDatabase=dcxml.portaDatabase;
        portaLog=dcxml.portaLog;
        numeroRigheTabella=dcxml.numeroRigheTabella;
        altezzaFinestra=dcxml.altezzaFinestra;
        larghezzaFinestra=dcxml.larghezzaFinestra;
    }
    
    public final XStream nuovoXStream(){
       XStream xs = new XStream();
        xs.useAttributeFor(DatiConfigurazioneXML.class, "coloreBackground");
        xs.useAttributeFor(DatiConfigurazioneXML.class, "numeroRigheTabella");
        xs.useAttributeFor(DatiConfigurazioneXML.class, "font");
        xs.useAttributeFor(DatiConfigurazioneXML.class, "ipLog");
        xs.useAttributeFor(DatiConfigurazioneXML.class, "portaLog");
        xs.useAttributeFor(DatiConfigurazioneXML.class, "ipDataBase");
        xs.useAttributeFor(DatiConfigurazioneXML.class, "portaDatabase");
        return xs; 
    }
    
    public String toString(){//03
        return nuovoXStream().toXML(this);
    }
}


/* COMMENTI AL CODICE
00)Se ValidaXML restituisce false(validazione non andata a buon fine) 
    vengono inizializzate le variabili di default;
02)Inizializza le variabili da stringa XML
03)Restituisce oggetto in stringa XML
*/