package se.fluff.aoc2021.days;

import se.fluff.aoc.AocDay;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Created by Fluff on 2021-12-07.
 */
public class Day07 extends AocDay {

    public Day07() {

    }

    @Override
    public String a(Scanner in, boolean isTest) throws Exception {
        List<Long> positions = Arrays.stream(in.nextLine().split(",")).mapToLong(Long::parseLong).boxed().collect(Collectors.toList());

        long min = positions.stream().min(Long::compare).get();
        long max = positions.stream().max(Long::compare).get();
        HashMap<Long, Long> fuelcost = new HashMap<>();
        for(long i = min; i <= max; i++) {
            long finalI = i;
            long fuel = positions.stream()
                    .map(p -> Math.abs(finalI - p))
                    .reduce(Long::sum)
                    .get();
            fuelcost.put(i, fuel);
        }

        return String.valueOf(fuelcost.values().stream().min(Long::compare).get());
    }

    @Override
    public String b(Scanner in, boolean isTest) {
        List<Long> positions = Arrays.stream(in.nextLine().split(",")).mapToLong(Long::parseLong).boxed().collect(Collectors.toList());

        long min = positions.stream().min(Long::compare).get();
        long max = positions.stream().max(Long::compare).get();
        HashMap<Long, Long> fuelcost = new HashMap<>();
        for(long i = min; i <= max; i++) {
            long finalI = i;
            long fuel = positions.stream()
                    .map(p -> (Math.abs(finalI - p) * (Math.abs(finalI - p) + 1))/2)
                    .reduce(Long::sum)
                    .get();
            fuelcost.put(i, fuel);
        }

        return String.valueOf(fuelcost.values().stream().min(Long::compare).get());
    }
}
