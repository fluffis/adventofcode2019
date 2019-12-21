package se.fluff.aoc2019;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.TimeUnit;

/**
 * Created by Fluff on 2019-12-09.
 */
public class LongCodeMachine implements Runnable {

    private HashMap<Integer, Long> data;
    private BlockingQueue<Long> inputs;
    private BlockingQueue<Long> outputs;
    private ArrayList<Long> seenoutputs = new ArrayList<>();
    private boolean halted = false;
    private int id;
    private int steppos = 0;
    private int relbase = 0;

    public LongCodeMachine(HashMap<Integer, Long> data, int id, BlockingQueue<Long> inputs, BlockingQueue<Long> outputs) {
        this.data = (HashMap<Integer, Long>) data.clone();
        this.id = id;
        this.inputs = inputs;
        this.outputs = outputs;
    }

    public void addInput(Long input) {
        inputs.add(input);
    }

    public Long read() {
        try {
            return outputs.poll(1L, TimeUnit.DAYS);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return null;
    }


    public void addInputString(String str) {
        addInputString(str, true);
    }
    public void addInputString(String str, boolean endWithCr) {
        for(char c : str.toCharArray())
            inputs.add((long) (int) c);

        if(endWithCr)
            inputs.add(10L);
    }

    public List<Long> getOutputs() {
        List<Long> outputsAvailable = new ArrayList<>();

        while (!outputs.isEmpty()) {
            try {
                outputsAvailable.add(outputs.take());
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
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
                if(inputs.size() > 0) {
                    try {
                        this.data.put(parameters.get(0).getAddress(), inputs.poll(10L, TimeUnit.DAYS));
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
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