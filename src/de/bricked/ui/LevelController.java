package de.bricked.ui;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Locale;
import java.util.Random;
import java.util.ResourceBundle;

import de.bricked.game.Game;
import de.bricked.game.GameState;
import de.bricked.game.HitLocation;
import de.bricked.game.balls.Ball;
import de.bricked.game.balls.BallType;
import de.bricked.game.board.Board;
import de.bricked.game.bricks.Brick;
import de.bricked.game.bricks.BrickType;
import de.bricked.game.paddle.Paddle;
import de.bricked.game.powerups.PowerUp;
import fontAwesome.FontIcon;
import fontAwesome.FontIconType;
import javafx.animation.AnimationTimer;
import javafx.animation.FadeTransition;
import javafx.animation.ParallelTransition;
import javafx.animation.TranslateTransition;
import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.geometry.Point2D;
import javafx.geometry.Pos;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.effect.Lighting;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
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
import javafx.util.Duration;
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
	@FXML private Label labelFPS;
	@FXML private Label labelMultiplicator;

	public Stage stage;
	public Image icon = new Image("de/bricked/resources/icon.png");
	public final ResourceBundle bundle = ResourceBundle.getBundle("de/bricked/main/", Locale.GERMANY);
	private LevelSelectController levelSelectController;
	private Game game;
	private static GridPane grid;
	private AnimationTimer timer;
	private double gamePaneWidth;
	private double gamePaneHeight;
	private final int MAX_LIVES = 7;
	private final int TARGET_FPS = 60;
	private final long OPTIMAL_TIME = 1000000000 / TARGET_FPS;
	private final static double BRICK_FADE_DURATION = 300.0;
	private int fps;
	private Paddle paddle;
	private GameState gameState;
	private ImageView labelPaddle;
	private StackPane stackPaneBall;
	private ChangeListener<Number> heightListener;
	private ChangeListener<Number> widthListener;
	private double oldMousePosition;
	private static ArrayList<Label> brickLabels;
	private ArrayList<Label> movingPowerUps;

    private void startGame()
    {   	
        anchorPaneGame.heightProperty().removeListener(heightListener);
        anchorPaneGame.widthProperty().removeListener(widthListener);

        // start random into left or right direction
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

        gameState = GameState.RUNNING;
    }

	public void init(Stage stage, LevelSelectController levelSelectController, Game game)
	{
		this.stage = stage;
		this.levelSelectController = levelSelectController;
		this.game = game;
		game.setBoard(new Board(game));
		game.setLevelController(this);	

        anchorPane.setOnMouseClicked(new EventHandler<MouseEvent>()
        {
            @Override
            public void handle(MouseEvent event)
            {
                startGame();
                event.consume();
                anchorPaneGame.requestFocus();
            }
        });

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
					if(gameState.equals(GameState.WAITING))
					{
						startGame();
					}
					event.consume();

					anchorPaneGame.requestFocus();
					return;
				}

				if(event.getCode().toString().equals("ESCAPE"))
				{
					back();
					event.consume();
				}
			}
		});

		anchorPane.setOnKeyPressed(new EventHandler<KeyEvent>()
		{
			@Override
			public void handle(KeyEvent event)
			{
				if(gameState.equals(GameState.RUNNING))
				{
					if(event.getCode().toString().equals("RIGHT"))
					{
						movePaddleRight();
					}

					if(event.getCode().toString().equals("LEFT"))
					{
						movePaddleLeft();
					}
				}
			}
		});

		anchorPane.setOnMouseMoved(new EventHandler<MouseEvent>()
		{
			@Override
			public void handle(MouseEvent event)
			{
				if(gameState.equals(GameState.RUNNING))
				{
					// --> direct follow mouse
					double newPaddlePosition = event.getSceneX() - paddle.getWidth() / 2;

					// move left
					if(newPaddlePosition < oldMousePosition)
					{
						if(newPaddlePosition > 0)
						{
							labelPaddle.setTranslateX(newPaddlePosition);
						}
						else
						{
							labelPaddle.setTranslateX(0);
						}
					}
					// move right
					else
					{
						if((newPaddlePosition + paddle.getWidth()) < gamePaneWidth)
						{
							labelPaddle.setTranslateX(newPaddlePosition);
						}
						else
						{
							labelPaddle.setTranslateX(gamePaneWidth - paddle.getWidth());
						}
					}

					oldMousePosition = labelPaddle.getScene().getX();
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
		vboxLives.setAlignment(Pos.BOTTOM_CENTER);
		vboxLives.setSpacing(9);

		anchorPaneGame.setPadding(new Insets(0));

		labelLevelPack.setText(game.getLevelPack().getPackageName());
		labelAuthor.setText("by " + game.getLevel().getAuthor());
		labelLevelName.setText(game.getLevel().getName() + " (" + game.getLevel().getPosition() + "/" + game.getLevelPack().getLevels().size() + ")");
		labelBlocksRemaining.setText(game.getBoard().getNumberOfRemainingBricks() + " Bricks remaining");

		game.setLivesRemaining(game.getLevel().getStartLives());

		gamePaneWidth = game.getSettings().getGameSize().getWidth() - 100;
		gamePaneHeight = game.getSettings().getGameSize().getHeight() - 150;

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

		refreshLiveCounter();

		initPaddle();
		oldMousePosition = labelPaddle.getScene().getX() - paddle.getWidth() / 2;

		heightListener = new ChangeListener<Number>()
		{
			@Override
			public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue)
			{
				initBall();
				gamePaneHeight = newValue.doubleValue();
			}
		};

		widthListener = new ChangeListener<Number>()
		{
			@Override
			public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue)
			{
				gamePaneWidth = newValue.doubleValue();
			}
		};

		anchorPaneGame.heightProperty().addListener(heightListener);
		anchorPaneGame.widthProperty().addListener(widthListener);

		anchorPaneGame.requestFocus();

		showLabelFPS(levelSelectController.controller.controller.getCommandLine().getBundle().isShowFPS());
		labelFPS.setStyle("-fx-text-fill: #FF0000");

		resetMultiplicator();
		movingPowerUps = new ArrayList<>();

		gameState = GameState.WAITING;
	}

	public Paddle getPaddle()
	{
		return paddle;
	}

	public ImageView getLabelPaddle()
	{
		return labelPaddle;
	}

	private void initTimer()
	{
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
					labelFPS.setText(String.valueOf(fps));
					Logger.log(LogLevel.DEBUG, "current FPS: " + fps);
					secondsElapsedSinceLastFpsUpdate = 0;
					fps = 0;
				}

				// update ball location
				stackPaneBall.setTranslateX(stackPaneBall.getTranslateX() + game.getBall().getDirection().getX() * delta);
				stackPaneBall.setTranslateY(stackPaneBall.getTranslateY() + game.getBall().getDirection().getY() * delta);

				// hit detection
				HitLocation hitLocation = game.hitsWall(gamePaneWidth, gamePaneHeight, stackPaneBall.getLayoutX(), stackPaneBall.getLayoutY(), stackPaneBall.getTranslateX(), stackPaneBall.getTranslateY(), game.getBall().getDirection());

				Point2D ballPosition = new Point2D(stackPaneBall.getLayoutX() + stackPaneBall.getTranslateX(), stackPaneBall.getLayoutY() + stackPaneBall.getTranslateY());
				Point2D paddlePosition = new Point2D(labelPaddle.getLayoutX() + labelPaddle.getTranslateX(), labelPaddle.getLayoutY() + labelPaddle.getTranslateY());
				// if ball collides with border then brick collisions are irrelevant
				if(hitLocation != null)
				{
					resetMultiplicator();
					
					if(hitLocation.equals(HitLocation.LIFE_LOST))
					{
						game.setLivesRemaining(game.getLivesRemaining() - 1);
						Logger.log(LogLevel.DEBUG, "Life lost (" + game.getLivesRemaining() + " lives remaining)");
						refreshLiveCounter();
						if(game.getLivesRemaining() <= 0)
						{
							// game over

							gameState = GameState.STOPPED;
							timer.stop();

							Platform.runLater(() -> {
								Alert alert = new Alert(AlertType.INFORMATION);
								alert.setTitle("Game Over");
								alert.setHeaderText("");
								alert.setContentText("You have no lives left");
								Stage dialogStage = (Stage)alert.getDialogPane().getScene().getWindow();
								dialogStage.getIcons().add(icon);
								dialogStage.centerOnScreen();
								alert.showAndWait();
							});
						}
						else
						{
							gameState = GameState.WAITING;
							timer.stop();

							// reset paddle and ball
							initPaddle();
							initBall();						
						}
					}
					else
					{
						game.getBall().setDirection(game.reflectBall(hitLocation, game.getBall().getDirection()));

						switch(hitLocation)
						{
							case BOTTOM:
								stackPaneBall.setTranslateX(stackPaneBall.getTranslateX());
								stackPaneBall.setTranslateY(1);
								break;

							case RIGHT:
								stackPaneBall.setTranslateX(1);
								stackPaneBall.setTranslateY(stackPaneBall.getTranslateY());
								break;

							case LEFT:
								stackPaneBall.setTranslateX(gamePaneWidth - game.getBall().getBallRadius() * 2 - 1);
								stackPaneBall.setTranslateY(stackPaneBall.getTranslateY());
								break;

							case CORNER:
								if(ballPosition.getX() + game.getBall().getBallRadius() > gamePaneWidth / 2)
								{
									// ball is in top right corner
									stackPaneBall.setTranslateX(gamePaneWidth - game.getBall().getBallRadius() * 2 - 1);
									stackPaneBall.setTranslateY(1);
									break;
								}
								else
								{
									// ball is in top left corner
									stackPaneBall.setTranslateX(1);
									stackPaneBall.setTranslateY(1);
									break;
								}

							default:
								break;
						}
					}
				}
				// ball doesn't collide with border --> check collision with paddle
				else
				{
					hitLocation = game.collides(ballPosition, paddlePosition, paddle.getWidth(), paddle.getHeight(), true);					
					if(hitLocation != null && (hitLocation.equals(HitLocation.PADDLE) || hitLocation.equals(HitLocation.CORNER)))
					{						
						game.getBall().setDirection(game.reflectOnPaddle(game.getBall().getDirection(), game.getDistanceToPaddleCenter(ballPosition, paddlePosition, paddle.getWidth())));

						correctBallPosition(hitLocation, ballPosition, paddlePosition, paddle.getWidth(), paddle.getHeight());
						
						resetMultiplicator();
					}
					// ball doesn't collide with paddle --> check collision with bricks
					else
					{
						if(game.getBall().getDirection().getX() > 0)
						{
							for(int i = 0; i < Board.HEIGHT; i++)
							{
								for(int k = 0; k < Board.WIDTH; k++)
								{
									brickCollisionDetection(i, k, ballPosition);
								}
							}
						}
						else
						{
							for(int i = (int)Board.HEIGHT - 1; i >= 0; i--)
							{
								for(int k = (int)Board.WIDTH - 1; k >= 0; k--)
								{
									brickCollisionDetection(i, k, ballPosition);
								}
							}
						}
					}
				}
				
				//move powerups
				movePowerUps();
				//check timed powerups
				checkPowerUps();
			}	
		};
	}

	public void correctBallPosition(HitLocation hitLocation, Point2D ballPosition, Point2D brickPosition, double brickWidth, double brickHeight)
	{
		// correct ball position if inside brick
		Point2D correctedBallPosition = game.getCorrectedBallPosition(hitLocation, ballPosition, brickPosition, brickWidth, brickHeight);
		stackPaneBall.setTranslateX(correctedBallPosition.getX() - stackPaneBall.getLayoutX());
		stackPaneBall.setTranslateY(correctedBallPosition.getY() - stackPaneBall.getLayoutY());
	}

	public void redraw()
	{
		grid.getChildren().clear();
		brickLabels = new ArrayList<>();

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
				Brick currentBrick = game.getBoard().getBricks().get(i).get(k);

				Label labelBrick = new Label();
				labelBrick.setStyle("-fx-background-image: url(\"de/bricked/resources/textures/bricks/" + currentBrick.getCurrentTextureID() + ".png\");" + "-fx-background-position: center center;" + "-fx-background-repeat: no-repeat;" + "-fx-background-size: cover");
				labelBrick.setAlignment(Pos.CENTER);

				labelBrick.prefWidthProperty().bind(grid.getColumnConstraints().get(0).percentWidthProperty().multiply(gamePaneWidth));
				labelBrick.prefHeightProperty().bind(grid.getRowConstraints().get(0).percentHeightProperty().multiply(gamePaneHeight));

				brickLabels.add(labelBrick);
				grid.add(labelBrick, k, i);
			}
		}
	}

	public static void redrawBrick(int column, int row, Brick brick, boolean fade)
	{
		int id = row * (int)Board.WIDTH + column;

		Label labelBrick = brickLabels.get(id);

		if(fade)
		{
			FadeTransition ft = new FadeTransition(Duration.millis(BRICK_FADE_DURATION), labelBrick);
			ft.setFromValue(1.0);
			ft.setToValue(0.0);
			ft.setCycleCount(1);
			ft.setAutoReverse(false);
			ft.setOnFinished(new EventHandler<ActionEvent>()
			{
				@Override
				public void handle(ActionEvent event)
				{
					labelBrick.setStyle("-fx-background-image: url(\"de/bricked/resources/textures/bricks/" + brick.getCurrentTextureID() + ".png\");" + "-fx-background-position: center center;" + "-fx-background-repeat: no-repeat;" + "-fx-background-size: cover");
				}
			});			
			ft.play();	
		}
		else
		{
			labelBrick.setStyle("-fx-background-image: url(\"de/bricked/resources/textures/bricks/" + brick.getCurrentTextureID() + ".png\");" + "-fx-background-position: center center;" + "-fx-background-repeat: no-repeat;" + "-fx-background-size: cover");
		}
	}

	private void refreshLiveCounter()
	{
		vboxLives.getChildren().clear();	

		for(int i = 0; i < game.getLivesRemaining() - 1; i++)
		{
			ImageView iv = new ImageView(new Image("de/bricked/resources/textures/paddle/paddle-extra-small.png"));
			iv.setFitWidth(30);
			iv.setFitHeight(120 / MAX_LIVES);
			vboxLives.getChildren().add(iv);	
		}
	}

	private void initPaddle()
	{
		anchorPaneGame.getChildren().remove(labelPaddle);

		paddle = new Paddle(game.getLevel().getInitPadSize(), gamePaneHeight / Board.HEIGHT, gamePaneWidth);
		labelPaddle = new ImageView(new Image("de/bricked/resources/textures/paddle/" + paddle.getPaddleSize().getTextureID() + ".png"));
		labelPaddle.setFitWidth(paddle.getWidth());
		labelPaddle.setFitHeight(paddle.getHeight());
		labelPaddle.setTranslateX(gamePaneWidth / 2 - paddle.getWidth() / 2);
		anchorPaneGame.getChildren().add(labelPaddle);
		AnchorPane.setBottomAnchor(labelPaddle, paddle.getHeight());
	}

	private void initBall()
	{
		anchorPaneGame.getChildren().remove(stackPaneBall);

		game.setBall(new Ball(BallType.NORMAL));

		// create circle for ball
		final Circle circle = new Circle(game.getBall().getBallRadius(), Color.web(game.getBall().getType().getColor()));
		circle.setEffect(new Lighting());
		stackPaneBall = new StackPane();
		stackPaneBall.getChildren().addAll(circle);
		stackPaneBall.setTranslateX(gamePaneWidth / 2 - game.getBall().getBallRadius());
		stackPaneBall.setTranslateY(anchorPaneGame.getHeight() - paddle.getHeight() * 2 - game.getBall().getBallRadius() * 2);
		anchorPaneGame.getChildren().add(stackPaneBall);

		initTimer();
	}

	private void resetMultiplicator()
	{
		game.applyMultiplicator();
		game.resetMultiplicator();
		game.resetPointsSinceLastMultiplicatorReset();
		labelMultiplicator.setText("x0");
		labelPoints.setText(String.valueOf(game.getTotalPoints()));
	}

	public void increaseMultiplicator(int points)
	{		
		game.increaseMultiplicator();		
		game.increasePointsSinceLastMultiplicatorReset(points);	
		labelMultiplicator.setText("x" + game.getMultiplicator());
	}

	private void movePaddleLeft()
	{
		if(labelPaddle.getLayoutX() + labelPaddle.getTranslateX() - paddle.getSpeed() > 0)
		{
			labelPaddle.setTranslateX(labelPaddle.getTranslateX() - paddle.getSpeed());
		}
		else
		{
			labelPaddle.setTranslateX(0);
		}
	}

	private void movePaddleRight()
	{
		if(labelPaddle.getLayoutX() + labelPaddle.getTranslateX() + paddle.getSpeed() + paddle.getWidth() < gamePaneWidth)
		{
			labelPaddle.setTranslateX(labelPaddle.getTranslateX() + paddle.getSpeed());
		}
		else
		{
			labelPaddle.setTranslateX(anchorPaneGame.getWidth() - paddle.getWidth());
		}
	}

	private void brickCollisionDetection(int i, int k, Point2D ballPosition)
	{
		Brick currentBrick = game.getBoard().getBricks().get(i).get(k);
		if(!currentBrick.getType().equals(BrickType.AIR))
		{
			Label stackPaneBrick = (Label)grid.getChildren().get(i * (int)Board.WIDTH + k);

			Point2D brickPosition = new Point2D(stackPaneBrick.getLayoutX() + stackPaneBrick.getTranslateX(), stackPaneBrick.getLayoutY() + stackPaneBrick.getTranslateY());

			HitLocation hitLocation = game.collides(ballPosition, brickPosition, stackPaneBrick.getWidth(), stackPaneBrick.getHeight(), false);
			if(hitLocation != null)
			{
				if(!game.getBall().getType().equals(BallType.NO_COLLISION))
				{
					game.getBall().setDirection(game.reflectBall(hitLocation, game.getBall().getDirection()));

					correctBallPosition(hitLocation, ballPosition, brickPosition, stackPaneBrick.getWidth(), stackPaneBrick.getHeight());
				}				
				
				int points = game.getBoard().hitBrick(i, k, game.getBall());
				//brick has been destroyed
				if(points > 0)
				{					
					game.setTotalPoints(game.getTotalPoints() + points);
					labelPoints.setText(String.valueOf(game.getTotalPoints()));
					labelBlocksRemaining.setText(game.getBoard().getNumberOfRemainingBricks() + " Bricks remaining");
				}				

				if(game.getBoard().getNumberOfRemainingBricks() == 0)
				{
					// level done
					gameState = GameState.STOPPED;
					resetMultiplicator();
					timer.stop();

					Platform.runLater(() -> {
						Alert alert = new Alert(AlertType.INFORMATION);
						alert.setTitle("Congratulations!");
						alert.setHeaderText("");
						alert.setContentText("You finished Level \"" + game.getLevel().getName() + "\" with " + game.getTotalPoints() + " Points");
						Stage dialogStage = (Stage)alert.getDialogPane().getScene().getWindow();
						dialogStage.getIcons().add(icon);
						dialogStage.centerOnScreen();
						alert.showAndWait();
					});
				}
			}
		}
	}
	
	public void showAnimatedPoints(int row, int col, int points)
	{
		double xPosition = (gamePaneWidth / Board.WIDTH) * (col);
		double yPosition = (gamePaneHeight / Board.HEIGHT) * (row);
		
		Label labelNotification = new Label("+" + points);
		labelNotification.setTranslateX(xPosition);
		labelNotification.setTranslateY(yPosition);
		labelNotification.setStyle("-fx-font-weight: bold; -fx-font-size: 15; ");
		labelNotification.setAlignment(Pos.CENTER);
		labelNotification.setPrefWidth(gamePaneWidth / Board.WIDTH);
		labelNotification.setPrefHeight(gamePaneHeight / Board.HEIGHT);
		anchorPaneGame.getChildren().add(labelNotification);

		FadeTransition fadeTransition = new FadeTransition(Duration.millis(1200), labelNotification);
		fadeTransition.setFromValue(1.0);
		fadeTransition.setToValue(0.0);
		fadeTransition.setCycleCount(1);
		fadeTransition.setAutoReverse(false);
		TranslateTransition translateTransition = new TranslateTransition(Duration.millis(1200), labelNotification);
		translateTransition.setFromY(yPosition);
		translateTransition.setToY(yPosition - (gamePaneHeight / Board.HEIGHT));
		translateTransition.setCycleCount(1);
		translateTransition.setAutoReverse(false);		

		ParallelTransition parallelTransition = new ParallelTransition();
		parallelTransition.getChildren().addAll(fadeTransition, translateTransition);
		parallelTransition.setCycleCount(1);		
		parallelTransition.setOnFinished(new EventHandler<ActionEvent>()
		{
			@Override
			public void handle(ActionEvent event)
			{
				anchorPaneGame.getChildren().remove(labelNotification);				
			}
		});

		parallelTransition.play();
	}
	
	public void addMovingPowerUp(int row, int col, PowerUp powerUp)
	{		
		Label labelPowerUp = new Label();
		labelPowerUp.setStyle("-fx-background-image: url(\"de/bricked/resources/textures/powerups/" + powerUp.getID() + ".png\");" + "-fx-background-position: center center;" + "-fx-background-repeat: no-repeat;" + "-fx-background-size: cover");
		labelPowerUp.setAlignment(Pos.CENTER);
		labelPowerUp.setUserData(powerUp);

		labelPowerUp.setPrefWidth(gamePaneWidth / Board.WIDTH);
		labelPowerUp.setPrefHeight(gamePaneHeight / Board.HEIGHT);
		
		anchorPaneGame.getChildren().add(labelPowerUp);
		labelPowerUp.setTranslateX(col * (gamePaneWidth/Board.WIDTH));
		labelPowerUp.setTranslateY(row * (gamePaneHeight/Board.HEIGHT));
		
		movingPowerUps.add(labelPowerUp);
	}
	
	private void movePowerUps()
	{	
		for(Iterator<Label> iterator = movingPowerUps.iterator(); iterator.hasNext();)
		{
			Label currentLabel = iterator.next();
			PowerUp currentPowerUp = (PowerUp)currentLabel.getUserData();			
			currentLabel.setTranslateY(currentLabel.getTranslateY() + currentPowerUp.getSpeed());		
			
			//check collision with paddle
			Point2D labelPosition = new Point2D(currentLabel.getTranslateX(), currentLabel.getTranslateY());
			Point2D paddlePosition = new Point2D(labelPaddle.getLayoutX() + labelPaddle.getTranslateX(), labelPaddle.getLayoutY() + labelPaddle.getTranslateY());
			
			HitLocation hitLocation = game.collides(labelPosition, paddlePosition, paddle.getWidth(), paddle.getHeight(), true);					
			if(hitLocation != null && (hitLocation.equals(HitLocation.PADDLE) || hitLocation.equals(HitLocation.CORNER)))
			{		
				//TODO activate method
				//TODO check if timed
				Logger.log(LogLevel.DEBUG, "Collected PowerUp with ID = " + currentPowerUp.getID());
				currentPowerUp.activate();
				anchorPaneGame.getChildren().remove(currentLabel);
				iterator.remove();		
				continue;
			}			
			
			if(currentLabel.getTranslateY() + currentLabel.getHeight() >= gamePaneHeight)
			{
				//power up reached bottom wall
				anchorPaneGame.getChildren().remove(currentLabel);
				iterator.remove();				
			}
		}
	}
	
	private void checkPowerUps()
	{
		//TODO check timed powerups
//		for(Iterator<PowerUp> iterator = game.getActivatedPowerUps().iterator(); iterator.hasNext();)
//		{
//			)
//		}
	}

	public void showLabelFPS(boolean value)
	{
		labelFPS.setVisible(value);
	}

	public void showCommandLine()
	{
		try
		{
			levelSelectController.controller.controller.getCommandLine().showCommandLine("Debug Console", 400, 250, 400, 200, -1, -1, true);
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
		game.setTotalPoints(0);
		game.resetMultiplicator();
		game.resetPointsSinceLastMultiplicatorReset();
		game.setBoard(null);
		game.setLevelController(null);	
		game.setMovingPowerUps(new ArrayList<>());
		game.setActivatedPowerUps(new ArrayList<>());

		anchorPaneGame.requestFocus();
	}
}