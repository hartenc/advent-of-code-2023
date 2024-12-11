package chris2024;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.*;
import java.util.stream.Collectors;

public class Day2 {
    public static void main(String[] args) {
        System.out.println(puzzel1());
        System.out.println(puzzel2());
    }

    private static String puzzel1() {
        Long totaal = 0L;
        try (BufferedReader bufferedReader = new BufferedReader(new FileReader("puzzel2.txt"))) {
            String regel;
            while ((regel = bufferedReader.readLine()) != null) {
                List<Integer> parts = Arrays.stream(regel.split(" ")).map(Integer::parseInt).toList();
                Boolean dir = null;
                for(int x=0; x< parts.size() - 1;x++) {
                    int diff = parts.get(x) - parts.get(x+1);
                    if (diff == 0) break;
                    if(dir == null) {
                        if (diff < 0) dir = false; else dir = true;
                    }
                    if (dir == false && (diff < -3 || diff > 0)) break;
                    if (dir == true && (diff > 3 || diff < 0)) break;
                    if (x == parts.size() - 2) {
                        totaal++;
                    }
                }
            }

            return totaal.toString();
        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }
    }

    private static String puzzel2() {
        Long totaal = 0L;
        List<List<Integer>> correct = new ArrayList<>();
        try (BufferedReader bufferedReader = new BufferedReader(new FileReader("puzzel2.txt"))) {
            String regel;
            while ((regel = bufferedReader.readLine()) != null) {
                List<Integer> parts = Arrays.stream(regel.split(" ")).map(Integer::parseInt).collect(Collectors.toList());
                if (checkCorrect(parts)) {
                    totaal++;
                    correct.add(parts);
                } else {
                    for(int x=0; x< parts.size(); x++) {
                        List<Integer> newParts = new ArrayList<>(parts);
                        newParts.remove(x);
                        if (checkCorrect(newParts)) {
                            totaal++;
                            correct.add(newParts);
                            break;
                        }
                    }
                }
            }
            Long totaal2 = 0L;
            for(int x=0;x< correct.size();x++) {
                if(checkCorrect(correct.get(x))) totaal2++;
            }
            System.out.println(totaal2);

            return totaal.toString();
        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }
    }

    public static boolean checkCorrect(List<Integer> parts) {
        Boolean dir = null;
        for(int x=0; x< parts.size() - 1;x++) {
            int diff = parts.get(x) - parts.get(x+1);
            if (diff == 0) break;
            if(dir == null) {
                if (diff < 0) dir = false; else dir = true;
            }
            if (dir == false && (diff < -3 || diff > 0)) break;
            if (dir == true && (diff > 3 || diff < 0)) break;
            if (x == parts.size() - 2) {
                return true;
            }
        }
        return false;
    }

}

