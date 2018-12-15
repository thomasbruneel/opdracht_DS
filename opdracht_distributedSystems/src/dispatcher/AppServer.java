package dispatcher;

import java.io.Serializable;

import interfaces.AppServerInterface;

public class AppServer implements Serializable {
	private String ipAdres;
	private int poortnummer;
	
	private int DBportnummer;
	
	
	public AppServer(String ipAdres, int poortnummer, int DBportnummer) {
		this.ipAdres = ipAdres;
		this.poortnummer = poortnummer;
		this.DBportnummer=DBportnummer;
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


	public int getDBportnummer() {
		return DBportnummer;
	}


	public void setDBportnummer(int dBportnummer) {
		DBportnummer = dBportnummer;
	}
	
	
	
	
	
	

	
	
	
	
	
	
	

}
