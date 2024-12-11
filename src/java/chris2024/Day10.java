package chris2024;

import java.awt.*;
import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Day10 {
    static int[][] map = new int[54][54];

    public static void main(String[] args) {
        System.out.println(puzzel1());
        System.out.println(puzzel2());
    }

    private static void readMap() {
        try (BufferedReader bufferedReader = new BufferedReader(new FileReader("puzzel10.txt"))) {
            String regel;
            int y = 0;
            while ((regel = bufferedReader.readLine()) != null) {
                map[y] = Arrays.stream(regel.split("")).mapToInt(Integer::parseInt).toArray();
                y++;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    static int peaks = 0;
    static List<Point> visitedPeaks;

    private static long puzzel1() {
        readMap();
        long score = 0l;
        for (int x = 0; x < map[0].length; x++) {
            for (int y = 0; y < map.length; y++) {
                if (map[x][y] == 0) {
                    peaks = 0;
                    visitedPeaks = new ArrayList<>();
                    seekPeak(x, y, 0);
                    score += peaks;
                }
            }
        }
        return score;
    }

    private static long puzzel2() {
        readMap();
        long score = 0l;
        for (int x = 0; x < map[0].length; x++) {
            for (int y = 0; y < map.length; y++) {
                if (map[x][y] == 0) {
                    peaks = 0;
                    seekPeak2(x, y, 0);
                    score += peaks;
                }
            }
        }
        return score;
    }

    private static void seekPeak(int x, int y, int level) {
        if (level == 9) {
            if (!visitedPeaks.contains(new Point(x, y))) {
                peaks++;
                visitedPeaks.add(new Point(x, y));
            }
            return;
        }
        if (x > 0 && map[x - 1][y] == level + 1) seekPeak(x - 1, y, level + 1);
        if (y > 0 && map[x][y - 1] == level + 1) seekPeak(x, y - 1, level + 1);
        if (x < 53 && map[x + 1][y] == level + 1) seekPeak(x + 1, y, level + 1);
        if (y < 53 && map[x][y + 1] == level + 1) seekPeak(x, y + 1, level + 1);
    }

    private static void seekPeak2(int x, int y, int level) {
        if (level == 9) {
            peaks++;
            return;
        }
        if (x > 0 && map[x - 1][y] == level + 1) seekPeak2(x - 1, y, level + 1);
        if (y > 0 && map[x][y - 1] == level + 1) seekPeak2(x, y - 1, level + 1);
        if (x < 53 && map[x + 1][y] == level + 1) seekPeak2(x + 1, y, level + 1);
        if (y < 53 && map[x][y + 1] == level + 1) seekPeak2(x, y + 1, level + 1);
    }
}
