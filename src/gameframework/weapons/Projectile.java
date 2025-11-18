package gameframework.weapons;

import gameframework.gameobjects.GameObject;
import gameframework.gameobjects.GameObjects;
import gameframework.gameobjects.InanimateObject;
import gameobjects.ThrownWeapon;

//copied this code from thrown weapon class in ninjaGame
public class Projectile extends InanimateObject {

    private boolean thrownByPlayer;
    private GameObject owner;

    private int velocityX;
    private int velocityY;

    private RangedHitBoxStrategy hitboxStrategy;
    private HitBox hitbox;


    public Projectile(String name, int x, int y, int vx, int vy, int scaleWidth, int scaleHeight,
                        boolean thrownByPlayer, GameObject owner) {
        super(name, x, y, 1, scaleWidth, scaleHeight);
        velocityX = vx;
        velocityY = vy;

        this.setThrownByPlayer(thrownByPlayer);
        this.setOwner(owner);
        this.hitboxStrategy = new RangedHitBoxStrategy();

        hitbox = new HitBox(x, y, scaleWidth, scaleHeight);

    }

    public boolean wasThrownByPlayer() {
        return isThrownByPlayer();
    }

    public GameObject getOwner() {
        return owner;
    }


    @Override
    public void update(GameObjects objects) {
        super.update(objects);



        hitbox = hitboxStrategy.updateProjectileHitBox(this);
    }





    @Override
    public boolean handleObjectCollision(GameObject object) {
        // Ignore collisions with the thrower (owner)
        if (object == getOwner()) {
            return false;
        }

        // If the weapon was thrown by the player, don’t hit other player projectiles
        if (isThrownByPlayer() && object instanceof ThrownWeapon
                && ((ThrownWeapon) object).wasThrownByPlayer()) {
            return false;
        }

        // If the weapon was thrown by an enemy, don’t hit other enemy projectiles
        if (!isThrownByPlayer() && object instanceof ThrownWeapon
                && !((ThrownWeapon) object).wasThrownByPlayer()) {
            return false;
        }

        // Otherwise, handle normal collision (e.g., hit enemy or player)
        return handleCollision(object);
    }

    public boolean shouldIgnoreCollisionWith(GameObject other) {
        return other == this.getOwner();
    }

    public boolean isThrownByPlayer() {
        return thrownByPlayer;
    }

    public void setThrownByPlayer(boolean thrownByPlayer) {
        this.thrownByPlayer = thrownByPlayer;
    }

    public void setOwner(GameObject owner) {
        this.owner = owner;
    }

    public int getVelocityX() {
        return velocityX;
    }

    public void setVelocityX(int velocityX) {
        this.velocityX = velocityX;
    }

    public int getVelocityY() {
        return velocityY;
    }

    public void setVelocityY(int velocityY) {
        this.velocityY = velocityY;
    }

    public RangedHitBoxStrategy getStrategy() {
        return hitboxStrategy;
    }

    public void setStrategy(RangedHitBoxStrategy strategy) {
        this.hitboxStrategy = strategy;
    }
}
