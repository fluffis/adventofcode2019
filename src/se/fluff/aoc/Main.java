package se.fluff.aoc;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
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

    private static String basePath = "/home/fluff/git/adventofcode2019/src/se/fluff/";

    public static void main(String[] args) throws IOException {

        SimpleDateFormat dayformat = new SimpleDateFormat("dd");
        SimpleDateFormat yearformat = new SimpleDateFormat("yyyy");
        Date d = new Date();
        String day = args.length > 0 ? args[0] : dayformat.format(d);
        String year = args.length > 1 ? args[1] : yearformat.format(d);
        String clazzname = "se.fluff.aoc" + year + ".days.Day" + day;
        String prodInput = basePath + "aoc" + year + "/data/" + day + ".in";

        System.out.println("Running " + clazzname);

        try {
            Class<?> clazz = Class.forName(clazzname);
            Constructor<?> constructor = clazz.getDeclaredConstructor();
            AocDay aocDay = (AocDay) constructor.newInstance();

            for(String puzzle : new String[] { "a", "b" }) {
                Method method = clazz.getDeclaredMethod(puzzle, Scanner.class);

                if(runTests(aocDay, puzzle, day)) {
                    System.out.println("Puzzle " + puzzle + ": All tests successful, running production");
                    Scanner in = new Scanner(new File(prodInput));
                    long starttime = System.currentTimeMillis();
                    System.out.println("Puzzle " + puzzle + ", answer is: " + method.invoke(aocDay, in));
                    long ttc = System.currentTimeMillis() - starttime;
                    System.out.println("Execution took " + ttc + " ms");
                    in.close();
                }
                else {
                    System.out.println("Puzzle " + puzzle + ", testing failed, exiting");
                    System.exit(1);
                }

                System.out.println("=======================================");
            }
        } catch (InstantiationException | IllegalAccessException | ClassNotFoundException | NoSuchMethodException | InvocationTargetException e) {
            e.printStackTrace();
        }
    }

    public static boolean runTests(AocDay aocDay, String puzzle, String day) throws IOException {

        File testDirectory = new File(basePath + "tests/" + day + "/" + puzzle);
        if(!testDirectory.exists() || !testDirectory.isDirectory()) {
            System.out.println("WARNING: Test directory not configured, not running tests");
            return true;
        }
        else if(testDirectory.list().length == 0) {
            System.out.println("WARNING: Test directory is empty, not running tests");
            return true;
        }

        Stream<Path> walk = Files.walk(Paths.get(basePath + "tests/" + day + "/" + puzzle));

        List<String> infiles = walk.filter(Files::isRegularFile)
                .map(Path::toString)
                .filter(x -> x.endsWith(".in"))
                .sorted()
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
            try {
                if (puzzle.equals("a"))
                    res = aocDay.a(in);
                else
                    res = aocDay.b(in);
            }
            catch(Exception e) {
                e.printStackTrace();
            }
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
