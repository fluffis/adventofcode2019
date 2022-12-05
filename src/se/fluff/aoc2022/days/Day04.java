package se.fluff.aoc2022.days;

import se.fluff.aoc.AocDay;
import java.util.*;

/**
 * Created by Fluff on 2022-12-04.
 */
public class Day04 extends AocDay {

    public Day04() {

    }

    @Override
    public String a(Scanner in, boolean isTest) throws Exception {

        int contained = 0;
        while(in.hasNext()) {
            String[] l = in.nextLine().split(",");
            Assignment e1 = new Assignment(l[0]);
            Assignment e2 = new Assignment(l[1]);
            if(e1.contains(e2) || e2.contains(e1)) {
                contained++;
            }
        }
        return String.valueOf(contained);
    }

    @Override
    public String b(Scanner in, boolean isTest) {

        int overlaps = 0;
        while(in.hasNext()) {
            String[] l = in.nextLine().split(",");
            Assignment elf1 = new Assignment(l[0]);
            Assignment elf2 = new Assignment(l[1]);
            if(elf1.overlaps(elf2) || elf2.overlaps(elf1)) {
                overlaps++;
            }
        }
        return String.valueOf(overlaps);
    }
}

class Assignment {

    private final int start;
    private final int stop;
    public Assignment(String a) {
        String[] pos = a.split("-");
        this.start = Integer.parseInt(pos[0]);
        this.stop = Integer.parseInt(pos[1]);
    }

    public boolean contains(Assignment other) {
        return other.start >= this.start && other.start <= this.stop && this.stop >= other.stop && this.start <= other.stop;
    }

    public boolean overlaps(Assignment other) {
        return other.start >= this.start && other.start <= this.stop || this.stop >= other.stop && this.start <= other.stop;
    }

    @Override
    public String toString() {
        return "Assignment{" +
                "start=" + start +
                ", stop=" + stop +
                '}';
    }
}