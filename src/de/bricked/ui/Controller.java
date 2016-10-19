package de.bricked.ui;

import java.io.IOException;
import java.util.Locale;
import java.util.ResourceBundle;

import de.bricked.commandLine.CommandLine;
import de.bricked.commandLine.commands.CommandBundle;
import de.bricked.game.Game;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import logger.LogLevel;
import logger.Logger;

public class Controller
{
	@FXML private AnchorPane mainPane;
	@FXML private Button buttonLevelSelect;
	@FXML private Button buttonSettings;
	@FXML private Button buttonAchievements;

	public Stage stage;
	public Image icon = new Image("de/bricked/resources/icon.png");
	public final ResourceBundle bundle = ResourceBundle.getBundle("de/bricked/main/", Locale.GERMANY);
	private Game game;
	private CommandLine cmd;

	public void init(Stage stage)
	{
		this.stage = stage;		
		game = new Game();
		
		cmd = new CommandLine(null, icon, ResourceBundle.getBundle("de/bricked/commandLine/", Locale.ENGLISH), new CommandBundle(game));
		
		mainPane.setOnKeyReleased(new EventHandler<KeyEvent>()
		{
			@Override
			public void handle(KeyEvent event)
			{				
				if(event.getCode().toString().equals(bundle.getObject("shortcut.debug.console")))				
				{					
					showCommandLine();
					Logger.log(LogLevel.INFO, "openend debug console");
					event.consume();
				}				
			}
		});
		
		Logger.log(LogLevel.INFO, "successfully started");
	}	
	
	public void showLevelSelect()
	{
		try
		{
			FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/de/bricked/ui/LevelSelectGUI.fxml"));

			Parent root = (Parent)fxmlLoader.load();
			Stage newStage = new Stage();
			newStage.setScene(new Scene(root, 650, 800));
			newStage.setTitle("Level Select");
			newStage.initOwner(stage);

			newStage.getIcons().add(icon);			
			LevelSelectController newController = fxmlLoader.getController();
			newController.init(newStage, this, game);
			cmd.getBundle().setLevelSelectController(newController);

			newStage.initModality(Modality.NONE);
			newStage.setResizable(false);
			stage.hide();
			newStage.show();
		}
		catch(IOException e1)
		{
			e1.printStackTrace();
		}
	}
	
	public void showSettings()
	{

	}
	
	public void showAchievements()
	{

	}	
	
	public void showCommandLine()
	{		
		try
		{				
	        cmd.showCommandLine("Debug Console", 400, 250, 400, 200, -1, -1, true);
		}
		catch(IOException e)
		{
	        //TODO: errorhandling
	       Logger.log(LogLevel.ERROR, Logger.exceptionToString(e));
		}
	}
	
	public CommandLine getCommandLine()
	{
		return cmd;
	}

	public void about()
	{
		Alert alert = new Alert(AlertType.INFORMATION);
		alert.setTitle("Über " + bundle.getString("app.name"));
		alert.setHeaderText(bundle.getString("app.name"));
		alert.setContentText("Version:     " + bundle.getString("version.name") + "\r\nDatum:      " + bundle.getString("version.date") + "\r\nAutor:        Robert Goldmann\r\n");
		Stage dialogStage = (Stage)alert.getDialogPane().getScene().getWindow();
		dialogStage.getIcons().add(icon);
		dialogStage.centerOnScreen();
		alert.showAndWait();
	}
}