package application;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;

import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.event.ActionEvent;

import javafx.scene.control.Label;

import javafx.scene.control.PasswordField;

public class NewUserFormController 
{
	@FXML
	private TextField userName;
	@FXML
	private TextField userID;
	@FXML
	private TextField phoneNumber;
	@FXML
	private TextField emailID;
	@FXML
	private PasswordField password;
	@FXML
	private Button register;
	@FXML
	private PasswordField walletPin;
	@FXML
	private Label unfilled;

	// Event Listener on Button[#register].onAction
	@FXML
	public void registerAction(ActionEvent e) throws Exception
	{
		CustomerDatabase obj = new CustomerDatabase();
		if(importantFieldsFilled())
		{
			if(obj.is_new_user(userID.getText()))
			{
				obj.new_user_entry(userName.getText(), userID.getText(), emailID.getText(), phoneNumber.getText(), password.getText(), walletPin.getText());
				
				((Node)e.getSource()).getScene().getWindow().hide();
				Stage primaryStage = new Stage();
				Parent root = FXMLLoader.load(getClass().getResource("LoginPage.fxml"));
				Scene scene = new Scene(root);
				primaryStage.setTitle("Login Page");
				primaryStage.setScene(scene);
				primaryStage.show();
			}
			else
			{
				unfilled.setText("UserID already exists");
			}
		}
	}
	public boolean importantFieldsFilled()
	{
		if(!(userName.getText().length() > 0))
		{
			unfilled.setText("Fill User Name");
			return false;
		}
		if(userName.getText().length() > 20)
		{
			unfilled.setText("User Name atmost 20 characters");
			return false;
		}
		
		
		
		if(!(userID.getText().length() > 0))
		{
			unfilled.setText("Fill User ID");
			return false;
		}
		
		if(userID.getText().length() > 20)
		{
			unfilled.setText("User ID atmost 20 characters");
			return false;
		}
		
		
		
		if(!(phoneNumber.getText().length() > 0))
		{
			unfilled.setText("Fill Phone Number");
			return false;
		}
		if(!(phoneNumber.getText().length() == 10))
		{
			unfilled.setText("Phone Number must be 10 digits only");
			return false;
		}
		
		
		
		if(!(password.getText().length() > 0))
		{
			unfilled.setText("Fill Password");
			return false;
		}
		
		if(!(password.getText().length() > 0))
		{
			unfilled.setText("Fill Password");
			return false;
		}
		if(password.getText().length() > 20)
		{
			unfilled.setText("Password length limit exceeded(20)");
			return false;
		}
		
		
		if(!(walletPin.getText().length() > 0))
		{
			unfilled.setText("Fill 4 Digit Wallet Pin");
			return false;
		}
		if(walletPin.getText().length() > 4)
		{
			unfilled.setText("Wallet Pin must be of 4 digits only");
			return false;
		}
		return true;
	}
}
