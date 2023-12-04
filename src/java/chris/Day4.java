package chris;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

public class Day4 {
    public static void main(String[] args) {
        System.out.println(puzzel1());
//        System.out.println(puzzel2());
    }

    private static String puzzel1() {
        Long score = 0L;
        try (BufferedReader bufferedReader = new BufferedReader(new FileReader("puzzel4.txt"))) {
            List<Long> repeats = new ArrayList<>();
            repeats.add(0L);
            for (int x = 0; x < 211; x++) {
                repeats.add(1L);
            }
            int it = 1;
            String regel;
            while ((regel = bufferedReader.readLine()) != null) {
                List<Integer> nummers = new ArrayList<>();
                List<Integer> winnend = new ArrayList<>();
                String nummerStart = regel.split(": ")[1];
                String eigen = nummerStart.split("\\|")[0];
                for (int i = 0; i < 30; i += 3) {
                    nummers.add(Integer.parseInt(eigen.substring(i, i + 2).trim()));
                }
                String winnendString = nummerStart.split("\\| ")[1];
                for (int i = 0; i < 75; i += 3) {
                    winnend.add(Integer.parseInt(winnendString.substring(i, i + 2).trim()));
                }

                AtomicInteger regelscore = new AtomicInteger();
                nummers.forEach(nummer -> {

                    if (winnend.contains(nummer)) {
                        regelscore.incrementAndGet();
                    }
                });
                long reps = repeats.get(it);

                    for (int i2 = 0; i2 < regelscore.get(); i2++) {
                        if (i2 + it < 211) {
                            if (repeats.size() > i2 + it) {
                                repeats.set(i2 + it + 1, repeats.get(i2 + it + 1) + reps);
                            }
                        }
                    }

                it++;
            }
            return repeats.stream().reduce(0L, Long::sum).toString();
        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }
    }
}
