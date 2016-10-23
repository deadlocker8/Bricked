package de.bricked.game.paddle;

import java.util.ArrayList;

public enum PaddleSize
{
	SMALL(0.1),
	MEDIUM(0.2),
	LARGE(0.3),
	EXTRA_LARGE(0.4);

	private double sizeFactor;
    private static PaddleSize[] paddleSizes = PaddleSize.values();
		
    PaddleSize(double sizeFactor)
	{				
		this.sizeFactor = sizeFactor;
	}

	public static PaddleSize getPaddleSize(double size)
    {
        for(PaddleSize paddleSize : paddleSizes)
        {
            if(paddleSize.getSizeFactor() == size)
            {
                return paddleSize;
            }
        }
        return PaddleSize.EXTRA_LARGE;
    }
	
	public double getSizeFactor()
	{
		return sizeFactor;
	}

	private static int getPaddleIndex(PaddleSize paddleSize)
    {
        //find index of paddle in array
        for(int i=0; i < paddleSizes.length; i++)
        {
            if(paddleSize == paddleSizes[i])
            {
                return i;
            }
        }
        return -1;
    }

	public static PaddleSize getNextSmaller(PaddleSize currentSize)
    {
        if(currentSize.getSizeFactor() > paddleSizes[0].getSizeFactor())
        {
            //returns next smaller paddle, if available
            return paddleSizes[getPaddleIndex(currentSize)-1];
        }
        else
        {
            return paddleSizes[0];
        }
    }

    public static PaddleSize getNextBigger(PaddleSize currentSize)
    {
        if(currentSize.getSizeFactor() < paddleSizes[paddleSizes.length-1].getSizeFactor())
        {
            //returns next bigger paddle, if available
            return paddleSizes[getPaddleIndex(currentSize)+1];
        }
        else
        {
            return paddleSizes[paddleSizes.length-1];
        }
    }
}