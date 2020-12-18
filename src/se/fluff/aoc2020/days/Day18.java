package se.fluff.aoc2020.days;

import se.fluff.aoc.AocDay;

import java.util.ArrayList;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by Fluff on 2020-12-18.
 */
public class Day18 extends AocDay {

    public Day18() {

    }

    @Override
    public String a(Scanner in) {

        ArrayList<String> lines = new ArrayList<>();
        while(in.hasNext())
            lines.add(in.nextLine());

        long sum = 0L;
        for(String l : lines) {
            while(l.contains("("))
                l = solveParentesis(l, 1);

            sum += Long.parseLong(solveLeftToRight(l));
        }
        return String.valueOf(sum);
    }

    @Override
    public String b(Scanner in) {
        ArrayList<String> lines = new ArrayList<>();
        while(in.hasNext())
            lines.add(in.nextLine());

        long sum = 0L;
        for(String l : lines) {

            while(l.contains("("))
                l = solveParentesis(l, 2);

            l = solveMulti(solveAdd(l));
            sum += Long.parseLong(l);
        }
        return String.valueOf(sum);
    }

    private String solveParentesis(String s, int solveMethod) {
        Pattern p = Pattern.compile("\\(([^()]+)\\)");
        Matcher m = p.matcher(s);
        while(m.find()) {
            if(solveMethod == 1)
                s = s.replace(m.group(0), solveLeftToRight(m.group(1)));
            else
                s = s.replace(m.group(0), solveMulti(solveAdd(m.group(1))));
        }
        return s;
    }

    private String solveLeftToRight(String s) {

        Pattern p = Pattern.compile("^(\\d+) (.) (\\d+)");
        Matcher m = p.matcher(s);

        while (m.find()) {
            long l1 = Long.parseLong(m.group(1));
            long l2 = Long.parseLong(m.group(3));
            long sum = 0L;

            switch (m.group(2)) {
                case "+":
                    sum = l1 + l2;
                    break;
                case "*":
                    sum = l1 * l2;
                    break;
            }
            s = s.replaceFirst(p.pattern(), String.valueOf(sum));
            m = p.matcher(s);
        }

        return s;
    }

    private String solveAdd(String s) {
        Pattern p = Pattern.compile("(\\d+) (\\+) (\\d+)");
        Matcher m = p.matcher(s);

        while (m.find()) {
            long sum = Long.parseLong(m.group(1)) + Long.parseLong(m.group(3));
            s = s.replaceFirst(p.pattern(), String.valueOf(sum));
            m = p.matcher(s);
        }

        return s;
    }

    private String solveMulti(String s) {
        Pattern p = Pattern.compile("(\\d+) (\\*) (\\d+)");
        Matcher m = p.matcher(s);

        while (m.find()) {
            long sum = Long.parseLong(m.group(1)) * Long.parseLong(m.group(3));
            s = solveMulti(s.replaceFirst(p.pattern(), String.valueOf(sum)));
        }

        return s;
    }
}
