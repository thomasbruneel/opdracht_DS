package dispatcher;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
public class DispatcherMain extends Application {
		@Override
		public void start(Stage primaryStage) {
			try {

				//hier een nullpointer?
				AnchorPane root = (AnchorPane)FXMLLoader.load(getClass().getResource("dispatcherUI.fxml"));
				Scene scene = new Scene(root,1074,769);
				primaryStage.setScene(scene);
				primaryStage.setResizable(false);
				primaryStage.show();
			} catch(Exception e) {
				e.printStackTrace();
			}
		}
		
		public static void main(String[] args) {
			launch(args);
		}
		
	}
	
