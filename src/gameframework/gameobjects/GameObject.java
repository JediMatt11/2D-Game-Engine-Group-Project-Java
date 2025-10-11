package gameframework.gameobjects;

import gameframework.animations.Animation;

import java.awt.*;

public abstract class GameObject
{
    private String name;
    private int type;
    private int x;
    private int y;
    private int z;

    protected int velX;
    protected int velY;

    protected int scaleWidth;
    protected int scaleHeight;

    protected Animation curAnimation;

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

    public int getX() {return x;}
    public int getY() {return y;}
    public int getZ() {return z;}

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

    public Animation getActiveAnimation() {
        return curAnimation;
    }

    public void changeActiveAnimation(Animation curAnimation)
    {
        if (curAnimation != null)
            this.curAnimation = curAnimation;
    }

    public void setPosition(int x, int y)
    {
        this.x = x;
        this.y = y;
    }
}
