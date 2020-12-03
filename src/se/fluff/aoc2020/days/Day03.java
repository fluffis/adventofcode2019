package se.fluff.aoc2020
.days;

import se.fluff.aoc.AocDay;

import java.awt.*;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

/**
 * Created by Fluff on 2020-12-03.
 */
public class Day03 extends AocDay {

    public Day03() {

    }

    @Override
    public String a(Scanner in) {
        HashMap<Point, Boolean> map = new HashMap<>();
        int x = -1;
        int y = -1;
        while(in.hasNext()) {
            x++;
            for(char c : in.nextLine().toCharArray()) {
                y++;
                map.put(new Point(x, y), c == '#');
            }
            y = -1;
        }

        y = map
                .keySet()
                .stream()
                .mapToInt(p -> (int) p.getY())
                .max()
                .getAsInt();

        return String.valueOf(checkSlope(x, y, new Point(1,3), map));
    }

    @Override
    public String b(Scanner in) {
        HashMap<Point, Boolean> map = new HashMap<>();
        int x = -1;
        int y = -1;
        while(in.hasNext()) {
            x++;
            for(char c : in.nextLine().toCharArray()) {
                y++;
                map.put(new Point(x, y), c == '#');
            }
            y = -1;
        }

        y = map
                .keySet()
                .stream()
                .mapToInt(p -> (int) p.getY())
                .max()
                .getAsInt();

        HashMap<Point, Integer> slopes = new HashMap<>();
        slopes.put(new Point(1, 1), 0);
        slopes.put(new Point(1, 3), 0);
        slopes.put(new Point(1, 5), 0);
        slopes.put(new Point(1, 7), 0);
        slopes.put(new Point(2, 1), 0);

        for(Map.Entry<Point, Integer> slope : slopes.entrySet())
            slopes.put(slope.getKey(), checkSlope(x, y, slope.getKey(), map));

        BigInteger total = slopes
                .values()
                .stream()
                .map(BigInteger::valueOf)
                .reduce(BigInteger.ONE, BigInteger::multiply);
        return String.valueOf(total);
    }

    private int checkSlope(int maxx, int maxy, Point movements, HashMap<Point, Boolean> map) {
        Point pos = new Point(0, 0);
        int trees = 0;
        while(pos.getX() < maxx) {
            pos.setLocation(pos.getX() + movements.getX(), pos.getY() + movements.getY());

            if(pos.getY() > maxy)
                pos.setLocation(pos.getX(), pos.getY() - maxy - 1);

            if(map.get(pos))
                trees++;
        }
        return trees;
    }
}

