package gameframework.inputhandlers;

import gameframework.GameThread;
import gameframework.gamecharacters.Player;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class KeyboardHandler implements KeyListener
{

    @Override
    public void keyTyped(KeyEvent e)
    {

    }

    @Override
    public void keyPressed(KeyEvent e)
    {
        int keyCode = e.getKeyCode();
        Player player = Player.getActivePlayer();

        switch (keyCode)
        {
            case KeyEvent.VK_RIGHT:
                player.moveRight(false);
                break;
            case KeyEvent.VK_LEFT:
                player.moveLeft(false);
                break;
            case KeyEvent.VK_UP:
                player.moveUp(false);
                break;
            case KeyEvent.VK_DOWN:
                player.moveDown(false);
                break;

            case KeyEvent.VK_F1:
                GameThread.displayFrameUpdateRate = !GameThread.displayFrameUpdateRate;
                break;
        }

    }

    @Override
    public void keyReleased(KeyEvent e)
    {
        int keyCode = e.getKeyCode();
        Player player = Player.getActivePlayer();

        switch (keyCode)
        {
            case KeyEvent.VK_RIGHT:
            case KeyEvent.VK_LEFT:
            case KeyEvent.VK_UP:
            case KeyEvent.VK_DOWN:
                player.stop();
                break;
        }
    }
}
