package se.fluff.aoc2021.days;

import se.fluff.aoc.AocDay;

import java.util.ArrayList;
import java.util.Scanner;

public class Day01 extends AocDay {
    @Override
    public String a(Scanner in, boolean isTest) throws Exception {
        ArrayList<Integer> numbers = new ArrayList<>();
        while(in.hasNext()) {
            int number = Integer.parseInt(in.nextLine());
            numbers.add(number);
        }

        int increases = 0;
        int lastval = numbers.get(0);
        for (int number : numbers) {
            if(number > lastval)
                increases++;
            lastval = number;
        }
        return String.valueOf(increases);
    }

    @Override
    public String b(Scanner in, boolean isTest) {
        ArrayList<Integer> numbers = new ArrayList<>();
        while(in.hasNext()) {
            int number = Integer.parseInt(in.nextLine());
            numbers.add(number);
        }

        int increases = 0;
        int lastval = numbers.get(0) + numbers.get(1) + numbers.get(2);
        for(int i = 0; i < (numbers.size() - 2); i++) {
            if(numbers.get(i) + numbers.get(i + 1) + numbers.get(i + 2) > lastval)
                increases++;
            lastval = numbers.get(i) + numbers.get(i + 1) + numbers.get(i + 2);
        }
        return String.valueOf(increases);
    }
}
