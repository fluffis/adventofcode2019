package se.fluff.aoc2022.days;

import se.fluff.aoc.AocDay;
import java.util.*;

/**
 * Created by Fluff on 2022-12-02.
 */
public class Day02 extends AocDay {

    public Day02() {

    }

    @Override
    public String a(Scanner in, boolean isTest) throws Exception {
        ArrayList<Round> rounds = new ArrayList<Round>();
        while(in.hasNext()) {
            String[] l = in.nextLine().split(" ");
            rounds.add(new Round(l[0], l[1]));
        }

        return String.valueOf(rounds.stream().mapToInt(Round::getScore).sum());
    }

    @Override
    public String b(Scanner in, boolean isTest) {
        ArrayList<Round> rounds = new ArrayList<Round>();
        while(in.hasNext()) {
            String[] l = in.nextLine().split(" ");
            rounds.add(new Round(l[0], l[1]));
        }
        return String.valueOf(rounds.stream().mapToInt(Round::getScoreWithResult).sum());

    }
}

class Round {

    private final String op;
    private final String me;
    private String result = "";
    private int score = 0;

    public Round(String op, String me) {
        this.op = op;
        this.me = me;
    }

    public int getScore() {
        if(op.equals("A")) {
            if(me.equals("X"))
                result = "draw";
            else if(me.equals("Y"))
                result = "win";
            else
                result = "loss";
        }

        if(op.equals("B")) {
            if(me.equals("X"))
                result = "loss";
            else if(me.equals("Y"))
                result = "draw";
            else
                result = "win";
        }

        if(op.equals("C")) {
            if(me.equals("X"))
                result = "win";
            else if(me.equals("Y"))
                result = "loss";
            else
                result = "draw";
        }
        score = 0;
        if(me.equals("X"))
            score += 1;
        else if(me.equals("Y"))
            score += 2;
        else if(me.equals("Z"))
            score += 3;

        if(result.equals("draw"))
            score += 3;
        else if(result.equals("win"))
            score += 6;

        //System.out.println(this.toString());

        return score;
    }

    public int getScoreWithResult() {

        if(op.equals("A")) {
            if(me.equals("X"))
                result = "s";
            else if(me.equals("Y"))
                result = "r";
            else
                result = "p";
        }

        if(op.equals("B")) {
            if(me.equals("X"))
                result = "r";
            else if(me.equals("Y"))
                result = "p";
            else
                result = "s";
        }

        if(op.equals("C")) {
            if(me.equals("X"))
                result = "p";
            else if(me.equals("Y"))
                result = "s";
            else
                result = "r";
        }

        score = 0;
        if(result.equals("r"))
            score += 1;
        else if(result.equals("p"))
            score += 2;
        else if(result.equals("s"))
            score += 3;

        if(me.equals("Y"))
            score += 3;
        else if(me.equals("Z"))
            score += 6;

        //System.out.println(this.toString());

        return score;
    }

    @Override
    public String toString() {
        return "Round{" +
                "op='" + op + '\'' +
                ", me='" + me + '\'' +
                ", result='" + result + '\'' +
                ", score=" + score +
                '}';
    }
}