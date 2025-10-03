package gameframework;

import gameframework.gameobjects.GameObject;

import java.util.LinkedList;

public class GameData
{
    private LinkedList<GameObject> objects;

    public GameData()
    {
        objects = new LinkedList<GameObject>();

        //load game level

        try
        {
            GameThread.getCurrentLevel().load();
        }
        catch (Exception e)
        {
            System.out.println("Unable to load level " +
                    GameThread.getCurrentLevel().getName());
        }


    }



    public void update()
    {
        GameThread.player.update();

        for (GameObject object : objects)
            object.update();
    }

    public LinkedList<GameObject> getObjects() {
        return objects;
    }
}
