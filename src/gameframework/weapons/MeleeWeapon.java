package gameframework.weapons;

import gameframework.gamecharacters.GameCharacter;
import gameframework.gameobjects.GameObject;
import gameframework.gameobjects.GameObjects;

import java.awt.*;

public abstract class MeleeWeapon extends Weapon
{

    //pass hitbox into weapon constructor
    public MeleeWeapon(String name, GameCharacter weaponHolder, int damage, int width, int height) {
        super(name, weaponHolder, damage, width, height);
        MeleeHitbox mHitBox = new MeleeHitbox(weaponHolder,width,height);
    }

    @Override
    public void update(GameObjects objects){
        setPosition(getWeaponHolder().getX(), getWeaponHolder().getY());
        super.update(objects);
    }


}
