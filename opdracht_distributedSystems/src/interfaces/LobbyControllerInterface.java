package interfaces;

import java.io.Serializable;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.ArrayList;

import applicationServer.ActiveGame;

/**
 * @deprecated was enkel bruikbaar bij 1appserver en 1databaseserver
 */
public interface LobbyControllerInterface extends Remote {
	
	//void updateLobby(ArrayList<ActiveGame> games) throws RemoteException;

	//String getIdController() throws RemoteException;

}
