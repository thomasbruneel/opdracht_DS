package client;

import interfaces.gameControllerInterface;
import javafx.application.Platform;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;
import javafx.event.Event;
import javafx.event.EventHandler;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.List;
import java.util.Observable;

import applicationServer.ActiveGame;
import client.Tasks.GameRefreshTask;
import client.Tasks.ScoreRefreshTask;
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

public class GameController extends UnicastRemoteObject implements gameControllerInterface {

	@FXML
	AnchorPane uiGamePane;
	
	
	@FXML
	AnchorPane uiGameInfo;

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
    
    @FXML
    GridPane scoreInfo;

    GameRefreshTask task;
    Thread gameRefreshThread;
    Boolean aanZet;
    
    int i1;
    int j1;
    int i2;
    int j2;
    
    int score=0;

    public GameController() throws RemoteException {
        aanZet = false;
    }


    @FXML
    public void initialize() throws RemoteException{
    	activeGame=asi.getActiveGame(gameId);
    	asi.addPlayer(gameId, userName); // ook initialiseren score
    	System.out.println("spelers :"+asi.getActiveGame(gameId).getSpelers());//testen
    	asi.increasePlayerCount(gameId,true);
    	game=activeGame.getGame();
    	if(game!=null){
    		game.getBord().print();
    	}
        setupGame();
        setupScoreBord();

    	task=new GameRefreshTask(this);
    	gameRefreshThread = new Thread(task);
    	gameRefreshThread.start();
    	
    	Task task2=new ScoreRefreshTask(this);
    	Thread ScoreRefreshTask = new Thread(task2);
    	ScoreRefreshTask.start();
    	


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
        		text.setOnMousePressed(this::onPressed);
        		text.setOnMouseReleased(arg0 -> {
					try {
						onRelease(arg0);
					} catch (RemoteException e) {
						e.printStackTrace();
					}
				});
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

    private synchronized void onRelease(MouseEvent mouseEvent) throws RemoteException {
        if(!(secondpress==null)){
            //TODO: Keuze doorgeven aan gameserver

            i2=GridPane.getRowIndex(secondpress);
            j2=GridPane.getColumnIndex(secondpress);
            System.out.println("release2 "+i2+ "  "+j2);
            try {
                asi.flipCard(activeGame.getCreator(),i2,j2);
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
            	//als juiste match --> punt gescoord
            	System.out.println("hoera");
            	asi.increaseScore(gameId, userName);
            	
            }
            else{
                //als geen juiste match --> kaarten terug omdraaien
                asi.flipCard(activeGame.getCreator(),i1,j1);
                asi.flipCard(activeGame.getCreator(),i2,j2);
            }

            firstpress = null;
            secondpress = null;
        } else {
            i1=GridPane.getRowIndex(firstpress);
            j1=GridPane.getColumnIndex(firstpress);
            try {
            	System.out.println("release1 "+i1+ "  "+j1);
                asi.flipCard(activeGame.getCreator(),i1,j1);
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }

    }
    
   
    public  void refreshBord(ActiveGame ag){
    	System.out.println("refresh");
        Bord bord=ag.getGame().getBord();
    	Kaart[][]matrix=bord.getMatrix();
    	int grootte=bord.getGrootte();
    	
    	ObservableList<Node> nodes = gridpane.getChildren();
    	System.out.println("size: "+nodes.size());	//geeft size 17 want eerst node is class group bij (4x4)
    	for(Node n:nodes){ 
    		if(n instanceof Text){
    			Text text=(Text)n;
    			int col=GridPane.getColumnIndex(n);
    			int row=GridPane.getRowIndex(n);
    			
    			if(!matrix[row][col].isOmgedraaid()){
    				text.setText(" x ");
    			}
    			else{
    				text.setText(" "+String.valueOf(matrix[row][col].getWaarde())+" ");
    			}
    			
    		}
    		
    	}
    			

    }
    
    private void setupScoreBord() throws RemoteException {
    	scoreInfo =new GridPane();
    	scoreInfo.setTranslateY(100);
    	scoreInfo.setGridLinesVisible(true);
    	uiGameInfo.getChildren().add(scoreInfo);
    	/*
    	int row=0;
    	for(String speler:asi.getActiveGame(gameId).getSpelers()){
    	 	scoreInfo.add(new Text(speler), 0, row);
        	scoreInfo.add(new Text("0"), 1, row);
        	row++;
    	}*/
    	for(int i=0;i<4;i++){
    		for(int j=0;j<2;j++){
        	 	scoreInfo.add(new Text(" "), j, i);
    		}
    	}
	}
    
    public synchronized void refreshScore(ActiveGame ag){
    	ObservableList<Node> nodes = scoreInfo.getChildren();	
        int index=0;
        for(Node n:nodes){
        	if(n instanceof Text){
        		Text text=(Text)n ;
        		int col=GridPane.getColumnIndex(n);
        		if(index<ag.getSpelers().size()){
        			String speler=ag.getSpelers().get(index);
                		String score=String.valueOf(ag.getScore().get(speler));
                		if(col==0){
                			text.setText(speler);
                		}
                		else{
                			text.setText(score);
                			index++;
                		}
        			

        		
        		}

            	 	
        			
        	}
        		
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
    
    

