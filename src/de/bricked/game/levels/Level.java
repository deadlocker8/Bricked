package de.bricked.game.levels;


public class Level
{
    private String name;
    private String author;
    private int position;
    private int difficulty;
    private int startLives;
    private int initPadSize;
    private String board;

    public Level(String name, String author, int position, int difficulty, int startLives, int initPadSize, String board)
    {
        this.name = name;
        this.author = author;
        this.position = position;
        this.difficulty = difficulty;
        this.startLives = startLives;
        this.initPadSize = initPadSize;
        this.board = board;
    }
}
