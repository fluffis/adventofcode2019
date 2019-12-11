package se.fluff.aoc2019.days;

import se.fluff.aoc2019.AocDay;
import se.fluff.aoc2019.LongCodeMachine;
import se.fluff.aoc2019.Robot;


import java.awt.*;
import java.util.*;
import java.util.concurrent.ArrayBlockingQueue;

/**
 * Created by Fluff on 2019-12-11.
 */
public class Day11 extends AocDay {

    public Day11() {

    }

    @Override
    public String a(Scanner in) {

        HashMap<Integer, Long> intcode = new HashMap<>();

        String line = in.nextLine();
        int i = 0;
        for(String nr : line.split(",")) {
            intcode.put(i, Long.parseLong(nr));
            i++;
        }

        Robot r = new Robot();

        ArrayBlockingQueue<Long> inputs = new ArrayBlockingQueue<>(9999);
        ArrayBlockingQueue<Long> outputs = new ArrayBlockingQueue<>(9999);
        LongCodeMachine lcm = new LongCodeMachine(intcode, 1, inputs, outputs);
        inputs.add(0L);
        while(!lcm.isHalted()) {
            int color = -1;
            int turn = -1;
            lcm.run();

            try {
                color = outputs.take().intValue();
                turn = outputs.take().intValue();

            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            if(color == 0 || color == 1)
                r.paint(color);
            else
                System.out.println("Wrong color " + color);

            if(turn == 0)
                r.turnLeft();
            else if(turn == 1)
                r.turnRight();
            else
                System.out.println("Wrong turn " + turn);

            r.move(1);
            inputs.add((long) r.getColorOfCurrentPos());

        }

        return String.valueOf(r.getPainted().keySet().size());
    }

    @Override
    public String b(Scanner in) {
        HashMap<Integer, Long> intcode = new HashMap<>();

        String line = in.nextLine();
        int i = 0;
        for(String nr : line.split(",")) {
            intcode.put(i, Long.parseLong(nr));
            i++;
        }

        Robot r = new Robot();

        ArrayBlockingQueue<Long> inputs = new ArrayBlockingQueue<>(9999);
        ArrayBlockingQueue<Long> outputs = new ArrayBlockingQueue<>(9999);
        LongCodeMachine lcm = new LongCodeMachine(intcode, 1, inputs, outputs);
        inputs.add(1L);
        while(!lcm.isHalted()) {
            int color = -1;
            int turn = -1;
            lcm.run();

            try {
                color = outputs.take().intValue();
                turn = outputs.take().intValue();

            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            if(color == 0 || color == 1)
                r.paint(color);
            else
                System.out.println("Wrong color " + color);

            if(turn == 0)
                r.turnLeft();
            else if(turn == 1)
                r.turnRight();
            else
                System.out.println("Wrong turn " + turn);

            r.move(1);
            inputs.add((long) r.getColorOfCurrentPos());
        }

        HashMap<Point, Integer> painting = r.getPainted();
        int miny = painting.entrySet().stream().min(Comparator.comparingInt(e -> e.getKey().y)).map(Map.Entry::getKey).get().y;
        int minx = painting.entrySet().stream().min(Comparator.comparingInt(e -> e.getKey().x)).map(Map.Entry::getKey).get().x;
        int maxy = painting.entrySet().stream().max(Comparator.comparingInt(e -> e.getKey().y)).map(Map.Entry::getKey).get().y;
        int maxx = painting.entrySet().stream().max(Comparator.comparingInt(e -> e.getKey().x)).map(Map.Entry::getKey).get().x;

        for(int y = maxy; y >= miny; y--) {
            for(int x = minx; x <= maxx; x++) {
                Point p = new Point(x, y);
                if(painting.containsKey(p)) {
                    if(painting.get(p) == 1)
                        System.out.print("█");
                    else
                        System.out.print(" ");
                }
                else {
                    System.out.print(" ");
                }
            }
            System.out.println();
        }

        return null;
    }
}