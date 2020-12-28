package se.fluff.aoc2020.days;

import se.fluff.aoc.AocDay;

import java.util.ArrayList;
import java.util.Scanner;

/**
 * Created by Fluff on 2020-12-05.
 */
public class Day05 extends AocDay {

    public Day05() {

    }

    @Override
    public String a(Scanner in, boolean isTest) {
        ArrayList<String> bi = new ArrayList<>();
        while(in.hasNext()) {
            bi.add(in.nextLine());
        }

        int max = 0;
        for(String s : bi) {
            int bid = getSeatId(s);

            if(bid > max)
                max = bid;
        }

        return String.valueOf(max);
    }

    @Override
    public String b(Scanner in, boolean isTest) {
        ArrayList<String> bi = new ArrayList<>();
        while(in.hasNext()) {
            bi.add(in.nextLine());
        }

        ArrayList<Integer> seats = new ArrayList<>();
        for(String s : bi)
            seats.add(getSeatId(s));

        for(int i = 1; i < seats.stream().max(Integer::compare).get(); i++) {
            if(!seats.contains(i) && seats.contains(i + 1) && seats.contains(i - 1))
                return String.valueOf(i);
        }
        return null;
    }

    private int getSeatId(String s) {
        return Integer.parseInt(s
                .replaceAll("[FL]", "0")
                .replaceAll("[BR]", "1"), 2);
    }
}
