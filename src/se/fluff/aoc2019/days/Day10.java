package se.fluff.aoc2019.days;

import se.fluff.aoc2019.AocDay;
import se.fluff.aoc2019.Asteroid;

import java.awt.*;
import java.util.*;

/**
 * Created by Fluff on 2019-12-10.
 */
public class Day10 extends AocDay {

    public Day10() {

    }

    @Override
    public String a(Scanner in) {

        HashMap<Point, Asteroid> map = new HashMap<>();
        int y = 0;
        int width = 0;
        while(in.hasNext()) {
            String[] row = in.nextLine().split("");
            for(int x = 0; x < row.length; x++) {
                if("#".equals(row[x]))
                    map.put(new Point(x, y), new Asteroid(new Point(x, y), 0));
            }
            width = row.length;
            y++;
        }

        for(Asteroid a : map.values()) {
            HashMap<Point, Float> visibleAsteroids = getVisibleAsteroids(a, map, width);
            for(Point p : visibleAsteroids.keySet())
                a.addAsteroid(p, visibleAsteroids.get(p));
        }

        Map.Entry<Point, Asteroid> max = map.entrySet().stream().max(Comparator.comparingInt(a -> a.getValue().getCansee())).get();

        System.out.println(max.getValue().getCansee());
        Point p = max.getKey();
        return p.x  + "," + p.y;
    }

    @Override
    public String b(Scanner in) {

        HashMap<Point, Asteroid> map = new HashMap<>();
        int y = 0;
        int width = 0;
        while(in.hasNext()) {
            String[] row = in.nextLine().split("");
            for(int x = 0; x < row.length; x++) {
                if("#".equals(row[x]))
                    map.put(new Point(x, y), new Asteroid(new Point(x, y), 0));
            }
            width = row.length;
            y++;
        }

        Asteroid a = map.get(new Point(11, 11));
        int hits = 0;
        while(hits <= 200) {
            HashMap<Point, Float> visibleAsteroids = getVisibleAsteroids(a, map, width);
            while(visibleAsteroids.size() > 0) {
                Optional<Point> op = visibleAsteroids.entrySet().stream()
                        .sorted(Comparator.comparingDouble(Map.Entry::getValue))
                        .findFirst()
                        .map(Map.Entry::getKey);

                if(op.isPresent()) {
                    Point p = op.get();
                    map.remove(p);
                    visibleAsteroids.remove(p);
                    hits++;

                    if (hits == 200)
                        return String.valueOf(100 * p.x + p.y);
                }
            }
        }

        return null;
    }

    private HashMap<Point, Float> getVisibleAsteroids(Asteroid a, HashMap<Point, Asteroid> map, int width) {
        HashMap<Point, Float> asteroids = new HashMap<>();
        ArrayList<Float> seenAngles = new ArrayList<>();
        for(int side = 1; side <= width; side += 2) {
            int startx = a.getCoords().x - (int) (Math.floor(side/2) + side);
            int starty = a.getCoords().y - (int) (Math.floor(side/2) + side);
            int endx = a.getCoords().x + (int) (Math.floor(side/2) + side);
            int endy = a.getCoords().y + (int) (Math.floor(side/2) + side);

            for (int x = startx; x <= endx; x++) {
                for (int y = starty; y <= endy; y++) {
                    Point p = new Point(x, y);
                    if (x < 0 || y < 0 || x > width || y > width || a.getCoords().equals(p))
                        continue;

                    if (map.containsKey(p)) {
                        float angle = a.angleTo(p);
                        if (!seenAngles.contains(angle)) {
                            seenAngles.add(angle);
                            asteroids.put(p, angle);
                        }
                    }

                }
            }
        }
        return asteroids;
    }
}
