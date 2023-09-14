package se.fluff.aoc2022.days;

import se.fluff.aoc.AocDay;
import se.fluff.aoc.GridUtils;

import java.awt.*;
import java.util.*;

/**
 * Created by Fluff on 2022-12-12.
 */
public class Day12 extends AocDay {
    HashMap<Point, Integer> map = new HashMap<>();

    public Day12() {

    }

    @Override
    public String a(Scanner in, boolean isTest) throws Exception {

        int y = 0;
        int x = 0;
        Point start = null;
        Point goal = null;
        while(in.hasNext()) {
            for(char c : in.nextLine().toCharArray()) {
                map.put(new Point(x, y), (int)c);
                if(c == 'S') {
                    start = new Point(x, y);
                    map.put(new Point(x, y), (int) 'a');
                }
                if(c == 'E') {
                    goal = new Point(x, y);
                    map.put(new Point(x, y), (int) 'z' + 1);
                }
                x++;
            }
            x = 0;
            y++;
        }

        return String.valueOf(climb(start, goal));
    }

    @Override
    public String b(Scanner in, boolean isTest) {
        int y = 0;
        int x = 0;
        ArrayList<Point> starts = new ArrayList<>();
        Point goal = null;

        while(in.hasNext()) {
            for(char c : in.nextLine().toCharArray()) {
                map.put(new Point(x, y), (int)c);
                if(c == 'S') {
                    starts.add(new Point(x, y));
                    map.put(new Point(x, y), (int) 'a');
                }
                if(c == 'E') {
                    goal = new Point(x, y);
                    map.put(new Point(x, y), (int) 'z' + 1);
                }
                if(c == 'a') {
                    starts.add(new Point(x, y));
                }
                x++;
            }
            x = 0;
            y++;
        }

        Point finalGoal = goal;
        return String.valueOf(starts.stream().map(s -> climb(s, finalGoal)).min(Comparator.comparingInt(res -> res)).get());
    }
    
    public int climb(Point start, Point goal) {
        HashMap<Point,Integer> gCost = new HashMap<>();
        HashMap<Point,Point> parent = new HashMap<>();
        LinkedList<Point> queue = new LinkedList<>();
        gCost.put(start,0);
        queue.add(start);
        while(queue.size() > 0) {
            Point cur = queue.poll();
            if(cur.equals(goal)) {
                ArrayList<Point> path = new ArrayList<>();
                while(parent.containsKey(cur)) {
                    path.add(cur);
                    cur = parent.get(cur);
                }
                return path.size();
            }
            for(Point n : GridUtils.getNeighbours(cur, false)) {

                if(!map.containsKey(n))
                    continue;
                int elevation = map.get(cur);
                int nelevation = map.get(n);
                if(nelevation - 1 > elevation)
                    continue;
                int tentativeG = gCost.get(cur) + 1;
                if(tentativeG < gCost.getOrDefault(n,Integer.MAX_VALUE)) {
                    gCost.put(n,tentativeG);
                    parent.put(n,cur);
                    queue.add(n);
                }
            }
        }
        return Integer.MAX_VALUE;
    }
}
