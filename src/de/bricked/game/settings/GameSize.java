package de.bricked.game.settings;

public enum GameSize
{
    SMALL(800, 600),
    NORMAL(1024, 768),
    BIGGER(1280, 1024),
    BIG(1920, 1080);

    private int width;
    private int height;

    GameSize(int width, int height)
    {
        this.width = width;
        this.height = height;
    }

    public int getWidth()
    {
        return width;
    }

    public int getHeight()
    {
        return height;
    }
}