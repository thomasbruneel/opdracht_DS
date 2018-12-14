package client;

import javafx.fxml.FXML;
import javafx.scene.control.*;

import static client.ClientMain.NoTokenCheck;
import static client.ClientMain.openUIScreen;

import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

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
        System.out.println("registreren");
        boolean check=ClientMain.asi.controleerUniekeNaam(gebruikersNaam);
        if(check){
        	if(wachtwoord.equals(herhaalwachtwoord)){
        		//String error = null;
        		//error=isValid(wachtwoord,error);
        		//if(error==null){
            		ClientMain.asi.register(gebruikersNaam, wachtwoord);
                    uiGebruikersnaam.clear();
                    uiWachtwoord.clear();
                    uiHerhaalWachtwoord.clear();
                    errorMessage.setText("registratie voltooid");
                    errorMessage.setVisible(true);
        		//}
        		//else{
        			//errorMessage.setText(error);
                    //errorMessage.setVisible(true);
        		//}

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
    public static String isValid(String password, String error) {

        Pattern specailCharPatten = Pattern.compile("[^a-z0-9 ]", Pattern.CASE_INSENSITIVE);
        Pattern UpperCasePatten = Pattern.compile("[A-Z ]");
        Pattern lowerCasePatten = Pattern.compile("[a-z ]");
        Pattern digitCasePatten = Pattern.compile("[0-9 ]");
        


        if (password.length() < 8) {
            error="Password lenght must have alleast 8 character !!";
        }
     
        if (!specailCharPatten.matcher(password).find()) {
            error="Password must have atleast one specail character !!";
        }
      
        if (!UpperCasePatten.matcher(password).find()) {
            error="Password must have atleast one uppercase character !!";
        }
        if (!lowerCasePatten.matcher(password).find()) {
            error="Password must have atleast one lowercase character !!";
        }
        if (!digitCasePatten.matcher(password).find()) {
            error="Password must have atleast one digit character !!";
        }
        System.out.println("error "+error);
        return error;

    }


	public void terugNaarLogin(){
    	NoTokenCheck("loginUI.fxml");
        loginLink.getScene().getWindow().hide();

    }
    
    
}
