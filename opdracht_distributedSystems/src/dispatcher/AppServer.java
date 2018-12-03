package dispatcher;

import java.io.Serializable;

import interfaces.AppServerInterface;

public class AppServer implements Serializable {
	private int ID;
	private String ipAdres;
	private int poortnummer;
	private AppServerInterface asi;
	
	
	public AppServer(int iD, String ipAdres, int poortnummer, AppServerInterface asi) {
		this.ID = iD;
		this.ipAdres = ipAdres;
		this.poortnummer = poortnummer;
		this.asi = asi;
	}
	
	public int getID() {
		return ID;
	}
	public void setID(int iD) {
		this.ID = iD;
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
	public AppServerInterface getAsi() {
		return asi;
	}
	public void setAsi(AppServerInterface asi) {
		this.asi = asi;
	}
	
	
	
	
	
	
	

}
