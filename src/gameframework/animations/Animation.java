package gameframework.animations;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

public class Animation
{
    private String name;
    private BufferedImage[] frames;
    private int frameCount;
    private int curFrameIndex;
    private BufferedImage curFrame;
    private ArrayList<SpriteBorder> frameBorders;
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
        initializeFrameBorders();
    }

    public Animation(BufferedImage image, String name,  int scaleWidth, int scaleHeight)
    {
        this.name = name;
        frames = new BufferedImage[1];
        curFrame = frames[0] = image;
        frameCount = 1;
        curFrameIndex = 0;
        this.scaleHeight = scaleHeight;
        this.scaleWidth = scaleWidth;
        speed = 1;
        speedCounter = 0;
        initializeFrameBorders();
    }

    /*
     * This method is used to obtain and store the borders of each frame of the animation (the actual
     * sprite borders not including the transparent background), having the borders handy is very useful
     * for collision detection
     */
    private void initializeFrameBorders()
    {
        frameBorders = new ArrayList<SpriteBorder>();

        for ( int i = 0; i < frameCount; i++)
        {
            SpriteBorder s = SpriteBorder.getSpriteBorders(
                    frames[i], scaleWidth, scaleHeight);
            frameBorders.add(s);
        }
    }

    public SpriteBorder getCurrentFrameBorders(int posX, int posY, boolean reposition)
    {

        SpriteBorder curSpriteBorders = (SpriteBorder) frameBorders.get(curFrameIndex);

        //If requested (reposition is true) then adjusts all border point positions
        //to reflect the current sprite position
        if ( reposition )
            return curSpriteBorders.reposition(posX, posY);
        return curSpriteBorders;
    }

    public Rectangle getCurrentFrameBounds()
    {
        return frameBorders.get(curFrameIndex).getBordersRectangle();
    }

    /* These method can be used to convert the border point positions for every frame in the animation
     * from relative coords based on the top left corner of the bounds rectangle of the frame to actual
     * absolute coordinates in the game world. */
    public void adjustFrameBordersPosition(int posX, int posY)
    {
        for ( int i = 0; i < frameCount; i++)
        {
            SpriteBorder s = frameBorders.get(i);
            frameBorders.set(i, s.reposition(posX, posY));
        }
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
