package de.bricked.ui;

import java.io.IOException;
import java.util.Locale;
import java.util.ResourceBundle;

import de.bricked.game.Game;
import de.bricked.game.levels.Level;
import de.bricked.ui.cells.LevelCell;
import de.bricked.utils.Colors;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ScrollPane.ScrollBarPolicy;
import javafx.scene.image.Image;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import javafx.util.Callback;
import kuusisto.tinysound.TinySound;
import logger.Logger;
import tools.AlertGenerator;

public class LevelSelectController implements CommandLineAvailabale
{
	@FXML private AnchorPane mainPane;
	@FXML private ScrollPane pane;
	@FXML private Label labelLevelPack;
	@FXML private Button buttonBack; 

	public Stage stage;
	public Image icon = new Image("de/bricked/resources/icon.png");
	public final ResourceBundle bundle = ResourceBundle.getBundle("de/bricked/main/", Locale.GERMANY);
	public LevelPackSelectController controller;
	public Game game;

	public void init(Stage stage, LevelPackSelectController controller, Game game)
	{
		this.stage = stage;
		this.controller = controller;
		this.game = game;

		labelLevelPack.setText("Levelpack: " + game.getLevelPack().getPackageName());

		reload();

		pane.setHbarPolicy(ScrollBarPolicy.NEVER);
		pane.setVbarPolicy(ScrollBarPolicy.AS_NEEDED);
		pane.setStyle("-fx-background-color:transparent;");

		mainPane.setOnKeyReleased(new EventHandler<KeyEvent>()
		{
			@Override
			public void handle(KeyEvent event)
			{
				if(event.getCode().toString().equals(controller.bundle.getObject("shortcut.debug.console")))
				{
					showCommandLine();
					Logger.info("openend debug console");
					event.consume();
				}

				if(event.getCode().toString().equals("ESCAPE"))
				{
					back();
					event.consume();
				}
			}
		});

		stage.setOnCloseRequest(new EventHandler<WindowEvent>()
		{
			@Override
			public void handle(WindowEvent event)
			{
				// TODO achievementhandling

				TinySound.shutdown();
				Platform.exit();
				System.exit(0);
			}
		});		
		
		mainPane.setStyle("-fx-base: " + Colors.BACKGROUND);
		buttonBack.setStyle("-fx-base: " + Colors.BUTTON);
	}

	public void reload()
	{
		CustomListView<Level> listView = new CustomListView<Level>(FXCollections.observableList(game.getLevelPack().getLevels()));

		listView.setCellFactory(new Callback<ListView<Level>, ListCell<Level>>()
		{
			@Override
			public ListCell<Level> call(ListView<Level> param)
			{
				return new LevelCell(pane.getMaxWidth() - 8);
			}
		});
		listView.setStyle("-fx-background-color: transparent");

		listView.setHScrollBarEnabled(false);

		listView.prefWidthProperty().bind(pane.maxWidthProperty());
		listView.prefHeightProperty().bind(pane.maxHeightProperty().subtract(10));

		listView.setOnMouseClicked(new EventHandler<MouseEvent>()
		{
			@Override
			public void handle(MouseEvent event)
			{
				Level selectedLevel = listView.getSelectionModel().getSelectedItem();				
				if(selectedLevel != null)
				{
					listView.getSelectionModel().clearSelection();
					game.setLevel(selectedLevel);

					startLevel();
				}
			}
		});

		pane.setContent(listView);
	}

	private LevelSelectController getController()
	{
		return this;
	}
	
	public void startLevel()
	{
		try
		{
			FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/de/bricked/ui/LevelGUI.fxml"));

			Parent root = (Parent)fxmlLoader.load();
			Stage newStage = new Stage();		
			newStage.setScene(new Scene(root, game.getSettings().getGameSize().getWidth(), game.getSettings().getGameSize().getHeight()));
			newStage.setTitle(bundle.getString("app.name") + " - " + game.getLevel().getName());
			newStage.initOwner(stage);

			newStage.getIcons().add(icon);
			LevelController newController = fxmlLoader.getController();
			newController.init(newStage, getController(), game);
			
			controller.controller.getCommandLine().getBundle().setLevelController(newController);

			newStage.initModality(Modality.NONE);
			newStage.setResizable(false);
			stage.hide();
			newStage.show();
		}
		catch(IOException e1)
		{
			Logger.error(e1);
		}
	}

	public void back()
	{
		stage.close();
		controller.stage.show();
	}

	@Override
	public void showCommandLine()
	{
		try
		{
			controller.controller.getCommandLine().showCommandLine("Debug Console", 400, 250, 400, 200, - 1, - 1, true);
		}
		catch(IOException e)
		{
			AlertGenerator.showAlert(AlertType.ERROR, "Error", "", "An error occurred while opening the debug console.\n\nDetails:\n" + e.getMessage(), icon, stage, null, false);
			Logger.error(e);
		}
	}
}