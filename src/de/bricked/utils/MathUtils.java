package de.bricked.utils;

import javafx.geometry.Point2D;

public class MathUtils
{
    /*
    Point2D directionBall, Point2D paddlePosition, double paddleWidth

javafx.geometry.Point2D

return angle
     */

    public static double getAngle(Point2D directionBall, Point2D paddlePosition, double paddleWidth)
    {
        Point2D paddleRight = new Point2D(paddlePosition.getX()+paddleWidth, paddlePosition.getY());
        double angle = directionBall.angle(paddleRight);
        return angle;
    }
}
