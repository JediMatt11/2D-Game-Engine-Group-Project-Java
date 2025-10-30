package gameframework;

import gameframework.display.GameDisplay;
import gameframework.gamecharacters.Player;
import gameframework.gameobjects.GameObject;
import gameframework.gameobjects.GameObjects;

import java.awt.*;

public class GameData
{
    private GameObjects objects;

    public GameData()
    {
        //initialize game objects list
        objects = new GameObjects(true, 6, 1);

        //load first game level
        try
        {
            GameThread.getCurrentLevel().load(this);
        }
        catch (Exception e)
        {
            System.out.println("Unable to load level " +
                    GameThread.getCurrentLevel().getName() + "\n" +
                    "Reason: " + e.getMessage());
        }
    }

    //Add a playable character, if the boolean is set to true then this is set
    //as the initial player character (can switch characters later on)
    public boolean addPlayableCharacter(Player playableCharacter,
                                        boolean startingCharacter)
    {
        boolean success = true;

        Player.addPlayer(playableCharacter, startingCharacter);

        if (startingCharacter)
        {
            objects.add(playableCharacter);
            //override position of the character with that of the current level
            Point playerStartPos = GameThread.getCurrentLevel().getPlayerStartPos();
            playableCharacter.setPosition(playerStartPos.x, playerStartPos.y);
        }
        return success;

    }

    /*This method is trigger during each update of the game loop, it iterates through the whole list of
      objects and allows them to use a fraction of the time to do their tasks and update themselves..*/
    public void update()
    {
        GameObjects objectsToUpdate = objects.getUpdateObjects();
        for (GameObject go : objectsToUpdate)
            go.update(objects);

       /* for (GameObject object : objects)
            object.update(objects);*/
    }

    //get all objects in the game
    public GameObjects getObjects() {
        return objects;
    }

    //Returns a list of all objects in the game that are of the requested type.
    GameObjects getObjectsOfType(int type)
    {
        GameObjects foundObjects = objects.getObjectsOfType(type);
        return foundObjects;

    }
}
