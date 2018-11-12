package client;

import java.rmi.RemoteException;
import java.util.ArrayList;

import applicationServer.ActiveGame;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import memoryGame.Game;

import static client.ClientMain.*;

public class GameController {

	@FXML
	AnchorPane GamePane;

	@FXML
    Button uiButton;
    
    Game game = null;
    ActiveGame activeGame=null;
    
    
    @FXML
    public void initialize() throws RemoteException{
    	
    	
    	ActiveGame activeGame=asi.getActiveGame(gameId);
    	game=activeGame.getGame();
    	if(game!=null){
    		game.getBord().print();
    	}
    	
        
        

    }
    
    public void backToLobby() throws RemoteException{
    	//als de creator het spel verlaat, wordt het spel beeindigd
    	if(activeGame.getCreator().equals(userName)){
    		System.out.println("hallohaalloffjzkfkzkfkf");
    		asi.removeActiveGame(activeGame);
    	}
    	openUIScreen("lobbyUI.fxml");
    	uiButton.getScene().getWindow().hide();
    	
    }

    void setupGame(){
        int width = (int)GamePane.getWidth();
        int height = (int)GamePane.getHeight();
    }

    
    
}
