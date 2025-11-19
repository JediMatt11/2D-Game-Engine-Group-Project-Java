package gameframework.weapons;

import gameframework.gamecharacters.GameCharacter;
import gameframework.gameobjects.GameObject;
import gameframework.gameobjects.GameObjects;
import gameframework.gameobjects.InanimateObject;

import java.awt.*;

public class Weapon extends InanimateObject
{
    private HitBoxStrategy strategy;
    private int damage;
    private HitBox hb;
    private GameCharacter weaponHolder;

    private Projectile projectile;



    public Weapon(String name, GameCharacter weaponHolder, int damage, int width, int height)
    {

        int x = weaponHolder.getX() * 3;
        int y = weaponHolder.getY() / 2;

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

        //copy weapon holder's position
        setPosition(weaponHolder.getX(), weaponHolder.getY());

        //keep track of direction of weapon holder
        if(weaponHolder.isMovingLeft()){

            setPosition(weaponHolder.getX(), weaponHolder.getY() + 70 );
        }
        else if(weaponHolder.isMovingRight()){
            setPosition(weaponHolder.getX() + 50, weaponHolder.getY() + 70);

        }
        else{
            setPosition(weaponHolder.getX() + 50, weaponHolder.getY() + 70);
        }
        updateHitBox();

        //check collision
        for(GameObject obj :  objects){
            //make sure to not compare hitbox and weaponHolder
            //only checking for npcs for testing
            if(obj != this.weaponHolder && obj.getType() != 3){
                checkCollision(obj);
            }
        }

    }

    //method for firing projectiles




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


    public HitBoxStrategy getStrategy() {
        return strategy;
    }

    public void setStrategy(HitBoxStrategy strategy) {
        this.strategy = strategy;
    }
}
