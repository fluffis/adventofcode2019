package se.fluff.aoc2021.days;

import se.fluff.aoc.AocDay;
import java.util.*;

/**
 * Created by Fluff on 2021-12-03.
 */
public class Day03 extends AocDay {

    public Day03() {

    }

    @Override
    public String a(Scanner in, boolean isTest) throws Exception {


        ArrayList<String> numbers = new ArrayList<>();
        while(in.hasNext()) {
            numbers.add(in.nextLine());
        }

        StringBuilder gamma = new StringBuilder();
        StringBuilder epsilon = new StringBuilder();
        for(int pos = 0; pos < numbers.get(0).length(); pos++) {
            char mostcommon = mostcommon(pos, numbers, 'z');

            if(mostcommon == '0') {
                gamma.append("0");
                epsilon.append("1");
            }
            else if(mostcommon == '1') {
                gamma.append("1");
                epsilon.append("0");
            }
            else
                throw new RuntimeException("Zerocount == onecount");
        }

        int g = Integer.parseInt(gamma.toString(), 2);
        int e = Integer.parseInt(epsilon.toString(), 2);
        return String.valueOf(g * e);
    }

    @Override
    public String b(Scanner in, boolean isTest) {

        ArrayList<String> numbers = new ArrayList<>();
        while(in.hasNext()) {
            numbers.add(in.nextLine());
        }

        ArrayList<String> generator = new ArrayList<>(numbers);
        ArrayList<String> scrubber = new ArrayList<>(numbers);
        for(int pos = 0; pos < numbers.get(0).length(); pos++) {
            char mostcommonatpos = mostcommon(pos, generator, '1');
            for(int numpos = 0; numpos < generator.size(); numpos++) {
                String number = generator.get(numpos);
                if(number.charAt(pos) != mostcommonatpos) {
                    generator.remove(numpos);
                    numpos--;
                }
            }
            if(generator.size() == 1)
                break;
        }

        int genrating = Integer.parseInt(generator.get(0), 2);

        for(int pos = 0; pos < numbers.get(0).length(); pos++) {
            char mostcommonatpos = mostcommon(pos, scrubber, '1');
            for(int numpos = 0; numpos < scrubber.size(); numpos++) {
                String number = scrubber.get(numpos);
                if(number.charAt(pos) == mostcommonatpos) {
                    scrubber.remove(numpos);
                    numpos--;
                }
            }
            if(scrubber.size() == 1)
                break;
        }

        int scrubrating = Integer.parseInt(scrubber.get(0), 2);
        return String.valueOf(genrating * scrubrating);
    }

    private char mostcommon(int pos, ArrayList<String> numbers, char winnerAtEqual) {

        int onecount = 0;
        int zerocount = 0;
        for(String number : numbers) {
            char c = number.charAt(pos);
            if(c == '1')
                onecount++;
            else
                zerocount++;
        }

        if(zerocount > onecount) {
            return '0';
        }
        else if(onecount > zerocount) {
            return '1';
        }
        else
            return winnerAtEqual;

    }
}
