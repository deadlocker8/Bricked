package de.bricked.game.settings;
import javafx.stage.Screen;

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
        this.width = (int)(Screen.getPrimary().getVisualBounds().getWidth());
        this.height = (int)(Screen.getPrimary().getVisualBounds().getHeight());
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
