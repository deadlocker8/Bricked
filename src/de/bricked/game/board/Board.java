package de.bricked.game.board;

import java.util.ArrayList;

import de.bricked.game.bricks.Brick;
import de.bricked.game.config.BrickType;
import de.bricked.game.levels.Level;
import de.bricked.game.levels.LevelPack;
import de.bricked.game.levels.LevelPackReader;

public class Board
{
	// first = row, second = column
	private ArrayList<ArrayList<Brick>> bricks;
	public static final int WIDTH = 18;
	public static final int HEIGHT = 26;

	public Board(Level level)
	{
		init();
		String boardString = level.getBoard();
		// parse board -> create bricks
		String[] bricksAndPowerArray = boardString.split(" ");
		ArrayList<Brick> loadedBricks = new ArrayList<>();
		for(String bricksAndPower : bricksAndPowerArray)
		{
			String brickValue = bricksAndPower.substring(0, 1);
			int powerUp = Integer.parseInt(bricksAndPower.substring(1));
			Brick currentBrick = null;
			// PowerUp currentPowerUp = null;
			// TODO IMPLEMENT POWERUP
			switch(powerUp)
			{
				case 0: // powerUp = new ExplosiveMegaPowerUp() :D
					break;
				case 1:
					break;
				case 2:
					break;
				case 3:
					break;
				case 4:
					break;
				case 5:
					break;
				case 6:
					break;
				default: //
					break;
			}

			switch(brickValue)
			{
				case "N":
					currentBrick = new Brick(BrickType.NORMAL);
					break;
				case "A":
					currentBrick = new Brick(BrickType.AIR);
					break;
				case "S":
					currentBrick = new Brick(BrickType.SOLID);
					break;
				case "H":
					currentBrick = new Brick(BrickType.HARD);
					break;
				case "E":
					currentBrick = new Brick(BrickType.EXTRA_HARD);
					break;
				case "I":
					currentBrick = new Brick(BrickType.INVISIBLE);
					break;
				case "T":
					currentBrick = new Brick(BrickType.TNT);
					break;
				default:
					currentBrick = new Brick(BrickType.AIR);
					break;
			}

			loadedBricks.add(currentBrick);
		}

		int currentRowNumber = 0;
		ArrayList<Brick> currentRow = new ArrayList<>();
		for(int i = 0; i < loadedBricks.size(); i++)
		{
			currentRow.add(loadedBricks.get(i));
			if(currentRow.size() == WIDTH)
			{
				bricks.set(currentRowNumber, currentRow);
				currentRow = new ArrayList<>();
				currentRowNumber++;
			}
		}
		print();
	}

	private ArrayList<Brick> getRemainingBricks()
    {
        ArrayList<Brick> remainingBricks = new ArrayList<>();
        for(ArrayList<Brick> row : bricks)
        {
            for(Brick currentBrick : row)
            {
                if(!(currentBrick.getType().equals(BrickType.AIR)) && !(currentBrick.getType().equals(BrickType.SOLID)))
                {
                    remainingBricks.add(currentBrick);
                }
            }
        }
        return remainingBricks;
    }
	
	public int getNumberOfRemainingBricks()
	{
		return getRemainingBricks().size();
	}

	private void init()
	{
		bricks = new ArrayList<ArrayList<Brick>>();

		for(int i = 0; i < HEIGHT; i++)
		{
			ArrayList<Brick> currentRow = new ArrayList<>();
			for(int k = 0; k < HEIGHT; k++)
			{
				currentRow.add(new Brick(BrickType.AIR));
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

		if(hittedBrick.getType().equals(BrickType.AIR))
		{
			return;
		}

		// block was destroyed
		if(hittedBrick.hit(instantDestroy))
		{
			bricks.get(row).set(col, new Brick(BrickType.AIR));

			if(hittedBrick.getType().equals(BrickType.TNT))
			{
				explodeBrick(row, col);
			}

			if(hittedBrick.getPowerUp() != null)
			{
				//TODO deploy PowerUp
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
				if(i >= 0 && i < (HEIGHT - 1))
				{
					if(k >= 0 && k < (WIDTH - 1))
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
			for(int k = 0; k < WIDTH; k++)
			{
				b.append(bricks.get(i).get(k).getType().getID());
				b.append(" ");
			}
			b.append("\n");
		}

		return b.toString();
	}

	public static void main(String[] args)
	{
		LevelPackReader levelPackReader = new LevelPackReader("default.json");
		LevelPack levelPack = levelPackReader.read();
		Board board = new Board(levelPack.getLevels().get(0));
		System.out.println(board.print());
	}

	@Override
	public String toString()
	{
		return "Board [WIDTH=" + WIDTH + ", HEIGHT=" + HEIGHT + ", bricks=" + bricks + "]";
	}
}