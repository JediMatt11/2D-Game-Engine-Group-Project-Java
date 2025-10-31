package gameframework.weapons;

import java.awt.event.KeyEvent;
//abstract factory for creating weapons in the game
//each weapon has its own weapon factory which implements this factory
public interface WeaponFactory {
    void createWeapon();
}


