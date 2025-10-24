package gameframework.gameobjects;

import gameframework.gamecharacters.Player;
import java.util.LinkedList;
import java.awt.*;

/* This class extends the functionality of a normal LinkedList of
 * game objects in order to be able to add objects to the list based on
 * z order or optionally track objects that require frequent updating on
 * a hidden internal list, among other features.
 */
public class GameObjects extends LinkedList<GameObject>
{
    /* This list is to store references only to objects
     * that require frequent updating (objects that are
     * moving or changing state)
     */
    private GameObjects updateObjects;

    public GameObjects(boolean enableSublists)
    {
        super();
        if (enableSublists)
        {
            updateObjects = new GameObjects(false);
        }
    }

    @Override
    public boolean add(GameObject go)
    {
        /* Add objects to the game object list but make
         * sure objects with a higher z priority attribute
         * (lower value) are stored after in the list.
         * That way they will display over the
         * other objects.
         */
        boolean success = true;

        int index = size() - 1;
        int curZ = 0;

        if (index != -1)
            curZ = get(index).getZ();

        while (go.getZ() > curZ && index >= 0)
        {
            index--;
            if (index >= 0)
                curZ = get(index).getZ();
        }
        add(index + 1, go);

        //add objects that require frequent
        //updating to the update list
        if (go.requiresUpdating() && updateObjects != null)
            updateObjects.add(go);

        return success;
    }

    @Override
    public boolean remove(Object go)
    {
        //Overwritten to also remove object from internal
        //update list when appropriate
        boolean success = true;

        GameObject gameObject = (GameObject)go;

        if (gameObject.requiresUpdating() && updateObjects != null)
            updateObjects.remove(gameObject);

        success = super.remove(go);

        return success;
    }

    @Override
    public void clear()
    {
        super.clear();

        //Make sure to clear any objects from the internal
        //objects update list if any
        if (updateObjects != null)
            updateObjects.clear();
        return;
    }

    //Returns a sublist of all objects currently in this
    //list that are of the requested type. Returns null
    //if no objects of the requested type are found.
    public GameObjects getObjectsOfType(int type)
    {
        GameObjects foundObjects = null;

        for (GameObject go : this)
        {
            if (go.getType() == type)
            {
                if (foundObjects == null)
                    foundObjects = new GameObjects(false);
                foundObjects.add(go);
            }
        }
        return foundObjects;
    }

    //Return all objects within a certain area of the game world. The fullyEnclosed
    //parameter determines whether we filter out partially contained objects.
    public GameObjects getObjectsWithinBounds(Rectangle bounds, boolean fullyEnclosed)
    {
        GameObjects objectsWithin = new GameObjects(false);

        for (GameObject go: this)
        {
            if ( fullyEnclosed && go.isWithinBounds(bounds) ||
                    !fullyEnclosed && go.getBounds().intersects(bounds))
                objectsWithin.add(go);
        }
        return objectsWithin;

    }

    public GameObjects getUpdateObjects()
    {
        return updateObjects;
    }

}
