package gameframework.gamecharacters;

import gameframework.gameobjects.GameObjectType;

import java.util.ArrayList;

public abstract class Player extends GameCharacter
{
    private static final int DEFAULT_TOTAL_LIVES = 3;

    protected int totalLives;
    protected int lives;
    private static final ArrayList<Player> availablePlayers = new ArrayList<Player>();
    private static int curPlayerIndex;
    private static double maximumXSpeed;

    public Player(String name, int x, int y,
                  int scaleWidth, int scaleHeight)
    {
        super(name, GameObjectType.PLAYER, x, y, scaleWidth, scaleHeight);
        totalLives = DEFAULT_TOTAL_LIVES;
        lives = totalLives;
        curPlayerIndex = 0;
        maximumXSpeed = runRight.getSpeed()*1.5;
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
    public void specialActionA(boolean startingAction) {};
    public void specialActionB(boolean startingAction) {};
    public void specialActionC(boolean startingAction) {};

    public void jump()
    {
        /* For the time being the engine doesn't allow jumping in midair
         * or during another jump, we might later allow double jumps. Note
         * that isInMidAir() only works for games that enable gravity so we need
         * also the other test to prevent double jumps in all other games.
         */
        if (isInMidAir() || isInTheMiddleOfJump())
            return;

        changeActiveAnimation(getJumpAnimation());

        //the character speed gets multiplied when jumping
        //depending on the jumping impulse attribute
        if (gravity > 0)
            velY = -speed;
        velY =  (int)Math.round(velY * jumpImpulseY);
        if (velX<maximumXSpeed)
            velX = (int)Math.round(velX*jumpImpulseX);


        setInMidAir(true);

        //System.out.println("Player jumped");
    }





}
