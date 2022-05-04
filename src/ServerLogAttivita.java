import java.io.*;
import java.net.*;
import java.nio.file.*;

public class ServerLogAttivita {//00
    private final static int PORTA = 6979;
    private final static String FILE_LOG = "logEventi.xml",
                                SCHEMA_LOG = "logEventi.xsd";
    
    public static void main(String[] args){
        System.out.println("Avviato il server log");
        
        try(ServerSocket serversock = new ServerSocket(PORTA))
            {
                while(true){
                    try(
                            Socket socket = serversock.accept();
                            ObjectInputStream ois = new ObjectInputStream(socket.getInputStream());
                        ){
                            String log =(String) ois.readObject();
                            System.out.println(log);
                            if(ValidatoreXML.valida(log, SCHEMA_LOG, false)){//01
                                try{
                                        Files.write(Paths.get(FILE_LOG), log.getBytes(), StandardOpenOption.APPEND);
                                }catch( IOException e) { System.err.println(e.getMessage());}
                            }
                        }
                }
            }catch( IOException | ClassNotFoundException e){ e.printStackTrace();}
    }
}

/* NOTE
00) Server: riceve log e lo memorizza su file 
01) Se è tutto ok, scrive su file
*/