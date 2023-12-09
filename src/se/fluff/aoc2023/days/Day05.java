package se.fluff.aoc2023.days;

import se.fluff.aoc.AocDay;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Created by Fluff on 2023-12-05.
 */
public class Day05 extends AocDay {

    public Day05() {

    }

    @Override
    public String a(Scanner in, boolean isTest) throws Exception {
        String seedsstr = in.nextLine();
        seedsstr = seedsstr.replace("seeds: ", "");
        List<Long> values = Arrays.stream(seedsstr.split(" ")).map(Long::parseLong).collect(Collectors.toList());
        ArrayList<Mapper> mappers = new ArrayList<>();

        Mapper mapper = null;
        while(in.hasNext()) {
            String l = in.nextLine();
            if(l.equals("")) {
                continue;
            }
            if(l.contains("map")) {
                if(mapper != null) {
                    mappers.add(mapper);
                }
                mapper = new Mapper(l);
            }
            else {
                if(mapper == null) {
                    throw new Exception("Mapper not set");
                }
                mapper.addMap(l);
            }
        }

        mappers.add(mapper);

        String source = "seed";
        String destination = "";
        while(!destination.equals("location")) {

            boolean destinationSet = false;
            for(Mapper inmapper : mappers) {
                if(inmapper.getFrom().equals(source)) {
                    destination = inmapper.getTo();
                    destinationSet = true;
                    mapper = inmapper;
                }
            }

            if(!destinationSet) {
                throw new Exception("Failed to find destination type for source " + source);
            }

            ArrayList<Long> newvalues = new ArrayList<>();
            for(long value : values) {
                newvalues.add(mapper.convertValue(value));
            }

            source = destination;
            values = new ArrayList<>(newvalues);
        }

        return String.valueOf(values.stream().min(Long::compareTo).get());
    }

    @Override
    public String b(Scanner in, boolean isTest) throws Exception {
        String seedsstr = in.nextLine();
        seedsstr = seedsstr.replace("seeds: ", "");
        List<Long> seeddata = Arrays.stream(seedsstr.split(" ")).map(Long::parseLong).collect(Collectors.toList());

        ArrayList<Mapper> mappers = new ArrayList<>();

        Mapper mapper = null;
        while(in.hasNext()) {
            String l = in.nextLine();
            if(l.equals("")) {
                continue;
            }
            if(l.contains("map")) {
                if(mapper != null) {
                    mappers.add(mapper);
                }
                mapper = new Mapper(l);
            }
            else {
                if(mapper == null) {
                    throw new Exception("Mapper not set");
                }
                mapper.addMap(l);
            }
        }

        mappers.add(mapper);
        Long bestvalue = Long.MAX_VALUE;

        for(int i = 0; i < seeddata.size(); i += 2) {
            long start = seeddata.get(i);
            long size = seeddata.get(i + 1);
            for(long s = start; s < start + size; s++) {
                long res = processMapChain(mappers, s);
                bestvalue = Math.min(bestvalue, res);
            }
        }

        return String.valueOf(bestvalue);
    }

    private long processMapChain(List<Mapper> mappers, long v) throws Exception {
        String source = "seed";
        String destination = "";
        while(!destination.equals("location")) {

            boolean destinationSet = false;
            for(Mapper inmapper : mappers) {
                if(inmapper.getFrom().equals(source)) {
                    destination = inmapper.getTo();
                    destinationSet = true;
                    v = inmapper.convertValue(v);
                }
            }

            if(!destinationSet) {
                throw new Exception("Failed to find destination type for source " + source);
            }
            source = destination;
        }
        return v;
    }
}

class Mapper {

    private String from;
    private String to;
    private ArrayList<Map> maps = new ArrayList<>();

    public Mapper(String name) {
        name = name.replaceFirst(" map:", "");
        String[] t = name.split("-to-");
        this.from = t[0];
        this.to = t[1];
    }

    public String getFrom() {
        return from;
    }

    public String getTo() {
        return to;
    }

    public void addMap(String row) {
        List<Long> t = Arrays.stream(row.split(" ")).map(Long::parseLong).collect(Collectors.toList());
        maps.add(new Map(t.get(0), t.get(1), t.get(2)));
    }

    public long convertValue(long value) {
        for(Map m : maps) {
            if(m.containsValue(value)) {
                return m.convertValue(value);
            }
        }

        return value;
    }
}

class Map {

    private long destStart;
    private long sourceStart;
    private long length;

    public Map(long destStart, long sourceStart, long length) {
        this.destStart = destStart;
        this.sourceStart = sourceStart;
        this.length = length;
    }

    public boolean containsValue(long value) {
        return value >= sourceStart && value <= sourceStart + length - 1;
    }

    public long convertValue(long value) {
        long dist = value - sourceStart;
        return destStart + dist;
    }
}