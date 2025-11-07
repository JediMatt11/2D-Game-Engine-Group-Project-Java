package gameframework.gamecharacters;

import gameframework.gameobjects.Direction;
import gameframework.gameobjects.GameObject;
import gameframework.gameobjects.GameObjectType;
import gameframework.animations.Animation;

/**
 * This class handles general support for characters in the game.
 * Everything in this class applies to any kind of character including
 * player characters, friendly NPCs and enemy characters.
 */
public abstract class GameCharacter extends GameObject
{

    //Attributes representing all different animations a character should ideally support.
    protected Animation idle;
    protected Animation jump;
    protected Animation walkRight;
    protected Animation walkLeft;
    protected Animation walkUp;
    protected Animation walkDown;
    protected Animation runRight;
    protected Animation runLeft;
    protected Animation runUp;
    protected Animation runDown;
    protected Animation attackRight;
    protected Animation attackLeft;
    protected Animation attackUp;
    protected Animation attackDown;
    protected Animation attackRangedRight;
    protected Animation attackRangedLeft;
    protected Animation attackRangedDown;
    protected Animation attackRangedUp;
    protected Animation dieRight;
    protected Animation dieLeft;
    protected Animation dieUp;
    protected Animation dieDown;
    protected Animation guardRight;
    protected Animation guardLeft;
    protected Animation guardUp;
    protected Animation guardDown;

    //Attributes for all game characters like health and speed
    private static final int DEFAULT_TOTAL_HEALTH = 100;
    private static final int DEFAULT_SPEED = 2;

    private int totalHealth;
    private int curHealth;
    protected int speed;
    protected int jumpHeight;

    public GameCharacter(String name, int type,
                         int x, int y,
                         int scaleWidth, int scaleHeight)
    {
        super(name, type, x, y, 3, scaleWidth, scaleHeight);
        totalHealth = DEFAULT_TOTAL_HEALTH;
        curHealth = totalHealth;
        speed = DEFAULT_SPEED;
        jumpHeight = -8;

        initializeAnimations();
        initializeStatus();
    }

    public abstract void initializeStatus();
    public abstract void initializeAnimations();

    public int getTotalHealth()
    {
        return totalHealth;
    }

    public void setTotalHealth(int totalHealth)
    {
        if (totalHealth >= 0)
            this.totalHealth = totalHealth;
    }

    public int getCurHealth()
    {
        return curHealth;
    }

    public void setCurHealth(int curHealth)
    {
        if ( curHealth >= 0)
            this.curHealth = curHealth;
        else  this.curHealth = 0;
    }


    public int getSpeed() {
        return speed;
    }

    public void setSpeed(int speed)
    {
        this.speed = speed;
    }

    // Returns the animation used when determining if a character is latched to a platform or not
    public Animation getPlatformingReferenceAnimation()
    {
        return getIdleAnimation();
    }

    public Animation getMoveRightAnimation()
    {
        return walkRight;
    }

    public Animation getMoveLeftAnimation()
    {
        return walkLeft;
    }

    public Animation getMoveUpAnimation()
    {
        return walkUp;
    }

    public Animation getMoveDownAnimation()
    {
        return walkDown;
    }

    public Animation getRunRightAnimation()
    {
        return runRight;
    }

    public Animation getRunLeftAnimation()
    {
        return runLeft;
    }

    public Animation getRunUpAnimation()
    {
        return runUp;
    }

    public Animation getRunDownAnimation()
    {
        return runDown;
    }

    public Animation getIdleAnimation()
    {
        return idle;
    }

    public Animation getJumpAnimation()
    {
        return jump;
    }

    public Animation getAttackRightAnimation()
    {
        return attackRight;
    }

    public Animation getAttackLeftAnimation()
    {
        return attackLeft;
    }

    public Animation getAttackUpAnimation()
    {
        return attackUp;
    }

    public Animation getAttackDownAnimation()
    {
        return attackDown;
    }

    public Animation getGuardRightAnimation()
    {
        return guardRight;
    }

    public Animation getGuardLeftAnimation()
    {
        return guardLeft;
    }

    public Animation getGuardUpAnimation()
    {
        return guardUp;
    }

    public Animation getGuardDownAnimation()
    {
        return guardDown;
    }

    public Animation getDieRightAnimation()
    {
        return dieRight;
    }

    public Animation getDieLeftAnimation()
    {
        return dieLeft;
    }

    public Animation getDieUpAnimation()
    {
        return dieUp;
    }

    public Animation getDieDownAnimation()
    {
        return dieDown;
    }

    public boolean isAttackingRight()
    {
        return (curAnimation == getAttackRightAnimation() );
    }

    public boolean isAttackingLeft()
    {
        return (curAnimation == getAttackLeftAnimation());
    }

    public boolean isAttackingUp()
    {
        return ( curAnimation == getAttackUpAnimation());
    }

    public boolean isAttackingDown()
    {
        return ( curAnimation == getAttackDownAnimation());
    }


    public boolean isDefending()
    {
        return ( curAnimation == getGuardRightAnimation() ||
                curAnimation == getGuardLeftAnimation() ||
                curAnimation == getGuardUpAnimation() ||
                curAnimation == getGuardDownAnimation()
        );
    }

    public boolean isMoving()
    {
        return ( curAnimation == getMoveRightAnimation() ||
                curAnimation == getMoveLeftAnimation() ||
                curAnimation == getMoveUpAnimation() ||
                curAnimation == getMoveDownAnimation() ||
                isRunning()
        );
    }

    public boolean isRunning()
    {
        return ( curAnimation == getRunRightAnimation() ||
                curAnimation == getRunLeftAnimation() ||
                curAnimation == getRunUpAnimation() ||
                curAnimation == getRunDownAnimation()
        );
    }

    public boolean isMovingRight()
    {
        return ( curAnimation == getMoveRightAnimation() ||
                curAnimation == getRunRightAnimation()
        );
    }

    public boolean isMovingLeft()
    {
        return ( curAnimation == getMoveLeftAnimation() ||
                curAnimation == getRunLeftAnimation()
        );
    }

    public boolean isMovingUp()
    {
        return ( curAnimation == getMoveUpAnimation() ||
                curAnimation == getRunUpAnimation()
        );
    }

    public boolean isMovingDown()
    {
        return ( curAnimation == getMoveDownAnimation() ||
                curAnimation == getRunDownAnimation()
        );
    }

    public boolean isJumping()
    {
        return (getJumpAnimation() != null &&
                curAnimation == getJumpAnimation());
    }

    public boolean isInTheMiddleOfJump()
    {
        return isJumping() && !curAnimation.isPaused();
    }

    public boolean isDead()
    {
        return (curAnimation == getDieLeftAnimation() ||
                curAnimation == getDieRightAnimation() ||
                curAnimation == getDieUpAnimation() ||
                curAnimation == getDieDownAnimation());
    }

    @Override
    public boolean isFalling()
    {
        //If we are in mid air and not in the middle of a jump then we are falling.
        return (isInMidAir() && !(isInTheMiddleOfJump()));
    }

    //Disable atomatic nearby tile relatching for characters that are jumping
    public boolean disableAutoRelatching() { return isJumping(); }

    /* These methods change the speed, direction and animation of a character
     * in order to make it move it in a certain direction. */
    public void moveRight(boolean running)
    {
        if (!isAbleToMove())
            return;

        if (running)
        {
            runRight();
            return;
        }
        changeActiveAnimation(getMoveRightAnimation());
        direction = Direction.RIGHT;
        velX = speed;
    }

    public void moveLeft(boolean running)
    {
        if (!isAbleToMove())
            return;

        if (running)
        {
            runLeft();
            return;
        }
        changeActiveAnimation(getMoveLeftAnimation());
        direction = Direction.LEFT;
        velX = -speed;
    }

    public void moveUp(boolean running)
    {
        if (!isAbleToMove())
            return;

        if (running)
        {
            runUp();
            return;
        }
        changeActiveAnimation(getMoveUpAnimation());
        direction = Direction.UP;
        velY = -speed;
    }

    public void moveDown(boolean running)
    {
        if (!isAbleToMove())
            return;

        if (running)
        {
            runDown();
            return;
        }
        changeActiveAnimation(getMoveDownAnimation());
        direction = Direction.DOWN;
        velY = speed;
    }

    public void stop()
    {
        if (isMoving())
            changeActiveAnimation(getIdleAnimation());
        direction = Direction.NONE;
        velX = velY = 0;
    }
    public void stopX()
    {
        velX = 0;
    }
    public void stopY()
    {
        velY = 0;
    }

    /* These methods change the speed, direction and animation of a character
     * in order to make it run in a certain direction (When running, the engine
     * sets the speed to double the walking speed, for the time being). */
    private void runRight()
    {
        velX = speed * 2;
        changeActiveAnimation(getRunRightAnimation());
        direction = Direction.RIGHT;
    }

    private void runLeft()
    {
        velX = -speed * 2;
        changeActiveAnimation(getRunLeftAnimation());
        direction = Direction.LEFT;
    }

    private void runUp()
    {
        velY = -speed * 2;
        changeActiveAnimation(getRunUpAnimation());
        direction = Direction.UP;
    }

    private void runDown()
    {
        velY = speed * 2;
        changeActiveAnimation(getRunDownAnimation());
        direction = Direction.DOWN;
    }
    /********/

    public void attack()
    {
        changeActiveAnimation(attackRight);
    }

    /* Method used to determine if a character is able to move in the current situation
     * or not. Note that currently this method is only called to evaluate if the player
     * can move in response to an input event.*/
    public boolean isAbleToMove()
    {
        boolean ableToMove = false;

        //Can't move if player is dead
        if (isDead())
            return false;
        else if (curAnimation == getIdleAnimation()
                || isMoving() )
        {
            //If the character is idle then we can move and if we are moving we
            // can always change direction.
            ableToMove = true;
        }
        else
        {
            //Performing a different action that isn't idle or moving
            if (getGravity() != 0 && isInTheMiddleOfJump())
            {
                //The engine allows characters to still move while
                //they are in the middle of a jump (still have jump impulse)
                ableToMove = true;
            }
            else
            {
                //We can move as long as we aren't in the middle
                //of another action (animation must be completed).
                if (curAnimation.isPaused())
                    ableToMove = true;
            }
        }
        return ableToMove;

    }

    public boolean handleObjectCollision(GameObject object)
    {
        //Handle collision with objects in a general way (applies to all game characters)
        boolean handled = true;

        switch (object.getType())
        {
            case GameObjectType.INANIMATE:
                //Handle how general characters handle collision with inanimate objects
                handled = handleCollision(object);
                break;
            default:
                //Call the general collision handler that provides basic collision
                //for all objects (repositioning to resolve the collision)
                handled = handleCollision(object);
                break;
        }
        return handled;
    }

}
