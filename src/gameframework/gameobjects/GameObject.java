package gameframework.gameobjects;

import gameframework.animations.Animation;
import gameframework.animations.BorderPoint;
import gameframework.animations.SpriteBorder;
import gameframework.collision.CollisionHandler;
import gameframework.display.GameDisplay;
import gameframework.supportfunctions.GraphicsLibrary;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.LinkedList;

public abstract class GameObject
{
    private String name;
    private int type;
    private int x;
    private int y;
    private int z;

    protected int velX;
    protected int velY;

    protected Direction direction;

    protected int scaleWidth;
    protected int scaleHeight;

    protected Animation curAnimation;

    protected boolean requiresUpdating;

    protected boolean constrainToBackground;

    public static boolean drawBoundsRect = false;
    public static boolean drawSpriteBorders = false;
    public static boolean disableRendering = false;

    //internal object attribute used to handle collisions
    private CollisionHandler collisionHandler;

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
        direction = Direction.NONE;
        this.scaleHeight = scaleHeight;
        this.scaleWidth = scaleWidth;
        velX = velY = 0;

        //initialize collision handler
        collisionHandler = new CollisionHandler(this);

        //By default all objects require to be updated
        requiresUpdating = true;

        //By default objects cannot move beyond the game's background
        constrainToBackground = true;
    }

    public int getX() {return x;}
    public int getY() {return y;}
    public int getZ() {return z;}
    // No setter for x and y as those should be changed exclusively using the setPosition method
    public void setZ(int z)
    {
        if ( z >= 0 )
            this.z = z;
    }

    public String getName()
    {
        return name;
    }

    public int getType()
    {
        return type;
    }

    public Direction getDirection()
    {
        return direction;
    }

    public boolean isInanimate()
    {
        return false;
    }
    //Is this an inanimate object that never changes position
    public boolean isUnmovable()
    {
        return isInanimate();
    }

    public boolean requiresUpdating()
    {
        return requiresUpdating;
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
                GraphicsLibrary.drawPixel(g, p, Color.RED);
            }
        }
        //restore previous color in graphics object
        g.setColor(oldColor);
    }

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

    public Point getPosition()
    {
        return new Point(x,y);
    }

    /* This method should be the only way used to change positions in order to
     * easily trace position changes while debugging the game.*/
    public void setPosition(int x, int y)
    {
        this.x = x;
        this.y = y;
    }

    /* Every object must override and extend these methods in order to handle
     * their own individual updates and how they handle collision with the
     * other objects in the game.
     */
    public void update(GameObjects objects)
    {
        /* Only objects that move need to update their position or handle their own collisions. By having
         * only the object moving (cause of the collision) handle the collision, we eliminate a  lot of the
         * computational overhead. */
        if ( !isUnmovable() )
        {
            setPosition(getX() + velX, getY() + velY);
            collision(objects);
        }
    }
    public abstract boolean handleObjectCollision(GameObject object);

    /** This method is used to enable/disable collision based on sprite borders, if disabled
     * then we use the collision bounds (tightest rectangle bounds fit to the sprite borders)
     * when evaluating all kind of collisions. */
    public void enableSpriteBordersCollision(boolean enable)
    {
        collisionHandler.enableSpriteBordersCollision(enable);
    }

    public boolean isSpriteBordersCollisionEnabled()
    {
        return collisionHandler.isSpriteBordersCollisionEnabled();
    }

    // Get a bounds rectangle for the overall object, based on its general position
    // and scaling width and height factors.
    public Rectangle getBounds()
    {
        Rectangle boundsRect = new Rectangle(x, y, scaleWidth,
                scaleHeight);
        return boundsRect;
    }

    /* Get a bounds rectangle which is suitable for collision detection, that is,
     * a tight fit to the actual borders of the current sprite frame in the
     * object's animation */
    public Rectangle getCollisionBounds()
    {
        return collisionHandler.getCollisionBounds();
    }

    /* This method returns all points comprising the borders of the sprite of the current frame within
     * the active animation. When performance is an issue we can disable repositioning. */
    public SpriteBorder getSpriteBorders(boolean reposition)
    {
        SpriteBorder spriteBorders = null;

        if (curAnimation != null)
        {
            // unmovable objects are automatically repositioned by the engine at load time for performance
            // reasons, so we ignore any further repositioning requests
            spriteBorders = curAnimation.getCurrentFrameBorders(x, y, !isUnmovable() && reposition);
        }
        return spriteBorders;
    };

    //This method returns true if the object collides with another
    //given object or false otherwise.
    public boolean collidesWith(GameObject otherObject)
    {
        boolean objectsCollide = false;
        objectsCollide = collisionHandler.checkCollision(otherObject);
        return objectsCollide;
    }

    public boolean handleCollision(GameObject collidingObject)
    {
        return collisionHandler.handleCollision(collidingObject);
    }

    public void collision(LinkedList<GameObject> objects)
    {
        // Loop through all other objects and handle any collisions between this object
        // and any other one

        for ( int i = 0; i < objects.size(); i++)
        {
            GameObject go = objects.get(i);

            if (go == this)
                continue;

            //Handle collision here for any objects that require some action
            //by the game object or character when collision occurs
            if ( collidesWith(go))
            {
                //allow each character/object to handle the collision in a specific way
                boolean handled = handleObjectCollision(go);

                if (!handled)
                {
                    System.out.println("Unable to handle collision with object "
                            + go.getName());
                    break;
                }
            }
        }
        return;
    }

    //Determine if an object is fully contained within the given bounds.
    public boolean isWithinBounds(Rectangle bounds)
    {
        boolean withinBounds = false;

        if (
                getX() + scaleWidth > bounds.x &&
                getX() < bounds.x + bounds.width &&
                getY() + scaleHeight > bounds.y &&
                getY() < bounds.y + bounds.height
        )
            withinBounds = true;

        return withinBounds;
    }

    //Make sure this object is never positioned outside the game world
    public void enforceBackgroundBounds()
    {
        //This method is only effective if the constrain to background flag is enabled
        if (constrainToBackground)
        {
            BufferedImage background = GameDisplay.getCurBackground();
            if (background != null)
            {
                Rectangle backgroundBounds = new Rectangle(0, 0, background.getWidth(), background.getHeight());
                enforceBounds(backgroundBounds);
            }
        }
    }

    //Make sure this objects is never positioned outside the given bounds area
    public void enforceBounds(Rectangle boundArea )
    {
        int scaledWidth = curAnimation.getScaleWidth();
        int scaledHeight = curAnimation.getScaleHeight();

        int updatedX = 0, updatedY = 0;

        if (x < boundArea.x)
        {
            updatedX = boundArea.x;
        }
        else if (x > boundArea.x + boundArea.width - scaledWidth)
        {
            updatedX = boundArea.x + boundArea.width - scaledWidth;
        }

        if (y < boundArea.y)
        {
            updatedY = boundArea.y;
        }
        else if (y > boundArea.y + boundArea.height - scaledHeight)
        {
            updatedY = boundArea.y + boundArea.height - scaledHeight;
        }

        setPosition(updatedX, updatedY);
    }

}
