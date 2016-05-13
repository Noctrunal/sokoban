package main.model;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class LevelLoader {
    private Path levels;

    public LevelLoader(Path levels) {
        this.levels = levels;
    }

    public GameObjects getLevel(int level) {
        int currentLevel = (level % 60 == 0) ? 60 : level % 60;
        String currentLineLevel = "Maze: " + currentLevel;
        List<String> listLevel = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(levels.toFile()))) {
            String currentLine = "";
            while (!currentLineLevel.equals(currentLine)) {
                currentLine = reader.readLine();
            }
            for (int i = 0; i < 7; i++) {
                currentLine = reader.readLine();
            }
            while (!"".equals(currentLine)) {
                listLevel.add(currentLine);
                currentLine = reader.readLine();
            }
        } catch (IOException ignore) {}
            Player player = null;
            Set<Wall> wallSet = new HashSet<>();
            Set<Box> boxSet = new HashSet<>();
            Set<Home> homeSet = new HashSet<>();
        for (int i = 0; i < listLevel.size(); i++) {
            String fileContent = listLevel.get(i);
            for (int j = 0; j < fileContent.length(); j++) {
                int x = Model.FIELD_SELL_SIZE / 2 + Model.FIELD_SELL_SIZE * j;
                int y = Model.FIELD_SELL_SIZE / 2 + Model.FIELD_SELL_SIZE * i;
                char gameObjects = fileContent.charAt(j);
                switch (gameObjects) {
                    case 'X':
                        wallSet.add(new Wall(x, y));
                        break;
                    case '*':
                        boxSet.add(new Box(x, y));
                        break;
                    case '.':
                        homeSet.add(new Home(x, y));
                        break;
                    case '&':
                        boxSet.add(new Box(x, y));
                        homeSet.add(new Home(x, y));
                        break;
                    case '@':
                        player = new Player(x, y);
                        break;
                }
            }
        }
        return new GameObjects(wallSet, boxSet, homeSet, player);
    }
}
