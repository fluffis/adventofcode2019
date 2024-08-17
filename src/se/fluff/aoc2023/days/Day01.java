package se.fluff.aoc2023.days;

import se.fluff.aoc.AocDay;
import java.util.*;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by Fluff on 2023-12-01.
 */
public class Day01 extends AocDay {

    public Day01() {

    }

    @Override
    public String a(Scanner in, boolean isTest) throws Exception {
        Pattern p = Pattern.compile("\\d");
        int sum = 0;
        while(in.hasNext()) {
            String s = in.nextLine();

            Matcher m = p.matcher(s);
            ArrayList<String> mm = new ArrayList<>();
            while(m.find()) {
                mm.add(m.group());
            }

            sum += Integer.parseInt(mm.get(0) + mm.get(mm.size() - 1));

        }

        return String.valueOf(sum);
    }

    @Override
    public String b(Scanner in, boolean isTest) {
        HashMap<String, String> numbers = new HashMap<>();
        numbers.put("zero", "0");
        numbers.put("one", "1");
        numbers.put("two", "2");
        numbers.put("three", "3");
        numbers.put("four", "4");
        numbers.put("five", "5");
        numbers.put("six", "6");
        numbers.put("seven", "7");
        numbers.put("eight", "8");
        numbers.put("nine", "9");

        Pattern p = Pattern.compile("\\d");
        int sum = 0;
        while(in.hasNext()) {
            String s = in.nextLine();
            System.out.println(s);

            for(int i = 0; i < s.length(); i++) {
                for (Map.Entry<String, String> e : numbers.entrySet()) {
                    if(s.substring(i).startsWith(e.getKey())) {
                        String replacement = e.getKey().substring(0, 2) + e.getValue() + e.getKey().substring(2);
                        s = s.replace(e.getKey(), replacement);
                        i++;
                    }
                }
            }

            Matcher m = p.matcher(s);
            ArrayList<String> mm = new ArrayList<>();
            while(m.find()) {
                mm.add(m.group());
            }

            sum += Integer.parseInt(mm.get(0) + mm.get(mm.size() - 1));
            System.out.println(Integer.parseInt(mm.get(0) + mm.get(mm.size() - 1)));
            System.out.println("---");
        }

        return String.valueOf(sum);
    }
}
