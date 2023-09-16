package se.fluff.aoc2022.days;

import se.fluff.aoc.AocDay;
import se.fluff.aoc.GridUtils;

import java.awt.*;
import java.util.*;

/**
 * Created by Fluff on 2022-12-18.
 */
public class Day18 extends AocDay {

    public Day18() {

    }

    @Override
    public String a(Scanner in, boolean isTest) throws Exception {
        HashSet<Point3D> points = new HashSet<>();
        while(in.hasNext()) {
            String[] coords = in.nextLine().split(",");
            Point3D p = new Point3D(Integer.parseInt(coords[0]), Integer.parseInt(coords[1]), Integer.parseInt(coords[2]));
            points.add(p);
        }

        int exposed = 0;
        for(Point3D p : points) {
            int n = 6;
            for(Point3D test : points) {
                if(!test.equals(p)) {
                    int mdist = Math.abs(p.x - test.x) + Math.abs(p.y - test.y) + Math.abs(p.z - test.z);
                    if(mdist == 1) {
                        n--;
                    }
                }
            }
            exposed += n;
        }

        return String.valueOf(exposed);
    }

    @Override
    public String b(Scanner in, boolean isTest) {
        HashMap<Integer, HashSet<Point>> space = new HashMap<>();
        HashSet<Point3D> points = new HashSet<>();
        while(in.hasNext()) {
            String[] coords = in.nextLine().split(",");
            Point3D p = new Point3D(Integer.parseInt(coords[0]), Integer.parseInt(coords[1]), Integer.parseInt(coords[2]));
            points.add(p);
        }

        int xmin = points.stream().min(Comparator.comparingInt(p -> p.x)).get().x - 1;
        int xmax = points.stream().max(Comparator.comparingInt(p -> p.x)).get().x + 1;
        int ymin = points.stream().min(Comparator.comparingInt(p -> p.y)).get().y - 1;
        int ymax = points.stream().max(Comparator.comparingInt(p -> p.y)).get().y + 1;
        int zmin = points.stream().min(Comparator.comparingInt(p -> p.z)).get().z - 1;
        int zmax = points.stream().max(Comparator.comparingInt(p -> p.z)).get().z + 1;

        int exposed = 0;
        for(Point3D p : points) {
            int n = 6;
            for(Point3D test : points) {
                if(!test.equals(p)) {
                    int mdist = Math.abs(p.x - test.x) + Math.abs(p.y - test.y) + Math.abs(p.z - test.z);
                    if(mdist == 1) {
                        n--;
                    }
                }
            }
            exposed += n;
        }

        ArrayDeque<Point3D> pointqueue = new ArrayDeque<>();
        HashSet<Point3D> filled = new HashSet<>(points);
        pointqueue.add(new Point3D(xmin, ymin, zmin));
        while(pointqueue.size() > 0) {
            Point3D test = pointqueue.poll();
            if(filled.contains(test))
                continue;
            else if(test.x < xmin || test.x > xmax || test.y < ymin || test.y > xmax || test.z < zmin || test.z > zmax)
                continue;

            filled.add(test);
            Point[] neighbours = GridUtils.getNeighbours(new Point(test.x, test.y), false);
            for(Point p : neighbours)
                pointqueue.add(new Point3D(p.x, p. y, test.z));

            pointqueue.add(new Point3D(test.x, test.y, test.z - 1));
            pointqueue.add(new Point3D(test.x, test.y, test.z + 1));
        }

        for(int z = zmin; z < zmax; z++) {
            for(int y = ymin; y < ymax; y++) {
                for(int x = xmin; x < xmax; x++) {
                    if(!filled.contains(new Point3D(x, y, z))) {
                        int nc = 0;
                        Point[] neighbours = GridUtils.getNeighbours(new Point(x, y), false);
                        for(Point p : neighbours) {
                            if(filled.contains(new Point3D(p.x, p.y, z))) {
                                nc++;
                            }
                        }
                        if(filled.contains(new Point3D(x, y, z - 1)))
                            nc++;
                        if(filled.contains(new Point3D(x, y, z + 1)))
                            nc++;

                        exposed -= nc;
                    }
                }
            }
        }

        return String.valueOf(exposed);
    }
}

class Point3D {
    int x;
    int y;
    int z;

    public Point3D(int x, int y, int z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Point3D point3D = (Point3D) o;
        return x == point3D.x && y == point3D.y && z == point3D.z;
    }

    @Override
    public int hashCode() {
        return Objects.hash(x, y, z);
    }
}
