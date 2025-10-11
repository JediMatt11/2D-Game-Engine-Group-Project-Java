package gameframework.gameobjects;

import gameframework.GameThread;
import gameframework.animations.Animation;

import java.awt.image.BufferedImage;

public class InanimateObject extends GameObject
{
    public InanimateObject(String name,
                           int x, int y, int z,
                           int scaleWidth, int scaleHeight)
    {
        super(name, GameObjectType.INANIMATE, x, y, z,
                scaleWidth, scaleHeight);
        BufferedImage image = GameThread.resourceManager.loadImageResource(name, GameThread.getCurrentLevel().getName());
        changeActiveAnimation(new Animation(image, name, scaleWidth, scaleHeight));
    }
}
