package se.fluff.aoc2019;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Main {

    private static String basePath = "/home/fluff/git/advent_of_code_2019/out/production/advent_of_code_2019/se/fluff/aoc2019/";

    public static void main(String[] args) throws IOException {

        SimpleDateFormat sdf = new SimpleDateFormat("dd");
        Date d = new Date();
        String clazzname = "se.fluff.aoc2019.days.Day";
        String prodInput = basePath + "data/";
        String day = args.length > 0 ? args[0] : sdf.format(d);

        clazzname += day;
        prodInput += day + ".in";

        System.err.println("Running " + clazzname);

        try {
            Class<?> clazz = Class.forName(clazzname);
            AocDay aocDay = (AocDay) clazz.newInstance();

            if(runTests(aocDay, "a", day)) {
                System.out.println("For puzzle A: All tests successful, running production");
                Scanner ina = new Scanner(new File(prodInput));
                System.out.println("For puzzle A, answer is: " + aocDay.a(ina));
                ina.close();
            }
            else {
                System.out.println("For puzzle A, testing failed, exiting");
                System.exit(1);
            }
            System.out.println("=======================================");
            if(runTests(aocDay, "b", day)) {
                System.out.println("For puzzle B: All tests successful, running production");
                Scanner inb = new Scanner(new File(prodInput));
                System.out.println("For puzzle B, answer is: " + aocDay.b(inb));
                inb.close();
            }
            else {
                System.out.println("For puzzle B, testing failed, exiting");
                System.exit(1);
            }

        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public static boolean runTests(AocDay aocDay, String puzzle, String day) throws IOException {

        Stream<Path> walk = Files.walk(Paths.get(basePath + "tests/" + day + "/" + puzzle));

        List<String> infiles = walk.filter(Files::isRegularFile)
                .map(Path::toString)
                .filter(x -> x.endsWith(".in"))
                .collect(Collectors.toList());

        System.out.println("Running " + infiles.size() + " tests for puzzle " + puzzle);

        int success = 0;
        for(String infile : infiles) {
            File i = new File(infile);
            File o = new File(infile.replace(".in", ".out"));
            Scanner out = new Scanner(o);
            String expectedResult = "";
            if(out.hasNext())
                expectedResult = out.nextLine();


            Scanner in = new Scanner(i);
            String res = "";
            if(puzzle.equals("a"))
                res = aocDay.a(in);
            else
                res = aocDay.b(in);

            if(expectedResult.equals(res)) {
                System.out.println("SUCCESS: Test " + i.getName() + " returned " + res);
                success++;
            }
            else {
                System.err.println("ERROR: Test " + i.getName() + " returned " + res + " expected result " + expectedResult);
            }
        }

        return success == infiles.size();

    }
}
