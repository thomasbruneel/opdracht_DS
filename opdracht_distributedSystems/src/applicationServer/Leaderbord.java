package applicationServer;

import java.io.Serializable;

public class Leaderbord implements Serializable {
	
	private String userName;
	private int wins;
	
	public Leaderbord(String userName, int wins) {
		this.userName = userName;
		this.wins = wins;
	}
	
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public int getWins() {
		return wins;
	}
	public void setWins(int wins) {
		this.wins = wins;
	}

	@Override
	public String toString() {
		return "Leaderbord [userName=" + userName + ", wins=" + wins + "]";
	}
	

}
