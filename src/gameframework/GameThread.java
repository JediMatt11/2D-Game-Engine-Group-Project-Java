package gameframework;

import gameframework.display.GameDisplay;
import gameframework.gamecharacters.Player;
import gameframework.gameobjects.GameObjectFactory;
import gameframework.gameobjects.GameObjects;
import gameframework.inputhandlers.KeyboardHandler;
import gameframework.resourcemanagement.ResourceManager;

import java.util.ArrayList;

/*
 * This class contains the functionality to start and run the engine. All games based on the engine must implement
 * their own game thread class inheriting this one, instantiate an object of the thread and call the gameRun() method,
 * that will activate the engine. This class implements the game loop, the game loop iterates through all objects and
 * allows them some execution time to update themselves, it also controls the amount of frames we render per second
 * and triggers the actual rendering of every object.
 */
public class GameThread
{
    /* How many times we aim to update the status of every object and character in the game each second.
       Ideally we want the frame rate to match this, render after every update, but it is not always possible.
       For the time being Game Developers can only fine tune this if they have access to the source code. */
    public final static int UPDATES_PER_SECOND = 60;

    public static GameData data;
    public static GameDisplay display;
    public static ResourceManager resourceManager = new ResourceManager();
    public static GameObjectFactory gameObjectFactory;
    public static volatile boolean displayFrameUpdateRate;
    private boolean gameOver;

    // Information for levels in the game
    private static final ArrayList<GameLevel> levels = new ArrayList<GameLevel>();
    private static int curLevelNumber;

    /* Information for tuning different optimizations in the game */

    /* To optimize collision and interaction with other objects in general we can divide the list of objects into
     * different sublists representing different areas of the game background, the amount of areas can be set up
     * by game developers using this tuning constants (width and height of the grid of sections/areas into which
     * the game background is divided). */
    public static int AREA_GRID_COLS = 0;
    public static int AREA_GRID_ROWS = 0;

    /********/

    /* The GameThread needs to receive a specific game object factory in order to be able to create
     * game specific objects when loading levels, if not provided (null) then it will create a general
     * engine object factory that only handles general types supported by the engine (specified in the
     * ObjectType class).
     */
    public GameThread(GameObjectFactory initGameObjectFactory)
    {
        gameOver = false;

        //initialize game object factory
        if (initGameObjectFactory != null)
            gameObjectFactory = initGameObjectFactory;
        else
            gameObjectFactory = new GameObjectFactory();

        //initialize resource loader/manager
        resourceManager = new ResourceManager();

        //initialize data and display window
        initializeGameDisplay();
        data = new GameData();
        display.setData(data);

        curLevelNumber = 0;
        displayFrameUpdateRate = false;
    }

    private boolean initializeGameDisplay()
    {
        boolean success = true;
        display = new GameDisplay(data);
        return success;
    }

    public static void addLevel(GameLevel level)
    {
        levels.add(level);
    }

    public static GameLevel getCurrentLevel()
    {
        GameLevel curLevel = null;
        if (curLevelNumber >= 0 && curLevelNumber < levels.size())
            curLevel = levels.get(curLevelNumber);
        return curLevel;
    }

    public void setGameTitle(String title)
    {
        display.setTitle(title);
    }

    public boolean isGameOver() { return gameOver;}

    // Add a playable character, if the boolean is set to true then this is set
    // as the initial player character (can switch characters later on)
    public static boolean addPlayableCharacter(Player playableCharacter,
                                               boolean startingCharacter)
    {
        return data.addPlayableCharacter(playableCharacter, startingCharacter);
    }

    // Change playable character to the next available one
    public static void changePlayableCharacter()
    {
        // Make sure to spawn it on the same position
        Player player = Player.getActivePlayer();
        GameObjects gameObjects = data != null ? data.getObjects() : null;

        if (player != null && gameObjects != null)
        {
            int x = player.getX();
            int y = player.getY();

            /* Player changes are usually triggered from input event threads, so make sure to
             * use thread safe methods */
            //gameObjects.remove(player);
            data.removeObject(player);        //thread safe method
            player = Player.nextPlayer();
            //gameObjects.add(player);
            data.addObject(player);           //thread safe method
            player.setPosition(x, y);
        }
    }

    public static void changeKeyboardHandler(KeyboardHandler newKeyboardHandler)
    {
        if (newKeyboardHandler != null)
            display.changeKeyboardHandler(newKeyboardHandler);
    }

    // This method triggers the update of every object in the game
    public void update()
    {
        data.update();
    }

    // This method triggers the rendering of every object in the game
    public void render()
    {
        display.render();
    }

    private void gameLoop() throws Exception
    {
        final long NANOSECONDS_PER_SECOND = 1000000000;
        long startTime = System.nanoTime();           // time when loop starts
        long elapsedTime = 0, curTime = 0, lastTime = startTime;
        int frames = 0, updates = 0;                  // counters for FPS & UPS
        // Each update should occur every (1/60) seconds  => 16,666,666 ns
        long updateInterval = NANOSECONDS_PER_SECOND / UPDATES_PER_SECOND;
        boolean refresh = false;

        while (!isGameOver())
        {
            // Measure time passed since last loop iteration
            curTime = System.nanoTime();
            elapsedTime += curTime - lastTime;
            lastTime = curTime;

            if (elapsedTime < updateInterval )
            {
                // Ahead of schedule (not enough time has passed for next update)
                // So we don't need to hog CPU resources until really needed
                long remaining = updateInterval - elapsedTime;
                try
                {
                    //sleep until we really need to update
                    Thread.sleep(remaining / 1000000);
                }
                catch (InterruptedException ie)
                {

                }
            }
            else
            {
                // On schedule or behind schedule
                while (elapsedTime >= updateInterval)
                {
                    // Perform as many updates as needed if we’ve fallen behind
                    update();
                    updates++;
                    refresh = true;
                    elapsedTime -= updateInterval;
                }

                // Only render once after at least one update occurred
                if (refresh)
                {
                    render();
                    frames++;
                    refresh = false;
                }
            }

            //check when its been a full second and display update and frame rates (how many updates & frames occurred in that second)
            if (curTime - startTime >= NANOSECONDS_PER_SECOND)
            {
                //A full second has passed
                System.out.println("Updates:" + updates + " Frames:" + frames);
                if (displayFrameUpdateRate)
                    display.setMessage("Updates:" + updates + " Frames:" + frames);
                else if (display.getMessage().startsWith("Updates:"))
                    display.setMessage("");

                updates = frames = 0;
                startTime = System.nanoTime();
            }

        }
    }

    // This is the method all games should call to start the engine and trigger the game loop
    public void gameRun() throws Exception
    {
        try
        {
            gameLoop();
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }

}
