package chris;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class Day18 {
    private static List<Trench> trenches = new ArrayList<>();
    public static void main(String[] args) {
//        System.out.println(puzzel1());
        System.out.println(puzzel2());
    }

    private static String puzzel1() {
        try (BufferedReader bufferedReader = new BufferedReader(new FileReader("puzzel18.txt"))) {

            List<Pos> fill = new ArrayList<>();
            String regel;
            int x = 0;
            int y = 0;
            while ((regel = bufferedReader.readLine()) != null) {
                String[] parts = regel.split(" ");
                int length = Integer.parseInt(parts[1]);
                for (int i = 0; i < length; i++) {
                    trenches.add(new Trench(x, y, parts[2].substring(2, parts[2].length() - 1)));
                    switch (parts[0]) {
                        case "R" -> x++;
                        case "D" -> y++;
                        case "L" -> x--;
                        case "U" -> y--;
                    }
                }
            }
            List<List<Character>> fields = new ArrayList<>();
            int minX = trenches.stream().min((a, b) -> a.x - b.x).get().x;
            int minY = trenches.stream().min((a, b) -> a.y - b.y).get().y;
            int maxX = trenches.stream().max((a, b) -> a.x - b.x).get().x + 1;
            int maxY = trenches.stream().max((a, b) -> a.y - b.y).get().y + 1;
            for(int y1 = minY; y1<= maxY; y1++) {
                List<Character> row = new ArrayList<>();
                for(int x1 = minX; x1  <= maxX; x1++ ) {
                    boolean match = false;
                    for (int t = 0; t < trenches.size(); t++) {
                        if (trenches.get(t).x == x1 && trenches.get(t).y == y1) {
                            row.add('#');
                            match = true;
                        }
                    }
                    if(!match) row.add('.');
                }
                fields.add(row);
            }

            fields.get(87).set(18, '*');
            long it = 0L;
            while(true) {
                it++;
                boolean chamged = false;
                for(int yy=0; yy< fields.size();yy++) {
                    for (int xx = 0; xx< fields.get(0).size(); xx++) {
                        if(fields.get(yy).get(xx) == '*') {
                            if (yy > 0 && fields.get(yy -1).get(xx) != '#' && fields.get(yy -1).get(xx) != '*') {fields.get(yy-1).set(xx, '*'); chamged = true;}
                            if (yy < fields.size()-1 && fields.get(yy +1).get(xx) != '#' && fields.get(yy +1).get(xx) != '*') {fields.get(yy+1).set(xx, '*'); chamged = true;}
                            if (xx > 0 && fields.get(yy).get(xx-1) != '#' && fields.get(yy).get(xx-1) != '*') {fields.get(yy).set(xx -1, '*'); chamged = true;}
                            if (xx < fields.get(yy).size()-1 && fields.get(yy).get(xx + 1) != '#' && fields.get(yy).get(xx + 1) != '*') {fields.get(yy).set(xx + 1, '*'); chamged =true;}
                        }

                    }
                }
                if(!chamged) break;
            }
            AtomicInteger aantal = new AtomicInteger();
            fields.forEach(charList -> {
                charList.forEach(chr -> {
                    if(chr=='#' || chr=='*') aantal.incrementAndGet();
                });
            });

            return aantal.toString();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private static String puzzel2() {
        try (BufferedReader bufferedReader = new BufferedReader(new FileReader("puzzel18.txt"))) {

            String regel;
            int x = 0;
            int y = 0;
            while ((regel = bufferedReader.readLine()) != null) {
                String[] parts = regel.split(" ");

                    trenches.add(new Trench(0, 0, parts[2]));

            }
            List<Pos> hoeken = routeGraven(trenches);

            return "" +(getOppervlakte(hoeken) + getAfstand(hoeken));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private static Pos volgendePositie(Pos huidig, int richting, long lengte) {
        switch (richting) {
            case 0:
                return new Pos(huidig.x + lengte, huidig.y);
            case 2:
                return new Pos(huidig.x - lengte, huidig.y);
            case 3:
                return new Pos(huidig.x, huidig.y - lengte);
            case 1:
            default:
                return new Pos(huidig.x, huidig.y + lengte);
        }

    }

    private static List<Pos> routeGraven(List<Trench> trenches) {
        List<Pos> hoeken = new ArrayList<>();
        Pos huidig = new Pos(0, 0);
        hoeken.add(huidig);
        for (int i = 0; i < trenches.size(); i++) {
            Long afstand = Long.parseLong(trenches.get(i).color.substring(2, 7),16);
            int richting = Integer.parseInt(trenches.get(i).color.substring(7,8));
            huidig = volgendePositie(huidig, richting, afstand);
            if (i != trenches.size() - 1) {
                hoeken.add(huidig);
            }
        }
        return hoeken;
    }

    private static long getAfstand(List<Pos> hoeken) {
        long total = 0;

        for (int i = 0; i < hoeken.size(); i++) {
            Pos c1 = hoeken.get(i);
            Pos c2 = hoeken.get(i == hoeken.size() - 1 ? 0 : i + 1);
            total += Math.abs(c1.x - c2.x) + Math.abs(c1.y - c2.y);
        }

        return total;
    }

    private static long getOppervlakte(List<Pos> hoeken) {
        // algoritme gestolen van internet
        long area = 0;
        for (int i = 0; i < hoeken.size(); i++) {
            Pos c1 = hoeken.get(i);
            Pos c2 = hoeken.get(i == hoeken.size() - 1 ? 0 : i + 1);
            long factor = c1.x * c2.y - c1.y * c2.x;
            area += factor;
        }
        area /= 2;
        return area - (getAfstand(hoeken) / 2) + 1;
    }

    private static class Trench {
        public final int x;
        public final int y;
        public final String color;

        private Trench(int x, int y, String color) {
            this.x = x;
            this.y = y;
            this.color = color;
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

}
