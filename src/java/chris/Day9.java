package chris;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.*;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Day9 {
    public static void main(String[] args) {
        System.out.println(puzzel1());
        System.out.println(puzzel2());
    }

    private static String puzzel1() {
        try (BufferedReader bufferedReader = new BufferedReader(new FileReader("puzzel9.txt"))) {
            List<List<Long>> series = new ArrayList<>();
            String regel;
            while ((regel = bufferedReader.readLine()) != null) {
                series.add(Arrays.stream(regel.split(" ")).map(Long::parseLong).collect(Collectors.toList()));
            }

            AtomicLong atomicLong = new AtomicLong();
            series.forEach(serie -> {
                Long diff = getDiffs(serie);
                atomicLong.addAndGet(diff);
            });
            return atomicLong.toString();
        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }
    }

    private static Long getDiffs(List<Long> serie) {
        if (serie.stream().allMatch(lng -> lng == 0L)) return 0L;
        List<Long> serie2 = new ArrayList<>();
        for(int x=0; x < serie.size() - 1; x++) {
            serie2.add(serie.get(x + 1) - serie.get(x));

        }
        Long diff = getDiffs(serie2);
        serie.add(serie.get(serie.size()-1) + diff);
        return serie.get(serie.size()-1);

    }

    private static String puzzel2() {
        try (BufferedReader bufferedReader = new BufferedReader(new FileReader("puzzel9.txt"))) {
            List<List<Long>> series = new ArrayList<>();
            String regel;
            while ((regel = bufferedReader.readLine()) != null) {
                series.add(Arrays.stream(regel.split(" ")).map(Long::parseLong).collect(Collectors.toList()));
            }

            AtomicLong atomicLong = new AtomicLong();
            series.forEach(serie -> {
                Long diff = getDiffsBack(serie);
                atomicLong.addAndGet(diff);
            });
            return atomicLong.toString();
        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }
    }

    private static Long getDiffsBack(List<Long> serie) {
        if (serie.stream().allMatch(lng -> lng == 0L)) return 0L;
        List<Long> serie2 = new ArrayList<>();
        for(int x=0; x < serie.size() - 1; x++) {
            serie2.add(serie.get(x + 1) - serie.get(x));

        }
        Long diff = getDiffsBack(serie2);
        serie.add(0, serie.get(0) - diff);
        return serie.get(0);

    }

}
