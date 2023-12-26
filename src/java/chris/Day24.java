package chris;

import java.awt.*;
import java.awt.geom.Line2D;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.io.BufferedReader;
import java.io.FileReader;
import java.math.BigDecimal;
import java.util.List;
import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.Collectors;

public class Day24 {
//    private static final Long MIN_POS = 200000000000000L;
//    private static final Long MAX_POS = 400000000000000L;
    private static final Long MIN_POS = 7L;
    private static final Long MAX_POS = 27L;

    private static List<Line> lijnen = new ArrayList<>();

    public static void main(String[] args) {
//        System.out.println(puzzel1());
        System.out.println(puzzel2());
    }

    private static String puzzel1() {
        try (BufferedReader bufferedReader = new BufferedReader(new FileReader("puzzel24.txt"))) {

            String regel;


            while ((regel = bufferedReader.readLine()) != null) {
                String[] row = regel.split("@");
                String[] start = row[0].split(", ");
                String[] move = row[1].split(", ");
                lijnen.add(new Line(Long.parseLong(start[0].trim()), Long.parseLong(start[1].trim()), Long.parseLong(start[2].trim()), Long.parseLong(move[0].trim()), Long.parseLong(move[1].trim()), Long.parseLong(move[2].trim())));
            }
            System.out.println("Read done");
            List<Line> filteredLijnen = lijnen.stream().filter(lijn -> {
                if(lijn.x >=+ MIN_POS && lijn.y >= MIN_POS && lijn.x <= MAX_POS && lijn.y <= MAX_POS) return true;
                if(lijn.x <= MIN_POS && lijn.moveX <= 0) return false;
                if(lijn.y <= MIN_POS && lijn.moveY <= 0) return false;
                if(lijn.x >= MAX_POS && lijn.moveX >= 0) return false;
                if(lijn.y >= MAX_POS && lijn.moveY >= 0) return false;
                return true;
            }).collect(Collectors.toList());
            System.out.println("Filter done");
            filteredLijnen.forEach(Line::calcMax);
            System.out.println("Max calc done");

            AtomicLong intersect = new AtomicLong();
            AtomicInteger pos = new AtomicInteger(0);
            filteredLijnen.forEach(lijn -> {
                System.out.println("Bereken " + lijn + " vanaf " + pos);
                filteredLijnen.subList(pos.get(), filteredLijnen.size()).forEach(lijn2 -> {
                    if(lijn.equals(lijn2)) return;
                    System.out.println("Door naar " + lijn2);
                    Line2D.Double line1 = new Line2D.Double(lijn.x, lijn.y, lijn.maxX, lijn.maxY);
                    Line2D.Double line2 = new Line2D.Double(lijn2.x, lijn2.y, lijn2.maxX, lijn2.maxY);
                    Point2D intersection = getIntersectPoint(line1.getP1(), line1.getP2(), line2.getP1(), line2.getP2());
                    if(intersection == null) return;
                    // intersectiefunctie trekt de lijn ook de andere kant op, daar op controleren
                    if(intersection.getX() > lijn.x && lijn.moveX < 0) return;
                    if(intersection.getX() < lijn.x && lijn.moveX > 0) return;
                    if(intersection.getY() > lijn.y && lijn.moveY < 0) return;
                    if(intersection.getY() < lijn.y && lijn.moveY > 0) return;
                    if(intersection.getX() > lijn2.x && lijn2.moveX < 0) return;
                    if(intersection.getX() < lijn2.x && lijn2.moveX > 0) return;
                    if(intersection.getY() > lijn2.y && lijn2.moveY < 0) return;
                    if(intersection.getY() < lijn2.y && lijn2.moveY > 0) return;

                    if(intersection.getX() >= MIN_POS && intersection.getX() <= MAX_POS && intersection.getY() >= MIN_POS && intersection.getY() <= MAX_POS) {
                        intersect.incrementAndGet();
                        System.out.println("Intersect op " + intersection);
                    }

                });
                pos.incrementAndGet();
            });


            return intersect.toString();
        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }


    }

    private static String puzzel2() {
        try (BufferedReader bufferedReader = new BufferedReader(new FileReader("puzzel24a.txt"))) {

            String regel;


            while ((regel = bufferedReader.readLine()) != null) {
                String[] row = regel.split("@");
                String[] start = row[0].split(", ");
                String[] move = row[1].split(", ");
                lijnen.add(new Line(Long.parseLong(start[0].trim()), Long.parseLong(start[1].trim()), Long.parseLong(start[2].trim()), Long.parseLong(move[0].trim()), Long.parseLong(move[1].trim()), Long.parseLong(move[2].trim())));
            }
            System.out.println("Read done");

            return "";

        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }


    }

    public static Point2D getIntersectPoint(Point2D ptA, Point2D ptB, Point2D ptC, Point2D ptD) {
        if (ptA == null || ptB == null || ptC == null || ptD == null) {
            return null;
        }

        Point2D.Double ptP = null;

        double denominator =
                (ptB.getX() - ptA.getX()) * (ptD.getY() - ptC.getY())
                        - (ptB.getY() - ptA.getY()) * (ptD.getX() - ptC.getX());

        if (denominator != 0) {
            double numerator =
                    (ptA.getY() - ptC.getY()) * (ptD.getX() - ptC.getX())
                            - (ptA.getX() - ptC.getX()) * (ptD.getY() - ptC.getY());

            double r = numerator / denominator;

            ptP =
                    new Point2D.Double(
                            ptA.getX() + r * (ptB.getX() - ptA.getX()),
                            ptA.getY() + r * (ptB.getY() - ptA.getY()));
        }

        return ptP;
    }

    private static class Line {
        public final long x;
        public final long y;
        public final long z;
        public final long moveX;
        public final long moveY;
        public final long moveZ;
        public long maxX;
        public long maxY;
        public long maxZ;

        private Line(long x, long y, long z, long moveX, long moveY, long moveZ) {
            this.x = x;
            this.y = y;
            this.z = z;
            this.moveX = moveX;
            this.moveY = moveY;
            this.moveZ = moveZ;
        }

        public void calcMax() {

            long xSteps;
            if(moveX < 0) {
                xSteps = (x - MIN_POS) / moveX;
            } else {
                xSteps = (MAX_POS - x) / moveX;
            }
            long ySteps;
            if(moveY < 0) {
                ySteps = (y - MIN_POS) / moveY;
            } else {
                ySteps = (MAX_POS - y) / moveY;
            }

            long minSteps = Math.min(Math.abs(xSteps), (Math.abs(ySteps)));

            maxX = x + ((minSteps + 1) * moveX);
            maxY = y + ((minSteps + 1) * moveY);


        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            Line line = (Line) o;
            return x == line.x && y == line.y && z == line.z && moveX == line.moveX && moveY == line.moveY && moveZ == line.moveZ && maxX == line.maxX && maxY == line.maxY && maxZ == line.maxZ;
        }

        @Override
        public int hashCode() {
            return Objects.hash(x, y, z, moveX, moveY, moveZ, maxX, maxY, maxZ);
        }

        @Override
        public String toString() {
            return "Line{" +
                    "x=" + x +
                    ", y=" + y +
                    ", z=" + z +
                    ", moveX=" + moveX +
                    ", moveY=" + moveY +
                    ", moveZ=" + moveZ +
                    ", maxX=" + maxX +
                    ", maxY=" + maxY +
                    ", maxZ=" + maxZ +
                    '}';
        }
    }





}
