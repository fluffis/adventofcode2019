package se.fluff.aoc2022.days;

import se.fluff.aoc.AocDay;

import java.math.BigInteger;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Created by Fluff on 2022-12-11.
 */
public class Day11 extends AocDay {

    public Day11() {

    }

    @Override
    public String a(Scanner in, boolean isTest) throws Exception {
        HashMap<Long, Monkey> monkeys = parseInput(in, true);
        monkeys = throwthingsaround(monkeys, 20, 0L);

        return String.valueOf(monkeys.values().stream()
                .map(Monkey::getInspections)
                .sorted(Collections.reverseOrder())
                .limit(2)
                .reduce((c, v) -> c * v)
                .get());
    }

    @Override
    public String b(Scanner in, boolean isTest) throws Exception {

        HashMap<Long, Monkey> monkeys = parseInput(in, false);

        long cdom = monkeys
                .values()
                .stream()
                .mapToLong(Monkey::getTestdivisor)
                .reduce(1L, (first, second) -> first * second);

        monkeys = throwthingsaround(monkeys, 10000, cdom);

        return String.valueOf(monkeys.values().stream()
                .map(Monkey::getInspections)
                .sorted(Collections.reverseOrder())
                .limit(2)
                .reduce((c, v) -> c * v)
                .get());    }

    public HashMap<Long, Monkey> parseInput(Scanner in, boolean dividebythree) {
        int monkey = 0;
        HashMap<Long, Monkey> monkeys = new HashMap<>();
        ArrayList<Integer> itemlist = new ArrayList<>();
        String operation = "";
        int divisor = 0;
        int truemonkey = -1;
        int falsemonkey = -1;
        while(in.hasNext()) {
            String l = in.nextLine();

            if(l.contains("Monkey")) {
                String[] p = l.split(" ");
                monkey = Integer.parseInt(p[1].replace(":", ""));
            }
            else if(l.contains("Starting items")) {
                String[] p = l.split(": ");
                itemlist.addAll(Arrays.stream(p[1].split(", ")).map(Integer::parseInt).collect(Collectors.toList()));
            }
            else if(l.contains("Operation:")) {
                String[] p = l.split("new = ");
                operation = p[1];
            }
            else if(l.contains("Test: ")) {
                String[] p = l.split("divisible by ");
                divisor = Integer.parseInt(p[1]);
            }
            else if(l.contains("If true:")) {
                String[] p = l.split("monkey ");
                truemonkey = Integer.parseInt(p[1]);
            }
            else if(l.contains("If false:")) {
                String[] p = l.split("monkey ");
                falsemonkey = Integer.parseInt(p[1]);

                Monkey m  = new Monkey(operation, divisor, truemonkey, falsemonkey, dividebythree);
                for(int item : itemlist)
                    m.addItem(BigInteger.valueOf(item));
                monkeys.put((long) monkey, m);
                itemlist = new ArrayList<>();
            }
        }

        return monkeys;
    }

    public HashMap<Long, Monkey> throwthingsaround(HashMap<Long, Monkey> monkeys, int rounds, long cdom) {
        for(int round = 1; round <= rounds; round++) {
            for(long monkeyid : monkeys.keySet()) {
                Monkey m = monkeys.get(monkeyid);
                m.setCdom(cdom);
                m.prepareNextRound();
                while(m.hasItem()) {
                    ArrayList<Long> res = m.processItem();
                    Monkey rec = monkeys.get(res.get(1));
                    rec.addThrownItem(BigInteger.valueOf(res.get(0)));
                    monkeys.put(res.get(1), rec);
                }
            }
        }
        return monkeys;
    }
}

class Monkey {


    private final ArrayList<BigInteger> items = new ArrayList<>();
    private ArrayList<BigInteger> thrownitems = new ArrayList<>();
    private final String operation;
    private final int testdivisor;
    private final long truemonkey;
    private final long falsemonkey;
    private long inspections = 0;
    private final boolean dividebythree;
    private long cdom;

    public Monkey(String operation, int testdivisor, int truemonkey, int falsemonkey, boolean dividebythree) {
        this.operation = operation;
        this.testdivisor = testdivisor;
        this.truemonkey = truemonkey;
        this.falsemonkey = falsemonkey;
        this.dividebythree = dividebythree;
    }

    public void setCdom(long cdom) {
        this.cdom = cdom;
    }

    public void addItem(BigInteger item) {
        items.add(item);
    }

    public boolean hasItem() {
        return items.size() > 0;
    }

    public BigInteger removeItem() {
        return items.remove(0);
    }

    public void addThrownItem(BigInteger item) {
        thrownitems.add(item);
    }

    public int getTestdivisor() {
        return testdivisor;
    }

    public void prepareNextRound() {
        items.addAll(thrownitems);
        thrownitems = new ArrayList<>();
    }

    public ArrayList<Long> processItem() {
        inspections++;
        BigInteger item = removeItem();
        String[] op = operation.split(" ");

        long newvalue = 0;
        long a = Long.parseLong(op[0].equals("old")? String.valueOf(item) : op[0]);
        long b = Long.parseLong(op[2].equals("old")? String.valueOf(item) : op[2]);

        switch(op[1]) {
            case "+":
                newvalue = a + b;
                break;
            case "-":
                newvalue = a - b;
                break;
            case "*":
                newvalue = a * b;
                break;
            case "/":
                newvalue = a / b;
                break;
        }

        if(dividebythree)
            newvalue = Math.floorDiv(newvalue, 3);
        else
            newvalue = newvalue % cdom;

        ArrayList<Long> r = new ArrayList<>();
        r.add(newvalue);
        if(newvalue % testdivisor == 0) {
            r.add(truemonkey);
        }
        else {
            r.add(falsemonkey);
        }
        return r;
    }

    public long getInspections() {
        return inspections;
    }

    @Override
    public String toString() {
        return "Monkey{" +
                "items=" + items +
                ", thrownitems=" + thrownitems +
                ", operation='" + operation + '\'' +
                ", testdivisor=" + testdivisor +
                ", truemonkey=" + truemonkey +
                ", falsemonkey=" + falsemonkey +
                '}';
    }
}