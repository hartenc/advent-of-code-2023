package chris2024;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class Day5 {
    static List<String[]> volgorde = new ArrayList<>();
    static List<List<String>> seqs = new ArrayList<>();
    static Integer failCase = 0;

    public static void main(String[] args) {
        System.out.println(puzzel1());
        System.out.println(puzzel2());
    }

    private static String puzzel1() {
        Long totaal = 0L;
        try (BufferedReader bufferedReader = new BufferedReader(new FileReader("puzzel5.txt"))) {
            String regel;
            boolean deel = false;
            while ((regel = bufferedReader.readLine()) != null) {
                if (!deel) {
                    if (regel.isEmpty()) {
                        deel = true;
                        continue;
                    }
                    volgorde.add(regel.split("\\|"));
                } else {
                    seqs.add(Arrays.asList(regel.split(",")));
                }
            }

            for (int a = 0; a < seqs.size(); a++) {
                boolean failure = isFailure(seqs.get(a));
                if (!failure) {
                    totaal += Long.parseLong(seqs.get(a).get(((int) (seqs.get(a).size() / 2))));
                }
            }

            return totaal.toString();
        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }
    }

    private static boolean isFailure(List<String> input) {
        boolean failure = false;
        for (int b = 0; b < volgorde.size(); b++) {
            String l = volgorde.get(b)[0];
            String r = volgorde.get(b)[1];
            if (input.contains(l) && input.contains(r)) {
                if (input.indexOf(l) > input.indexOf(r)) {
                    failure = true;
                    failCase = b;
                    break;
                }
            }
        }
        return failure;
    }

    private static String puzzel2() {
        Long totaal = 0L;
        List<List<String>> fails = new ArrayList<>();

        for (int a = 0; a < seqs.size(); a++) {
            boolean failure = isFailure(seqs.get(a));
            boolean firstFail = false;
            while (failure) {
                firstFail = true;
                int failIndexL = seqs.get(a).indexOf(volgorde.get(failCase)[0]);
                int failIndexR = seqs.get(a).indexOf(volgorde.get(failCase)[1]);
                String charL = seqs.get(a).get(failIndexL);
                String charR = seqs.get(a).get(failIndexR);
                seqs.get(a).set(failIndexL, charR);
                seqs.get(a).set(failIndexR, charL);
                failure = isFailure(seqs.get(a));
            }
            if (firstFail) fails.add(seqs.get(a));
        }
        for (int a = 0; a < fails.size(); a++) {

            totaal += Long.parseLong(fails.get(a).get(((int) (fails.get(a).size() / 2))));

        }
        return totaal.toString();
    }
}
