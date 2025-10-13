package gameframework.gameobjects;

import gameframework.animations.Animation;
import gameframework.animations.BorderPoint;
import gameframework.animations.SpriteBorder;

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

    public static boolean drawBoundsRect = false;
    public static boolean drawSpriteBorders = false;
    public static boolean disableRendering = false;

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

    //Paints a pixel for the point at the requested position using the given color.
    public static void drawPixel(Graphics g, Point pixelPos, Color color)
    {
        /* There is no method to draw a single pixel in the java awt library that I know of :(
         * Therefore I have to implement this by drawing a line with the same start and end point. */
        g.setColor(color);
        g.drawLine(pixelPos.x, pixelPos.y, pixelPos.x, pixelPos.y);
    }

    /* We use this function to render either the bounds rectangle or the actual borders of a sprite
     * depending on which of those options are enabled. */
    protected void renderBorders(Graphics g)
    {
        Color oldColor = g.getColor();

        if (drawBoundsRect)
        {
            //draw the bounds rectangle of every object
            Rectangle collisionBounds = getCollisionBounds();
            g.setColor(Color.BLUE);
            g.drawRect(collisionBounds.x, collisionBounds.y, collisionBounds.width, collisionBounds.height);
        }

        if (drawSpriteBorders)
        {
            SpriteBorder borders = getSpriteBorders(true);

            if (borders == null)
                return;

            //traverse through set of points and draw each point
            for (BorderPoint p: borders)
            {
                drawPixel(g, p, Color.RED);
            }
        }
        //restore previous color in graphics object
        g.setColor(oldColor);
    }

    private Rectangle getCollisionBounds()
    {
        Rectangle animationFrameBounds = curAnimation.getCurrentFrameBounds();
        //convert relative x and y to absolute coordinates
        animationFrameBounds.x += x;
        animationFrameBounds.y += y;
        return animationFrameBounds;
    }

    /* This method returns all points comprising the borders of the sprite of the current frame within
     * the active animation. When performance is an issue we can disable repositioning. */
    public SpriteBorder getSpriteBorders(boolean reposition)
    {
        SpriteBorder spriteBorders = null;

        if (curAnimation != null)
        {
            /* We are currently repositioning sprite borders for all objects, in the future we should
             * only handle it here if the object is constantly changing position, for inanimate objects
             * that don't move, we should have the engine do it automatically (and only once).*/
            spriteBorders = curAnimation.getCurrentFrameBorders(x, y, true  /*reposition*/);
        }
        return spriteBorders;
    };

    public void render(Graphics g)
    {
        //draw sprite borders
        renderBorders(g);

        if ( !disableRendering && curAnimation != null)
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

    public void update()
    {
        x += velX;
        y += velY;
    }
}
