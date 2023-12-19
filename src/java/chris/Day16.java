package chris;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class Day16 {
    private static List<List<Character>> field = new ArrayList<>();
    private static List<List<Character>> visited = new ArrayList<>();

    public static void main(String[] args) {
        System.out.println(puzzel1());
        System.out.println(puzzel2());
    }

    private static String puzzel1() {
        try (BufferedReader bufferedReader = new BufferedReader(new FileReader("puzzel16.txt"))) {

            String regel;
            while ((regel = bufferedReader.readLine()) != null) {

                List<Character> fill = new ArrayList<>();
                for (int x = 0; x < regel.length(); x++) {
                    fill.add(regel.charAt(x));
                }
                field.add(fill);
            }
            visited = cleanSheet();
            solve(0, 0, 3, new ArrayList<>());
            Long visitedSpace = 0L;
            for (int x = 0; x < visited.get(0).size(); x++) {
                for (int y = 0; y < visited.size(); y++) {
                    if (visited.get(y).get(x) == '#') visitedSpace++;
                }
            }
            return visitedSpace.toString();
        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }


    }

    private static String puzzel2() {
        field = new ArrayList<>();
        visited = new ArrayList<>();
        try (BufferedReader bufferedReader = new BufferedReader(new FileReader("puzzel16.txt"))) {

            String regel;
            while ((regel = bufferedReader.readLine()) != null) {

                List<Character> fill = new ArrayList<>();
                for (int x = 0; x < regel.length(); x++) {
                    fill.add(regel.charAt(x));
                }
                field.add(fill);
            }
            visited = cleanSheet();
            Long longest = 0L;
            for (int i = 0; i < field.get(0).size(); i++) {
                visited = cleanSheet();
                solve(i, 0, 2, new ArrayList<>());
                Long visitedSpace = 0L;
                for (int x = 0; x < visited.get(0).size(); x++) {
                    for (int y = 0; y < visited.size(); y++) {
                        if (visited.get(y).get(x) == '#') visitedSpace++;
                    }
                }
                if (visitedSpace > longest) longest = visitedSpace;

                visited = cleanSheet();
                solve(i, field.get(0).size() - 1, 0, new ArrayList<>());
                visitedSpace = 0L;
                for (int x = 0; x < visited.get(0).size(); x++) {
                    for (int y = 0; y < visited.size(); y++) {
                        if (visited.get(y).get(x) == '#') visitedSpace++;
                    }
                }
                if (visitedSpace > longest) longest = visitedSpace;

                visited = cleanSheet();
                solve(0, i, 3, new ArrayList<>());
                visitedSpace = 0L;
                for (int x = 0; x < visited.get(0).size(); x++) {
                    for (int y = 0; y < visited.size(); y++) {
                        if (visited.get(y).get(x) == '#') visitedSpace++;
                    }
                }
                if (visitedSpace > longest) longest = visitedSpace;

                visited = cleanSheet();
                solve(field.size() - 1, i, 1, new ArrayList<>());
                visitedSpace = 0L;
                for (int x = 0; x < visited.get(0).size(); x++) {
                    for (int y = 0; y < visited.size(); y++) {
                        if (visited.get(y).get(x) == '#') visitedSpace++;
                    }
                }
                if (visitedSpace > longest) longest = visitedSpace;
            }

            return longest.toString();
        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }


    }

    // 0=up 1=left 2=down 3=right
    private static void solve(int x, int y, int direction, List<Trail> trail) {
        if (x < 0 || y < 0 || x == field.get(0).size() || y == field.size()) return;
        if (trail.contains(new Trail(x, y, direction))) return;
        trail.add(new Trail(x, y, direction));
        Character chr = field.get(y).get(x);
        visited.get(y).set(x, '#');

        switch (chr) {
            case '\\' -> {
                if (direction == 0) solve(x - 1, y, 1, trail);
                if (direction == 1) solve(x, y - 1, 0, trail);
                if (direction == 2) solve(x + 1, y, 3, trail);
                if (direction == 3) solve(x, y + 1, 2, trail);
            }
            case '/' -> {
                if (direction == 0) solve(x + 1, y, 3, trail);
                if (direction == 1) solve(x, y + 1, 2, trail);
                if (direction == 2) solve(x - 1, y, 1, trail);
                if (direction == 3) solve(x, y - 1, 0, trail);
            }
            case '-' -> {
                if (direction == 0) {
                    solve(x - 1, y, 1, trail);
                    solve(x + 1, y, 3, trail);
                }
                if (direction == 1) solve(x - 1, y, 1, trail);
                if (direction == 2) {
                    solve(x - 1, y, 1, trail);
                    solve(x + 1, y, 3, trail);
                }
                if (direction == 3) solve(x + 1, y, 3, trail);
            }
            case '|' -> {
                if (direction == 0) solve(x, y - 1, 0, trail);
                if (direction == 1) {
                    solve(x, y - 1, 0, trail);
                    solve(x, y + 1, 2, trail);
                }
                if (direction == 2) solve(x, y + 1, 2, trail);
                if (direction == 3) {
                    solve(x, y - 1, 0, trail);
                    solve(x, y + 1, 2, trail);
                }
            }
            case '.' -> {
                if (direction == 0) solve(x, y - 1, 0, trail);
                if (direction == 1) solve(x - 1, y, 1, trail);
                if (direction == 2) solve(x, y + 1, 2, trail);
                if (direction == 3) solve(x + 1, y, 3, trail);
            }


        }
    }

    private static class Trail {
        public int x;
        public int y;
        public int direction;

        public Trail(int x, int y, int direction) {
            this.x = x;
            this.y = y;
            this.direction = direction;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            Trail trail = (Trail) o;
            return x == trail.x && y == trail.y && direction == trail.direction;
        }

        @Override
        public int hashCode() {
            return Objects.hash(x, y, direction);
        }
    }

    private static List<List<Character>> cleanSheet() {
        List<List<Character>> sheet = new ArrayList<>();
        for(int x=0; x<110;x++) {
            List<Character> crList =IntStream.range(0,110).mapToObj(i -> '.').collect(Collectors.toList());
            sheet.add(crList);
        }
        return sheet;
    }
}
