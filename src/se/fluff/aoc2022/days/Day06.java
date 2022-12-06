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

        StringBuilder sb = new StringBuilder();
        int pos = 0;
        for(char c : l.toCharArray()) {
            pos++;
            sb.append(c);
            if(sb.length() < 4) {
                continue;
            }

            ArrayList<Character> crosscheck = new ArrayList<>();
            for (char d : sb.toString().toCharArray()) {
                if(!crosscheck.contains(d))
                    crosscheck.add(d);
            }

            if(crosscheck.size() == sb.length()) {
                break;
            }
            sb.replace(0, 1, "");
        }
        return String.valueOf(pos);
    }

    @Override
    public String b(Scanner in, boolean isTest) {
        String l = in.nextLine();

        StringBuilder sb = new StringBuilder();
        int pos = 0;
        for(char c : l.toCharArray()) {
            pos++;
            sb.append(c);
            if(sb.length() < 14) {
                continue;
            }

            ArrayList<Character> content = new ArrayList<>();
            for (char d : sb.toString().toCharArray()) {
                if(!content.contains(d))
                    content.add(d);
            }

            if(content.size() == sb.length()) {
                break;
            }
            sb.replace(0, 1, "");
        }
        return String.valueOf(pos);
    }
}
