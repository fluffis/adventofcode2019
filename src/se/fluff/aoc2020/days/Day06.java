package se.fluff.aoc2020.days;

import se.fluff.aoc.AocDay;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

/**
 * Created by Fluff on 2020-12-06.
 */
public class Day06 extends AocDay {

    public Day06() {

    }

    @Override
    public String a(Scanner in) {

        int count = 0;
        ArrayList<GroupOfForms> gofs = new ArrayList<>();
        GroupOfForms gof = new GroupOfForms();
        while(in.hasNext()) {
            String s = in.nextLine();

            if(s.length() == 0) {
                count += gof.getYesQuestions();
                gofs.add(gof);
                gof = new GroupOfForms();
            }
            else {
                gof.addForm(s);
            }
        }

        count += gof.getYesQuestions();
        return String.valueOf(count);
    }

    @Override
    public String b(Scanner in) {
        int count = 0;
        ArrayList<GroupOfForms> gofs = new ArrayList<>();
        GroupOfForms gof = new GroupOfForms();
        while(in.hasNext()) {
            String s = in.nextLine();

            if(s.length() == 0) {
                count += gof.countCombinedYes();
                gofs.add(gof);
                gof = new GroupOfForms();
            }
            else {
                gof.addForm(s);
            }
        }

        count += gof.countCombinedYes();
        return String.valueOf(count);
    }
}

class GroupOfForms {
    private ArrayList<String> forms = new ArrayList<>();

    public void addForm(String s) {
        forms.add(s);
    }

    public int getYesQuestions() {
        ArrayList<Character> distinctQuestions = new ArrayList<>();
        for(String s : forms) {
            for(char c : s.toCharArray()) {
                if(!distinctQuestions.contains(c))
                    distinctQuestions.add(c);
            }
        }
        return distinctQuestions.size();
    }

    public long countCombinedYes() {
        HashMap<Character, Integer> combinedQuestions = new HashMap<>();
        for(String s : forms) {
            for(char c : s.toCharArray()) {
                combinedQuestions.put(c, combinedQuestions.getOrDefault(c, 0) + 1);
            }
        }
        return combinedQuestions
                .values()
                .stream()
                .filter(v -> v == forms.size())
                .count();
    }
}