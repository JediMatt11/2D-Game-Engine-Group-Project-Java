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

}
