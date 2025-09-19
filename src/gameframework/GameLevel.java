package gameframework;
import java.awt.*;

public class GameLevel
{
    private String name;
    private int number;
    private String background;
    private String theme;
    private Point playerStartPos;

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
}
