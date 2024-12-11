package chris2024;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.*;

public class Day1 {
    public static void main(String[] args) {
        System.out.println(puzzel1());
        System.out.println(puzzel2());
    }

    private static String puzzel1() {
        List<Integer> left = new ArrayList<>();
        List<Integer> right = new ArrayList<>();
        try (BufferedReader bufferedReader = new BufferedReader(new FileReader("puzzel1.txt"))) {
            String regel;
            while ((regel = bufferedReader.readLine()) != null) {
                String[] parts = regel.split("   ");
                left.add(Integer.parseInt(parts[0]));
                right.add(Integer.parseInt(parts[1]));
            }
            Collections.sort(left);
            Collections.sort(right);

            Long totaal = 0L;
            for(int x=0;x<left.size();x++) {
                totaal += Math.abs(left.get(x) - right.get(x));
            }
            return totaal.toString();
        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }
    }

    private static String puzzel2() {
        List<Integer> left = new ArrayList<>();
        List<Integer> right = new ArrayList<>();
        try (BufferedReader bufferedReader = new BufferedReader(new FileReader("puzzel1.txt"))) {
            String regel;
            while ((regel = bufferedReader.readLine()) != null) {
                String[] parts = regel.split("   ");
                left.add(Integer.parseInt(parts[0]));
                right.add(Integer.parseInt(parts[1]));
            }
            Collections.sort(left);
            Collections.sort(right);

            Long totaal = 0L;
            for(int x=0;x<left.size();x++) {
                final Integer leftVal = left.get(x);
                long occ = right.stream().filter(a -> Objects.equals(a, leftVal)).count();
                totaal += (leftVal * occ);
            }
            return totaal.toString();
        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }
    }

}

