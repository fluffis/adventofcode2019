package se.fluff.aoc2019.days;

import se.fluff.aoc2019.AocDay;

import java.util.Arrays;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by Fluff on 2019-12-04.
 */
public class Day04 extends AocDay {

    public Day04() {

    }

    @Override
    public String a(Scanner in) {
        String range = in.nextLine();
        String[] ranges = range.split("-");

        int matchesfound = 0;
        for(int i = Integer.parseInt(ranges[0]); i <= Integer.parseInt(ranges[1]); i++) {

            String nr = Integer.toString(i);
            String[] nrs = nr.split("");
            String[] sorted = nrs.clone();
            Arrays.sort(sorted);
            if(!Arrays.equals(nrs, sorted))
                continue;

            Pattern p = Pattern.compile("(\\w)\\1");
            Matcher m = p.matcher(nr);
            if (!m.find())
                continue;

            matchesfound++;
        }

        return Integer.toString(matchesfound);
    }

    @Override
    public String b(Scanner in) {
        String range = in.nextLine();
        String[] ranges = range.split("-");

        int matchesfound = 0;
        for(int i = Integer.parseInt(ranges[0]); i <= Integer.parseInt(ranges[1]); i++) {

            boolean twoequal = false;
            String nr = Integer.toString(i);
            String[] nrs = nr.split("");
            String[] sorted = nrs.clone();
            Arrays.sort(sorted);
            if(!Arrays.equals(nrs, sorted))
                continue;

            Pattern p = Pattern.compile("(\\w)\\1*");
            Matcher m = p.matcher(nr);

            while(m.find()) {
                if(m.end() - m.start() == 2)
                    twoequal = true;
            }

            if(twoequal)
                matchesfound++;

        }



        return Integer.toString(matchesfound);
    }
}
