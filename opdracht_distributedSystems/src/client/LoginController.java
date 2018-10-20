package client;

import databankServer.DatabankServerMain;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

public class LoginController {
	
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
    
    DatabankServerMain dsm;

    @FXML
    public void initialize(){
        dsm=new DatabankServerMain();
        uiFailLogin.setVisible(false);
        

    }
    
    public void inloggen(){
        String gebruikersNaam=uiGebruikersnaam.getText();
        String wachtwoord= uiWachtwoord.getText();
        boolean check=false;
        check=dsm.checkPwd(gebruikersNaam, wachtwoord);
        System.out.println(gebruikersNaam);
        System.out.println(wachtwoord);
        System.out.println(check);
        if(check){
        	Main.openMenuUI();
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
        Main.openRegistratieUI();
        registratieLink.getScene().getWindow().hide();

    }

}
