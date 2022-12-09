package se.fluff.aoc2022.days;

import se.fluff.aoc.AocDay;

import java.awt.*;
import java.util.*;
import java.util.List;

/**
 * Created by Fluff on 2022-12-08.
 */
public class Day07 extends AocDay {

    public Day07() {

    }

    @Override
    public String a(Scanner in, boolean isTest) throws Exception {
        ArrayDeque<String> cmds = new ArrayDeque<>();
        while(in.hasNext()) {
            cmds.add(in.nextLine());
        }

        Dir root = null;
        Dir current = null;
        while(!cmds.isEmpty()) {
            String cmd = cmds.remove();
            if(cmd.equals("$ cd ..")) {
                current = current.getParent();
            }
            else if(cmd.startsWith("$ cd")) {
                String dir = cmd.replace("$ cd ", "");

                Dir creating = new Dir(dir, current);
                if(current != null) {
                    Dir subdir = current.getSubdir(dir);
                    if(subdir == null)
                        current.addSubdir(dir, creating);
                    else
                        creating = subdir;
                }
                if(dir.equals("/")) {
                    root = creating;
                }
                current = creating;
            }
            else if(cmd.startsWith("$ ls")) {
                while(cmds.peek() != null && !cmds.peek().startsWith("$")) {
                    String[] f = cmds.remove().split(" ");
                    if(f[0].equals("dir")) {
                        current.addSubdir(f[1], new Dir(f[1], current));
                    }
                    else {
                        current.addFile(f[1], Integer.parseInt(f[0]));
                    }
                }
                if(current.getParent() != null)
                    current.getParent().addSubdir(current.getName(), current);
            }
        }

        int sum = 0;
        for(Dir d : recurseforsum(root, new HashSet<>())) {
            sum += d.getSumFilesize();
        }

        return String.valueOf(sum);
    }

    @Override
    public String b(Scanner in, boolean isTest) {

        ArrayDeque<String> cmds = new ArrayDeque<>();
        while(in.hasNext()) {
            cmds.add(in.nextLine());
        }

        Dir root = null;
        Dir current = null;
        while(!cmds.isEmpty()) {
            String cmd = cmds.remove();
            if(cmd.equals("$ cd ..")) {
                current = current.getParent();
            }
            else if(cmd.startsWith("$ cd")) {
                String dir = cmd.replace("$ cd ", "");

                Dir creating = new Dir(dir, current);
                if(current != null) {
                    Dir subdir = current.getSubdir(dir);
                    if(subdir == null)
                        current.addSubdir(dir, creating);
                    else
                        creating = subdir;
                }
                if(dir.equals("/")) {
                    root = creating;
                }
                current = creating;
            }
            else if(cmd.startsWith("$ ls")) {
                while(cmds.peek() != null && !cmds.peek().startsWith("$")) {
                    String[] f = cmds.remove().split(" ");
                    if(f[0].equals("dir")) {
                        current.addSubdir(f[1], new Dir(f[1], current));
                    }
                    else {
                        current.addFile(f[1], Integer.parseInt(f[0]));
                    }
                }
                if(current.getParent() != null)
                    current.getParent().addSubdir(current.getName(), current);
            }
        }

        Set<Dir> dirs = recursealldir(root, new HashSet<>());
        long drivesize = 70000000;
        long requirement = 30000000;
        long occupied = root.getSumFilesize();
        long smallestdelete = Long.MAX_VALUE;
        for(Dir d : dirs) {
            long occupiedafterdelete = occupied - d.getSumFilesize();
            if(drivesize - occupiedafterdelete > requirement) {
                if(d.getSumFilesize() < smallestdelete)
                    smallestdelete = d.getSumFilesize();
            }
        }
        return String.valueOf(smallestdelete);
    }

    public Set<Dir> recursealldir(Dir dir, HashSet<Dir> dirs) {
        dirs.add(dir);
        for(Dir d : dir.getSubdirs())
            dirs.addAll(recursealldir(d, dirs));

        return dirs;

    }

    public Set<Dir> recurseforsum(Dir dir, HashSet<Dir> dirs) {

        for(Dir d : dir.getSubdirs()) {
            if(d.getSumFilesize() < 100000) {
                dirs.add(d);
            }
            dirs.addAll(recurseforsum(d, dirs));
        }
        return dirs;
    }

}

class Dir {

    private Dir parent;
    private HashMap<String,Dir> subdirs = new HashMap<>();
    private HashMap<String, Integer> files = new HashMap<>();
    private String name;

    public Dir(String name, Dir parent) {
        this.name = name;
        this.parent = parent;
    }

    public void addFile(String name, int size) {
        files.put(name, size);
    }

    public void addSubdir(String name, Dir subdir) {
        subdirs.put(name, subdir);
    }

    public Dir getSubdir(String name) {
        return subdirs.get(name);
    }

    public Collection<Dir> getSubdirs() {
        return subdirs.values();
    }

    public String getName() {
        return name;
    }

    public Dir getParent() {
        return parent;
    }

    public int getSumFilesize() {

        int s = 0;
        if(files.values().stream().reduce(Integer::sum).isPresent()) {
            s = files.values().stream().reduce(Integer::sum).get();
        }
        for(Dir sub : subdirs.values()) {
            s += sub.getSumFilesize();
        }
        return s;
    }

}