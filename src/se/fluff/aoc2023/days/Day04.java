package se.fluff.aoc2023.days;

import se.fluff.aoc.AocDay;
import java.util.*;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by Fluff on 2023-12-04.
 */
public class Day04 extends AocDay {

    public Day04() {

    }

    @Override
    public String a(Scanner in, boolean isTest) throws Exception {
        int sum = 0;
        while(in.hasNext()) {
            String l = in.nextLine();

            Pattern p = Pattern.compile("^Card\\ +(\\d+): ");
            Matcher m = p.matcher(l);
            if(m.find()) {
                l = l.replaceFirst("^Card\\ +\\d+: ", "");
                String[] d = l.split(" \\| ");
                String[] numbersstr = d[0].split(" +");
                String[] winnersstr = d[1].split(" +");

                Card c = new Card(numbersstr, winnersstr);
                if(c.getPoints() > 0) {
                    int cpoints = 1;
                    for(int i = 0; i < c.getPoints() - 1; i++) {
                        cpoints *= 2;
                    }
                    System.out.println(cpoints);
                    sum += cpoints;
                }
            }
            else {
                System.err.println("No match");
            }
        }
        return String.valueOf(sum);
    }

    @Override
    public String b(Scanner in, boolean isTest) {
        HashMap<Integer,Card> cards = new HashMap<>();
        HashMap<Integer, Integer> amount = new HashMap<>();
        while(in.hasNext()) {
            String l = in.nextLine();

            Pattern p = Pattern.compile("^Card\\ +(\\d+): ");
            Matcher m = p.matcher(l);
            if(m.find()) {
                l = l.replaceFirst("^Card\\ +\\d+: ", "");
                String[] d = l.split(" \\| ");
                String[] numbersstr = d[0].split(" +");
                String[] winnersstr = d[1].split(" +");

                Card c = new Card(numbersstr, winnersstr);
                int id = Integer.parseInt(m.group(1));
                cards.put(id, c);
                amount.put(id, 1);
            }
            else {
                System.err.println("No match");
            }

            for(Map.Entry<Integer, Card> e : cards.entrySet()) {
                int id = e.getKey();
                int points = e.getValue().getPoints();
                for(int i = id + 1; i < Math.min(id + 1 + points, cards.size() + 1); i++) {
                    amount.put(i, amount.get(id) + amount.get(i));
                }
            }
        }

        return String.valueOf(amount.values().stream().mapToLong(Integer::longValue).sum());
    }

}

class Card {

    public ArrayList<Integer> numbers = new ArrayList<>();
    public ArrayList<Integer> winners = new ArrayList<>();

    public Card(String[] nstr, String[] wstr) {
        for(String n : nstr) {
            if(n.equals("")) {
                continue;
            }
            numbers.add(Integer.parseInt(n));
        }
        for(String w : wstr) {
            if(w.equals("")) {
                continue;
            }
            winners.add(Integer.parseInt(w));
        }
    }

    public int getPoints() {
        int points = 0;
        for(int w : winners) {
            if(numbers.contains(w)) {
                points++;
            }
        }
        return points;
    }

}