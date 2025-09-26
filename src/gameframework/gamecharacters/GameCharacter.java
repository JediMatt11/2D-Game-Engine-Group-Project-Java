package gameframework.gamecharacters;

import gameframework.gameobjects.GameObject;

public abstract class GameCharacter extends GameObject
{
    private int totalHealth;
    private int curHealth;
    private int speed;

    public GameCharacter(String name, int type,
                         int x, int y, int z,
                         int scaleWidth, int scaleHeight,
                         int totalHealth)
    {
        super(name, type, x, y, z, scaleWidth, scaleHeight);
        this.totalHealth = totalHealth;
        curHealth = totalHealth;
    }

    public int getSpeed() {
        return speed;
    }

    public void setSpeed(int speed) {
        this.speed = speed;
    }
}
