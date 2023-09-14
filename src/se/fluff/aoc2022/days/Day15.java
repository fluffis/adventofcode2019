package se.fluff.aoc2022.days;

import se.fluff.aoc.AocDay;
import se.fluff.aoc.GridUtils;

import java.awt.*;
import java.util.*;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

/**
 * Created by Fluff on 2022-12-15.
 */
public class Day15 extends AocDay {

    public static int ytest = 10;
    public static int yprod = 2000000;

    public static Pair areatest = new Pair(0, 20);
    public static Pair areaprod = new Pair(0, 4000000);

    public Day15() {

    }

    @Override
    public String a(Scanner in, boolean isTest) throws Exception {
        int yactive = isTest ? ytest : yprod;
        ArrayList<Pair> pairs = new ArrayList<>();
        Pattern p = Pattern.compile("Sensor at x=(-?\\d+), y=(-?\\d+): closest beacon is at x=(-?\\d+), y=(-?\\d+)");
        while(in.hasNext()) {
            Matcher m = p.matcher(in.nextLine());
            if(m.matches()) {
                Point sensor = new Point(Integer.parseInt(m.group(1)), Integer.parseInt(m.group(2)));
                Point beacon = new Point(Integer.parseInt(m.group(3)), Integer.parseInt(m.group(4)));
                int mdist = Math.abs(sensor.x - beacon.x) + Math.abs(sensor.y - beacon.y);
                int xdist = mdist - Math.abs(sensor.y - yactive);
                if(xdist > 0) {
                    pairs.add(new Pair(sensor.x - xdist, sensor.x + xdist));
                }
            }
            else {
                throw new Exception("No match!");
            }
        }

        pairs.sort(Comparator.comparingLong(pair -> pair.xmin));

        Stack<Pair> stack = new Stack<>();
        stack.add(pairs.get(0));
        for (Pair pair : pairs) {
            Pair top = stack.peek();
            if (top.xmax < pair.xmin) {
                stack.push(pair);
            } else if (top.xmax < pair.xmax) {
                top.xmax = pair.xmax;
                stack.pop();
                stack.push(top);
            }
        }
        return String.valueOf(stack.stream().map(Pair::getInterval).reduce(Long::sum).get());
    }

    @Override
    public String b(Scanner in, boolean isTest) throws Exception {
        Pair area = isTest ? areatest : areaprod;
        HashMap<Point, Point> sensors = new HashMap<>();
        Pattern p = Pattern.compile("Sensor at x=(-?\\d+), y=(-?\\d+): closest beacon is at x=(-?\\d+), y=(-?\\d+)");
        while(in.hasNext()) {
            Matcher m = p.matcher(in.nextLine());
            if(m.matches()) {
                Point sensor = new Point(Integer.parseInt(m.group(1)), Integer.parseInt(m.group(2)));
                Point beacon = new Point(Integer.parseInt(m.group(3)), Integer.parseInt(m.group(4)));
                sensors.put(sensor, beacon);
            }
            else {
                throw new Exception("No match!");
            }
        }

        for(long row = area.xmin; row <= area.xmax; row++) {
            ArrayList<Pair> pairs = scanrow(row, area, sensors);
            if(pairs.size() > 0) {
                long y = pairs.stream().mapToLong(point -> point.xmax).min().getAsLong() + 1;
                return String.valueOf(y * 4000000 + row);
            }
        }

        return null;
    }

    public ArrayList<Pair> scanrow(long row, Pair area, HashMap<Point, Point> sensors) {

        long rowmin;
        long rowmax;
        HashSet<Pair> pairs = new HashSet<>();
        for(Map.Entry<Point, Point> sb : sensors.entrySet()) {
            if(area.isBetween(sb.getKey().x) && sb.getKey().y == row)
                pairs.add(new Pair(sb.getKey().x, sb.getKey().x));
            if(area.isBetween(sb.getValue().x) && sb.getValue().y == row)
                pairs.add(new Pair(sb.getValue().x, sb.getValue().x));

            long mdist = Math.abs(sb.getKey().x - sb.getValue().x) + Math.abs(sb.getKey().y - sb.getValue().y);
            long ydist = Math.abs(sb.getKey().y - row);
            if(ydist > mdist)
                continue;

            rowmin = Math.max(Math.min(sb.getKey().x - (mdist - ydist), area.xmax), area.xmin);
            rowmax = Math.min(Math.max(sb.getKey().x + (mdist - ydist), area.xmin), area.xmax);
            pairs.add(new Pair(rowmin, rowmax));
        }

        ArrayList<Pair> pairlist = new ArrayList<>(pairs);
        Stack<Pair> stack = new Stack<>();
        for(int round = 0; round < 3; round++) {
            stack = new Stack<>();
            pairlist.sort(Comparator.comparingLong(pair -> pair.xmin));
            stack.add(pairlist.get(0));
            for (Pair pair : pairlist) {
                Pair top = stack.peek();
                if (top.xmax + 1 < pair.xmin) {
                    stack.push(pair);
                }
                else if (top.xmax + 1 <= pair.xmax) {
                    top.xmax = pair.xmax;
                    stack.pop();
                    stack.push(top);
                }
            }
            pairlist = new ArrayList<>(stack);
        }


        return stack.size() > 1 ? new ArrayList<>(stack) : new ArrayList<>();
    }
}

class Pair {

    public long xmin;
    public long xmax;

    public Pair(long xmin, long xmax) {
        this.xmin = xmin;
        this.xmax = xmax;
    }

    public long getInterval() {
        return xmax - xmin;
    }

    public boolean isBetween(long test) {
        return test >= xmin && test <= xmax;
    }

    @Override
    public String toString() {
        return "Pair{" +
                "xmin=" + xmin +
                ", xmax=" + xmax +
                '}';
    }
}