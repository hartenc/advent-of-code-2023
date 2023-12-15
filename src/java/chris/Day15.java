package chris;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class Day15 {
    public static void main(String[] args) {
        System.out.println(puzzel1());
        System.out.println(puzzel2());
    }

    private static String puzzel1() {
        try (BufferedReader bufferedReader = new BufferedReader(new FileReader("puzzel15.txt"))) {
            List<String> chars = new ArrayList<>();
            String regel;
            while ((regel = bufferedReader.readLine()) != null) {
                chars = List.of(regel.split(","));
            }
            Long sum = 0L;
            for(int x=0; x<chars.size();x++) {
                sum += calcHashVal(chars.get(x));
            }
            return sum.toString();
        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }
    }

    private static String puzzel2() {
        try (BufferedReader bufferedReader = new BufferedReader(new FileReader("puzzel15.txt"))) {
            List<Lens> lenzen = new ArrayList<>();
            List<List<Lens>> boxes = new ArrayList<>();
            IntStream.range(0,256).forEach(i -> boxes.add(new ArrayList<>()));
            String regel;
            while ((regel = bufferedReader.readLine()) != null) {
                lenzen = List.of(regel.split(",")).stream().map(Lens::new).collect(Collectors.toList());
            }
            lenzen.forEach(lens -> {
                int boxId = calcHashVal(lens.label);
                if(lens.action == '-') {
                    boxes.get(boxId).removeIf(lensInBox -> lensInBox.label.equals(lens.label));
                }
                if(lens.action == '=') {
                    boolean replaced = false;
                    for (int x=0; x< boxes.get(boxId).size(); x++) {
                        if(boxes.get(boxId).get(x).label.equals(lens.label)) {
                            boxes.get(boxId).set(x, lens);
                            replaced = true;
                            break;
                        }
                    }
                    if (!replaced) boxes.get(boxId).add(lens);
                }
            });
            Long sum = 0L;

            for(int x=0; x<boxes.size();x++) {
                for (int y=0;y<boxes.get(x).size();y++) {
                    sum += ((x+1) * (y+1) * boxes.get(x).get(y).vocal);
                }
            }

            return sum.toString();
        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }
    }

    private static Integer calcHashVal(String input) {
        Integer hashVal = 0;
        for(int l=0; l<input.length();l++) {
            hashVal += input.charAt(l);
            hashVal *= 17;
            hashVal = hashVal % 256;
        }
        return hashVal;
    }

    private static class Lens {
        public String label;
        public Integer vocal;
        public Character action;
        public Lens(String input) {
            int actionPos = Math.max(input.indexOf("="), input.indexOf("-"));
            action = input.charAt(actionPos);
            label = input.substring(0, actionPos);
            if(action=='=') vocal = Integer.parseInt(input.substring(actionPos+1));
        }
    }
}
