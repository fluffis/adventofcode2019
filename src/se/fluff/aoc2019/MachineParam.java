package se.fluff.aoc2019;

import java.util.Map;

/**
 * Created by Fluff on 2019-12-09.
 */
public class MachineParam {

    private int mode;
    private int address;
    private Map<Integer, Long> intcode;

    public MachineParam(int mode, int address, Map<Integer, Long> intcode) {
        this.mode = mode;
        this.address = address;
        this.intcode = intcode;
    }

    public int getAddress() {
        return this.address;
    }

    public long getValue() {
        return intcode.getOrDefault(address, 0L);
    }


}
