package chris;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.*;

public class Day10 {
    public static void main(String[] args) {
        System.out.println(puzzel1());
        System.out.println(puzzel2());
    }

    private static String puzzel1() {
        int posX = 0;
        int posY = 0;
        int length = 0;
        int richting = 0;
        try (BufferedReader bufferedReader = new BufferedReader(new FileReader("puzzel10.txt"))) {
            List<List<Character>> field = new ArrayList<>();
            String regel;
            while ((regel = bufferedReader.readLine()) != null) {
                List<Character> chars = new ArrayList<>();
                for (int x = 0; x < regel.length(); x++) {
                    chars.add(regel.charAt(x));
                    if (regel.charAt(x) == 'S') {
                        posX = x;
                        posY = field.size();
                    }
                }
                field.add(chars);
            }
            // 0 = boven
            // 1 = rechts
            // 2 = onder
            // 3 = rechts
            posY--;
            length++;
            while (field.get(posY).get(posX) != 'S') {
                Character c = field.get(posY).get(posX);
                switch (c) {
                    case '|' -> {
                        if (richting == 0) {
                            posY--;
                        } else {
                            posY++;
                        }
                    }
                    case '-' -> {
                        if (richting == 1) {
                            posX++;
                        } else {
                            posX--;
                        }
                    }
                    case 'F' -> {
                        if (richting == 0) {
                            posX++;
                            richting = 1;
                        } else {
                            posY++;
                            richting = 2;
                        }
                    }
                    case '7' -> {
                        if (richting == 0) {
                            posX--;
                            richting = 3;
                        } else {
                            posY++;
                            richting = 2;
                        }
                    }
                    case 'L' -> {
                        if (richting == 2) {
                            posX++;
                            richting = 1;
                        } else {
                            posY--;
                            richting = 0;
                        }
                    }
                    case 'J' -> {
                        if (richting == 2) {
                            posX--;
                            richting = 3;
                        } else {
                            posY--;
                            richting = 0;
                        }
                    }
                }
                length++;
            }
            return "" + length / 2;
        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }
    }

    private static String puzzel2() {
        int posX = 0;
        int posY = 0;
        int richting = 0;
        try (BufferedReader bufferedReader = new BufferedReader(new FileReader("puzzel10.txt"))) {
            List<List<Character>> field = new ArrayList<>();
            String regel;
            while ((regel = bufferedReader.readLine()) != null) {
                List<Character> chars = new ArrayList<>();
                for (int x = 0; x < regel.length(); x++) {
                    chars.add(regel.charAt(x));
                    if (regel.charAt(x) == 'S') {
                        posX = x;
                        posY = field.size();
                    }
                }
                field.add(chars);
            }
            // 0 = boven
            // 1 = rechts
            // 2 = onder
            // 3 = rechts
            List<Pos> route = new ArrayList<>();
            route.add(new Pos(posX, posY));
            posY--;

            while (field.get(posY).get(posX) != 'S') {
                Character c = field.get(posY).get(posX);
                switch (c) {
                    case '|' -> {
                        if (richting == 0) {
                            posY--;
                        } else {
                            posY++;
                        }
                    }
                    case '-' -> {
                        if (richting == 1) {
                            posX++;
                        } else {
                            posX--;
                        }
                    }
                    case 'F' -> {
                        if (richting == 0) {
                            posX++;
                            richting = 1;
                        } else {
                            posY++;
                            richting = 2;
                        }
                    }
                    case '7' -> {
                        if (richting == 0) {
                            posX--;
                            richting = 3;
                        } else {
                            posY++;
                            richting = 2;
                        }
                    }
                    case 'L' -> {
                        if (richting == 2) {
                            posX++;
                            richting = 1;
                        } else {
                            posY--;
                            richting = 0;
                        }
                    }
                    case 'J' -> {
                        if (richting == 2) {
                            posX--;
                            richting = 3;
                        } else {
                            posY--;
                            richting = 0;
                        }
                    }
                }
                route.add(new Pos(posX, posY));
            }
            field.get(57).set(65, '|');
            long enclosed = 0L;
            boolean inside = false;
            for (int y = 0; y < field.size(); y++) {
                for (int x = 0; x < field.get(0).size(); x++) {

                    if (route.contains(new Pos(x, y))) {
                        Character fld = field.get(y).get(x);
                        if (fld == 'F' || fld == '7' || fld == '|') {
                            inside = !inside;
                        }
                        System.out.print(fld);

                    } else {
                        if (inside) {
                            enclosed++;
                            System.out.print('I');
                        } else {
                            System.out.print('.');
                        }

                    }

                }
                inside = false;
                System.out.println("");
            }
            return "" + enclosed;
        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }
    }

    private static class Pos {
        public int x;
        public int y;

        public Pos(int x, int y) {
            this.x = x;
            this.y = y;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            Pos pos = (Pos) o;
            return x == pos.x && y == pos.y;
        }

        @Override
        public int hashCode() {
            return Objects.hash(x, y);
        }
    }
}
