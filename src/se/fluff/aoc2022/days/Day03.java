package se.fluff.aoc2022.days;

import se.fluff.aoc.AocDay;
import java.util.*;

/**
 * Created by Fluff on 2022-12-03.
 */
public class Day03 extends AocDay {

    public Day03() {

    }

    @Override
    public String a(Scanner in, boolean isTest) throws Exception {

        int priority = 0;

        while(in.hasNext()) {
            String items = in.nextLine();
            String c1 = items.substring(0, (items.length())/2);
            String c2 = items.substring((items.length())/2);
            for(char f : c1.toCharArray()) {
                if(c2.indexOf(f) != -1) {
                    priority += (int) f - (Character.isLowerCase(f) ? 96 : 38);
                    break;
                }
            }
        }

        return String.valueOf(priority);
    }

    @Override
    public String b(Scanner in, boolean isTest) {
        int priority = 0;

        while(in.hasNext()) {
            String r1 = in.nextLine();
            String r2 = in.nextLine();
            String r3 = in.nextLine();
            char[] first = new char[1];
            for(int i = 0; i < r1.length(); i++) {
                r1.getChars(i, i + 1, first, 0);
                if(r2.indexOf(first[0]) != -1 && r3.indexOf(first[0]) != -1) {
                    priority += (int) first[0] - (Character.isLowerCase(first[0]) ? 96 : 38);
                    break;
                }
            }

        }

        return String.valueOf(priority);
    }
}

