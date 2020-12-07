package se.fluff.aoc2020.days;

import se.fluff.aoc.AocDay;

import java.util.*;

/**
 * Created by Fluff on 2020-12-07.
 */
public class Day07 extends AocDay {

    public Day07() {

    }

    @Override
    public String a(Scanner in) {

        HashMap<String, Bag> bags = new HashMap<>();
        while(in.hasNext()) {
            String s = in.nextLine().replace(".", "");
            String[] data = s.split(" bags contain ");
            Bag b = bags.getOrDefault(data[0], new Bag(data[0]));

            for(String sub : data[1].split(", ")) {

                if(sub.startsWith("no other bags"))
                    continue;

                String[] cs = sub.split("\\ ", 2);
                int count = Integer.parseInt(cs[0]);
                sub = cs[1].replaceAll(" bags?\\.?", "");
                Bag subbag = bags.getOrDefault(sub, new Bag(sub));
                subbag.addParentBag(b);
                b.addChildBag(subbag, count);
                bags.put(sub, subbag);
            }
            bags.put(data[0], b);
        }

        return String.valueOf(bags.get("shiny gold").getUniqueParents().size());
    }

    @Override
    public String b(Scanner in) {
        HashMap<String, Bag> bags = new HashMap<>();
        while(in.hasNext()) {
            String s = in.nextLine().replace(".", "");
            String[] data = s.split(" bags contain ");
            Bag b = bags.getOrDefault(data[0], new Bag(data[0]));

            for(String sub : data[1].split(", ")) {

                if(sub.startsWith("no other bags"))
                    continue;
                String[] cs = sub.split("\\ ", 2);
                int count = Integer.parseInt(cs[0]);
                sub = cs[1].replaceAll(" bags?\\.?", "");
                Bag subbag = bags.getOrDefault(sub, new Bag(sub));
                subbag.addParentBag(b);
                b.addChildBag(subbag, count);
                bags.put(sub, subbag);
            }
            bags.put(data[0], b);

        }

        return String.valueOf(bags.get("shiny gold").sumChildren());
    }
}

class Bag {
    private String color;
    private HashMap<Bag, Integer> children = new HashMap<>();
    private ArrayList<Bag> parents = new ArrayList<>();

    public Bag(String color) {
        this.color = color;
    }

    public String getColor() {
        return color;
    }

    public void addChildBag(Bag bag, int count) {
        children.put(bag, count);
    }

    public void addParentBag(Bag bag) {
        if(!parents.contains(bag))
            parents.add(bag);
    }

    public ArrayList<String> getUniqueParents() {
        ArrayList<String> unique = new ArrayList<>();
        for(Bag pbag : parents) {
            if(!unique.contains(pbag.getColor()))
                unique.add(pbag.getColor());

            for(String ppbag : pbag.getUniqueParents()) {
                if(!unique.contains(ppbag))
                    unique.add(ppbag);
            }
        }
        return unique;
    }

    public int sumChildren() {
        int count = children
                .values()
                .stream()
                .reduce(0, Integer::sum);
        count += children
                .entrySet()
                .stream()
                .mapToInt(entry -> entry.getKey().sumChildren() * entry.getValue())
                .sum();

        return count;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Bag bag = (Bag) o;
        return Objects.equals(color, bag.color);
    }

    @Override
    public int hashCode() {
        return Objects.hash(color);
    }
}