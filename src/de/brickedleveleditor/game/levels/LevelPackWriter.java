package de.brickedleveleditor.game.levels;
import de.bricked.game.bricks.Brick;
import de.bricked.game.levels.Level;
import de.bricked.game.paddle.PaddleSize;
import de.bricked.game.powerups.PowerUp;


import java.util.ArrayList;

public class LevelPackWriter
{
    private ArrayList<Level> levels;

    public LevelPackWriter()
    {
        levels = new ArrayList<>();
    }

    public void writePack()
    {

    }

    private String getBoard(ArrayList<Brick> bricks)
    {
        StringBuilder builder = new StringBuilder();
        for(Brick brick : bricks)
        {
            String brickName = brick.getType().getID();
            String powerupName = "";
            PowerUp powerUp = brick.getPowerUp();
            if(powerUp == null)
            {
                powerupName = "0";
            }
            else
            {
                powerupName = String.valueOf(powerUp.getID());
            }
            builder.append(brickName + powerupName + " ");
        }
        builder.delete(builder.length()-1,builder.length());
        System.out.println(builder.toString());
        return builder.toString();
    }

    public void addLevel(String levelname, String author, int position, int difficulty, int startLives, PaddleSize paddleSize, ArrayList<Brick> bricks)
    {
        Level level = new Level(levelname, author, position, difficulty, startLives, paddleSize, getBoard(bricks));
        levels.add(level);
    }

    public ArrayList<Level> getLevels()
    {
        return levels;
    }
}
