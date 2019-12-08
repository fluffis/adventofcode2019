package se.fluff.aoc2019;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by Fluff on 2019-12-07.
 */
public class IntCodeMachine {

    private HashMap<Integer, Integer> data;
    private ArrayList<Integer> inputs = new ArrayList<>();
    private ArrayList<Integer> outputs = new ArrayList<>();
    private ArrayList<Integer> seenoutputs = new ArrayList<>();
    private boolean halted = false;
    private int id;
    private int steppos = 0;

    public IntCodeMachine(HashMap<Integer, Integer> data, int id) {
        this.data = (HashMap<Integer, Integer>) data.clone();
        this.id = id;
    }

    public void addInput(int input) {
        inputs.add(input);
    }

    public int read() {
        return outputs.remove(0);
    }

    public List<Integer> getOutputs() {
        List<Integer> outputsAvailable = new ArrayList<>();

        while (!outputs.isEmpty()) {
            outputsAvailable.add(outputs.remove(0));
        }

        return outputsAvailable;
    }

    public List<Integer> getAllSeenOutputs() {
        return seenoutputs;
    }

    public boolean isHalted() {
        return halted;
    }

    public void run() {
        while(true) {

            int opcode = this.data.get(steppos);

            String opcodes = String.format("%05d", opcode);
            opcode = Integer.parseInt(opcodes.substring(opcodes.length() - 2));
            int[] paramMode = new int[opcodes.length() - 2];
            for(int c = opcodes.length() - 3; c >= 0; c--) {
                String pp = opcodes.substring(c, c + 1);
                int p = Integer.parseInt(pp);
                paramMode[c] = p;
            }
            if(opcode == 1) {
                int opval1 = this.data.get(steppos + 1);
                int opval2 = this.data.get(steppos + 2);
                int storeat = this.data.get(steppos + 3);
                int v1 = paramMode[2] == 0 ? this.data.getOrDefault(opval1, 0) : opval1;
                int v2 = paramMode[1] == 0 ? this.data.getOrDefault(opval2, 0) : opval2;
                this.data.put(storeat, v1 + v2);
                steppos += 4;
            }
            else if(opcode == 2) {
                int opval1 = this.data.get(steppos + 1);
                int opval2 = this.data.get(steppos + 2);
                int storeat = this.data.get(steppos + 3);
                int v1 = paramMode[2] == 0 ? this.data.getOrDefault(opval1,0) : opval1;
                int v2 = paramMode[1] == 0 ? this.data.getOrDefault(opval2, 0) : opval2;
                this.data.put(storeat, v1 * v2);
                steppos += 4;
            }
            else if(opcode == 3) {
                int opval1 = this.data.get(steppos + 1);
                if (paramMode[2] == 0) {
                    if(inputs.size() > 0)
                        this.data.put(opval1, inputs.remove(0));
                    else
                        break;
                }
                else
                    this.data.put(opval1, opval1);
                steppos += 2;
            }
            else if(opcode == 4) {
                int opval1 = this.data.get(steppos + 1);
                if(paramMode[2] == 0) {
                    outputs.add(this.data.get(opval1));
                    seenoutputs.add(this.data.get(opval1));
                }
                else {
                    outputs.add(opval1);
                    seenoutputs.add(opval1);
                }
                steppos += 2;
            }
            else if(opcode == 5) {
                int opval1 = this.data.get(steppos + 1);
                int opval2 = this.data.get(steppos + 2);
                int v1 = paramMode[2] == 0 ? this.data.getOrDefault(opval1, 0) : opval1;
                int v2 = paramMode[1] == 0 ? this.data.getOrDefault(opval2, 0) : opval2;
                if(v1 != 0)
                    steppos = v2;
                else
                    steppos += 3;
            }
            else if(opcode == 6) {
                int opval1 = this.data.get(steppos + 1);
                int opval2 = this.data.get(steppos + 2);
                int v1 = paramMode[2] == 0 ? this.data.getOrDefault(opval1, 0) : opval1;
                int v2 = paramMode[1] == 0 ? this.data.getOrDefault(opval2, 0) : opval2;
                if(v1 == 0)
                    steppos = v2;
                else
                    steppos += 3;
            }
            else if(opcode == 7) {
                int opval1 = this.data.get(steppos + 1);
                int opval2 = this.data.get(steppos + 2);
                int opval3 = this.data.get(steppos + 3);
                int v1 = paramMode[2] == 0 ? this.data.get(opval1) : opval1;
                int v2 = paramMode[1] == 0 ? this.data.get(opval2) : opval2;
                this.data.put(opval3, v1 < v2 ? 1 : 0);
                steppos += 4;
            }
            else if(opcode == 8) {
                int opval1 = this.data.get(steppos + 1);
                int opval2 = this.data.get(steppos + 2);
                int opval3 = this.data.get(steppos + 3);
                int v1 = paramMode[2] == 0 ? this.data.getOrDefault(opval1, 0) : opval1;
                int v2 = paramMode[1] == 0 ? this.data.getOrDefault(opval2, 0) : opval2;
                this.data.put(opval3, v1 == v2 ? 1 : 0);
                steppos += 4;
            }
            else if(opcode == 99) {
                this.halted = true;
                break;
            }
        }
    }
}
