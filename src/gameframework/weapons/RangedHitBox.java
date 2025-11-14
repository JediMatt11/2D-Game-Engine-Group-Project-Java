package gameframework.weapons;

import java.awt.*;

public class RangedHitBox implements BoxHit {

    private Projectile projectile;
    private Rectangle bounds;

    public RangedHitBox(Projectile projectile, int width, int height){
        this.projectile = projectile;
        bounds = new Rectangle(projectile.getPosition().x, projectile.getPosition().y, width, height);
    }

    @Override
    public void updatePosition(int x, int y) {
        bounds.setBounds(x,y,bounds.width,bounds.height);
    }

    @Override
    public Rectangle getBounds() {
        return bounds;
    }
}
