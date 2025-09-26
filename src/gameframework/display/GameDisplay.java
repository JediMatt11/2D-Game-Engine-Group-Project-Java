package gameframework.display;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;

import gameframework.GameData;
import gameframework.GameLevel;
import gameframework.GameThread;
import gameframework.inputhandlers.KeyboardHandler;

public class GameDisplay extends JFrame
{
    private GameData data;
    private static int displayWidth;
    private static int displayHeight;

    private BufferStrategy bufferStrategy;

    /* We intend to be able to toggle a message on the game window, these attributes hold the properties of
     * how that message displays (like position and text color), for example we can use it to show game
     * information like frame rates, update rates, etc
     */
    private final static int DEFAULT_MESSAGE_OFFSET = 100;
    private final static Font DEFAULT_MESSAGE_FONT = new Font("Arial", Font.BOLD, 42);

    private String message;
    private Color messageColor;
    private Font messageFont;
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

        add(new GamePanel(this));

        //setup buffer strategy
        createBufferStrategy(2);
        bufferStrategy = getBufferStrategy();

        //add input handlers
        addKeyListener(new KeyboardHandler());

        setMessage("");
        setMessageColor(Color.WHITE);
        messageFont = DEFAULT_MESSAGE_FONT;
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

    public void render()
    {
        Graphics g = bufferStrategy.getDrawGraphics();

        //draw normally whatever you want
        GameLevel level = GameThread.getCurrentLevel();
        BufferedImage background = level != null ?
                GameThread.resourceManager.loadImageResource(level.getBackground()):
                null;
        if (background != null)
            g.drawImage(background, 0, 0, getWidth(), getHeight(), null);

        g.setColor(messageColor);
        g.setFont(messageFont);
        g.drawString(message, messageOffsetX, messageOffsetY);

        GameThread.player.render(g);

        g.dispose();
        bufferStrategy.show();

    }

    @Override
    public void paint(Graphics g)
    {
        super.paint(g);
        g.setColor(Color.ORANGE);
    }

    public int getMessageOffsetX() {
        return messageOffsetX;
    }

    public int getMessageOffsetY() {
        return messageOffsetY;
    }
}
