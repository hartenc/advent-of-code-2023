package chris2024;

import java.io.BufferedReader;
import java.io.FileReader;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class Day7 {
    static BigInteger totaal = new BigInteger("0");
    public static void main(String[] args) {
        Long time = System.currentTimeMillis();
        System.out.println(puzzel1());
        System.out.println(System.currentTimeMillis()-time);
        totaal = new BigInteger("0");
        System.out.println(puzzel2());
        System.out.println(System.currentTimeMillis()-time);
    }

    private static String puzzel1() {
        try (BufferedReader bufferedReader = new BufferedReader(new FileReader("puzzel7.txt"))) {
            String regel;
            while ((regel = bufferedReader.readLine()) != null) {
                Long doel = Long.parseLong(regel.split(":")[0]);
                List<Integer> getallen = Arrays.stream(regel.split(": ")[1].split(" ")).map(Integer::parseInt).collect(Collectors.toList());
                try {
                    check(doel, new BigInteger(getallen.get(0).toString()), getallen.subList(1, getallen.size()));
                } catch (RuntimeException e) {

                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return totaal.toString();
    }

    private static String puzzel2() {
        try (BufferedReader bufferedReader = new BufferedReader(new FileReader("puzzel7.txt"))) {
            String regel;
            while ((regel = bufferedReader.readLine()) != null) {
                Long doel = Long.parseLong(regel.split(":")[0]);
                List<Integer> getallen = Arrays.stream(regel.split(": ")[1].split(" ")).map(Integer::parseInt).collect(Collectors.toList());
                try {
                    check2(doel, new BigInteger(getallen.get(0).toString()), getallen.subList(1, getallen.size()));
                } catch (RuntimeException e) {

                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return totaal.toString();
    }

    private static void check(Long doel, BigInteger current, List<Integer> integers) {
        if(current.longValue() > doel) return;
        if(integers.size()==0) {
            if(current.longValue() == doel) {
                totaal = totaal.add(BigInteger.valueOf(doel));throw new RuntimeException();
            }
            return;
        }
        check(doel, current.multiply(BigInteger.valueOf(integers.get(0))), integers.subList(1, integers.size()));
        check(doel, current.add(BigInteger.valueOf(integers.get(0))), integers.subList(1, integers.size()));
    }

    private static void check2(Long doel, BigInteger current, List<Integer> integers) {
        if(current.longValue() > doel) return;
        if(integers.size()==0) {
            if(current.longValue() == doel) {
                totaal = totaal.add(BigInteger.valueOf(doel));throw new RuntimeException();
            }
            return;
        }
        check2(doel, current.multiply(BigInteger.valueOf(integers.get(0))), integers.subList(1, integers.size()));
        check2(doel, current.add(BigInteger.valueOf(integers.get(0))), integers.subList(1, integers.size()));
        check2(doel, new BigInteger(current.toString() + integers.get(0).toString()), integers.subList(1, integers.size()));
    }
}
