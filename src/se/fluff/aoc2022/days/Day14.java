package se.fluff.aoc2022.days;

import se.fluff.aoc.AocDay;
import se.fluff.aoc.GridUtils;

import java.awt.*;
import java.util.*;

/**
 * Created by Fluff on 2022-12-15.
 */
public class Day14 extends AocDay {

    public Day14() {
    }

    @Override
    public String a(Scanner in, boolean isTest) throws Exception {

        HashMap<Point, Character> cave = new HashMap<>();
        while(in.hasNext()) {
            ArrayList<Point> points = new ArrayList<>();
            String[] pdata = in.nextLine().split(" -> ");
            for(String pnext : pdata) {
                String[] parts = pnext.split(",");
                points.add(new Point(Integer.parseInt(parts[0]), Integer.parseInt(parts[1])));
            }
            while(true) {
                Point cur = points.remove(0);
                if(points.size() == 0)
                    break;
                Point next = points.get(0);
                while(cur.x != next.x || cur.y != next.y) {
                    cave.put(new Point(cur), '#');

                    if(cur.x == next.x) {
                        if(cur.y < next.y)
                            cur.y++;
                        else
                            cur.y--;
                    }
                    else if(cur.y == next.y) {
                        if(cur.x < next.x)
                            cur.x++;
                        else
                            cur.x--;
                    }
                }
                cave.put(new Point(cur), '#');
            }
        }

        int rockfloor = cave.keySet().stream().mapToInt(p -> p.y).max().getAsInt();
        Point sandentrance = new Point(500, 0);
        boolean keepfalling = true;
        while(keepfalling) {
            Point sand = new Point(sandentrance);
            while(keepfalling) {
                Point down = new Point(sand.x, sand.y + 1);
                Point left = new Point(sand.x - 1, sand.y + 1);
                Point right = new Point(sand.x + 1, sand.y + 1);

                if(sand.y >= rockfloor)
                    keepfalling = false;


                if(!cave.containsKey(down)) {
                    sand.y++;
                }
                else if(cave.containsKey(down) && !cave.containsKey(left)) {
                    sand.x--;
                }
                else if(cave.containsKey(down) && cave.containsKey(left) && !cave.containsKey(right)) {
                    sand.x++;
                }
                else {
                    cave.put(new Point(sand), 'o');
                    if(sandentrance.x == sand.x && sandentrance.y == sand.y)
                        keepfalling = false;
                    break;

                }

            }
        }

        return String.valueOf(cave.values().stream().filter(p -> p.equals('o')).count());
    }

    @Override
    public String b(Scanner in, boolean isTest) {
        HashMap<Point, Character> cave = new HashMap<>();
        while(in.hasNext()) {
            ArrayList<Point> points = new ArrayList<>();
            String[] pdata = in.nextLine().split(" -> ");
            for(String pnext : pdata) {
                String[] parts = pnext.split(",");
                points.add(new Point(Integer.parseInt(parts[0]), Integer.parseInt(parts[1])));
            }
            while(true) {
                Point cur = points.remove(0);
                if(points.size() == 0)
                    break;
                Point next = points.get(0);
                while(cur.x != next.x || cur.y != next.y) {
                    cave.put(new Point(cur), '#');

                    if(cur.x == next.x) {
                        if(cur.y < next.y)
                            cur.y++;
                        else
                            cur.y--;
                    }
                    else if(cur.y == next.y) {
                        if(cur.x < next.x)
                            cur.x++;
                        else
                            cur.x--;
                    }
                }
                cave.put(new Point(cur), '#');
            }
        }

        int rockfloor = cave.keySet().stream().mapToInt(p -> p.y).max().getAsInt() + 2;
        int minx = cave.keySet().stream().mapToInt(p -> p.x).min().getAsInt() - 100;
        int maxx = cave.keySet().stream().mapToInt(p -> p.x).max().getAsInt() + 150;
        for(int ix = minx; ix < maxx; ix++) {
            cave.put(new Point(ix, rockfloor), '#');
        }
        Point sandentrance = new Point(500, 0);
        boolean keepfalling = true;
        while(keepfalling) {
            Point sand = new Point(sandentrance);
            while(keepfalling) {
                Point down = new Point(sand.x, sand.y + 1);
                Point left = new Point(sand.x - 1, sand.y + 1);
                Point right = new Point(sand.x + 1, sand.y + 1);

                if(sand.y >= rockfloor)
                    keepfalling = false;


                if(!cave.containsKey(down)) {
                    sand.y++;
                }
                else if(cave.containsKey(down) && !cave.containsKey(left)) {
                    sand.x--;
                }
                else if(cave.containsKey(down) && cave.containsKey(left) && !cave.containsKey(right)) {
                    sand.x++;
                }
                else {
                    cave.put(new Point(sand), 'o');
                    if(sandentrance.x == sand.x && sandentrance.y == sand.y)
                        keepfalling = false;
                    break;

                }

            }
        }
        return String.valueOf(cave.values().stream().filter(p -> p.equals('o')).count());
    }
}
