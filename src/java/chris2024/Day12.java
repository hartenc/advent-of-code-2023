package chris2024;

import java.awt.*;
import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Day12 {
    static char[][] map = new char[140][140];
    static int[][] perimeterMap = new int[140][140];

    public static void main(String[] args) {
        System.out.println(puzzel1());
//        System.out.println(puzzel2());
    }

    private static void readMap() {
        try (BufferedReader bufferedReader = new BufferedReader(new FileReader("puzzel12.txt"))) {
            String regel;
            int y = 0;
            while ((regel = bufferedReader.readLine()) != null) {
                map[y] = regel.toCharArray();
                y++;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    private static long puzzel1() {
        readMap();
        long score = 0l;
        for (int x = 0; x < map[0].length; x++) {
            for (int y = 0; y < map.length; y++) {
                char teken = map[x][y];
                int perimeter = 4;
                if (peek(x-1, y) == teken) perimeter--;
                if (peek(x+1, y) == teken) perimeter--;
                if (peek(x, y+1) == teken) perimeter--;
                if (peek(x, y-1) == teken) perimeter--;
                perimeterMap[x][y] = perimeter;
            }
        }
        for (int x = 0; x < map[0].length; x++) {
            for (int y = 0; y < map.length; y++) {
                char teken = map[x][y];
                if (teken != '2') {
                   search(x,y, teken);
                   int fieldsize = 0;
                   int perimetersize = 0;
                   for (int a=0; a < 140; a++) {
                       for (int b=0; b<140; b++) {
                           if (map[a][b] == '1') {
                               map[a][b] = '2';
                               fieldsize++;
                               perimetersize += perimeterMap[a][b];
                           }
                       }
                   }
                   score += (fieldsize * perimetersize);
                }
            }
        }
        return score;
    }

    static void search(int x, int y, char teken) {
        if (x < 0) return ;
        if (x > 139) return ;
        if (y < 0) return ;
        if (y > 139) return ;
        if (map[x][y] != teken) return ;
        map[x][y] = '1';
        search(x-1, y, teken);
        search(x+1, y, teken);
        search(x, y-1, teken);
        search(x, y+1, teken);

    }

    static char peek(int x, int y) {
        if (x < 0) return '0';
        if (x > 139) return '0';
        if (y < 0) return '0';
        if (y > 139) return '0';
        return map[x][y];
    }

//    private static long puzzel2() {
//        readMap();
//        long score = 0l;
//        for (int x = 0; x < map[0].length; x++) {
//            for (int y = 0; y < map.length; y++) {
//                if (map[x][y] == 0) {
//                    peaks = 0;
//                    seekPeak2(x, y, 0);
//                    score += peaks;
//                }
//            }
//        }
//        return score;
//    }
}
