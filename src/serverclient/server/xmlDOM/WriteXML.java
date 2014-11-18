package serverclient.server.xmlDOM;

/**
 * Created by RTCCD on 04.06.14.
 */
import javafx.collections.ObservableList;
import org.w3c.dom.Attr;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import serverclient.server.other.server.Library;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.File;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;

public class WriteXML {
   public WriteXML(ArrayList<Library> data){
        try {

            DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder docBuilder = docFactory.newDocumentBuilder();

            // root elements
            Document doc = docBuilder.newDocument();
            Element rootElement = doc.createElement("Library");
            doc.appendChild(rootElement);

            for(Library lib : data) {

                Element book = doc.createElement("Book");
                rootElement.appendChild(book);

                Attr attr = doc.createAttribute("id");
                attr.setValue(String.valueOf(lib.getId()));
                book.setAttributeNode(attr);

                // Book name
                Element name = doc.createElement("Имя");
                name.appendChild(doc.createTextNode(lib.getName()));
                book.appendChild(name);

                //Book genre
                Element genre = doc.createElement("Жанр");
                genre.appendChild(doc.createTextNode(lib.getGenre()));
                book.appendChild(genre);

                //Book author
                Element author = doc.createElement("Автор");
                author.appendChild(doc.createTextNode(lib.getAuthor()));
                book.appendChild(author);

                //Book date of publication
                Element count = doc.createElement("Количество");
                count.appendChild(doc.createTextNode(String.valueOf(lib.getCount())));
                book.appendChild(count);

                Element date = doc.createElement("Дата");
                date.appendChild(doc.createTextNode(String.valueOf(lib.getDate())));
                book.appendChild(date);
            }



            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();
            DOMSource source  = new DOMSource(doc);

            StreamResult result = new StreamResult(new File("library.xml"));

            transformer.transform(source,result);
            System.out.print("File saved");

        } catch (ParserConfigurationException pce) {
            pce.printStackTrace();
        } catch (TransformerException tr){
            tr.printStackTrace();
        }

    }



}
