package se.fluff.aoc2020.days;

import se.fluff.aoc.AocDay;

import java.awt.*;
import java.util.*;
import java.util.stream.LongStream;

/**
 * Created by Fluff on 2020-12-17.
 */
public class Day17 extends AocDay {

    public Day17() {

    }

    @Override
    public String a(Scanner in) {

        HashSet<Cube> g2 = new HashSet<>();
        long y = 0L;
        long x = 0L;
        while(in.hasNext()) {
            String s = in.nextLine();
            for(Character c : s.toCharArray()) {
                if(c == '#')
                    g2.add(new Cube(x, y, 0));
                x++;
            }
            x = 0;
            y++;
        }

        Grid grid = new Grid(g2);
        int c = 0;
        for(int i = 1; i <= 6; i++)
            c = grid.nextGen();

        return String.valueOf(c);
    }

    @Override
    public String b(Scanner in) {

        HashSet<Cube> g2 = new HashSet<>();
        long y = 0L;
        long x = 0L;
        while(in.hasNext()) {
            String s = in.nextLine();
            for(Character c : s.toCharArray()) {
                if(c == '#')
                    g2.add(new Cube(x, y, 0, 0));
                x++;
            }
            x = 0;
            y++;
        }

        Grid grid = new Grid(g2);
        int c = 0;
        for(int i = 1; i <= 6; i++)
            c = grid.nextGenHyper();

        return String.valueOf(c);
    }


}

class Grid {

    private HashSet<Cube> grid = new HashSet<>();
    private Cube minc = new Cube(Long.MAX_VALUE, Long.MAX_VALUE, Long.MAX_VALUE, Long.MAX_VALUE);
    private Cube maxc = new Cube(Long.MIN_VALUE, Long.MIN_VALUE, Long.MIN_VALUE, Long.MIN_VALUE);

    public Grid(HashSet<Cube> cubes) {
        for(Cube c : cubes)
            addCube(c);
    }

    public void addCube(Cube cube) {
        updateMinMax(cube);
        grid.add(cube);
    }

    public void updateMinMax(Cube cube) {
        for(char c : new char[] {'X', 'Y', 'Z', 'W'}) {
            if(cube.get(c) - 1 <= minc.get(c))
                minc.set(c, cube.get(c) - 1);
            if(cube.get(c) + 1 >= maxc.get(c))
                maxc.set(c, cube.get(c) + 1);
        }
    }

    public int neighbourCount(Cube c) {
        int count = 0;

        for(long xx = c.getX() - 1; xx <= c.getX() + 1; xx++) {
            for(long yy = c.getY() - 1; yy <= c.getY() + 1; yy++) {
                for(long zz = c.getZ() - 1; zz <= c.getZ() + 1; zz++) {
                    if(xx == c.getX() && yy == c.getY() && zz == c.getZ())
                        continue;

                    if(grid.contains(new Cube(xx, yy, zz)))
                        count++;
                }
            }
        }
        return count;
    }

    public int neighbourCountHyper(Cube c) {
        int count = 0;

        for(long x = c.getX() - 1; x <= c.getX() + 1; x++) {
            for(long y = c.getY() - 1; y <= c.getY() + 1; y++) {
                for(long z = c.getZ() - 1; z <= c.getZ() + 1; z++) {
                    for(long w = c.getW() - 1; w <= c.getW() + 1; w++) {
                        if (x == c.getX() && y == c.getY() && z == c.getZ() && w == c.getW())
                            continue;

                        if (grid.contains(new Cube(x, y, z, w)))
                            count++;
                    }
                }
            }
        }
        return count;
    }

    public void print() {
        for(long z = minc.getZ(); z <= maxc.getZ(); z++) {
            System.out.println("z=" + z);
            for(long y = minc.getY(); y <= maxc.getY(); y++) {
                for (long x = minc.getX(); x <= maxc.getX(); x++) {
                    Cube toFind = new Cube(x, y, z);
                    System.out.print(grid.contains(toFind) ? "#" : ".");
                }
                System.out.println();
            }
        }
    }

    public int nextGen() {
        HashSet<Cube> nextgrid = new HashSet<>();

        for(long x = minc.getX(); x <= maxc.getX(); x++) {
            for(long y = minc.getY(); y <= maxc.getY(); y++) {
                for(long z = minc.getZ(); z <= maxc.getZ(); z++) {
                    Cube testcube = new Cube(x, y, z);
                    int nc = neighbourCount(testcube);
                    if(grid.contains(testcube) && (nc == 2 || nc == 3)) {
                        nextgrid.add(testcube);
                        updateMinMax(testcube);
                    }
                    else if(!grid.contains(testcube) && nc == 3) {
                        nextgrid.add(testcube);
                        updateMinMax(testcube);
                    }
                }
            }
        }

        grid = nextgrid;
        return nextgrid.size();
    }

    public int nextGenHyper() {
        HashSet<Cube> nextgrid = new HashSet<>();

        for(long x = minc.getX(); x <= maxc.getX(); x++) {
            for(long y = minc.getY(); y <= maxc.getY(); y++) {
                for(long z = minc.getZ(); z <= maxc.getZ(); z++) {
                    for(long w = minc.getW(); w <= maxc.getW(); w++) {
                        Cube testcube = new Cube(x, y, z, w);
                        int nc = neighbourCountHyper(testcube);
                        if (grid.contains(testcube) && (nc == 2 || nc == 3)) {
                            nextgrid.add(testcube);
                            updateMinMax(testcube);
                        } else if (!grid.contains(testcube) && nc == 3) {
                            nextgrid.add(testcube);
                            updateMinMax(testcube);
                        }
                    }
                }
            }
        }

        grid = nextgrid;
        return nextgrid.size();
    }

}


class Cube {

    private long x;
    private long y;
    private long z;
    private long w;

    public Cube(long x, long y, long z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }

    public Cube(long x, long y, long z, long w) {
        this.x = x;
        this.y = y;
        this.z = z;
        this.w = w;
    }

    public long getX() {
        return x;
    }

    public long getY() {
        return y;
    }

    public long getZ() {
        return z;
    }

    public long getW() {
        return w;
    }

    public long get(Character axis) {
        long rv = Long.MAX_VALUE;
        switch(axis) {
            case 'X':
                rv = x;
                break;
            case 'Y':
                rv = y;
                break;
            case 'Z':
                rv = z;
                break;
            case 'W':
                rv = w;
                break;
        }

        return rv;
    }

    public void set(Character axis, long v) {
        switch(axis) {
            case 'X':
                x = v;
                break;
            case 'Y':
                y = v;
                break;
            case 'Z':
                z = v;
                break;
            case 'W':
                w = v;
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Cube cube = (Cube) o;
        return x == cube.x && y == cube.y && z == cube.z && w == cube.w;
    }

    @Override
    public int hashCode() {
        return Math.toIntExact(z + w * 10 + y * 100 + x * 1000);
    }
}