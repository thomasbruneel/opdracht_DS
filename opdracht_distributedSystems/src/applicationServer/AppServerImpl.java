package applicationServer;


import java.io.Serializable;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.List;

import client.LobbyController;
import interfaces.AppServerInterface;
import interfaces.DatabankServerInterface;
import interfaces.LobbyControllerInterface;
import interfaces.gameControllerInterface;
import memoryGame.Kaart;

public class AppServerImpl extends UnicastRemoteObject implements AppServerInterface{
	private DatabankServerInterface dsi;
	private int id;
	private ArrayList<ActiveGame>activeGames;
	private ArrayList<ActiveGameInfo>activeGamesInfo;

	//private ArrayList<LobbyControllerInterface> lobbyControllers;

	public AppServerImpl() throws RemoteException{
		//lobbyControllers=new ArrayList<LobbyControllerInterface>();
		activeGames=new ArrayList<ActiveGame>();
		Registry registry=LocateRegistry.getRegistry("localhost",2222);
		try {
			dsi=(DatabankServerInterface) registry.lookup("DataBankService");
		} catch (NotBoundException e) {
			e.printStackTrace();
		}

	}
	// nieuw..
	public AppServerImpl(int DBportNumber, int id) throws RemoteException{
		//lobbyControllers=new ArrayList<LobbyControllerInterface>();
		activeGames=new ArrayList<ActiveGame>();
		this.id = id;
		Registry registry=LocateRegistry.getRegistry("localhost",DBportNumber);
		try {
			dsi=(DatabankServerInterface) registry.lookup("DataBankService");
			dsi.aanmelden();
		} catch (NotBoundException e) {
			e.printStackTrace();
		}

	}

	@Override
	public void register(String naam, String pwd) throws RemoteException {
		dsi.register(naam, pwd);

	}

	@Override
	public String login(String userName, String userPwd) throws RemoteException {	//geeft token terug als username en pass overeenkomt
		if(dsi.login(userName, userPwd)){
			String token=TokenGenerator.generate(userName);
			dsi.updateToken(userName,token);
			return token;

		}
		else{
			return null;
		}

	}

	@Override
	public boolean controleerUniekeNaam(String naam) throws RemoteException {
		return dsi.controleerUniekeNaam(naam);
	}

	@Override
	public void addActiveGame(ActiveGame activeGame) throws RemoteException {
		activeGames.add(activeGame);

		dsi.createActiveGame(activeGame);

	}

	@Override
	public void removeActiveGame(ActiveGame activeGame) throws RemoteException {
		ActiveGame tmp = null;
		for(ActiveGame ag:activeGames){
			if(ag.getCreator().equals(activeGame.getCreator())){
				tmp=ag;
			}
		}
		dsi.removeActiveGameInfo(tmp.getCreator());
		activeGames.remove(tmp);



	}
	@Override
	public  ArrayList<ActiveGame> getActiveGames() throws RemoteException {
		return activeGames;
	}

	@Override
	public ActiveGame getActiveGame(String id)throws RemoteException {
		ActiveGame activeGame=null;
    	for(ActiveGame ag:activeGames){
    		if(ag.getCreator().equals(id)){
    			activeGame=ag;
    		}
    	}
		return activeGame;

	}

	@Override
	public void increasePlayerCount(String gameId,boolean bit) throws RemoteException{

		ActiveGame activeGame=null;
    	for(ActiveGame ag:activeGames){
    		if(ag.getCreator().equals(gameId)){
    			ag.increasePlayerCount(bit);
    		}
    	}
    
		dsi.increasePlayerCount(gameId, bit);

	}

	@Override
	public void removeactiveGameById(String creator) throws RemoteException {
		ActiveGame tmp = null;
		for(ActiveGame ag:activeGames){
			if(ag.getCreator().equals(creator)){
				tmp=ag;
			}
		}
		if(tmp!=null){
			dsi.removeActiveGameInfo(tmp.getCreator());
			activeGames.remove(tmp);

		}


	}

	@Override
	public void flipCard(String creator,int x,int y)throws RemoteException{
		System.out.println("flipcard");
		ActiveGame activeGame=null;
    	for(ActiveGame ag:activeGames){
    		if(ag.getCreator().equals(creator)){
    			activeGame=ag;
    		}
    	}
    	if(activeGame!=null){
    		activeGame.getGame().getBord().flipCard(x,y);
    		activeGame.setChanged(true);
    	}
    	for(gameControllerInterface gci:activeGame.getGamecontrollers()){
    		System.out.println("interface");
    		gci.refreshBord2(x, y);
    	}

    	for(gameControllerInterface gci:activeGame.getSpectatecontrollers()){
    		System.out.println("interface");
    		gci.refreshBord2(x, y);
    	}

		System.out.println(creator + ": " + x + ", " +y);

	}

	@Override
	public void addPlayer(String gameId,String player)throws RemoteException{
		ActiveGame activeGame=null;
    	for(ActiveGame ag:activeGames){
    		if(ag.getCreator().equals(gameId)){
    			activeGame=ag;
    		}
    	}
    	activeGame.addPlayer(player);
    	activeGame.initializeScore(player,0);


	}

	@Override
	public void removePlayer(String gameID, String userName) throws RemoteException {
		ActiveGame tmpActiveGame=null;
    	for(ActiveGame ag:activeGames){
    		if(ag.getCreator().equals(gameID)){
    			tmpActiveGame=ag;
    		}
    	}
    	System.out.println("spelers size voor remove "+tmpActiveGame.getSpelers().size()+ " --> "+tmpActiveGame.getSpelers());
    	String tmp=null;
    	for(String s:tmpActiveGame.getSpelers()){
    		if(s.equals(userName)){
    			tmp=s;
    		}
    	}
    	tmpActiveGame.getSpelers().remove(tmp);
    	System.out.println("spelers size na remove "+tmpActiveGame.getSpelers().size()+ " --> "+tmpActiveGame.getSpelers());
    }

	@Override
	public ActiveGame getChangedActiveGame(String gameId) throws RemoteException{
		ActiveGame activeGame=null;
		for(ActiveGame ag:activeGames){
			if(ag.getCreator().equals(gameId)){
				activeGame=ag;
                if (activeGame.isChanged()) return activeGame;
			}
		}
        return null;
	}

	@Override
	public void increaseScore(String gameId, String speler) throws RemoteException {
		ActiveGame activeGame=null;
		for(ActiveGame ag:activeGames){
			if(ag.getCreator().equals(gameId)){
				activeGame=ag;

			}
		}
		activeGame.increaseScore(speler);

	}

	@Override
	public void addGameController(String gameId, gameControllerInterface gci) throws RemoteException {
		ActiveGame activeGame=null;
		for(ActiveGame ag:activeGames){
			if(ag.getCreator().equals(gameId)){
				activeGame=ag;

			}
		}
		if (activeGame != null && gci != null) {
			activeGame.addGameController(gci);
		}


	}

	@Override
	public void endTurnTest(String Gameid) throws RemoteException {
		ActiveGame activeGame=null;
		for(ActiveGame ag:activeGames){
			if(ag.getCreator().equals(Gameid)){
				activeGame=ag;

			}
		}
		int beurt;
		if(activeGame.getBeurt()==activeGame.getMaxPlayers()-1){
			beurt=0;
		}
		else{
			beurt=activeGame.getBeurt()+1;
		}
		activeGame.setBeurt(beurt);
		activeGame.getGamecontrollers().get(beurt).giveTurn();
	}
/*
	@Override
	public void addLobbyController(LobbyControllerInterface lobbyController) throws RemoteException {
		lobbyControllers.add(lobbyController);

	}

	@Override
	public void updateLobby() throws RemoteException {
		System.out.println("aantal lobbycontrollers "+lobbyControllers.size());
		for(LobbyControllerInterface lobbyController:lobbyControllers){
			System.out.println("lobbycontroller "+lobbyController.getIdController());
			lobbyController.updateLobby(activeGames);
		}

	}
	*/

	@Override
	public void addSpectateController(String gameId, gameControllerInterface gci) throws RemoteException {
		ActiveGame activeGame=null;
		for(ActiveGame ag:activeGames){
			if(ag.getCreator().equals(gameId)){
				activeGame=ag;

			}
		}
		if (activeGame != null && gci != null) {
			activeGame.addSpectateController(gci);

			Kaart[][] huidigspel = activeGame.getGame().getBord().getMatrix();
			int gamesize = activeGame.getGame().getBord().getGrootte();
			for(int i = 0; i<gamesize; i++){
				for (int j = 0; j<gamesize; j++){
					if (huidigspel[i][j].isOmgedraaid()) {
					    gci.refreshBord2(i,j);
					    gci.refreshBord2(i,j);
                    }
				}
			} //doet niet wat het moet
		}


	}

	@Override
	public void refreshScore(ActiveGame activeGame) throws RemoteException {
    	for(gameControllerInterface gci:activeGame.getGamecontrollers()){
    		gci.refreshScore(activeGame);
    	}

    	for(gameControllerInterface gci:activeGame.getSpectatecontrollers()){
    		System.out.println("interface");
    		gci.refreshScore(activeGame);
    	}

	}

	@Override
	public List<Leaderbord> getLeaderBord() throws RemoteException {
		return dsi.getAllLeaderbord();
	}

	@Override
	public void increaseWin(String userName) throws RemoteException {
		dsi.increaseWin(userName);

	}

	@Override
	public void leaveGame(String gameId) throws RemoteException {
		ActiveGame activeGame=null;
		for(ActiveGame ag:activeGames){
			if(ag.getCreator().equals(gameId)){
				activeGame=ag;

			}
		}
		for(gameControllerInterface gci:activeGame.getGamecontrollers()){
			System.out.println("gamecontroller");
    		gci.backToLobby();
    	}

    	for(gameControllerInterface gci:activeGame.getSpectatecontrollers()){
    		gci.backToLobby();
    	}

	}
/*
	@Override
	public void removeLobbyController(LobbyControllerInterface lobbyController) throws RemoteException {
		lobbyControllers.remove(lobbyController);

	}
*/
	/*
	@Override
	public void removeLobbyControllerByID(String id) throws RemoteException {
		LobbyControllerInterface lobbyControllerInterface=null;
		for(LobbyControllerInterface lci:lobbyControllers){
			if(lci.getIdController().equals(id)){
				lobbyControllerInterface=lci;
			}
		}
		if(lobbyControllerInterface!=null){
			lobbyControllers.remove(lobbyControllerInterface);
		}

	}
	*/

	@Override
	public List<byte[]> getImagesByTheme(String theme) throws RemoteException {
		return dsi.getImagesByTheme(theme);


	}

	@Override
	public ArrayList<ActiveGameInfo> getAllActiveGamesInfo() throws RemoteException {

		return (ArrayList<ActiveGameInfo>) dsi.getAllActiveGamesInfo();
	}
	@Override
	public void addActiveGameInfo(ActiveGameInfo activeGameInfo) throws RemoteException {
		dsi.createActiveGameInfo(activeGameInfo);

	}

    @Override
    public int getServerid() throws RemoteException {
        return id;
    }

    @Override
    public int getActiveGamessize() throws RemoteException {
        return activeGames.size();
    }


}
