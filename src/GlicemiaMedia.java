
import java.time.*;
import javafx.geometry.*;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.text.*;


public class GlicemiaMedia {//00
   private final Label labelGlicemiaMedia;
   private final  TextField textFieldGlicemiaMedia;
   private final VBox sezioneGlicemiaMedia;
   
   public GlicemiaMedia(DatiConfigurazioneXML dcxml){
       labelGlicemiaMedia= new Label(" Glicemia Media");
       textFieldGlicemiaMedia= new TextField();
       textFieldGlicemiaMedia.setEditable(false);//01
       textFieldGlicemiaMedia.setText("0");
       sezioneGlicemiaMedia= new VBox(20);
       sezioneGlicemiaMedia.getChildren().addAll(labelGlicemiaMedia, textFieldGlicemiaMedia);
       impostaStileGlicemiaMedia(dcxml);
       
   }
   
   public void setValoreGlicemia(LocalDate dataInizio, LocalDate dataFine, String email,DatiConfigurazioneXML dcxml){
       String stringGlicemiaMedia= String.valueOf(AzioniSuDatabaseDiarioDiabete.riceviGlicemiaMedia(dataInizio, dataFine,email , dcxml ));//02
       textFieldGlicemiaMedia.setText(stringGlicemiaMedia);
   }
   
   public VBox getSezioneGlicemiaMedia(){
       return sezioneGlicemiaMedia;
   }
   
   private void impostaStileGlicemiaMedia(DatiConfigurazioneXML dcxml){
       labelGlicemiaMedia.setFont(Font.font(dcxml.font, 18));
       labelGlicemiaMedia.setAlignment(Pos.CENTER);
       textFieldGlicemiaMedia.setMaxWidth(75);
       textFieldGlicemiaMedia.setAlignment(Pos.CENTER);
       sezioneGlicemiaMedia.setAlignment(Pos.TOP_CENTER);

   }
}

/*Commenti al Codice
00)Classe che realizza la visualizzazione del dato statistico Glicemia Media.
01)Non è permessa la modifica all'utente.
02)Il valore viene calcolato dal database in base ai valori e al periodo inseriti dall'utente.
*/
