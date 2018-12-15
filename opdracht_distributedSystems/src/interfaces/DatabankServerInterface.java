package interfaces;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;

import applicationServer.ActiveGame;
import applicationServer.Leaderbord;
import client.User;

public interface DatabankServerInterface extends Remote{
	
	void register(String naam, String pwd) throws RemoteException;
	
	boolean login(String userName,String userPwd) throws RemoteException;
	
	boolean controleerUniekeNaam(String naam) throws RemoteException;

	void updateToken(String userName, String token) throws RemoteException;

	void createActiveGame(ActiveGame activeGame)throws RemoteException;

	void removeActiveGame(String creator)throws RemoteException;

	void increaseWin(String userName) throws RemoteException;

	void createLeaderbord(String userName) throws RemoteException;
	
	List<Leaderbord> getAllLeaderbord() throws RemoteException;
	
	public List<byte[]> getImagesByTheme(String theme) throws RemoteException;

    void registreerdb(String ip, int poortnummer)throws RemoteException;

	String pingAnderen() throws RemoteException;

	String pingResult() throws RemoteException;

	void verwerkQueues() throws RemoteException;

	List<User> getUserQueue() throws RemoteException;

	List<ActiveGame> getActivegames() throws RemoteException;

	void clearQueues() throws RemoteException;

	String getid() throws  RemoteException;

	void aanmelden() throws  RemoteException;

	int getCount() throws RemoteException;
}
