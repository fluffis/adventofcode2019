package se.fluff.aoc2021.days;

import se.fluff.aoc.AocDay;
import se.fluff.aoc.GridUtils;

import java.awt.*;
import java.util.*;
import java.util.regex.MatchResult;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by Fluff on 2022-05-31.
 */
public class Day17 extends AocDay {

    public Day17() {

    }

    @Override
    public String a(Scanner in, boolean isTest) throws Exception {
        String s = in.nextLine();
        Pattern p = Pattern.compile("^target area: x=([^\\.]+)\\.\\.([^\\,]+), y=([^\\.]+)\\.\\.(.+)");
        Matcher m = p.matcher(s);
        Point p1, p2;

        if(m.find()) {
            p1 = new Point(Integer.parseInt(m.group(1)), Integer.parseInt(m.group(3)));
            p2 = new Point(Integer.parseInt(m.group(2)), Integer.parseInt(m.group(4)));
        }
        else
            throw new RuntimeException("Failed to parse pattern");

        ArrayList<Result> hits = new ArrayList<>();

        for(int x = 200; x > -200; x--) {
            for(int y = 200; y > -200; y--) {
                Result r = launch(p1, p2, x, y);
                if(r.isHit())
                    hits.add(r);
            }
        }


        return String.valueOf(hits.stream().mapToInt(Result::getMaxY).max().getAsInt());
    }

    @Override
    public String b(Scanner in, boolean isTest) {
        String s = in.nextLine();
        Pattern p = Pattern.compile("^target area: x=([^\\.]+)\\.\\.([^\\,]+), y=([^\\.]+)\\.\\.(.+)");
        Matcher m = p.matcher(s);
        Point p1, p2;

        if(m.find()) {
            p1 = new Point(Integer.parseInt(m.group(1)), Integer.parseInt(m.group(3)));
            p2 = new Point(Integer.parseInt(m.group(2)), Integer.parseInt(m.group(4)));
        }
        else
            throw new RuntimeException("Failed to parse pattern");

        ArrayList<Result> hits = new ArrayList<>();

        for(int x = 200; x > -200; x--) {
            for(int y = 200; y > -200; y--) {
                Result r = launch(p1, p2, x, y);
                if(r.isHit())
                    hits.add(r);
            }
        }


        return String.valueOf(hits.size());
    }

    private Result launch(Point target1, Point target2, int xvel, int yvel) {

        ArrayList<Point> trajectory = new ArrayList<>();
        Point probe = new Point(0, 0);

        for(int i = 1; i < 400; i++) {
            trajectory.add(new Point(probe));
            if(GridUtils.isWithinArea(target1, target2, probe, true)) {
                return new Result(true, new Point(probe), trajectory.stream().mapToInt(p -> p.y).max().getAsInt());
            }
            probe.x += xvel;
            probe.y += yvel;
            if(xvel > 0)
                xvel--;
            else if(xvel < 0)
                xvel ++;

            yvel--;
        }

        return new Result(false, new Point(0, 0), Integer.MIN_VALUE);
    }

}

class Result {

    private boolean isHit;
    private Point landing;
    private int maxY;

    public Result(boolean isHit, Point landing, int maxY) {

        this.isHit = isHit;
        this.landing = landing;
        this.maxY = maxY;
    }

    public boolean isHit() {
        return isHit;
    }

    public Point getLanding() {
        return landing;
    }

    public int getMaxY() {
        return maxY;
    }
}