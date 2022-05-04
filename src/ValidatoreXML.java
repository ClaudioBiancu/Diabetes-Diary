import java.io.*;
import javax.xml.*;
import javax.xml.parsers.*;
import javax.xml.transform.dom.*;
import javax.xml.transform.stream.*;
import javax.xml.validation.*;
import org.w3c.dom.*;
import org.xml.sax.*;


public class ValidatoreXML { //
    public static boolean valida(String dcxml, String fileSchema, boolean file){//01
        try{
            DocumentBuilder db = DocumentBuilderFactory.newInstance().newDocumentBuilder();//02
            SchemaFactory sf = SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);
            Document d;
            if(file)
                d = db.parse(new File(dcxml));
            else
                d = db.parse(new InputSource(new StringReader(dcxml)));
            
            Schema s = sf.newSchema(new StreamSource(new File(fileSchema.equals("configurazione.xsd")?fileSchema:("./"+fileSchema))));//03
            s.newValidator().validate(new DOMSource(d));
        }catch(Exception e){ 
            if(e instanceof SAXException)//04
                System.err.println("Errore di validazione");
            System.err.println(e.getMessage());
            return false;
        }
        return true;
    }
}

/*COMMENTI AL CODICE
00)Gestiosce la validazione XML comparandolo con file XSD. Restituisce true se va a buon file
01)"boolean file" è true se viene passato un file XML, altrimenti è false e viene passata una stringa. 
02)Ottengo degli oggetti DOM da file XML
03)Oggetto Schema creato dallo SchemaFactory, ottenuto prelevando il file di schema XSD.
04)Se l'eccezione e' dovuta ad un errore di validazione.
*/