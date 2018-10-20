package client;

import javafx.fxml.FXML;
import javafx.scene.control.*;

import databankServer.DatabankServerMain;


public class RegistratieController {
	
    @FXML
    Button uiButton;

    @FXML
    TextField uiGebruikersnaam;

    @FXML
    PasswordField uiWachtwoord;
    
    @FXML
    Hyperlink loginLink;
    
    @FXML
    Label errorMessage;
    

    
    DatabankServerMain dsm;
    

    @FXML
    public void initialize(){
        dsm=new DatabankServerMain();
        errorMessage.setVisible(false);


    }
    
    public void registreren(){
        String gebruikersNaam=uiGebruikersnaam.getText();
        String wachtwoord= uiWachtwoord.getText();
        
        boolean check=dsm.controleerUniekeNaam(gebruikersNaam);
        if(check){
        	dsm.spelerToevoegen(gebruikersNaam, wachtwoord);
            uiGebruikersnaam.clear();
            uiWachtwoord.clear();
            errorMessage.setText("registratie voltooid");
            errorMessage.setVisible(true);

        }
        else{
        	uiGebruikersnaam.clear();
            uiWachtwoord.clear();
            errorMessage.setText("username is al gebruikt ");
            errorMessage.setVisible(true);

        }


    }
    
    public void terugNaarLogin(){
        Main.openLoginUI();
        loginLink.getScene().getWindow().hide();

    }
    
    
}
