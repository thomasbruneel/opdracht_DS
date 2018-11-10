package client;
	
import java.io.IOException;
import java.rmi.NotBoundException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

import interfaces.AppServerInterface;
import interfaces.DatabankServerInterface;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;


public class Main extends Application {
	
	public static AppServerInterface asi;
	
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
	
	public static void openRegistratieUI(){
		AnchorPane root = null;
        try {
            root = FXMLLoader.load(Main.class.getResource("registratieUI.fxml"));
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
            root = FXMLLoader.load(Main.class.getResource("loginUI.fxml"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        Stage stage= new Stage();
        Scene scene= new Scene(root,600 , 600);
        stage.setScene(scene);
        stage.setResizable(false);
        stage.show();
    }
	
	
	public static void openMenuUI(){
		AnchorPane root = null;
        try {
            root = FXMLLoader.load(Main.class.getResource("menuUI.fxml"));
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
    		Registry registry=LocateRegistry.getRegistry("localhost",1111);
			asi=(AppServerInterface) registry.lookup("AppService");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		launch(args);
	}
}
