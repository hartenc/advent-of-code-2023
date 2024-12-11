package chris2024;

import java.awt.*;
import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Day8 {

    static Map<Character, List<Point>> map = new HashMap<>();

    public static void main(String[] args) {
//        System.out.println(puzzel1());
        System.out.println(puzzel2());
    }

    private static void readMap() {
        map = new HashMap<>();
        try (BufferedReader bufferedReader = new BufferedReader(new FileReader("puzzel8.txt"))) {
            String regel;
            int y = 0;
            while ((regel = bufferedReader.readLine()) != null) {
                for (int x = 0; x < regel.length(); x++) {
                    if (regel.charAt(x) != '.') {
                        if (!map.containsKey(regel.charAt(x))) map.put(regel.charAt(x), new ArrayList<>());
                        List<Point> points = map.get(regel.charAt(x));
                        points.add(new Point(x, y));
                    }
                }
                y++;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static String puzzel1() {
        readMap();
        List<Point> pts = new ArrayList<>();
        map.forEach((ch, locs) -> {
            if (locs.size() > 0) {
                for (int x = 0; x < locs.size(); x++) {
                    for (int y = 0; y < locs.size(); y++) {
                        if (x == y) continue;
                        Point a = locs.get(x);
                        Point b = locs.get(y);
                        int distX = a.x - b.x;
                        int distY = a.y - b.y;
                        Point n = new Point(a.x, a.y);

                        n.x += distX;
                        n.y += distY;
                        if (n.x > -1 && n.x < 50 && n.y > -1 && n.y < 50) {
                            if (!pts.contains(new Point(n.x, n.y))) pts.add(new Point(n.x, n.y));
                        }

                    }

                }
            }
        });

        return "" + pts.size();
    }


    private static String puzzel2() {
        readMap();
        List<Point> pts = new ArrayList<>();
        map.forEach((ch, locs) -> {
            if (locs.size() > 0) {
                for (int x = 0; x < locs.size(); x++) {
                    for (int y = 0; y < locs.size(); y++) {
                        if (x == y) continue;
                        Point a = locs.get(x);
                        Point b = locs.get(y);
                        int distX = a.x - b.x;
                        int distY = a.y - b.y;
                        Point n = new Point(b.x, b.y);
                        while (true) {
                            n.x += distX;
                            n.y += distY;
                            if (n.x > -1 && n.x < 50 && n.y > -1 && n.y < 50) {
                                if (!pts.contains(new Point(n.x, n.y))) pts.add(new Point(n.x, n.y));
                            } else {
                                break;
                            }
                        }
                    }

                }
            }
        });

        return "" + pts.size();
    }
}
