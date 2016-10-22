package de.bricked.ui;

import java.io.IOException;
import java.util.Locale;
import java.util.Random;
import java.util.ResourceBundle;

import de.bricked.game.Game;
import de.bricked.game.HitLocation;
import de.bricked.game.balls.Ball;
import de.bricked.game.balls.BallType;
import de.bricked.game.board.Board;
import de.bricked.game.bricks.Brick;
import de.bricked.game.bricks.BrickType;
import fontAwesome.FontIcon;
import fontAwesome.FontIconType;
import javafx.animation.AnimationTimer;
import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.effect.Lighting;
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
import javafx.scene.shape.Circle;
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
	private GridPane grid;
	private final int MAX_LIVES = 7;
	private AnimationTimer timer;
	private double gamePaneWidth;
	private double gamePaneHeight;
	final int TARGET_FPS = 60;
	final long OPTIMAL_TIME = 1000000000 / TARGET_FPS;
	private int fps;

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
					return;
				}
				
				if(event.getCode().toString().equals("SPACE"))
				{
					//start random into left or right direction
					int random = new Random().nextInt(2);
					if(random == 0)
					{
						game.getBall().startBallToRight();
					}
					else
					{
						game.getBall().startBallToLeft();
					}
					
					
					timer.start();
					Logger.log(LogLevel.INFO, "ball start");
					event.consume();
					return;
				}
			}
		});

		stage.setOnCloseRequest(new EventHandler<WindowEvent>()
		{
			public void handle(WindowEvent event)
			{
				if(timer != null)
				{
					timer.stop();
				}
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
		
		gamePaneWidth = game.getSettings().getGameSize().getWidth() - 100;
		gamePaneHeight = game.getSettings().getGameSize().getHeight() - 150;
		
		refreshLiveCounter();		
		
		//TODO init ball and paddle			
		game.setBall(new Ball(BallType.NORMAL));

		//create circle for ball
		final Circle circle = new Circle(game.getBall().getBallRadius(), Color.rgb(156, 216, 255));
		circle.setEffect(new Lighting());
		final StackPane stackPaneBall = new StackPane();
		stackPaneBall.getChildren().addAll(circle);
		//TODO center on paddle
		stackPaneBall.setLayoutX(gamePaneWidth/2);
		stackPaneBall.setLayoutY(gamePaneHeight - game.getBall().getBallRadius() *2);		
		anchorPaneGame.getChildren().add(stackPaneBall);
		
		timer = new AnimationTimer()
		{			
			private long previousTime = 0;
			private float secondsElapsedSinceLastFpsUpdate = 0f;			

			@Override
			public void handle(long currentTime)
			{
				if(previousTime == 0)
				{
					previousTime = currentTime;
					return;
				}

				float secondsElapsed = currentTime - previousTime;
				previousTime = currentTime;
				double delta = secondsElapsed / ((double)OPTIMAL_TIME);

				secondsElapsedSinceLastFpsUpdate += secondsElapsed;	
				fps++;
				
				if(secondsElapsedSinceLastFpsUpdate >= 1000000000)
				{
					System.out.println("(FPS: " + fps + ")");
					secondsElapsedSinceLastFpsUpdate = 0;
					fps = 0;
				}
				
				//update ball location		
				stackPaneBall.setTranslateX(stackPaneBall.getTranslateX() + game.getBall().getDirection().getX() * delta);
				stackPaneBall.setTranslateY(stackPaneBall.getTranslateY() + game.getBall().getDirection().getY() * delta);	
				
				//hit detection
				HitLocation hitLocation = game.hitsWall(gamePaneWidth, gamePaneHeight, stackPaneBall.getLayoutX(), stackPaneBall.getLayoutY(), stackPaneBall.getTranslateX(), stackPaneBall.getTranslateY(), game.getBall().getDirection());
				//if ball collides with border then brick collisions are irrelevant
				if(hitLocation != null)
				{
					game.getBall().setDirection(game.reflectBall(hitLocation, game.getBall().getDirection()));
				}
				//ball doesn't collide with border --> check collision with bricks
				else
				{
					//loop over all non air bricks
					for(int i = 0; i < Board.HEIGHT; i++)
					{						
						for(int k = 0; k < Board.WIDTH; k++)
						{
							Brick currentBrick = board.getBricks().get(i).get(k);							
							if(!currentBrick.getType().equals(BrickType.AIR))
							{																	
								StackPane stackPaneBrick = (StackPane)grid.getChildren().get(i * (int)Board.WIDTH + k);											
												
								hitLocation = game.collides(
										stackPaneBall.getBoundsInParent(), 
										stackPaneBall.getLayoutX(), 
										stackPaneBall.getLayoutY(), 
										stackPaneBall.getTranslateX(), 
										stackPaneBall.getTranslateY(), 
										stackPaneBrick.getBoundsInParent(), 
										stackPaneBrick.getLayoutX(),
										stackPaneBrick.getLayoutY(),
										stackPaneBrick.getTranslateX(),
										stackPaneBrick.getTranslateY(),
										stackPaneBrick.getWidth(),
										stackPaneBrick.getHeight(),
										false);
								if(hitLocation != null)
								{		
									if(hitLocation.equals(HitLocation.LIFE_LOST))
									{
										game.setLivesRemaining(game.getLivesRemaining() - 1);
										refreshLiveCounter();
										if(game.getLivesRemaining() <= 0)
										{
											//game over
											Alert alert = new Alert(AlertType.INFORMATION);
											alert.setTitle("Game Over");
											alert.setHeaderText(""); 
											alert.setContentText("You have no lives left");
											Stage dialogStage = (Stage)alert.getDialogPane().getScene().getWindow();
											dialogStage.getIcons().add(icon);
											dialogStage.centerOnScreen();
											alert.showAndWait();
										}
										//TODO reset ball and paddle
									}
									else
									{
										game.getBall().setDirection(game.reflectBall(hitLocation, game.getBall().getDirection()));
										game.setPoints(game.getPoints() + board.hitBrick(i, k, false));									
										labelPoints.setText(String.valueOf(game.getPoints()));
										labelBlocksRemaining.setText(board.getNumberOfRemainingBricks() + " Bricks remaining");	
										redraw();
										if(board.getNumberOfRemainingBricks() == 0)
										{											
											//level done
											Alert alert = new Alert(AlertType.INFORMATION);
											alert.setTitle("Congratulations!");
											alert.setHeaderText(""); 
											alert.setContentText("You finished Level \"" + game.getLevel().getName() + "\" with " + game.getPoints() + " Points" );
											Stage dialogStage = (Stage)alert.getDialogPane().getScene().getWindow();
											dialogStage.getIcons().add(icon);
											dialogStage.centerOnScreen();
											alert.showAndWait();
										}
									}
								}
							}
						}						
					}
				}
				
				long sleepTime = (previousTime-System.nanoTime() + OPTIMAL_TIME)/1000000;
				
				if(sleepTime > 0)
				{			
					try
					{
						Thread.sleep(sleepTime);
					}
					catch(Exception e)
					{
						e.printStackTrace();
					}	
				}
			}
		};		
		
		grid = new GridPane();
		grid.setStyle("-fx-border-color: #333333; -fx-border-width: 2px;");
		grid.setGridLinesVisible(false);
		grid.setHgap(0);		
		grid.setVgap(0);		
		
		anchorPaneGame.getChildren().add(grid);
		AnchorPane.setTopAnchor(grid, 0.0);
		AnchorPane.setRightAnchor(grid, 0.0);
		AnchorPane.setBottomAnchor(grid, 0.0);
		AnchorPane.setLeftAnchor(grid, 0.0);
		
		redraw();
		
		anchorPaneGame.requestFocus();				
	}

	public void redraw()
	{
		grid.getChildren().clear();		

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

				//DEBUG
				Label l = new Label(currentBrick.getType().getID());
				l.setStyle("-fx-background-image: url(\"de/bricked/resources/textures/bricks/" + currentBrick.getCurrentTextureID() + ".png\");"
						+ "-fx-background-position: center center;"
						+ "-fx-background-repeat: no-repeat;"
						+ "-fx-background-size: cover");
				l.setAlignment(Pos.CENTER);
				
				l.prefWidthProperty().bind(grid.getColumnConstraints().get(0).percentWidthProperty().multiply((game.getSettings().getGameSize().getWidth() - 100)));
				l.prefHeightProperty().bind(grid.getRowConstraints().get(0).percentHeightProperty().multiply((game.getSettings().getGameSize().getHeight() - 150)));

				pane.getChildren().add(l);

				grid.add(pane, k, i);
			}
		}
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
		if(timer != null)
		{
			timer.stop();
		}
		stage.close();
		levelSelectController.stage.show();
	}
}