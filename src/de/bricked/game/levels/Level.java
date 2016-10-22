package de.bricked.game.levels;

import de.bricked.game.paddle.PaddleSize;

public class Level
{
    private String name;
    private String author;
    private int position;
    private int difficulty;
    private int startLives;
    private PaddleSize initPadSize;
    private String board;

    public Level(String name, String author, int position, int difficulty, int startLives, PaddleSize initPadSize, String board)
    {
        this.name = name;
        this.author = author;
        this.position = position;
        this.difficulty = difficulty;
        this.startLives = startLives;
        this.initPadSize = initPadSize;
        this.board = board;
    }

	public String getName()
	{
		return name;
	}

	public String getAuthor()
	{
		return author;
	}

	public int getPosition()
	{
		return position;
	}

	public int getDifficulty()
	{
		return difficulty;
	}

	public int getStartLives()
	{
		return startLives;
	}

	public PaddleSize getInitPadSize()
	{
		return initPadSize;
	}

	public String getBoard()
	{
		return board;
	}
}