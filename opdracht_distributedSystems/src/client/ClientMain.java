package client;
	
import static client.ClientMain.token;

import java.io.IOException;
import java.rmi.NotBoundException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.Objects;

import applicationServer.TokenGenerator;
import interfaces.AppServerInterface;
import interfaces.DatabankServerInterface;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;


public class ClientMain extends Application {
	
	public static AppServerInterface asi;
	public static String token;
	public static String userName;

    private Parent createGame() {
        AnchorPane root = null;
        try {
            root =(AnchorPane)FXMLLoader.load(getClass().getResource("gameUI.fxml"));
            Pane gamePane = (Pane)root.lookup("gamePane");
            Text text = new Text();
            text.setText("TestText");
            gamePane.getChildren().add(text);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return root;
    }

	
	public static int gameId;
	
	@Override
	public void start(Stage primaryStage) {
		try {
			Parent root = createGame();

                    //

			Scene scene = new Scene(root);
			primaryStage.setScene(scene);
			primaryStage.setResizable(false);
			primaryStage.show();
		} catch(Exception e) {
			e.printStackTrace();
		}
	}


    public static void openRegistratieUI(){
		AnchorPane root = null;
        try {
            root = FXMLLoader.load(ClientMain.class.getResource("registratieUI.fxml"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        Stage stage= new Stage();
        Scene scene= new Scene(root,600 , 600);
        stage.setScene(scene);
        stage.setResizable(false);
        stage.show();
    }
	
	public static void openLoginUI(){
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
	
	

	public static void openUIScreen(String screen){
		AnchorPane root = null;
        try {
            root = FXMLLoader.load(ClientMain.class.getResource("lobbyUI.fxml"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        Stage stage= new Stage();
        Scene scene= new Scene(root,600 , 600);
        stage.setScene(scene);
        stage.setResizable(false);
        stage.show();
		boolean checkToken=TokenGenerator.CheckExpiration(token);
		if(checkToken){
	        try {
	            root = FXMLLoader.load(ClientMain.class.getResource(screen));
	        } catch (IOException e) {
	            e.printStackTrace();
	        }
	        Stage stage= new Stage();
	        Scene scene= new Scene(root,600 , 600);
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
	
	public static void openNewGameUI(){
		AnchorPane root = null;
        try {
            root = FXMLLoader.load(ClientMain.class.getResource("newGameUI.fxml"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        Stage stage= new Stage();
        Scene scene= new Scene(root,600 , 600);
        stage.setScene(scene);
        stage.setResizable(false);
        stage.show();
    }
	
	public static void openGameUI(){
		AnchorPane root = null;
        try {
            root = FXMLLoader.load(ClientMain.class.getResource("gameUI.fxml"));
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
