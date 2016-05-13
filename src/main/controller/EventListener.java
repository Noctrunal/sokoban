package main.controller;

import main.model.Direction;

public interface EventListener {
    void move(Direction direction);

    void restart();

    void startNextLevel();

    void levelCompleted(int level);

}
