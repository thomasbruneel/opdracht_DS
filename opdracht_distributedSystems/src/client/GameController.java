package client;

import java.rmi.RemoteException;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

import static client.ClientMain.*;

public class GameController {
	
    @FXML
    Button uiButton;

    
    @FXML
    Label uiFailLogin;
    
    
    @FXML
    public void initialize() throws RemoteException{
        
        

    }
    
    public void backToLobby(){
    	openUIScreen("lobbyUI.fxml");
    	uiButton.getScene().getWindow().hide();
    	
    }
    
    
}
