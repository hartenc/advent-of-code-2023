package chris2024;

import java.io.BufferedReader;
import java.io.FileReader;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class Day9 {
    static List<Integer> drive = new ArrayList<>();
    public static void main(String[] args) {
//        System.out.println(puzzel1());
        System.out.println(puzzel2());
    }

    private static void buildDrive() {

        try (BufferedReader bufferedReader = new BufferedReader(new FileReader("puzzel9.txt"))) {
            char[] files = bufferedReader.readLine().toCharArray();

            for(int x=0;x<files.length;x++) {
                Integer toWrite;
                if (x % 2 == 0) {
                    toWrite = x/2;
                } else {
                    toWrite = -1;
                }
                String a = "";
                IntStream.generate(() -> toWrite).limit(files[x] - 48).forEach(i -> drive.add(i));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static String puzzel1() {
        buildDrive();
        while(drive.contains(-1)) {
            if(drive.getLast()== -1) { drive.removeLast(); continue; }
            Integer move = drive.getLast();
            drive.set(drive.indexOf(-1), move);
            drive.removeLast();
        }
        BigInteger checksum = BigInteger.ZERO;
        for(int x=0;x< drive.size();x++) {
            checksum = checksum.add(BigInteger.valueOf(x).multiply(BigInteger.valueOf(drive.get(x))));
        }
        return checksum.toString();
    }

    private static String puzzel2() {
        buildDrive();
        for(int x = 9999; x > 1; x--) {
            int firstIndex = drive.indexOf(x);
            int lastIndex = drive.lastIndexOf(x);
            int size = (lastIndex - firstIndex+1);
            int emptyBlock = 0;
            for (int y=0; y< drive.size(); y++) {
                if (drive.get(y) == x) break;
                if (drive.get(y) == -1) emptyBlock++; else emptyBlock = 0;
                if(emptyBlock== size) {
                    for (int z=firstIndex; z < lastIndex + 1; z++) {
                        drive.set(z, -1);
                    }
                    for (int z = (y - emptyBlock) + 1; z < y + 1; z++) {
                        drive.set(z, x);
                    }
                    break;
                }
            }
            while (drive.getLast() == -1) drive.removeLast();
        }
        BigInteger checksum = BigInteger.ZERO;
        for(int x=0;x< drive.size();x++) {
            if(drive.get(x) != -1) checksum = checksum.add(BigInteger.valueOf(x).multiply(BigInteger.valueOf(drive.get(x))));
        }
        return checksum.toString();
    }
}
