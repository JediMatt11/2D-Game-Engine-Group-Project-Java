package gameframework.weapons;

import gameframework.animations.Animation;
import gameframework.gamecharacters.GameCharacter;
import gameframework.gamecharacters.HitBox;
import gameframework.gameobjects.GameObject;
import gameframework.gameobjects.GameObjectType;
import gameframework.gameobjects.GameObjects;
import gameframework.gameobjects.InanimateObject;

public abstract class Weapon extends InanimateObject
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
    private GameCharacter weaponHolder;
    protected Animation weaponIdle;

    public Weapon(String name, GameCharacter weaponHolder, int damage, int width, int height)
    {
        super(name, weaponHolder.getX(), weaponHolder.getY(), weaponHolder.getZ(), width, height);

        this.weaponHolder = weaponHolder;
        this.setName(name);
        this.setDamage(damage);
        this.setRange(range);
        this.setSpeed(speed);
        this.setX(x);
        this.setY(y);
        this.setWidth(width);
        this.setHeight(height);
        setHb(new HitBox(x,y,width,height));
        initializeWeaponAnimation();
    }

    //weapon's hitbox checks if any object comes within its bounds
    public boolean checkCollision(GameObject object){
        return getHb().intersects(object);
    }

    //keep track of weapon on weapon holder
    @Override
    public void update(GameObjects objects){
        if(weaponHolder != null){
            setPosition(weaponHolder.getX(), weaponHolder.getY());
        }
    }

    public abstract void initializeWeaponAnimation();




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
