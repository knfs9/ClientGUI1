package serverclient.client;

/**
 * Created by RTCCD on 04.06.14.
 */
import java.io.*;
import java.rmi.NotBoundException;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.LinkedList;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import sample.Controller;
import serverclient.server.other.server.Library;
import serverclient.server.other.server.ServerClient;


public class Client extends UnicastRemoteObject  implements ClientInterface {
    private Library lib;
    private Controller controller;
    private Registry registry;
    private ServerClient srv;
    private ObservableList<Library> books = FXCollections.observableArrayList();


    public Client(Controller controller) throws RemoteException {
        super();
        this.controller = controller;

    }

    public void connect(Client cl) throws Exception{
        try {
            registry = LocateRegistry.getRegistry("localhost", 1099);
            srv = (ServerClient) registry.lookup("LibraryServer");
           //books = FXCollections.observableArrayList(srv.getAll());
            srv.reg(cl);
        }catch (RemoteException rm){
            rm.printStackTrace();
        }catch (NotBoundException nb){
            nb.printStackTrace();
        }

    }

    public void disconnect(Client cl) throws Exception{
            srv.unreg(cl);
    }

    public Library edit(Library lib,String author, String name, String genre, int date, int count){
        Library temp = null;
        for(int i = 0; i < books.size();i++){
            if(books.get(i).equals(lib)){
                temp = books.get(i);
                break;
            }
        }
        temp.setAuthor(author);
        temp.setName(name);
        temp.setGenre(genre);
        temp.setDate(date);
        temp.setCount(count);
        return temp;
    }

    public ArrayList<Library> getAllData(){
        try {
            return srv.getAll();
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }

    public ObservableList<Library> setData(Library lib){
        ObservableList<Library> data = FXCollections.observableArrayList(lib);
        return data;
    }

    // 0 - if String, 1 - if Class
    public void removeData(String obj/*, int mode*/){
        try {
           // if(mode == 0)
                srv.remove(obj);
            /*else if(mode == 1)
                srv.remove((Library)obj);*/
        }catch (Exception e){
            e.printStackTrace();
        }
    }
    public void addData(ArrayList<Library> book){
        try {
            books.addAll(book);
            srv.add(book);
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public boolean update() throws RemoteException{
        try {
            books.clear();
            books.addAll(books);
            controller.updateTable();
            return true;
        }catch (Exception e){
            e.printStackTrace();
            return false;
        }
    }

}
