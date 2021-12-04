package se.fluff.aoc2021.days;

import se.fluff.aoc.AocDay;
import java.util.*;

/**
 * Created by Fluff on 2021-12-02.
 */
public class Day02 extends AocDay {

    @Override
    public String a(Scanner in, boolean isTest) throws Exception {

        int x = 0;
        int y = 0;
        while(in.hasNext()) {
            String[] cmd = in.nextLine().split(" ");
            int count = Integer.parseInt(cmd[1]);
            switch (cmd[0]) {
                case "up":
                    y -= count;
                    break;
                case "down":
                    y += count;
                    break;
                case "forward":
                    x += count;
                    break;
            }
        }

        return String.valueOf(x * y);
    }

    @Override
    public String b(Scanner in, boolean isTest) {

        int x = 0;
        int y = 0;
        int aim = 0;

        while(in.hasNext()) {
            String[] cmd = in.nextLine().split(" ");
            int count = Integer.parseInt(cmd[1]);
            switch (cmd[0]) {
                case "up":
                    aim -= count;
                    break;
                case "down":
                    aim += count;
                    break;
                case "forward":
                    x += count;
                    y += (aim * count);
                    break;
            }
        }

        return String.valueOf(x * y);
    }
}
