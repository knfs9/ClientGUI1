package serverclient.server.other.server; /**
 * Created by RTCCD on 04.06.14.
 */

import javafx.collections.ObservableList;
import sample.Controller;
import serverclient.client.Client;
import serverclient.client.ClientInterface;
import serverclient.server.other.server.Library;

import java.util.*;
import java.rmi.Remote;
import java.rmi.RemoteException;

public interface ServerClient extends Remote {
    void add(ArrayList<Library> data) throws RemoteException;
    void add(String name, String author, String genre,int count,int date, int id) throws RemoteException;
    ArrayList<Library> getAll() throws RemoteException;
    ArrayList<Library> findByName(String name) throws RemoteException;
    ArrayList<Library> findByAuthor(String auth) throws RemoteException;
    ArrayList<Library> findByGenre(String genre) throws RemoteException;
    void remove(String name) throws RemoteException;
    void remove(Library book) throws RemoteException;
   // void update() throws RemoteException;
    public void reg(ClientInterface client) throws RemoteException;
    public void unreg(Client client) throws RemoteException;


}
