package chris;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

public class Day22 {
    public static void main(String[] args) {
        System.out.println(puzzel1());
        System.out.println(puzzel2());
    }

    private static String puzzel1() {
        try (BufferedReader bufferedReader = new BufferedReader(new FileReader("puzzel22.txt"))) {
            List<Brick> bricks = new ArrayList<>();
            boolean[][][] filled;
            int minX = 999999999;
            int maxX = 0;
            int minY = 999999999;
            int maxY = 0;
            int minZ = 999999999;
            int maxZ = 0;
            String regel;
            while ((regel = bufferedReader.readLine()) != null) {
                String[] parts = regel.split("~");
                String[] start = parts[0].split(",");
                String[] end = parts[1].split(",");
                Brick brick = new Brick(Integer.parseInt(start[0]), Integer.parseInt(start[1]), Integer.parseInt(start[2]), Integer.parseInt(end[0]), Integer.parseInt(end[1]), Integer.parseInt(end[2]), bricks.size());
                bricks.add(brick);
                if(  Integer.parseInt(start[0]) < minX) minX = Integer.parseInt(start[0]);
                if(  Integer.parseInt(start[1]) < minY ) minY = Integer.parseInt(start[1]);
                if(  Integer.parseInt(start[2]) < minZ ) minZ = Integer.parseInt(start[2]);
                if(  Integer.parseInt(end[0]) > maxX) maxX = Integer.parseInt(end[0]);
                if(  Integer.parseInt(end[1]) >maxY  ) maxY = Integer.parseInt(end[1]);
                if(  Integer.parseInt(end[2]) >maxZ  )  maxZ = Integer.parseInt(end[2]);
            }
            filled = new boolean[maxX+1][maxY+1][maxZ];
            bricks.sort(Comparator.comparingInt(a -> a.startZ));
            bricks.forEach(brick -> {
                while (brick.startZ > 1) {
                    boolean space = canFall(brick, filled);
                    if(space) {
                        brick.endZ--;
                        brick.startZ--;
                    } else {
                        break;
                    }
                }
                for(int x = brick.startX; x< brick.endX+1; x++) {
                    for (int y =brick.startY; y< brick.endY +1 ; y++) {
                        for (int z= brick.startZ; z< brick.endZ + 1; z++) {
                            filled[x][y][z] = true;
                        }
                    }
                }

            });
            AtomicLong verwijderdbaar = new AtomicLong();
            bricks.forEach(brick -> {
                for(int x = brick.startX; x< brick.endX+1; x++) {
                    for (int y =brick.startY; y< brick.endY +1 ; y++) {
                        for (int z= brick.startZ; z< brick.endZ + 1; z++) {
                            filled[x][y][z] = false;
                        }
                    }
                }
                if(bricks.stream().noneMatch(brick2 -> canFall(brick2, filled))) {
                    verwijderdbaar.incrementAndGet();
                }
                for(int x = brick.startX; x< brick.endX+1; x++) {
                    for (int y =brick.startY; y< brick.endY +1 ; y++) {
                        for (int z= brick.startZ; z< brick.endZ + 1; z++) {
                            filled[x][y][z] = true;
                        }
                    }
                }
            });


            return verwijderdbaar.toString();
        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }
    }

    private static String puzzel2() {
        try (BufferedReader bufferedReader = new BufferedReader(new FileReader("puzzel22.txt"))) {
            final List<Brick> bricks = new ArrayList<>();
            boolean[][][] filled;
            int minX = 999999999;
            int maxX = 0;
            int minY = 999999999;
            int maxY = 0;
            int minZ = 999999999;
            int maxZ = 0;
            String regel;
            while ((regel = bufferedReader.readLine()) != null) {
                String[] parts = regel.split("~");
                String[] start = parts[0].split(",");
                String[] end = parts[1].split(",");
                Brick brick = new Brick(Integer.parseInt(start[0]), Integer.parseInt(start[1]), Integer.parseInt(start[2]), Integer.parseInt(end[0]), Integer.parseInt(end[1]), Integer.parseInt(end[2]), bricks.size());
                bricks.add(brick);
                if(  Integer.parseInt(start[0]) < minX) minX = Integer.parseInt(start[0]);
                if(  Integer.parseInt(start[1]) < minY ) minY = Integer.parseInt(start[1]);
                if(  Integer.parseInt(start[2]) < minZ ) minZ = Integer.parseInt(start[2]);
                if(  Integer.parseInt(end[0]) > maxX) maxX = Integer.parseInt(end[0]);
                if(  Integer.parseInt(end[1]) >maxY  ) maxY = Integer.parseInt(end[1]);
                if(  Integer.parseInt(end[2]) >maxZ  )  maxZ = Integer.parseInt(end[2]);
            }
            filled = new boolean[maxX+1][maxY+1][maxZ];
            bricks.sort(Comparator.comparingInt(a -> a.startZ));
            bricks.forEach(brick -> {
                while (brick.startZ > 1) {
                    boolean space = canFall(brick, filled);
                    if(space) {
                        brick.endZ--;
                        brick.startZ--;
                    } else {
                        break;
                    }
                }
                for(int x = brick.startX; x< brick.endX+1; x++) {
                    for (int y =brick.startY; y< brick.endY +1 ; y++) {
                        for (int z= brick.startZ; z< brick.endZ + 1; z++) {
                            filled[x][y][z] = true;
                        }
                    }
                }

            });
            bricks.sort(Comparator.comparingInt(a -> a.startZ));
            List<Brick> backup = new ArrayList<>();
            bricks.forEach(brick -> backup.add(new Brick(brick.startX, brick.startY, brick.startZ, brick.endX, brick.endY, brick.endZ, brick.id)));
            AtomicLong vallendSom = new AtomicLong();
            for(int x=0;x<bricks.size();x++) {
                System.out.println(bricks.get(x));
                Brick brick = bricks.get(x);
                for(int x2 = brick.startX; x2< brick.endX+1; x2++) {
                    for (int y =brick.startY; y< brick.endY +1 ; y++) {
                        for (int z= brick.startZ; z< brick.endZ + 1; z++) {
                            filled[x2][y][z] = false;
                        }
                    }
                }
                AtomicLong vallend = new AtomicLong();
                boolean[][][] newFilled = new boolean[maxX+1][maxY+1][maxZ];
                for(int i = 0; i < filled.length; i++) {
                    for( int i2 = 0; i2 < filled[i].length;i2++) {
                        for (int i3 = 0; i3 < filled[i][i2].length; i3++) {
                            newFilled[i][i2][i3] = filled[i][i2][i3];
                        }
                    }
                }
                while(bricks.stream().anyMatch(brick2 -> canFall(brick2, newFilled))) {
                    bricks.forEach(brick2 -> {
                        if(brick2.id == brick.id)return;
                        for(int x2 = brick2.startX; x2< brick2.endX+1; x2++) {
                            for (int y =brick2.startY; y< brick2.endY +1 ; y++) {
                                for (int z= brick2.startZ; z< brick2.endZ + 1; z++) {
                                    newFilled[x2][y][z] = false;
                                }
                            }
                        }
                        boolean fallen =false;
                        while(canFall(brick2, newFilled)) {
                            brick2.startZ--;
                            brick2.endZ--;
                            fallen = true;
                        }
                        for(int x2 = brick2.startX; x2< brick2.endX+1; x2++) {
                            for (int y =brick2.startY; y< brick2.endY +1 ; y++) {
                                for (int z= brick2.startZ; z< brick2.endZ + 1; z++) {
                                    newFilled[x2][y][z] = true;
                                }
                            }
                        }
                        if(fallen) vallend.incrementAndGet();
                    });
                }
                System.out.println(vallendSom.addAndGet(vallend.get()));

                for(int x2 = brick.startX; x2< brick.endX+1; x2++) {
                    for (int y =brick.startY; y< brick.endY +1 ; y++) {
                        for (int z= brick.startZ; z< brick.endZ + 1; z++) {
                            filled[x2][y][z] = true;
                        }
                    }
                }
                bricks.clear();
                backup.forEach(k -> bricks.add(new Brick(k.startX,k.startY, k.startZ, k.endX,k.endY, k.endZ, k.id)));

            };


            return vallendSom.toString();
        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }
    }

    private static boolean canFall(Brick brick, boolean[][][] filled) {
        if(brick.startZ == 1) return false;
        boolean space = true;
        for(int x = brick.startX; x< brick.endX+1; x++) {
            for (int y = brick.startY; y< brick.endY +1 ; y++) {
                if(filled[x][y][brick.startZ - 1]) space = false;
            }
        }
        return space;
    }

    private static class Brick {
        public int startX;
        public int startY;
        public int startZ;
        public int endX;
        public int endY;
        public int endZ;
        public int id;

        public Brick(int startX, int startY, int startZ, int endX, int endY, int endZ, int id) {

            this.startX = startX;
            this.startY = startY;
            this.startZ = startZ;
            this.endX = endX;
            this.endY = endY;
            this.endZ = endZ;
            this.id = id;
        }

        @Override
        public String toString() {
            return String.format("%d: %d %d %d : %d %d %d", id, startX, startY, startZ, endX, endY, endZ);
        }

    }

    ;

}
