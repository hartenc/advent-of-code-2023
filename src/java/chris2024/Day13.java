package chris2024;

import java.awt.*;
import java.awt.geom.Line2D;
import java.awt.geom.Point2D;
import java.io.BufferedReader;
import java.io.FileReader;
import java.math.BigInteger;
import java.util.*;
import java.util.stream.Collectors;

public class Day13 {

    public static void main(String[] args) {
        System.out.println(puzzel1());
        System.out.println(puzzel2());
    }

    private static Long puzzel1() {
        Long tokens = 0L;
        try (BufferedReader bufferedReader = new BufferedReader(new FileReader("puzzel13.txt"))) {
            String regel;
            while ((regel = bufferedReader.readLine()) != null) {
                String[] parts = regel.split(" ");
                long aX = Integer.parseInt(parts[2].substring(2,parts[2].length()-1));
                long aY = Integer.parseInt(parts[3].substring(2));
                regel = bufferedReader.readLine();
                parts = regel.split(" ");
                long bX = Integer.parseInt(parts[2].substring(2,parts[2].length()-1));
                long bY = Integer.parseInt(parts[3].substring(2));
                regel = bufferedReader.readLine();
                parts = regel.split(" ");
                long priceX = Integer.parseInt(parts[1].substring(2,parts[1].length()-1));
                long priceY = Integer.parseInt(parts[2].substring(2));
                regel = bufferedReader.readLine();

                int maxBPresses = Math.min((int)(priceX / bX), (int)(priceY / bY));
                for(int x=maxBPresses; x >= 0; x--) {
                    long xOver = priceX - (bX * x);
                    long yOver = priceY - (bY * x);

                    long aPresses = xOver / aX;
                    if (aX * aPresses != xOver || aY * aPresses != yOver) {
                        continue;
                    }
                    tokens += (aPresses * 3) + x;
                    break;
                }
                System.out.println("machine!");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return tokens;
    }

    private static Long puzzel2() {
        Long tokens = 0L;
        try (BufferedReader bufferedReader = new BufferedReader(new FileReader("puzzel13.txt"))) {
            String regel;
            while ((regel = bufferedReader.readLine()) != null) {
                String[] parts = regel.split(" ");
                long aX = Integer.parseInt(parts[2].substring(2,parts[2].length()-1));
                long aY = Integer.parseInt(parts[3].substring(2));
                regel = bufferedReader.readLine();
                parts = regel.split(" ");
                long bX = Integer.parseInt(parts[2].substring(2,parts[2].length()-1));
                long bY = Integer.parseInt(parts[3].substring(2));
                regel = bufferedReader.readLine();
                parts = regel.split(" ");
                long priceX = Integer.parseInt(parts[1].substring(2,parts[1].length()-1)) + 10000000000000L;
                long priceY = Integer.parseInt(parts[2].substring(2)) + 10000000000000L;
                regel = bufferedReader.readLine();

                Line2D l1 = new Line2D.Double(new Point(0,0), new Point2D.Double(bX*10000000000000L, bY * 10000000000000L));
                Long toZero = priceY / aY;
                Line2D l2 = new Line2D.Double(new Point2D.Double(priceX, priceY), new Point2D.Double(aX - (toZero * aX), aY - (toZero * aY)));

                System.out.println(l1.intersectsLine(l2));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return tokens;
    }
}
