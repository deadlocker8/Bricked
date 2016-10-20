package de.bricked.ui;

import java.io.IOException;
import java.util.Locale;
import java.util.ResourceBundle;

import de.bricked.game.Game;
import de.bricked.game.levels.Level;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.ScrollPane.ScrollBarPolicy;
import javafx.scene.image.Image;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import javafx.util.Callback;
import logger.LogLevel;
import logger.Logger;

public class LevelSelectController
{
	@FXML private AnchorPane mainPane;
	@FXML private ScrollPane pane;
	@FXML private Label labelLevelPack;

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
					Logger.log(LogLevel.INFO, "openend debug console");
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
		
		
		
		
		
		
		CustomListView<Level> listView = new CustomListView<Level>(FXCollections.observableList(game.getLevelPack().getLevels()));		
		
		listView.setCellFactory(new Callback<ListView<Level>, ListCell<Level>>()
		{			
			@Override
			public ListCell<Level> call(ListView<Level> param)
			{
				return new LevelCell(pane.getMaxWidth());
			}
		});
		listView.setStyle("-fx-background-color: transparent");		
	
		listView.setSelectable(false);
		
		listView.prefWidthProperty().bind(pane.widthProperty());
		listView.prefHeightProperty().bind(pane.heightProperty().subtract(10));
		
//		GridPane gridPane = new GridPane();
//		gridPane.setAlignment(Pos.TOP_CENTER);
//		gridPane.setVgap(30);
//		gridPane.setHgap(30);
//
//		int rowCounter = 0;
//
//		for(int i = 0; i < game.getLevelPack().getLevels().size(); i++)
//		{
//			Level currentLevel = game.getLevelPack().getLevels().get(i);
//
//			StackPane stack = new StackPane();
//			stack.setAlignment(Pos.TOP_CENTER);
//
//			HBox hbox = new HBox();
//			hbox.setPrefHeight(102);
//			hbox.setStyle("-fx-background-radius: 10; -fx-background-color: #00000066;");
//			hbox.setAlignment(Pos.BOTTOM_CENTER);
//			hbox.setPadding(new Insets(0, 0, 2, 0));
//
//			Rectangle rectangle = new Rectangle(75, 75);
//
//			int stars = currentLevel.getDifficulty();
//
//			FontIcon iconStarOne = new FontIcon(FontIconType.STAR);
//			iconStarOne.setSize(20);
//			iconStarOne.setTextFill(Color.YELLOW);
//			Label labelStarOne = new Label();
//			labelStarOne.setGraphic(iconStarOne);
//			hbox.getChildren().add(labelStarOne);
//
//			FontIcon iconStarTwo = new FontIcon(FontIconType.STAR);
//			iconStarTwo.setSize(20);
//			if(stars > 1)
//			{
//				iconStarTwo.setTextFill(Color.YELLOW);
//			}
//			else
//			{
//				iconStarTwo.setTextFill(Color.TRANSPARENT);
//			}
//			Label labelStarTwo = new Label();
//			labelStarTwo.setGraphic(iconStarTwo);
//			hbox.getChildren().add(labelStarTwo);
//
//			FontIcon iconStarThree = new FontIcon(FontIconType.STAR);
//			iconStarThree.setSize(20);
//			if(stars > 2)
//			{
//				iconStarThree.setTextFill(Color.YELLOW);
//			}
//			else
//			{
//				iconStarThree.setTextFill(Color.TRANSPARENT);
//			}
//			Label labelStarThree = new Label();
//			labelStarThree.setGraphic(iconStarThree);
//			hbox.getChildren().add(labelStarThree);
//
//			rectangle.setFill(Color.web("#CBE581"));
//
//			stack.getChildren().add(hbox);
//
//			rectangle.setStyle("-fx-stroke: black; -fx-stroke-width: 2; -fx-arc-height: 10; -fx-arc-width: 10;");
//			stack.getChildren().add(rectangle);
//
//			Label label = new Label(String.valueOf(currentLevel.getName()));
//			label.setStyle("-fx-font-size: 15; -fx-font-weight: bold; -fx-text-fill: white;");
//			label.setPadding(new Insets(17, 0, 0, 0));
//			stack.getChildren().add(label);
//
//			if(i % 2 == 0)
//			{
//				rowCounter++;
//			}
//
//			gridPane.add(stack, i % 2, rowCounter);
//
//			stack.setOnMouseReleased(event -> {
//				try
//				{
//					game.setLevel(currentLevel);
//
//					FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/de/bricked/ui/LevelGUI.fxml"));
//
//					Parent root = (Parent)fxmlLoader.load();
//					Stage newStage = new Stage();
//					newStage.setScene(new Scene(root, 800, 800));
//					newStage.setTitle("Level " + currentLevel.getName());
//					newStage.initOwner(stage);
//
//					newStage.getIcons().add(controller.icon);
//					LevelController newController = fxmlLoader.getController();
//					newController.init(newStage, this, game);
//
//					newStage.initModality(Modality.NONE);
//					newStage.setResizable(false);
//					stage.hide();
//					newStage.show();
//				}
//				catch(IOException e1)
//				{
//					e1.printStackTrace();
//				}
//			});
//		}

		pane.setContent(listView);
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
			controller.controller.getCommandLine().showCommandLine("Debug Console", 400, 250, 400, 200, - 1, - 1, true);
		}
		catch(IOException e)
		{
			// TODO: errorhandling
			Logger.log(LogLevel.ERROR, Logger.exceptionToString(e));
		}
	}
}