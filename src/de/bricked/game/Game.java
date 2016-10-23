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
				return new Point2D(direction.getX(), -direction.getY());

			case RIGHT:
				return new Point2D(-direction.getX(), direction.getY());

			case BOTTOM:
				return new Point2D(direction.getX(), -direction.getY());

			case LEFT:
				return new Point2D(-direction.getX(), direction.getY());

			case CORNER:
				return new Point2D(-direction.getX(), -direction.getY());		

			default:
				return direction;
		}
	}

	public Point2D reflectOnPaddle(Point2D direction, double factor)
	{
		double influenceX = 0.75;

		double totalSpeed = Math.sqrt(direction.getX() * direction.getX() + direction.getY() * direction.getY());
		double newXSpeed = totalSpeed * factor * influenceX;
		double newYSpeed = Math.sqrt(totalSpeed * totalSpeed - newXSpeed * newXSpeed);

		return new Point2D(-newXSpeed, -newYSpeed);
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
			// TODO replace with LIFE_LOST
			return HitLocation.LIFE_LOST;
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

	public HitLocation collides(Bounds ballBoundsInParent, Point2D ballPosition, Bounds brickBoundsInParent, Point2D brickPosition, double brickWidth,
			double brickHeight, boolean isPaddle)
	{
		if(brickBoundsInParent.intersects(ballBoundsInParent))
		{
			if(collision)
			{
				return null;
			}

			collision = true;

			// find collided side
			Point2D centerBrick = new Point2D(brickPosition.getX() + brickWidth / 2, brickPosition.getY() + brickHeight / 2);
			Point2D centerBall = new Point2D(ballPosition.getX() + ball.getBallRadius(), ballPosition.getY() + ball.getBallRadius());

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
				if(wy > -hx)
				{
					if(isPaddle)
					{
						System.out.println("PADDLE");
						return HitLocation.PADDLE;
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
				if(wy > -hx)
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

	public boolean collidesWithRightPaddleSide(Point2D ballPosition, Point2D paddlePosition, double paddleWidth)
	{
		Point2D paddleCenter = new Point2D(paddlePosition.getX() + paddleWidth / 2, paddlePosition.getY());
		Point2D ballCenter = new Point2D(ballPosition.getX() + ball.getBallRadius(), ballPosition.getY() + ball.getBallRadius());

		if(ballCenter.getX() >= paddleCenter.getX())
		{
			return true;
		}
		else
		{
			return false;
		}
	}

	public double getDistanceToPaddleCenter(Point2D ballPosition, Point2D paddlePosition, double paddleWidth)
	{
		Point2D paddleCenter = new Point2D(paddlePosition.getX() + paddleWidth / 2, paddlePosition.getY());
		Point2D ballCenter = new Point2D(ballPosition.getX() + ball.getBallRadius(), ballPosition.getY() + ball.getBallRadius());

		return (paddleCenter.getX() - ballCenter.getX()) / (paddleWidth / 2);
	}

	public Point2D getCorrectedBallPosition(HitLocation hitLocation, Point2D ballPosition, Point2D brickPosition, double brickWidth, double brickHeight)
	{
		switch(hitLocation)
		{
			case TOP:
				return new Point2D(ballPosition.getX(), brickPosition.getY() - ball.getBallRadius() * 2 - 1);
				
			case PADDLE:
				return new Point2D(ballPosition.getX(), brickPosition.getY() - ball.getBallRadius() * 2 - 1);
				
			case RIGHT:
				return new Point2D(brickPosition.getX() + brickWidth, ballPosition.getY() + 1);

			case BOTTOM:
				return new Point2D(ballPosition.getX(), brickPosition.getY() + brickHeight + 1);

			case LEFT:
				return new Point2D(brickPosition.getX() - ball.getBallRadius() * 2 - 1, ballPosition.getY());

			case CORNER:
				if(ballPosition.getX() + ball.getBallRadius() > brickPosition.getX() + brickWidth / 2)
				{					
					//ball is on right	
					return new Point2D(brickPosition.getX() + brickWidth, ballPosition.getY() + 1);
				}
				else
				{					
					//ball is on left
					return new Point2D(brickPosition.getX() - ball.getBallRadius() * 2 - 1, ballPosition.getY());
				}
				
			default:
				return null;
		}
	}
}