package chris;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;


public class Day21 {
    private static List<List<Character>> field = new ArrayList<>();

    public static void main(String[] args) {
//        System.out.println(puzzel1());
        System.out.println(puzzel2());
    }

    // 0 = boven 1 = rechts 2=onder 3 = links
    private static String puzzel1() {
        try (BufferedReader bufferedReader = new BufferedReader(new FileReader("puzzel21a.txt"))) {

            String regel;
            while ((regel = bufferedReader.readLine()) != null) {

                List<Character> fill = new ArrayList<>();
                for (int x = 0; x < regel.length(); x++) {
                    if (regel.charAt(x) == 'S') {
                        fill.add('O');
                    } else {
                        fill.add(regel.charAt(x));
                    }
                }
                field.add(fill);
            }
            for (int it = 0; it < 50; it++) {
                List<List<Character>> newField = copyField(field);
                for (int y = 0; y < field.size(); y++) {
                    for (int x = 0; x < field.get(0).size(); x++) {
                        if (field.get(y).get(x) == 'O') {
                            if (x != 0 && field.get(y).get(x - 1) != '#') newField.get(y).set(x - 1, 'O');
                            if (y != 0 && field.get(y - 1).get(x) != '#') newField.get(y - 1).set(x, 'O');
                            if (x < field.get(0).size() - 1 && field.get(y).get(x + 1) != '#')
                                newField.get(y).set(x + 1, 'O');
                            if (y < field.size() - 1 && field.get(y + 1).get(x) != '#') newField.get(y + 1).set(x, 'O');
                            newField.get(y).set(x, '.');
                        }
                    }
                }
                field = copyField(newField);
            }
            Long count = 0L;
            for (int r = 0; r < field.size(); r++) {
                count += field.get(r).stream().filter(c -> c == 'O').count();
            }
            return count.toString();
        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }
    }

    private static String puzzel2() {
        field = new ArrayList<>();
        try (BufferedReader bufferedReader = new BufferedReader(new FileReader("puzzel21.txt"))) {

            String regel;
            while ((regel = bufferedReader.readLine()) != null) {

                List<Character> fill = new ArrayList<>();
                for (int x = 0; x < regel.length(); x++) {
                    if (regel.charAt(x) == 'S') {
                        fill.add('1');
                    } else {
                        if(regel.charAt(x) == '#') {
                            fill.add(regel.charAt(x));
                        } else {
                            fill.add((char)0);
                        }
                    }
                }
                field.add(fill);
            }
            for (int it = 0; it < 6; it++) {
                List<List<Character>> newField = copyField(field);
                for (int y = 0; y < field.size(); y++) {
                    for (int x = 0; x < field.get(0).size(); x++) {
                            if(field.get(y).get(x)==0) continue;
                            if(field.get(y).get(x) > 0 && field.get(y).get(x) != '#') {
                                if (field.get(y).get((x - 1) % field.size()) != '#') {
                                    newField.get(y).set((x - 1) % field.size(), (field.get(y).get((x-1)  + 1)));
                                }
                                if (field.get((y - 1) % field.size()).get(x) != '#') {
                                    newField.get((y-1) % field.size()).set(x, (field.get((y-1) % field.size()).get(x)));
                                }
                                if (field.get(y).get((x + 1) % field.size()) != '#') {
                                    newField.get(y).set((x + 1) % field.size(), (field.get(y).get((x+1)  + 1)));
                                }

                                if (field.get((y + 1) % field.size()).get(x) != '#') {
                                    newField.get((y+1) % field.size()).set(x, (field.get((y+1) % field.size()).get(x)));
                                }
                                newField.get(y).set(x,(char)0);
                            }
                    }
                }
                field = copyField(newField);
            }
            Long val = 0L;
            for (int r = 0; r < field.size(); r++) {
                for (int r2=0;r2<field.get(0).size();r2++) {
                    if(field.get(r).get(r2) != '#')
                        val += field.get(r).get(r2);
                }
            }
            return val.toString();
        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }
    }

    private static List<List<Character>> copyField(List<List<Character>> input) {
        List<List<Character>> newField = new ArrayList<>();
        for (int y = 0; y < input.size(); y++) {
            newField.add(new ArrayList<>());
            for (int x = 0; x < input.get(0).size(); x++) {
                newField.get(y).add(input.get(y).get(x));
            }
        }
        return newField;
    }


}
