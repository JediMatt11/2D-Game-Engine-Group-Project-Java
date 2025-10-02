package gameframework.gameobjects;

import gameframework.animations.Animation;

import java.awt.*;
import java.awt.image.BufferedImage;

public abstract class GameObject
{
    private String name;
    private int type;
    private int x;
    private int y;
    private int z;

    protected int velX;
    protected int velY;

    private int scaleWidth;
    private int scaleHeight;

    private Animation curAnimation;

    public GameObject(String name, int type,
                      int x, int y, int z,
                      int scaleWidth, int scaleHeight
                      )
    {
        this.name = name;
        this.type = type;
        this.x = x;
        this.y = y;
        this.z = z;
        this.scaleHeight = scaleHeight;
        this.scaleWidth = scaleWidth;
        velX = velY = 0;
    }

    public void update()
    {
        x += velX;
        y += velY;
    }

    public void render(Graphics g)
    {
        if (curAnimation != null)
        {
            curAnimation.drawFrame(g, x, y);
            curAnimation.nextFrame();
        }
    }

    public Animation getCurAnimation() {
        return curAnimation;
    }

    public void setCurAnimation(Animation curAnimation)
    {
        if (curAnimation != null)
            this.curAnimation = curAnimation;
    }

    public Point getPosition()
    {
        return new Point(x,y);
    }
}
