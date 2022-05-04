import java.util.*;
import java.sql.*;
import java.time.*;
import javafx.collections.*;
import javafx.scene.chart.*;

public class AzioniSuDatabaseDiarioDiabete {//00
    public static void inserisciMisurazione(String utente, MisurazioneGlicemia misurazione, DatiConfigurazioneXML dcxml){
         
         try(
                Connection connessione = DriverManager.getConnection(("jdbc:mysql://" + dcxml.ipDataBase + ":" + dcxml.portaDatabase + "/diariodiabete"), dcxml.nomeUtenteDatabase, dcxml.passwordDatabase);
                PreparedStatement query = connessione.prepareStatement("INSERT INTO misurazione (email, marcaTemporale, valoreGlicemia, primadopo, pasto )"
                                                                                + "VALUES(?, ?, ?, ?, ?);");
        ){
            query.setString(1,utente);
            query.setString(2,LocalDate.now().toString());
            query.setInt(3,misurazione.getValoreGlicemia());
            query.setString(4,misurazione.getPrimaDopoPasto());
            query.setString(5,misurazione.getPasto());
            query.executeUpdate();
        }catch( SQLException e){
            System.err.println(e.getMessage());
        }  
    }  
    
    public static List<MisurazioneGlicemia>  riceviMisurazione(LocalDate DataInizio, LocalDate DataFine, String utente, DatiConfigurazioneXML dcxml){
        List<MisurazioneGlicemia> listaMisurazioni = new ArrayList<>();
         try(
                 Connection connessione = DriverManager.getConnection(("jdbc:mysql://" + dcxml.ipDataBase + ":" + dcxml.portaDatabase + "/diariodiabete"), dcxml.nomeUtenteDatabase, dcxml.passwordDatabase);
                PreparedStatement query = connessione.prepareStatement("SELECT * FROM misurazione WHERE email = ? "
                                                                        + "AND (STRCMP(marcaTemporale, ? )= 1 OR STRCMP(marcaTemporale,?)=0)"
                                                                        + " AND (STRCMP(marcaTemporale, ? )= -1 OR STRCMP(marcaTemporale,?)=0)"
                                                                        + "ORDER BY marcaTemporale, FIELD(pasto,'Colazione','Pranzo','Cena'), FIELD(primadopo,'Prima','Dopo')  ");//01
        ){
            query.setString(1, utente);
            query.setString(2, DataInizio.toString());
            query.setString(3, DataInizio.toString());
            query.setString(4, DataFine.toString());
            query.setString(5, DataFine.toString());
            ResultSet rs = query.executeQuery();
            while(rs.next()){
                listaMisurazioni.add(new MisurazioneGlicemia(rs.getInt("valoreGlicemia"), rs.getString("marcaTemporale"), rs.getString("pasto"), rs.getString("primadopo"), rs.getInt("id") ));
            }
        }catch( SQLException e){
            System.err.println(e.getMessage());
        }
        return listaMisurazioni;     
    }
         
    public static void eliminaMisurazione(int id, DatiConfigurazioneXML dcxml){
        try(
                Connection connessione = DriverManager.getConnection(("jdbc:mysql://" + dcxml.ipDataBase + ":" + dcxml.portaDatabase + "/diariodiabete"), dcxml.nomeUtenteDatabase, dcxml.passwordDatabase);
                PreparedStatement query = connessione.prepareStatement("DELETE FROM misurazione WHERE id = ? ");
        ){
            query.setInt(1, id);
            query.executeUpdate(); 
        }catch( SQLException e){System.err.println(e.getMessage());}
    }   
    
     public static void aggiornaMisurazione(MisurazioneGlicemia nuovaMisurazione, String utente, DatiConfigurazioneXML dcxml){
        try(
                 Connection connessione = DriverManager.getConnection(("jdbc:mysql://" + dcxml.ipDataBase + ":" + dcxml.portaDatabase + "/diariodiabete"), dcxml.nomeUtenteDatabase, dcxml.passwordDatabase);
                PreparedStatement query = connessione.prepareStatement("UPDATE misurazione " 
                                                                                + "SET pasto = ?, primadopo = ?, valoreGlicemia = ? WHERE id = ?");
        ){
            query.setString(1, nuovaMisurazione.getPasto());
            query.setString(2, nuovaMisurazione.getPrimaDopoPasto());
            query.setInt(3, nuovaMisurazione.getValoreGlicemia());
            query.setInt(4, nuovaMisurazione.getId());
            query.executeUpdate();
        }catch( SQLException e){System.err.println(e.getMessage());}
        
     }
    
    
    public static int riceviGlicemiaMedia(LocalDate DataInizio, LocalDate DataFine, String utente, DatiConfigurazioneXML dcxml){
         int mediaIntera=0; 
         double mediaDouble=0;
         try(
                 Connection connessione = DriverManager.getConnection(("jdbc:mysql://" + dcxml.ipDataBase + ":" + dcxml.portaDatabase + "/diariodiabete"), dcxml.nomeUtenteDatabase, dcxml.passwordDatabase);
                PreparedStatement query = connessione.prepareStatement("SELECT AVG(valoreGlicemia) AS mediaglicemia FROM misurazione WHERE email = ? "
                                                                                 + "AND (STRCMP(marcaTemporale, ? )= 1 OR STRCMP(marcaTemporale,?)=0) AND (STRCMP(marcaTemporale, ? )= -1 OR STRCMP(marcaTemporale,?)=0) ");
        ){
            query.setString(1, utente);
            query.setString(2, DataInizio.toString());
            query.setString(3, DataInizio.toString());
            query.setString(4, DataFine.toString());
            query.setString(5, DataFine.toString());
            ResultSet rs = query.executeQuery();
            while(rs.next()){
                mediaDouble=rs.getDouble("mediaglicemia");
            }
        }catch( SQLException e){
            System.err.println(e.getMessage());
        }
        mediaIntera=(int) mediaDouble;
        return mediaIntera;     
    }
    
     public static void ottieniAndamento(ObservableList<XYChart.Data<String, Double>> voci, LocalDate DataInizio, LocalDate DataFine, String email, DatiConfigurazioneXML dcxml) { 

         try(
                 Connection connessione = DriverManager.getConnection(("jdbc:mysql://" + dcxml.ipDataBase + ":" + dcxml.portaDatabase + "/diariodiabete"), dcxml.nomeUtenteDatabase, dcxml.passwordDatabase);
                PreparedStatement query = connessione.prepareStatement("SELECT pasto, valoreGlicemia, marcaTemporale, primadopo FROM misurazione WHERE email = ? and"
                                                                                 + " (STRCMP(marcaTemporale, ? )= 1 OR STRCMP(marcaTemporale,?)=0) AND (STRCMP(marcaTemporale, ? )= -1 OR STRCMP(marcaTemporale,?)=0)"
                                                                                 + " ORDER BY marcaTemporale, FIELD(pasto,'Colazione','Pranzo','Cena'), FIELD(primadopo,'Prima','Dopo') ");//01
        ){
            query.setString(1, email);
            query.setString(2, DataInizio.toString());
            query.setString(3, DataInizio.toString());
            query.setString(4, DataFine.toString());
            query.setString(5, DataFine.toString());
            ResultSet rs = query.executeQuery();
            voci.clear(); 
            while(rs.next()) 
                voci.add(new XYChart.Data<>(rs.getString("primadopo")+" "+ rs.getString("pasto")+ "\n "+ rs.getString("marcaTemporale"), rs.getDouble("valoreGlicemia"))); 
        } catch(SQLException e) {
            System.err.println(e.getMessage());
        }
    }
    

}


/*Commenti al Codice
00)Classe che esegue le query per soddisfare le richieste delle varie classi.
01)Ho bisogno di ordinare sia per data, che in ordine:Colazione, Pranzo, Cena. I pasti "Prima" hanno precedenza su quelli "Dopo"
*/