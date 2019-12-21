package se.fluff.aoc2019.days;

import se.fluff.aoc2019.AocDay;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

/**
 * Created by Fluff on 2019-12-14.
 */
public class Day14 extends AocDay {

    ArrayList<ProcessRule> rules = new ArrayList<>();

    public Day14() {

    }

    @Override
    public String a(Scanner in) {

        rules = new ArrayList<>();
        while(in.hasNext()) {
            String[] line = in.nextLine().split(" => ");
            String[] inputs = line[0].split(", ");

            String[] output = line[1].split(" ");
            ProcessRule pr = new ProcessRule(output[1], Integer.parseInt(output[0]));
            for(String inp : inputs) {
                String[] s = inp.split(" ");
                pr.addInputtype(s[1], Integer.parseInt(s[0]));
            }
            rules.add(pr);
        }

        HashMap<String, Long> leftovers = new HashMap<>();
        return String.valueOf(require("FUEL", 1L, leftovers));
    }


    @Override
    public String b(Scanner in) {

        rules = new ArrayList<>();
        while(in.hasNext()) {
            String[] line = in.nextLine().split(" => ");
            String[] inputs = line[0].split(", ");

            String[] output = line[1].split(" ");
            ProcessRule pr = new ProcessRule(output[1], Integer.parseInt(output[0]));
            for(String inp : inputs) {
                String[] s = inp.split(" ");
                pr.addInputtype(s[1], Integer.parseInt(s[0]));
            }
            rules.add(pr);
        }

        long min = 0L;
        long targetreq = 1000000000000L;
        long max = targetreq;
        while(min < max) {
            long test = Math.floorDiv(min + max + 1, 2);
            HashMap<String, Long> leftovers = new HashMap<>();
            if(require("FUEL", test, leftovers) < targetreq)
                min = test;
            else
                max = --test;
        }
        return String.valueOf(min);
    }

    private long require(String type, long amountrequired, HashMap<String, Long> leftovers) {

        if(type.equals("ORE"))
            return amountrequired;
        else if(leftovers.getOrDefault(type, 0L) >= amountrequired) {
            leftovers.put(type, leftovers.getOrDefault(type, 0L) - amountrequired);
            return 0;
        }

        amountrequired -= leftovers.getOrDefault(type, 0L);
        leftovers.put(type, 0L);

        ProcessRule pr = rules.stream().filter(processRule -> processRule.getOutputtype().equals(type)).findFirst().get();
        long quantity = amountrequired / pr.getOutputamount() + ((amountrequired % pr.getOutputamount() == 0) ? 0 : 1);

        long sum = pr.getInputtypes()
                .entrySet()
                .stream()
                .map(e -> require(e.getKey(), e.getValue() * quantity, leftovers))
                .reduce(0L, Long::sum);

        leftovers.put(type, leftovers.getOrDefault(type, 0L) + pr.getOutputamount() * quantity - amountrequired);

        return sum;
    }

}

class ProcessRule {

    private HashMap<String, Integer> inputtypes = new HashMap<>();
    private String outputtype;
    private int outputamount;

    public ProcessRule(String outputtype, int outputamount) {

        this.outputtype = outputtype;
        this.outputamount = outputamount;
    }

    public void addInputtype(String name, int amount) {
        this.inputtypes.put(name, amount);
    }

    public HashMap<String, Integer> getInputtypes() {
        return inputtypes;
    }

    public String getOutputtype() {
        return outputtype;
    }

    public int getOutputamount() {
        return outputamount;
    }

    public void setOutputamount(int amount) {
        this.outputamount = amount;
    }
}