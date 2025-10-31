package gameframework.weapons;

import gameframework.gamecharacters.HitBox;
import gameframework.gameobjects.GameObject;

import java.awt.*;

public class Weapon
{
    private int x;
    private int y;
    private String name;
    private int damage;
    private int range;
    private int speed;
    private int width;
    private int height;
    private HitBox hb;

    public Weapon(String name, int damage, int range, int speed, int x, int y)
    {
        super();
        this.setName(name);
        this.setDamage(damage);
        this.setRange(range);
        this.setSpeed(speed);
        this.setX(x);
        this.setY(y);
        hb = new HitBox(x,y,width,height);
    }

    //weapon's hitbox checks if any object comes within its bounds
    public boolean checkCollision(GameObject object){
        return hb.intersects(object);
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public String getName() {
        return name;
    }


    public boolean handleObjectCollision(GameObject object) {
        return false;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getDamage() {
        return damage;
    }

    public void setDamage(int damage) {
        this.damage = damage;
    }

    public int getRange() {
        return range;
    }

    public void setRange(int range) {
        this.range = range;
    }

    public int getSpeed() {
        return speed;
    }

    public void setSpeed(int speed) {
        this.speed = speed;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }
}
