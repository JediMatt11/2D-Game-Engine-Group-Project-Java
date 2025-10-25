package gameframework.collision;

import gameframework.gameobjects.GameObject;
import gameframework.gameobjects.GameObjects;

import java.awt.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedList;

public class CollisionRegions
{
    private static int gridSizeX = 1;
    private static int gridSizeY = 1;

    //Assuming camera origin is always (0,0)
    private static int gridCellLength = Integer.MAX_VALUE;
    private static int gridCellHeight = Integer.MAX_VALUE;

    private static ArrayList<ArrayList<HashSet<GameObject>>> collisionRegions = new ArrayList<>();

    //Assign the object a collision region if not already in one or
    //Call this every time the object changes its position
    public static Point assignCollisionRegion(GameObject object) {
        Point currentRegion = object.getCurrentCollisionRegion();

        //Get which grid cell the object will be in.
        int newRegionX = object.getX() / gridCellLength;
        int newRegionY = object.getY() / gridCellHeight;

        //Object out of bounds.
        if (newRegionX > gridSizeX - 1 || newRegionX < 0 || newRegionY > gridSizeY - 1 || newRegionY < 0)
            return new Point(-1, -1);

        //Doesn't need to be reassigned.
        if (currentRegion.x == newRegionX && currentRegion.y == newRegionY)
            return currentRegion;

        //Remove from current collision region (if in one) and add to new one
        if (currentRegion.x >= 0 && currentRegion.y >= 0) //If x or y are -1, that means the object wasn't in a valid region and isn't on any of the lists.
            collisionRegions.get(currentRegion.x).get(currentRegion.y).remove(object);

        collisionRegions.get(newRegionX).get(newRegionY).add(object);

        //Return new collision region
        return new Point(newRegionX, newRegionY);
    }

    public static HashSet<GameObject> getCollisionRegionObjects(Point currentRegion) {
        //Return collision region if exists
        if (currentRegion.x < 0 || currentRegion.y < 0 || currentRegion.x > collisionRegions.size() - 1 || currentRegion.y > collisionRegions.get(0).size() - 1)
            return null;
        else
            return collisionRegions.get(currentRegion.x).get(currentRegion.y);
    }

    public static void setLevelBounds(int newLevelLength, int newLevelHeight) {
        gridCellLength = newLevelLength / gridSizeX;
        gridCellHeight = newLevelHeight / gridSizeY;
    }

    public static boolean setCollisionRegionGridSize(int x, int y) {
        //Min value check
        if (x < 1 || y < 1)
            return false;

        gridSizeX = x;
        gridSizeY = y;

        //Setup array list
        collisionRegions = new ArrayList<>();
        for (int i = 0; i < x; i ++)
        {
            ArrayList<HashSet<GameObject>> yList = new ArrayList<>();
            for (int j = 0; j < y; j++)
                yList.add(new HashSet<GameObject>());
            collisionRegions.add(yList);
        }
        return true;
    }

    public static int getGridSizeX()
    {
        return gridSizeX;
    }

    public static int getGridSizeY()
    {
        return gridSizeY;
    }
}
