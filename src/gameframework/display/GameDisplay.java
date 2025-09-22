package gameframework.display;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;

import gameframework.GameData;
import gameframework.GameLevel;
import gameframework.GameThread;

public class GameDisplay extends JFrame
{
    private GameData data;
    private static int displayWidth;
    private static int displayHeight;

    /* We intend to be able to toggle a message on the game window, these attributes hold the properties of
     * how that message displays (like position and text color), for example we can use it to show game
     * information like frame rates, update rates, etc
     */
    private final static int DEFAULT_MESSAGE_OFFSET = 100;
    private String message;
    private Color messageColor;
    private int messageOffsetX;
    private int messageOffsetY;

    public GameDisplay(GameData data)
    {
        super();
        setBounds(0,0, displayWidth, displayHeight);
        setVisible(true);
        setFocusable(true);
        setResizable(false);
        this.data = data;
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        setMessage("");
        setMessageColor(Color.WHITE);
        messageOffsetX = DEFAULT_MESSAGE_OFFSET;
        messageOffsetY = DEFAULT_MESSAGE_OFFSET;
    }

    public String getMessage()
    {
        return message;
    }

    public void setMessage(String message)
    {
        this.message = message;
    }

    public Color getMessageColor()
    {
        return messageColor;
    }

    public void setMessageColor(Color messageColor)
    {
        if (messageColor != null)
            this.messageColor = messageColor;
    }

    public void setMessageOffsets(int offsetX, int offsetY)
    {
        if (offsetX > 0 && offsetY > 0 )
        {
            messageOffsetX = offsetX;
            messageOffsetY = offsetY;
        }
    }

    public static void setDisplayResolution(int newDisplayWidth, int newDisplayHeight)
    {
        if (newDisplayWidth > 0 && newDisplayHeight > 0)
        {
            displayWidth = newDisplayWidth;
            displayHeight = newDisplayHeight;
        }
    }

    @Override
    public void paint(Graphics g)
    {
        super.paint(g);
        g.setColor(Color.ORANGE);
        GameLevel level = GameThread.getCurrentLevel();
        BufferedImage background = level != null ?
                                  GameThread.resourceManager.loadImageResource(level.getBackground()):
                                  null;
        if (background != null)
            g.drawImage(background, 0, 0, displayWidth, displayHeight, null);
    }

}
