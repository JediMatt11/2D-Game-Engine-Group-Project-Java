package gameframework.weapons;

import gameframework.gamecharacters.GameCharacter;
import gameframework.gameobjects.GameObject;
import gameobjects.ThrownWeapon;

import java.awt.*;

//need to initialize projectile within this class

public abstract class RangedWeapon extends Weapon
{



    //pass hitbox into weapon constructor
    public RangedWeapon(String name, GameCharacter weaponHolder, int damage, int width, int height) {
        super(name, weaponHolder, damage, width, height);
    }

    @Override
    public Rectangle createHitBox(int x, int y, int width, int height) {


        return null;
    }

    @Override
    public void update(int x, int y, int width, int height) {
        //hitbox follows projectile
    }



}
