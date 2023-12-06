package chris;

import java.io.BufferedReader;
import java.io.FileReader;

public class Day6 {
    public static void main(String[] args) {
        System.out.println(puzzel1());
        System.out.println(puzzel2());
    }

    private static String puzzel1() {
        try (BufferedReader bufferedReader = new BufferedReader(new FileReader("puzzel6.txt"))) {
            Long[] tijd = new Long[]{45L, 97L, 72L, 95L};
            Long[] afstand = new Long[]{305L, 1062L, 1110L, 1695L};
            Long manierSom = 0L;
            for (int x = 0; x < 4; x++) {
                Long manieren = 0L;
                for (int t = 0; t < tijd[x]; t++) {
                    Long afstandRace = t * (tijd[x] - t);
                    if (afstandRace > afstand[x]) manieren++;
                }
                if (manierSom == 0) manierSom = manieren;
                else manierSom *= manieren;
            }

            return manierSom.toString();
        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }
    }

    private static String puzzel2() {
        Long tijd = 45977295L;
        Long afstand = 305106211101695L;

        Long manieren = 0L;
        for (long t = 0; t < tijd; t++) {
            Long afstandRace = t * (tijd - t);
            if (afstandRace > afstand) manieren++;
        }
        return manieren.toString();

    }
}
