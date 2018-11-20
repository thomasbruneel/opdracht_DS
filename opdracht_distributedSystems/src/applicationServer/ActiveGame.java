package applicationServer;

import java.io.Serializable;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.Map;

import memoryGame.Game;

public class ActiveGame implements Serializable {
	private String creator;
	private int numberPlayers;
	private int maxPlayers;
	private int size;
	private Game game;
	private ArrayList<String>spelers;
	private Map<String,Integer> score;
	private boolean changed;
	boolean theme;
	
	
	//constructor
	public ActiveGame(String creator, int numberPlayers,int maxPlayers, int size,Game game,ArrayList<String>spelers,Map<String,Integer>score, boolean theme) {
		this.creator = creator;
		this.numberPlayers = numberPlayers;
		this.maxPlayers=maxPlayers;
		this.size = size;
		this.game=game;
		this.spelers=spelers;
		this.changed=false;
		this.score=score;
		this.theme=theme;
	}
	
	
	// getters en setters
	public String getCreator() {
		return creator;
	}
	public void setCreator(String creator) {
		this.creator = creator;
	}
	public int getNumberPlayers() {
		return numberPlayers;
	}
	public void setNumberPlayers(int numberPlayers) {
		this.numberPlayers = numberPlayers;
	}
	
	public int getMaxPlayers() {
		return maxPlayers;
	}

	public void setMaxPlayers(int maxPlayers) {
		this.maxPlayers = maxPlayers;
	}



	public int getSize() {
		return size;
	}


	public void setSize(int size) {
		this.size = size;
	}


	public Game getGame() {
		return game;
	}

	public void setGame(Game game) {
		this.game = game;
	}

	public ArrayList<String> getSpelers() {
		return spelers;
	}

	public void setSpelers(ArrayList<String> spelers) {
		this.spelers = spelers;
	}

	public boolean isChanged() {
		return changed;
	}

	public void setChanged(boolean changed) {
		this.changed = changed;
	}
	
	

	public Map<String, Integer> getScore() {
		return score;
	}


	public void setScore(Map<String, Integer> score) {
		this.score = score;
	}
	

	public boolean getTheme() {
		return theme;
	}


	public void setTheme(boolean theme) {
		this.theme = theme;
	}


	public void increasePlayerCount(boolean bit) {
		if(bit){
			numberPlayers++;
		}
		else{
			numberPlayers--;
		}
		
		
	}
	
	public void addPlayer(String s) throws RemoteException{
		spelers.add(s);
	}


	public void initializeScore(String player, int i) {
		score.put(player, i);
		
		
	}


	public void increaseScore(String speler) {
		score.put(speler, score.get(speler) + 1);
		
	}


}
