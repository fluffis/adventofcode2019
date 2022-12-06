package se.fluff.aoc2022.days;

import se.fluff.aoc.AocDay;
import java.util.*;

/**
 * Created by Fluff on 2022-12-06.
 */
public class Day06 extends AocDay {

    public Day06() {

    }

    @Override
    public String a(Scanner in, boolean isTest) throws Exception {
        String l = in.nextLine();

        return String.valueOf(findUnique(l, 4));
    }

    @Override
    public String b(Scanner in, boolean isTest) {
        String l = in.nextLine();

        return String.valueOf(findUnique(l, 14));
    }

    public int findUnique(String l, int len) {
        String s = "";
        int pos = 0;
        for(char c : l.toCharArray()) {
            pos++;
            s += c;
            if(s.length() < len) {
                continue;
            }

            HashSet<String> cc2 = new HashSet<>(List.of(s.split("")));
            if(cc2.size() == s.length()) {
                break;
            }
            s = s.replaceFirst("^.", "");
        }
        return pos;
    }
}
