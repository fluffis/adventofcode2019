package se.fluff.aoc2020.days;

import se.fluff.aoc.AocDay;

import java.util.*;
import java.util.stream.Collectors;

/**
 * Created by Fluff on 2020-12-16.
 */
public class Day16 extends AocDay {

    public Day16() {

    }

    @Override
    public String a(Scanner in) {
        ArrayList<Rule> rules = new ArrayList<>();
        ArrayList<Ticket> tickets = new ArrayList<>();
        while(in.hasNext()) {
            String s = in.nextLine();
            if(s.contains(": "))
                rules.add(new Rule(s));
            else if(s.contains("your ticket:") && in.hasNext())
                tickets.add(new Ticket(in.nextLine()));
            else if(s.matches("nearby tickets:")) {
                while(in.hasNext())
                    tickets.add(new Ticket(in.nextLine()));
            }

        }

        return String.valueOf(tickets.stream().mapToInt(t -> t.errorScanRate(rules)).sum());
    }

    @Override
    public String b(Scanner in) {

        Ticket myticket = new Ticket();
        ArrayList<Rule> rules = new ArrayList<>();
        ArrayList<Ticket> tickets = new ArrayList<>();
        while(in.hasNext()) {
            String s = in.nextLine();
            if(s.contains(": "))
                rules.add(new Rule(s));
            else if(s.contains("your ticket:") && in.hasNext())
                myticket.set(in.nextLine());
            else if(s.matches("nearby tickets:")) {
                while(in.hasNext())
                    tickets.add(new Ticket(in.nextLine()));
            }

        }

        tickets = tickets
                .stream()
                .filter(t -> t.isValid(rules))
                .collect(Collectors.toCollection(ArrayList::new));

        Rulepositions rp = new Rulepositions(rules);
        for(Rule r : rules) {
            for (int i = 0; i < myticket.getNumSize(); i++) {
                int ii = i;
                if(tickets.stream().allMatch(t -> t.posMatchesRule(ii, r)))
                    rp.addPosToRule(r, ii);
            }
        }

        rp.autoAssign();
        return String.valueOf(rp.calcDepartureValue(myticket));
    }
}

class Ticket {
    private ArrayList<Integer> numbers = new ArrayList<>();
    private HashMap<String, Integer> props = new HashMap<>();
    public Ticket() {

    }

    public Ticket(String s) {
        set(s);
    }

    public void set(String s) {
        for(String n : s.split(",")) {
            numbers.add(Integer.parseInt(n));
        }
    }
    public int getNumSize() {
        return numbers.size();
    }

    public int getNumAtPos(int pos) {
        return numbers.get(pos);
    }

    public boolean posMatchesRule(int pos, Rule r) {
        return r.matchesRule(numbers.get(pos));
    }

    public int errorScanRate(ArrayList<Rule> rules) {
        int sum = 0;
        for(int n : numbers) {
            if(rules.stream().noneMatch(r -> r.matchesRule(n)))
                sum += n;
        }

        return sum;
    }

    public boolean isValid(ArrayList<Rule> rules) {
        int c = 0;
        for(int n : numbers) {
            if(rules.stream().noneMatch(r -> r.matchesRule(n)))
                c++;
        }
        return c == 0;
    }
}

class Rule {

    private String name;
    private Set<Integer> rangevalues = new TreeSet<>();
    private int pos = -1;

    public Rule(String s) {
        String[] parts = s.split(": ");
        this.name = parts[0];

        for(String range : parts[1].split(" or ")) {
            String[] numbers = range.split("-");
            for(int i = Integer.parseInt(numbers[0]); i <= Integer.parseInt(numbers[1]); i++)
                rangevalues.add(i);
        }
    }

    public String getName() {
        return name;
    }

    public boolean matchesRule(int n) {
        return rangevalues.contains(n);
    }

    public void setPos(int pos) {
        this.pos = pos;
    }

    public int getPos() {
        return pos;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Rule rule = (Rule) o;
        return name.equals(rule.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name);
    }
}

class Rulepositions {

    private Set<Integer> positions = new TreeSet<>();
    private HashMap<Rule,Set<Integer>> rulepos = new HashMap<>();

    public Rulepositions(ArrayList<Rule> rules) {
        for(Rule r : rules)
            rulepos.put(r, new TreeSet<>());
    }

    public void addPosToRule(Rule r, int pos) {
        rulepos.get(r).add(pos);
        positions.add(pos);
    }

    public void removePosFromRules(int pos) {
        for(Map.Entry<Rule,Set<Integer>> r : rulepos.entrySet())
            r.getValue().remove(pos);
    }

    public void autoAssign() {
        while(rulepos.values().stream().anyMatch(p -> p.size() == 1)) {
            for(Map.Entry<Rule, Set<Integer>> e : rulepos.entrySet()) {
                if(e.getValue().size() == 1) {
                    int i = e.getValue().stream().findFirst().get();
                    e.getKey().setPos(i);
                    removePosFromRules(i);
                    positions.remove(i);
                }
            }
        }
    }

    public long calcDepartureValue(Ticket t) {
        long s = 1L;
        for(Rule r : rulepos.keySet()) {
            if(r.getName().contains("departure"))
                s *= t.getNumAtPos(r.getPos());
        }
        return s;
    }
}