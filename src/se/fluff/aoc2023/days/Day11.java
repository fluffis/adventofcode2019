package se.fluff.aoc2023.days;

import se.fluff.aoc.AocDay;
import se.fluff.aoc.GridUtils;

import java.awt.*;
import java.util.*;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

/**
 * Created by Fluff on 2023-12-11.
 */
public class Day11 extends AocDay {

    public Day11() {

    }

    @Override
    public String a(Scanner in, boolean isTest) throws Exception {
        HashMap<Point, Character> map = new HashMap<>();

        int x = 0;
        int y = 0;
        int maxx = 0;
        int maxy = 0;
        List<Integer> expandspaceatrows = new ArrayList<>();
        List<Integer> expandspaceatcols = new ArrayList<>();
        List<Point> galaxies = new ArrayList<>();
        while(in.hasNext()) {
            String l = in.nextLine();
            int match = 0;
            for(char c : l.toCharArray()) {
                map.put(new Point(x, y), c);
                if(c == '#') {
                    galaxies.add(new Point(x, y));
                }
                if(c == '.') {
                    match++;
                }
                x++;
            }
            maxx = Math.max(x, maxx);

            if(match == maxx) {
                expandspaceatrows.add(y);
            }

            x = 0;
            y++;
            maxy = Math.max(y, maxy);
        }

        for(x = 0; x < maxx; x++) {
            int match = 0;
            for(y = 0; y < maxy; y++) {
                if(map.get(new Point(x, y)) == '.') {
                    match++;
                }
            }

            if(match == maxy) {
                expandspaceatcols.add(x);
            }
        }

        long sum = 0;

        for(int pc = 0; pc < galaxies.size(); pc++) {
            Point p1 = galaxies.get(pc);
            for(int pcc = pc + 1; pcc < galaxies.size(); pcc++) {
                Point p2 = galaxies.get(pcc);
                sum += getExpandedDistance(p1, p2, expandspaceatcols, expandspaceatrows, 1);
            }
        }

        return String.valueOf(sum);

    }

    @Override
    public String b(Scanner in, boolean isTest) throws Exception {
        HashMap<Point, Character> map = new HashMap<>();

        Pattern p = Pattern.compile("^\\.+$");
        int x = 0;
        int y = 0;
        int maxx = 0;
        int maxy = 0;
        List<Integer> expandspaceatrows = new ArrayList<>();
        List<Integer> expandspaceatcols = new ArrayList<>();
        List<Point> galaxies = new ArrayList<>();

        while(in.hasNext()) {
            String l = in.nextLine();
            int match = 0;
            for(char c : l.toCharArray()) {
                map.put(new Point(x, y), c);
                if(c == '#') {
                    galaxies.add(new Point(x, y));
                }
                if(c == '.') {
                    match++;
                }
                x++;
            }
            maxx = Math.max(x, maxx);

            if(match == maxx) {
                expandspaceatrows.add(y);
            }
            x = 0;
            y++;
            maxy = Math.max(y, maxy);
        }

        for(x = 0; x < maxx; x++) {
            int match = 0;
            for(y = 0; y < maxy; y++) {
                if(map.get(new Point(x, y)) == '.') {
                    match++;
                }
            }

            if(match == maxy) {
                expandspaceatcols.add(x);
            }
        }

        long sum = 0;
        for(int pc = 0; pc < galaxies.size(); pc++) {
            Point p1 = galaxies.get(pc);
            for(int pcc = pc + 1; pcc < galaxies.size(); pcc++) {
                Point p2 = galaxies.get(pcc);
                sum += getExpandedDistance(p1, p2, expandspaceatcols, expandspaceatrows, 999999);
            }
        }

        return String.valueOf(sum);
    }

    public long getExpandedDistance(Point p1, Point p2, List<Integer> expandedcols, List<Integer> expandedrows, long expandedsize) {

        long minx = Math.min(p1.x, p2.x);
        long maxx = Math.max(p1.x, p2.x);

        long miny = Math.min(p1.y, p2.y);
        long maxy = Math.max(p1.y, p2.y);

        long finalx = maxx;
        long finaly = maxy;
        for(int row : expandedrows) {
            if(row > miny && row < maxy) {
                finaly += expandedsize;
            }
        }
        for(int col : expandedcols) {
            if(col > minx && col < maxx) {
                finalx += expandedsize;
            }
        }

        return Math.abs(minx - finalx) + Math.abs(miny - finaly);
    }
}