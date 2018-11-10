package client;

import java.rmi.RemoteException;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

import static client.ClientMain.*;

public class LobbyController {
    @FXML
    Label uiWelcomeUser;
    
    @FXML
    Button uiLogoutButton;
    
    @FXML
    Button uiCreateNewGameButton;
    
    
    @FXML
    public void initialize() throws RemoteException{
    	System.out.println("username "+userName);
        uiWelcomeUser.setText(userName);
        

    }
    
    public void createNewGame(){
    	openNewGameUI();
    	uiLogoutButton.getScene().getWindow().hide();
    }

}
