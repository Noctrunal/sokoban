package main.model;

import java.awt.*;

public class Box extends CollisionObject implements Movable {
    public Box(int x, int y) {
        super(x, y);
    }

    @Override
    public void draw(Graphics graphics) {
        graphics.setColor(new Color(250, 180, 5));
        graphics.fillRect(this.getX(), this.getY(), this.getWidth(), this.getHeight());
    }

    @Override
    public void move(int x, int y) {
        int thisX = this.getX() + x;
        int thisY = this.getY() + y;
        this.setX(thisX);
        this.setY(thisY);
    }
}
