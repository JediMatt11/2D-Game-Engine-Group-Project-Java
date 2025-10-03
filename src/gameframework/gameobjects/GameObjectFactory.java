package gameframework.gameobjects;

import java.util.Scanner;

public class GameObjectFactory
{
    private int posX;
    private int posY;
    private int type;
    private String subtype;
    private int scaleWidth;
    private int scaleHeight;

    public GameObject createGameObject(String objectStr)
    {
        GameObject gameObject = null;
        Scanner scanner = new Scanner(objectStr);
        posX = scanner.nextInt();
        posY = scanner.nextInt();
        type = scanner.nextInt();
        subtype = scanner.next();
        scaleWidth = scanner.nextInt();
        scaleHeight = scanner.nextInt();


        switch (type)
        {
            case ObjectType.NPC:
                break;
            case ObjectType.INANIMATE:
                gameObject = new InanimateObject(subtype, posX, posY, 2, scaleWidth, scaleHeight);
                break;
        }
        return gameObject;
    }


}
