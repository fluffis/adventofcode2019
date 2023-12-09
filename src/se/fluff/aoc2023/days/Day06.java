package se.fluff.aoc2023.days;

import se.fluff.aoc.AocDay;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by Fluff on 2023-12-06.
 */
public class Day06 extends AocDay {

    public Day06() {

    }

    @Override
    public String a(Scanner in, boolean isTest) throws Exception {
        String timestr = in.nextLine();
        String distancestr = in.nextLine();

        Pattern p = Pattern.compile("(\\d+)");

        Matcher timematcher = p.matcher(timestr);
        Matcher distancematcher = p.matcher(distancestr);

        HashMap<Integer, Integer> races = new HashMap<>();
        ArrayList<Integer> times = new ArrayList<>();
        ArrayList<Integer> distances = new ArrayList<>();
        while(timematcher.find()) {
            times.add(Integer.parseInt(timematcher.group(0)));
        }

        while(distancematcher.find()) {
            distances.add(Integer.parseInt(distancematcher.group(0)));
        }

        if(distances.size() != times.size()) {
            throw new Exception("Mismatching times and distances");
        }

        for(int i = 0; i < times.size(); i++) {
            races.put(times.get(i), distances.get(i));
        }

        int sum = 0;
        for(int time : times) {
            int longerthanrecord = 0;
            for(int hold = 0; hold < time; hold++) {
                int ttm = time - hold;
                int dist = ttm * hold;
                if(dist > races.get(time)) {
                    longerthanrecord++;
                }
            }

            if(sum == 0) {
                sum = longerthanrecord;
            }
            else {
                sum *= longerthanrecord;
            }

        }

        return String.valueOf(sum);
    }

    @Override
    public String b(Scanner in, boolean isTest) throws Exception {
        String timestr = in.nextLine();
        String distancestr = in.nextLine();

        timestr = timestr.replaceAll(" ", "");
        distancestr = distancestr.replaceAll(" ", "");

        Pattern p = Pattern.compile("(\\d+)");

        Matcher timematcher = p.matcher(timestr);
        Matcher distancematcher = p.matcher(distancestr);

        long time = -1L;
        while(timematcher.find()) {
            time = Long.parseLong(timematcher.group(0));
        }

        long dist = -1L;
        while(distancematcher.find()) {
            dist = Long.parseLong(distancematcher.group(0));
        }

        if(dist < 0 || time < 0) {
            throw new Exception("Time and/or distance isn't matched");
        }

        long longerthanrecord = 0;
        for(long hold = 0; hold < time; hold++) {
            if((time - hold) * hold > dist) {
                longerthanrecord++;
            }
        }

        return String.valueOf(longerthanrecord);
    }
}
