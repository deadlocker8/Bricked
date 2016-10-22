package de.bricked.game;

import de.bricked.game.balls.Ball;
import de.bricked.game.levels.Level;
import de.bricked.game.levels.LevelPack;
import de.bricked.game.settings.Settings;
import javafx.geometry.Bounds;
import javafx.geometry.Point2D;

public class Game
{
	private Settings settings;
	private LevelPack levelPack;
	private Level level;
	private int livesRemaining;
	private Ball ball;
	private boolean collision = false;
	private boolean wallCollision = false;
	private int points;

	public Game()
	{		
		this.settings = new Settings();				
		this.levelPack = null;
		this.level = null;	
		this.livesRemaining = 0;
		this.ball = null;
		this.points = 0;
	}
	
	public Settings getSettings()
	{
		return settings;
	}

	public void setSettings(Settings settings)
	{
		this.settings = settings;
	}

	public void setLevelPack(LevelPack levelPack)
	{
		this.levelPack = levelPack;
	}

	public LevelPack getLevelPack()
	{
		return levelPack;
	}
	
	public void setLevel(Level level)
	{
		this.level = level;
	}
	
	public Level getLevel()
	{
		return level;
	}

	public int getLivesRemaining()
	{
		return livesRemaining;
	}

	public void setLivesRemaining(int livesRemaining)
	{
		this.livesRemaining = livesRemaining;
	}

	public Ball getBall()
	{
		return ball;
	}

	public void setBall(Ball ball)
	{
		this.ball = ball;
	}	
	
	public int getPoints()
	{
		return points;
	}

	public void setPoints(int points)
	{
		this.points = points;
	}

	public Point2D reflectBall(HitLocation hitLocation, Point2D direction)
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
				
			case LIFE_LOST:
				return direction;
		}

		return null;
	}

	public HitLocation hitsWall(double gamePaneWidth, double gamePaneHeight, double ballLayoutX, double ballLayoutY, double ballTranslateX, double ballTranslateY, Point2D direction)
	{
		if(wallCollision)
		{
			wallCollision = false;
			return null;
		}

		// collides with top right corner
		if((ballLayoutX + ballTranslateX + ball.getBallRadius() * 2) >= gamePaneWidth && (ballLayoutY + ballTranslateY) <= 0)
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
		if((ballLayoutX + ballTranslateX + ball.getBallRadius() * 2) >= gamePaneWidth)
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
		if((ballLayoutY + ballTranslateY + ball.getBallRadius() * 2) >= gamePaneHeight)
		{
			wallCollision = true; 
			System.out.println("BOTTOM-WALL");
			//TODO replace with LIFE_LOST
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
	
	public HitLocation collides(Bounds ballBoundsInParent, double ballLayoutX, double ballLayoutY, double ballTranslateX, double ballTranslateY, Bounds brickBoundsInParent, double brickLayoutX, double brickLayoutY, double brickTranslateX, double brickTranslateY, double brickWidth, double brickHeight, boolean isPaddle)
	{			
		if(brickBoundsInParent.intersects(ballBoundsInParent))
		{
			if(collision)
			{
				return null;
			}

			collision = true;

			// find collided side
			Point2D centerBrick = new Point2D(brickLayoutX + brickTranslateX + brickWidth / 2, brickLayoutY + brickTranslateY + brickHeight / 2);
			Point2D centerBall = new Point2D(ballLayoutX + ballTranslateX + ball.getBallRadius(), ballLayoutY + ballTranslateY + ball.getBallRadius());

			// Minkowski sum
			double wy = (brickWidth + ball.getBallRadius() * 2) * (centerBrick.getY() - centerBall.getY());
			double hx = (brickHeight + ball.getBallRadius() * 2) * (centerBrick.getX() - centerBall.getX());			

			if(Math.abs(wy - hx) < 0.00001)
			{
				// ball flies in 45 degree angle
				if(Math.abs(ball.getDirection().getX()) == Math.abs(ball.getDirection().getY()))
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
}