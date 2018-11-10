package client;

import javafx.fxml.FXML;
import javafx.scene.control.*;

import java.rmi.RemoteException;

import databankServer.DatabankServerImpl;


public class RegistratieController {
	
    @FXML
    Button uiButton;

    @FXML
    TextField uiGebruikersnaam;

    @FXML
    PasswordField uiWachtwoord;
    
    @FXML
    PasswordField uiHerhaalWachtwoord;
    
    @FXML
    Hyperlink loginLink;
    
    @FXML
    Label errorMessage;
    

    
    

    @FXML
    public void initialize() throws RemoteException{
        errorMessage.setVisible(false);


    }
    
    public void registreren() throws RemoteException{
        String gebruikersNaam=uiGebruikersnaam.getText();
        String wachtwoord= uiWachtwoord.getText();
        String herhaalwachtwoord= uiHerhaalWachtwoord.getText();
        
        boolean check=Main.asi.controleerUniekeNaam(gebruikersNaam);
        if(check){
        	if(wachtwoord.equals(herhaalwachtwoord)){
        		Main.asi.register(gebruikersNaam, wachtwoord);
                uiGebruikersnaam.clear();
                uiWachtwoord.clear();
                uiHerhaalWachtwoord.clear();
                errorMessage.setText("registratie voltooid");
                errorMessage.setVisible(true);
        	}
        	
        	else{
                uiWachtwoord.clear();
                uiHerhaalWachtwoord.clear();
                errorMessage.setText("wachtwoord komt niet overeen");
                errorMessage.setVisible(true);
        	}
        	

        }
        else{
        	uiGebruikersnaam.clear();
            uiWachtwoord.clear();
            uiHerhaalWachtwoord.clear();
            errorMessage.setText("username is al gebruikt ");
            errorMessage.setVisible(true);

        }


    }
    
    public void terugNaarLogin(){
        Main.openLoginUI();
        loginLink.getScene().getWindow().hide();

    }
    
    
}
