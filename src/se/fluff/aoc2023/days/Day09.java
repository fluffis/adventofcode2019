package se.fluff.aoc2023.days;

import se.fluff.aoc.AocDay;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Created by Fluff on 2023-12-09.
 */
public class Day09 extends AocDay {

    public Day09() {

    }

    @Override
    public String a(Scanner in, boolean isTest) throws Exception {

        int sum = 0;

        while(in.hasNext()) {
            String l = in.nextLine();
            HashMap<Integer, List<Integer>> extras = new HashMap<>();
            List<Integer> numbers = Arrays.stream(l.split(" +")).map(Integer::parseInt).collect(Collectors.toList());
            extras.put(0, numbers);
            do {
                List<Integer> row = extras.get(extras.size() - 1);
                List<Integer> distances = new ArrayList<>();
                for(int i = 0; i < row.size() - 1; i++) {
                    distances.add(row.get(i + 1) - row.get(i));
                }
                extras.put(extras.size(), distances);
            }
            while(extras.get(extras.size() - 1).stream().anyMatch(n -> n != 0));

            for(int i = extras.size() - 1; i >= 0; i--) {
                List<Integer> row = extras.get(i);
                if(i == extras.size() - 1) {
                    row.add(0);
                }
                else {
                    row.add(row.get(row.size() - 1) + extras.get(i + 1).get(extras.get(i + 1).size() - 1));
                }
                extras.put(i, row);
            }

            sum += extras.get(0).get(extras.get(0).size() - 1);

        }

        return String.valueOf(sum);
    }

    @Override
    public String b(Scanner in, boolean isTest) throws Exception {
        int sum = 0;

        while(in.hasNext()) {
            String l = in.nextLine();
            HashMap<Integer, List<Integer>> extras = new HashMap<>();
            List<Integer> numbers = Arrays.stream(l.split(" +")).map(Integer::parseInt).collect(Collectors.toList());
            extras.put(0, numbers);
            do {
                List<Integer> row = extras.get(extras.size() - 1);
                List<Integer> distances = new ArrayList<>();
                for(int i = 0; i < row.size() - 1; i++) {
                    distances.add(row.get(i + 1) - row.get(i));
                }
                extras.put(extras.size(), distances);
            }
            while(extras.get(extras.size() - 1).stream().anyMatch(n -> n != 0));

            for(int i = extras.size() - 1; i >= 0; i--) {
                List<Integer> row = extras.get(i);
                if(i == extras.size() - 1) {
                    row.add(0, 0);
                }
                else {
                    row.add(0, row.get(0) - extras.get(i + 1).get(0));
                }
                extras.put(i, row);
            }


            sum += extras.get(0).get(0);

        }

        return String.valueOf(sum);
    }
}
