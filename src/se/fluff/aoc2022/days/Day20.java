package se.fluff.aoc2022.days;

import se.fluff.aoc.AocDay;
import java.util.*;

/**
 * Created by Fluff on 2022-12-20.
 */
public class Day20 extends AocDay {

    public Day20() {

    }

    @Override
    public String a(Scanner in, boolean isTest) throws Exception {
        LinkedList<Number> seq = new LinkedList<>();
        while(in.hasNext()) {
            int n = Integer.parseInt(in.nextLine());
            seq.addLast(new Number(n));
        }

        while(seq.stream().anyMatch(n -> !n.hasMoved())) {
            Optional<Number> optionaln = seq.stream().filter(number -> !number.hasMoved()).findFirst();
            if(optionaln.isPresent()) {
                Number n = optionaln.get();
                int pos = seq.indexOf(n);
                int newpos = (int) ((pos + n.number) % (seq.size() - 1));
                if(newpos <= 0) {
                    newpos = seq.size() + newpos - 1;
                }
                seq.remove(n);
                n.setMoved(true);
                seq.add(newpos, n);
            }
        }

        int score = 0;
        int pos = seq.stream().filter(n -> n.number == 0).map(seq::indexOf).findFirst().get();
        for(int round = 1000; round <= 3000; round += 1000) {
            score += seq.get((pos + round) % seq.size()).number;
        }

        return String.valueOf(score);
    }

    @Override
    public String b(Scanner in, boolean isTest) {
        LinkedList<Number> seq = new LinkedList<>();
        ArrayList<Number> orgorder = new ArrayList<>();
        while(in.hasNext()) {
            long n = Long.parseLong(in.nextLine()) * 811589153;
            Number number = new Number(n);
            seq.addLast(number);
            orgorder.add(number);
        }

        for(int round = 1; round <= 10; round++) {
            for(Number tomove : orgorder) {
                Number n = seq.stream().filter(number -> number.equals(tomove) && !number.hasMoved()).findFirst().get();
                int pos = seq.indexOf(n);
                int newpos = (int) ((pos + n.number) % (seq.size() - 1));
                if (newpos <= 0) {
                    newpos = seq.size() + newpos - 1;
                }
                seq.remove(n);
                n.setMoved(true);
                seq.add(newpos, n);
            }
            for(Number n : seq) {
                n.setMoved(false);
            }
        }

        long score = 0;
        int pos = seq.stream().filter(n -> n.number == 0).map(seq::indexOf).findFirst().get();
        for(int round = 1000; round <= 3000; round += 1000) {
            score += seq.get((pos + round) % seq.size()).number;
        }
        return String.valueOf(score);
    }
}
class Number {

    long number;
    boolean moved = false;

    public Number(long number) {
        this.number = number;
    }

    public void setMoved(boolean moved) {
        this.moved = moved;
    }

    public boolean hasMoved() {
        return moved;
    }

    @Override
    public String toString() {
        return String.valueOf(number);
    }

}