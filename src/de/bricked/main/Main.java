package de.bricked.main;

import java.io.File;
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
import logger.FileOutputMode;
import logger.Logger;
import tools.PathUtils;
import tools.Worker;

public class Main extends Application
{
	public static final ResourceBundle bundle = ResourceBundle.getBundle("de/bricked/main/", Locale.GERMANY);
	
	@Override
	public void start(Stage stage)
	{
		try
		{
			FXMLLoader loader = new FXMLLoader(getClass().getClassLoader().getResource("de/bricked/ui/MainGUI.fxml"));
			Parent root = (Parent)loader.load();

			Scene scene = new Scene(root, 650, 800);			

			((Controller)loader.getController()).init(stage);
			
			stage.getIcons().add(new Image("/de/bricked/resources/icon.png"));
			stage.setTitle(bundle.getString("app.name"));
			stage.setScene(scene);
			stage.setResizable(false);
			
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
			Logger.error(e);
		}
	}
	
	@Override
	public void init() throws Exception
	{	
		Parameters params = getParameters();
		String logLevelParam = params.getNamed().get("loglevel");		
		Logger.setLevel(logLevelParam);	
		
		File logFolder = new File(PathUtils.getOSindependentPath() + "deadspaghetti/" + bundle.getString("app.name"));			
		PathUtils.checkFolder(logFolder);
		Logger.enableFileOutput(logFolder, System.out, System.err, FileOutputMode.COMBINED);
		
		Logger.appInfo(bundle.getString("app.name"), bundle.getString("version.name"), bundle.getString("version.code"), bundle.getString("version.date"));
	}

	public static void main(String[] args)
	{
		launch(args);
	}
}