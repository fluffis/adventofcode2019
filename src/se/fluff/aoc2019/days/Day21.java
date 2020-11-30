package se.fluff.aoc2019.days;

import se.fluff.aoc.AocDay;
import se.fluff.aoc2019.LongCodeMachine;

import java.util.HashMap;
import java.util.Scanner;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.TimeUnit;

/**
 * Created by Fluff on 2019-12-21.
 */
public class Day21 extends AocDay {

    HashMap<Integer, Long> intcode = new HashMap<>();

    public Day21() {

    }

    @Override
    public String a(Scanner in) {

        String line = in.nextLine();
        int i = 0;
        for(String nr : line.split(",")) {
            intcode.put(i, Long.parseLong(nr));
            i++;
        }

        ArrayBlockingQueue<Long> inputs = new ArrayBlockingQueue<>(10000);
        ArrayBlockingQueue<Long> outputs = new ArrayBlockingQueue<>(10000);
        LongCodeMachine lcm = new LongCodeMachine(intcode, 1, inputs, outputs);

        lcm.addInputString("NOT A J");
        lcm.addInputString("NOT J J");
        lcm.addInputString("AND B J");
        lcm.addInputString("AND C J");
        lcm.addInputString("NOT J J");
        lcm.addInputString("AND D J");

        lcm.addInputString("WALK");
        lcm.run();
        while(!outputs.isEmpty()) {

            try {
                int res = outputs.poll(1, TimeUnit.DAYS).intValue();
                if(res < 256)
                    System.out.print((char) res);
                else
                    return String.valueOf(res);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        return null;
    }

    @Override
    public String b(Scanner in) {

        ArrayBlockingQueue<Long> inputs = new ArrayBlockingQueue<>(10000);
        ArrayBlockingQueue<Long> outputs = new ArrayBlockingQueue<>(10000);
        LongCodeMachine lcm = new LongCodeMachine(intcode, 1, inputs, outputs);

        lcm.addInputString("NOT C J");
        lcm.addInputString("AND H J");
        lcm.addInputString("NOT B T");
        lcm.addInputString("OR T J");
        lcm.addInputString("NOT A T");
        lcm.addInputString("OR T J");
        lcm.addInputString("AND D J");
        lcm.addInputString("RUN");
        lcm.run();
        while(!outputs.isEmpty()) {

            try {
                int res = outputs.poll(1, TimeUnit.DAYS).intValue();
                if(res < 256)
                    System.out.print((char) res);
                else
                    return String.valueOf(res);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        return null;
    }
}
