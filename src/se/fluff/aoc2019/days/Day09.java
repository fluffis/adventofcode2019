package se.fluff.aoc2019.days;

import se.fluff.aoc.AocDay;
import se.fluff.aoc2019.LongCodeMachine;

import java.util.*;
import java.util.concurrent.ArrayBlockingQueue;

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

        ArrayBlockingQueue<Long> inputs = new ArrayBlockingQueue<>(10000);
        ArrayBlockingQueue<Long> outputs = new ArrayBlockingQueue<>(10000);
        LongCodeMachine lcm = new LongCodeMachine(intcode, 1, inputs, outputs);
        inputs.add(1L);
        lcm.run();

        String output = "";
        try {
            output = String.valueOf(outputs.take());
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        return output;
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

        ArrayBlockingQueue<Long> inputs = new ArrayBlockingQueue<>(10000);
        ArrayBlockingQueue<Long> outputs = new ArrayBlockingQueue<>(10000);
        LongCodeMachine lcm = new LongCodeMachine(intcode, 1, inputs, outputs);
        inputs.add(2L);
        lcm.run();

        String output = "";
        try {
            output = String.valueOf(outputs.take());
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        return output;
    }
}



