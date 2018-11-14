package client;

import javafx.event.Event;
import javafx.event.EventHandler;
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
import javafx.scene.layout.GridPane;
import javafx.scene.text.Text;
import memoryGame.Bord;
import memoryGame.Game;
import memoryGame.Kaart;

import static client.ClientMain.*;

public class GameController {

	@FXML
	AnchorPane uiGamePane;

	@FXML
    Button uiButton;

    
    Game game = null;
    Bord bord=null;
    Kaart[][] matrix=null;
    ActiveGame activeGame=null;
    
    private GridPane gridpane;
    
    
    @FXML
    public void initialize() throws RemoteException{
    	
    	
    	activeGame=asi.getActiveGame(gameId);
    	game=activeGame.getGame();
    	if(game!=null){
    		game.getBord().print();
    	}
    	
        setupGame();

    }
    
    public void backToLobby() throws RemoteException{
    	//als de creator het spel verlaat, wordt het spel beeindigd
    	if(activeGame.getCreator().equals(userName)){
    		asi.removeActiveGame(activeGame);
    	}
    	openUIScreen("lobbyUI.fxml");
    	uiButton.getScene().getWindow().hide();
    	
    }

    public void setupGame() throws RemoteException{
        int width = (int)uiGamePane.getPrefWidth();
        int height = (int)(uiGamePane.getPrefWidth());
        System.out.println("gamepane size:  "+width+ "   "+height);
        gridpane=new GridPane();
        uiGamePane.getChildren().add(gridpane);
        bord=game.getBord();
        matrix=bord.getMatrix();
        for(int i=0;i<game.getBord().getGrootte();i++){
        	for(int j=0;j<game.getBord().getGrootte();j++){
        		Text text=new Text();
        		text.setText(" x ");
        		text.setOnMouseClicked(this::click);
        		gridpane.add(text, j, i);
        	}
        }
        

    }
    
    public void click(Event event){
    	Text text=(Text)event.getSource();
    	int i=GridPane.getRowIndex(text);
    	int j=GridPane.getColumnIndex(text);
    	if(text.getText().equals(" x ")){
        	text.setText(" "+String.valueOf(matrix[i][j].getWaarde())+" ");
    	}
    	else{
    		text.setText(" x ");
    	}

    	
    }


    
    
}
