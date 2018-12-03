package interfaces;

import java.rmi.Remote;
import java.rmi.RemoteException;

import applicationServer.ActiveGame;

public interface gameControllerInterface extends Remote{
    void giveTurn() throws RemoteException;
    
    void refreshBord2(int i,int j) throws RemoteException;

	void refreshScore(ActiveGame ag) throws RemoteException;

	void backToLobby() throws RemoteException;
}
