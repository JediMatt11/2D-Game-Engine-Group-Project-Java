package gameframework.display;

import javax.swing.*;
import java.awt.*;
import gameframework.GameData;

public class GameDisplay extends JFrame
{
    private GameData data;
    private int displayWidth;
    private int displayHeight;

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

    public void setDisplayResolution(int displayWidth, int displayHeight)
    {
        if (displayWidth > 0 && displayHeight > 0)
        {
            this.displayWidth = displayWidth;
            this.displayHeight = displayHeight;
        }
    }

}
