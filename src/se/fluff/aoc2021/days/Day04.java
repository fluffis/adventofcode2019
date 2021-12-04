package se.fluff.aoc2021.days;

import se.fluff.aoc.AocDay;

import java.awt.*;
import java.util.*;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by Fluff on 2021-12-04.
 */
public class Day04 extends AocDay {

    public Day04() {

    }

    @Override
    public String a(Scanner in, boolean isTest) throws Exception {

        List<Integer> callednumbers = Arrays.stream(in.nextLine().split(","))
                .mapToInt(Integer::parseInt)
                .boxed()
                .collect(Collectors.toList());

        ArrayList<BingoBoard> boards = new ArrayList<>();

        BingoBoard b = null;
        int row = 1;
        while(in.hasNext()) {
            String l = in.nextLine();
            if(l.length() == 0) {
                if(b != null)
                    boards.add(b);
                b = new BingoBoard();
                row = 1;
            }
            else {
                List<Integer> rown = Arrays.stream(l.trim().split(" +"))
                        .mapToInt(Integer::parseInt)
                        .boxed()
                        .collect(Collectors.toList());

                for(int col = 1; col <= 5; col++) {
                    if(b != null)
                        b.addNumberAt(rown.get(col - 1), new Point(row, col));
                }
                row++;
            }
        }

        if(b != null)
            boards.add(b);

        for(int cn : callednumbers) {
            for(BingoBoard board : boards) {
                if(board.markAndCheck(cn)) {
                    return String.valueOf(board.getSumUncheked() * cn);
                }
            }
        }
        return null;
    }

    @Override
    public String b(Scanner in, boolean isTest) {
        List<Integer> callednumbers = Arrays.stream(in.nextLine().split(",")).mapToInt(Integer::parseInt).boxed().collect(Collectors.toList());
        ArrayList<BingoBoard> boards = new ArrayList<>();

        BingoBoard b = null;
        int row = 1;
        while(in.hasNext()) {
            String l = in.nextLine();
            if(l.length() == 0) {
                if(b != null)
                    boards.add(b);
                b = new BingoBoard();
                row = 1;
            }
            else {
                List<Integer> rown = Arrays.stream(l.trim().split(" +"))
                        .mapToInt(Integer::parseInt)
                        .boxed()
                        .collect(Collectors.toList());

                for(int col = 1; col <= 5; col++) {
                    if(b != null)
                        b.addNumberAt(rown.get(col - 1), new Point(row, col));
                }
                row++;
            }
        }

        if(b != null)
            boards.add(b);

        for(int cn : callednumbers) {
            for(BingoBoard board : boards) {
                if(board.markAndCheck(cn)) {
                    if(boards.stream().allMatch(BingoBoard::check))
                        return String.valueOf(cn * board.getSumUncheked());
                }
            }
        }
        return null;
    }
}

class BingoBoard {

    HashMap<Integer, Point> numbers = new HashMap<>();
    ArrayList<Point> checked = new ArrayList<>();
    ArrayList<Integer> unchecked = new ArrayList<>();

    public void addNumberAt(int number, Point p) {
        numbers.put(number, p);
        unchecked.add(number);
    }

    public boolean markAndCheck(int number) {

        Point p = numbers.get(number);
        checked.add(p);
        unchecked.remove(Integer.valueOf(number));

        return check();
    }

    public boolean check() {
        for(int x = 1; x <= 5; x++) {
            int c = 0;
            for(int y = 1; y <= 5; y++) {
                if(checked.contains(new Point(x, y)))
                    c++;
            }
            if(c == 5)
                return true;
        }

        for(int y = 1; y <= 5; y++) {
            int c = 0;
            for(int x = 1; x <= 5; x++) {
                if(checked.contains(new Point(x, y)))
                    c++;
            }
            if(c == 5)
                return true;
        }
        return false;
    }

    public int getSumUncheked() {
        return unchecked.stream().reduce(Integer::sum).get();
    }

}