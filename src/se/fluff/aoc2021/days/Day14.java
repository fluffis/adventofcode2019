package se.fluff.aoc2021.days;

import se.fluff.aoc.AocDay;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Created by Fluff on 2021-12-14.
 */
public class Day14 extends AocDay {

    public Day14() {

    }

    @Override
    public String a(Scanner in, boolean isTest) throws Exception {
        String polymer = in.nextLine();
        in.nextLine();
        HashMap<String, String> insertionrules = new HashMap<>();
        while(in.hasNext()) {
            String[] row = in.nextLine().split(" -> ");
            insertionrules.put(row[0], row[1]);
        }

        for(int step = 1; step <= 10; step++) {
            StringBuilder p2 = new StringBuilder();
            for (int i = 0; i <= polymer.length() - 2; i++) {
                String pair = polymer.substring(i, i + 2);
                if(insertionrules.containsKey(pair)) {
                    if(i == 0)
                        p2.append(pair.charAt(0));
                    p2.append(insertionrules.get(pair)).append(pair.charAt(1));
                }
                else
                    p2.append(pair);
            }

            polymer = p2.toString();
        }

        HashMap<Character,Long> lcount = new HashMap<>();
        for(int i = 0; i < polymer.length(); i++)
            lcount.put(polymer.charAt(i), lcount.getOrDefault(polymer.charAt(i), 0L) + 1);

        ArrayList<Long> values = lcount
                .values()
                .stream()
                .sorted((a, b) -> Long.compare(b, a))
                .collect(Collectors.toCollection(ArrayList::new));

        return String.valueOf(values.get(0) - values.get(values.size() - 1));
    }

    @Override
    public String b(Scanner in, boolean isTest) {
        HashMap<String, Long> pairs = new HashMap<>();
        String polymer = in.nextLine();
        for(int i = 0; i <= polymer.length() - 2; i++) {
            String pair = polymer.substring(i, i + 2);
            pairs.put(pair, pairs.getOrDefault(pair, 0L) + 1);
        }

        in.nextLine();
        HashMap<String, String> insertionrules = new HashMap<>();
        while(in.hasNext()) {
            String[] row = in.nextLine().split(" -> ");
            insertionrules.put(row[0], row[1]);
        }

        HashMap<Character,Long> lcount = new HashMap<>();
        for(char c : polymer.toCharArray())
            lcount.put(c, lcount.getOrDefault(c, 0L) + 1);

        for(int step = 1; step <= 40; step++) {
            HashMap<String, Long> copy = new HashMap<>();

            for (Map.Entry<String, Long> e : pairs.entrySet()) {
                long c = e.getValue();
                String key = e.getKey().charAt(0) + insertionrules.get(e.getKey());
                copy.put(key, copy.getOrDefault(key, 0L) + c);
                key = insertionrules.get(e.getKey()) + e.getKey().charAt(1);
                copy.put(key, copy.getOrDefault(key, 0L) + c);

                lcount.put(insertionrules.get(e.getKey()).charAt(0), lcount.getOrDefault(insertionrules.get(e.getKey()).charAt(0), 0L) + c);
            }
            pairs = copy;

        }

        ArrayList<Long> values = lcount.values()
                .stream()
                .sorted((a, b) -> Long.compare(b, a))
                .collect(Collectors.toCollection(ArrayList::new));

        return String.valueOf(values.get(0) - values.get(values.size() - 1));
    }
}