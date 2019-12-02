package se.fluff.aoc2019.days;

import se.fluff.aoc2019.AocDay;

import java.util.HashMap;
import java.util.Scanner;

/**
 * Created by Fluff on 2019-12-01.
 */
public class Day02 extends AocDay {

    public Day02() {

    }

    @Override
    public String a(Scanner in) {

        HashMap<Integer, Integer> intcode = new HashMap<>();

        String line = in.nextLine();
        int i = 0;
        for(String nr : line.split(",")) {
            intcode.put(i, Integer.parseInt(nr));
            i++;
        }

        intcode.put(1, 12);
        intcode.put(2, 2);

        int steppos = 0;
        while(intcode.get(steppos) != 99) {
            int opcode = intcode.get(steppos);
            int opval1 = intcode.get(steppos + 1);
            int opval2 = intcode.get(steppos + 2);
            int storeat = intcode.get(steppos + 3);

            if(opcode == 1) {
                intcode.put(storeat, intcode.get(opval1) + intcode.get(opval2));
            }
            else if(opcode == 2) {
                intcode.put(storeat, intcode.get(opval1) * intcode.get(opval2));
            }

            steppos += 4;
        }

        return Integer.toString(intcode.get(0));
    }

    @Override
    public String b(Scanner in) {

        HashMap<Integer, Integer> ointcode = new HashMap<>();

        String line = in.nextLine();
        int i = 0;
        for(String nr : line.split(",")) {
            ointcode.put(i, Integer.parseInt(nr));
            i++;
        }

        int target = 19690720;
        for(int noun = 0; noun < 99; noun++) {
            for(int verb = 0; verb < 99; verb++) {
                HashMap<Integer, Integer> intcode = new HashMap<>(ointcode);
                intcode.put(1, noun);
                intcode.put(2, verb);

                int steppos = 0;
                while(intcode.get(steppos) != 99) {
                    int opcode = intcode.get(steppos);
                    int opval1 = intcode.get(steppos + 1);
                    int opval2 = intcode.get(steppos + 2);
                    int storeat = intcode.get(steppos + 3);

                    if(opcode == 1) {
                        intcode.put(storeat, intcode.get(opval1) + intcode.get(opval2));
                    }
                    else if(opcode == 2) {
                        intcode.put(storeat, intcode.get(opval1) * intcode.get(opval2));
                    }

                    steppos += 4;
                }

                if(intcode.get(0) == target)
                    return Integer.toString(100 * noun + verb);

            }
        }
        return null;
    }
}
