
import java.time.*;
import javafx.scene.chart.*;


public class GraficoAndamento {//00
    private XYChart.Series serie;//01
    private LineChart <String, Number> grafico;
    
    public GraficoAndamento(){
        serie = new XYChart.Series();
        CategoryAxis xAxis = new CategoryAxis();
        //xAxis.setTickLabelRotation(70);
        NumberAxis yAxis = new NumberAxis();
        grafico = new LineChart<>(xAxis, new NumberAxis());
        grafico.getData().add(serie);
        grafico.setAnimated(false); 
        grafico.setLegendVisible(false); 
       // grafico.setPrefSize(800, 300);
        grafico.setMinHeight(300);
        grafico.setMinWidth(800);
    }
    
    
    public LineChart<String, Number> getGrafico(){
        return grafico;
    }
   
    
    public void aggiornaGrafico(LocalDate dataInizio, LocalDate dataFine, String email, DatiConfigurazioneXML dcxml) {//02
        AzioniSuDatabaseDiarioDiabete.ottieniAndamento(serie.getData(), dataInizio, dataFine, email, dcxml); 
    }
}



/*
00)Classe che realizza un grafico di tipo LineChart
01) Membro che andrà a contenere le coppie di valori per il tracciamento del grafico
02) Richiede al Database i dati relativi al periodo selezionato nella classe PeriodoVisualizzazione
*/