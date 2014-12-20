package controller;

import operations.SavedFiles;
import operations.TwitterSetup;

import java.awt.Desktop;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;

import twitter4j.TwitterException;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.application.Application;
import javafx.beans.binding.Bindings;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.stage.Stage;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;


public class Login extends Application {
	@Override
	public void start(Stage primaryStage) {
		TwitterSetup.setup();
		try {
			VBox root = new VBox();
			root.setAlignment(Pos.CENTER);
			root.setSpacing(30.0);
			
			ImageView logo = new ImageView(new Image(getClass().getResourceAsStream("/res/logo.png")));
	        logo.setFitWidth(150);
	        logo.setPreserveRatio(true);
	        logo.setSmooth(true);
	        logo.setCache(true);
	     
			Button button = new Button("Start");
			root.getChildren().add(logo);
			root.getChildren().add(button);

			// If no AccessToken is saved
			if (!TwitterSetup.hasAccessToken()) {
				button.setText("Sign in with Twitter");
		        button.setOnAction(new EventHandler<ActionEvent>() {
		            @Override public void handle(ActionEvent e) {
						try {
			            	URL url = new URL(TwitterSetup.getRequestToken().getAuthorizationURL()); //Some instantiated URL object
			            	URI uri = url.toURI();
		            		openWebpage(uri);
		            		login(primaryStage);
						} catch (Exception e1) {
							System.out.println("URL Error");
						}
		            }
		        });
			} else {
				button.setOnAction(new EventHandler<ActionEvent>() {
					public void handle(ActionEvent e) {
						menu(primaryStage);
					}
				});
			}

			Scene scene = new Scene(root,600,400);
			scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
			primaryStage.setScene(scene);
			primaryStage.show();
			
		} catch(Exception e) {
			e.printStackTrace();
		}
		
	}
	
	// Login view
	public void login(Stage primaryStage) {
		VBox root = new VBox();
		HBox buttonRow = new HBox();
		root.setAlignment(Pos.CENTER);
		buttonRow.setAlignment(Pos.CENTER);
		root.setSpacing(30.0);
		buttonRow.setSpacing(10.0);
		
		
		Label enterPinLabel = new Label("Enter Pin");
		TextField pinInput = new TextField();
		pinInput.setAlignment(Pos.CENTER);
		root.getChildren().add(enterPinLabel);
		root.getChildren().add(pinInput);
		
		Button auth = new Button("Authorize");
		Button newPin = new Button("Regenerate Pin");
		buttonRow.getChildren().add(auth);
		buttonRow.getChildren().add(newPin);
		root.getChildren().add(buttonRow);

		
        newPin.setOnAction(new EventHandler<ActionEvent>() {
            @Override public void handle(ActionEvent e) {
				try {
	            	URL url = new URL(TwitterSetup.getRequestToken().getAuthorizationURL()); //Some instantiated URL object
	            	URI uri = url.toURI();
            		openWebpage(uri);
				} catch (Exception e1) {
					System.out.println("URL Error");
				}
            }
        });
        
        auth.disableProperty().bind(Bindings.isEmpty(pinInput.textProperty()));
        
        auth.setOnAction(new EventHandler<ActionEvent>() {
        	public void handle(ActionEvent e) {
        		String pin = pinInput.getText();
        	
        		try {
        			TwitterSetup.getToken(pin);
        			System.out.println("Success!");
        			menu(primaryStage);
        		} catch (TwitterException te) {
        			if (401 == te.getStatusCode()) {
        				System.out.println("Unable to get the access token.");
        				Label errorLabel = new Label("Check pin");
        				root.getChildren().add(errorLabel);
        			} else {
        				te.printStackTrace();
        			}
        		}
				
        	}
        });
		
		Scene scene = new Scene(root, 600, 400);
		primaryStage.setScene(scene);
		primaryStage.show();
	}
	
	
	
	public void menu(Stage primaryStage) {
		Main main = new Main();
		main.start(primaryStage);
	}
	
	// Opens webpage for OAuth
    public static void openWebpage(URI string) {
        Desktop desktop = Desktop.isDesktopSupported() ? Desktop.getDesktop() : null;
        if (desktop != null && desktop.isSupported(Desktop.Action.BROWSE)) {
            try {
                desktop.browse(string);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    // Converts url to URI and open webpage
    public static void openWebpage(URL url) {
        try {
            openWebpage(url.toURI());
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
    }
	
	public static void main(String[] args) {
		launch(args);
	}
}
