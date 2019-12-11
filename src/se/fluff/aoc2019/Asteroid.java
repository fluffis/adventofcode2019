package se.fluff.aoc2019;

import java.awt.*;
import java.util.HashMap;

/**
 * Created by Fluff on 2019-12-10.
 */
public class Asteroid {
    private Point coords;
    private int cansee = 0;
    private HashMap<Float, Point> angletocoord = new HashMap<>();

    public Asteroid(Point coords, int visible) {
        this.coords = coords;
        this.cansee = visible;
    }

    public boolean isAngleClear(float angle) {
        return !angletocoord.containsKey(angle);
    }

    public boolean addAsteroid(Point p, float angle) {
        if(angletocoord.containsKey(angle))
            return false;

        angletocoord.put(angle, p);
        this.cansee++;

        return true;
    }

    public double distanceTo(Point p) {
        return this.coords.distanceSq(p);
    }

    public float angleTo(Point b) {
        float angle = (float) Math.toDegrees(Math.atan2(this.coords.y - b.y, this.coords.x - b.x)) - 90;

        if(angle < 0)
            angle += 360;

        return angle;
    }

    public Point getCoords() {
        return coords;
    }

    public void setCoords(Point coords) {
        this.coords = coords;
    }

    public int getCansee() {
        return cansee;
    }

    public void setCansee(int cansee) {
        this.cansee = cansee;
    }
}
