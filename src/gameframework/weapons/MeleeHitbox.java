package gameframework.weapons;

import gameframework.gamecharacters.GameCharacter;

import java.awt.*;


public class MeleeHitbox implements BoxHit {

    private GameCharacter weaponHolder;
    private Rectangle bounds;

    public MeleeHitbox(GameCharacter weaponHolder, int width, int height) {
        this.setWeaponHolder(weaponHolder);
        setBounds(new Rectangle(weaponHolder.getX(),weaponHolder.getY(),width,height));
    }

    @Override
    public void updatePosition(int x, int y) {
        bounds.setBounds(x, y,bounds.width,bounds.height);
    }

    public GameCharacter getWeaponHolder() {
        return weaponHolder;
    }

    public void setWeaponHolder(GameCharacter weaponHolder) {
        this.weaponHolder = weaponHolder;
    }

    @Override
    public Rectangle getBounds() {
        return bounds;
    }

    public void setBounds(Rectangle bounds) {
        this.bounds = bounds;
    }
}
