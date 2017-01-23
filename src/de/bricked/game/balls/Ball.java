package de.bricked.game.balls;

import javafx.geometry.Point2D;
import logger.LogLevel;
import logger.Logger;

public class Ball
{		
	private BallType type;
	private double gameHeight;
	private double ballRadius;
	private Point2D direction;
	private final double START_ANGLE = 40;
	
	public Ball(BallType type, double gameHeight)
	{	
		this.type = type;
		this.gameHeight = gameHeight;
		this.ballRadius = (gameHeight * type.getSizeFactor()) / 2;		
		this.direction = new Point2D(0, 0);
	}
	
	public void startBallToRight()
	{
		direction = new Point2D((gameHeight * type.getSpeedFactor()) * Math.cos(START_ANGLE), - ((gameHeight *  type.getSpeedFactor()) * Math.sin(START_ANGLE)));
	}
	
	public void startBallToLeft()
	{
		direction = new Point2D(- ((gameHeight * type.getSpeedFactor()) * Math.cos(START_ANGLE)), - ((gameHeight * type.getSpeedFactor()) * Math.sin(START_ANGLE)));
	}

	public BallType getType()
	{
		return type;
	}

	public double getBallRadius()
	{
		return ballRadius;
	}

	public void setBallRadius(double ballRadius)
	{
		this.ballRadius = ballRadius;
	}

	public Point2D getDirection()
	{
		return direction;
	}

	public void setDirection(Point2D direction)
	{
		this.direction = direction;
	}
}