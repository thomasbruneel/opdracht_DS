package applicationServer;

import java.io.Serializable;
import java.util.ArrayList;

import memoryGame.Game;

public class ActiveGame implements Serializable {
	private String creator;
	private int numberPlayers;
	private int maxPlayers;
	private String size;
	private Game game;
	private ArrayList<String>spelers;
	
	
	//constructor
	public ActiveGame(String creator, int numberPlayers,int maxPlayers, String size,Game game,ArrayList<String>spelers) {
		this.creator = creator;
		this.numberPlayers = numberPlayers;
		this.maxPlayers=maxPlayers;
		this.size = size;
		this.game=game;
		this.spelers=spelers;
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

	public String getSize() {
		return size;
	}
	public void setSize(String size) {
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


	public void increasePlayerCount(boolean bit) {
		if(bit==true){
			numberPlayers++;
		}
		else{
			numberPlayers--;
		}
		
		
	}
	
	public void voegSpelerToe(String s){
		spelers.add(s);
	}


}
