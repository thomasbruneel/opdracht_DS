package client;

import interfaces.gameControllerInterface;
import javafx.application.Platform;
import javafx.collections.ObservableList;

import java.net.URL;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;

import applicationServer.ActiveGame;
import client.Tasks.GameRefreshTask;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
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

import java.io.ByteArrayInputStream;

public class GameController extends UnicastRemoteObject implements gameControllerInterface, Initializable {

	@FXML
	AnchorPane uiGamePane;
	
	@FXML
	AnchorPane uiGameInfo;

	@FXML
    Button uiButton;
	
    @FXML
    Label beurt;
    
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
    
    List<byte[]> afbeeldingen;

    public GameController() throws RemoteException {
        aanZet = false;
    }


    @FXML
    public void initialize() throws RemoteException{
    	
    	beurt.setVisible(false);
    	setupScoreBord();
    	activeGame=asi.getActiveGame(gameId);
        game=activeGame.getGame();
        if(game!=null){
            game.getBord().print();
        }
        afbeeldingen=new ArrayList<>();
        if(activeGame.getTheme().equals("batman")){
        	afbeeldingen=imagesBatman;
        }
        else if(activeGame.getTheme().equals("pokemon")){
        	afbeeldingen=imagesPokemon;
        }
        setupGame();

        if(!spectateMode){
            asi.addGameController(gameId, this);
        	asi.addPlayer(gameId, userName); // ook initialiseren score
        	asi.increasePlayerCount(gameId,true);
        	activeGame=asi.getActiveGame(gameId);
        	asi.refreshScore(activeGame);
        	if(activeGame.getMaxPlayers()==activeGame.getNumberPlayers()){
        		System.out.println("ik ben aan beurt");
        		aanZet=true;
        		beurt.setVisible(true);
        		beurt.setText("JOUW BEURT");
        	}
        	else{
        		System.out.println("ik ben niet aan beurt");
        	}
        	
        	
        }
        else{
        	asi.addSpectateController(gameId, this);
        	asi.refreshScore(asi.getActiveGame(gameId));
        }
        
    	//asi.updateLobby();//refreshen lobby
    	


    	//task=new GameRefreshTask(this);
    	//gameRefreshThread = new Thread(task);
    	//gameRefreshThread.start();
    	
    	//Task task2=new ScoreRefreshTask(this);
    	//Thread ScoreRefreshTask = new Thread(task2);
    	//ScoreRefreshTask.start();
    }


	public void leaveGame() throws RemoteException{
    	//als de creator het spel verlaat, wordt het spel beeindigd
		if(!spectateMode){
	    	asi.leaveGame(gameId);
			asi.removeActiveGame(activeGame);
	    	//asi.updateLobby();//refreshen lobby

		}
		else{
			openUIScreen("lobbyUI.fxml");
        	uiButton.getScene().getWindow().hide();
		}
    	
    }
	@Override
	public void backToLobby() throws RemoteException{
		System.out.println("somone left the game");
		 new Thread(new Runnable() {
	            @Override public void run() {
	                Platform.runLater(new Runnable() {
	                    @Override public void run() {
	                    	openUIScreen("lobbyUI.fxml");
	                    	uiButton.getScene().getWindow().hide();

	                    }
	                });
	            }}).start();
    	
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
        		gridpane.add(convertStringToImageView(afbeeldingen.get(18)), j, i);
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

                i2 = GridPane.getRowIndex(secondpress);
                j2 = GridPane.getColumnIndex(secondpress);
                System.out.println("release2 " + i2 + "  " + j2);

                if(!(i1==i2 && j1==j2)) {

                    try {
                        asi.flipCard(activeGame.getCreator(), i2, j2);
                    } catch (RemoteException e) {
                        e.printStackTrace();
                    }

                    if (press1 == press2) {
                        //als juiste match --> punt gescoord
                        System.out.println("hoera");
                        asi.increaseScore(gameId, userName);
                        asi.refreshScore(asi.getActiveGame(gameId));
                    	beurt.setVisible(true);
                        beurt.setText("JOUW BEURT");

                    } else {
                        //als geen juiste match --> kaarten terug omdraaien
                        Thread t = new Thread(() -> {
                            try {
                                System.out.println("voor");
                                sleep(2000);
                                System.out.println("na");
                                asi.flipCard(activeGame.getCreator(), i1, j1);
                                asi.flipCard(activeGame.getCreator(), i2, j2);
                                asi.endTurn(gameId);
                                aanZet = false;
                            } catch (RemoteException | InterruptedException e) {
                                e.printStackTrace();
                            }
                        });
                        t.start();
                    	beurt.setVisible(true);
                        beurt.setText("WACHTEN");

                    }
                    firstpress = null;
                    secondpress = null;
                } else secondpress = null;
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
                        imageView.setImage(new Image(new ByteArrayInputStream(afbeeldingen.get(18))));
                    } else {
                        imageView.setImage(new Image(new ByteArrayInputStream(afbeeldingen.get(matrix[row][col].getWaarde()))));
                    }
                }
    			
    		}
    		
    	}


    }
    public void setupScoreBord() throws RemoteException {
    	scoreInfo =new GridPane();
    	scoreInfo.setTranslateY(120);
    	scoreInfo.setGridLinesVisible(true);
    	uiGameInfo.getChildren().add(scoreInfo);
    	for(int i=0;i<4;i++){
    		for(int j=0;j<2;j++){
        	 	scoreInfo.add(new Text(" "), j, i);
    		}
    	}
	}
    @Override
    public  void refreshScore(ActiveGame ag) throws RemoteException{
    	ObservableList<Node> nodes = scoreInfo.getChildren();
        int index=0;
        int punten=0;
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
                			punten=punten+Integer.parseInt(score);
                			text.setText(score);
                			index++;
                		}

        		}
	
        	}
        		
        }
        int grootte=asi.getActiveGame(gameId).getGame().getBord().getGrootte();
        if(punten==((grootte*grootte)/2)){
        	Map<String,Integer>map=ag.getScore();
        	int highScore=0;
        	ArrayList<String> winnaar=new ArrayList<String>();
        	for (Map.Entry<String,Integer> entry : map.entrySet()){
        		if(highScore<=entry.getValue()){
        			highScore=entry.getValue();
        		}

        	}
        	for (Map.Entry<String,Integer> entry : map.entrySet()){
        		if(highScore==entry.getValue()){
        			winnaar.add(entry.getKey());
        		}

        	}
        	for(String s:winnaar){
        		if(s.equals(userName)){
        			new Thread(new Runnable() {
        	            @Override public void run() {
        	                Platform.runLater(new Runnable() {
        	                    @Override public void run() {
        	                    	beurt.setVisible(true);
        	                        beurt.setText("WINNER");
        	                    }
        	                });
        	                try {
								asi.increaseWin(userName);
							} catch (RemoteException e) {
								e.printStackTrace();
							}
        	            }}).start();
        			break;
        		}
        		else{
        			 new Thread(new Runnable() {
           	            @Override public void run() {
           	                Platform.runLater(new Runnable() {
           	                    @Override public void run() {
           	                    	beurt.setVisible(true);
           	                        beurt.setText("LOSER");
           	                    }
           	                });

           	            }}).start();
        		}
        	}
        	
        
        
        }
    }
    public ImageView convertStringToImageView(byte[] byteArray) throws RemoteException{
    	Image image=new Image(new ByteArrayInputStream(byteArray));
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
        new Thread(() -> Platform.runLater(() -> {
            beurt.setVisible(true);
            beurt.setText("JOUW BEURT");
        })).start();
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
    public void refreshBord(int i, int j) throws RemoteException{
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
            	 imageView.setImage(new Image(new ByteArrayInputStream(afbeeldingen.get(18))));
                 System.out.println("back");
                 System.out.println(matrix[i][j].getWaarde()+"    "+matrix[i][j].isOmgedraaid());
             } else {
                 imageView.setImage(new Image(new ByteArrayInputStream(afbeeldingen.get(matrix[row][col].getWaarde()))));
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
    
    

