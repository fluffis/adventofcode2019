package se.fluff.aoc;

import java.io.*;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import java.util.zip.GZIPInputStream;

public class Main {

    private static final String projectPath = "/home/fluff/git/adventofcode2019/";
    private static final String basePath = "/home/fluff/git/adventofcode2019/src/se/fluff/";
    private static String prodInput;
    private static String testPath;
    private static String key;

    public static void main(String[] args) throws IOException {

        SimpleDateFormat dayformat = new SimpleDateFormat("dd");
        SimpleDateFormat yearformat = new SimpleDateFormat("yyyy");
        Date d = new Date();
        String day = args.length > 0 ? args[0] : dayformat.format(d);
        String year = args.length > 1 ? args[1] : yearformat.format(d);
        String clazzname = "se.fluff.aoc" + year + ".days.Day" + day;
        prodInput = basePath + "aoc" + year + "/data/" + day + ".in";
        testPath = basePath + "aoc" + year + "/tests/" + day + "/";

        Scanner keyscanner = new Scanner(new File(projectPath + ".cookie"));
        key = keyscanner.nextLine();
        keyscanner.close();

        Class<?> clazz = null;

        try {
            clazz = Class.forName(clazzname);
        }
        catch (ClassNotFoundException e) {
            System.out.println("Class not found: " + e.getLocalizedMessage());
            System.out.println("Should we create and fetch? [y/N]");
            Scanner stdin = new Scanner(System.in);
            if(stdin.hasNext()) {
                String fetch = stdin.nextLine();
                if (fetch.matches("[Yy]"))
                    initDay(year, day);
             }
            stdin.close();
        }


        try {
            if(clazz == null)
                throw new RuntimeException("Class not loaded");

            System.out.println("Running " + clazzname);
            Constructor<?> constructor = clazz.getDeclaredConstructor();
            AocDay aocDay = (AocDay) constructor.newInstance();

            for(String puzzle : new String[] { "a", "b" }) {
                Method method = clazz.getDeclaredMethod(puzzle, Scanner.class, boolean.class);

                long starttime = System.currentTimeMillis();
                if(runTests(testPath, aocDay, puzzle)) {
                    long ttc = System.currentTimeMillis() - starttime;
                    System.out.println("Puzzle " + puzzle + ": All tests successful, running production");
                    System.out.println("Tests execution took " + ttc + " ms");

                    Scanner in = new Scanner(new File(prodInput));
                    starttime = System.currentTimeMillis();
                    System.out.println("Puzzle " + puzzle + ", answer is: " + method.invoke(aocDay, in, false));
                    ttc = System.currentTimeMillis() - starttime;
                    System.out.println("Puzzle execution took " + ttc + " ms");
                    in.close();
                }
                else {
                    System.out.println("Puzzle " + puzzle + ", testing failed, exiting");
                    System.exit(1);
                }

                System.out.println("=======================================");
            }
        } catch (InstantiationException | IllegalAccessException | NoSuchMethodException | InvocationTargetException e) {
            e.printStackTrace();
        }
    }

    public static boolean runTests(String testPath, AocDay aocDay, String puzzle) throws IOException {

        File testDirectory = new File(testPath + puzzle);
        if(!testDirectory.exists() || !testDirectory.isDirectory()) {
            System.out.println("WARNING: Test directory not configured, not running tests");
            return true;
        }
        else if(testDirectory.list().length == 0) {
            System.out.println("WARNING: Test directory is empty, not running tests");
            return true;
        }

        Stream<Path> walk = Files.walk(Paths.get(testPath + puzzle));

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
                    res = aocDay.a(in, true);
                else
                    res = aocDay.b(in, true);
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

    public static void initDay(String year, String day) {
        fetchInput(year, day);
        copyTemplate(year, day);
        createTests();
        System.out.println("Day initialized, happy coding");
        System.exit(0);
    }

    public static void fetchInput(String year, String day) {
        try {
            URL url = new URL("https://adventofcode.com/" + year + "/day/"+ day.replaceFirst("^0", "") + "/input");
            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            con.setRequestMethod("GET");
            con.setDoInput(true);
            con.setRequestProperty("Accept", "text/plain;q=0.9");
            con.setRequestProperty("Accept-Encoding", "gzip");
            con.setRequestProperty("Cookie", "session=" + key);
            con.connect();

            if(con.getResponseCode() == 200) {
                Writer fw = new FileWriter(prodInput);
                InputStream is;
                if("gzip".equals(con.getContentEncoding()))
                    is = new GZIPInputStream(con.getInputStream());
                else
                    is = con.getInputStream();

                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(is));
                String in;
                while((in = bufferedReader.readLine()) != null)
                    fw.write(in + "\n");
                fw.close();
                bufferedReader.close();
            }
            else
                throw new RuntimeException("Retrieving input returned error: " + con.getResponseCode() + ": " + con.getResponseMessage());

            con.disconnect();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static boolean copyTemplate(String year, String day) {
        SimpleDateFormat dformat = new SimpleDateFormat("yyyy-MM-dd");
        Date d = new Date();
        String javafile = basePath + "aoc" + year + "/days/Day" + day + ".java";

        File fin = new File(projectPath + "java.template");

        File fout = new File(javafile);
        if(fout.exists()) {
            System.out.println("Java file '" + javafile + "' already exists, not overwriting");
            return false;
        }

        try {
            Writer fw = new FileWriter(javafile);
            BufferedReader finreader = new BufferedReader(new FileReader(fin));
            String in;
            while((in = finreader.readLine()) != null) {
                in = in
                        .replace("$year", year)
                        .replace("$day", day)
                        .replace("$today", dformat.format(d));

                fw.write(in + "\n");
            }
            fw.close();
            finreader.close();
            return true;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }

    public static void createTests() {
        File f = new File(testPath + "a/");
        if(f.mkdirs()) {
            try {
                f = new File(testPath + "a/1.in");
                f.createNewFile();
                f = new File(testPath + "a/1.out");
                f.createNewFile();
                f = new File(testPath + "b/");
                f.mkdir();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
