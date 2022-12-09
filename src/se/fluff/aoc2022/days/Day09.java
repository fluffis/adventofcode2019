package se.fluff.aoc2022.days;

import se.fluff.aoc.AocDay;
import se.fluff.aoc.GridUtils;

import java.awt.*;
import java.util.*;

/**
 * Created by Fluff on 2022-12-09.
 */
public class Day09 extends AocDay {

    public Day09() {

    }

    @Override
    public String a(Scanner in, boolean isTest) throws Exception {

        Rope rope = new Rope(2);
        while(in.hasNext()) {
            String[] l = in.nextLine().split(" ");
            rope.moveHead(l[0], Integer.parseInt(l[1]));
        }


        return String.valueOf(rope.getRopeLastVisisted());
    }

    @Override
    public String b(Scanner in, boolean isTest) {
        Rope rope = new Rope(10);
        while(in.hasNext()) {
            String[] l = in.nextLine().split(" ");
            rope.moveHead(l[0], Integer.parseInt(l[1]));
        }


        return String.valueOf(rope.getRopeLastVisisted());
    }
}

class Rope {

    private int len;
    private ArrayList<Point> positions = new ArrayList<>();
    private HashSet<Point> lasttailvisit = new HashSet<>();

    public Rope(int len) {
        this.len = len;
        for(int i = 0; i < len; i++) {
            positions.add(new Point(0, 0));
        }
    }

    public void moveHead(String direction, int steps) {
        int xd = 0, yd = 0;

        switch (direction) {
            case "R":
                xd = 1;
                break;
            case "L":
                xd = -1;
                break;
            case "U":
                yd = 1;
                break;
            case "D":
                yd = -1;
                break;
        }

        for(int i = 0; i < steps; i++) {

            Point head = positions.get(0);
            positions.set(0, new Point(head.x + xd, head.y + yd));
            Point tail = null;
            for(int pos = 1; pos < len; pos++) {
                if(pos > positions.size())
                    continue;
                head = positions.get(pos - 1);
                tail = positions.get(pos);
                Point[] neighbours = GridUtils.getNeighbours(head, true, true);
                Point finalTail = tail;
                if (Arrays.stream(neighbours).noneMatch(n -> n.x == finalTail.x && n.y == finalTail.y)) {
                    if (head.x == tail.x) {
                        if (head.y > tail.y)
                            tail.y++;
                        else
                            tail.y--;
                    } else if (head.y == tail.y) {
                        if (head.x > tail.x)
                            tail.x++;
                        else
                            tail.x--;
                    } else {
                        if (head.y > tail.y)
                            tail.y++;
                        else
                            tail.y--;
                        if (head.x > tail.x)
                            tail.x++;
                        else
                            tail.x--;

                    }
                }
                positions.set(pos, new Point(tail));

            }
            lasttailvisit.add(new Point(positions.get(len - 1)));
        }
    }

    public int getRopeLastVisisted() {
        return lasttailvisit.size();
    }
}

