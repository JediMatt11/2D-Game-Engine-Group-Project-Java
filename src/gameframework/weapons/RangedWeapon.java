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

    protected abstract Projectile createProjectile();
}
