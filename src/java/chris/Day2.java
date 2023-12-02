package chris;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;

public class Day2 {
    public static void main(String[] args) {
        System.out.println(puzzel1());
        System.out.println(puzzel2());
    }

    private static String puzzel1() {
        try (BufferedReader bufferedReader = new BufferedReader(new FileReader("puzzel2.txt"))) {
            Long possibleGames = 0L;
            int gameId = 0;
            String regel;
            while ((regel = bufferedReader.readLine()) != null) {
                gameId++;
                regel = regel.split(": ")[1];
                String[] parts = regel.split(";");

                boolean possible = true;
                for (String part : parts) {
                    String[] gems = part.split(",");
                    for (String gem : gems) {
                        String color = gem.trim().split(" ")[1];
                        Integer amount = Integer.parseInt(gem.trim().split(" ")[0]);
                        if (color.equals("red") && amount > 12) possible = false;
                        if (color.equals("blue") && amount > 14) possible = false;
                        if (color.equals("green") && amount > 13) possible = false;
                    }
                    if (!possible) break;
                }
                if (possible) {
                    possibleGames += gameId;
                }
            }
            return possibleGames.toString();
        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }
    }

    private static String puzzel2() {
        try (BufferedReader bufferedReader = new BufferedReader(new FileReader("puzzel2.txt"))) {
            Long score = 0L;
            String regel;
            while ((regel = bufferedReader.readLine()) != null) {
                regel = regel.split(": ")[1];
                String[] parts = regel.split(";");

                Long red = 0L;
                Long blue = 0L;
                Long green = 0L;
                for (String part : parts) {
                    String[] gems = part.split(",");
                    for (String gem : gems) {
                        String color = gem.trim().split(" ")[1];
                        Long amount = Long.parseLong(gem.trim().split(" ")[0]);
                        if (color.equals("red") && amount > red) red = amount;
                        if (color.equals("blue") && amount > blue) blue = amount;
                        if (color.equals("green") && amount > green) green = amount;
                    }
                }
                score += (red * green * blue);

            }
            return score.toString();
        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }
    }

}
