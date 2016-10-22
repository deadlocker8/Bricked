package de.bricked.utils;

import javafx.geometry.Point2D;

public class MathUtils
{
    public static double getAngle(Point2D directionBall, Point2D paddlePosition, double paddleWidth)
    {
        Point2D paddleRight = new Point2D(paddlePosition.getX()+paddleWidth, paddlePosition.getY());
        Point2D paddleVector = new Point2D(paddleRight.getX() - paddlePosition.getX(), paddleRight.getY() - paddlePosition.getY());
        double angle = directionBall.angle(paddleVector);
        return angle;
    }
}