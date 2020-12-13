package se.fluff.aoc2020.days;

import se.fluff.aoc.AocDay;

import java.awt.*;
import java.util.Scanner;

/**
 * Created by Fluff on 2020-12-12.
 */
public class Day12 extends AocDay {

    public Day12() {

    }

    @Override
    public String a(Scanner in) {

        Point p = new Point(0, 0);
        int direction = 90;

        while(in.hasNext()) {
            String s = in.nextLine();
            char movement = s.charAt(0);
            int amount = Integer.parseInt(s.substring(1));

            switch(movement) {
                case 'L':
                    direction -= amount;
                    if(direction < 0)
                        direction += 360;
                    break;
                case 'R':
                    direction += amount;
                    if(direction >= 360)
                        direction -= 360;
                    break;
                case 'F':
                    p = move(direction, amount, p);
                    break;
                case 'N':
                    p = move(0, amount, p);
                    break;
                case 'S':
                    p = move(180, amount, p);
                    break;
                case 'W':
                    p = move(270, amount, p);
                    break;
                case 'E':
                    p = move(90, amount, p);
                    break;
            }
        }

        return String.valueOf(Math.abs(p.x) + Math.abs(p.y));
    }

    public Point move(int direction, int amount, Point p) {

        switch(direction) {
            case 0:
                p.x -= amount;
                break;
            case 180:
                p.x += amount;
                break;
            case 90:
                p.y += amount;
                break;
            case 270:
                p.y -= amount;
                break;
        }

        return p;
    }

    public Point movemultiply(int amount, Point ship, Point waypoint) {

        ship.x += waypoint.x * amount;
        ship.y += waypoint.y * amount;
        return ship;
    }


    public Point transpose(Point waypoint, int angle) {


        Point org = new Point(waypoint.x, waypoint.y);
        switch(angle) {
            case -90:
            case 270:
                waypoint.x = -org.y;
                waypoint.y = org.x;
                break;
            case 180:
            case -180:
                waypoint.x = -org.x;
                waypoint.y = -org.y;
                break;
            case 90:
            case -270:
                waypoint.x = org.y;
                waypoint.y = -org.x;
                break;

        }


        return waypoint;
    }

    @Override
    public String b(Scanner in) {
        Point ship = new Point(0, 0);
        Point waypoint = new Point(-1, 10);
        int direction = 90;

        while(in.hasNext()) {
            String s = in.nextLine();
            char movement = s.charAt(0);
            int amount = Integer.parseInt(s.substring(1));

            switch (movement) {
                case 'L':
                    direction -= amount;
                    if(direction < 0)
                        direction += 360;

                    waypoint = transpose(waypoint, -amount);
                    break;
                case 'R':
                    direction += amount;
                    if(direction > 360)
                        direction -= 360;
                    waypoint = transpose(waypoint, amount);
                    break;
                case 'F':
                    ship = movemultiply(amount, ship, waypoint);
                    break;
                case 'N':
                    waypoint = move(0, amount, waypoint);
                    break;
                case 'S':
                    waypoint = move(180, amount, waypoint);
                    break;
                case 'W':
                    waypoint = move(270, amount, waypoint);
                    break;
                case 'E':
                    waypoint = move(90, amount, waypoint);
                    break;
            }
        }
        return String.valueOf(Math.abs(ship.x) + Math.abs(ship.y));
    }
}
