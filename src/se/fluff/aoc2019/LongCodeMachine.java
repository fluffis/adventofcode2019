package se.fluff.aoc2019;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by Fluff on 2019-12-09.
 */
public class LongCodeMachine {

    private HashMap<Integer, Long> data;
    private ArrayList<Long> inputs = new ArrayList<>();
    private ArrayList<Long> outputs = new ArrayList<>();
    private ArrayList<Long> seenoutputs = new ArrayList<>();
    private boolean halted = false;
    private int id;
    private int steppos = 0;
    private int relbase = 0;

    public LongCodeMachine(HashMap<Integer, Long> data, int id) {
        this.data = (HashMap<Integer, Long>) data.clone();
        this.id = id;
    }

    public void addInput(Long input) {
        inputs.add(input);
    }

    public Long read() {
        return outputs.remove(0);
    }

    public List<Long> getOutputs() {
        List<Long> outputsAvailable = new ArrayList<>();

        while (!outputs.isEmpty()) {
            outputsAvailable.add(outputs.remove(0));
        }

        return outputsAvailable;
    }

    public List<Long> getAllSeenOutputs() {
        return seenoutputs;
    }

    public boolean isHalted() {
        return halted;
    }

    public void run() {
        while(true) {

            Long opcode = this.data.get(steppos);

            String opcodes = String.format("%05d", opcode);
            opcode = Long.parseLong(opcodes.substring(opcodes.length() - 2));


            int[] paramMode = new int[opcodes.length() - 2];
            //List<MachineParam> parameters = new ArrayList<>();
            HashMap<Integer, MachineParam> parameters = new HashMap<>();
            for(int c = opcodes.length() - 3; c >= 0; c--) {
                int p = Integer.parseInt(opcodes.substring(c, c + 1));
                int parampos = opcodes.length() - 2 - c;
                MachineParam param = null;
                if(p == 0)
                    param = new MachineParam(p, this.data.getOrDefault(steppos + parampos, 0L).intValue(), this.data);
                else if(p == 1)
                    param = new MachineParam(p, steppos + parampos, this.data);
                else if(p == 2)
                    param = new MachineParam(p, Math.toIntExact(this.data.getOrDefault(steppos + parampos, 0L).intValue() + relbase), this.data);
                parameters.put(opcodes.length() - 3 - c, param);
            }

            if(opcode == 1) {
                long v1 = parameters.get(0).getValue();
                long v2 = parameters.get(1).getValue();
                long storeat = parameters.get(2).getAddress();

                this.data.put((int) storeat, v1 + v2);
                steppos += 4;
            }
            else if(opcode == 2) {
                long v1 = parameters.get(0).getValue();
                long v2 = parameters.get(1).getValue();
                long storeat = parameters.get(2).getAddress();

                this.data.put((int) storeat, v1 * v2);

                steppos += 4;
            }
            else if(opcode == 3) {
                if(inputs.size() > 0)
                    this.data.put(parameters.get(0).getAddress(), inputs.remove(0));
                else
                    break;

                steppos += 2;
            }
            else if(opcode == 4) {
                outputs.add(parameters.get(0).getValue());
                steppos += 2;
            }
            else if(opcode == 5) {
                long v1 = parameters.get(0).getValue();
                long v2 = parameters.get(1).getValue();
                if(v1 != 0)
                    steppos = (int) v2;
                else
                    steppos += 3;
            }
            else if(opcode == 6) {
                long v1 = parameters.get(0).getValue();
                long v2 = parameters.get(1).getValue();
                if(v1 == 0)
                    steppos = (int) v2;
                else
                    steppos += 3;
            }
            else if(opcode == 7) {
                long v1 = parameters.get(0).getValue();
                long v2 = parameters.get(1).getValue();
                int storeat = parameters.get(2).getAddress();

                this.data.put(storeat, v1 < v2 ? 1L : 0L);
                steppos += 4;
            }
            else if(opcode == 8) {
                long v1 = parameters.get(0).getValue();
                long v2 = parameters.get(1).getValue();
                int storeat = parameters.get(2).getAddress();

                this.data.put(storeat, v1 == v2 ? 1L : 0L);
                steppos += 4;
            }
            else if(opcode == 9) {
                relbase += parameters.get(0).getValue();
                steppos += 2;
            }
            else if(opcode == 99) {
                this.halted = true;
                break;
            }
            else {
                System.out.println("Unknown opcode");
            }
        }
    }
}