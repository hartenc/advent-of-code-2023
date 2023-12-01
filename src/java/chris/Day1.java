package chris;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class Day1 {
    public static void main(String[] args) {
        System.out.println(puzzel1());
        System.out.println(puzzel2());
    }

    private static String puzzel1() {
        try (BufferedReader bufferedReader = new BufferedReader(new FileReader("puzzel1.txt"))) {
            Long values = 0L;
            String regel;
            while ((regel = bufferedReader.readLine()) != null) {
                String letter1 = null;
                String letter2 = null;
                for(int i = 0; i < regel.length(); i++) {
                    String letter = regel.substring(i, i+1);

                    try {
                        Integer.parseInt(letter);
                        letter1 = letter;
                        break;
                    } catch (Exception e) {
                        // geen nummer;
                    }
                }
                for(int i = regel.length(); i > 0; i--) {
                    String letter = regel.substring(i-1, i);
                    try {
                        Integer.parseInt(letter);
                        letter2 = letter;
                        break;
                    } catch (Exception e) {
                        // geen nummer;
                    }
                }
                values += Long.parseLong(letter1 + letter2);
            }
            return values.toString();
        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }
    }

    private static String puzzel2() {
        try (BufferedReader bufferedReader = new BufferedReader(new FileReader("puzzel1.txt"))) {
            Long values = 0L;
            String regel;
            while ((regel = bufferedReader.readLine()) != null) {
                String letter1 = null;
                String letter2 = null;
                for(int i = 0; i < regel.length(); i++) {
                    String letter = regel.substring(i, i+1);

                    try {
                        Integer.parseInt(letter);
                        letter1 = letter;
                        break;
                    } catch (Exception e) {
                        // misschien tekst?
                        Set<String> keys = map.keySet();
                        for(String woord : keys) {
                            if (regel.substring(i).startsWith(woord)) letter1 = map.get(woord).toString();
                        }
                        if (letter1 != null) break;

                    }
                }

                for(int i = 0; i < regel.length(); i++) {
                    String letter = regel.substring(i, i+1);

                    try {
                        Integer.parseInt(letter);
                        letter2 = letter;
                    } catch (Exception e) {
                        // misschien tekst?
                        Set<String> keys = map.keySet();
                        for(String woord : keys) {
                            if (regel.substring(i).startsWith(woord)) letter2 = map.get(woord).toString();
                        }

                    }
                }


                values += Long.parseLong(letter1 + letter2);
            }
            return values.toString();
        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }
    }

    private static Map<String, Integer> map = Map.of("one", 1, "two", 2, "three", 3, "four", 4, "five", 5, "six", 6, "seven", 7, "eight", 8, "nine", 9);
}
