package serverclient.client;

import java.rmi.Remote;
import java.rmi.RemoteException;

/**
 * Created by RTCCD on 31.10.2014.
 */
public interface ClientInterface extends Remote {
    public boolean update() throws RemoteException;
    public boolean thisIsMe() throws RemoteException;
}
