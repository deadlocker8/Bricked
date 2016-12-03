package de.brickedleveleditor.ui;


import de.bricked.game.bricks.BrickType;
import de.bricked.game.powerups.PowerUpType;
import javafx.scene.Node;
import javafx.scene.control.Label;

public class BrickLabel extends Label
{
    private BrickType brickType;
    private PowerUpType powerUpType;

    public BrickLabel()
    {
    }

    public BrickLabel(BrickType brickType, PowerUpType powerUpType)
    {
        super(brickType.getID() + powerUpType.getID());
        this.brickType = brickType;
        this.powerUpType = powerUpType;

    }

    public BrickLabel(String text, Node graphic)
    {
        super(text, graphic);
    }

    public BrickType getBrickType()
    {
        return brickType;
    }

    public void setBrickType(BrickType brickType)
    {
        this.brickType = brickType;
        setText(this.brickType.getID()+this.powerUpType.getID());
    }

    public PowerUpType getPowerUpType()
    {
        return powerUpType;
    }

    public void setPowerUpType(PowerUpType powerUpType)
    {
        this.powerUpType = powerUpType;
        setText(this.brickType.getID()+this.powerUpType.getID());
    }
}
