package gameframework.gamecharacters;

import gameframework.gameobjects.Direction;
import gameframework.gameobjects.Collectible;
import gameframework.gameobjects.GameObjectType;
import gameframework.gameobjects.GameObjects;

import java.util.ArrayList;

public abstract class Player extends GameCharacter
{
    private static final int DEFAULT_TOTAL_LIVES = 3;

    protected int totalLives;
    protected int lives;
    private static final ArrayList<Player> availablePlayers = new ArrayList<>();
    private static int curPlayerIndex;
    private static double dashSpeed;
    private static final long DASH_COOL_DOWN = 5000;
    private static long lastDashTime;
    private static boolean isDashing;
    private static boolean dashSlowDown;
    private static int score;
    private static GameObjects keyList;

    public Player(String name, int x, int y,
                  int scaleWidth, int scaleHeight)
    {
        super(name, GameObjectType.PLAYER, x, y, scaleWidth, scaleHeight);
        totalLives = DEFAULT_TOTAL_LIVES;
        lives = totalLives;
        isDashing=false;
        curPlayerIndex = 0;
        lastDashTime = 0;
        score=0;
        if (keyList==null)
            keyList=new GameObjects(false);
    }

    public static void addPlayer(Player player, boolean activePlayer )
    {
        availablePlayers.add(player);
        if (activePlayer)
            curPlayerIndex = availablePlayers.indexOf(player);
    }

    public static Player getActivePlayer()
    {
        if (curPlayerIndex >= 0 && curPlayerIndex < availablePlayers.size())
            return availablePlayers.get(curPlayerIndex);
        else
            return null;
    }

    // changes the player in the game to the next player in the registered players list
    public static Player nextPlayer()
    {
        curPlayerIndex = (curPlayerIndex + 1) % availablePlayers.size();
        return getActivePlayer();
    }

    /* The engine supports the triggering of special actions by pressing the right (A), middle (B) and
     * left (C) mouse buttons, by default these don't do anything, and developers must override these
     * method to implement their own functionality. */
    public void specialActionA(boolean startingAction) {}
    public void specialActionB(boolean startingAction) {}
    public void specialActionC(boolean startingAction) {}

    public void jump()
    {
        // Normal jump if we have remaining jumps
        if (remainingJumps > 0)
        {
            changeActiveAnimation(getJumpAnimation(), true);
            // Apply upward impulse for the jump and mark as in midair
            velY = jumpImpulse;
            setInMidAir(true);
            // Consume one jump
            remainingJumps--;
            return;
        }

        // If out of jumps but touching a wall while airborne, allow a wall-jump
        if (touchingWall)
        {
            changeActiveAnimation(getJumpAnimation(), true);
            // Upward impulse
            velY = jumpImpulse;
            // Apply horizontal push away from wall
            if (touchingWallSide == Direction.LEFT)
                velX = wallJumpHorizontalImpulse; // push right
            else if (touchingWallSide == Direction.RIGHT)
                velX = -wallJumpHorizontalImpulse; // push left

            setInMidAir(true);
            // After wall-jumping, restore some air jumps so player can follow up
            remainingJumps = Math.max(0, maxJumps - 1);
            // Clear wall contact so we don't repeatedly wall-jump
            touchingWall = false;
            touchingWallSide = gameframework.gameobjects.Direction.NONE;
            return;
        }
    }

    @Override
    protected void performJump()
    {
        // Delegate to the Player.jump() method that implements jumping/wall-jump logic
        this.jump();
    }

    public void dash()
    {
        if (isMoving())
        {
            dashSpeed = speed * 6;
            System.out.println("This is speed " + speed);
            System.out.println("This is dashSpeed " + dashSpeed);
            long now = System.currentTimeMillis();
            if (now < 5000)
                now = 5001;

            if (!isDashing && (now - lastDashTime > DASH_COOL_DOWN))
                isDashing = true;
            System.out.println(isDashing);
        }
    }

    public void update(GameObjects objects)
    {
        if (isDashing)
        {
            velX*=1.2;
            System.out.println("updated speed " + velX);
            if (Math.abs(velX)>dashSpeed)
            {
                isDashing = false;
                dashSlowDown=true;
                System.out.println("Max Speed Reached, Slowing Down");
            }
        }
        if (dashSlowDown && Math.abs(velX)> Math.abs(runRight.getSpeed()))
        {
            velX*=.8;
            System.out.println("updated speed "+velX);
        }
        else dashSlowDown=false;

        super.update(objects);
        System.out.println("Current X And Y: "+getPosition());
    }

    public static void collectCoin()
    {
        score+=100;
        System.out.println("Collected a Coin");
    }

    public static void collectKey(Collectible key)
    {
        keyList.add(key);
        System.out.println("Collected a Key");

    }

    public static boolean useKey()
    {
        if (!keyList.isEmpty())
        {
            keyList.removeFirst();
            return true;
        }
        return false;
    }
}
