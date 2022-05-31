package se.fluff.aoc2021.days;

import se.fluff.aoc.AocDay;
import java.util.*;

/**
 * Created by Fluff on 2021-12-10.
 */
public class Day10 extends AocDay {

    public Day10() {

    }

    @Override
    public String a(Scanner in, boolean isTest) throws Exception {
        int sum = 0;


        while(in.hasNext()) {
            String line = in.nextLine();

            boolean run = true;
            while(run) {

                String inline = line;
                line = line
                    .replaceAll("\\(\\)", "")
                    .replaceAll("\\[\\]", "")
                    .replaceAll("\\{\\}", "")
                    .replaceAll("\\<\\>", "");

                run = !inline.equals(line);
            }

            int points = 0;
            for(int i = 0; i < line.length(); i++) {
                if(line.charAt(i) == ')')
                    points = 3;
                else if(line.charAt(i) == ']')
                    points = 57;
                else if(line.charAt(i) == '}')
                    points = 1197;
                else if(line.charAt(i) == '>')
                    points = 25137;

                if(points > 0) {
                    sum += points;
                    break;
                }
            }
        }
        return String.valueOf(sum);
    }

    @Override
    public String b(Scanner in, boolean isTest) {

        ArrayList<Long> scores = new ArrayList<>();
        while(in.hasNext()) {
            String line = in.nextLine();

            boolean run = true;
            while(run) {

                String inline = line;
                line = line
                    .replaceAll("\\(\\)", "")
                    .replaceAll("\\[\\]", "")
                    .replaceAll("\\{\\}", "")
                    .replaceAll("\\<\\>", "");

                run = !inline.equals(line);
            }

            if(line.contains("]") || line.contains(">") || line.contains(")") || line.contains("}"))
                continue;
            
            long points = 0;
            for(int i = line.length() - 1; i >= 0; i--) {
                points *= 5;
                if(line.charAt(i) == '(')
                    points += 1;
                else if(line.charAt(i) == '[')
                    points += 2;
                else if(line.charAt(i) == '{')
                    points += 3;
                else if(line.charAt(i) == '<')
                    points += 4;
            }

            scores.add(points);
        }

        Collections.sort(scores);

        return String.valueOf(scores.get((int) Math.floor(scores.size()/2)));
    }
}
