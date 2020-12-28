package se.fluff.aoc2019.days;

import se.fluff.aoc.AocDay;
import se.fluff.aoc2019.LongCodeMachine;

import java.awt.*;
import java.util.*;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.TimeUnit;

/**
 * Created by Fluff on 2019-12-17.
 */
public class Day17 extends AocDay {
    HashMap<Point,Character> space = new HashMap<>();
    HashMap<Integer, Long> intcode = new HashMap<>();

    public Day17() {

    }

    @Override
    public String a(Scanner in, boolean isTest) {

        String line = in.nextLine();
        int i = 0;
        for(String nr : line.split(",")) {
            intcode.put(i, Long.parseLong(nr));
            i++;
        }

        ArrayBlockingQueue<Long> inputs = new ArrayBlockingQueue<>(10000);
        ArrayBlockingQueue<Long> outputs = new ArrayBlockingQueue<>(10000);
        LongCodeMachine lcm = new LongCodeMachine(intcode, 1, inputs, outputs);

        lcm.run();
        int y = 0;
        int x = 0;
        while(!outputs.isEmpty()) {
            Point p = new Point(x, y);
            char c = '?';
            try {
                Long poll = outputs.poll(1, TimeUnit.DAYS);
                if (poll == 10L) {
                    x = 0;
                    y++;
                } else {
                    x++;
                    space.put(p, (char) poll.intValue());
                }
            }
            catch(Exception e) {
                e.printStackTrace();
            }
        }

        ArrayList<Point> intersections = new ArrayList<>();
        for(Map.Entry<Point, Character> s : space.entrySet()) {

            if(s.getValue().equals('#')) {
                int matches = 0;
                for(int c = 1; c <= 4; c++) {
                    if(space.getOrDefault(newpoint(s.getKey(), c), '?').equals('#')) {
                        matches++;
                    }
                }
                if(matches == 4)
                    intersections.add(s.getKey());
            }
        }

        printMap(space);

        return String.valueOf(intersections.stream().mapToInt(p -> p.x * p.y).sum());
    }

    @Override
    public String b(Scanner in, boolean isTest) {

        intcode.put(0, 2L);

        ArrayBlockingQueue<Long> inputs = new ArrayBlockingQueue<>(10000);
        ArrayBlockingQueue<Long> outputs = new ArrayBlockingQueue<>(1000000);
        LongCodeMachine lcm = new LongCodeMachine(intcode, 1, inputs, outputs);

        String main = "A,B,A,B,C,B,A,C,B,C";
        String pgra = "L,12,L,8,R,10,R,10";
        String pgrb = "L,6,L,4,L,12";
        String pgrc = "R,10,L,8,L,4,R,10";
        lcm.addInputString(main);
        lcm.addInputString(pgra);
        lcm.addInputString(pgrb);
        lcm.addInputString(pgrc);
        lcm.addInputString("n");

        lcm.run();
        int y = 0;
        int x = 0;
        while(!outputs.isEmpty()) {
            Point p = new Point(x, y);
            try {
                Long poll = outputs.poll(1, TimeUnit.DAYS);
                if (poll == 10L) {
                    x = 0;
                    y++;
                }
                else {
                    x++;

                    int i = poll.intValue();
                    if(i < 256)
                        space.put(p, (char) i);
                    else
                        return String.valueOf(i);
                }
            }
            catch(Exception e) {
                e.printStackTrace();
            }
        }

        printMap(space);
        return null;
    }

    private Point newpoint(Point curpos, int movement) {
        Point p = (Point) curpos.clone();
        switch(movement) {
            case 1:
                p.y++;
                break;
            case 2:
                p.y--;
                break;
            case 3:
                p.x--;
                break;
            case 4:
                p.x++;
                break;
            default:
                System.out.println("Unkonwn new point marker");
        }

        return p;
    }

    private void printMap(HashMap<Point, Character> grid) {
        int miny = grid.entrySet().stream().min(Comparator.comparingInt(e -> e.getKey().y)).map(Map.Entry::getKey).get().y;
        int minx = grid.entrySet().stream().min(Comparator.comparingInt(e -> e.getKey().x)).map(Map.Entry::getKey).get().x;
        int maxy = grid.entrySet().stream().max(Comparator.comparingInt(e -> e.getKey().y)).map(Map.Entry::getKey).get().y;
        int maxx = grid.entrySet().stream().max(Comparator.comparingInt(e -> e.getKey().x)).map(Map.Entry::getKey).get().x;

        for (int y = miny; y <= maxy; y++) {
            for (int x = minx; x <= maxx; x++) {
                Point pp = new Point(x, y);
                if (grid.containsKey(pp))
                    System.out.print(grid.get(pp));
                else
                    System.out.print(" ");
            }
            System.out.println();
        }
    }
}
