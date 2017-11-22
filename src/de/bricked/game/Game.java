package de.bricked.game;

import java.util.ArrayList;

import de.bricked.game.balls.Ball;
import de.bricked.game.board.Board;
import de.bricked.game.levels.Level;
import de.bricked.game.levels.LevelPack;
import de.bricked.game.powerups.PowerUp;
import de.bricked.game.settings.Settings;
import de.bricked.game.sound.SoundHandler;
import de.bricked.game.sound.SoundType;
import de.bricked.ui.LevelController;
import javafx.geometry.Point2D;
import logger.Logger;

public class Game
{
	private final int MAX_LIVES = 7;
	private Settings settings;
	private LevelPack levelPack;
	private Level level;
	private int livesRemaining;
	private Ball ball;
	private boolean collision = false;
	private boolean wallCollision = false;
	private int totalPoints;
	private Board board;	
	private final double speedIncreasePerPaddleHitFactor = 1.05;
	private int pointsSinceLastMultiplicatorReset;
	private int multiplicator;
	private LevelController levelController;
	private ArrayList<PowerUp> movingPowerUps;	
	private SoundHandler soundHandler;

	public Game()
	{
		this.settings = new Settings(true);			
		this.soundHandler = new SoundHandler(settings.getVolume(), settings.isMuted());			
		this.levelPack = null;
		this.level = null;
		this.livesRemaining = 0;
		this.ball = null;
		this.totalPoints = 0;
		this.board = null;
		this.multiplicator = 0;
		this.pointsSinceLastMultiplicatorReset = 0;
		this.levelController = null;
		this.movingPowerUps = new ArrayList<>();	
	}		

	public int getMaxLives()
	{
		return MAX_LIVES;
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

	public int getTotalPoints()
	{
		return totalPoints;
	}

	public void setTotalPoints(int totalPoints)
	{
		this.totalPoints = totalPoints;
	}

	public Board getBoard()
	{
		return board;
	}

	public void setBoard(Board board)
	{
		this.board = board;
	}	

	public int getMultiplicator()
	{
		return multiplicator;
	}

	public void increaseMultiplicator()
	{
		this.multiplicator++;
	}
	
	public void resetMultiplicator()
	{
		this.multiplicator = 0;
	}	

	public int getPointsSinceLastMultiplicatorReset()
	{
		return pointsSinceLastMultiplicatorReset;
	}

	public void increasePointsSinceLastMultiplicatorReset(int points)
	{
		this.pointsSinceLastMultiplicatorReset += points;
	}
	
	public void resetPointsSinceLastMultiplicatorReset()
	{
		pointsSinceLastMultiplicatorReset = 0;
	}
	
	public void applyMultiplicator()
	{
		if(multiplicator > 1)
		{
			totalPoints += pointsSinceLastMultiplicatorReset * multiplicator;
			Logger.debug("Applied multiplicator: " + pointsSinceLastMultiplicatorReset + " x" + multiplicator + "  =  " + totalPoints);
			soundHandler.play(SoundType.MULTIPLICATOR);
		}
	}

	public LevelController getLevelController()
	{
		return levelController;
	}

	public void setLevelController(LevelController levelController)
	{
		this.levelController = levelController;
	}	

	public ArrayList<PowerUp> getMovingPowerUps()
	{
		return movingPowerUps;
	}

	public void setMovingPowerUps(ArrayList<PowerUp> movingPowerUps)
	{
		this.movingPowerUps = movingPowerUps;
	}
	
	public SoundHandler getSoundHandler()
	{
		return soundHandler;
	}	

	public void refreshSoundHandler()
	{
		soundHandler = new SoundHandler(settings.getVolume(), settings.isMuted());
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

	public Point2D reflectOnPaddle(Point2D direction, double factor, double gameHeight)
	{
		double influenceX = 0.75;		

		double totalSpeed = Math.sqrt(direction.getX() * direction.getX() + direction.getY() * direction.getY());
		totalSpeed = totalSpeed * speedIncreasePerPaddleHitFactor;
		if(totalSpeed > (gameHeight * ball.getType().getMaxTotalSpeed()))
		{
			
			totalSpeed = (gameHeight * ball.getType().getMaxTotalSpeed());
		}
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
			Logger.debug("TOP-RIGHT-CORNER");
			return HitLocation.CORNER;
		}

		// collides with top left corner
		if((ballLayoutX + ballTranslateX) <= 0 && (ballLayoutY + ballTranslateY) <= 0)
		{
			wallCollision = true;
			Logger.debug("TOP-LEFT-CORNER");
			return HitLocation.CORNER;
		}

		// collides with right wall
		if((ballLayoutX + ballTranslateX + ball.getBallRadius() * 2) >= gamePaneWidth)
		{
			wallCollision = true;
			Logger.debug("RIGHT-WALL");
			return HitLocation.LEFT;
		}

		// collides with left wall
		if((ballLayoutX + ballTranslateX) <= 0)
		{
			wallCollision = true;
			Logger.debug("LEFT-WALL");
			return HitLocation.RIGHT;
		}

		// collides with bottom wall
		if((ballLayoutY + ballTranslateY + ball.getBallRadius() * 2) >= gamePaneHeight)
		{
			wallCollision = true;
			Logger.debug("BOTTOM-WALL");
			return HitLocation.LIFE_LOST;
		}

		// collides with top wall
		if((ballLayoutY + ballTranslateY) <= 0)
		{
			wallCollision = true;
			Logger.debug("TOP-WALL");
			return HitLocation.BOTTOM;
		}

		wallCollision = false;
		return null;
	}

	public HitLocation collides(Point2D ballPosition, Point2D brickPosition, double brickWidth, double brickHeight, boolean isPaddle)
	{
		double dx = (ballPosition.getX() + ball.getBallRadius()) - (brickPosition.getX() + brickWidth / 2);
		double dy = (ballPosition.getY() + ball.getBallRadius()) - (brickPosition.getY() + brickHeight / 2);
		double width = (ball.getBallRadius() * 2 + brickWidth) / 2;
		double height = (ball.getBallRadius() * 2 + brickHeight) / 2;
		double crossWidth = width * dy;
		double crossHeight = height * dx;

		if(Math.abs(dx) <= width && Math.abs(dy) <= height)
		{
			if(collision)
			{
				return null;
			}
			
			collision = true;
			
			if(crossWidth > crossHeight)
			{
				if(crossWidth > (-crossHeight))
				{
					Logger.debug("BOTTOM");
					return HitLocation.BOTTOM;
				}
				else
				{
					if(isPaddle)
					{
						Logger.debug("PADDLE-LEFT");
						return HitLocation.CORNER;
					}
					else
					{
						Logger.debug("LEFT");
						return HitLocation.LEFT;
					}
				}
			}
			else
			{
				if(crossWidth > (-crossHeight))
				{
					if(isPaddle)
					{
						Logger.debug("PADDLE-RIGHT");
						return HitLocation.CORNER;
					}
					else
					{
						Logger.debug("RIGHT");
						return HitLocation.RIGHT;
					}
				}
				else
				{
					if(isPaddle)
					{
						Logger.debug("PADDLE");
						return HitLocation.PADDLE;
					}
					else
					{
						Logger.debug("TOP");
						return HitLocation.TOP;
					}
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
					// ball is on right
					return new Point2D(brickPosition.getX() + brickWidth, ballPosition.getY() + 1);
				}
				else
				{
					// ball is on left
					return new Point2D(brickPosition.getX() - ball.getBallRadius() * 2 - 1, ballPosition.getY());
				}

			default:
				return null;
		}
	}
	
	public Point2D getNewSpeedDirection(Point2D oldDirection, double speed)
	{		
		double oldSpeed =  Math.sqrt(oldDirection.getX() * oldDirection.getX()+ oldDirection.getY() * oldDirection.getY());		
		
		double newXSpeed = oldDirection.getX() * speed / oldSpeed;
		double newYSpeed = oldDirection.getY() * speed / oldSpeed;

		return new Point2D(newXSpeed, newYSpeed);
	}
}