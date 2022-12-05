package se.fluff.aoc2022.days;

import se.fluff.aoc.AocDay;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

/**
 * Created by Fluff on 2022-12-05.
 */
public class Day05 extends AocDay {

    public Day05() {

    }

    @Override
    public String a(Scanner in, boolean isTest) throws Exception {

        HashMap<Integer,ArrayDeque<Character>> stacks = new HashMap<>();
        while(in.hasNext()) {
            String l = in.nextLine();
            if(l.startsWith(" 1   2   3")) {
                break;
            }

            for(int i = 0; i < l.length(); i++) {
                if(l.charAt(i) == '[') {
                    int stackid = 1 + (i/4);
                    ArrayDeque<Character> stack = stacks.getOrDefault(stackid, new ArrayDeque<>());
                    stack.addLast(l.charAt(i + 1));
                    stacks.put(stackid, stack);
                    i += 3;
                }
            }
        }

        in.nextLine();
        while(in.hasNext()) {
            String l = in.nextLine();
            Pattern p = Pattern.compile("^move (\\d+) from (\\d+) to (\\d)$");
            Matcher m = p.matcher(l);
            if(m.find()) {
                int c = Integer.parseInt(m.group(1));
                int from = Integer.parseInt(m.group(2));
                int to = Integer.parseInt(m.group(3));

                ArrayDeque<Character> fromstack = stacks.get(from);
                ArrayDeque<Character> tostack = stacks.get(to);

                for(int i = 0; i < c; i++) {
                    tostack.addFirst(fromstack.removeFirst());
                }
                stacks.put(from, fromstack);
                stacks.put(to, tostack);
            }
            else {
                System.out.println("Did not match on: " + l);
            }
        }

        return stacks.values().stream().map(s -> s.getFirst().toString()).collect(Collectors.joining());
    }

    @Override
    public String b(Scanner in, boolean isTest) {
        HashMap<Integer,ArrayDeque<Character>> stacks = new HashMap<>();
        while(in.hasNext()) {
            String l = in.nextLine();
            if(l.startsWith(" 1   2   3")) {
                break;
            }

            for(int i = 0; i < l.length(); i++) {
                if(l.charAt(i) == '[') {
                    int stackid = 1 + (i/4);
                    ArrayDeque<Character> stack = stacks.getOrDefault(stackid, new ArrayDeque<>());
                    stack.addLast(l.charAt(i + 1));
                    stacks.put(stackid, stack);
                    i += 3;
                }
            }
        }

        in.nextLine();
        while(in.hasNext()) {
            String l = in.nextLine();
            Pattern p = Pattern.compile("^move (\\d+) from (\\d+) to (\\d)$");
            Matcher m = p.matcher(l);
            if(m.find()) {
                int c = Integer.parseInt(m.group(1));
                int from = Integer.parseInt(m.group(2));
                int to = Integer.parseInt(m.group(3));

                ArrayDeque<Character> fromstack = stacks.get(from);
                ArrayDeque<Character> tostack = stacks.get(to);

                ArrayDeque<Character> movestack = new ArrayDeque<>();
                for(int i = 0; i < c; i++) {
                    movestack.addFirst(fromstack.remove());
                }
                for(int i = 0; i < c; i++) {
                    tostack.addFirst(movestack.remove());
                }

                stacks.put(from, fromstack);
                stacks.put(to, tostack);
            }
            else {
                System.out.println("Did not match on: " + l);
            }
        }

        return stacks.values().stream().map(s -> s.getFirst().toString()).collect(Collectors.joining());
    }
}
