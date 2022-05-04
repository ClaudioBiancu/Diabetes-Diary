import java.io.*;
import java.net.*;

public class InvioLogAttivita {//00
    private static void inviaLog(LogAttivita log, String ipLog, int portaLog){//01
        try(
                Socket socket = new Socket(ipLog, portaLog);
                ObjectOutputStream oos = new ObjectOutputStream(socket.getOutputStream());
        ){
            oos.writeObject(log.toString());
        }catch(IOException e){ e.printStackTrace();}
    }
    
    public static void creaLog(String tipoLog, DatiConfigurazioneXML dcxml){//02
        LogAttivita log = new LogAttivita(tipoLog, dcxml.ipClient);
        inviaLog(log, dcxml.ipLog, dcxml.portaLog);
    }
}

/* Commenti al Codice
00) Classe che crea ed invia il log al server di log ogni volta che
    si verifica un evento
01) Invio del log
02) Crea un nuovo log e chiama il metodo che lo invia
*/