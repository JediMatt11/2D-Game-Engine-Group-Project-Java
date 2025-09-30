package gameframework.animations;

import gameframework.GameThread;

import java.awt.image.BufferedImage;

public class Spritesheet
{
    private final static String SPRITESHEET_FOLDER = "spritesheets/";
    private String name;
    private int numRows;
    private int numCols;
    private int spriteCount;
    private BufferedImage spritesheet;

    public Spritesheet(String name, int numRows, int numCols,
                       int spriteCount)
    {
        this.name = name;
        spritesheet = GameThread.resourceManager.
                loadImageResource(SPRITESHEET_FOLDER + name);
        this.numRows = numRows;
        this.numCols = numCols;
        this.spriteCount = spriteCount;
    }

    public BufferedImage[] convertToImageArray()
    {
        BufferedImage[] images = new BufferedImage[spriteCount];
        int nextImagePos = 0;

        for (int row = 0; row < numRows; row++)
            for (int col = 0; col < numCols; col++)
            {
                if (nextImagePos < spriteCount)
                {
                    images[nextImagePos] = getSprite(row, col);
                    nextImagePos++;
                }
            }
        return images;
    }

    private BufferedImage getSprite(int row, int col)
    {
        int spriteWidth = spritesheet.getWidth() / numCols;
        int spriteHeight = spritesheet.getHeight() / numRows;

        return spritesheet.getSubimage(col * spriteWidth, row * spriteHeight,
                spriteWidth, spriteHeight);

    }

    public String getName() {
        return name;
    }
}
