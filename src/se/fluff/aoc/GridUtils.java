package se.fluff.aoc;

import java.awt.*;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;

public class GridUtils {

    public GridUtils() {

    }
    public static void printGrid(HashMap<Point, ?> grid, char filler) {
        int miny = grid.entrySet().stream().min(Comparator.comparingInt(e -> e.getKey().y)).map(Map.Entry::getKey).get().y;
        int minx = grid.entrySet().stream().min(Comparator.comparingInt(e -> e.getKey().x)).map(Map.Entry::getKey).get().x;
        int maxy = grid.entrySet().stream().max(Comparator.comparingInt(e -> e.getKey().y)).map(Map.Entry::getKey).get().y;
        int maxx = grid.entrySet().stream().max(Comparator.comparingInt(e -> e.getKey().x)).map(Map.Entry::getKey).get().x;

        for (int y = miny; y <= maxy; y++) {
            for (int x = minx; x <= maxx; x++) {
                Point pp = new Point(x, y);
                if (grid.containsKey(pp)) {
                    Object o = grid.get(pp);
                    if(o instanceof String) {
                        System.out.print(((String) o).charAt(0));
                    }
                    else if(o instanceof Boolean) {
                        if(((boolean) o))
                            System.out.print("#");
                        else
                            System.out.print(".");
                    }
                    else if(o instanceof Character) {
                        System.out.print(((char) 0));
                    }
                    else if(o instanceof Integer)
                        System.out.print(String.valueOf(o).charAt(0));
                }
                else
                    System.out.print(filler);
            }
            System.out.println();
        }
    }

    public static Point[] getNeighbours(Point p, boolean includeDiagonal) {
        return getNeighbours(p, 1, includeDiagonal);
    }

    public static Point[] getNeighbours(Point p, int radius, boolean includeDiagonal) {

        ArrayList<Point> neighbours = new ArrayList<>();
        for(int y = p.y - radius; y <= p.y + radius; y++) {
            for (int x = p.x - radius; x <= p.x + radius; x++) {
                Point n = new Point(x, y);
                if(n.equals(p))
                    continue;

                if(includeDiagonal)
                    neighbours.add(n);
                else if(p.x == x || p.y == y)
                    neighbours.add(n);
            }
        }
        Point[] pa = new Point[neighbours.size()];
        return neighbours.toArray(pa);
    }

    public static boolean isWithinArea(Point a1, Point a2, Point p, boolean inclusive) {

        if(inclusive) {
            if(Math.min(a1.x, a2.x) <= p.x && p.x <= Math.max(a1.x, a2.x)) {
                if(Math.min(a1.y, a2.y) <= p.y && p.y <= Math.max(a1.y, a2.y))
                    return true;
            }
            return false;
        }
        else {
            if(Math.min(a1.x, a2.x) < p.x && p.x < Math.max(a1.x, a2.x)) {
                if(Math.min(a1.y, a2.y) < p.y && p.y < Math.max(a1.y, a2.y))
                    return true;
            }
            return false;
        }
    }
}
