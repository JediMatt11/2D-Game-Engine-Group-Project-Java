package gameframework.weapons;

import gameframework.gameobjects.GameObject;
import gameframework.gameobjects.GameObjects;
import gameframework.gameobjects.InanimateObject;
import gameobjects.ThrownWeapon;

//copied this code from thrown weapon class in ninjaGame
public class Projectile extends InanimateObject {

    private boolean thrownByPlayer;
    private GameObject owner;
    private HitBox hitbox;


    public Projectile(String name, int x, int y, int vx, int vy, int scaleWidth, int scaleHeight,
                        boolean thrownByPlayer, GameObject owner) {
        super(name, x, y, 1, scaleWidth, scaleHeight);
        velX = vx;
        velY = vy;


        this.setThrownByPlayer(thrownByPlayer);
        this.setOwner(owner);

        hitbox = new HitBox(x, y, scaleWidth, scaleHeight);

    }

    public void createProjectile(){

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
        x += velX;
        y += velY;

        updateHitBox();

        for (GameObject obj : objects) {
            if (obj == owner) continue;

            if (hitbox.intersects(obj)) {
                System.out.println("Projectile hit: " + obj.getName());
                obj.handleObjectCollision(this);
                objects.remove(this);
                break;
            }
        }


        //hitbox = hitboxStrategy.updateProjectileHitBox(this);
    }

    public void updateHitBox(){
        setHitbox(x,y,scaleWidth,scaleHeight);
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



    public HitBox getHitbox() {
        return hitbox;
    }

    public void setHitbox(int x, int y, int scaleWidth, int scaleHeight) {
        this.hitbox = new HitBox(x, y, scaleWidth, scaleHeight);
    }
}
