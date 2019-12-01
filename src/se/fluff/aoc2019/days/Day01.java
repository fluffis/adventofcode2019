package se.fluff.aoc2019.days;

import se.fluff.aoc2019.AocDay;

import java.util.Scanner;

/**
 * Created by Fluff on 2019-12-01.
 */
public class Day01 extends AocDay {

    public Day01() {

    }

    @Override
    public String a(Scanner in) {
        int totalFuel = 0;

        while(in.hasNext()) {
            int fuel = Integer.parseInt(in.nextLine());
            fuel = Math.round(fuel / 3) - 2;
            totalFuel += fuel;
        }
        return Integer.toString(totalFuel);
    }

    @Override
    public String b(Scanner in) {
        int totalFuel = 0;

        while(in.hasNext()) {
            int fuel = Integer.parseInt(in.nextLine());
            while(fuel > 0) {
                fuel = Math.round(fuel / 3) - 2;
                if(fuel > 0)
                    totalFuel += fuel;
            }
        }
        return Integer.toString(totalFuel);
    }
}
