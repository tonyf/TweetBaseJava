package controller;


import java.io.File;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import operations.SavedFiles;
import operations.StoredFile;
import operations.TwitterPoster;
import operations.TwitterSetup;
import operations.UploadFile;


public class Main extends Application {
	File uploadFile;
	@Override
	public void start(Stage primaryStage) {
		try {
			TabPane root = new TabPane();
			root.setTabClosingPolicy(TabPane.TabClosingPolicy.UNAVAILABLE);
			Tab uploadTab = new Tab("Upload");
			Tab downloadTab = new Tab("Download");
			
			VBox uploadV = new VBox();
			uploadV.setSpacing(5.0);
			Label uploadLabel = new Label("Upload");
			uploadV.getChildren().add(uploadLabel);
			
			HBox fileSelecting = new HBox();
			fileSelecting.setAlignment(Pos.CENTER);
			TextField path = new TextField();
			path.setDisable(true);
			Button browse = new Button("Browse");
			fileSelecting.getChildren().add(path);
			fileSelecting.getChildren().add(browse);
			uploadV.getChildren().add(fileSelecting);
			uploadV.setAlignment(Pos.CENTER);
			uploadTab.setContent(uploadV);
			
			Button uploadButton = new Button("Upload");
			uploadButton.setDisable(true);
			uploadV.getChildren().add(uploadButton);
			
			browse.setOnAction(new EventHandler<ActionEvent>() {
	            @Override public void handle(ActionEvent e) {
	    			FileChooser fileChooser = new FileChooser();
	   			 	fileChooser.setTitle("Open Resource File");
	   			 	File selectedFile = fileChooser.showOpenDialog(primaryStage);
	   			 	setFile(selectedFile);
				 if (selectedFile != null) {
					    path.setText(selectedFile.getName());
					    uploadButton.setDisable(false);
					 }
	            }
			});
			
			uploadButton.setOnAction(new EventHandler<ActionEvent>() {
				@Override public void handle(ActionEvent e) {
					UploadFile upload = new UploadFile(uploadFile);
					upload.post();
				}
			});
			
			VBox downloadV = new VBox();
			Button downloadButton = new Button("Download");
			downloadV.getChildren().add(downloadButton);
			downloadV.setAlignment(Pos.CENTER);
			downloadTab.setContent(downloadV);
			
			downloadButton.setOnAction(new EventHandler<ActionEvent>() {
				@Override public void handle(ActionEvent e) {
					SavedFiles.launch();
				}
			});
			
			
			
			root.getTabs().add(uploadTab);
			root.getTabs().add(downloadTab);
			Scene scene = new Scene(root,600,400);
			scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
			primaryStage.setScene(scene);
			primaryStage.show();
			
		} catch(Exception e) {
			e.printStackTrace();
		}
		
	}
	
	public void uploading(Stage primaryStage) {
		VBox root = new VBox();
		
	}

	private void setFile(File selectedFile) {
		uploadFile = selectedFile;
	}
	
//	public static void main(String[] args) {
//		launch(args);
//	}
	
}
