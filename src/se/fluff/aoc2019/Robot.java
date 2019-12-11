package se.fluff.aoc2019;

import java.awt.*;
import java.util.HashMap;

/**
 * Created by Fluff on 2019-12-11.
 */
public class Robot {

    private Point pos;
    private int direction;
    private HashMap<Point, Integer> painted = new HashMap<>();

    public Robot() {
        this.pos = new Point(0, 0);
        this.direction = 0;
    }

    public int turnLeft() {
        return turn(-90);
    }

    public int turnRight() {
        return turn(90);
    }

    private int turn(int degrees) {
        this.direction += degrees;
        if(this.direction < 0)
            this.direction += 360;
        if(this.direction >= 360)
            this.direction -= 360;

        return direction;
    }

    public HashMap<Point, Integer> getPainted() {
        return painted;
    }

    public void paint(int color) {
        Point p = (Point) pos.clone();
        painted.put(p, color);
    }

    public int getColorOfCurrentPos() {
        return painted.getOrDefault(pos, 0);
    }

    public void move(int step) {
        switch(direction) {
            case 0:
                pos.y += step;
                break;
            case 90:
                pos.x += step;
                break;
            case 180:
                pos.y -= step;
                break;
            case 270:
                pos.x -= step;
                break;
            default:
                System.out.println("We're set at an unknown direction: " + direction);
        }
    }

}
