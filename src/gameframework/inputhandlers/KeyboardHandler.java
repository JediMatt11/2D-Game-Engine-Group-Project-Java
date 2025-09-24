package gameframework.inputhandlers;

import gameframework.GameThread;

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

        switch (keyCode)
        {
            case KeyEvent.VK_RIGHT:
                break;
            case KeyEvent.VK_LEFT:
                break;
            case KeyEvent.VK_F1:
                GameThread.displayFrameUpdateRate = !GameThread.displayFrameUpdateRate;
                break;
        }

    }

    @Override
    public void keyReleased(KeyEvent e)
    {

    }
}
