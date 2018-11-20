package interfaces;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface gameControllerInterface extends Remote{
    void giveTurn() throws RemoteException;
}
