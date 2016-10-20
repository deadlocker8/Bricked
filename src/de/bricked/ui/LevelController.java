package de.bricked.ui;

import java.io.IOException;
import java.util.Locale;
import java.util.ResourceBundle;

import de.bricked.game.Game;
import de.bricked.game.board.Board;
import de.bricked.game.bricks.Brick;
import fontAwesome.FontIcon;
import fontAwesome.FontIconType;
import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.RowConstraints;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import logger.LogLevel;
import logger.Logger;
import tools.Worker;

public class LevelController
{
	@FXML private AnchorPane anchorPane;
	@FXML private Label labelLevelName;
	@FXML private Label labelAuthor;
	@FXML private Label labelLevelPack;
	@FXML private Label labelPoints;
	@FXML private Label labelBlocksRemaining;
	@FXML private AnchorPane anchorPaneGame;
	@FXML private Button buttonBack;
	@FXML private VBox vboxPowerUps;
	@FXML private VBox vboxLives;

	public Stage stage;
	public Image icon = new Image("de/bricked/resources/icon.png");
	public final ResourceBundle bundle = ResourceBundle.getBundle("de/bricked/main/", Locale.GERMANY);
	private LevelSelectController levelSelectController;
	private Game game;
	private Board board;	
	private final int MAX_LIVES = 7;

	public void init(Stage stage, LevelSelectController levelSelectController, Game game)
	{
		this.stage = stage;
		this.levelSelectController = levelSelectController;
		this.game = game;
		this.board = new Board(game.getLevel());

		anchorPane.setOnKeyReleased(new EventHandler<KeyEvent>()
		{
			@Override
			public void handle(KeyEvent event)
			{
				if(event.getCode().toString().equals(bundle.getString("shortcut.debug.console")))
				{
					showCommandLine();
					Logger.log(LogLevel.INFO, "openend debug console");
					event.consume();
				}
			}
		});

		stage.setOnCloseRequest(new EventHandler<WindowEvent>()
		{
			public void handle(WindowEvent event)
			{
				// TODO
				Worker.shutdown();
				Platform.exit();
				System.exit(0);
			}
		});
		
		FontIcon iconBack = new FontIcon(FontIconType.ARROW_LEFT);
		iconBack.setSize(18);
		buttonBack.setText("");
		buttonBack.setGraphic(iconBack);
		
		vboxPowerUps.setStyle("-fx-border-color: #333333; -fx-border-width: 2px;");		
		vboxLives.setStyle("-fx-border-color: #333333; -fx-border-width: 2px;");
		vboxLives.setPadding(new Insets(3));
		
		anchorPaneGame.setPadding(new Insets(0));

		labelLevelPack.setText(game.getLevelPack().getPackageName());
		labelAuthor.setText("by " + game.getLevel().getAuthor());
		labelLevelName.setText(game.getLevel().getName() + " (" + game.getLevel().getPosition() + "/" + game.getLevelPack().getLevels().size() + ")");
		labelBlocksRemaining.setText(board.getNumberOfRemainingBricks() + " Bricks remaining");
		
		game.setLivesRemaining(game.getLevel().getStartLives());
		
		refreshLiveCounter();

		redraw();

		// Collision detection:

		// StackPane pane = new StackPane();
		// Rectangle r = new Rectangle(44, 20);
		// r.setFill(Color.RED);
		//
		// ImageView iv = new ImageView(icon);
		// iv.setFitWidth(44);
		// iv.setFitHeight(20);
		//
		// pane.getChildren().addAll(r, iv);
		//
		// Circle c = new Circle(10);
		// c.setFill(Color.GREEN);
		// c.setTranslateX(59);
		// c.setTranslateY(30);
		// anchorPane.getChildren().add(c);
		//
		// anchorPane.getChildren().add(pane);
		//
		// if(Shape.intersect(r, c).getBoundsInLocal().getWidth() != - 1)
		// {
		// System.out.println("collision");
		// }
	}

	public void redraw()
	{
		anchorPaneGame.getChildren().clear();
		double brickWidth = (game.getSettings().getGameSize().getWidth() - 100) / Board.WIDTH;
		double brickHeight = (game.getSettings().getGameSize().getHeight() - 150) / Board.HEIGHT;		

		GridPane grid = new GridPane();
		grid.setStyle("-fx-border-color: #333333; -fx-border-width: 2px;");
		grid.setGridLinesVisible(false);
		grid.setHgap(0);
		grid.setVgap(0);

		grid.getColumnConstraints().clear();
		double xPercentage = 1.0 / Board.WIDTH;
		for(int i = 0; i < Board.WIDTH; i++)
		{
			ColumnConstraints c = new ColumnConstraints();
			c.setPercentWidth(xPercentage * 100);
			grid.getColumnConstraints().add(c);
		}

		grid.getRowConstraints().clear();
		double yPercentage = 1.0 / Board.HEIGHT;
		for(int i = 0; i < Board.HEIGHT; i++)
		{
			RowConstraints c = new RowConstraints();
			c.setPercentHeight(yPercentage * 100);
			grid.getRowConstraints().add(c);
		}		

		for(int i = 0; i < Board.HEIGHT; i++)
		{
			for(int k = 0; k < Board.WIDTH; k++)
			{				
				Brick currentBrick = board.getBricks().get(i).get(k);

				StackPane pane = new StackPane();

				Rectangle r = new Rectangle(brickWidth, brickHeight);
				r.setFill(Color.TRANSPARENT);

				ImageView iv = new ImageView(new Image("de/bricked/resources/textures/bricks/" + currentBrick.getCurrentTextureID() + ".png"));
				iv.setFitWidth(brickWidth);
				iv.setFitHeight(brickHeight);

				//DEBUG
				Label l = new Label(currentBrick.getID());

				pane.getChildren().addAll(r, iv, l);

				grid.add(pane, k, i);
			}
		}

		anchorPaneGame.getChildren().add(grid);
		AnchorPane.setTopAnchor(grid, 0.0);
		AnchorPane.setRightAnchor(grid, 0.0);
		AnchorPane.setBottomAnchor(grid, 0.0);
		AnchorPane.setLeftAnchor(grid, 0.0);
	}
	
	private void refreshLiveCounter()
	{
		vboxLives.getChildren().clear();
		
		for(int i = 0; i < MAX_LIVES - game.getLivesRemaining(); i++)
		{
			ImageView iv = new ImageView(new Image("de/bricked/resources/textures/bricks/empty.png"));
			iv.setFitWidth(30);
			iv.setFitHeight(148 / MAX_LIVES);
			vboxLives.getChildren().add(iv);
			if(i > 0)
			{
				VBox.setMargin(iv, new Insets(4,0,0,0));
			}
		}	
		
		for(int i = 0; i < game.getLivesRemaining(); i++)
		{
			ImageView iv = new ImageView(new Image("de/bricked/resources/textures/paddle/paddle.png"));
			iv.setFitWidth(30);
			iv.setFitHeight(148 / MAX_LIVES);
			vboxLives.getChildren().add(iv);	
			
			if(game.getLivesRemaining() == MAX_LIVES)
			{
				if(i > 0)
				{
					VBox.setMargin(iv, new Insets(4,0,0,0));
				}
			}
			else
			{
				VBox.setMargin(iv, new Insets(4,0,0,0));
			}
		}	
	}

	public void showCommandLine()
	{
		try
		{
			levelSelectController.controller.controller.getCommandLine().showCommandLine("Debug Console", 400, 250, 400, 200, - 1, - 1, true);
		}
		catch(IOException e)
		{
			// TODO: errorhandling
			Logger.log(LogLevel.ERROR, Logger.exceptionToString(e));
		}
	}
	
	public void back()
	{
		stage.close();
		levelSelectController.stage.show();
	}
}