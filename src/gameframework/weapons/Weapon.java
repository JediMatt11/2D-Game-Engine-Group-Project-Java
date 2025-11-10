package gameframework.weapons;

import gameframework.gamecharacters.GameCharacter;
import gameframework.gamecharacters.HitBox;
import gameframework.gameobjects.GameObject;
import gameframework.gameobjects.GameObjects;
import gameframework.gameobjects.InanimateObject;

import java.awt.*;

public abstract class Weapon extends InanimateObject
{
//    private int x;
//    private int y;
//    private int width;
//    private int height;
    private int damage;
    private HitBox hb;
    private GameCharacter weaponHolder;



    public Weapon(String name, GameCharacter weaponHolder, int damage, int width, int height)
    {

        int x = weaponHolder.getX() + 500;
        int y = weaponHolder.getY() - 100;

        super(name, x, y, 1, width, height);

        this.weaponHolder = weaponHolder;
        this.setDamage(damage);

        //create weapon's hitbox
        setHb(new HitBox(weaponHolder.getX(), weaponHolder.getY(), width, height));


    }

    @Override
    public void render(Graphics g){
        super.render(g);

        if(hb != null){
            Rectangle bounds = hb.getBounds();
            Color oldColor = g.getColor();
            g.setColor(Color.RED);
            g.drawRect(bounds.x, bounds.y, bounds.width, bounds.height);
            g.setColor(oldColor);
        }
    }


    //weapon's hitbox checks if any object comes within its bounds
    //does nothing else as of now
    public void  checkCollision(GameObject object){
        if(getHb().intersects(object)){
            System.out.println("HITBOX COLLISION DETECTED");
        }
    }

    //keep track of weapon on weapon holder
    @Override
    public void update(GameObjects objects){

        super.update(objects);


        setPosition(weaponHolder.getX(), weaponHolder.getY());


        updateHitBox();

        //check collision
        for(GameObject obj :  objects){
            //make sure to not compare hitbox and weaponHolder
            if(obj != this.weaponHolder){
                checkCollision(obj);
            }
        }


        System.out.println("WEAPON UPDATING. . .");
    }



    public void updateHitBox()
    {

        hb.update(getX(),getY(),getScaleWidth(),getScaleHeight());
    }


    //handle collision
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



}
