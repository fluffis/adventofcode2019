package se.fluff.aoc2021.days;

import se.fluff.aoc.AocDay;
import se.fluff.aoc.GridUtils;

import java.awt.*;
import java.util.*;

/**
 * Created by Fluff on 2021-12-13.
 */
public class Day13 extends AocDay {

    public Day13() {

    }

    @Override
    public String a(Scanner in, boolean isTest) throws Exception {
        HashMap<Point,Character> map = new HashMap<>();
        ArrayList<String> folds = new ArrayList<>();
        while(in.hasNext()) {
            String row = in.nextLine();
            if(row.startsWith("fold along")) {
                folds.add(row.replaceAll("fold along ", ""));
            }
            else if(!row.equals("")) {
                int[] c = Arrays.stream(row.split(",")).mapToInt(Integer::parseInt).toArray();
                map.put(new Point(c[0], c[1]), '#');
            }
        }

        String[] f = folds.get(0).split("=");
        int l = Integer.parseInt(f[1]);
        HashMap<Point, Character> foldmap = new HashMap<>();
        for (Point p : map.keySet()) {
            Point np = new Point(p);
            if (f[0].equals("y") && p.y > l)
                np.y = l - (p.y - l);
            else if(f[0].equals("x") && p.x > l)
                np.x = l - (p.x - l);

            foldmap.put(np, '#');
        }
        map = foldmap;


        return String.valueOf(map.size());
    }

    @Override
    public String b(Scanner in, boolean isTest) {
        HashMap<Point,Character> map = new HashMap<>();
        ArrayList<String> folds = new ArrayList<>();
        while(in.hasNext()) {
            String row = in.nextLine();
            if(row.startsWith("fold along")) {
                folds.add(row.replaceAll("fold along ", ""));
            }
            else if(!row.equals("")) {
                int[] c = Arrays.stream(row.split(",")).mapToInt(Integer::parseInt).toArray();
                map.put(new Point(c[0], c[1]), '#');
            }
        }

        for(String fold : folds) {
            String[] f = fold.split("=");
            int l = Integer.parseInt(f[1]);
            HashMap<Point, Character> foldmap = new HashMap<>();

            for (Point p : map.keySet()) {
                Point np = new Point(p);
                if (f[0].equals("y") && p.y > l)
                    np.y = l - (p.y - l);
                else if(f[0].equals("x") && p.x > l)
                    np.x = l - (p.x - l);

                foldmap.put(np, '#');
            }

            map = foldmap;
        }

        if(!isTest)
            GridUtils.printGrid(map, '.');
        return null;
    }
}
