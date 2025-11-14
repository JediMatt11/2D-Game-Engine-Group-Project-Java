package gameframework.gameobjects;

import gameframework.GameThread;
import gameframework.animations.Animation;
import gameframework.gamecharacters.Player;
import java.awt.image.BufferedImage;

public class Collectible extends GameObject
{
    private final int X_COLLECTION_BOUNDS  = 150;
    private final int Y_COLLECTION_BOUNDS = 150;
    public Collectible(String name, int x, int y, int z, int scaleWidth, int scaleHeight)
    {
        super(name, GameObjectType.COLLECTIBLE, x, y, z, scaleWidth, scaleHeight);
        BufferedImage image = GameThread.resourceManager.loadImageResource(name, GameThread.getCurrentLevel().getName());
        initializeBaseAnimation(image);
    }


    protected void initializeBaseAnimation(BufferedImage image)
    {
        Animation inanimate = new Animation(image, getName(), scaleWidth, scaleHeight);

        /* If these borders will apply to an inanimate object that won't
         * ever be moving then its a good idea to adjust the border points
         * positions in the animation to reflect directly their final
         * positions on the screen, otherwise we have to recalculate the
         * positions on the fly every time while the game is running which
         * will (needlessly) affect performance.
         */
        if (!inanimate.getCurrentFrameBorders(0, 0, false).verifyBoundPoints())
            System.out.println("Unable to verify bound points for " + getName());
        changeActiveAnimation(inanimate, true);
    }


    @Override
    public boolean handleObjectCollision(GameObject object)
    {

        return false;
    }

    @Override
    public void update(GameObjects objects)
    {
        int playerX = Player.getActivePlayer().getX();
        int playerY = Player.getActivePlayer().getY();

        if (Math.abs(playerX-getX())<X_COLLECTION_BOUNDS && Math.abs(playerY-getY())<Y_COLLECTION_BOUNDS)
            collect();

        super.update(objects);
    }

    public void collect()
    {
        System.out.println(this+" COLLECTED");
        GameThread.data.removeObject(this);
    }


}
