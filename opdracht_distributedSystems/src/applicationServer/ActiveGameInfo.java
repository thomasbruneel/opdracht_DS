package applicationServer;

import java.io.Serializable;

public class ActiveGameInfo implements Serializable {
	
	private String creator;
	private int numberPlayers;
	private int maxPlayers;
	private int appServerId;
	
	
	public ActiveGameInfo(String creator, int numberPlayers, int maxPlayers, int appServerId) {
		this.creator = creator;
		this.numberPlayers = numberPlayers;
		this.maxPlayers = maxPlayers;
		this.appServerId = appServerId;
	}
	
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
	public int getAppServerId() {
		return appServerId;
	}
	public void setAppServerId(int appServerId) {
		this.appServerId = appServerId;
	}
	
	
	

	
	
	
	

}
