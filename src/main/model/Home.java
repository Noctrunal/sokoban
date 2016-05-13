package main.model;

import java.awt.*;

public class Home extends GameObject {
    public Home(int x, int y) {
        super(x, y);
        this.setWidth(2);
        this.setHeight(2);
    }

    @Override
    public void draw(Graphics graphics) {
        graphics.setColor(new Color(255, 30, 20));
        graphics.fillOval(this.getX(), this.getY(), this.getWidth(), this.getHeight());
    }
}
