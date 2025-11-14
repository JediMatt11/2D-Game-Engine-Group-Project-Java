package gameframework.weapons;

import gameframework.gameobjects.GameObject;
import gameframework.gameobjects.InanimateObject;
import gameobjects.ThrownWeapon;

//copied this code from thrown weapon class in ninjaGame
public class Projectile extends InanimateObject {

    protected boolean thrownByPlayer;
    protected GameObject owner;  // The object that threw this weapon

    public Projectile(String name, int x, int y, int z, int scaleWidth, int scaleHeight,
                        boolean thrownByPlayer, GameObject owner) {
        super(name, x, y, z, scaleWidth, scaleHeight);
        this.thrownByPlayer = thrownByPlayer;
        this.owner = owner;
    }

    public boolean wasThrownByPlayer() {
        return thrownByPlayer;
    }

    public GameObject getOwner() {
        return owner;
    }

    @Override
    public boolean handleObjectCollision(GameObject object) {
        // Ignore collisions with the thrower (owner)
        if (object == owner) {
            return false;
        }

        // If the weapon was thrown by the player, don’t hit other player projectiles
        if (thrownByPlayer && object instanceof ThrownWeapon
                && ((ThrownWeapon) object).wasThrownByPlayer()) {
            return false;
        }

        // If the weapon was thrown by an enemy, don’t hit other enemy projectiles
        if (!thrownByPlayer && object instanceof ThrownWeapon
                && !((ThrownWeapon) object).wasThrownByPlayer()) {
            return false;
        }

        // Otherwise, handle normal collision (e.g., hit enemy or player)
        return handleCollision(object);
    }

    public boolean shouldIgnoreCollisionWith(GameObject other) {
        return other == this.getOwner();
    }

}
