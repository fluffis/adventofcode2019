package se.fluff.aoc2019.days;

import se.fluff.aoc.AocDay;

import java.awt.*;
import java.util.*;
import java.util.List;

/**
 * Created by Fluff on 2019-12-08.
 */
public class Day08 extends AocDay {

    public Day08() {

    }

    @Override
    public String a(Scanner in, boolean isTest) {

        int width = 25;
        int height = 6;
        int layer = 0;

        List<Integer> imagedata = new ArrayList<>();
        HashMap<Integer, HashMap<Point, Integer>> image = new HashMap<Integer, HashMap<Point, Integer>>();

        for(String s : in.nextLine().split(""))
            imagedata.add(Integer.parseInt(s));


        while(imagedata.size() > 0) {
            layer++;
            HashMap<Point, Integer> l = new HashMap<>();
            for(int h = 0; h < height; h++) {
                for (int w = 0; w < width; w++) {
                    Point p = new Point(h, w);
                    l.put(p, imagedata.remove(0));
                }
            }
            image.put(layer, l);
        }

        long zerocount = Integer.MAX_VALUE;
        int slayer = -1;

        for(Map.Entry<Integer, HashMap<Point, Integer>> l : image.entrySet()) {
            long zeros = l.getValue().values().stream().filter(x -> x == 0).count();
            if(zeros < zerocount) {
                zerocount = zeros;
                slayer = l.getKey();
            }
        }

        HashMap<Point, Integer> res = image.get(slayer);
        long output = res.values().stream().filter(x -> x == 1).count() * res.values().stream().filter(x -> x == 2).count();
        return String.valueOf(output);
    }

    @Override
    public String b(Scanner in, boolean isTest) {
        int width = 25;
        int height = 6;


        List<Integer> imagedata = new ArrayList<>();
        ArrayList<HashMap<Point, Integer>> image = new ArrayList<>();

        for(int pixel : Arrays.stream(in.nextLine().split("")).mapToInt(Integer::parseInt).toArray())
            imagedata.add(pixel);

        while(imagedata.size() > 0) {
            HashMap<Point, Integer> l = new HashMap<>();
            for(int h = 0; h < height; h++) {
                for (int w = 0; w < width; w++) {
                    l.put(new Point(w, h), imagedata.remove(0));
                }
            }
            image.add(l);
        }

        for(int h = 0; h < height; h++) {
            for(int w = 0; w < width; w++) {
                for(HashMap<Point, Integer> layer : image) {
                    int v = layer.get(new Point(w, h));
                    if(v != 2) {
                        if(v == 1)
                            System.out.print("█");
                        else
                            System.out.print(" ");
                        break;
                    }
                }
            }
            System.out.println();
        }

        return null;
    }
}
