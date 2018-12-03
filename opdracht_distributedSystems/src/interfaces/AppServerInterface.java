package interfaces;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.List;

import applicationServer.ActiveGame;
import applicationServer.Leaderbord;
import client.LobbyController;

public interface AppServerInterface extends Remote{
	
	void register(String naam, String pwd) throws RemoteException;
	
	String login(String userName,String userPwd) throws RemoteException;
	
	boolean controleerUniekeNaam(String naam) throws RemoteException;
	
	void addActiveGame(ActiveGame activeGame) throws RemoteException;
	
	ArrayList<ActiveGame> getActiveGames() throws RemoteException;

	void removeActiveGame(ActiveGame activeGame) throws RemoteException;
	
	void removeactiveGameById(String creator) throws RemoteException;

	ActiveGame getActiveGame(String id) throws RemoteException;

	void increasePlayerCount(String gameId,boolean bit) throws RemoteException;

	void flipCard(String creator, int x, int y)throws RemoteException;

	void addPlayer(String gameId, String speler) throws RemoteException;

	void removePlayer(String creator, String userName) throws RemoteException;

	ActiveGame getChangedActiveGame(String gameId) throws RemoteException;
	
	void increaseScore(String gameId, String speler) throws RemoteException;
	
	void addGameController(String Gameid,gameControllerInterface gci) throws RemoteException;

	void endTurnTest(String Gameid) throws RemoteException;

	void addLobbyController(LobbyControllerInterface lobbyController) throws RemoteException;

	void updateLobby() throws RemoteException;

	void addSpectateController(String gameId, gameControllerInterface gameController) throws RemoteException;

	void refreshScore(ActiveGame activeGame) throws RemoteException;

	List<Leaderbord> getLeaderBord()throws RemoteException;

	void increaseWin(String userName) throws RemoteException;

	void leaveGame(String gameId) throws RemoteException;

	void removeLobbyController(LobbyControllerInterface lobbyController)throws RemoteException;
	
	void removeLobbyControllerByID(String id)throws RemoteException;

}
