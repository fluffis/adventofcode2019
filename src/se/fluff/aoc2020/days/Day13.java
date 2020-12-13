package se.fluff.aoc2020.days;

import se.fluff.aoc.AocDay;

import java.math.BigInteger;
import java.util.*;
import java.util.stream.Stream;

/**
 * Created by Fluff on 2020-12-13.
 */
public class Day13 extends AocDay {

    public Day13() {

    }

    @Override
    public String a(Scanner in) {
        long ts = Long.parseLong(in.nextLine());
        String[] schedule = in.nextLine().split(",");

        HashMap<Long, Long> nextdepartures = new HashMap<>();
        for(String s : schedule) {

            if(s.equals("x"))
                continue;

            long busid = Long.parseLong(s);
            long t = (long) Math.floor(ts/busid) + 1;
            long next = busid * t;

            nextdepartures.put(busid, next);

        }

        Map.Entry<Long, Long> e = nextdepartures.entrySet().stream().min(Comparator.comparingLong(Map.Entry::getValue)).get();

        return String.valueOf(e.getKey() * (e.getValue() - ts));
    }

    @Override
    public String b(Scanner in) {
        long ts = Long.parseLong(in.nextLine());
        String[] schedule = in.nextLine().split(",");

        HashMap<Integer, BigInteger> busses = new HashMap<>();
        for(int i = 0; i < schedule.length; i++) {
            if(schedule[i].equals("x"))
                continue;
            busses.put(i, new BigInteger(schedule[i]));
        }

        BigInteger i = new BigInteger("" + ts);
        while(!checkAllBusses(i, busses)) {

            BigInteger ti = i;
            BigInteger prod = busses
                    .entrySet()
                    .stream()
                    .filter(e -> checkBus(ti, e.getKey(), e.getValue()))
                    .map(Map.Entry::getValue)
                    .reduce(BigInteger.ONE, (bus, p) -> p.multiply(bus));
            i = i.add(prod);
        }

        return i.toString();
    }

    private boolean checkAllBusses(BigInteger ts, HashMap<Integer, BigInteger> busses) {
        return busses
                .entrySet()
                .stream()
                .allMatch(e -> checkBus(ts, e.getKey(), e.getValue()));
    }

    private boolean checkBus(BigInteger ts, int mod, BigInteger bus) {
        return ts
                .add(new BigInteger("" + mod))
                .mod(bus)
                .equals(BigInteger.ZERO);
    }
}
