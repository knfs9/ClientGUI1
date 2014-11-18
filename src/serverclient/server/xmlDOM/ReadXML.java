package serverclient.server.xmlDOM;

/**
 * Created by RTCCD on 04.06.14.
 */
import javax.xml.parsers.DocumentBuilderFactory;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import serverclient.server.other.server.Library;
import com.sun.javaws.jnl.LibraryDesc;
import com.sun.org.apache.xerces.internal.dom.DeferredElementImpl;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import sun.plugin.dom.core.Element;

import java.io.Serializable;
import java.util.*;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.File;

public class ReadXML  {
    public void print(){
        for(Library item: book)
        {
            System.out.println("----------------------------------");
            System.out.println(" Автор "+item.getAuthor());
            System.out.println(" Название "+item.getName());
            System.out.println(" Жанр "+ item.getGenre());
            System.out.println(" Количество " + item.getCount());
            System.out.println(" Id " + item.getId());
            System.out.println("Дата" + item.getDate());
            System.out.println("----------------------------------");
        }
    }

    private ArrayList<Library> book= new ArrayList<Library>();

    private ArrayList<Library> allBooks = new ArrayList<Library>();

    public ArrayList<Library> getAllBooks() {

        return allBooks;
    };

    public ReadXML(String file){
        try{
            DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
            DocumentBuilder db = dbf.newDocumentBuilder();
            Document doc = db.parse(file);

            System.out.println("Book:" + doc.getDocumentElement().getNodeName());
            NodeList list = doc.getElementsByTagName("Book");
            Library Book = null;
            for(int i = 0; i < list.getLength(); i++)
            {
                Node node = list.item(i);
                System.out.println("Element" + node.getNodeName());
                if(node.getNodeType() == Node.ELEMENT_NODE)
                {
                    DeferredElementImpl el = (DeferredElementImpl) node;

                   // int id = Integer.parseInt(el.getAttribute("id"));
                    //int id = Integer.parseInt(el.getElementsByTagName("id").item(0).getTextContent());
                    String name = el.getElementsByTagName("Имя").item(0).getTextContent();
                    String author = el.getElementsByTagName("Автор").item(0).getTextContent();
                    String genre = el.getElementsByTagName("Жанр").item(0).getTextContent();
                    int date = Integer.parseInt(el.getElementsByTagName("Дата").item(0).getTextContent());
                    int count = Integer.parseInt(el.getElementsByTagName("Количество").item(0).getTextContent());
                    Book = new Library(name,author,genre,count,date,i);
                    allBooks.add(Book);
                }
                book.add(Book);
            }

            print();
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
