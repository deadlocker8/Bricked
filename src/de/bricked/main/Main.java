package de.bricked.main;

import java.util.Arrays;
import java.util.Locale;
import java.util.ResourceBundle;

import de.bricked.ui.Controller;
import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import logger.LogLevel;
import logger.Logger;
import tools.Worker;

public class Main extends Application
{
	public static final ResourceBundle bundle = ResourceBundle.getBundle("de/bricked/main/", Locale.GERMANY);
	
	@Override
	public void start(Stage stage)
	{
		try
		{
			FXMLLoader loader = new FXMLLoader(getClass().getClassLoader().getResource("de/bricked/ui/GUI.fxml"));
			Parent root = (Parent)loader.load();

			Scene scene = new Scene(root, 800, 800);		

			((Controller)loader.getController()).init(stage);
			
			stage.getIcons().add(new Image("/de/bricked/resources/icon.png"));
			stage.setTitle(bundle.getString("app.name"));
			stage.setScene(scene);
			stage.setResizable(true);

			stage.setOnCloseRequest(new EventHandler<WindowEvent>()
			{
				public void handle(WindowEvent we)
				{
					Worker.shutdown();
					System.exit(0);
				}
			});

			stage.show();
		}
		catch(Exception e)
		{
			Logger.log(LogLevel.ERROR, Logger.exceptionToString(e));
		}
	}

	public static void main(String[] args)
	{
		if(Arrays.asList(args).contains("debug"))
		{
			Logger.setLevel(LogLevel.ALL);
			Logger.log(LogLevel.INFO, "Running in Debug Mode");
			Logger.log(LogLevel.INFO, bundle.getString("app.name") + " - v" + bundle.getString("version.name") + " - (versioncode: " + bundle.getString("version.code") + ") from " + bundle.getString("version.date"));
		}
		else
		{
			Logger.setLevel(LogLevel.ERROR);
		}	

		launch(args);
	}
}