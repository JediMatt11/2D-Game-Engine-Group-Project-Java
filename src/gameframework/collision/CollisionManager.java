package gameframework.collision;

import gameframework.gameobjects.Direction;
import gameframework.gameobjects.GameObject;
import gameframework.gameobjects.GameObjects;

import java.awt.*;
import java.util.List;

public class CollisionManager {
    private GameObjects gameObjects;

    public CollisionManager(GameObjects gameObjects) {
        this.gameObjects = gameObjects;
    }

    public void resolveGlobalCollisions() {
        //Get all updated objects
        List<GameObject> all = gameObjects.getUpdateObjects();

        //Loop through each logical and handle movable ones
        for (GameObject objectFirst : all) {
            if (objectFirst.isUnmovable()) continue;

            //Get neighboring objects to lessen runtime
            GameObjects nearby = gameObjects.getNeighborObjects(objectFirst);

            for (int i = 0; i < nearby.size(); i++) {
                GameObject objectTwo = nearby.get(i);

                if (objectFirst == objectTwo) continue;
                if (objectFirst.isUnmovable() && objectTwo.isUnmovable()) continue;


                if (objectFirst.collidesWith(objectTwo)) {
                    resolveOverlapAxis(objectFirst, objectTwo, true);
                    resolveOverlapAxis(objectFirst, objectTwo, false);
                }
            }
        }
    }

    /*
    Resolves overlaps depending on if it is coming from a horizontal and vertical direction separately.
     */
    private void resolveOverlapAxis(GameObject objectFirst, GameObject objectSecond, boolean horizontal) {
        //Get collision bounds for the first object and the second object it is colliding with
        Rectangle objectFirstBounds = objectFirst.getCollisionBounds();
        Rectangle objectSecondBounds = objectSecond.getCollisionBounds();

        //Determine any overlap from the horizontal or vertical direction
        int overlapX = Math.min(objectFirstBounds.x + objectFirstBounds.width, objectSecondBounds.x + objectSecondBounds.width)
                - Math.max(objectFirstBounds.x, objectSecondBounds.x);
        int overlapY = Math.min(objectFirstBounds.y + objectFirstBounds.height, objectSecondBounds.y + objectSecondBounds.height)
                - Math.max(objectFirstBounds.y, objectSecondBounds.y);

        if (overlapX <= 0 || overlapY <= 0)
            return; // if no overlap simply exit

        //Determine if both objects are animated or inanimate
        boolean firstMovable = !objectFirst.isUnmovable();
        boolean secondMovable = !objectSecond.isUnmovable();
        if (!firstMovable && !secondMovable)
            return; //if neither are animate then exit


        //Get the current positions of both objects
        double firstObjectPositionX = objectFirst.getX(), firstObjectPositionY = objectFirst.getY();
        double secondObjectPositionX = objectSecond.getX(), secondObjectPositionY = objectSecond.getY();

        //Split the overlap correction evenly on the horizontal and vertical axis
        double pushHoriz = overlapX * 0.5;
        double pushVert = overlapY * 0.5;

        //Handle depending on direction of movement
        if (horizontal) {
            int direct = (objectFirstBounds.x < objectSecondBounds.x) ? -1 : 1;
            // push the object away from the other depending on where they are facing ech other (right or left)

            //If both objects move, move them for half the overlap away from each other
            //If one of either object is movable, move that one only
            if (firstMovable && secondMovable) {
                objectFirst.setPosition((int)(firstObjectPositionX + direct * pushHoriz), (int)firstObjectPositionY);
                objectSecond.setPosition((int)(secondObjectPositionX - direct * pushHoriz), (int)secondObjectPositionY);
            } else if (firstMovable) {
                objectFirst.setPosition((int)(firstObjectPositionX + direct * overlapX), (int)firstObjectPositionY);
            } else if (secondMovable) {
                objectSecond.setPosition((int)(secondObjectPositionX - direct * overlapX), (int)secondObjectPositionY);
            }
        } else {
            int direct = (objectFirstBounds.y < objectSecondBounds.y) ? -1 : 1;
            //Push up or down depending on direction

            if (firstMovable && secondMovable) {
                objectFirst.setPosition((int)firstObjectPositionX, (int)(firstObjectPositionY + direct * pushVert));
                objectSecond.setPosition((int)secondObjectPositionX, (int)(secondObjectPositionY - direct * pushVert));
            } else if (firstMovable) {
                objectFirst.setPosition((int)firstObjectPositionX, (int)(firstObjectPositionY + direct * overlapY));
            } else if (secondMovable) {
                objectSecond.setPosition((int)secondObjectPositionX, (int)(secondObjectPositionY - direct * overlapY));
            }
        }
    }
}
