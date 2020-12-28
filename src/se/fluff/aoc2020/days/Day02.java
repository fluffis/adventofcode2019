package se.fluff.aoc2020.days;

import se.fluff.aoc.AocDay;

import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by Fluff on 2020-12-02.
 */
public class Day02 extends AocDay {

    public Day02() {

    }

    @Override
    public String a(Scanner in, boolean isTest) {

        Pattern p = Pattern.compile("(\\d+)-(\\d+) (\\w): (.*)");
        int correct = 0;

        while(in.hasNext()) {
            String l = in.nextLine();
            Matcher m = p.matcher(l);
            if(m.find()) {
                PasswordEntry e = new PasswordEntry();
                e.setMin(Integer.parseInt(m.group(1)));
                e.setMax(Integer.parseInt(m.group(2)));
                e.setLetter(m.group(3).charAt(0));
                e.setPassword(m.group(4));
                if(e.testPassword())
                    correct++;
            }
        }

        return String.valueOf(correct);
    }

    @Override
    public String b(Scanner in, boolean isTest) {

        Pattern p = Pattern.compile("(\\d+)-(\\d+) (\\w): (.*)");
        int correct = 0;

        while(in.hasNext()) {
            String l = in.nextLine();
            Matcher m = p.matcher(l);
            if(m.find()) {
                PasswordEntry e = new PasswordEntry();
                e.setMin(Integer.parseInt(m.group(1)));
                e.setMax(Integer.parseInt(m.group(2)));
                e.setLetter(m.group(3).charAt(0));
                e.setPassword(m.group(4));
                if(e.testPasswordPosition())
                    correct++;
            }
        }

        return String.valueOf(correct);
    }
}

class PasswordEntry {
    private int min;
    private int max;
    private char letter;
    private String password;

    public int getMin() {
        return min;
    }

    public void setMin(int min) {
        this.min = min;
    }

    public int getMax() {
        return max;
    }

    public void setMax(int max) {
        this.max = max;
    }

    public char getLetter() {
        return letter;
    }

    public void setLetter(char letter) {
        this.letter = letter;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public boolean testPassword() {
        long c = this.getPassword()
                .chars()
                .filter(ch -> ch == this.getLetter())
                .count();

        return c >= this.getMin() && c <= this.getMax();
    }

    public boolean testPasswordPosition() {
        char first = this.getPassword().charAt(this.getMin() - 1);
        char second = this.getPassword().charAt(this.getMax() - 1);
        return first == this.getLetter() ^ second == this.getLetter();
    }
}