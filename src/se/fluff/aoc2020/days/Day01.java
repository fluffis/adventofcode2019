package se.fluff.aoc2020.days;

import se.fluff.aoc.AocDay;

import java.util.ArrayList;
import java.util.Scanner;

/**
 * Created by Fluff on 2020-12-01.
 */
public class Day01 extends AocDay {

    public Day01() {

    }

    @Override
    public String a(Scanner in) {

        ArrayList<Integer> numbers = new ArrayList<>();
        while(in.hasNext()) {
            int number = Integer.parseInt(in.nextLine());
            numbers.add(number);
        }

        for(int n : numbers) {
            for(int m : numbers) {
                if(n + m == 2020 && n != m)
                    return Integer.toString(n * m);
            }
        }
        return "No match";
    }

    @Override
    public String b(Scanner in) {
        ArrayList<Integer> numbers = new ArrayList<>();
        while(in.hasNext()) {
            int number = Integer.parseInt(in.nextLine());
            numbers.add(number);
        }

        for(int n : numbers) {
            for(int m : numbers) {
                for(int l : numbers) {
                    if(n + m + l == 2020)
                        return Integer.toString(n * m * l);
                }
            }
        }
        return "No match";
    }
}
