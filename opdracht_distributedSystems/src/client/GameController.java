package client;

import javafx.application.Platform;
import javafx.concurrent.Task;
import javafx.event.Event;
import javafx.event.EventHandler;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.List;

import applicationServer.ActiveGame;
import client.Tasks.GameRefreshTask;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.RowConstraints;
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
    Text firstpress=null;
    Text secondpress=null;

    @FXML
    GridPane gridpane;

    GameRefreshTask task;
    Thread gameRefreshThread;
    Boolean aanZet;
    
    
    @FXML
    public void initialize() throws RemoteException{
    	activeGame=asi.getActiveGame(gameId);
    	asi.addPlayer(gameId, userName);
    	System.out.println("spelers :"+asi.getActiveGame(gameId).getSpelers());//testen
    	asi.increasePlayerCount(gameId,true);
    	game=activeGame.getGame();
    	if(game!=null){
    		game.getBord().print();
    	}
        setupGame();

    	task=new GameRefreshTask(this);
    	gameRefreshThread = new Thread(task);
    	gameRefreshThread.start();

    }
    
    public void backToLobby() throws RemoteException{
    	//als de creator het spel verlaat, wordt het spel beeindigd
    	if(activeGame.getCreator().equals(userName)){
    		asi.removeActiveGame(activeGame);
    	}
    	else{
    		asi.removePlayer(gameId,userName);
    	}
    	asi.increasePlayerCount(gameId,false);
    	openUIScreen("lobbyUI.fxml");
    	uiButton.getScene().getWindow().hide();
    	
    }

    public void setupGame() throws RemoteException{
        int width = (int)uiGamePane.getPrefWidth();
        int height = (int)(uiGamePane.getPrefWidth());
        System.out.println("gamepane size:  "+width+ "   "+height);
        gridpane=new GridPane();
        gridpane.setGridLinesVisible(true);
        uiGamePane.getChildren().add(gridpane);
        bord=game.getBord();
        matrix=bord.getMatrix();
        for(int i=0;i<game.getBord().getGrootte();i++){
        	for(int j=0;j<game.getBord().getGrootte();j++){
        		Text text=new Text();
        		text.setText(" x ");
        		//text.setOnMouseClicked(this::click);
        		text.setOnMousePressed(this::onPressed);
        		text.setOnMouseReleased(this::onRelease);
        		gridpane.add(text, j, i);
        	}
        }
        //Volledige speelveld gebruiken
        gridpane.setPrefSize(uiGamePane.getPrefWidth(),uiGamePane.getPrefWidth());
        for (int i=0; i<game.getBord().getGrootte(); i++) {
            RowConstraints row = new RowConstraints();
            row.setPercentHeight(50);
            ColumnConstraints column = new ColumnConstraints();
            column.setPercentWidth(50);
            gridpane.getRowConstraints().add(row);
            gridpane.getColumnConstraints().add(column);
        }
    }


    private synchronized void onPressed(MouseEvent mouseEvent){
        /* geeft error
        try {
            gameRefreshThread.wait();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        */
        Text text=(Text)mouseEvent.getSource();
        int i=GridPane.getRowIndex(text);
        int j=GridPane.getColumnIndex(text);
        if(text.getText().equals(" x ")){
            text.setText(" "+String.valueOf(matrix[i][j].getWaarde())+" ");
            if (firstpress==null) firstpress=text;
            else {
                secondpress=text;
            }
        }

    }

    private synchronized void onRelease(MouseEvent mouseEvent) {
        if(!(secondpress==null)){
            //TODO: Keuze doorgeven aan gameserver

            int i=GridPane.getRowIndex(secondpress);
            int j=GridPane.getColumnIndex(secondpress);
            System.out.println("release2 "+i+ "  "+j);
            try {
                asi.flipCard(activeGame.getCreator(),i,j);
            } catch (RemoteException e) {
                e.printStackTrace();
            }
            System.out.println("Eerste keuze: " + firstpress.getText() + " Tweede Keuze: " + secondpress.getText());

            try {
                wait(3000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            if(firstpress.getText().equals(secondpress.getText())){
            	System.out.println("hoera");
            	
            }
            else{
                firstpress.setText(" x ");
                secondpress.setText(" x ");
            }

            firstpress = null;
            secondpress = null;
        } else {
            int i=GridPane.getRowIndex(firstpress);
            int j=GridPane.getColumnIndex(firstpress);
            try {
            	System.out.println("release1 "+i+ "  "+j);
                asi.flipCard(activeGame.getCreator(),i,j);
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }

    }
    
    
/*
    public synchronized void click(Event event){
    	Text text=(Text)event.getSource();
    	int i=GridPane.getRowIndex(text);
    	int j=GridPane.getColumnIndex(text);
    	if(text.getText().equals(" x ")){
        	text.setText(" "+String.valueOf(matrix[i][j].getWaarde())+" ");
        	if (firstpress==null) firstpress=text;
        	else {
        	    secondpress=text;
        	    //TODO: Keuze doorgeven aan gameserver
                System.out.println("Eerste keuze: " + firstpress.getText() + " Tweede Keuze: " + secondpress.getText());
                try {
                    wait(3000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                //TODO: respons van server verwerken
                firstpress.setText(" x ");
                secondpress.setText(" x ");
                firstpress = null;
                secondpress = null;
            }
    	}
    	else{
    		text.setText(" x ");
    	}

    	
    }
*/
    public  void refreshBord(ActiveGame ag){
    	System.out.println("refresh");
    	if(Platform.isFxApplicationThread()) System.out.println("APPthread");
    	else System.out.println("NietAPPThread!!!");
        Bord bord=ag.getGame().getBord();
    	Kaart[][]matrix=bord.getMatrix();
    	int grootte=bord.getGrootte();
    	
    	for(int i=0;i<grootte;i++){
    		for(int j=0;j<grootte;j++){
    			System.out.println(matrix[i][j].isOmgedraaid());
    			
    			if(!matrix[i][j].isOmgedraaid()){
            		Text text=new Text();
            		text.setText(" x ");
            		gridpane.add(text, j, i); // hier zit de fout want thread stopt bij deze lijn
            		
    			}
    			else{
    				
            		Text text=new Text();
            		text.setText(String.valueOf(matrix[i][j].getWaarde()));
            		gridpane.add(text, j, i);
            	
            		
    			}
    			
    		}
    	}
    }
    
    
}
