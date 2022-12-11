package se.fluff.aoc2022.days;

import se.fluff.aoc.AocDay;
import se.fluff.aoc.GridUtils;

import java.awt.*;
import java.util.*;

/**
 * Created by Fluff on 2022-12-10.
 */
public class Day10 extends AocDay {

    public Day10() {

    }

    @Override
    public String a(Scanner in, boolean isTest) throws Exception {
        int cycle = 0;
        int xreg = 1;
        int ssi = 0;

        while(in.hasNext()) {
            String instr = in.nextLine();
            int wait = instr.equals("noop") ? 1 : 2;
            while(wait > 0) {
                cycle++;

                if((cycle - 20) % 40 == 0) {
                    ssi += cycle * xreg;
                }

                wait--;
            }
            if(instr.startsWith("addx")) {
                xreg += Integer.parseInt(instr.split(" ")[1]);
            }
        }

        return String.valueOf(ssi);
    }

    @Override
    public String b(Scanner in, boolean isTest) {
        int cycle = 0;
        int xreg = 1;
        HashMap<Point, Character> display = new HashMap<>();

        while(in.hasNext()) {
            String instr = in.nextLine();
            int wait = instr.equals("noop") ? 1 : 2;
            while(wait > 0) {
                int x = cycle % 40;
                int y = cycle/40;
                display.put(new Point(x, y), Math.abs(xreg - x) <= 1 ? '█' : ' ');
                cycle++;
                wait--;
            }
            if(instr.startsWith("addx")) {
                xreg += Integer.parseInt(instr.split(" ")[1]);
            }
        }

        GridUtils.printGrid(display, '.');
        return null;
    }
}