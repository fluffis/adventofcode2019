package se.fluff.aoc2021.days;

import se.fluff.aoc.AocDay;
import se.fluff.aoc.GridUtils;

import java.awt.*;
import java.util.*;

/**
 * Created by Fluff on 2021-12-11.
 */
public class Day11 extends AocDay {

    public Day11() {

    }

    @Override
    public String a(Scanner in, boolean isTest) throws Exception {
        HashMap<Point, Integer> map = new HashMap<>();
        int y = 0;
        while(in.hasNext()) {
            int[] row = Arrays.stream(in.nextLine().split("")).mapToInt(Integer::parseInt).toArray();
            for(int x = 0; x < row.length; x++) {
                map.put(new Point(x,y), row[x]);
            }
            y++;
        }

        int flashes = 0;
        for(int step = 1; step <= 100; step++) {
            LinkedList<Point> flashqueue = new LinkedList<>();
            for(Map.Entry<Point, Integer> e : map.entrySet()) {
                map.put(e.getKey(), e.getValue() + 1);
                if(e.getValue() > 9)
                    flashqueue.add(e.getKey());
            }

            while(flashqueue.size() > 0) {
                Point p = flashqueue.removeFirst();
                for(Point pn : GridUtils.getNeighbours(p, true)) {
                    if(!map.containsKey(pn))
                        continue;

                    int pv = map.get(pn) + 1;

                    if(pv <= 10)
                        map.put(pn, pv);

                    if(pv == 10 && !flashqueue.contains(pn))
                        flashqueue.add(pn);

                }
            }

            for(Map.Entry<Point, Integer> e : map.entrySet()) {
                if(e.getValue() > 9) {
                    flashes++;
                    map.put(e.getKey(), 0);
                }
            }
        }
        return String.valueOf(flashes);
    }

    @Override
    public String b(Scanner in, boolean isTest) {
        HashMap<Point, Integer> map = new HashMap<>();
        int y = 0;
        while(in.hasNext()) {
            int[] row = Arrays.stream(in.nextLine().split("")).mapToInt(Integer::parseInt).toArray();
            for(int x = 0; x < row.length; x++) {
                map.put(new Point(x,y), row[x]);
            }
            y++;
        }

        int step = 0;
        while(true) {
            int flashes = 0;
            step++;
            LinkedList<Point> flashqueue = new LinkedList<>();
            for(Map.Entry<Point, Integer> e : map.entrySet()) {
                map.put(e.getKey(), e.getValue() + 1);
                if(e.getValue() > 9)
                    flashqueue.add(e.getKey());
            }

            while(flashqueue.size() > 0) {
                Point p = flashqueue.removeFirst();
                for(Point pn : GridUtils.getNeighbours(p, true)) {
                    if(!map.containsKey(pn))
                        continue;

                    int pv = map.get(pn) + 1;

                    if(pv <= 10)
                        map.put(pn, pv);

                    if(pv == 10 && !flashqueue.contains(pn))
                        flashqueue.add(pn);

                }
            }
            for(Map.Entry<Point, Integer> e : map.entrySet()) {
                if(e.getValue() > 9) {
                    flashes++;
                    map.put(e.getKey(), 0);
                }
            }

            if(flashes == 100)
                return String.valueOf(step);
        }

    }

}
