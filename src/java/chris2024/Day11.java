package chris2024;

import java.io.BufferedReader;
import java.io.FileReader;
import java.math.BigInteger;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class Day11 {
    static List<Long> stones = new ArrayList<>();
    public static void main(String[] args) {
        System.out.println(puzzel1());
        System.out.println(puzzel2());
    }

    private static void readStones() {

        try (BufferedReader bufferedReader = new BufferedReader(new FileReader("puzzel11.txt"))) {
           stones = Arrays.stream(bufferedReader.readLine().split(" ")).map(Long::parseLong).collect(Collectors.toList());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static int puzzel1() {
        readStones();
        for(int b = 0; b < 25; b++) {
            List<Long> newList = new ArrayList<>();
            for (Long stone : stones) {
                if (stone == 0) { newList.add(1L);continue;}
                String stringStone = stone.toString();
                if (stringStone.length() % 2 == 0) {
                    newList.add(Long.parseLong(stringStone.substring( 0, stringStone.length()/2)));
                    newList.add(Long.parseLong(stringStone.substring(stringStone.length()/2)));
                } else {
                    newList.add(stone * 2024);
                }
            }
            stones = newList;
        }
        return stones.size();
    }

    static BigInteger totaal = BigInteger.ZERO;
    private static BigInteger puzzel2() {
        readStones();
        Map<Long, Long> occurance = new HashMap<>();
        for(int x=0;x< stones.size();x++) {
            occurance.put(stones.get(x), 1L);
        }

        for(int b = 0; b < 75; b++) {
            Map<Long, Long> newOcc = new HashMap<>();
            occurance.forEach((k,v) -> {
                if (k == 0) { newOcc.put(1L, newOcc.getOrDefault(1, 0L) + v);return;}
                String stringStone = k.toString();
                if (stringStone.length() % 2 == 0) {
                    Long left = Long.parseLong(stringStone.substring(0, stringStone.length()/2));
                    Long right = Long.parseLong(stringStone.substring(stringStone.length()/2));
                    newOcc.put(left, newOcc.getOrDefault(left,0L) + v);
                    newOcc.put(right, newOcc.getOrDefault(right,0L) + v);
                } else {
                    newOcc.put(k*2024, newOcc.getOrDefault(k*2024, 0L) + v);
                }
            });
            occurance = newOcc;
        }

        occurance.forEach((k, v) -> totaal = totaal.add(new BigInteger(v.toString())));
        return totaal;
    }
}
