package de.bricked.ui;

import java.io.IOException;
import java.util.Locale;
import java.util.ResourceBundle;

import de.bricked.game.Game;
import de.bricked.game.levels.LevelPack;
import de.bricked.game.levels.LevelPackHandler;
import de.bricked.ui.cells.LevelPackCell;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.ScrollPane.ScrollBarPolicy;
import javafx.scene.image.Image;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import javafx.util.Callback;
import logger.LogLevel;
import logger.Logger;

public class LevelPackSelectController
{
	@FXML private AnchorPane mainPane;
	@FXML private ScrollPane pane;
	@FXML private Label labelLevelPack;

	public Stage stage;
	public Image icon = new Image("de/bricked/resources/icon.png");
	public final ResourceBundle bundle = ResourceBundle.getBundle("de/bricked/main/", Locale.GERMANY);
	public Controller controller;
	public Game game;

	public void init(Stage stage, Controller controller, Game game)
	{
		this.stage = stage;
		this.controller = controller;
		this.game = game;

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
					Logger.log(LogLevel.INFO, "openend debug console");
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

				Platform.exit();
			}
		});
	}

	public void reload()
	{
		CustomListView<LevelPack> listView = new CustomListView<LevelPack>(FXCollections.observableList(LevelPackHandler.getAllLevelPacks()));

		listView.setCellFactory(new Callback<ListView<LevelPack>, ListCell<LevelPack>>()
		{
			@Override
			public ListCell<LevelPack> call(ListView<LevelPack> param)
			{
				return new LevelPackCell(pane.getMaxWidth() - 14);
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
				LevelPack selectedPack = listView.getSelectionModel().getSelectedItem();			
				if(selectedPack != null)
				{
					listView.getSelectionModel().clearSelection();
					game.setLevelPack(selectedPack);

					try
					{
						FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/de/bricked/ui/LevelSelectGUI.fxml"));

						Parent root = (Parent)fxmlLoader.load();
						Stage newStage = new Stage();
						newStage.setScene(new Scene(root, 650, 800));
						newStage.getScene().getStylesheets().add("de/bricked/ui/style.css");
						newStage.setTitle("Level Select");
						newStage.initOwner(stage);

						newStage.getIcons().add(icon);
						LevelSelectController newController = fxmlLoader.getController();
						newController.init(newStage, getController(), game);

						newStage.initModality(Modality.NONE);
						newStage.setResizable(false);
						stage.hide();
						newStage.show();
					}
					catch(IOException e1)
					{
						Logger.log(LogLevel.ERROR, Logger.exceptionToString(e1));
					}
				}
			}
		});

		pane.setContent(listView);
	}

	private LevelPackSelectController getController()
	{
		return this;
	}

	public void back()
	{
		stage.close();
		controller.stage.show();
	}

	public void showCommandLine()
	{
		try
		{
			controller.getCommandLine().showCommandLine("Debug Console", 400, 250, 400, 200, - 1, - 1, true);
		}
		catch(IOException e)
		{
			// TODO: errorhandling
			Logger.log(LogLevel.ERROR, Logger.exceptionToString(e));
		}
	}
}