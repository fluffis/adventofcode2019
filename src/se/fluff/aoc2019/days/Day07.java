package se.fluff.aoc2019.days;

import se.fluff.aoc.AocDay;
import se.fluff.aoc2019.IntCodeMachine;
import se.fluff.aoc2019.helpers.Permutations;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Created by Fluff on 2019-12-07.
 */
public class Day07 extends AocDay {

    public Day07() {

    }

    @Override
    public String a(Scanner in, boolean isTest) {

        int v = 0;
        HashMap<Integer, Integer> intcode = new HashMap<>();

        String line = in.nextLine();
        int i = 0;
        for(String nr : line.split(",")) {
            intcode.put(i, Integer.parseInt(nr));
            i++;
        }

        List<Stream<Integer>> combinations = Permutations.of(Arrays.asList(0, 1, 2, 3, 4)).collect(Collectors.toList());
        for(Stream<Integer> c : combinations) {
            int output = 0;
            for(int phase : c.collect(Collectors.toList())) {
                IntCodeMachine icm = new IntCodeMachine(intcode, phase);
                try {
                    icm.addInput(phase);
                    icm.addInput(output);
                    icm.run();
                    output = icm.read();
                }
                catch(Exception e) {
                    e.printStackTrace();
                }
            }

            if(output > v)
                v = output;
        }

        return String.valueOf(v);
    }

    @Override
    public String b(Scanner in, boolean isTest) {
        int v = 0;
        HashMap<Integer, Integer> intcode = new HashMap<>();

        String line = in.nextLine();
        int i = 0;
        for(String nr : line.split(",")) {
            intcode.put(i, Integer.parseInt(nr));
            i++;
        }

        List<Stream<Integer>> combinations = Permutations.of(Arrays.asList(5, 6, 7, 8, 9)).collect(Collectors.toList());
        for(Stream<Integer> c : combinations) {
            ArrayList<Integer> phases = new ArrayList<>(c.collect(Collectors.toList()));

            ArrayList<IntCodeMachine> amps = new ArrayList<>();
            for(int a = 0; a <= 4; a++) {
                IntCodeMachine icm = new IntCodeMachine(intcode, a);
                icm.addInput(phases.get(a));
                amps.add(icm);
            }

            int output = 0;

            List<Integer> outputlist = new ArrayList<>();
            int amp = 0;
            while(!amps.get(amps.size() - 1).isHalted()) {
                if(outputlist.size() > 0) {
                    for(int o : outputlist)
                        amps.get(amp).addInput(o);
                }
                else {
                    amps.get(amp).addInput(output);
                }

                amps.get(amp).run();
                outputlist = amps.get(amp).getOutputs();
                amp++;
                if(amp == 5)
                    amp = 0;
            }

            output = amps.get(amps.size() - 1).getAllSeenOutputs().stream().reduce(Integer::max).get();

            if(output > v)
                v = output;
        }

        return String.valueOf(v);
    }
}
