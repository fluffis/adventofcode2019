package se.fluff.aoc2023.days;

import se.fluff.aoc.AocDay;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by Fluff on 2023-12-02.
 */
public class Day02 extends AocDay {

    public Day02() {

    }

    @Override
    public String a(Scanner in, boolean isTest) throws Exception {
        int sum = 0;

        Pattern gameIdPattern = Pattern.compile("^Game (\\d+): ");
        Pattern bluePattern = Pattern.compile("(\\d+) blue");
        Pattern greenPattern = Pattern.compile("(\\d+) green");
        Pattern redPattern = Pattern.compile("(\\d+) red");

        while(in.hasNext()) {
            String l = in.nextLine();
            Matcher gameIdMatcher = gameIdPattern.matcher(l);

            if(gameIdMatcher.find()) {
                boolean isPossible = true;
                int gameid = Integer.parseInt(gameIdMatcher.group(1));
                String setstr = l.replaceAll("^Game (\\d+): ", "");
                String[] sets = setstr.split("; ");
                for(String set : sets) {
                    Matcher blueMatcher = bluePattern.matcher(set);
                    Matcher greenMatcher = greenPattern.matcher(set);
                    Matcher redMatcher = redPattern.matcher(set);

                    if(blueMatcher.find()) {
                        if(Integer.parseInt(blueMatcher.group(1)) > 14) {
                            isPossible = false;
                        }
                    }
                    if(greenMatcher.find()) {
                        if(Integer.parseInt(greenMatcher.group(1)) > 13) {
                            isPossible = false;
                        }
                    }
                    if(redMatcher.find()) {
                        if(Integer.parseInt(redMatcher.group(1)) > 12) {
                            isPossible = false;
                        }
                    }
                }

                if(isPossible) {
                    sum += gameid;
                }
            }
            else {
                System.err.println("No match");
            }
        }

        return String.valueOf(sum);
    }

    @Override
    public String b(Scanner in, boolean isTest) {
        long sum = 0;

        Pattern gameIdPattern = Pattern.compile("^Game (\\d+): ");
        Pattern bluePattern = Pattern.compile("(\\d+) blue");
        Pattern greenPattern = Pattern.compile("(\\d+) green");
        Pattern redPattern = Pattern.compile("(\\d+) red");

        while(in.hasNext()) {
            String l = in.nextLine();
            Matcher gameIdMatcher = gameIdPattern.matcher(l);

            if(gameIdMatcher.find()) {
                int minblue = 0, minred = 0, mingreen = 0;
                String[] sets = l.replaceAll("^Game (\\d+): ", "").split("; ");
                for(String set : sets) {

                    Matcher blueMatcher = bluePattern.matcher(set);
                    Matcher greenMatcher = greenPattern.matcher(set);
                    Matcher redMatcher = redPattern.matcher(set);

                    if(blueMatcher.find()) {
                        minblue = Math.max(minblue, Integer.parseInt(blueMatcher.group(1)));
                    }
                    if(greenMatcher.find()) {
                        mingreen = Math.max(mingreen, Integer.parseInt(greenMatcher.group(1)));
                    }
                    if(redMatcher.find()) {
                        minred = Math.max(minred, Integer.parseInt(redMatcher.group(1)));
                    }
                }

                sum += (long) minblue * mingreen * minred;
            }
            else {
                System.err.println("No match");
            }


        }

        return String.valueOf(sum);
    }
}
