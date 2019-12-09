package se.fluff.aoc2019.days;

import se.fluff.aoc2019.AocDay;
import se.fluff.aoc2019.IntCodeMachine;
import se.fluff.aoc2019.LongCodeMachine;

import java.util.*;

/**
 * Created by Fluff on 2019-12-09.
 */
public class Day09 extends AocDay {

    public Day09() {

    }

    @Override
    public String a(Scanner in) {
        HashMap<Integer, Long> intcode = new HashMap<>();

        String line = in.nextLine();
        int i = 0;
        for(String nr : line.split(",")) {
            intcode.put(i, Long.parseLong(nr));
            i++;
        }
        LongCodeMachine lcm = new LongCodeMachine(intcode, 1);
        lcm.addInput(1L);
        lcm.run();

        List<Long> o = lcm.getOutputs();

        return String.valueOf(o.remove(0));
    }

    @Override
    public String b(Scanner in) {

        HashMap<Integer, Long> intcode = new HashMap<>();

        String line = in.nextLine();
        int i = 0;
        for(String nr : line.split(",")) {
            intcode.put(i, Long.parseLong(nr));
            i++;
        }
        LongCodeMachine lcm = new LongCodeMachine(intcode, 1);
        lcm.addInput(2L);
        lcm.run();

        List<Long> o = lcm.getOutputs();

        return String.valueOf(o.remove(0));
    }
}



