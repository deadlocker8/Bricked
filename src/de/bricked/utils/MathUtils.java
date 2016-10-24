package de.bricked.utils;

import javafx.geometry.Point2D;

public class MathUtils
{	
    public static double getAngle(Point2D a, Point2D b)
    {
//    	double angle = Math.toDegrees(Math.atan2(b.getY(), b.getX()) - Math.atan2(a.getY(), a.getX()));
    	return Math.toDegrees(Math.acos(a.dotProduct(b) / (a.magnitude() * b.magnitude()))); 
    }
    public static Point2D normalize(Point2D direction)
    {
        double length = Math.sqrt(direction.getX() * direction.getX() + direction.getY() * direction.getY());
        double newX =  direction.getX() / length;
        double newY = direction.getY() / length;
        return new Point2D(newX, newY);
    }
}