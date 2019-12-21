package se.fluff.aoc2019.days;

import se.fluff.aoc2019.AocDay;
import se.fluff.aoc2019.LongCodeMachine;

import java.awt.*;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
import java.util.concurrent.ArrayBlockingQueue;

/**
 * Created by Fluff on 2019-12-13.
 */
public class Day13 extends AocDay {

    public Day13() {

    }

    @Override
    public String a(Scanner in) {

        HashMap<Integer, Long> intcode = new HashMap<>();
        HashMap<Point, Integer> screen = new HashMap<Point, Integer>();
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

        int c = 0;
        Point p = new Point();
        while(!lcm.isHalted() || outputs.size() > 0) {
            c++;
            Long output = null;
            try {
                output = outputs.take();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            if (c == 1) {
                p = new Point();
                p.x = Math.toIntExact(output);
            } else if (c == 2) {
                p.y = Math.toIntExact(output);
            } else if (c == 3) {
                c = 0;
                screen.put(p, Math.toIntExact(output));
            }
        }

        return String.valueOf(screen.values().stream().filter(x -> x == 2).count());
    }

    @Override
    public String b(Scanner in) {
        HashMap<Integer, Long> intcode = new HashMap<>();
        HashMap<Point, Integer> screen = new HashMap<Point, Integer>();
        String line = in.nextLine();
        int i = 0;
        for(String nr : line.split(",")) {
            intcode.put(i, Long.parseLong(nr));
            i++;
        }

        intcode.put(0, 2L);

        ArrayBlockingQueue<Long> inputs = new ArrayBlockingQueue<>(10000);
        ArrayBlockingQueue<Long> outputs = new ArrayBlockingQueue<>(10000);
        LongCodeMachine lcm = new LongCodeMachine(intcode, 1, inputs, outputs);

        Long score = null;

        boolean keeprunning = true;
        while(keeprunning) {

            lcm.run();

            int c = 0;
            Point p = new Point();

            while(outputs.size() > 0) {
                c++;
                Long output = null;
                try {
                    output = outputs.take();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                if (c == 1) {
                    p = new Point();
                    p.x = Math.toIntExact(output);
                } else if (c == 2) {
                    p.y = Math.toIntExact(output);
                } else if (c == 3) {
                    c = 0;
                    if (p.x == -1 && p.y == 0) {
                        score = output;
                    } else {
                        screen.put(p, Math.toIntExact(output));
                    }
                }
            }

            if(false) {
                int miny = screen.entrySet().stream().min(Comparator.comparingInt(e -> e.getKey().y)).map(Map.Entry::getKey).get().y;
                int minx = screen.entrySet().stream().min(Comparator.comparingInt(e -> e.getKey().x)).map(Map.Entry::getKey).get().x;
                int maxy = screen.entrySet().stream().max(Comparator.comparingInt(e -> e.getKey().y)).map(Map.Entry::getKey).get().y;
                int maxx = screen.entrySet().stream().max(Comparator.comparingInt(e -> e.getKey().x)).map(Map.Entry::getKey).get().x;

                for (int y = miny; y <= maxy; y++) {
                    for (int x = minx; x <= maxx; x++) {
                        Point pp = new Point(x, y);
                        if (screen.containsKey(pp)) {
                            switch (screen.get(pp)) {
                                case 0:
                                    System.out.print(" ");
                                    break;
                                case 1:
                                    System.out.print("█");
                                    break;
                                case 2:
                                    System.out.print("#");
                                    break;
                                case 3:
                                    System.out.print("_");
                                    break;
                                case 4:
                                    System.out.print("*");
                                    break;
                                default:
                                    System.err.print("Wrong tile type");
                                    break;
                            }
                        } else {
                            System.err.print("Missing tile in screen data " + pp);
                        }
                    }
                    System.out.println();
                }
            }

            long tiles = screen.values().stream().filter(x -> x == 2).count();
            Point ball = screen.entrySet().stream().filter(x -> x.getValue() == 4).findFirst().map(Map.Entry::getKey).get();
            Point paddle = screen.entrySet().stream().filter(x -> x.getValue() == 3).findFirst().map(Map.Entry::getKey).get();

            if(tiles == 0)
                keeprunning = false;

            if (paddle.x < ball.x) {
                inputs.add(1L);
            } else if (paddle.x > ball.x) {
                inputs.add(-1L);
            } else {
                inputs.add(0L);
            }
        }

        return String.valueOf(score);
    }
}
