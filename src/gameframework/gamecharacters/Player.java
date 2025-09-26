package gameframework.gamecharacters;

import gameframework.gameobjects.ObjectType;

public /*abstract*/ class Player extends GameCharacter
{
    private int totalLives;
    private int lives;

    public Player(String name, int x, int y, int z,
                  int scaleWidth, int scaleHeight, int totalHealth,
                  int totalLives)
    {
        super(name, ObjectType.PLAYER, x, y, z, scaleWidth, scaleHeight, totalHealth);
        this.totalLives = totalLives;
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
