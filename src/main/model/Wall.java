package main.model;

import java.awt.*;

public class Wall extends CollisionObject {
    public Wall(int x, int y) {
        super(x, y);
    }

    @Override
    public void draw(Graphics graphics) {
        graphics.setColor(new Color(80, 55, 25));
        graphics.fillRect(this.getX(), this.getY(), this.getWidth(), this.getHeight());
    }
}
