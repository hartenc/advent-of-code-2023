package chris2024;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;

public class Day4 {

    public static void main(String[] args) {
        System.out.println(puzzel1());
        System.out.println(puzzel2());
    }

    private static List<String> field = new ArrayList<>();

    private static String puzzel1() {
        Long totaal = 0L;
        try (BufferedReader bufferedReader = new BufferedReader(new FileReader("puzzel4.txt"))) {

            String regel;
            while ((regel = bufferedReader.readLine()) != null) {
                field.add(regel);
            }

            for (int x = 0; x < field.get(0).length(); x++) {
                for (int y = 0; y < field.size(); y++) {
                    // linksboven
                    if (getChar(x, y) == 'X' && getChar(x - 1, y - 1) == 'M' && getChar(x - 2, y - 2) == 'A' && getChar(x - 3, y - 3) == 'S')
                        totaal++;
                    // boven
                    if (getChar(x, y) == 'X' && getChar(x, y - 1) == 'M' && getChar(x, y - 2) == 'A' && getChar(x, y - 3) == 'S')
                        totaal++;
                    // rechtsboven
                    if (getChar(x, y) == 'X' && getChar(x + 1, y - 1) == 'M' && getChar(x + 2, y - 2) == 'A' && getChar(x + 3, y - 3) == 'S')
                        totaal++;
                    // rechts
                    if (getChar(x, y) == 'X' && getChar(x + 1, y) == 'M' && getChar(x + 2, y) == 'A' && getChar(x + 3, y) == 'S')
                        totaal++;
                    // rechtsonder
                    if (getChar(x, y) == 'X' && getChar(x + 1, y + 1) == 'M' && getChar(x + 2, y + 2) == 'A' && getChar(x + 3, y + 3) == 'S')
                        totaal++;
                    // onder
                    if (getChar(x, y) == 'X' && getChar(x, y + 1) == 'M' && getChar(x, y + 2) == 'A' && getChar(x, y + 3) == 'S')
                        totaal++;
                    // linksonder
                    if (getChar(x, y) == 'X' && getChar(x - 1, y + 1) == 'M' && getChar(x - 2, y + 2) == 'A' && getChar(x - 3, y + 3) == 'S')
                        totaal++;
                    // links
                    if (getChar(x, y) == 'X' && getChar(x - 1, y) == 'M' && getChar(x - 2, y) == 'A' && getChar(x - 3, y) == 'S')
                        totaal++;

                }
            }

            return totaal.toString();
        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }
    }

    private static char getChar(int x, int y) {
        if (x < 0 || y < 0 || x > 139 || y > 139) return 'b';
        return field.get(y).charAt(x);
    }

    private static String puzzel2() {
        Long totaal = 0L;

        for (int x = 1; x < field.get(0).length() - 1; x++) {
            for (int y = 1; y < field.size() - 1; y++) {
                if (getChar(x, y) == 'A') {
                    if ((getChar(x - 1, y - 1) == 'M' && getChar(x + 1, y + 1) == 'S') || (getChar(x - 1, y - 1) == 'S' && getChar(x + 1, y + 1) == 'M')) {
                        if ((getChar(x + 1, y - 1) == 'M' && getChar(x - 1, y + 1) == 'S') || (getChar(x + 1, y - 1) == 'S' && getChar(x - 1, y + 1) == 'M')) {
                            totaal++;
                        }
                    }
                }

            }
        }

        return totaal.toString();
    }
}
