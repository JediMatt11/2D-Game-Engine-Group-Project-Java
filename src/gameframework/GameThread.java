package gameframework;

import gameframework.display.GameDisplay;
import gameframework.resourcemanagement.ResourceManager;

import java.util.ArrayList;

public class GameThread
{
    /* How many times we aim to update the status of every object and character in the game each second.
       Ideally we want the frame rate to match this, render after every update, but it is not always possible */
    public final static int UPDATES_PER_SECOND = 60;
    private boolean gameOver;
    public static GameData data;
    public static GameDisplay display;
    public static ResourceManager resourceManager;

    private static ArrayList<GameLevel> levels;
    private static int curLevelNumber;

    public GameThread()
    {
        gameOver = false;
        data = new GameData();
        initializeGameDisplay();
        resourceManager = new ResourceManager();
        levels = new ArrayList<GameLevel>();
        curLevelNumber = 0;
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

    public boolean initializeGameDisplay()
    {
        boolean success = true;
        display = new GameDisplay(data);
        return success;
    }

    public void setGameTitle(String title)
    {
        display.setTitle(title);
    }

    public boolean isGameOver() { return gameOver;}
    // This method triggers the update of every object in the game
    public void update() {}
    // This method triggers the rendering of every object in the game
    public void render()
    {
        display.repaint();
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
                long remaining = updateInterval - elapsedTime;
                try
                {
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
                updates = frames = 0;
                startTime = System.nanoTime();
            }

        }


    }

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
