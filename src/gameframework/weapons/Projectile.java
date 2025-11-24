package gameframework.weapons;

import gameframework.GameThread;
import gameframework.gameobjects.GameObject;
import gameframework.gameobjects.GameObjects;
import gameframework.gameobjects.InanimateObject;
import gameobjects.ThrownWeapon;

import java.awt.*;

//copied this code from thrown weapon class in ninjaGame
public class Projectile extends InanimateObject {

    private boolean thrownByPlayer;
    private GameObject owner;
    private HitBox hitbox;
    private int vx;
    private int vy;


    public Projectile(String name, int x, int y, int vx, int vy, int scaleWidth, int scaleHeight,
                        boolean thrownByPlayer, GameObject owner) {
        super(name, x, y, 1, scaleWidth, scaleHeight);
        velX = vx;
        velY = vy;
        this.x=x;
        this.y=y;


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
    public void render(Graphics g){
        super.render(g);
        if(hitbox != null){
            Rectangle bounds = hitbox.getBounds();
            Color oldColor = g.getColor();
            g.setColor(Color.RED);
            g.drawRect(bounds.x, bounds.y, bounds.width, bounds.height);
            g.setColor(oldColor);
        }

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

    }

    public void updateHitBox(){
        setHitbox(x,y,scaleWidth,scaleHeight);
    }

    @Override
    public boolean handleCollision(GameObject collidingObject) {
        if (!collidingObject.isInanimate()) {
            GameThread.data.removeObjectWhenSafe(this);
            return true;
        }
        else
            return true;
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
