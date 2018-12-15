package dispatcher;

import java.io.Serializable;

import interfaces.AppServerInterface;
import interfaces.DatabankServerInterface;

public class DatabankServer implements Serializable {
	private String ipAdres;
	private int poortnummer;
	private boolean online;
	
	
	public DatabankServer(String ipAdres, int poortnummer) {
		this.ipAdres = ipAdres;
		this.poortnummer = poortnummer;
		this.online=false;
	}
	
	public String getIpAdres() {
		return ipAdres;
	}
	public void setIpAdres(String ipAdres) {
		this.ipAdres = ipAdres;
	}
	public int getPoortnummer() {
		return poortnummer;
	}
	public void setPoortnummer(int poortnummer) {
		this.poortnummer = poortnummer;
	}

	public boolean isOnline() {
		return online;
	}

	public void setOnline(boolean online) {
		this.online = online;
	}
	
	



}
