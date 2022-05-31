package se.fluff.aoc2021.days;

import se.fluff.aoc.AocDay;
import se.fluff.aoc.GridUtils;

import java.awt.*;
import java.util.*;

/**
 * Created by Fluff on 2022-05-28.
 */
public class Day15 extends AocDay {

    public Day15() {

    }
    HashMap<Point,Integer> map = new HashMap<>();
    HashMap<Point,Integer> wsum = new HashMap<>();
    HashSet<Point> visited = new HashSet<>();

    @Override
    public String a(Scanner in, boolean isTest) throws Exception {
        map = new HashMap<>();
        wsum = new HashMap<>();
        visited = new HashSet<>();
        int y = 0;
        int x = 0;
        while(in.hasNext()) {
            int[] weights = Arrays.stream(in.nextLine().split("")).mapToInt(Integer::parseInt).toArray();
            for(x = 0; x < weights.length; x++) {
                map.put(new Point(x, y), weights[x]);
                wsum.put(new Point(x, y), Integer.MAX_VALUE);
            }
            y++;
        }

        Point goal = new Point(x - 1, y - 1);
        wsum.put(new Point(0, 0), 0);
        PriorityQueue<CavePart> pq = new PriorityQueue<>(x * y, Comparator.comparingInt(CavePart::getRisklevel));
        pq.add(new CavePart(new Point(0, 0), 0));
        while(!pq.isEmpty()) {
            CavePart cp = pq.poll();

            if(cp.getP() == goal)
                return String.valueOf(wsum.get(goal));

            Point[] neighbours = GridUtils.getNeighbours(cp.getP(), false);
            for(Point n : neighbours) {
                if(n.x < 0 || n.y < 0 || n.x > goal.x || n.y > goal.y)
                    continue;

                if(visited.contains(n))
                    continue;

                int w = wsum.get(cp.getP()) + map.get(n);
                if(wsum.get(n) > w)
                    wsum.put(n,w);
                pq.add(new CavePart(n, w));
                visited.add(n);
            }
        }
        return String.valueOf(wsum.get(goal));

    }

    @Override
    public String b(Scanner in, boolean isTest) {
        map = new HashMap<>();
        wsum = new HashMap<>();
        visited = new HashSet<>();
        int y = 0;
        int x = 0;
        while(in.hasNext()) {
            int[] weights = Arrays.stream(in.nextLine().split("")).mapToInt(Integer::parseInt).toArray();
            for(x = 0; x < weights.length; x++) {
                map.put(new Point(x, y), weights[x]);
                wsum.put(new Point(x, y), Integer.MAX_VALUE);
            }
            y++;
        }

        HashMap<Point,Integer> expandedmap = new HashMap<>();

        for(int rx = 0; rx < 5; rx++) {
            for(int ry = 0; ry < 5; ry++) {
                for(Point p : map.keySet()) {
                    Point pex = new Point(p.x + (x * rx), p.y + (y * ry));

                    int w = map.get(p) + rx + ry;
                    if(w > 9)
                        w -= 9;
                    expandedmap.put(pex, w);
                    wsum.put(pex, Integer.MAX_VALUE);
                }
            }
        }

        Point goal = new Point((x * 5) - 1, (y * 5) - 1);
        wsum.put(new Point(0, 0), 0);
        PriorityQueue<CavePart> pq = new PriorityQueue<>(x * y * 5, Comparator.comparingInt(CavePart::getRisklevel));
        pq.add(new CavePart(new Point(0, 0), 0));
        while(!pq.isEmpty()) {
            CavePart cp = pq.poll();

            if(cp.getP() == goal)
                return String.valueOf(wsum.get(goal));

            Point[] neighbours = GridUtils.getNeighbours(cp.getP(), false);
            for(Point n : neighbours) {
                if(n.x < 0 || n.y < 0 || n.x > goal.x || n.y > goal.y)
                    continue;

                if(visited.contains(n))
                    continue;
                int w = wsum.get(cp.getP()) + expandedmap.get(n);
                if(wsum.get(n) > w)
                    wsum.put(n,w);
                pq.add(new CavePart(n, w));
                visited.add(n);
            }
        }
        return String.valueOf(wsum.get(goal));
    }

}

class CavePart {
    private Point p;
    private int risklevel;

    public CavePart(Point p, int risklevel) {
        this.p = p;
        this.risklevel = risklevel;
    }

    public Point getP() {
        return p;
    }

    public int getRisklevel() {
        return risklevel;
    }
}