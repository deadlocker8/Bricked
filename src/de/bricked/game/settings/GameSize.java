package de.bricked.game.settings;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;

public enum GameSize
{
    SMALL(800, 600),
    NORMAL(1024, 768),
    BIGGER(1280, 1024),
    BIG(1920, 1080),
    FULL_SCREEN();

    private int width;
    private int height;

    GameSize(int width, int height)
    {
        this.width = width;
        this.height = height;
    }

    GameSize()
    {
        GraphicsDevice gd = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice();
        this.width = gd.getDisplayMode().getWidth();
        this.height = gd.getDisplayMode().getHeight();
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