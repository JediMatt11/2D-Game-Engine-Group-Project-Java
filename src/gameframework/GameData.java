package gameframework;

import gameframework.display.GameDisplay;
import gameframework.gamecharacters.Player;
import gameframework.gameobjects.GameObject;

import java.awt.*;
import java.util.LinkedList;

public class GameData
{
    private LinkedList<GameObject> objects;

    public GameData()
    {
        objects = new LinkedList<GameObject>();

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
        for (GameObject object : objects)
            object.update(objects);
    }

    public LinkedList<GameObject> getObjects() {
        return objects;
    }
}
