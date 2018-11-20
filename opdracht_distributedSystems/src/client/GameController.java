package client;

import interfaces.gameControllerInterface;
import javafx.application.Platform;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;
import javafx.event.Event;
import javafx.event.EventHandler;

import java.net.URL;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.List;
import java.util.Observable;
import java.util.ResourceBundle;

import applicationServer.ActiveGame;
import client.Tasks.GameRefreshTask;
import client.Tasks.ScoreRefreshTask;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
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
import static java.lang.Thread.sleep;

public class GameController extends UnicastRemoteObject implements gameControllerInterface, Initializable {

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
    ImageView firstpress=null;
    ImageView secondpress=null;

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
    
    int width;
    int height;
    
    int press1=-666;
    int press2=-666;

    public GameController() throws RemoteException {
        aanZet = true;
    }


    @FXML
    public void initialize() throws RemoteException{
    	
        asi.addGameController(gameId, this);
    	activeGame=asi.getActiveGame(gameId);
    	asi.addPlayer(gameId, userName); // ook initialiseren score
    	System.out.println("spelers :"+asi.getActiveGame(gameId).getSpelers());//testen
    	asi.increasePlayerCount(gameId,true);
    	
    	asi.updateLobby();//refreshen lobby
    	
    	game=activeGame.getGame();
    	if(game!=null){
    		game.getBord().print();
    	}
        setupGame();
        setupScoreBord();

    	//task=new GameRefreshTask(this);
    	//gameRefreshThread = new Thread(task);
    	//gameRefreshThread.start();
    	
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
    	asi.updateLobby();//refreshen lobby
    	openUIScreen("lobbyUI.fxml");
    	uiButton.getScene().getWindow().hide();
    	
    }

    public void setupGame() throws RemoteException{
        width = (int)uiGamePane.getPrefWidth();
        height = (int)(uiGamePane.getPrefWidth());
        System.out.println("gamepane size:  "+width+ "   "+height);
        gridpane=new GridPane();
        gridpane.setGridLinesVisible(true);
        uiGamePane.getChildren().add(gridpane);
        bord=game.getBord();
        matrix=bord.getMatrix();
        for(int i=0;i<game.getBord().getGrootte();i++){
        	for(int j=0;j<game.getBord().getGrootte();j++){
        		gridpane.add(convertStringToImageView("client/images/batman/back2.jpg"), j, i);
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
        if(aanZet) {
            ImageView image = (ImageView) mouseEvent.getSource();
            int i = GridPane.getRowIndex(image);
            int j = GridPane.getColumnIndex(image);

            if (firstpress == null) {
                firstpress = image;
                press1 = matrix[i][j].getWaarde();
            } else {
                secondpress = image;
                press2 = matrix[i][j].getWaarde();
            }
        }
    }

    private synchronized void onRelease(MouseEvent mouseEvent) throws RemoteException {
        if(aanZet) {
            if (!(secondpress == null)) {
                //TODO: Keuze doorgeven aan gameserver

                i2 = GridPane.getRowIndex(secondpress);
                j2 = GridPane.getColumnIndex(secondpress);
                System.out.println("release2 " + i2 + "  " + j2);
                try {
                    asi.flipCard(activeGame.getCreator(), i2, j2);
                } catch (RemoteException e) {
                    e.printStackTrace();
                }

                if (press1 == press2) {
                    //als juiste match --> punt gescoord
                    System.out.println("hoera");
                    asi.increaseScore(gameId, userName);

                } else {
                    //als geen juiste match --> kaarten terug omdraaien
                    Thread t = new Thread(() -> {
                        try {
                            System.out.println("voor");
                            sleep(2000);
                            System.out.println("na");
                            asi.flipCard(activeGame.getCreator(), i1, j1);
                            asi.flipCard(activeGame.getCreator(), i2, j2);
                        } catch (RemoteException | InterruptedException e) {
                            e.printStackTrace();
                        }
                    });
                    t.start();

                }

                //asi.endTurnTest(gameId);
                firstpress = null;
                secondpress = null;
            } else {
                i1 = GridPane.getRowIndex(firstpress);
                j1 = GridPane.getColumnIndex(firstpress);
                try {
                    System.out.println("release1 " + i1 + "  " + j1);
                    asi.flipCard(activeGame.getCreator(), i1, j1);
                } catch (RemoteException e) {
                    e.printStackTrace();
                }
            }
        }
    }
    
   
    public void refreshBord(ActiveGame ag){
    	//System.out.println("refresh");
        Bord bord=ag.getGame().getBord();
    	Kaart[][]matrix=bord.getMatrix();
    	Kaart[][]huidige=activeGame.getGame().getBord().getMatrix();
    	int grootte=bord.getGrootte();
    	
    	ObservableList<Node> nodes = gridpane.getChildren();
    	System.out.println("size: "+nodes.size());	//geeft size 17 want eerst node is class group bij (4x4)
    	for(Node n:nodes){ 
    		if(n instanceof ImageView){
                int col=GridPane.getColumnIndex(n);
                int row=GridPane.getRowIndex(n);
                if(matrix[row][col]!=huidige[row][col]) {

                    ImageView imageView=(ImageView)n;


                    if (!matrix[row][col].isOmgedraaid()) {
                        imageView.setImage(new Image("client/images/batman/back2.jpg"));
                    } else {
                        imageView.setImage(new Image(afbeeldingen.get(matrix[row][col].getWaarde())));
                    }
                }
    			
    		}
    		
    	}


    }
    
    private void setupScoreBord() throws RemoteException {
    	scoreInfo =new GridPane();
    	scoreInfo.setTranslateY(120);
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
    public ImageView convertStringToImageView(String s) throws RemoteException{
    	Image image=new Image(s);
    	ImageView imageView=new ImageView(image);
    	imageView.setFitWidth(width/bord.getGrootte());
    	imageView.setFitHeight(height/bord.getGrootte());
    	imageView.setOnMousePressed(event -> {
			onPressed(event);
		});
    	imageView.setOnMouseReleased(event -> {
			try {
				onRelease(event);
			} catch (RemoteException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		});

		
		return imageView;
    }


    @Override
    public void giveTurn() throws RemoteException {
        aanZet=!aanZet;
        System.out.println("Aanzet? : " + aanZet);
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        try {
            initialize();
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }
    
    @Override
    public void refreshBord2(int i,int j) throws RemoteException{
    	System.out.println("refresh2");
    	Kaart[][]matrix=bord.getMatrix();
 
    	 ObservableList<Node> nodes = gridpane.getChildren();
    	 System.out.println("aantal nodes : "+nodes.size());

             Node n=nodes.get(bord.getGrootte()*i+j+1);
             int col = GridPane.getColumnIndex(n);
             int row = GridPane.getRowIndex(n);
             System.out.println("row "+row+ " col "+col);
             ImageView imageView = (ImageView) n;
             matrix[row][col].setOmgedraaid(!matrix[row][col].isOmgedraaid());
             if (!matrix[row][col].isOmgedraaid()) {
                 imageView.setImage(new Image("client/images/batman/back2.jpg"));
                 System.out.println("back");
                 System.out.println(matrix[i][j].getWaarde()+"    "+matrix[i][j].isOmgedraaid());
             } else {
                 imageView.setImage(new Image(afbeeldingen.get(matrix[row][col].getWaarde())));
                 System.out.println("front");
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
    
    

