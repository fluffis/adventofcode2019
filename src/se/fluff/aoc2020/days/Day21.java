package se.fluff.aoc2020.days;

import se.fluff.aoc.AocDay;

import java.util.*;
import java.util.stream.Collectors;

/**
 * Created by Fluff on 2020-12-21.
 */
public class Day21 extends AocDay {

    public Day21() {

    }

    @Override
    public String a(Scanner in, boolean isTest) {
        ArrayList<Food> foods = new ArrayList<>();
        HashSet<String> allergens = new HashSet<>();
        HashSet<String> allingredients = new HashSet<>();
        while(in.hasNext()) {
            String s = in.nextLine();
            Food f = new Food(s);
            foods.add(f);
            allergens.addAll(f.getAllergens());
            allingredients.addAll(f.getIngredients());
        }

        HashMap<String,ArrayList<String>> amap = new HashMap<>();
        for(String allergen : allergens) {
            ArrayList<Food> foodsWithA = foods
                    .stream()
                    .filter(f -> f.containsAllergen(allergen))
                    .collect(Collectors.toCollection(ArrayList::new));
            HashMap<String,Integer> commonIngredients = new HashMap<>();
            amap.put(allergen, new ArrayList<>());
            for(Food f : foodsWithA) {
                for(String s : f.getIngredients()) {
                    commonIngredients.put(s, commonIngredients.getOrDefault(s, 0) + 1);
                    if(commonIngredients.get(s) == foodsWithA.size())
                        amap.get(allergen).add(s);

                }
            }
        }

        while(true) {
            Optional<Map.Entry<String, ArrayList<String>>> optentry = amap
                    .entrySet()
                    .stream()
                    .filter(e -> e.getValue().size() == 1)
                    .findFirst();
            if(optentry.isEmpty())
                break;

            Map.Entry<String, ArrayList<String>> entry = optentry.get();
            String ing = entry.getValue().get(0);
            allingredients.remove(ing);
            amap.values().forEach(ings -> ings.remove(ing));

        }

        return String.valueOf(foods.stream().mapToInt(f -> f.containsIngredients(allingredients)).sum());
    }

    @Override
    public String b(Scanner in, boolean isTest) {
        ArrayList<Food> foods = new ArrayList<>();
        HashSet<String> allergens = new HashSet<>();
        while (in.hasNext()) {
            String s = in.nextLine();
            Food f = new Food(s);
            foods.add(f);
            allergens.addAll(f.getAllergens());
        }

        HashMap<String,ArrayList<String>> amap = new HashMap<>();
        for(String allergen : allergens) {
            ArrayList<Food> foodsWithA = foods
                    .stream()
                    .filter(f -> f.containsAllergen(allergen))
                    .collect(Collectors.toCollection(ArrayList::new));
            HashMap<String,Integer> commonIngredients = new HashMap<>();
            amap.put(allergen, new ArrayList<>());
            for(Food f : foodsWithA) {
                for(String s : f.getIngredients()) {
                    commonIngredients.put(s, commonIngredients.getOrDefault(s, 0) + 1);
                    if(commonIngredients.get(s) == foodsWithA.size())
                        amap.get(allergen).add(s);

                }
            }
        }

        HashMap<String, String> pairs = new HashMap<>();
        while (true) {
            Optional<Map.Entry<String, ArrayList<String>>> optentry = amap.entrySet().stream().filter(e -> e.getValue().size() == 1).findFirst();
            if (optentry.isEmpty())
                break;

            Map.Entry<String, ArrayList<String>> entry = optentry.get();
            String ing = entry.getValue().get(0);
            pairs.put(entry.getKey(), ing);
            amap.values().forEach(ings -> ings.remove(ing));
        }

        return Arrays.stream(pairs.keySet().stream().sorted().toArray(String[]::new))
                .map(pairs::get)
                .collect(Collectors.joining(","));
    }

    public class Food {
        HashSet<String> ingredients = new HashSet<>();
        HashSet<String> allergens = new HashSet<>();

        public Food(String s) {
            String[] d = s.split("\\(contains ");
            ingredients.addAll(Arrays.asList(d[0].split(" ")));

            d[1] = d[1].replaceAll("\\)", "");
            allergens.addAll(Arrays.asList(d[1].split("\\, ")));

        }

        public HashSet<String> getAllergens() {
            return allergens;
        }

        public HashSet<String> getIngredients() {
            return ingredients;
        }

        public boolean containsAllergen(String a) {
            return allergens.contains(a);
        }

        public int containsIngredients(HashSet<String> inset) {
            HashSet<String> copy = new HashSet<>(inset);
            copy.retainAll(ingredients);
            return copy.size();
        }
    }
}
