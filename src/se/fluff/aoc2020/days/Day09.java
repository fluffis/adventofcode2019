package se.fluff.aoc2020.days;

import se.fluff.aoc.AocDay;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Scanner;

/**
 * Created by Fluff on 2020-12-09.
 */
public class Day09 extends AocDay {

    public Day09() {

    }

    @Override
    public String a(Scanner in, boolean isTest) {
        ArrayList<Long> numbers = new ArrayList<>();
        while(in.hasNext())
            numbers.add(Long.parseLong(in.nextLine()));

        ArrayList<Long> preamble = new ArrayList<>();
        for(long n : numbers) {
            if(preamble.size() == 25) {
                boolean found = false;
                HashSet<Long> s = new HashSet<>();
                for (Long aLong : preamble) {
                    if (s.contains(n - aLong)) {
                        found = true;
                        break;
                    }
                    s.add(aLong);
                }
                if(!found)
                    return String.valueOf(n);
                preamble.remove(0);
            }
            preamble.add(n);
        }
        return null;
    }

    @Override
    public String b(Scanner in, boolean isTest) {
        long weak = -1;
        ArrayList<Long> numbers = new ArrayList<>();
        while(in.hasNext())
            numbers.add(Long.parseLong(in.nextLine()));

        ArrayList<Long> preamble = new ArrayList<>();
        for(long n : numbers) {
            if(preamble.size() == 25) {
                boolean found = false;
                HashSet<Long> s = new HashSet<>();
                for (Long aLong : preamble) {
                    if (s.contains(n - aLong)) {
                        found = true;
                        break;
                    }
                    s.add(aLong);
                }
                if(!found) {
                    weak = n;
                    break;
                }
                preamble.remove(0);
            }
            preamble.add(n);
        }

        ArrayList<Long> contiguous = new ArrayList<>();
        int i = 0;
        while(contiguous.stream().reduce(0L, Long::sum) != weak) {
            contiguous = new ArrayList<>();
            int j = i;

            while(contiguous.stream().reduce(0L, Long::sum) < weak)
                contiguous.add(numbers.get(j++));

            i++;
        }

        return String.valueOf(
                contiguous
                        .stream()
                        .min(Long::compare)
                        .get() +
                contiguous
                        .stream()
                        .max(Long::compare)
                        .get()
        );
    }
}
