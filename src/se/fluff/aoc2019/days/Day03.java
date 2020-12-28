package se.fluff.aoc2019.days;

import se.fluff.aoc.AocDay;

import java.awt.*;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Created by Fluff on 2019-12-03.
 */
public class Day03 extends AocDay {

    public Day03() {

    }

    @Override
    public String a(Scanner in, boolean isTest) {

        String wire1 = in.nextLine();
        String wire2 = in.nextLine();

        HashMap<Point, Integer> board1 = getStepboard(wire1);
        HashMap<Point, Integer> board2 = getStepboard(wire2);

        ArrayList<Point> shortcuts = (ArrayList<Point>) board1.entrySet()
                .stream()
                .filter(e -> board2.containsKey(e.getKey()))
                .map(Map.Entry::getKey)
                .collect(Collectors.toList());

        Point closest = shortcuts
                .stream()
                .min(Comparator.comparingInt(p -> Math.abs(p.x) + Math.abs(p.y)))
                .orElseThrow(NoSuchElementException::new);

        return Integer.toString(Math.abs(closest.x) + Math.abs(closest.y));
    }

    @Override
    public String b(Scanner in, boolean isTest) {

        String wire1 = in.nextLine();
        String wire2 = in.nextLine();

        HashMap<Point,Integer> board1 = getStepboard(wire1);
        HashMap<Point,Integer> board2 = getStepboard(wire2);

        ArrayList<Point> shortcuts = (ArrayList<Point>) board1.entrySet()
                .stream()
                .filter(e -> board2.containsKey(e.getKey()))
                .map(Map.Entry::getKey)
                .collect(Collectors.toList());

        int minsteps = 0;
        for(Point p : shortcuts) {
            int sumsteps = board1.get(p) + board2.get(p);
            if(minsteps == 0 || sumsteps < minsteps)
                minsteps = sumsteps;
        }

        return Integer.toString(minsteps);

    }

    private HashMap<Point, Integer> getStepboard(String wire) {
        String[] movements = wire.split(",");

        HashMap<Point, Integer> board = new HashMap<>();
        int x = 0;
        int y = 0;
        int steps = 0;
        for(String m : movements) {

            char direction = m.charAt(0);
            int length = Integer.parseInt(m.substring(1));
            for(int i = 0; i < length; i++) {
                steps++;
                switch (direction) {
                    case 'U':
                        y++;
                        break;
                    case 'D':
                        y--;
                        break;
                    case 'L':
                        x--;
                        break;
                    case 'R':
                        x++;
                        break;
                }

                Point p = new Point(x, y);
                if (!board.containsKey(p)) {
                    board.put(p, steps);
                }
            }
        }

        return board;
    }

}
