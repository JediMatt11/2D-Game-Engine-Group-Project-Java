package gameframework.animations;

import java.awt.*;
import java.awt.image.BufferedImage;

public class Animation
{
    private String name;
    private BufferedImage[] frames;
    private int frameCount;
    private int curFrameIndex;
    private BufferedImage curFrame;
    private int scaleWidth;
    private int scaleHeight;
    private int speed;
    private int speedCounter;

    public Animation(Spritesheet spritesheet, int scaleWidth, int scaleHeight )
    {
        name = spritesheet.getName();
        frames = spritesheet.convertToImageArray();
        frameCount = frames.length;
        curFrameIndex = 0;
        curFrame = frames[curFrameIndex];
        this.scaleHeight = scaleHeight;
        this.scaleWidth = scaleWidth;
        speed = 1;
        speedCounter = 0;
    }

    public Animation(BufferedImage image, String name,  int scaleWidth, int scaleHeight)
    {
        this.name = name;
        frames = new BufferedImage[1];
        frameCount = 1;
        curFrameIndex = 0;
        curFrame = frames[0];
        this.scaleHeight = scaleHeight;
        this.scaleWidth = scaleWidth;
        speed = 1;
        speedCounter = 0;

    }

    public void nextFrame()
    {
        speedCounter++;

        if (speedCounter == speed)
        {
            curFrameIndex++;
            if (curFrameIndex == frameCount)
                curFrameIndex = 0;
            curFrame = frames[curFrameIndex];
            speedCounter = 0;
        }
    }

    public void drawFrame(Graphics g, int x, int y)
    {
        g.drawImage(curFrame, x, y, scaleWidth, scaleHeight, null );
    }


    public int getSpeed() {
        return speed;
    }

    public void setSpeed(int speed) {
        this.speed = speed;
    }
}
