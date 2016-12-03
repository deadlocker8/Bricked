package de.brickedleveleditor.ui;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Locale;
import java.util.ResourceBundle;

import de.bricked.game.bricks.Brick;
import de.bricked.game.bricks.BrickType;
import de.bricked.game.paddle.PaddleSize;
import de.bricked.game.powerups.PowerUpType;
import de.brickedleveleditor.game.levels.LevelPackWriter;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BackgroundPosition;
import javafx.scene.layout.BackgroundRepeat;
import javafx.scene.layout.BackgroundSize;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.RowConstraints;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Paint;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import logger.LogLevel;
import logger.Logger;
import tools.Worker;

public class Controller
{
    @FXML
    private AnchorPane anchorPane;
    @FXML
    private VBox sidebarVBox;
    @FXML
    private GridPane gridPane;
    @FXML private MenuItem saveMenuItem;
    private BrickType[] brickTypes = BrickType.values();
    private Image currentlySelectedBrickImage;
    private Image currentlySelectedPowerupImage;
    private final int WIDTH = 18;
    private final int HEIGHT = 18;

    public Stage stage;
    public final ResourceBundle bundle = ResourceBundle.getBundle("de/brickedleveleditor/main/", Locale.GERMANY);
    private HashMap<BrickType, Image> bricksTextures;
    private ArrayList<Image> powerupTextures;
    private LevelPackWriter levelPackWriter;

    public void init(Stage stage)
    {
        powerupTextures = new ArrayList<>();
        levelPackWriter = new LevelPackWriter();
        gridPane.setMaxSize(50, 50);
        this.stage = stage;
        bricksTextures = new HashMap<>();
        loadPowerupTextures();
        addPowerupsToVBox();
        loadBrickTextures();
        addBricksToVBox();
        fillGridPaneWithAirBricks();
        currentlySelectedBrickImage = bricksTextures.get(BrickType.HARD);
        currentlySelectedPowerupImage = powerupTextures.get(0);
        stage.setOnCloseRequest(new EventHandler<WindowEvent>()
        {
            public void handle(WindowEvent event)
            {
                Worker.shutdown();
                System.exit(0);
            }
        });

        saveMenuItem.setOnAction(new EventHandler<ActionEvent>()
        {
            @Override
            public void handle(ActionEvent event)
            {
                String levelName = "";
                String author = "";
                int position = 0;
                int difficulty = 1;
                int startLives = 1;
                PaddleSize initPadSize = PaddleSize.EXTRA_LARGE;
                levelPackWriter.addLevel(levelName, author, position, difficulty, startLives, initPadSize, getBrickArrayList());
            }
        });
    }

    private void loadPowerupTextures()
    {
        String fileExt = ".png";
        String rootPath = "de/bricked/resources/textures/powerups/";
        //powerup[0] means no powerup --> set to null
        powerupTextures.add(null);
        for (int i=1; i < PowerUpType.types.length; i++)
        {
            File powerupPath = new File(rootPath + PowerUpType.types[i].getID() + fileExt);
            try
            {
                Image image = new Image(powerupPath.getPath());
                powerupTextures.add(image);
            }
            catch (Exception e)
            {
                Logger.log(LogLevel.ERROR, Logger.exceptionToString(e));
            }
        }
    }

    private void loadBrickTextures()
    {
        String fileExt = ".png";
        String rootPath = "de/bricked/resources/textures/bricks/";
        for (BrickType brickType : brickTypes)
        {
            File brickPath = new File(rootPath + brickType.getTextureIDs()[0] + fileExt);
            System.out.println(brickPath.getAbsolutePath());
            try
            {
                Image image = new Image(brickPath.getPath());
                bricksTextures.put(brickType, image);
            }
            catch (Exception e)
            {
                Logger.log(LogLevel.ERROR, Logger.exceptionToString(e));
            }
        }
    }

    private HashMap<Image, BrickType> getReversedBrickHashMap()
    {
        HashMap<Image, BrickType> reversedHashMap = new HashMap<Image, BrickType>();
        for (BrickType key : bricksTextures.keySet())
        {
            reversedHashMap.put(bricksTextures.get(key), key);
        }
        return reversedHashMap;
    }

    public ArrayList<Brick> getBrickArrayList()
    {
        ArrayList<Brick> bricks = new ArrayList<>();
        Object[] gridPaneChildren = gridPane.getChildren().toArray();
        for(int i = 0; i < gridPaneChildren.length; i++)
        {
            if(gridPaneChildren[i] instanceof BrickLabel)
            {
                BrickLabel currentLabel = (BrickLabel)gridPaneChildren[i];
                Brick brick = new Brick(currentLabel.getBrickType(),
                        PowerUpType.getInstance(currentLabel.getPowerUpType()));
                bricks.add(brick);
            }
        }
        return bricks;
    }

    private void addBricksToVBox()
    {
        for (BrickType brickType : brickTypes)
        {
            Label brickLabel = new Label(brickType.toString());
            brickLabel.setBackground(getBackGroundFromImage(bricksTextures.get(brickType)));
            brickLabel.setOnMouseClicked(new EventHandler<MouseEvent>()
            {
                @Override
                public void handle(MouseEvent event)
                {
                    currentlySelectedBrickImage = bricksTextures.get(brickType);
                    Logger.log(LogLevel.DEBUG, brickType.toString() + " selected");
                }
            });
            HBox brickHBox = new HBox(brickLabel);
            sidebarVBox.getChildren().add(brickHBox);
        }
    }

    private void addPowerupsToVBox()
    {
        for(int i=1; i < powerupTextures.size(); i++)
        {
            Image powerupImage = powerupTextures.get(i);
            Label powerupLabel = new Label(PowerUpType.types[i].toString());
            powerupLabel.setOnMouseClicked(new EventHandler<MouseEvent>()
            {
                @Override
                public void handle(MouseEvent event)
                {
                    currentlySelectedPowerupImage = powerupImage;
                }
            });
            powerupLabel.setBackground(getBackGroundFromImage(powerupTextures.get(i)));
            HBox powerupHBox = new HBox(powerupLabel);
            VBox.setMargin(sidebarVBox, new Insets(50, 10, 10, 10));
            sidebarVBox.getChildren().add(powerupHBox);
        }
    }

    private Background getBackGroundFromImage(Image image)
    {
        BackgroundImage backgroundImage = new BackgroundImage(image,
                BackgroundRepeat.NO_REPEAT,BackgroundRepeat.NO_REPEAT,
                BackgroundPosition.CENTER,BackgroundSize.DEFAULT);
        Background background = new Background(backgroundImage);
        return background;
    }

    private void initGrid()
    {
        gridPane.getColumnConstraints().clear();
        double xPercentage = 1.0 / WIDTH;
        for (int i = 0; i < WIDTH; i++)
        {
            ColumnConstraints c = new ColumnConstraints();
            c.setPercentWidth(xPercentage * 100);
            gridPane.getColumnConstraints().add(c);
        }

        gridPane.getRowConstraints().clear();
        double yPercentage = 1.0 / HEIGHT;
        for (int i = 0; i < HEIGHT; i++)
        {
            RowConstraints c = new RowConstraints();
            c.setPercentHeight(yPercentage * 100);
            gridPane.getRowConstraints().add(c);
        }
        gridPane.setGridLinesVisible(true);
    }

    private void fillGridPaneWithAirBricks()
    {
        initGrid();
        for (int i = 0; i < WIDTH; i++)
        {
            for (int k = 0; k < HEIGHT; k++)
            {
                BrickType brickType = BrickType.AIR;
                PowerUpType powerUpType = PowerUpType.NONE;
                Image image = bricksTextures.get(brickType);
                BrickLabel label = new BrickLabel(brickType, powerUpType);
                label.setMinSize(30, 25);
                label.setBackground(getBackGroundFromImage(image));
                gridPane.add(label, k, i);
                label.setAlignment(Pos.CENTER);
                label.setTextFill(Paint.valueOf("black"));

                label.setOnMouseClicked(new EventHandler<MouseEvent>()
                {
                    @Override
                    public void handle(MouseEvent event)
                    {
                        label.setBackground(getBackGroundFromImage(currentlySelectedBrickImage));
                        label.setPowerUpType(PowerUpType.values()[powerupTextures.indexOf(currentlySelectedPowerupImage)]);
                        label.setBrickType(getReversedBrickHashMap().get(currentlySelectedBrickImage));
                    }
                });
            }
        }
    }

    public void about()
    {
        Alert alert = new Alert(AlertType.INFORMATION);
        alert.setTitle("About " + bundle.getString("app.name"));
        alert.setHeaderText(bundle.getString("app.name"));
        alert.setContentText("Version:     " + bundle.getString("version.name") + "\r\nDate:         " + bundle.getString("version.date") + "\r\nAuthors:     " + bundle.getString("author") + "\r\n");
        Stage dialogStage = (Stage) alert.getDialogPane().getScene().getWindow();
        dialogStage.centerOnScreen();
        alert.showAndWait();
    }
}