package de.bricked.game.board;

import java.util.ArrayList;

import de.bricked.game.bricks.AirBrick;
import de.bricked.game.bricks.Brick;
import de.bricked.game.bricks.TNTBrick;
import de.bricked.game.levels.Level;

public class Board
{
	//first = row, second = column
	private ArrayList<ArrayList<Brick>> bricks;
	public static final int WIDTH = 18;
	public static final int HEIGHT = 26;
		
	public Board(Level level)
	{
		init();
		
		level.getBoard();
		
		
	}
	
	private void init()
	{
		bricks = new ArrayList<ArrayList<Brick>>();
		
		for(int i = 0; i < HEIGHT; i++)
		{
			ArrayList<Brick> currentRow = new ArrayList<>();
			for(int k = 0; k < HEIGHT; k++)
			{
				currentRow.add(new AirBrick());			
			}
			bricks.add(currentRow);
		}	
	}

	public ArrayList<ArrayList<Brick>> getBricks()
	{
		return bricks;
	}

	public void setBricks(ArrayList<ArrayList<Brick>> bricks)
	{
		this.bricks = bricks;
	}

	public int getWidth()
	{
		return WIDTH;
	}

	public int getHeight()
	{
		return HEIGHT;
	}
	
	public void hitBrick(int row, int col, boolean instantDestroy)
	{
		Brick hittedBrick = bricks.get(row).get(col);
		
		if(hittedBrick instanceof AirBrick)
		{
			return;
		}
		
		//block was destroyed
		if(hittedBrick.hit(instantDestroy))
		{		
			bricks.get(row).set(col, new AirBrick());
			
			if(hittedBrick instanceof TNTBrick)
			{				
				explodeBrick(row, col);				
			}
			
			if(hittedBrick.getPowerUp() != null)
			{
				//deploy PowerUp
			}
		}	
	}	
	
	private void explodeBrick(int row, int col)
	{
		int currentRow = row - 1;
		int currentCol = col - 1;
		
		for(int i = currentRow; i <= currentRow + 2; i++)
		{
			for(int k = currentCol; k <= currentCol + 2; k++)
			{				
				if(i >= 0 && i < (HEIGHT -1))
				{
					if(k >= 0 && k < (WIDTH -1))
					{
						hitBrick(i, k, true);
					}
				}
			}
		}
	}

	public String print()
	{
		StringBuilder b = new StringBuilder();
		for(int i = 0; i < HEIGHT; i++)
		{			
			for(int k = 0; k < HEIGHT; k++)
			{
				b.append(bricks.get(i).get(k).getID());
				b.append(" ");				
			}		
			b.append("\n");
		}	
		
		return b.toString();
	}	

	@Override
	public String toString()
	{
		return "Board [WIDTH=" + WIDTH + ", HEIGHT=" + HEIGHT + ", bricks=" + bricks + "]";
	}	
}