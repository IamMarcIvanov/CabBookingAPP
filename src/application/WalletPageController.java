package application;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

public class WalletPageController 
{
	@FXML
	private Button add_money;
	@FXML
	private Button remove_money;
	@FXML
	private Button show_available_bal;
	@FXML
	private TextField add_amt;
	@FXML
	private TextField remove_amt;
	@FXML
	private Label available_bal;
	@FXML
	private Label warning1;
	@FXML
	private Label warning2;
	public String temp_id;
	
	public void assign_data(String user_id)
	{
		temp_id = user_id;
	}
	public void showAvailableBalance(ActionEvent e) throws Exception
	{
		CustomerDatabase obj1 = new CustomerDatabase();
		available_bal.setText(Integer.toString(obj1.wallet_amt(temp_id)));
	}
	public void add_money(ActionEvent e) throws Exception
	{
		boolean check_working = false;
		try
		{
			Integer.parseInt(add_amt.getText());
			check_working = true;
		}
		catch(NumberFormatException exp)
		{
			warning1.setText("Enter positive integer amount");
		}
		if(check_working && Integer.parseInt(add_amt.getText()) > 0)
		{
			warning1.setText("");
			int amt = Integer.parseInt(add_amt.getText());
			CustomerDatabase obj1 = new CustomerDatabase();
			obj1.add_money(amt, temp_id);
			
			available_bal.setText(Integer.toString(obj1.wallet_amt(temp_id)));
		}
		else
		{
			warning1.setText("Enter positive integer amount");
		}
	}
	public void remove_money(ActionEvent event) throws Exception
	{
		CustomerDatabase obj1 = new CustomerDatabase();
		boolean check_working = false;
		try
		{
			Integer.parseInt(remove_amt.getText());
			check_working = true;
		}
		catch(NumberFormatException exp)
		{
			warning2.setText("Enter positive integer amount");
		}
		if(check_working && Integer.parseInt(remove_amt.getText()) > 0)
		{
			if(Integer.parseInt(remove_amt.getText()) <= obj1.wallet_amt(temp_id))
			{
				warning2.setText("");
				int amt = Integer.parseInt(remove_amt.getText());
				obj1.add_money(-1 * amt, temp_id);
				
				available_bal.setText(Integer.toString(obj1.wallet_amt(temp_id)));
			}
			else
			{
				warning2.setText("Error: deduct request exceeds balance");
			}
		}
		else
		{
			warning2.setText("Enter positive integer amount");
		}
	}
}
