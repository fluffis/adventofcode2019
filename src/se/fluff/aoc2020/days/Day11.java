package se.fluff.aoc2020.days;

import se.fluff.aoc.AocDay;

import java.awt.*;
import java.util.HashMap;
import java.util.Scanner;

/**
 * Created by Fluff on 2020-12-11.
 */
public class Day11 extends AocDay {

    public Day11() {

    }

    @Override
    public String a(Scanner in) {
        int x = 0;
        int y = 0;

        HashMap<Point, Character> seats = new HashMap<>();
        while(in.hasNext()) {
            String s = in.nextLine();
            for(char c : s.toCharArray()) {
                seats.put(new Point(x, y), c);
                x++;
            }
            x = 0;
            y++;
        }

        long newresult = 0;
        while(true) {
            long lastresult = newresult;
            seats = shift(seats, 4);
            newresult = seats.values().stream().filter(c -> c == '#').count();

            if(newresult == lastresult)
                return String.valueOf(newresult);

        }
    }

    @Override
    public String b(Scanner in) {
        int x = 0;
        int y = 0;
        HashMap<Point, Character> seats = new HashMap<>();
        while(in.hasNext()) {
            String s = in.nextLine();
            for(char c : s.toCharArray()) {
                seats.put(new Point(x, y), c);
                x++;
            }
            x = 0;
            y++;
        }

        long newresult = 0;
        while(true) {
            long lastresult = newresult;
            seats = shiftVisible(seats, 5);
            newresult = seats.values().stream().filter(c -> c == '#').count();

            if(newresult == lastresult)
                return String.valueOf(newresult);

        }
    }

    public int countNeighbours(HashMap<Point, Character> seats, Point p) {
        int c = 0;
        for(int y = p.y - 1; y <= p.y + 1; y++) {
            for(int x = p.x - 1; x <= p.x + 1; x++) {
                Point check = new Point(x, y);
                if(seats.getOrDefault(check, '.') == '#' && (check.x != p.x || check.y != p.y))
                    c++;
            }
        }
        return c;
    }

    public int countVisibleNeighbours(HashMap<Point, Character> seats, Point p) {
        int c = 0;
        for(int y = p.y - 1; y <= p.y + 1; y++) {
            for(int x = p.x - 1; x <= p.x + 1; x++) {
                Point check = new Point(x, y);

                while(true) {
                    if(!seats.containsKey(check) || (check.x == p.x && check.y == p.y) || seats.get(check) == 'L')
                        break;

                    if(seats.get(check) == '#') {
                        c++;
                        break;
                    }

                    if(check.y < p.y)
                        check.y--;
                    else if(check.y > p.y)
                        check.y++;

                    if(check.x < p.x)
                        check.x--;
                    else if(check.x > p.x)
                        check.x++;
                }

            }
        }
        return c;
    }

    public HashMap<Point, Character> shift(HashMap<Point, Character> seats, int tol) {
        HashMap<Point, Character> newseatmap = new HashMap<>(seats);
        for(Point p : seats.keySet()) {
            int n = countNeighbours(seats, p);
            if(n == 0 && seats.get(p) == 'L')
                newseatmap.put(p, '#');
            else if(n >= tol && seats.get(p) == '#')
                newseatmap.put(p, 'L');
        }

        return newseatmap;
    }

    public HashMap<Point, Character> shiftVisible(HashMap<Point, Character> seats, int tol) {
        HashMap<Point, Character> newseatmap = new HashMap<>(seats);
        for(Point p : seats.keySet()) {
            int n = countVisibleNeighbours(seats, p);
            if(n == 0 && seats.get(p) == 'L')
                newseatmap.put(p, '#');
            else if(n >= tol && seats.get(p) == '#')
                newseatmap.put(p, 'L');
        }

        return newseatmap;
    }
}
