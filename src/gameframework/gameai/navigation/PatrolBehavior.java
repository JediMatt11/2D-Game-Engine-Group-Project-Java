package gameframework.gameai.navigation;

import gameframework.gamecharacters.GameCharacter;
import gameframework.gameobjects.GameObjects;

import java.awt.*;

public class PatrolBehavior {

    private final GameCharacter enemy;
    private final Point leftBound;
    private final Point rightBound;
    private final LedgeDetector ledgeDetector = new LedgeDetector();
    private boolean movingRight = true;

    public PatrolBehavior(GameCharacter enemy, Point leftBound, Point rightBound) {
        this.enemy = enemy;
        this.leftBound = leftBound;
        this.rightBound = rightBound;
    }

    public boolean isMovingRight() {
        return movingRight;
    }

    public void patrol(GameObjects objects) {

        boolean ledgeAhead = !ledgeDetector.hasFloorAhead(enemy, movingRight, objects);

        if (ledgeAhead) {
            movingRight = !movingRight;
        }
        else if (movingRight && enemy.getX() >= rightBound.x) {
            movingRight = false;
        }
        else if (!movingRight && enemy.getX() <= leftBound.x) {
            movingRight = true;
        }

        // If true, start movement
        if (movingRight) enemy.moveRight(true);
        else enemy.moveLeft(true);
    }
}