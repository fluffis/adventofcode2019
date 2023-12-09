package se.fluff.aoc2023.days;

import se.fluff.aoc.AocDay;
import java.util.*;
import java.util.Map;

import static java.util.Comparator.comparing;
import static java.util.stream.Collectors.*;

/**
 * Created by Fluff on 2023-12-07.
 */
public class Day07 extends AocDay {

    public Day07() {

    }

    @Override
    public String a(Scanner in, boolean isTest) throws Exception {

        List<Hand> hands = new ArrayList<>();
        while(in.hasNext()) {
            String[] l = in.nextLine().split(" ");
            hands.add(new Hand(l[0], Integer.parseInt(l[1]), false));
        }

        List<Hand> sorted = hands.stream().sorted(new HandComparator()).collect(toList());
        long sum = 0;
        for(int i = 0; i < sorted.size(); i++) {
            sum += (long) sorted.get(i).getBid() * (i + 1);
        }
        return String.valueOf(sum);
    }

    @Override
    public String b(Scanner in, boolean isTest) throws Exception {
        List<Hand> hands = new ArrayList<>();
        while(in.hasNext()) {
            String[] l = in.nextLine().split(" ");
            hands.add(new Hand(l[0], Integer.parseInt(l[1]), true));
        }

        List<Hand> sorted = hands.stream().sorted(new HandComparator()).collect(toList());
        long sum = 0;
        for(int i = 0; i < sorted.size(); i++) {
            sum += (long) sorted.get(i).getBid() * (i + 1);
        }
        return String.valueOf(sum);
    }
}

class Hand implements Comparable<Hand> {

    private final List<PokerCard> cards = new ArrayList<>();
    private final int bid;
    private final boolean jokerswild;

    public Hand(String cardstr, int bid, boolean jokerswild) {
        for(char c : cardstr.toCharArray()) {
            switch(c) {
                case '2':
                    cards.add(new PokerCard(Rank.TWO));
                    break;
                case '3':
                    cards.add(new PokerCard(Rank.THREE));
                    break;
                case '4':
                    cards.add(new PokerCard(Rank.FOUR));
                    break;
                case '5':
                    cards.add(new PokerCard(Rank.FIVE));
                    break;
                case '6':
                    cards.add(new PokerCard(Rank.SIX));
                    break;
                case '7':
                    cards.add(new PokerCard(Rank.SEVEN));
                    break;
                case '8':
                    cards.add(new PokerCard(Rank.EIGHT));
                    break;
                case '9':
                    cards.add(new PokerCard(Rank.NINE));
                    break;
                case 'T':
                    cards.add(new PokerCard(Rank.TEN));
                    break;
                case 'J':
                    if(jokerswild) {
                        cards.add(new PokerCard(Rank.JOKER));
                    }
                    else {
                        cards.add(new PokerCard(Rank.JACK));
                    }
                    break;
                case 'Q':
                    cards.add(new PokerCard(Rank.QUEEN));
                    break;
                case 'K':
                    cards.add(new PokerCard(Rank.KING));
                    break;
                case 'A':
                    cards.add(new PokerCard(Rank.ACE));
                    break;
            }
        }
        this.bid = bid;
        this.jokerswild = jokerswild;
    }

    @Override
    public int compareTo(Hand hand) {
        return comparing(Hand::handValue)
                .thenComparing(Hand::cardsValue, Arrays::compare)
                .compare(this, hand);
    }

    public Category handValue() {
        var counts = cards
                .stream()
                .collect(groupingBy(PokerCard::getRank, counting()));
        long jokers = 0;
        if(jokerswild && counts.getOrDefault(Rank.JOKER, 0L) > 0 && counts.size() > 1) {
            jokers = counts.remove(Rank.JOKER);
        }
        var ranks = counts
                .entrySet()
                .stream()
                .sorted(
                comparing(Map.Entry<Rank, Long>::getValue).thenComparing(Map.Entry::getKey).reversed()
        )
                .map(Map.Entry::getKey)
                .toArray(Rank[]::new);

        counts.put(ranks[0], counts.get(ranks[0]) + jokers);

        if(ranks.length == 4) {
            return Category.ONE_PAIR;
        }
        else if(ranks.length == 3) {
            return counts.get(ranks[0]) == 2 ? Category.TWO_PAIR : Category.THREE_OF_A_KIND;
        }
        else if(ranks.length == 2) {
            return counts.get(ranks[0]) == 3 ? Category.FULL_HOUSE : Category.FOUR_OF_A_KIND;
        }
        else if(ranks.length == 1) {
            return Category.FIVE_OF_A_KIND;
        }

        return Category.HIGH_CARD;
    }

    public Rank[] cardsValue() {
        return cards
                .stream()
                .map(PokerCard::getRank)
                .toArray(Rank[]::new);
    }

    public int getBid() {
        return bid;
    }
}

class HandComparator implements Comparator<Hand> {

    @Override
    public int compare(Hand hand, Hand t1) {
        return hand.compareTo(t1);
    }

}

enum Category {HIGH_CARD, ONE_PAIR, TWO_PAIR, THREE_OF_A_KIND, FULL_HOUSE, FOUR_OF_A_KIND, FIVE_OF_A_KIND}

enum Rank {JOKER, TWO, THREE, FOUR, FIVE, SIX, SEVEN, EIGHT, NINE, TEN, JACK, QUEEN, KING, ACE}

class PokerCard {
    public Rank rank;

    public PokerCard(Rank rank) {
        this.rank = rank;
    }

    public Rank getRank() {
        return rank;
    }
}