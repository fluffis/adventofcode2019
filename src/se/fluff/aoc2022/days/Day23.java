package se.fluff.aoc2022.days;

import se.fluff.aoc.AocDay;
import se.fluff.aoc.GridUtils;

import java.awt.*;
import java.util.*;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

/**
 * Created by Fluff on 2022-12-29.
 */
public class Day23 extends AocDay {

    public Day23() {

    }

    @Override
    public String a(Scanner in, boolean isTest) throws Exception {
        HashMap<Point, Elf> elfmap = new HashMap<>();
        int y = 0;
        int x = 0;
        while(in.hasNext()) {
            String l = in.nextLine();
            for(char c : l.toCharArray()) {
                if(c == '#') {
                    elfmap.put(new Point(x, y), new Elf(new Point(x, y)));
                }
                x++;
            }
            y++;
            x = 0;
        }

        //System.out.println("Initial state");
        //GridUtils.printGrid(map, '.');

        for(int round = 1; round <= 10; round++) {

            HashMap<Point, Integer> proposedmoves = new HashMap<>();

           for(Elf elf : elfmap.values()) {
               List<Elf> neighbours = new ArrayList<>();
               for(Point npoint : GridUtils.getNeighbours(elf.pos, true)) {
                   if(elfmap.containsKey(npoint)) {
                       neighbours.add(elfmap.get(npoint));
                   }
               }
               Point target = elf.suggestMove(neighbours);
               proposedmoves.put(target, proposedmoves.getOrDefault(target, 0) + 1);

           }

           HashMap<Point, Elf> movedelfmap = new HashMap<>();
           for(Elf elf : elfmap.values()) {
               if(proposedmoves.get(elf.proposedMove) == 1) {
                   elf.move();
               }
               movedelfmap.put(elf.pos, elf);
               elf.proposedMove = null;
               elf.state = State.IDLE;
           }
           elfmap = movedelfmap;

            //System.out.println("End or round " + round);
            //GridUtils.printGrid(map, '.');
        }

        long miny = elfmap.entrySet().stream().min(Comparator.comparingInt(e -> e.getKey().y)).map(Map.Entry::getKey).get().y;
        long minx = elfmap.entrySet().stream().min(Comparator.comparingInt(e -> e.getKey().x)).map(Map.Entry::getKey).get().x;
        long maxy = elfmap.entrySet().stream().max(Comparator.comparingInt(e -> e.getKey().y)).map(Map.Entry::getKey).get().y;
        long maxx = elfmap.entrySet().stream().max(Comparator.comparingInt(e -> e.getKey().x)).map(Map.Entry::getKey).get().x;

        long emptytiles = (maxx - minx + 1) * (maxy - miny + 1) - elfmap.size();

        return String.valueOf(emptytiles);
    }

    @Override
    public String b(Scanner in, boolean isTest) throws Exception {
        HashMap<Point, Elf> map = new HashMap<>();
        int y = 0;
        int x = 0;
        while(in.hasNext()) {
            String l = in.nextLine();
            for(char c : l.toCharArray()) {
                if(c == '#') {
                    map.put(new Point(x, y), new Elf(new Point(x, y)));
                }
                x++;
            }
            y++;
            x = 0;
        }

        int round = 0;
        long movecount = Integer.MAX_VALUE;
        while(movecount > 0) {
            round++;

            HashMap<Point, Integer> proposedmoves = new HashMap<>();

            movecount = 0;
            for(Elf elf : map.values()) {
                List<Elf> neighbours = new ArrayList<>();
                for(Point npoint : GridUtils.getNeighbours(elf.pos, true)) {
                    if(map.containsKey(npoint)) {
                        neighbours.add(map.get(npoint));
                    }
                }
                Point target = elf.suggestMove(neighbours);
                if(target != elf.pos) {
                    movecount++;
                }
                proposedmoves.put(target, proposedmoves.getOrDefault(target, 0) + 1);
            }

            HashMap<Point, Elf> movedmap = new HashMap<>();
            for(Elf elf : map.values()) {
                if(proposedmoves.get(elf.proposedMove) == 1) {
                    elf.move();
                }
                movedmap.put(elf.pos, elf);
                elf.proposedMove = null;
                elf.state = State.IDLE;
            }

            map = movedmap;
        }

        return String.valueOf(round);
    }
}

class Elf {
    Point pos;
    Point proposedMove;
    char proposedDirection = 'R';
    State state = State.IDLE;
    int firstdirection = 0;


    public Elf(Point pos) {
        this.pos = pos;
    }

    public Point suggestMove(List<Elf> neighbours) throws Exception {

        proposedMove = null;
        if(neighbours.size() == 0) {
            proposedMove = pos;
            proposedDirection = 'R';
        }
        else {
            ArrayList<Long> dn = new ArrayList<>();

            dn.add(neighbours.stream().filter(e -> e.pos.y == pos.y - 1).count());
            dn.add(neighbours.stream().filter(e -> e.pos.y == pos.y + 1).count());
            dn.add(neighbours.stream().filter(e -> e.pos.x == pos.x - 1).count());
            dn.add(neighbours.stream().filter(e -> e.pos.x == pos.x + 1).count());
            for(int i = 0; i <= 3; i++) {
                int n = (i + firstdirection) % 4;
                if(dn.get(n) == 0) {
                    switch(n) {
                        case 0:
                            proposedMove = new Point(pos.x, pos.y - 1);
                            proposedDirection = 'N';
                            break;
                        case 1:
                            proposedMove = new Point(pos.x, pos.y + 1);
                            proposedDirection = 'S';
                            break;
                        case 2:
                            proposedMove = new Point(pos.x - 1, pos.y);
                            proposedDirection = 'W';
                            break;
                        case 3:
                            proposedMove = new Point(pos.x + 1, pos.y);
                            proposedDirection = 'E';
                            break;
                        default:
                            throw new Exception("Unknown direction");

                    }
                    break;
                }

            }

        }
        if(proposedMove == null) {
            proposedDirection = 'C';
            proposedMove = new Point(pos);
        }

        firstdirection++;
        if(firstdirection == 4)
            firstdirection = 0;

        state = State.PROPOSE;
        return proposedMove;
    }

    public void move() throws Exception {
        if(proposedMove != null) {
            pos = new Point(proposedMove);
            state = State.MOVE;
        }
        else {
            throw new Exception("proposed move is null");
        }
    }

}
enum State {
    IDLE,
    PROPOSE,
    MOVE
}