package chris;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class Day11 {
    public static void main(String[] args) {
        System.out.println(puzzel1());
        System.out.println(puzzel2());
    }

    private static String puzzel1() {
        try (BufferedReader bufferedReader = new BufferedReader(new FileReader("p11test.txt"))) {
            String regel;
            List<Pos> galaxies = new ArrayList<>();
            List<List<Character>> filled = new ArrayList<>();
            while ((regel = bufferedReader.readLine()) != null) {
                List<Character> fill = new ArrayList<>();
                for (int x = 0; x < regel.length(); x++) {
                    fill.add(regel.charAt(x));
                }
                filled.add(fill);
            }
            // shift right
            for (int y = 0; y < filled.size(); y++) {
                int empty = 0;
                for (int x = 0; x < filled.get(0).size(); x++) {
                    if (filled.get(y).get(x) == '.') empty++;
                }
                if (empty == filled.get(0).size()) {

                    filled.add(y, IntStream.range(0, empty).mapToObj(i -> '.').collect(Collectors.toList()));

                    y++;
                }
            }
            // shift down
            for (int x = 0; x < filled.get(0).size(); x++) {
                int empty = 0;
                for (int y = 0; y < filled.size(); y++) {
                    if (filled.get(y).get(x) == '.') empty++;
                }
                if (empty == filled.size()) {
                    for (int z = 0; z < filled.size(); z++) {
                        filled.get(z).add(x, '.');
                    }
                    x++;

                }
            }
            // galaxies
            for (int y = 0; y < filled.size(); y++) {
                for (int x = 0; x < filled.get(0).size(); x++) {
                    if (filled.get(y).get(x) == '#') galaxies.add(new Pos(x, y));
                }

            }
            Long afstandSom = 0L;
            for (int g = 0; g < galaxies.size() - 1; g++) {
                for (int g2 = g + 1; g2 < galaxies.size(); g2++) {
                    long afstand = (Math.abs(galaxies.get(g).x - galaxies.get(g2).x) + Math.abs(galaxies.get(g).y - galaxies.get(g2).y));
                    afstandSom += afstand;
                }
            }
            return afstandSom.toString();
        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }
    }

    private static String puzzel2() {
        try (BufferedReader bufferedReader = new BufferedReader(new FileReader("puzzel11.txt"))) {
            String regel;
            List<Pos> galaxies = new ArrayList<>();
            List<List<Character>> filled = new ArrayList<>();
            while ((regel = bufferedReader.readLine()) != null) {
                List<Character> fill = new ArrayList<>();
                for (int x = 0; x < regel.length(); x++) {
                    fill.add(regel.charAt(x));
                }
                filled.add(fill);
            }
            // shift right
            for (int y = 0; y < filled.size(); y++) {
                int empty = 0;
                for (int x = 0; x < filled.get(0).size(); x++) {
                    if (filled.get(y).get(x) == '.') empty++;
                }
                if (empty == filled.get(0).size()) {

                    filled.add(y, IntStream.range(0, empty).mapToObj(i -> 'X').collect(Collectors.toList()));

                    y++;
                }
            }
            // shift down
            for (int x = 0; x < filled.get(0).size(); x++) {
                int empty = 0;
                for (int y = 0; y < filled.size(); y++) {
                    if (filled.get(y).get(x) == '.' || filled.get(y).get(x) == 'X') empty++;
                }
                if (empty == filled.size()) {
                    for (int z = 0; z < filled.size(); z++) {
                        filled.get(z).add(x, filled.get(z).get(x) == 'X' ? 'X' : 'X');
                    }
                    x++;

                }
            }
            // galaxies
            for (int y = 0; y < filled.size(); y++) {
                for (int x = 0; x < filled.get(0).size(); x++) {
                    if (filled.get(y).get(x) == '#') galaxies.add(new Pos(x , y));

                }

            }
            galaxies.forEach(galaxy -> {
                long hitsX=0;
                for (int x=0; x<galaxy.x; x++) {
                    if(filled.get((int)galaxy.y).get(x) == 'X') hitsX++;
                }
                long hitsY = 0;
                for (int y=0; y<galaxy.y; y++) {
                    if(filled.get(y).get((int)galaxy.x) == 'X') hitsY++;
                }
                galaxy.x += (hitsX * 999_998);
                galaxy.y += (hitsY * 999_998);
            });
            Long afstandSom = 0L;
            for (int g = 0; g < galaxies.size() - 1; g++) {
                for (int g2 = g + 1; g2 < galaxies.size(); g2++) {
                    long afstand = (Math.abs(galaxies.get(g).x - galaxies.get(g2).x) + Math.abs(galaxies.get(g).y - galaxies.get(g2).y));
                    afstandSom += afstand;
                }
            }
            return afstandSom.toString();
        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }
    }

    private static class Pos {
        public long x;
        public long y;

        public Pos(long x, long y) {
            this.x = x;
            this.y = y;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            Day11.Pos pos = (Day11.Pos) o;
            return x == pos.x && y == pos.y;
        }

        @Override
        public int hashCode() {
            return Objects.hash(x, y);
        }
    }

}
