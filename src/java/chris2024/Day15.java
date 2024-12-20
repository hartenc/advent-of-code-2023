package chris2024;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class Day15 {
    static char[][] map = new char[50][50];
    static List<Character> commands = new ArrayList<>();

    public static void main(String[] args) {
        System.out.println(puzzel1());
//        System.out.println(puzzel2());
    }

    private static void readMap() {
        try (BufferedReader bufferedReader = new BufferedReader(new FileReader("puzzel15.txt"))) {
            String regel;
            int y = 0;
            boolean sw = false;
            while ((regel = bufferedReader.readLine()) != null) {
                if(regel.length() > 0) {
                    if(!sw) {
                        map[y] = regel.toCharArray();
                        y++;
                    } else {
                        commands.addAll(Arrays.stream(regel.split("")).map(s -> s.charAt(0)).collect(Collectors.toList()));
                    }
                } else {
                    sw = true;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    private static long puzzel1() {
        readMap();
        long score = 0l;
        int robotX = 24;
        int robotY = 24;
        for(int x=0;x<commands.size();x++) {
            Character command = commands.get(x);
            switch (command) {
                case '^' -> {
                    boolean result = tryPush(robotX, robotY, 0);
                    if(result) {
                        map[robotY][robotX] = '.';
                        robotY--;
                    }
                }
                case '>' -> {
                    boolean result = tryPush(robotX, robotY, 1);
                    if(result) {
                        map[robotY][robotX] = '.';
                        robotX++;
                    }
                }
                case 'v' -> {
                    boolean result = tryPush(robotX, robotY, 2);
                    if(result) {
                        map[robotY][robotX] = '.';
                        robotY++;
                    }
                }
                case '<' -> {
                    boolean result = tryPush(robotX, robotY, 3);
                    if(result) {
                        map[robotY][robotX] = '.';
                        robotX--;
                    }
                }
            }
            map[robotY][robotX] = '@';
        }
        for(int x=0;x<map[0].length;x++) {
            for(int y=0;y<map[0].length;y++) {
                if(map[y][x] == 'O') score += (x + (y*100));
            }
        }

        return score;
    }

    static boolean tryPush(int x, int y, int dir) {
        if (map[y][x] == '.') return true;
        if (map[y][x] == '#') return false;
        int targetX = x;
        int targetY = y;
        if (dir == 0) targetY--;
        if (dir == 1) targetX++;
        if (dir == 2) targetY++;
        if (dir == 3) targetX--;
        boolean result = tryPush(targetX, targetY, dir);
        if (result) {map[y][x] = '.'; map[targetY][targetX] = 'O';}
        return result;
    }

}
