package application;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

public class HomePageController 
{
	@FXML
	private Button prevUser;
	@FXML
	private Button newUser;
	
	// Event Listener on Button[#newUser].onAction
	@FXML
	public void newUserAction(ActionEvent event) throws Exception
	{
		((Node)event.getSource()).getScene().getWindow().hide();
		Stage primaryStage = new Stage();
		Parent root = FXMLLoader.load(getClass().getResource("NewUserForm.fxml"));
		Scene scene = new Scene(root);
		primaryStage.setTitle("New User Form");
		primaryStage.setScene(scene);
		primaryStage.show();
	}
	// Event Listener on Button[#prevUser].onAction
	@FXML
	public void prevUserAction(ActionEvent event) throws Exception
	{
		((Node)event.getSource()).getScene().getWindow().hide();
		Stage primaryStage = new Stage();
		Parent root = FXMLLoader.load(getClass().getResource("LoginPage.fxml"));
		Scene scene = new Scene(root);
		primaryStage.setTitle("Login Page");
		primaryStage.setScene(scene);
		primaryStage.show();
	}
}
