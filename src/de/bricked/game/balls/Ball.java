package de.bricked.game.balls;

import javafx.geometry.Point2D;

public class Ball
{	
	protected String textureID;
	protected double ballRadius;
	protected Point2D direction;
	
	public Ball()
	{
		this.textureID = "";
		this.ballRadius = 20;
		this.direction = new Point2D(0, 0);
	}

	public String getTextureID()
	{
		return textureID;
	}

	
	
	
}