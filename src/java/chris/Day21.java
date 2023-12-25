package chris;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.*;
import java.util.concurrent.atomic.AtomicLong;


public class Day21 {
    private static List<List<Character>> orgField = new ArrayList<>();
    private static List<List<Character>> field = new ArrayList<>();
    private static Map<Pos, Quadrant> quadrantMap = new HashMap<>();

    public static void main(String[] args) {
//        System.out.println(puzzel1());
        System.out.println(puzzel2());
    }

    // 0 = boven 1 = rechts 2=onder 3 = links
    private static String puzzel1() {
        try (BufferedReader bufferedReader = new BufferedReader(new FileReader("puzzel21a.txt"))) {

            String regel;
            while ((regel = bufferedReader.readLine()) != null) {

                List<Character> fill = new ArrayList<>();
                for (int x = 0; x < regel.length(); x++) {
                    if (regel.charAt(x) == 'S') {
                        fill.add('O');
                    } else {
                        fill.add(regel.charAt(x));
                    }
                }
                field.add(fill);
            }
            for (int it = 0; it < 50; it++) {
                List<List<Character>> newField = copyField(field);
                for (int y = 0; y < field.size(); y++) {
                    for (int x = 0; x < field.get(0).size(); x++) {
                        if (field.get(y).get(x) == 'O') {
                            if (x != 0 && field.get(y).get(x - 1) != '#') newField.get(y).set(x - 1, 'O');
                            if (y != 0 && field.get(y - 1).get(x) != '#') newField.get(y - 1).set(x, 'O');
                            if (x < field.get(0).size() - 1 && field.get(y).get(x + 1) != '#')
                                newField.get(y).set(x + 1, 'O');
                            if (y < field.size() - 1 && field.get(y + 1).get(x) != '#') newField.get(y + 1).set(x, 'O');
                            newField.get(y).set(x, '.');
                        }
                    }
                }
                field = copyField(newField);
            }
            Long count = 0L;
            for (int r = 0; r < field.size(); r++) {
                count += field.get(r).stream().filter(c -> c == 'O').count();
            }
            return count.toString();
        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }
    }

    private static String puzzel2() {
        field = new ArrayList<>();
        try (BufferedReader bufferedReader = new BufferedReader(new FileReader("puzzel21a.txt"))) {

            String regel;
            while ((regel = bufferedReader.readLine()) != null) {

                List<Character> fill = new ArrayList<>();
                for (int x = 0; x < regel.length(); x++) {
                    if (regel.charAt(x) == 'S') {
                        fill.add('O');
                    } else {

                        fill.add(regel.charAt(x));

                    }
                }
                field.add(fill);
            }
            orgField = copyField(field);
            quadrantMap.put(new Pos(0, 0), new Quadrant(0, 0, field));

            for (int it = 0; it < 10; it++) {
                Map<Pos, Quadrant> scan = new HashMap<>();
                quadrantMap.forEach((pos, quadrantMap) -> scan.put(pos, quadrantMap));
                scan.forEach((pos, quadrant) -> {
                    List<List<Character>> newField = copyField(quadrant.field);
                    for (int y = 0; y < quadrant.field.size(); y++) {
                        for (int x = 0; x < quadrant.field.get(0).size(); x++) {
                            if (quadrant.field.get(y).get(x) == 'O') {
                                // left
                                if (x == 0) {
                                    if (quadrantMap.containsKey(new Pos(pos.x - 1, pos.y))) {
                                        quadrantMap.get(new Pos(pos.x - 1, pos.y)).field.get(field.size() - 1).set(y, 'O');
                                    } else {
                                        Quadrant q = new Quadrant(pos.x - 1, pos.y, copyField(orgField));
                                        quadrantMap.put(new Pos(pos.x - 1, pos.y), q);
                                        quadrantMap.get(new Pos(pos.x - 1, pos.y)).field.get(field.size() - 1).set(y, 'O');
                                    }
                                } else {
                                    if (quadrant.field.get(y).get(x - 1) != '#')
                                        newField.get(y).set(x - 1, 'O');
                                }
                                // UP
                                if (y == 0) {
                                    if (quadrantMap.containsKey(new Pos(pos.x, pos.y - 1))) {
                                        quadrantMap.get(new Pos(pos.x, pos.y - 1)).field.get(field.size() - 1).set(y, 'O');
                                    } else {
                                        Quadrant q = new Quadrant(pos.x, pos.y - 1, copyField(orgField));
                                        quadrantMap.put(new Pos(pos.x, pos.y - 1), q);
                                        quadrantMap.get(new Pos(pos.x, pos.y - 1)).field.get(field.size() - 1).set(y, 'O');
                                    }
                                } else {
                                    if (quadrant.field.get(y - 1).get(x) != '#') newField.get(y - 1).set(x, 'O');
                                }
                                // RIGHT
                                if (x == quadrant.field.get(0).size() - 1) {
                                    if (quadrantMap.containsKey(new Pos(pos.x + 1, pos.y))) {
                                        quadrantMap.get(new Pos(pos.x + 1, pos.y)).field.get(0).set(y, 'O');
                                    } else {
                                        Quadrant q = new Quadrant(pos.x + 1, pos.y, copyField(orgField));
                                        quadrantMap.put(new Pos(pos.x + 1, pos.y), q);
                                        quadrantMap.get(new Pos(pos.x + 1, pos.y)).field.get(0).set(y, 'O');
                                    }
                                } else {
                                    if (quadrant.field.get(y).get(x + 1) != '#')
                                        newField.get(y).set(x + 1, 'O');
                                }
                                // DOWN
                                if (y == quadrant.field.get(0).size() - 1) {
                                    if (quadrantMap.containsKey(new Pos(pos.x, pos.y + 1))) {
                                        quadrantMap.get(new Pos(pos.x, pos.y + 1)).field.get(0).set(y, 'O');
                                    } else {
                                        Quadrant q = new Quadrant(pos.x, pos.y + 1, copyField(orgField));
                                        quadrantMap.put(new Pos(pos.x, pos.y + 1), q);
                                        quadrantMap.get(new Pos(pos.x, pos.y + 1)).field.get(0).set(y, 'O');
                                    }
                                } else {
                                    if (y < quadrant.field.size() - 1 && quadrant.field.get(y + 1).get(x) != '#')
                                        newField.get(y + 1).set(x, 'O');
                                    newField.get(y).set(x, '.');
                                }
                            }
                        }
                    }
                    quadrantMap.put(pos, new Quadrant(pos.x, pos.y, copyField(newField)));
                });
            }
            AtomicLong val = new AtomicLong();
            List<Pos> qPos = quadrantMap.keySet().stream().toList();
            long minX = qPos.stream().map(p -> p.x).sorted().findFirst().get();
            long minY = qPos.stream().map(p -> p.y).sorted().findFirst().get();
            long maxX = qPos.stream().map(p -> p.x).sorted(Comparator.reverseOrder()).findFirst().get();
            long maxY = qPos.stream().map(p -> p.y).sorted(Comparator.reverseOrder()).findFirst().get();
            for (long b = minY; b <= maxY; b++) {
                for (long a = minX; a <= maxX; a++) {
                    if(quadrantMap.containsKey(new Pos(a,b))) {
                        System.out.println(quadrantMap.get(new Pos(a, b)).toString());
                    } else {
                        System.out.print(new Quadrant(0,0, copyField(orgField)));
                    }
                    System.out.println("a="+a+",b="+b);
                }
            }
            quadrantMap.forEach((pos, quadrant) -> {
                for (int r = 0; r < field.size(); r++) {
                    val.addAndGet(quadrant.field.get(r).stream().filter(c -> c == 'O').count());
                }
            });
            return val.toString();
        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }
    }

    private static class Quadrant {
        public long x;
        public long y;
        public List<List<Character>> field;

        public Quadrant(long x, long y, List<List<Character>> field) {
            this.x = x;
            this.y = y;
            this.field = field;
        }

        @Override
        public String toString() {
            StringBuilder b = new StringBuilder();
            for (int y = 0; y < field.size(); y++) {
                for (int x = 0; x < field.get(0).size(); x++) {
                    b.append(field.get(y).get(x));
                }
                b.append("\n");
            }
            return b.toString();
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
            Pos pos = (Pos) o;
            return x == pos.x && y == pos.y;
        }

        @Override
        public int hashCode() {
            return Objects.hash(x, y);
        }
    }

    private static List<List<Character>> copyField(List<List<Character>> input) {
        List<List<Character>> newField = new ArrayList<>();
        for (int y = 0; y < input.size(); y++) {
            newField.add(new ArrayList<>());
            for (int x = 0; x < input.get(0).size(); x++) {
                newField.get(y).add(input.get(y).get(x));
            }
        }
        return newField;
    }


}
