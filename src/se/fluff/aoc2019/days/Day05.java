package se.fluff.aoc2019.days;

import se.fluff.aoc2019.AocDay;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Scanner;
import java.util.stream.Collectors;

/**
 * Created by Fluff on 2019-12-05.
 */
public class Day05 extends AocDay {

    public static int POSM = 0;
    public static int IMMM = 1;

    public Day05() {

    }

    @Override
    public String a(Scanner in) {

        int input = 1;
        HashMap<Integer, Integer> intcode = new HashMap<>();

        ArrayList<Integer> output = new ArrayList<>();

        String line = in.nextLine();
        int i = 0;
        for(String nr : line.split(",")) {
            intcode.put(i, Integer.parseInt(nr));
            i++;
        }

        int steppos = 0;
        while(intcode.get(steppos) != 99) {
            int step = 0;
            int opcode = intcode.get(steppos);

            String opcodes = String.format("%05d", opcode);
            System.out.println(steppos + " " + opcodes);
            opcode = Integer.parseInt(opcodes.substring(opcodes.length() - 2));
            int[] paramMode = new int[opcodes.length() - 2];
            for(int c = opcodes.length() - 3; c >= 0; c--) {
                String pp = opcodes.substring(c, c + 1);
                int p = Integer.parseInt(pp);
                paramMode[c] = p;
            }
            if(opcode == 1) {
                int opval1 = intcode.get(steppos + 1);
                int opval2 = intcode.get(steppos + 2);
                int storeat = intcode.get(steppos + 3);
                int v1 = paramMode[2] == 0 ? intcode.getOrDefault(opval1, 0) : opval1;
                int v2 = paramMode[1] == 0 ? intcode.getOrDefault(opval2, 0) : opval2;
                intcode.put(storeat, v1 + v2);
                step = 4;
            }
            else if(opcode == 2) {
                int opval1 = intcode.get(steppos + 1);
                int opval2 = intcode.get(steppos + 2);
                int storeat = intcode.get(steppos + 3);
                int v1 = paramMode[2] == 0 ? intcode.getOrDefault(opval1,0) : opval1;
                int v2 = paramMode[1] == 0 ? intcode.getOrDefault(opval2, 0) : opval2;
                intcode.put(storeat, v1 * v2);
                step = 4;
            }
            else if(opcode == 3) {
                int opval1 = intcode.get(steppos + 1);
                if (paramMode[2] == 0)
                    intcode.put(opval1, input);
                else
                    intcode.put(opval1, opval1);

                step = 2;
            }
            else if(opcode == 4) {
                int opval1 = intcode.get(steppos + 1);
                if(paramMode[2] == 0)
                    input = intcode.get(opval1);
                else
                    input = opval1;
                step = 2;
                if(intcode.get(steppos + step) % 100 == 99)
                    System.out.println("D: " + input);
            }

            steppos += step;
        }

        return output.stream().map(Object::toString)
                .collect(Collectors.joining(""));
    }

    @Override
    public String b(Scanner in) {

        int input = 5;
        HashMap<Integer, Integer> intcode = new HashMap<>();

        ArrayList<Integer> output = new ArrayList<>();

        String line = in.nextLine();
        int i = 0;
        for(String nr : line.split(",")) {
            intcode.put(i, Integer.parseInt(nr));
            i++;
        }

        int steppos = 0;
        while(intcode.get(steppos) != 99) {
            int step = 0;
            int opcode = intcode.get(steppos);

            String opcodes = String.format("%05d", opcode);
            opcode = Integer.parseInt(opcodes.substring(opcodes.length() - 2));
            int[] paramMode = new int[opcodes.length() - 2];
            for(int c = opcodes.length() - 3; c >= 0; c--) {
                String pp = opcodes.substring(c, c + 1);
                int p = Integer.parseInt(pp);
                paramMode[c] = p;
            }
            if(opcode == 1) {
                int opval1 = intcode.get(steppos + 1);
                int opval2 = intcode.get(steppos + 2);
                int storeat = intcode.get(steppos + 3);
                int v1 = paramMode[2] == 0 ? intcode.getOrDefault(opval1, 0) : opval1;
                int v2 = paramMode[1] == 0 ? intcode.getOrDefault(opval2, 0) : opval2;
                intcode.put(storeat, v1 + v2);
                steppos += 4;
            }
            else if(opcode == 2) {
                int opval1 = intcode.get(steppos + 1);
                int opval2 = intcode.get(steppos + 2);
                int storeat = intcode.get(steppos + 3);
                int v1 = paramMode[2] == 0 ? intcode.getOrDefault(opval1,0) : opval1;
                int v2 = paramMode[1] == 0 ? intcode.getOrDefault(opval2, 0) : opval2;
                intcode.put(storeat, v1 * v2);
                steppos += 4;
            }
            else if(opcode == 3) {
                int opval1 = intcode.get(steppos + 1);
                if (paramMode[2] == 0)
                    intcode.put(opval1, input);
                else
                    intcode.put(opval1, opval1);

                steppos += 2;
            }
            else if(opcode == 4) {
                int opval1 = intcode.get(steppos + 1);
                if(paramMode[2] == 0)
                    input = intcode.get(opval1);
                else
                    input = opval1;
                steppos += 2;
                if(intcode.get(steppos + step) % 100 == 99)
                    System.out.println("D: " + input);
                else
                    System.out.println("Test: " + input);
            }
            else if(opcode == 5) {
                int opval1 = intcode.get(steppos + 1);
                int opval2 = intcode.get(steppos + 2);
                int v1 = paramMode[2] == 0 ? intcode.getOrDefault(opval1, 0) : opval1;
                int v2 = paramMode[1] == 0 ? intcode.getOrDefault(opval2, 0) : opval2;
                if(v1 != 0)
                    steppos = v2;
                else
                    steppos += 3;
            }
            else if(opcode == 6) {
                int opval1 = intcode.get(steppos + 1);
                int opval2 = intcode.get(steppos + 2);
                int v1 = paramMode[2] == 0 ? intcode.getOrDefault(opval1, 0) : opval1;
                int v2 = paramMode[1] == 0 ? intcode.getOrDefault(opval2, 0) : opval2;
                if(v1 == 0)
                    steppos = v2;
                else
                    steppos += 3;
            }
            else if(opcode == 7) {
                int opval1 = intcode.get(steppos + 1);
                int opval2 = intcode.get(steppos + 2);
                int opval3 = intcode.get(steppos + 3);
                int v1 = paramMode[2] == 0 ? intcode.get(opval1) : opval1;
                int v2 = paramMode[1] == 0 ? intcode.get(opval2) : opval2;
                intcode.put(opval3, v1 < v2 ? 1 : 0);
                steppos += 4;
            }
            else if(opcode == 8) {
                int opval1 = intcode.get(steppos + 1);
                int opval2 = intcode.get(steppos + 2);
                int opval3 = intcode.get(steppos + 3);
                int v1 = paramMode[2] == 0 ? intcode.getOrDefault(opval1, 0) : opval1;
                int v2 = paramMode[1] == 0 ? intcode.getOrDefault(opval2, 0) : opval2;
                intcode.put(opval3, v1 == v2 ? 1 : 0);
                steppos += 4;
            }

        }

        return output.stream().map(Object::toString)
                .collect(Collectors.joining(""));
    }
}
