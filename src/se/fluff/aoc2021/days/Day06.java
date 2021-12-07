package se.fluff.aoc2021.days;

import se.fluff.aoc.AocDay;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Created by Fluff on 2021-12-06.
 */
public class Day06 extends AocDay {

    public Day06() {

    }

    @Override
    public String a(Scanner in, boolean isTest) throws Exception {
        ArrayList<LanternFish> fishes = new ArrayList<>();
        List<Integer> inits = Arrays.stream(in.nextLine().split(","))
                .mapToInt(Integer::parseInt)
                .boxed()
                .collect(Collectors.toList());

        for(int i : inits)
            fishes.add(new LanternFish(i, false));

        for(int c = 1; c <= 80; c++) {

            ArrayList<LanternFish> newfishes = new ArrayList<>();
            for(LanternFish f : fishes) {
                int d = f.ageOneDay();
                if(d == 6 && !f.getNewFish())
                    newfishes.add(new LanternFish(8, true));

            }

            fishes.addAll(newfishes);
        }

        return String.valueOf(fishes.size());
    }

    @Override
    public String b(Scanner in, boolean isTest) {
        List<Integer> inits = Arrays.stream(in.nextLine().split(",")).mapToInt(Integer::parseInt).boxed().collect(Collectors.toList());

        HashMap<Long, Long> fishes = new HashMap<>();
        HashMap<Long, Long> newfishes = new HashMap<>();

        for(int i : inits)
            fishes.put((long) i, fishes.getOrDefault((long) i, 0L) + 1);

        for(int c = 1; c <= 256; c++) {

            HashMap<Long, Long> temp = new HashMap<>();
            for(int d = 6; d > -1; d--) {
                int newday = d == 0 ? 6 : d - 1;
                temp.put((long) newday, fishes.getOrDefault((long) d, 0L));
            }
            fishes = temp;
            temp = new HashMap<>();

            for(int d = 8; d > 0; d--)
                temp.put((long) d - 1, newfishes.getOrDefault((long) d, 0L));

            fishes.put(6L, fishes.getOrDefault(6L, 0L) + newfishes.getOrDefault(0L, 0L));
            newfishes = temp;
            newfishes.put(8L, fishes.get(6L));
        }

        long sum = fishes.values().stream().reduce(0L, Long::sum) + newfishes.values().stream().reduce(0L, Long::sum);

        return String.valueOf(sum);
    }
}

class LanternFish {

    private int timer;
    private boolean newfish;

    public LanternFish(int timer, boolean newfish) {
        this.timer = timer;
        this.newfish = newfish;
    }

    public int ageOneDay() {
        this.timer--;
        if(this.timer == -1) {
            this.timer = 6;
            this.newfish = false;
        }
        return timer;
    }

    public int getAge() {
        return timer;
    }

    public boolean getNewFish() {
        return this.newfish;
    }

}