package gameframework.weapons;

import java.awt.*;


//using strategy design pattern to create hit boxes for melee weapons and ranged weapons
public interface BoxHit {

    //Rectangle createHitBox(int x, int y, int width, int height);
    void updatePosition(int x, int y);
    Rectangle getBounds();

}
