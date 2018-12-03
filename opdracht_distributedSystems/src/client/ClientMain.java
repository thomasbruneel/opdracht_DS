package client;
	
import static client.ClientMain.asi;
import static client.ClientMain.gameId;
import static client.ClientMain.spectateMode;
import static client.ClientMain.token;
import static client.ClientMain.userName;

import java.io.IOException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.HashMap;
import java.util.Map;

import applicationServer.TokenGenerator;
import interfaces.AppServerInterface;
import interfaces.DatabankServerInterface;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;


public class ClientMain extends Application {
	
	public static AppServerInterface asi;
	public static String token;
	public static String userName;
	
	public static String gameId;
	
	public static Map<Integer,String> afbeeldingen=new HashMap();
	
	public static boolean spectateMode=false;
	
	@Override
	public void start(Stage primaryStage) {
		try {
			AnchorPane root = (AnchorPane)FXMLLoader.load(getClass().getResource("loginUI.fxml"));
			Scene scene = new Scene(root,600,600);
			primaryStage.setScene(scene);
			primaryStage.setResizable(false);
			primaryStage.show();
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	
	@Override
	public void stop() throws RemoteException{
		if(!spectateMode&&gameId!=null){
	    	asi.leaveGame(gameId);
			asi.removeactiveGameById(gameId);
	    	asi.updateLobby();//refreshen lobby

		}
		asi.removeLobbyControllerByID(userName);
		userName=token=null;
	    System.out.println("Stage is closing");
	}
	
	public static void openUIScreen(String screen){
		AnchorPane root = null;
		boolean checkToken=false;
		if(token!=null){
			checkToken=TokenGenerator.CheckExpiration(token);
		}
		if(checkToken){
	        try {
	            root = FXMLLoader.load(ClientMain.class.getResource(screen));
	        } catch (IOException e) {
	            e.printStackTrace();
	        }
	        Stage stage= new Stage();
	        Scene scene= new Scene(root);
	        stage.setScene(scene);
	        stage.setResizable(false);
	        stage.show();
			
		}
		
		else{
			userName=token=null;
			try {
	            root = FXMLLoader.load(ClientMain.class.getResource("loginUI.fxml"));
	        } catch (IOException e) {
	            e.printStackTrace();
	        }
	        Stage stage= new Stage();
	        Scene scene= new Scene(root,600 , 600);
	        stage.setScene(scene);
	        stage.setResizable(false);
	        stage.show();
			
		}

    }
	public static void NoTokenCheck(String s){
		AnchorPane root = null;
	    try {
	    	root = FXMLLoader.load(ClientMain.class.getResource(s));
	    	} catch (IOException e) {
	    		e.printStackTrace();
	    	}
	    Stage stage= new Stage();
	    Scene scene= new Scene(root);
	    stage.setScene(scene);
	    stage.setResizable(false);
	    stage.show();
	}
	
	public static void logoutNow(){
		userName=token=null;;
    	token=null;
		AnchorPane root = null;
		try {
            root = FXMLLoader.load(ClientMain.class.getResource("loginUI.fxml"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        Stage stage= new Stage();
        Scene scene= new Scene(root,600 , 600);
        stage.setScene(scene);
        stage.setResizable(false);
        stage.show();
	}
	
	
	
	public static void main(String[] args) {
		
    	try {
    		System.out.println("client started...");
    		Registry registry=LocateRegistry.getRegistry("localhost",1111);
			asi=(AppServerInterface) registry.lookup("AppService");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		launch(args);
	}
}
