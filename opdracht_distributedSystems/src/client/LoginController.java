package client;

import java.rmi.RemoteException;
import java.util.Observable;

import databankServer.DatabankServerImpl;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

import static client.ClientMain.*;// aan attributen asi geraken

public class LoginController{
	
    @FXML
    Button uiButton;

    @FXML
    TextField uiGebruikersnaam;

    @FXML
    PasswordField uiWachtwoord;
    
    @FXML
    Hyperlink registratieLink;
    
    @FXML
    Label uiFailLogin;
    


    @FXML
    public void initialize() throws RemoteException{
        uiFailLogin.setVisible(false);
        

    }
    
    public void inloggen() throws RemoteException{
        String gebruikersNaam=uiGebruikersnaam.getText();
        String wachtwoord= uiWachtwoord.getText();
        String t;
        t=asi.login(gebruikersNaam, wachtwoord);
        System.out.println(gebruikersNaam);
        System.out.println(wachtwoord);
        if(t!=null){
        	ClientMain.openMenuUI();
        	uiButton.getScene().getWindow().hide();
        }
        else{
        	uiGebruikersnaam.clear();
        	uiWachtwoord.clear();
        	uiFailLogin.setVisible(true);
        	
        }

    }
    
    public void registreerHier(){
    	System.out.println("word ik uitgevoerd");
        ClientMain.openRegistratieUI();
        registratieLink.getScene().getWindow().hide();

    }

}
