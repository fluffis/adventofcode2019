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
    public String a(Scanner in) {
        ArrayList<String> bi = new ArrayList<>();
        while(in.hasNext()) {
            bi.add(in.nextLine());
        }

        int maxbid = 0;
        for(String s : bi) {
            int bid = getSeatId(s);

            if(bid > maxbid)
                maxbid = bid;
        }

        return String.valueOf(maxbid);
    }

    @Override
    public String b(Scanner in) {
        ArrayList<String> bi = new ArrayList<>();
        while(in.hasNext()) {
            bi.add(in.nextLine());
        }
        int max = 0;
        ArrayList<Integer> seats = new ArrayList<>();
        for(String s : bi) {
            int seatId = getSeatId(s);
            seats.add(seatId);
            if(seatId > max)
                max = seatId;
        }
        for(int i = 1; i < max; i++) {
            if(!seats.contains(i) && seats.contains(i+1) && seats.contains(i-1))
                return String.valueOf(i);
        }
        return null;
    }

    private int getSeatId(String s) {
        s = s
                .replace("F", "0")
                .replace("L", "0")
                .replace("B", "1")
                .replace("R", "1");

        return Integer.parseInt(s, 2);
    }
}
