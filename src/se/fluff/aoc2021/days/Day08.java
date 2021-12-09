package se.fluff.aoc2021.days;

import se.fluff.aoc.AocDay;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Created by Fluff on 2021-12-08.
 */
public class Day08 extends AocDay {

    public Day08() {

    }

    @Override
    public String a(Scanner in, boolean isTest) throws Exception {
        int c = 0;

        int[] validLengths = {2, 3, 4, 7};

        while(in.hasNext()) {
            String[] row = in.nextLine().split(" \\| ");
            String[] digits = row[1].split(" ");
            for(String d : digits) {
                if(Arrays.stream(validLengths).anyMatch(i -> i == d.length()))
                    c++;
            }
        }
        return String.valueOf(c);
    }

    @Override
    public String b(Scanner in, boolean isTest) {
        int c = 0;

        while(in.hasNext()) {
            HashMap<Integer,String> suspects = new HashMap<>();
            String[] row = in.nextLine().split(" \\| ");
            String[] inputs = row[0].split(" ");
            String[] outputs = row[1].split(" ");
            for(String d : inputs) {
                switch (d.length()) {
                    case 2:
                        suspects.put(1, d);
                        break;
                    case 3:
                        suspects.put(7, d);
                        break;
                    case 4:
                        suspects.put(4, d);
                        break;
                    case 7:
                        suspects.put(8, d);
                        break;
                }
            }

            for(String d : inputs) {

                if(d.length() != 6)
                    continue;

                if(removeCommonLetters(d, suspects.get(1)).length() != 0)
                    suspects.put(6, d);
                else if(removeCommonLetters(d, suspects.get(4)).length() == 0)
                    suspects.put(9, d);
                else if(removeCommonLetters(d, suspects.get(4)).length() != 0)
                    suspects.put(0, d);
            }

            String topright = removeCommonLetters(suspects.get(6), suspects.get(1));
            for(String d : inputs) {

                if(d.length() != 5)
                    continue;

                if(removeCommonLetters(d, suspects.get(1)).length() == 0)
                    suspects.put(3, d);
                else if(d.contains(topright))
                    suspects.put(2, d);
                else
                    suspects.put(5, d);
            }

            suspects.replaceAll((i, v) -> sortString(v));

            StringBuilder sb = new StringBuilder();
            for(String d : outputs) {
                suspects.forEach((key, value) -> {
                    if(value.equals(sortString(d)))
                        sb.append(key);
                });
            }
            c += Integer.parseInt(sb.toString());
        }
        return String.valueOf(c);
    }

    private String sortString(String str) {
        char[] ca = str.toCharArray();
        Arrays.sort(ca);
        return new String(ca);
    }

    private String removeCommonLetters(String lookFor, String removeFrom) {
        for (char c : lookFor.toCharArray())
            removeFrom = removeFrom.replaceAll(String.valueOf(c), "");

        return removeFrom;
    }
}
