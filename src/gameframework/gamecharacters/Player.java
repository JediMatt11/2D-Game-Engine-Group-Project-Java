package gameframework.gamecharacters;

import gameframework.gameobjects.Direction;
import gameframework.gameobjects.GameObjectType;

import java.util.ArrayList;

public abstract class Player extends GameCharacter
{
    private static final int DEFAULT_TOTAL_LIVES = 3;

    protected int totalLives;
    protected int lives;
    private static final ArrayList<Player> availablePlayers = new ArrayList<>();
    private static int curPlayerIndex;

    public Player(String name, int x, int y,
                  int scaleWidth, int scaleHeight)
    {
        super(name, GameObjectType.PLAYER, x, y, scaleWidth, scaleHeight);
        totalLives = DEFAULT_TOTAL_LIVES;
        lives = totalLives;
        curPlayerIndex = 0;
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
}
