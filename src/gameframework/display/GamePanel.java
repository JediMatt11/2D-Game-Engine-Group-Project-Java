package gameframework.display;

import gameframework.GameLevel;
import gameframework.GameThread;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;

public class GamePanel extends JPanel
{
    private GameDisplay gameDisplay;

    public GamePanel(GameDisplay gameDisplay)
    {
        this.gameDisplay = gameDisplay;
    }

    public void paintComponent(Graphics g)
    {
        super.paintComponent(g);

    }
}
