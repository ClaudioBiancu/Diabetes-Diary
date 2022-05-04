
import javafx.geometry.*;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.text.*;


public class AndamentoGlicemia {//00
    private final VBox sezioneAndamentoGlicemia;
    private final HBox sezioneGraficoGlicemiaMedia;
    private final GraficoAndamento graficoAndamento;
    private final GlicemiaMedia glicemiaMedia;
    private final Label labelAndamentoGlicemia;
    
    
    public AndamentoGlicemia(DatiConfigurazioneXML dcxml, InterfacciaDiarioDiabete interfacciaDiarioDiabete){
        glicemiaMedia=new GlicemiaMedia(dcxml);
        graficoAndamento=new GraficoAndamento();
        
        labelAndamentoGlicemia= new Label("Andamento Glicemia");
        sezioneAndamentoGlicemia= new VBox(20);
        sezioneGraficoGlicemiaMedia= new HBox(15);
        sezioneGraficoGlicemiaMedia.getChildren().addAll(graficoAndamento.getGrafico(), glicemiaMedia.getSezioneGlicemiaMedia());
        sezioneAndamentoGlicemia.getChildren().addAll(labelAndamentoGlicemia, sezioneGraficoGlicemiaMedia);
    }
    
    public VBox ottieniAndamentoGlicemia(){
        return sezioneAndamentoGlicemia;
    }
    
     public GraficoAndamento ottieniGraficoAndamento(){
        return graficoAndamento;
    }
     
     public GlicemiaMedia ottieniGlicemiaMedia(){
         return glicemiaMedia;
     }
    
    public void impostaStileAndamento(DatiConfigurazioneXML dcxml){
        labelAndamentoGlicemia.setFont(Font.font(dcxml.font, 22));
        labelAndamentoGlicemia.setAlignment(Pos.CENTER);
        sezioneAndamentoGlicemia.setAlignment(Pos.CENTER);
    }
}


/*Commenti al Codice
00)Contiene Grafico e GlicemiaMedia e i metodi per aggiornarli
*/