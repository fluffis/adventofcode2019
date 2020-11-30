package se.fluff.aoc2019.days;

import se.fluff.aoc.AocDay;
import se.fluff.aoc2019.LongCodeMachine;

import java.awt.*;
import java.util.*;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

/**
 * Created by Fluff on 2019-12-15.
 */
public class Day15 extends AocDay {

    HashMap<Integer, Long> intcode = new HashMap<>();
    HashMap<Point, Character> grid = new HashMap<>();

    public Day15() {

    }

    @Override
    public String a(Scanner in) throws Exception {

        LinkedList<Point> queue = new LinkedList<>();

        String line = in.nextLine();
        int i = 0;
        for(String nr : line.split(",")) {
            intcode.put(i, Long.parseLong(nr));
            i++;
        }

        ArrayBlockingQueue<Long> inputs = new ArrayBlockingQueue<>(10000);
        ArrayBlockingQueue<Long> outputs = new ArrayBlockingQueue<>(10000);
        LongCodeMachine lcm = new LongCodeMachine(intcode, 1, inputs, outputs);

        Point goal = null;
        Point curpos = new Point(0, 0);
        grid.put(new Point(0, 0), ' ');
        queue.add(new Point(0, 0));

        while(!queue.isEmpty()) {

            Point target = queue.removeLast();

            for(int instr : solveMaze(grid, (Point) curpos.clone(), target)) {
                inputs.add((long) instr);
                lcm.run();
                Long res = outputs.remove();
                if(res == 0L)
                    throw new Exception("Hit a wall where we shouldn't");

                curpos = newpoint(curpos, instr);
            }

            for (int c = 1; c <= 4; c++) {
                inputs.add((long) c);
                lcm.run();
                try {
                    long res = outputs.poll(1, TimeUnit.DAYS);
                    if (res == 0L)
                        grid.put(newpoint(curpos, c), '#');
                    else {
                        inputs.add((long) revertDirection(c));
                        lcm.run();
                        outputs.clear();
                        if (res == 1L && !grid.containsKey(newpoint(curpos, c))) {
                            grid.put(newpoint(curpos, c), ' ');
                            queue.add(newpoint(curpos, c));
                        } else if (res == 2L) {
                            grid.put(newpoint(curpos, c), 'O');
                            goal = newpoint(curpos, c);
                        }
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }

        return String.valueOf(solveMaze(grid, new Point(0, 0), goal).size());
    }

    @Override
    public String b(Scanner in) {

        int miny = grid.entrySet().stream().min(Comparator.comparingInt(e -> e.getKey().y)).map(Map.Entry::getKey).get().y;
        int minx = grid.entrySet().stream().min(Comparator.comparingInt(e -> e.getKey().x)).map(Map.Entry::getKey).get().x;
        int maxy = grid.entrySet().stream().max(Comparator.comparingInt(e -> e.getKey().y)).map(Map.Entry::getKey).get().y;
        int maxx = grid.entrySet().stream().max(Comparator.comparingInt(e -> e.getKey().x)).map(Map.Entry::getKey).get().x;

        for (int y = maxy; y >= miny; y--) {
            for (int x = minx; x <= maxx; x++) {
                Point pp = new Point(x, y);
                if (!grid.containsKey(pp))
                    grid.put(pp, '?');
            }
        }

        int minutes = 0;
        while(grid.entrySet().stream().filter(e -> e.getValue().equals(' ')).count() > 0) {
            minutes++;
            ArrayList<Point> oxygens = (ArrayList<Point>) grid.entrySet()
                    .stream()
                    .filter(e -> e.getValue().equals('O'))
                    .map(Map.Entry::getKey)
                    .collect(Collectors.toList());
            for(Point oxypoint : oxygens) {
                for(int i = 1; i <= 4; i++) {
                    Point test = newpoint(oxypoint, i);
                    if(grid.containsKey(test)) {
                        if(grid.get(test).equals(' ') || grid.get(test).equals('C'))
                            grid.put(test, 'O');
                    }
                }
            }
        }


        return String.valueOf(minutes);
    }

    private void printMap(HashMap<Point, Character> grid, Point curpos) {
        int miny = grid.entrySet().stream().min(Comparator.comparingInt(e -> e.getKey().y)).map(Map.Entry::getKey).get().y;
        int minx = grid.entrySet().stream().min(Comparator.comparingInt(e -> e.getKey().x)).map(Map.Entry::getKey).get().x;
        int maxy = grid.entrySet().stream().max(Comparator.comparingInt(e -> e.getKey().y)).map(Map.Entry::getKey).get().y;
        int maxx = grid.entrySet().stream().max(Comparator.comparingInt(e -> e.getKey().x)).map(Map.Entry::getKey).get().x;

        for (int y = maxy; y >= miny; y--) {
            for (int x = minx; x <= maxx; x++) {
                Point pp = new Point(x, y);
                if(pp.equals(curpos))
                    System.out.print("C");
                else if (grid.containsKey(pp))
                    System.out.print(grid.get(pp));
                else
                    System.out.print("?");
            }
            System.out.println();
        }
    }

    private int revertDirection(int i) {
        switch(i) {
            case 1:
                return 2;
            case 2:
                return 1;
            case 3:
                return 4;
            case 4:
                return 3;
            default:
                System.err.println("Unknown int to revert direction for");
                return -1;
        }
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
        }

        return p;
    }

    private ArrayList<Integer> solveMaze(HashMap<Point, Character> grid, Point from, Point to) {
        HashMap<Point, Boolean> visited = new HashMap<>();
        LinkedList<Coord> q = new LinkedList<>();
        q.add(new Coord(from, null));

        while(!q.isEmpty()) {
            Coord p = q.remove();

            if(!grid.containsKey(p) && visited.getOrDefault(p, false))
                continue;

            if(grid.containsKey(p) && grid.get(p).equals('#')) {
                visited.put(new Point(p.getX(), p.getY()), true);
                continue;
            }

            if(p.getX() == to.x && p.getY() == to.y)
                return backTrack(p);

            for(int i = 1; i <= 4; i++) {
                Point testNeighbour = newpoint(new Point(p.getX(), p.getY()), i);
                if(grid.containsKey(testNeighbour)) {
                    if(!grid.get(testNeighbour).equals('#') && !visited.getOrDefault(testNeighbour, false)) {
                        visited.put(testNeighbour, true);
                        q.add(new Coord(testNeighbour, p));
                    }
                }
            }
        }
        return new ArrayList<>();
    }

    private ArrayList<Integer> backTrack(Coord p) {
        ArrayList<Integer> path = new ArrayList<>();
        Coord iter = p;

        while (iter.getParent() != null) {
            if(iter.getY() > iter.getParent().getY())
                path.add(1);
            else if(iter.getY() < iter.getParent().getY())
                path.add(2);
            else if(iter.getX() < iter.getParent().getX())
                path.add(3);
            else if(iter.getX() > iter.getParent().getX())
                path.add(4);

            iter = iter.getParent();
        }

        Collections.reverse(path);
        return path;
    }
}

class Coord {

    private Coord parent;
    private int x;
    private int y;

    public Coord(Point cur, Coord parent) {
        this.x = cur.x;
        this.y = cur.y;
        this.parent = parent;
    }

    public Coord getParent() {
        return parent;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public int getAbsX() {
        return Math.abs(x);
    }

    public int getAbsY() {
        return Math.abs(y);
    }

    @Override
    public String toString() {
        return "Coord{" +
                "parent=" + parent +
                ", x=" + x +
                ", y=" + y +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Coord coord = (Coord) o;
        return x == coord.x &&
                y == coord.y &&
                Objects.equals(parent, coord.parent);
    }

    @Override
    public int hashCode() {
        return Objects.hash(parent, x, y);
    }
}