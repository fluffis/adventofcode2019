package se.fluff.aoc2019.days;

import se.fluff.aoc.AocDay;

import java.util.*;

/**
 * Created by Fluff on 2019-12-06.
 */
public class Day06 extends AocDay {

    HashMap<String, ArrayList<String>> orbits = new HashMap<>();
    HashMap<String, String> revOrbits = new HashMap<>();
    HashMap<String, Integer> distance = new HashMap<>();

    Set<String> unsettled = new HashSet<>();
    Set<String> settled = new HashSet<>();

    public Day06() {

    }

    @Override
    public String a(Scanner in, boolean isTest) {

        orbits = new HashMap<>();
        revOrbits = new HashMap<>();

        while(in.hasNext()) {
            String[] s = in.nextLine().split("\\)");
            if(orbits.containsKey(s[0])) {
                orbits.get(s[0]).add(s[1]);
            }
            else {
                ArrayList<String> a = new ArrayList<>();
                a.add(s[1]);
                orbits.put(s[0], a);
            }
            revOrbits.put(s[1], s[0]);
        }

        int orbitcount = r("COM", 0);

        return Integer.toString(orbitcount);
    }


    @Override
    public String b(Scanner in, boolean isTest) {

        String start = revOrbits.get("YOU");
        String goal = revOrbits.get("SAN");

        distance.put(start, 0);
        unsettled.add(start);
        while(unsettled.size() > 0) {
            String node = getMinimum(unsettled);
            settled.add(node);
            unsettled.remove(node);

        }


        return null;

    }

    private String getMinimum(Set<String> nodes) {
        String minNode = null;
        for(String n : nodes) {
            if(minNode == null)
                minNode = n;
            else {
                if(getDistance(n) < getDistance(minNode))
                    minNode = n;
            }
        }
        return minNode;
    }

    private int getDistance(String node) {
        return distance.getOrDefault(node, Integer.MAX_VALUE);
    }

    private int r(String o, int depth) {

        int outer = depth;

        if(orbits.containsKey(o)) {
            ArrayList<String> children = orbits.get(o);
            for (String c : children) {
                outer += r(c, depth + 1);
            }
        }
        return outer;
    }
}
