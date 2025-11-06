package gameframework.weapons;

import gameframework.gamecharacters.GameCharacter;
import gameframework.gamecharacters.HitBox;
import gameframework.gameobjects.GameObject;
import gameframework.gameobjects.GameObjects;
import gameframework.gameobjects.InanimateObject;

import java.awt.*;

public abstract class Weapon extends InanimateObject
{
    private int x;
    private int y;
    private int width;
    private int height;
    private int damage;
    private HitBox hb;
    private GameCharacter weaponHolder;


    public Weapon(String name, GameCharacter weaponHolder, int damage, int width, int height)
    {
        super(name, weaponHolder.getX(), weaponHolder.getY(), weaponHolder.getZ(), width, height);

        this.x=x;
        this.y=y;
        this.width=width;
        this.height=height;
        this.weaponHolder = weaponHolder;
        this.setDamage(damage);

        //create weapon's hitbox
        setHb(new HitBox(weaponHolder.getX(), weaponHolder.getY(), width, height));

    }

    //weapon's hitbox checks if any object comes within its bounds
    public boolean checkCollision(GameObject object){
        return getHb().intersects(object);
    }

    //keep track of weapon on weapon holder
    @Override
    public void update(GameObjects objects){

        super.update(objects);
        if(weaponHolder != null){
            setPosition(weaponHolder.getX(), weaponHolder.getY());
        }

        updateHitBox();

        //getHb().setHb(new Rectangle(weaponHolder.getX(),weaponHolder.getY(),scaleWidth,scaleHeight));
        System.out.println("WEAPON UPDATING. . .");
    }

    public void updateHitBox()
    {
        hb.update(x,y,width,height);
    }


    public boolean handleObjectCollision(GameObject object) {
        return false;
    }



    public int getDamage() {
        return damage;
    }

    public void setDamage(int damage) {
        this.damage = damage;
    }


    public GameCharacter getWeaponHolder() {
        return weaponHolder;
    }

    public void setWeaponHolder(GameCharacter weaponHolder) {
        this.weaponHolder = weaponHolder;
    }

    public HitBox getHb() {
        return hb;
    }

    public void setHb(HitBox hb) {
        this.hb = hb;
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
