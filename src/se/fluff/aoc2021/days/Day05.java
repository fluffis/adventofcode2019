package se.fluff.aoc2021.days;

import se.fluff.aoc.AocDay;
import se.fluff.aoc.GridUtils;

import java.awt.*;
import java.util.*;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by Fluff on 2021-12-05.
 */
public class Day05 extends AocDay {

    public Day05() {

    }

    @Override
    public String a(Scanner in, boolean isTest) throws Exception {

        HashMap<Point,Integer> map = new HashMap<>();
        while(in.hasNext()) {
            String[] s = in.nextLine().split(" -> ");
            List<Integer> pint1 = Arrays.stream(s[0].split(",")).mapToInt(Integer::parseInt).boxed().collect(Collectors.toList());
            Point p1 = new Point(pint1.get(0), pint1.get(1));
            List<Integer> pint2 = Arrays.stream(s[1].split(",")).mapToInt(Integer::parseInt).boxed().collect(Collectors.toList());
            Point p2 = new Point(pint2.get(0), pint2.get(1));

            if(!Objects.equals(p1.x, p2.x) && !Objects.equals(p1.y, p2.y))
                continue;

            int minx = Math.min(p1.x, p2.x);
            int maxx = Math.max(p1.x, p2.x);
            int miny = Math.min(p1.y, p2.y);
            int maxy = Math.max(p1.y, p2.y);

            for (int x = minx; x <= maxx; x++) {
                for (int y = miny; y <= maxy; y++) {
                    Point p = new Point(x, y);
                    map.put(p, map.getOrDefault(p, 0) + 1);
                }
            }

        }

        long atleasttwo = map.values().stream().filter(c -> c >= 2).count();
        return String.valueOf(atleasttwo);

    }

    @Override
    public String b(Scanner in, boolean isTest) {
        HashMap<Point,Integer> map = new HashMap<>();
        while(in.hasNext()) {
            String[] s = in.nextLine().split(" -> ");
            List<Integer> pint1 = Arrays.stream(s[0].split(",")).mapToInt(Integer::parseInt).boxed().collect(Collectors.toList());
            Point p1 = new Point(pint1.get(0), pint1.get(1));
            List<Integer> pint2 = Arrays.stream(s[1].split(",")).mapToInt(Integer::parseInt).boxed().collect(Collectors.toList());
            Point p2 = new Point(pint2.get(0), pint2.get(1));

            int minx = Math.min(p1.x, p2.x);
            int maxx = Math.max(p1.x, p2.x);
            int miny = Math.min(p1.y, p2.y);
            int maxy = Math.max(p1.y, p2.y);

            if(minx == maxx) {
                for (int y = miny; y <= maxy; y++) {
                    Point p = new Point(minx, y);
                    int c = map.getOrDefault(p, 0);
                    c++;
                    map.put(p, c);
                }
            }
            else if(maxy == miny) {
                for(int x = minx; x <= maxx; x++) {
                    Point p = new Point(x, miny);

                    int c = map.getOrDefault(p, 0);
                    c++;
                    map.put(p, c);
                }
            }
            else {
                int xstep = p1.x > p2.x ? -1 : 1;
                int ystep = p1.y > p2.y ? -1 : 1;

                int y = p1.y;
                for(int x = p1.x; x != p2.x + xstep; x += xstep) {
                    Point p = new Point(x, y);
                    map.put(p, map.getOrDefault(p, 0) + 1);

                    y += ystep;
                }
            }
        }

        long atleasttwo = map.values().stream().filter(c -> c >= 2).count();
        return String.valueOf(atleasttwo);
    }
}
