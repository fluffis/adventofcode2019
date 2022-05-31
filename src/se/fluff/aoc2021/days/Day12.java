package se.fluff.aoc2021.days;

import se.fluff.aoc.AocDay;
import java.util.*;

/**
 * Created by Fluff on 2021-12-12.
 */
public class Day12 extends AocDay {

    public Day12() {

    }

    HashMap<String, ArrayList<String>> caves = new HashMap<>();

    @Override
    public String a(Scanner in, boolean isTest) throws Exception {

        caves = new HashMap<>();
        while(in.hasNext()) {
            String[] cavein = in.nextLine().split("-");
            ArrayList<String> calt = caves.getOrDefault(cavein[0], new ArrayList<>());
            if(!"start".equals(cavein[1]))
                calt.add(cavein[1]);
            caves.put(cavein[0], calt);
            calt = caves.getOrDefault(cavein[1], new ArrayList<>());
            if(!"start".equals(cavein[0]))
                calt.add(cavein[0]);
            caves.put(cavein[1], calt);

        }

        return String.valueOf(findPaths("start", new ArrayList<>(), -1));
    }

    @Override
    public String b(Scanner in, boolean isTest) {

        caves = new HashMap<>();
        while(in.hasNext()) {
            String[] cavein = in.nextLine().split("-");
            ArrayList<String> calt = caves.getOrDefault(cavein[0], new ArrayList<>());
            if(!"start".equals(cavein[1]))
                calt.add(cavein[1]);
            caves.put(cavein[0], calt);
            calt = caves.getOrDefault(cavein[1], new ArrayList<>());
            if(!"start".equals(cavein[0]))
                calt.add(cavein[0]);
            caves.put(cavein[1], calt);
        }

        return String.valueOf(findPaths("start", new ArrayList<>(), 0));
    }

    private int findPaths(String cave, ArrayList<String> visited, int revisitcode) {
        if("end".equals(cave))
            return 1;

        if(visited.contains(cave) && cave.toLowerCase(Locale.ROOT).equals(cave)) {
            if(revisitcode != 0)
                return 0;
            revisitcode++;
        }
        final int revisit = revisitcode;

        ArrayList<String> ledger = new ArrayList<>(visited);
        ledger.add(cave);

        return caves.get(cave)
                .stream()
                .mapToInt(next -> findPaths(next, ledger, revisit))
                .sum();
    }
}