package application;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.Timer;

import org.controlsfx.control.Rating;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.stage.Stage;

public class StartPageController implements Initializable{
	@FXML
	private ComboBox<String> pickup;
	@FXML
	private ComboBox<String> dropoff;
	@FXML
	private Button newRide;
	@FXML
	private Button wallet;
	@FXML
	private Button bookCab;
	@FXML
	private Label amountDeducted;
	@FXML
	private Label driverArrivalTime;
	@FXML
	private Label tripDuration;
	@FXML
	private Label tripCost;
	@FXML
	private Label driverName;
	@FXML
	private Label driverPhoneNumber;
	@FXML
	private Label driverRating;
	@FXML
	private Label pennyless;
	@FXML
	private Label reached;
	@FXML
	private ProgressBar pBar;
	@FXML
	private Label driverHere;
	@FXML
	private CheckBox ratingGiven;
	@FXML
	private Rating rate;
	@FXML
	private Button cancelRide;
	private int driver_id1;
	double start_time = 0;
	private boolean pick_up_set = false;
	private boolean drop_off_set = false;
	private String userID;
	private boolean isCancelledRide = false;
	private boolean bookCabRunning = false;
	private boolean isUpdatableRating = false;
	
	ObservableList<String> list= FXCollections.observableArrayList("Secunderabad", "BPHC" , "Banjara Hills", "Panjagutta", "Jubilee Hills", "Hi-Tech city", "Nampally", "Golconda Fort");
	@Override
	public void initialize(URL location, ResourceBundle resources) 
	{	
		pickup.setItems(list);
		dropoff.setItems(list);
		rating_settings();
		ratingGiven.setOpacity(0);
		isUpdatableRating = false;
		cancelRide.setOpacity(0);
	}
	public void assign_data(String userID)
	{
		this.userID = userID;
	}
	// Event Listener on Button[#newRide].onAction
	@FXML
	public void newRideAction(ActionEvent e) throws IOException 
	{
		Stage primaryStage = new Stage();
		Parent root = FXMLLoader.load(getClass().getResource("HomePage.fxml"));
		Scene scene = new Scene(root);
		primaryStage.setTitle("Home Page");
		primaryStage.setScene(scene);
		primaryStage.show();
	}
	// Event Listener on Button[#wallet].onAction
	@FXML
	public void walletAction(ActionEvent e) throws IOException
	{
		Stage primaryStage = new Stage();
		Parent root = FXMLLoader.load(getClass().getResource("WalletPageLogin.fxml"));
		Scene scene = new Scene(root);
		primaryStage.setTitle("Wallet Page Login");
		primaryStage.setScene(scene);
		primaryStage.show();
	}
	// Event Listener on Button[#bookCab].onAction
    @FXML
	public void bookCabAction(ActionEvent e) throws Exception
	{
    	isCancelledRide = false;
		rating_settings();
		ratingGiven.setOpacity(0);
		isUpdatableRating = false;
		ratingGiven.setSelected(false);
		amountDeducted.setText("");
		
		CustomerDatabase obj = new CustomerDatabase();
		Travel ob = new Travel();
		if(pick_up_set && drop_off_set)
		{
			int[] some_stuff = obj.closest_driver(pickup.getValue());
			int driver_id = some_stuff[0];
			driver_id1 = driver_id;
			int driver_dist = some_stuff[1];
			int destination_dist = ob.dist_bw_loc(pickup.getValue(), dropoff.getValue());
			reached.setText("");
			
			pBar.setProgress(0);
			if(obj.wallet_amt(userID) >= 300 && obj.wallet_amt(userID) >= 4 * destination_dist)
			{
				if(!obj.is_riding(userID))
				{
					obj.set_riding(userID, 1);
					if(driver_id==0)
					{
						pennyless.setText("Request Timed Out");
						reset_details();
					}
					else
					{
						cancelRide.setOpacity(1);
						bookCabRunning = true;
					    String[] details = obj.get_driver_details(driver_id);
					    driverArrivalTime.setText(Integer.toString(2 * driver_dist));
					    tripDuration.setText(Integer.toString(2 * destination_dist));
					    tripCost.setText("Rs." + Integer.toString(4 * destination_dist));
					    driverName.setText(details[0]);
					    driverPhoneNumber.setText(details[1]);
					    driverRating.setText(details[2]);
						pennyless.setText("");
						obj.driver_busy_ness(driver_id, 1);
						long delay_time = (2 * destination_dist + 2 * driver_dist) * 1000;
						Timer t1 = new java.util.Timer();
						t1.schedule(new java.util.TimerTask()
						{
						            @Override
						            public void run()
						            {
						            	try
						            	{
						            		if(!isCancelledRide)
						            		{
							            		obj.driver_busy_ness(driver_id, 0);
							            		obj.driver_relocate(driver_id, dropoff.getValue());
							            		obj.deduct_user_money(userID, 4 * destination_dist);
							            		obj.set_riding(userID, 0);
							            		obj.update_rides(driver_id);
							            		/*startpageController ob = new startpageController();
							            		ob.reset_details();*/
							            		
							            		Platform.runLater(new Runnable() {
							            		    @Override public void run() {
							            		        reset_details();
							            		        pennyless.setText("");
							            		        reached.setText("You have arrived at your destination" );
							            		        cancelRide.setOpacity(0);
							            		        rate.setOpacity(1);
							            		        ratingGiven.setOpacity(1);
							            		        isUpdatableRating = true;
							            		        bookCabRunning = false;
							            		        
							            		    }
							            		});
						            		}
						            		else
						            		{
						            			obj.driver_busy_ness(driver_id, 0);
						            			obj.set_riding(userID, 0);
						            			
						            			Platform.runLater(new Runnable() {
							            		    @Override public void run() {
							            		        reset_details();
							            		        pennyless.setText("");
							            		        reached.setText("You have cancelled your ride" );
							            		        rate.setOpacity(1);
							            		        ratingGiven.setOpacity(1);
							            		        isUpdatableRating = true;
							            		        bookCabRunning = false;
							            		        cancelRide.setOpacity(0);
							            		        t1.cancel();
							            		        t1.purge();
							            		    }
							            		});
						            		}
						            		
						            	}
						            	catch(Exception e)
						            	{
						            		System.out.println(e);
						            	}
						            }
						}, delay_time);
						new java.util.Timer().schedule(new java.util.TimerTask() 
						{
						            @Override
						            public void run() 
						            {
						            	Platform.runLater(new Runnable() 
						            	{
					            		    @Override 
					            		    public void run() 
					            		    {
					            		    	if(!isCancelledRide)
					            		    	{
						            		    	driverHere.setText("Driver Arrived");
						            		    	start_time = System.currentTimeMillis() * 0.001d;
					            		    	}
					            		    }
						            	});
						            	Timer t = new java.util.Timer();
						            	t.schedule(new java.util.TimerTask() 
						            	{
										            @Override
										            public void run() 
										            {
										            	Platform.runLater(new Runnable() 
										            	{
									            		    @Override 
									            		    public void run() 
									            		    {
									            		    	if(!isCancelledRide)
									            		    	{
										            		    	double elapsed_time = System.currentTimeMillis() * 0.001d - start_time;
										            		    	double delay = (2 * destination_dist);
										            		        pBar.setProgress(elapsed_time/delay);
										            		        if(elapsed_time/delay > 1)
										            		        {
										            		        	amountDeducted.setText("Amount Deducted is : Rs." + Integer.toString(4 * destination_dist));
										            		        	t.cancel();
										            		        	t.purge();
										            		        }
									            		    	}
									            		    	else
									            		    	{
									            		    		try
									            		    		{
										            		    		CustomerDatabase obj = new CustomerDatabase();
										            		    		obj.driver_busy_ness(driver_id, 0);
												            			obj.set_riding(userID, 0);
										            		    		t.cancel();
										            		        	t.purge();
										            		        	t1.cancel();
										            		        	t1.purge();
										            		        	bookCabRunning = false;
										            		        	cancelRide.setOpacity(0);
										            		        	reset_details();
											            		        pennyless.setText("");
											            		        reached.setText("You have cancelled your ride" );
											            		        rate.setOpacity(1);
											            		        ratingGiven.setOpacity(1);
											            		        isUpdatableRating = true;
									            		    		}
									            		    		catch(Exception e)
									            		    		{
									            		    			System.out.println(e);
									            		    		}
									            		    	}
									            		    }
									            		});
										            }
										 },  0, 300);
						            }
						   }, (2 * driver_dist) *1000);
						remove_driver_arrived_tag((2 * driver_dist + 2) *1000);
					}
				}
				else
				{
					pennyless.setText("You are already on a ride");
					//reset_details();
				}
			}
			else
			{
				pennyless.setText("Error: Money Insufficient");
				reset_details();
			}
		}
		else
		{
			pennyless.setText("Choose Pick-up and Drop-Off");
		}
		//bookCabRunning = false;
	}
	// Event Listener on ComboBox[#pickup].onAction
	@FXML
	public void pickupAction(ActionEvent e) throws Exception
	{
		pick_up_set = true;
	}
	// Event Listener on ComboBox[#dropoff].onAction
	@FXML
	public void dropoffAction(ActionEvent e) throws Exception
	{
		drop_off_set = true;
	}
	public void reset_details()
	{
		driverArrivalTime.setText("-");
		tripDuration.setText("-");
		tripCost.setText("-");
		driverName.setText("-");
		driverPhoneNumber.setText("-");
		driverRating.setText("-");
	}
	public void rating_settings()
	{
		rate.setPartialRating(true);
		rate.setRating(0);
		rate.setMax(5);
		rate.setOpacity(0);
	}
	public void remove_driver_arrived_tag(int time)
	{
		new java.util.Timer().schedule(new java.util.TimerTask() 
		{
		            @Override
		            public void run() 
		            {
		            	Platform.runLater(new Runnable() 
		            	{
	            		    @Override 
	            		    public void run() 
	            		    {
	            		    	driverHere.setText("");
	            		    }
		            	});
		            }
		}, time);
	}
	// Event Listener on CheckBox[#rating_given].onAction
	@FXML
	public void ratingGivenAction(ActionEvent e) throws Exception
	{
		if(isUpdatableRating)
		{
			CustomerDatabase obj = new CustomerDatabase();
			obj.update_driver_rating(driver_id1, rate.getRating());
		}
	}
	// Event Listener on CheckBox[#cancelRide].onAction
	@FXML
	public void cancelRideAction(ActionEvent e) throws Exception
	{
		if(bookCabRunning)
		{
			double elapsed_time = 0;
			isCancelledRide = true;
			if(!(start_time == 0))
			{
				elapsed_time = System.currentTimeMillis() * 0.001d - start_time;
			}
			CustomerDatabase obj = new CustomerDatabase();
			obj.deduct_user_money(userID, (int)(2 * elapsed_time));
			amountDeducted.setText("Amount Deducted is : Rs." + (int)(2 * elapsed_time));
			cancelRide.setOpacity(0);
		}
	}
}
