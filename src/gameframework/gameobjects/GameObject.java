package gameframework.gameobjects;

import java.awt.*;
import java.awt.image.BufferedImage;

public abstract class GameObject
{
    private String name;
    private int type;
    private int x;
    private int y;
    private int z;

    protected int velX;
    protected int velY;

    private int scaleWidth;
    private int scaleHeight;

    private BufferedImage image;

    public GameObject(String name, int type,
                      int x, int y, int z,
                      int scaleWidth, int scaleHeight
                      )
    {
        this.name = name;
        this.type = type;
        this.x = x;
        this.y = y;
        this.z = z;
        this.scaleHeight = scaleHeight;
        this.scaleWidth = scaleWidth;
        velX = velY = 0;
    }


    public BufferedImage getImage()
    {
        return image;
    }

    public void setImage(BufferedImage image)
    {
        if (image != null)
            this.image = image;
    }

    public void update()
    {
        x += velX;
        y += velY;
    }

    public void render(Graphics g)
    {
        g.drawImage(getImage(), x, y, scaleWidth, scaleHeight, null );
    }
}
