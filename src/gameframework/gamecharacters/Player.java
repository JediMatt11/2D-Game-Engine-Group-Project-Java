package gameframework.gamecharacters;

import gameframework.gameobjects.ObjectType;

import java.sql.Array;
import java.util.ArrayList;

public abstract class Player extends GameCharacter
{
    private int totalLives;
    private int lives;
    private static ArrayList<Player> availablePlayers = null;
    private static int curPlayerIndex;

    public Player(String name, int x, int y, int z,
                  int scaleWidth, int scaleHeight, int totalHealth,
                  int totalLives)
    {
        super(name, ObjectType.PLAYER, x, y, z, scaleWidth, scaleHeight, totalHealth);
        this.totalLives = totalLives;
        initializeAvailablePlayers();
        curPlayerIndex = 0;
    }

    private void initializeAvailablePlayers()
    {
        if (availablePlayers == null)
            availablePlayers = new ArrayList<Player>();
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

    public static void nextPlayer()
    {
        curPlayerIndex = (curPlayerIndex + 1) % availablePlayers.size();
    }

    public void moveRight(boolean running)
    {

        velX = getSpeed();
    }

    public void moveLeft(boolean running)
    {
        velX = -getSpeed();
    }

    public void moveDown(boolean running)
    {

        velY = getSpeed();
    }

    public void moveUp(boolean running)
    {

        velY = -getSpeed();
    }

    public void stop()
    {
        velX = velY = 0;
    }









}
