package se.fluff.aoc2020.days;

import se.fluff.aoc.AocDay;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by Fluff on 2020-12-14.
 */
public class Day14 extends AocDay {

    public Day14() {

    }

    @Override
    public String a(Scanner in, boolean isTest) {

        HashMap<Integer,Long> memory = new HashMap<>();
        Pattern p = Pattern.compile("mem\\[(\\d+)\\] = (\\d+)");

        Mask mask = new Mask("");
        while(in.hasNext()) {
            String s = in.nextLine();
            if(s.startsWith("mask =")) {
                s = s.replace("mask = ", "");
                mask = new Mask(s);
            }
            else if(s.startsWith("mem[")) {
                Matcher m = p.matcher(s);
                if(m.find())
                    memory.put(Integer.parseInt(m.group(1)), mask.maskMemory(Integer.parseInt(m.group(2))));
                else
                    System.out.println("No match");
            }
        }
        return String.valueOf(memory.values().stream().reduce(0L, Long::sum));
    }

    @Override
    public String b(Scanner in, boolean isTest) {

        HashMap<Long,Long> memory = new HashMap<>();
        Pattern p = Pattern.compile("mem\\[(\\d+)\\] = (\\d+)");

        Mask mask = new Mask("");
        while(in.hasNext()) {
            String s = in.nextLine();
            if(s.startsWith("mask =")) {
                s = s.replace("mask = ", "");
                mask = new Mask(s);
            }
            else if(s.startsWith("mem[")) {
                Matcher m = p.matcher(s);
                if(m.find()) {
                    Set<Long> memaddrs = mask.decodeAddress(Integer.parseInt(m.group(1)));
                    for(Long l : memaddrs)
                        memory.put(l, Long.parseLong(m.group(2)));

                }
                else
                    System.out.println("No match");
            }
        }
        return String.valueOf(memory.values().stream().reduce(0L, Long::sum));
    }
}

class Mask {

    private char[] mask;

    public Mask(String m) {
        mask = m.toCharArray();
    }

    public long maskMemory(int value) {
        char[] sv = String.format("%36s", Integer.toBinaryString(value)).replaceAll(" ", "0").toCharArray();
        for(int i = 0; i < mask.length; i++) {
            if(mask[i] == 'X')
                continue;

            sv[i] = mask[i];
        }
        return Long.parseLong(new String(sv), 2);
    }

    public Set<Long> decodeAddress(int address) {
        char[] bits = String.format("%36s", Integer.toBinaryString(address))
                .replaceAll(" ", "0")
                .toCharArray();
        return addressMatrix(bits, 0);
    }

    private Set<Long> addressMatrix(char[] bits, int i) {
        Set<Long> set = new HashSet<>();
        int next = i + 1;
        if (i == bits.length) {
            set.add(Long.parseLong(new String(bits), 2));
            return set;
        }

        if (mask[i] == '0')
            set.addAll(addressMatrix(bits, next));
        else if (mask[i] == '1' || mask[i] == 'X') {
            bits[i] = '1';
            set.addAll(addressMatrix(bits, next));
            if (mask[i] == 'X') {
                bits[i] = '0';
                set.addAll(addressMatrix(bits, next));
            }
        }
        return set;
    }

}