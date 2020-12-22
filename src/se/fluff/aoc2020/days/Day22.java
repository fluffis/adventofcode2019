package se.fluff.aoc2020.days;

import se.fluff.aoc.AocDay;

import java.util.*;

/**
 * Created by Fluff on 2020-12-22.
 */
public class Day22 extends AocDay {

    public Day22() {

    }

    @Override
    public String a(Scanner in) {

        Deck d1 = new Deck();
        Deck d2 = new Deck();
        int player = 1;
        while(in.hasNext()) {
            String s = in.nextLine();
            if(s.equals("Player 1:"))
                continue;
            else if(s.equals("Player 2:")) {
                player = 2;
                continue;
            }
            else if(s.length() == 0)
                continue;

            if(player == 1)
                d1.addOnBottom(Integer.parseInt(s));
            else
                d2.addOnBottom(Integer.parseInt(s));

        }

        while(d1.cards() > 0 && d2.cards() > 0) {
            int c1 = d1.removeFromTop();
            int c2 = d2.removeFromTop();

            if(c1 > c2)
                d1.addOnBottom(c1, c2);
            else if(c2 > c1)
                d2.addOnBottom(c2, c1);
        }

        return String.valueOf(d1.cards() > 0 ? d1.tally() : d2.tally());
    }

    @Override
    public String b(Scanner in) {

        Deck d1 = new Deck();
        Deck d2 = new Deck();
        int player = 1;
        while(in.hasNext()) {
            String s = in.nextLine();
            if(s.equals("Player 1:"))
                continue;
            else if(s.equals("Player 2:")) {
                player = 2;
                continue;
            }
            else if(s.length() == 0)
                continue;

            if(player == 1)
                d1.addOnBottom(Integer.parseInt(s));
            else
                d2.addOnBottom(Integer.parseInt(s));

        }

        Game g = new Game(d1, d2);
        int winner = g.run();

        return String.valueOf(g.getscore());

    }

    public class Game {

        private Deck d1;
        private Deck d2;
        private int gamewinner = 0;

        public Game(Deck d1, Deck d2) {
            this.d1 = d1;
            this.d2 = d2;
        }

        /**
         * Returns winning player
         * @return int
         */
        public int run() {
            Set<String> rounds = new TreeSet<>();

            while(d1.cards() > 0 && d2.cards() > 0) {

                String lr = d1.tally() + "|" + d2.tally();

                if(rounds.contains(lr)) {
                    gamewinner = 1;
                    return gamewinner;
                }

                rounds.add(d1.tally() + "|" + d2.tally());

                int winner = 0;
                int c1 = d1.removeFromTop();
                int c2 = d2.removeFromTop();

                if(c1 <= d1.cards() && c2 <= d2.cards()) {
                    Game sub = new Game(new Deck(d1.copyFromTop(c1)), new Deck(d2.copyFromTop(c2)));
                    winner = sub.run();
                }
                else {
                    if(c1 > c2)
                        winner = 1;
                    else if(c2 > c1)
                        winner = 2;
                }

                if(winner == 1)
                    d1.addOnBottom(c1, c2);
                else if(winner == 2)
                    d2.addOnBottom(c2, c1);
                else
                    System.out.println("No round winner was declared!");

            }

            if(d1.cards() > 0)
                gamewinner = 1;
            else if(d2.cards() > 0)
                gamewinner = 2;

            return gamewinner;
        }

        public int getscore() {
            if(gamewinner == 1)
                return d1.tally();
            else if(gamewinner == 2)
                return d2.tally();
            else
                return Integer.MIN_VALUE;
        }

    }

    public class Deck {
        LinkedList<Integer> deck = new LinkedList<>();

        public Deck() {

        }

        public Deck(List<Integer> deck) {
            this.deck = new LinkedList<>(deck);
        }

        public void addOnBottom(int n) {
            deck.addLast(n);
        }

        public void addOnBottom(int c1, int c2) {
            deck.addLast(c1);
            deck.addLast(c2);
        }

        public int removeFromTop() {
            return deck.removeFirst();
        }

        public List<Integer> copyFromTop(int n) {
            return deck.subList(0, n);
        }

        public int cards() {
            return deck.size();
        }

        public int tally() {
            int sum = 0;
            for(int value = deck.size(); value > 0; value--)
                sum += value * deck.get(deck.size() - value);

            return sum;
        }
    }


}
