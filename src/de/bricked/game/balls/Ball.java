package de.bricked.game.balls;

import javafx.geometry.Point2D;

public class Ball
{		
	private BallType type;
	private double ballRadius;
	private Point2D direction;
	private final double START_ANGLE = 40;
	
	public Ball(BallType type)
	{	
		this.type = type;
		this.ballRadius = type.getRadius();
		this.direction = new Point2D(0, 0);
	}
	
	public void startBallToRight()
	{
		direction = new Point2D(type.getSpeedFactor() * Math.cos(START_ANGLE), - (type.getSpeedFactor() * Math.sin(START_ANGLE)));
	}
	
	public void startBallToLeft()
	{
		direction = new Point2D(- (type.getSpeedFactor() * Math.cos(START_ANGLE)), - (type.getSpeedFactor() * Math.sin(START_ANGLE)));
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