package se.fluff.aoc2020.days;

import se.fluff.aoc.AocDay;

import java.util.*;

/**
 * Created by Fluff on 2020-12-10.
 */
public class Day10 extends AocDay {

    public Day10() {

    }

    @Override
    public String a(Scanner in, boolean isTest) {
        ArrayList<Integer> adapters = new ArrayList<>();
        HashMap<Integer,Integer> diffs = new HashMap<>();
        while(in.hasNext())
            adapters.add(Integer.parseInt(in.nextLine()));

        int rating = adapters.stream().max(Integer::compare).get() + 3;
        adapters.add(rating);
        Collections.sort(adapters);

        int cint = 0;

        for(int i = 0; i <= rating; i++) {
            if(adapters.contains(i)) {
                diffs.put(i - cint, diffs.getOrDefault(i - cint, 0) + 1);
                cint = i;
            }
        }

        return String.valueOf(diffs.get(1) * diffs.get(3));
    }

    @Override
    public String b(Scanner in, boolean isTest) {
        ArrayList<Integer> adapters = new ArrayList<>();

        while(in.hasNext())
            adapters.add(Integer.parseInt(in.nextLine()));

        int rating = adapters.stream().max(Integer::compare).get() + 3;
        adapters.add(rating);
        Collections.sort(adapters);

        HashMap<Integer, Long> dp = new HashMap<>();
        dp.put(0, 1L);

        for(int adapter : adapters) {
            dp.put(adapter, 0L);
            for(int i = 1; i <= 3; i++) {
                if (dp.containsKey(adapter - i))
                    dp.put(adapter, dp.get(adapter) + dp.get(adapter - i));
            }
        }

        return String.valueOf(dp.get(rating));
    }
}
