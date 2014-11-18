package serverclient.server;


import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import sample.Controller;
import serverclient.client.Client;
import serverclient.client.ClientInterface;
import serverclient.server.other.server.ServerClient;
import serverclient.server.other.server.Library;
import serverclient.server.xmlDOM.ReadXML;
import serverclient.server.xmlDOM.WriteXML;


/**
 * Created by RTCCD on 04.06.14.
 */
import java.io.*;
import java.nio.channels.AlreadyBoundException;
import java.util.*;
import java.net.*;
import java.rmi.registry.Registry;
import java.rmi.registry.LocateRegistry;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

public class Server implements ServerClient {
    WriteXML writeXML;
    ReadXML readXML = new ReadXML("library.xml");
    private ArrayList<Library> lib;
    private static ServerClient srv;
    private static Registry registry;
    private static Server obj;
    public static ArrayList<ClientInterface> clients = new ArrayList<ClientInterface>();
    public static ArrayList<Controller> contr = new ArrayList<Controller>();


    public Server() throws RemoteException{
        super();
    }

    public void add(ArrayList<Library> data){
        ReadData();
        data.addAll(lib);
        writeXML = new WriteXML(data);
        try {
            for(ClientInterface c:clients){
                c.update();
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public void ReadData(){
        //readXML = new ReadXML("library.xml");
        readXML = new ReadXML("library.xml");
        lib = readXML.getAllBooks();
    }


    public void add(String name, String author, String genre,int count,int date, int id)
    {
        Library book = new Library( author,name,genre, count, date, id );
        ArrayList<Library> data = new ArrayList<Library>();
        data.add(book);
        writeXML = new WriteXML(data);
        try {
            for(ClientInterface c:clients){
                c.update();
            }
        }catch (Exception e){
            e.printStackTrace();
        }

    }

    public ArrayList<Library> findByName(String name)
    {
        ArrayList<Library> temp = new ArrayList<Library>();
        Library tempBook = null;
        //readXML = new ReadXML("library.xml");
        lib = readXML.getAllBooks();
        for(int i = 0; i < lib.size(); i++)
        {
            tempBook = lib.get(i);
            if(tempBook.getName().equals(name))
                temp.add(tempBook);
        }
        return temp;
    }

    public void remove(String name)
    {
        Library book = null;
        //readXML = new ReadXML("library.xml");
        lib = readXML.getAllBooks();
        for(int i = 0; i < lib.size(); i++)
        {
            book = lib.get(i);
            if(book.getName().equals(name))
            {
                lib.remove(i);
                break;
            }
        }

        writeXML = new WriteXML(lib);
        try {
            for(ClientInterface c:clients){
                c.update();
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }

   /* public void update() throws RemoteException{
        String[] clients = registry.list();
        //ServerClient tempSrv =
        for(int i = 0; i < clients.length; i++){
            try {
                registry.bind(clients[i], obj);
            }catch(Exception e){
                e.printStackTrace();
            }
        }
    }*/

    public void remove(Library book){
        Library tempLib = null;
        lib = readXML.getAllBooks();
        for(int i = 0; i < lib.size(); i++){
            tempLib = lib.get(i);
            if(tempLib.getName().equals(book.getName())){
                lib.remove(i);
            }
        }
        writeXML = new WriteXML(lib);

    }

    public ArrayList<Library> findByGenre(String genre)
    {
        ArrayList<Library> temp = new ArrayList<Library>();
        Library tempBook = null;
        //readXML = new ReadXML("library.xml");
        lib = readXML.getAllBooks();
        for(int i = 0; i < lib.size(); i++)        {

            tempBook = lib.get(i);
            if(tempBook.getGenre().equals(genre) )
                temp.add(tempBook);
        }
        return temp;
    }

    public ArrayList<Library> getAll(){
        //readXML = new ReadXML("library.xml");
        ReadData();
        lib = readXML.getAllBooks();
        return lib;
    }

    public ArrayList<Library> findByAuthor(String auth){

        ArrayList<Library> temp = new ArrayList<Library>();
        Library tempBook = null;
        readXML = new ReadXML("library.xml");
        lib = readXML.getAllBooks();
        for(int i = 0; i < lib.size(); i++)
        {

            tempBook = lib.get(i);
            if(tempBook.getAuthor().equals(auth))
                temp.add(tempBook);
        }
        return temp;

    }

    public void reg(ClientInterface client) {
        clients.add(client);
    }

    public void unreg(Client client) throws RemoteException {
        clients.remove(client);
    }

    public static void main(String[] args) {

        try{
            obj = new Server();
            srv = (ServerClient) UnicastRemoteObject.exportObject(obj,0);
            registry = LocateRegistry.createRegistry(1099);
            registry.bind("LibraryServer", srv);
            System.err.println("Server ready");
        }catch (Exception e){
            System.err.println("server exception: " + e.toString());
            e.printStackTrace();
        }

    }


}
