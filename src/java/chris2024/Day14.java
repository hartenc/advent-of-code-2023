package chris2024;

import java.awt.*;
import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;

public class Day14 {
    static List<Robot> robots = new ArrayList<>();

    public static void main(String[] args) {
        System.out.println(puzzel1());
        System.out.println(puzzel2());
    }

    private static void readRobots() {
        try (BufferedReader bufferedReader = new BufferedReader(new FileReader("puzzel14.txt"))) {
            String regel;
            while ((regel = bufferedReader.readLine()) != null) {
                String[] parts = regel.split("=");
                String[] start = parts[1].split(" ")[0].split(",");
                String[] vel = parts[2].split(",");
                robots.add(new Robot(new Point(Integer.parseInt(start[0]), Integer.parseInt(start[1])), Integer.parseInt(vel[0]), Integer.parseInt(vel[1])));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static Long puzzel1() {
        readRobots();
        for (int x = 0; x < 100; x++) {
            robots.forEach(robot -> {
                robot.position.x += robot.velX;
                robot.position.y += robot.velY;

                if (robot.position.x < 0) robot.position.x += 101;
                if (robot.position.y < 0) robot.position.y += 103;
                if (robot.position.x > 100) robot.position.x -= 101;
                if (robot.position.y > 102) robot.position.y -= 103;
            });
        }
        long quad1 = 0;
        long quad2 = 0;
        long quad3 = 0;
        long quad4 = 0;
        for(int x=0;x<robots.size();x++) {
            Robot r = robots.get(x);
            if (r.position.x < 50) {
                if (r.position.y < 51) {
                    quad1++;
                } else {
                    if (r.position.y > 51) {
                        quad3++;
                    }
                }
            } else {
                if (r.position.x > 50) {
                    if (r.position.y < 51) {
                        quad2++;
                    } else {
                        if (r.position.y > 51) {
                            quad4++;
                        }
                    }
                }
            }
        }
        return quad1 * quad2 * quad3 * quad4;
    }

    private static Long puzzel2() {
        readRobots();
        long cycles = 0;
       while(true) {
           cycles++;
            robots.forEach(robot -> {
                robot.position.x += robot.velX;
                robot.position.y += robot.velY;

                if (robot.position.x < 0) robot.position.x += 101;
                if (robot.position.y < 0) robot.position.y += 103;
                if (robot.position.x > 100) robot.position.x -= 101;
                if (robot.position.y > 102) robot.position.y -= 103;
            });
            char[][] map = new char[101][103];

            for(int x=0; x<robots.size();x++) {
                Robot r = robots.get(x);
                if (map[r.position.x][r.position.y] == 0) {
                    map[r.position.x][r.position.y] = 1;
                }
            }
            boolean inARow=false;
           for (int y=0;y<103;y++) {
               int row=0;
               for(int x=0;x<101;x++) {
                   if(map[x][y] == 1) {
                       row++;
                       if (row > 10) break;
                   } else row=0;
               }
               if(row > 10) {
                   inARow = true;
                   break;
               }
           }
            if(inARow) {
                for (int a = 0; a < 103; a++) {
                    for (int b = 0; b < 101; b++) {
                        System.out.print(map[b][a] == 0 ? "." : "X");
                    }
                    System.out.println();
                }
                String a = "";
//                break;
            }
            if(robots.size() == 0) break;
        }

        return cycles;
    }

    private static class Robot {
        public Point position;
        public int velX;
        public int velY;

        public Robot(Point position, int velX, int velY) {
            this.position = position;
            this.velX = velX;
            this.velY = velY;
        }
    }
}
