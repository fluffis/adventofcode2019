package se.fluff.aoc2021.days;

import se.fluff.aoc.AocDay;

import java.math.BigInteger;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Created by Fluff on 2022-05-29.
 */
public class Day16 extends AocDay {

    public Day16() {

    }

    @Override
    public String a(Scanner in, boolean isTest) throws Exception {

        String hex = in.nextLine();
        StringBuilder sb = new StringBuilder();
        for(int i = 0; i < hex.length(); i++) {
            String f = Integer.toBinaryString(Integer.parseInt("" + hex.charAt(i), 16));
            sb.append("0".repeat(Math.max(0, 4 - f.length())));
            sb.append(f);
        }

        Packet p = new Packet(sb.toString());

        return String.valueOf(p.getComputedVersion());
    }

    @Override
    public String b(Scanner in, boolean isTest) {
        String hex = in.nextLine();
        StringBuilder sb = new StringBuilder();
        for(int i = 0; i < hex.length(); i++) {
            String f = Integer.toBinaryString(Integer.parseInt("" + hex.charAt(i), 16));
            sb.append("0".repeat(Math.max(0, 4 - f.length())));
            sb.append(f);
        }

        Packet p = new Packet(sb.toString());

        return String.valueOf(p.getTypedValue());
    }
}

class Packet {
    private int version = 0;
    private int type = 0;
    private int lengthType;
    private ArrayList<String> groups = new ArrayList<>();
    private ArrayList<Packet> subpackets = new ArrayList<>();
    private String payload = "";

    public Packet(String bitstr) {
        payload = bitstr;
        version = Integer.parseInt(payload.substring(0, 3), 2);
        type = Integer.parseInt(payload.substring(3, 6), 2);
        if(type == 4) {
            int i = 6;
            while(i + 5 <= payload.length()) {
                groups.add(payload.substring(i, i + 5));
                if(payload.charAt(i) == '0')
                    break;
                else
                    i += 5;
            }
        }
        else {
            lengthType = payload.charAt(6) - '0';
            if(lengthType == 0) {
                int len = Integer.parseInt(payload.substring(7, 22), 2);

                String subpayload = payload.substring(22, 23 + len);
                int u = 0;
                while(u < len) {
                    Packet p = new Packet(subpayload);
                    subpackets.add(p);
                    subpayload = subpayload.substring(p.getLength());
                    u += p.getLength();
                }
            }
            else if(lengthType == 1) {
                int count = Integer.parseInt(payload.substring(7, 18), 2);
                String subpayload = payload.substring(18);
                int u = 0;
                while(u < count) {
                    Packet p = new Packet(subpayload);
                    subpackets.add(p);
                    subpayload = subpayload.substring(p.getLength());
                    u++;
                }
            }
        }
    }

    public int getComputedVersion() {
        return getVersion() + subpackets.stream().mapToInt(Packet::getComputedVersion).sum();
    }

    public int getVersion() {
        return version;
    }

    public int getType() {
        return type;
    }

    public Long getTypedValue() {
        Long sum = 0L;
        if(type == 0)
            sum += subpackets.stream().mapToLong(Packet::getTypedValue).sum();
        else if(type == 1)
            sum = subpackets.stream().mapToLong(Packet::getTypedValue).reduce(1L, (a, b) -> a * b);
        else if(type == 2)
            sum = subpackets.stream().mapToLong(Packet::getTypedValue).min().getAsLong();
        else if(type == 3)
            sum = subpackets.stream().mapToLong(Packet::getTypedValue).max().getAsLong();
        else if(type == 4)
            sum = getGroupValue();
        else if(type == 5) {
            if(subpackets.get(0).getTypedValue() > subpackets.get(1).getTypedValue())
                sum = 1L;
        }
        else if(type == 6) {
            if(subpackets.get(0).getTypedValue() < subpackets.get(1).getTypedValue())
                sum = 1L;
        }
        else if(type == 7) {
            if(Objects.equals(subpackets.get(0).getTypedValue(), subpackets.get(1).getTypedValue()))
                sum = 1L;
        }
        return sum;
    }

    public int getLength() {
        int l = 6;
        if(type == 4)
            l += groups.size() * 5;
        else {
            if (lengthType == 0)
                l += 16;
            else
                l += 12;
        }

        l += subpackets.stream().mapToInt(Packet::getLength).sum();
        return l;
    }

    public Long getGroupValue() {
        String gv = groups.stream().map(s -> s.substring(1)).collect(Collectors.joining(""));

        return Long.parseLong(gv, 2);
    }

    @Override
    public String toString() {
        return "Packet{" +
                "version=" + version +
                ", type=" + type +
                ", groups=" + groups +
                ", subpackets=" + subpackets +
                ", payload='" + payload + '\'' +
                '}';
    }
}