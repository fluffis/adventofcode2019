package se.fluff.aoc2020.days;

import se.fluff.aoc.AocDay;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 * Created by Fluff on 2020-12-19.
 */
public class Day19 extends AocDay {

    HashMap<Integer, String> lookup = new HashMap<>();
    ArrayList<String> messages = new ArrayList<>();
    public Day19() {

    }

    @Override
    public String a(Scanner in) {

        while(in.hasNext()) {
            String s = in.nextLine();
            if(s.contains(":")) {
                String[] d = s.split(": ");
                lookup.put(Integer.parseInt(d[0]), d[1]);
            }
            else if(s.matches("^.+"))
                messages.add(s);

        }

        String rs = rulesToString(0);
        return String.valueOf(messages.stream().filter(s -> s.matches("^" + rs + "$")).count());
    }

    @Override
    public String b(Scanner in) {
        lookup = new HashMap<>();
        messages = new ArrayList<>();
        while(in.hasNext()) {
            String s = in.nextLine();
            if(s.contains(":")) {
                String[] d = s.split(": ");
                lookup.put(Integer.parseInt(d[0]), d[1]);
            }
            else if(s.matches("^.+"))
                messages.add(s);
        }

        String rs = specialRulesToString(0);
        return String.valueOf(messages.stream().filter(s -> s.matches("^" + rs + "$")).count());
    }

    public String rulesToString(int n) {
        String rv = lookup.get(n).replaceAll("\"", "");
        StringBuilder sb = new StringBuilder("(");
        for(String s : rv.split(" ")) {
            if(s.matches("\\d.*"))
                sb.append(rulesToString(Integer.parseInt(s)));
            else
                sb.append(s);
        }
        sb.append(")");

        return sb.toString();
    }

    public String specialRulesToString(int n) {
        String rv = lookup.get(n).replaceAll("\"", "");
        StringBuilder sb = new StringBuilder();

        if(n == 8)
            sb
                .append("(")
                .append(specialRulesToString(42))
                .append("+)");

        else if(n == 11) {
            sb.append("(");
            for(int i = 1; i < 10; i++) {
                if(i > 1)
                    sb.append("|");

                sb
                    .append("(")
                    .append(specialRulesToString(42).repeat(i))
                    .append(specialRulesToString(31).repeat(i))
                    .append(")");
            }
            sb.append(")");
        }
        else {
            sb.append("(");
            for (String s : rv.split(" ")) {
                if (s.matches("\\d.*"))
                    sb.append(specialRulesToString(Integer.parseInt(s)));
                else
                    sb.append(s);
            }
            sb.append(")");
        }
        return sb.toString();
    }

}