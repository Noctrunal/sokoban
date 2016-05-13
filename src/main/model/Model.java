package main.model;

import main.controller.EventListener;

import java.nio.file.Paths;
import java.util.Set;

public class Model {
    public static final int FIELD_SELL_SIZE = 20;

    private EventListener eventListener;
    private GameObjects gameObjects;
    private int currentLevel = 1;
    private LevelLoader levelLoader = new LevelLoader(
            Paths.get("D:\\projects javarush\\sokoban\\src\\main\\res\\levels.txt")
    );

    public GameObjects getGameObjects() {
        return gameObjects;
    }

    public void restartLevel(int level) {
        gameObjects = levelLoader.getLevel(level);
    }

    public void restart() {
        restartLevel(currentLevel);
    }

    public void startNextLevel() {
        currentLevel++;
        restart();
    }

    public void setEventListener(EventListener eventListener) {
        this.eventListener = eventListener;
    }

    public void move(Direction direction) {
        Player player = gameObjects.getPlayer();
        if (checkWallCollision(player, direction)) {
            return;
        }
        if (checkBoxCollision(direction)) {
            return;
        }

        int space = FIELD_SELL_SIZE;

        switch (direction) {
            case LEFT:
                player.move(-space, 0);
                break;
            case RIGHT:
                player.move(space, 0);
                break;
            case UP:
                player.move(0, -space);
                break;
            case DOWN:
                player.move(0, space);
                break;
        }
        checkCompletion();
    }

    public boolean checkWallCollision(CollisionObject gameObject, Direction direction) {
        Set<Wall> wallSet = gameObjects.getWalls();
        for (Wall wall : wallSet) {
            if (gameObject.isCollision(wall, direction)) {
                return true;
            }
        }
        return false;
    }

    public boolean checkBoxCollision(Direction direction) {
        Player player = gameObjects.getPlayer();
        Set<GameObject> gameObjectSet = gameObjects.getAll();
        Set<Box> boxSet = gameObjects.getBoxes();
        int thisX = player.getX();
        int thisY = player.getY();
        int space = FIELD_SELL_SIZE;
        switch (direction) {
            case LEFT:
                thisX -= space;
                break;
            case RIGHT:
                thisX += space;
                break;
            case UP:
                thisY -= space;
                break;
            case DOWN:
                thisY += space;
                break;
        }

        GameObject stopped = null;
        for (GameObject gameObject : gameObjectSet) {
            if ((thisX == gameObject.getX())
                    && (thisY == gameObject.getY())
                    && !(gameObject instanceof Player)
                    && !(gameObject instanceof Home)) {
                stopped = gameObject;
            }
        }

        if (stopped == null) {
            return false;
        }

        if (stopped instanceof Box) {
            Box stoppedBox = (Box) stopped;
            if (checkWallCollision(stoppedBox, direction)) {
                return true;
            }

            for (Box box : boxSet) {
                if (stoppedBox.isCollision(box, direction)) {
                    return true;
                }
            }

            switch (direction) {
                case LEFT:
                    stoppedBox.move(-space, 0);
                    break;
                case RIGHT:
                    stoppedBox.move(space, 0);
                    break;
                case UP:
                    stoppedBox.move(0, -space);
                    break;
                case DOWN:
                    stoppedBox.move(0, space);
                    break;
            }
        }
        return false;
    }

    public void checkCompletion() {
        Set<Home> homeSet = gameObjects.getHomes();
        Set<Box> boxSet = gameObjects.getBoxes();
        boolean complete = true;

        for (Box box : boxSet) {
            boolean currentComplete = false;
            for (Home home : homeSet) {
                if (box.getX() == home.getX()
                        && box.getY() == home.getY()) {
                    currentComplete = true;
                }
            }
            if (!currentComplete) {
                complete = false;
            }
        }

        if (complete) {
            eventListener.levelCompleted(currentLevel);
        }
    }
}
