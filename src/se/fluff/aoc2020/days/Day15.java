package se.fluff.aoc2020.days;

import se.fluff.aoc.AocDay;

import java.util.*;

/**
 * Created by Fluff on 2020-12-15.
 */
public class Day15 extends AocDay {

    public Day15() {

    }

    @Override
    public String a(Scanner in, boolean isTest) {
        String[] s = in.nextLine().split(",");
        int[] start = Arrays.stream(s).mapToInt(Integer::parseInt).toArray();
        HashMap<Integer, Num> spoken = new HashMap<>();
        int turn = 0;
        int lastspoken = -1;

        for(int i = 0; i < start.length; i++) {
            turn++;
            spoken.put(start[turn - 1], new Num(turn));
        }
        lastspoken = start[start.length - 1];

        while(turn++ < 2020) {
            lastspoken = spoken.get(lastspoken).getNext();
            if(spoken.containsKey(lastspoken))
                spoken.get(lastspoken).addTurn(turn);
            else
                spoken.put(lastspoken, new Num(turn));
        }
        return String.valueOf(lastspoken);
    }

    @Override
    public String b(Scanner in, boolean isTest) {
        String[] s = in.nextLine().split(",");
        int[] start = Arrays.stream(s).mapToInt(Integer::parseInt).toArray();
        HashMap<Integer, Num> spoken = new HashMap<>();
        int turn = 0;
        int lastspoken = -1;
        for(int i = 0; i < start.length; i++) {
            turn++;
            spoken.put(start[turn - 1], new Num(turn));
        }
        lastspoken = start[start.length - 1];

        while(turn++ < 30000000) {
            lastspoken = spoken.get(lastspoken).getNext();
            if(spoken.containsKey(lastspoken))
                spoken.get(lastspoken).addTurn(turn);
            else
                spoken.put(lastspoken, new Num(turn));
        }
        return String.valueOf(lastspoken);
    }
}

class Num {

    private TreeSet<Integer> turns = new TreeSet<>();

    public Num(int turn) {
        this.turns.add(turn);
    }

    public void addTurn(int turn) {
        this.turns.add(turn);
    }

    public int getNext() {
        if(turns.size() == 1)
            return 0;
        else {
            Iterator<Integer> ti = turns.descendingIterator();
            return ti.next() - ti.next();
        }
    }
}