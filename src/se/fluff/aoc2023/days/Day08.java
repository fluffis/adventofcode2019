package se.fluff.aoc2023.days;

import se.fluff.aoc.AocDay;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

/**
 * Created by Fluff on 2023-12-09.
 */
public class Day08 extends AocDay {

    public Day08() {

    }

    @Override
    public String a(Scanner in, boolean isTest) throws Exception {

        Pattern p = Pattern.compile("(\\w+) = \\((\\w+), (\\w+)\\)");
        String navs = in.nextLine();
        in.nextLine();
        HashMap<String, Node> map = new HashMap<>();
        while(in.hasNext()) {
            Matcher m = p.matcher(in.nextLine());
            if(m.find()) {
                Node n = new Node();
                n.left = m.group(2);
                n.right = m.group(3);
                map.put(m.group(1), n);
            }
            else {
                throw new Exception("Unable to match line");
            }
        }
        int steps = 0;
        String currentnode = "AAA";
        while(!currentnode.equals("ZZZ")) {
            for(char nav : navs.toCharArray()) {
                Node n = map.get(currentnode);
                if(nav == 'L') {
                    currentnode = n.left;
                }
                else if(nav == 'R') {
                    currentnode = n.right;
                }
                else {
                    throw new Exception("Wrong nav type");
                }
                steps++;

                if(currentnode.equals("ZZZ")) {
                    break;
                }
            }
        }

        return String.valueOf(steps);
    }

    @Override
    public String b(Scanner in, boolean isTest) throws Exception {
        Pattern p = Pattern.compile("(\\w+) = \\((\\w+), (\\w+)\\)");
        String navs = in.nextLine();
        in.nextLine();
        HashMap<String, Node> map = new HashMap<>();
        while(in.hasNext()) {
            Matcher m = p.matcher(in.nextLine());
            if(m.find()) {
                Node n = new Node();
                n.left = m.group(2);
                n.right = m.group(3);
                map.put(m.group(1), n);
            }
            else {
                throw new Exception("Unable to match line");
            }
        }

        HashMap<String, Integer> stepmap = new HashMap<>();
        List<String> startnodes = map.keySet().stream().filter(s -> s.endsWith("A")).collect(Collectors.toList());
        for(String startnode : startnodes) {
            int steps = 0;
            String currentnode = startnode;
            while(!currentnode.endsWith("Z")) {
                for(char nav : navs.toCharArray()) {
                    Node n = map.get(currentnode);
                    if(nav == 'L') {
                        currentnode = n.left;
                    }
                    else if(nav == 'R') {
                        currentnode = n.right;
                    }
                    else {
                        throw new Exception("Wrong nav type");
                    }
                    steps++;

                    if(currentnode.endsWith("Z")) {
                        break;
                    }
                }
            }
            stepmap.put(startnode, steps);
        }

        return String.valueOf(lcm(stepmap.values().stream().mapToLong(i -> (long) i).toArray()));
    }

    private static long gcd(long a, long b) {
        while (b > 0) {
            long temp = b;
            b = a % b;
            a = temp;
        }
        return a;
    }

    private static long gcd(long[] input) {
        long result = input[0];
        for(int i = 1; i < input.length; i++) result = gcd(result, input[i]);
        return result;
    }

    private static long lcm(long a, long b) {
        return a * (b / gcd(a, b));
    }

    private static long lcm(long[] input) {
        long result = input[0];
        for(int i = 1; i < input.length; i++) result = lcm(result, input[i]);
        return result;
    }
}

class Node {

    public String left;
    public String right;
}