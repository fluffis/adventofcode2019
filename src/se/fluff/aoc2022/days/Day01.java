package se.fluff.aoc2022.days;

import se.fluff.aoc.AocDay;
import java.util.*;

/**
 * Created by Fluff on 2022-12-01.
 */
public class Day01 extends AocDay {

    public Day01() {

    }

    @Override
    public String a(Scanner in, boolean isTest) throws Exception {
        ArrayList<Integer> elfs = new ArrayList<>();
        int e = 0;
        while(in.hasNext()) {
            String s = in.nextLine();
            if(s.length() == 0) {
                elfs.add(e);
                e = 0;
                continue;
            }
            e += Integer.parseInt(s);
        }
        elfs.add(e);

        return String.valueOf(elfs.stream().max(Comparator.comparingInt(i -> i)).get());

    }

    @Override
    public String b(Scanner in, boolean isTest) {
        ArrayList<Integer> elfs = new ArrayList<>();
        int e = 0;
        while(in.hasNext()) {
            String s = in.nextLine();
            if(s.length() == 0) {
                elfs.add(e);
                e = 0;
                continue;
            }
            e += Integer.parseInt(s);
        }
        elfs.add(e);

        return String.valueOf(elfs.stream().sorted(Comparator.reverseOrder()).limit(3).reduce(Integer::sum).get());
    }
}
