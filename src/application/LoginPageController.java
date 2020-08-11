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

public class LoginPageController {
	@FXML
	private PasswordField password;
	@FXML
	private TextField userID;
	@FXML
	private Button login;
	@FXML
	private Label warningLabel;

	// Event Listener on Button[#login].onAction
	@FXML
	public void loginAction(ActionEvent e) throws Exception
	{
		if(login_is_filled())
		{
			CustomerDatabase obj = new CustomerDatabase();
			String passwd = obj.return_passwd(userID.getText());
			if(passwd.equals(password.getText()))
			{
				if(!obj.is_logged_in(userID.getText()))
				{
					FXMLLoader loader = new FXMLLoader(getClass().getResource("StartPage.fxml"));
					Parent root = loader.load();
					StartPageController controller = loader.getController();
					controller.assign_data(userID.getText());
					((Node)e.getSource()).getScene().getWindow().hide();
					Stage primaryStage = new Stage();
					
					/*primaryStage.setOnCloseRequest(new EventHandler<WindowEvent>() {
					    @Override
					    public void handle(WindowEvent t) {
					        Platform.exit();
					        //System.exit(0);
					    }
					});*/
					
					Scene scene = new Scene(root);
					primaryStage.setTitle("Start Page");
					primaryStage.setScene(scene);
					primaryStage.show();
					
					obj.update_log_in(userID.getText());
				}
				else
				{
					warningLabel.setText("Already Logged in");
				}
				
			}
			else
			{
				warningLabel.setText("Wrong UserID/Password");
			}
		}
	}
	public boolean login_is_filled()
	{
		if(!(password.getText().length() > 0))
		{
			warningLabel.setText("Fill UserID");
			return false;
		}
		if(!(password.getText().length() > 0))
		{
			warningLabel.setText("Fill Password");
			return false;
		}
		return true;
	}
}
