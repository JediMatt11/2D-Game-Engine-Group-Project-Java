package gameframework.weapons;

import gameframework.GameThread;
import gameframework.gamecharacters.GameCharacter;
import gameframework.gameobjects.GameObject;
import gameframework.gameobjects.GameObjects;
import gameobjects.ThrownWeapon;

import java.awt.*;

//need to initialize projectile within this class

public abstract class RangedWeapon extends Weapon
{
    private ProjectileStrategy projectileStrategy;

    //pass hitbox into weapon constructor
    public RangedWeapon(String name, GameCharacter weaponHolder, int damage, int width, int height) {
        super(name, weaponHolder, damage, width, height);
    }

    public void fire() {
        Projectile p = projectileStrategy.createProjectile(this);
        GameThread.data.addObject(p);
    }



}
