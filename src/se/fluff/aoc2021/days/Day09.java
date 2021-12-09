package se.fluff.aoc2021.days;

import se.fluff.aoc.AocDay;
import se.fluff.aoc.GridUtils;

import java.awt.*;
import java.util.*;
import java.util.List;

/**
 * Created by Fluff on 2021-12-09.
 */
public class Day09 extends AocDay {

    public Day09() {

    }

    @Override
    public String a(Scanner in, boolean isTest) throws Exception {
        HashMap<Point, Integer> heatmap = new HashMap<>();
        ArrayList<Point> lowpoints = new ArrayList<>();
        int y = 0;
        while(in.hasNext()) {
            int[] row = Arrays.stream(in.nextLine().split("")).mapToInt(Integer::parseInt).toArray();
            for(int x = 0; x < row.length; x++)
                heatmap.put(new Point(x, y), row[x]);

            y++;
        }

        for(Point p : heatmap.keySet()) {
            int nc = 0;
            int higher = 0;

            for (Point np : getNeighbours(p)) {
                if (heatmap.containsKey(np)) {
                    nc++;
                    if (heatmap.get(np) > heatmap.get(p))
                        higher++;

                }
            }

            if (nc == higher)
                lowpoints.add(p);
        }


        int sum = 0;
        for(Point p : lowpoints)
            sum += heatmap.get(p) + 1;

        return String.valueOf(sum);
    }

    @Override
    public String b(Scanner in, boolean isTest) {
        HashMap<Point, Integer> heatmap = new HashMap<>();
        ArrayList<Point> lowpoints = new ArrayList<>();
        int y = 0;
        while(in.hasNext()) {
            int[] row = Arrays.stream(in.nextLine().split("")).mapToInt(Integer::parseInt).toArray();
            for(int x = 0; x < row.length; x++)
                heatmap.put(new Point(x, y), row[x]);

            y++;
        }

        for(Point p : heatmap.keySet()) {
            int nc = 0;
            int higher = 0;

            for(Point np : getNeighbours(p)) {
                if (heatmap.containsKey(np)) {
                    nc++;
                    if (heatmap.get(np) > heatmap.get(p))
                        higher++;

                }
            }

            if(nc == higher)
                lowpoints.add(p);
        }


        ArrayList<Integer> basinsizes = new ArrayList<>();

        for(Point p : lowpoints) {
            LinkedList<Point> queue = new LinkedList<>();
            ArrayList<Point> basinmembers = new ArrayList<>();
            ArrayList<Point> tested = new ArrayList<>();
            queue.add(p);

            while(queue.size() > 0) {
                
                Point test = queue.removeFirst();
                if(!heatmap.containsKey(test)) {
                    tested.add(test);
                    continue;
                }

                if(heatmap.get(test) != 9 && !basinmembers.contains(test)) {
                    basinmembers.add(test);

                    for(Point xp : getNeighbours(test))
                        if (!tested.contains(xp) && !queue.contains(xp))
                            queue.add(xp);


                }
            }
            basinsizes.add(basinmembers.size());
        }

        Collections.sort(basinsizes);
        return String.valueOf(basinsizes.subList(basinsizes.size() -3, basinsizes.size()).stream().reduce((c, v) -> c * v).get());
    }

    private Point[] getNeighbours(Point p) {
        Point[] n = new Point[4];
        n[0] = new Point(p.x - 1, p.y);
        n[1] = new Point(p.x + 1, p.y);
        n[2] = new Point(p.x, p.y - 1);
        n[3] = new Point(p.x, p.y + 1);
        return n;

    }
    
}
