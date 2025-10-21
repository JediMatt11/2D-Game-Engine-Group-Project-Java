package gameframework.collision;

import gameframework.gameobjects.GameObject;

import java.util.LinkedList;

public class CollisionLayers
{
    public static final LinkedList<GameObject> characterLayer = new LinkedList<>();
    public static final LinkedList<GameObject> groundLayer = new LinkedList<>();
    public static final LinkedList<GameObject> triggerLayer = new LinkedList<>();
}
