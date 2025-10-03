package gameframework;
import gameframework.gameobjects.GameObject;

import java.awt.*;
import java.util.ArrayList;

public class GameLevel
{
    private String name;
    private int number;
    private String background;
    private String theme;
    private Point playerStartPos;
    private final static String LEVEL_FOLDER = "levels/";

    public GameLevel(String initName, int initNumber, String initBackground,
                     String initTheme,
                     Point initPlayerStartPos)
    {
        name = initName;
        number = initNumber;
        background = initBackground;
        theme = initTheme;
        playerStartPos = initPlayerStartPos;
    }


    public String getName()
    {
        return name;
    }

    public int getNumber()
    {
        return number;
    }

    public String getBackground()
    {
        return background;
    }

    public String getTheme()
    {
        return theme;
    }

    public Point getPlayerStartPos()
    {
        return playerStartPos;
    }

    public boolean load(GameData data) throws Exception
    {
        ArrayList<String> text = GameThread.resourceManager.loadTextResource(LEVEL_FOLDER
                + name + "/" + name + ".txt");

        for (String textLine : text)
        {
            GameObject gameObject = createObject();
            data.getObjects().add(gameObject);
        }
        return true;

    }
}
