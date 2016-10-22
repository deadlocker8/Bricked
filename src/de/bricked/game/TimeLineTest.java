package de.bricked.game;

import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.geometry.Bounds;
import javafx.geometry.Point2D;
import javafx.scene.Scene;
import javafx.scene.effect.Lighting;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

public class TimeLineTest extends Application
{
	private AnimationTimer timer;

	double width = 500;
	double height = 500;

	double maxWidth;
	double maxHeight;
	private Point2D direction = new Point2D(2, 4);
	private double ballRadius = 10;

	private double rowHeight = 50;
	private double columnWidth = 50;

	private Rectangle rect;

	private boolean collision = false;
	private boolean wallCollision = false;

	final int TARGET_FPS = 60;
	final long OPTIMAL_TIME = 1000000000 / TARGET_FPS;
	private int fps;

	@Override
	public void start(Stage stage)
	{
		AnchorPane p = new AnchorPane();
		Scene scene = new Scene(p, width, height);
		stage.setScene(scene);
		stage.setResizable(false);
		stage.setOnCloseRequest(new EventHandler<WindowEvent>()
		{			
			@Override
			public void handle(WindowEvent event)
			{
				timer.stop();				
			}
		});

		AnchorPane gamePane = new AnchorPane();
		p.getChildren().add(gamePane);
		gamePane.setStyle("-fx-border-color: black");

		AnchorPane.setTopAnchor(gamePane, 10.0);
		AnchorPane.setRightAnchor(gamePane, 10.0);
		AnchorPane.setBottomAnchor(gamePane, 10.0);
		AnchorPane.setLeftAnchor(gamePane, 10.0);

		final Circle circle = new Circle(ballRadius, Color.rgb(156, 216, 255));
		circle.setEffect(new Lighting());
		final StackPane stack = new StackPane();
		stack.getChildren().addAll(circle);
		stack.setLayoutX(0);
		stack.setLayoutY(39);

		rect = new Rectangle(columnWidth, rowHeight);
		rect.setFill(Color.GREEN);
		rect.setTranslateX(100);
		rect.setTranslateY(100);
		gamePane.getChildren().add(rect);

		gamePane.getChildren().add(stack);
		stage.show();

		maxWidth = gamePane.getWidth();
		maxHeight = gamePane.getHeight();

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
				stack.setTranslateX(stack.getTranslateX() + direction.getX() * delta);
				stack.setTranslateY(stack.getTranslateY() + direction.getY() * delta);	
				
				//hit detection
				HitLocation hitLocation = hitsWall(stack.getLayoutX(), stack.getLayoutY(), stack.getTranslateX(), stack.getTranslateY(), direction);
				//if ball collides with border then brick collisions are irrelevant
				if(hitLocation != null)
				{
					direction = reflectBall(hitLocation, direction);
				}
				//ball doesn't collide with border --> check collision with bricks
				else
				{
					//TODO loop over all non air bricks
					//DEBUG replace rect with currentBrick					
					hitLocation = collides(stack.getBoundsInParent(), stack.getLayoutX(), stack.getLayoutY(), stack.getTranslateX(), stack.getTranslateY(), rect, false);
					if(hitLocation != null)
					{
						direction = reflectBall(hitLocation, direction);
					}
				}
			
				try
				{
					Thread.sleep( (previousTime-System.nanoTime() + OPTIMAL_TIME)/1000000);
				}
				catch(Exception e)
				{
					e.printStackTrace();
				}		
			}
		};

		timer.start();
	}

	private Point2D reflectBall(HitLocation hitLocation, Point2D direction)
	{
		switch(hitLocation)
		{
			case TOP:
				return new Point2D(direction.getX(), - direction.getY());

			case RIGHT:
				return new Point2D( - direction.getX(), direction.getY());

			case BOTTOM:
				return new Point2D(direction.getX(), - direction.getY());

			case LEFT:
				return new Point2D( - direction.getX(), direction.getY());

			case CORNER:
				return new Point2D( - direction.getX(), - direction.getY());
		}

		return null;
	}

	private HitLocation hitsWall(double ballLayoutX, double ballLayoutY, double ballTranslateX, double ballTranslateY, Point2D direction)
	{
		if(wallCollision)
		{
			wallCollision = false;
			return null;
		}

		// collides with top right corner
		if((ballLayoutX + ballTranslateX + ballRadius * 2) >= maxWidth && (ballLayoutY + ballTranslateY) <= 0)
		{
			wallCollision = true;
			System.out.println("TOP-RIGHT-CORNER");
			return HitLocation.CORNER;
		}

		// collides with top left corner
		if((ballLayoutX + ballTranslateX) <= 0 && (ballLayoutY + ballTranslateY) <= 0)
		{
			wallCollision = true;
			System.out.println("TOP-LEFT-CORNER");
			return HitLocation.CORNER;
		}

		// collides with right wall
		if((ballLayoutX + ballTranslateX + ballRadius * 2) >= maxWidth)
		{
			wallCollision = true;
			System.out.println("RIGHT-WALL");
			return HitLocation.LEFT;
		}

		// collides with left wall
		if((ballLayoutX + ballTranslateX) <= 0)
		{
			wallCollision = true;
			System.out.println("LEFT-WALL");
			return HitLocation.RIGHT;
		}

		// collides with bottom wall
		// TODO life lost
		if((ballLayoutY + ballTranslateY + ballRadius * 2) >= maxHeight)
		{
			wallCollision = true;
			System.out.println("BOTTOM-WALL");
			return HitLocation.TOP;
		}

		// collides with top wall
		if((ballLayoutY + ballTranslateY) <= 0)
		{
			wallCollision = true;
			System.out.println("TOP-WALL");
			return HitLocation.BOTTOM;
		}

		wallCollision = false;
		return null;			
	}
	
	private HitLocation collides(Bounds ballBoundsInParent, double ballLayoutX, double ballLayoutY, double ballTranslateX, double ballTranslateY, Rectangle brick, boolean isPaddle)
	{
		// DEBUG replace rect with brick in row and col
		if(brick.getBoundsInParent().intersects(ballBoundsInParent))
		{
			if(collision)
			{
				return null;
			}

			collision = true;

			// find collided side
			Point2D centerBrick = new Point2D(brick.getLayoutX() + brick.getTranslateX() + columnWidth / 2, brick.getLayoutY() + brick.getTranslateY() + rowHeight / 2);
			Point2D centerBall = new Point2D(ballLayoutX + ballTranslateX + ballRadius, ballLayoutY + ballTranslateY + ballRadius);

			// Minkowski sum
			double wy = (columnWidth + ballRadius * 2) * (centerBrick.getY() - centerBall.getY());
			double hx = (rowHeight + ballRadius * 2) * (centerBrick.getX() - centerBall.getX());			

			if(Math.abs(wy - hx) < 0.00001)
			{
				// ball flies in 45 degree angle
				if(Math.abs(direction.getX()) == Math.abs(direction.getY()))
				{
					System.out.println("CORNER");
					return HitLocation.CORNER;
				}
				else
				{
					System.out.println("CORNER-RIGHT");
					return HitLocation.RIGHT;
				}
			}

			if(wy > hx)
			{
				if(wy > - hx)
				{
					if(isPaddle)
					{
						//TODO reflect on paddle
					}
					else
					{
						System.out.println("TOP");
						return HitLocation.TOP;
					}
				}
				else
				{
					if(isPaddle)
					{
						System.out.println("PADDLE-RIGHT");					
						return HitLocation.CORNER;
					}
					else
					{
						System.out.println("RIGHT");					
						return HitLocation.RIGHT;
					}
				}
			}
			else
			{
				if(wy > - hx)
				{
					if(isPaddle)
					{
						System.out.println("PADDLE-LEFT");					
						return HitLocation.CORNER;
					}
					else
					{
						System.out.println("LEFT");					
						return HitLocation.LEFT;
					}
				}
				else
				{
					System.out.println("BOTTOM");
					return HitLocation.BOTTOM;
				}
			}
		}
		else
		{
			collision = false;
		}

		return null;
	}

	public static void main(String[] args)
	{
		launch(args);
	}
}