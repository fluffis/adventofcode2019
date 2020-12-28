package se.fluff.aoc2020.days;

import se.fluff.aoc.AocDay;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

/**
 * Created by Fluff on 2020-12-08.
 */
public class Day08 extends AocDay {

    public Day08() {

    }

    @Override
    public String a(Scanner in, boolean isTest) {
        long acc = 0L;
        ArrayList<Instruction> instructions = new ArrayList<>();
        ArrayList<Integer> accessedInstructions = new ArrayList<>();
        while(in.hasNext()) {
            String[] s = in.nextLine().split(" ");
            instructions.add(new Instruction(s[0], Integer.parseInt(s[1])));
        }

        int i = 0;
        while(true) {
            Instruction instr = instructions.get(i);

            if(accessedInstructions.contains(i))
                return String.valueOf(acc);

            accessedInstructions.add(i);
            System.out.println("At instr " + i + " " + instr.getType() + " we have has " + acc);

            if(instr.getType().equals("acc")) {
                acc += instr.getLength();
                i++;
            }
            else if(instr.getType().equals("jmp")) {
                i += instr.getLength();
            }
            else
                i++;
        }

    }

    @Override
    public String b(Scanner in, boolean isTest) {
        ArrayList<Instruction> instructions = new ArrayList<>();

        while(in.hasNext()) {
            String[] s = in.nextLine().split(" ");
            instructions.add(new Instruction(s[0], Integer.parseInt(s[1])));
        }

        for(int i = 0; i < instructions.size(); i++) {
            List<Instruction> ic = instructions.stream().map(Instruction::new).collect(Collectors.toList());
            if(ic.get(i).getType().equals("nop") && ic.get(i).getLength() != 0)
                ic.get(i).setType("jmp");
            else if(ic.get(i).getType().equals("jmp"))
                ic.get(i).setType("nop");
            else
                continue;

            try {
                long acc = tryLoop(ic);
                return String.valueOf(acc);
            }
            catch(InfiniteLoopException e) {
                System.err.println("Switching instr " + i + " failed");
            }
        }
        return null;
    }

    private long tryLoop(List<Instruction> instructions) throws InfiniteLoopException {

        long acc = 0L;
        ArrayList<Integer> accessedInstructions = new ArrayList<>();
        int i = 0;
        while(i < instructions.size()) {
            Instruction instr = instructions.get(i);

            if(accessedInstructions.contains(i))
                throw new InfiniteLoopException();

            accessedInstructions.add(i);

            if(instr.getType().equals("acc")) {
                acc += instr.getLength();
                i++;
            }
            else if(instr.getType().equals("jmp")) {
                i += instr.getLength();
            }
            else
                i++;
        }
        return acc;
    }
}

class Instruction {
    private String type;
    private int length;

    public Instruction(String type, int length) {
        this.type = type;
        this.length = length;
    }

    public Instruction(Instruction i2) {
        this.type = i2.getType();
        this.length = i2.getLength();
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public int getLength() {
        return length;
    }

    public void setLength(int length) {
        this.length = length;
    }
}

class InfiniteLoopException extends Exception {

}