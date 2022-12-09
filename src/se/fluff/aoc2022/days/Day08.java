package se.fluff.aoc2022.days;

import se.fluff.aoc.AocDay;
import se.fluff.aoc.GridUtils;

import java.awt.*;
import java.util.*;

/**
 * Created by Fluff on 2022-12-08.
 */
public class Day08 extends AocDay {

    public Day08() {

    }

    @Override
    public String a(Scanner in, boolean isTest) throws Exception {
        HashMap<Point, Integer> trees = new HashMap<>();

        int maxx = Integer.MIN_VALUE;
        int maxy = Integer.MIN_VALUE;
        int y = 0, x = 0;
        while(in.hasNext()) {
            String l = in.nextLine();
            maxx = l.length() - 1;
            for(String c : l.split("")) {
                Point p = new Point(x, y);
                trees.put(p, Integer.parseInt(c));
                x++;

            }
            x = 0;
            y++;
        }
        maxy = y - 1;


        int visible = 0;
        for(Point p : trees.keySet()) {
            int obstructed = 0;
            int v = trees.get(p);

            for(int i = p.x - 1; i >= 0; i--) {
                Point eval = new Point(i, p.y);
                if(trees.get(eval) >= v) {
                    obstructed++;
                    break;
                }
            }

            for(int i = p.x + 1; i <= maxx; i++) {
                Point eval = new Point(i, p.y);
                if(trees.get(eval) >= v) {
                    obstructed++;
                    break;
                }
            }
            for(int i = p.y - 1; i >= 0; i--) {
                if(trees.get(new Point(p.x, i)) >= v) {
                    obstructed++;
                    break;
                }
            }
            for(int i = p.y + 1; i <= maxy; i++) {
                if(trees.get(new Point(p.x, i)) >= v) {
                    obstructed++;
                    break;
                }
            }

            if(obstructed < 4) {
                visible++;
            }
        }


        return String.valueOf(visible);
    }

    @Override
    public String b(Scanner in, boolean isTest) {
        HashMap<Point, Integer> trees = new HashMap<>();
        HashMap<Point, Integer> score = new HashMap<>();

        int maxx = Integer.MIN_VALUE;
        int maxy = Integer.MIN_VALUE;
        int y = 0, x = 0;
        while(in.hasNext()) {
            String l = in.nextLine();
            maxx = l.length() - 1;
            for(String c : l.split("")) {
                Point p = new Point(x, y);
                trees.put(p, Integer.parseInt(c));
                x++;

            }
            x = 0;
            y++;
        }
        maxy = y - 1;



        for(Point p : trees.keySet()) {
            int visible = 0;
            int icansee = 0;
            int v = trees.get(p);

            for(int i = p.x - 1; i >= 0; i--) {
                icansee++;
                Point eval = new Point(i, p.y);
                if(trees.get(eval) >= v) {
                    break;
                }
            }
            visible = icansee;
            icansee = 0;


            for(int i = p.x + 1; i <= maxx; i++) {
                icansee++;
                Point eval = new Point(i, p.y);
                if(trees.get(eval) >= v) {
                    break;
                }
            }
            visible *= icansee;
            icansee = 0;

            for(int i = p.y - 1; i >= 0; i--) {
                icansee++;
                if(trees.get(new Point(p.x, i)) >= v) {
                    break;
                }
            }

            visible *= icansee;
            icansee = 0;

            for(int i = p.y + 1; i <= maxy; i++) {
                icansee++;
                if(trees.get(new Point(p.x, i)) >= v) {
                    break;
                }
            }

            visible *= icansee;
            icansee = 0;

            score.put(p, visible);
        }


        return String.valueOf(score.values().stream().max(Comparator.comparingInt(v -> v)).get());
    }

}
