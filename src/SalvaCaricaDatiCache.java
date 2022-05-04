import java.io.*;

public class SalvaCaricaDatiCache {//00
    public static void salvaDatiCache(InterfacciaDiarioDiabete interfacciaDiarioDiabete, DatiConfigurazioneXML dcxml, String fileCache){
        try(
            ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(fileCache))
        ){
            oos.writeObject(new DatiCacheInterfaccia(interfacciaDiarioDiabete));
        }catch(IOException e){System.err.println(e.getMessage());}
    }
    
    public static DatiCacheInterfaccia caricaDatiCache(String fileCache){
        try(
            ObjectInputStream ois = new ObjectInputStream(new FileInputStream(fileCache))
        ){
            return (DatiCacheInterfaccia) ois.readObject();
        }catch(IOException | ClassNotFoundException e){System.err.println(e.getMessage());}
        return null;
    }
}

/* NOTE
00) Classe che si occupa di salvare su file e caricare da file la cache
*/