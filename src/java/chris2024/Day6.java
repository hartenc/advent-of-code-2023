package chris2024;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Stream;

public class Day6 {

    static List<String> map = new ArrayList<>();
    static int guardX=0;
    static int guardY=0;
    static int guardDir = 0;
    public static void main(String[] args) {
        System.out.println(puzzel1());
        System.out.println(puzzel2());
    }

    private static void readMap() {
        map = new ArrayList<>();
        try (BufferedReader bufferedReader = new BufferedReader(new FileReader("puzzel6.txt"))) {
            String regel;
            while ((regel = bufferedReader.readLine()) != null) {
                if(regel.contains("^")) {
                    guardX = regel.indexOf("^");
                    guardY = map.size();
                }
                map.add(regel);
            }
            guardDir = 0;
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static String puzzel1() {
        readMap();
        while(true) {
            char[] row = map.get(guardY).toCharArray();
            row[guardX] = 'X';
            map.set(guardY, new String(row));
            char next = peekNext();
            if(next == 'V') break;
            if(next == '#') {
                guardDir++;
                guardDir = guardDir % 4;
            } else {
                if(guardDir==0) guardY--;
                if(guardDir==1) guardX++;
                if(guardDir==2) guardY++;
                if(guardDir==3) guardX--;
            }
        }
        Long totaal = 0L;
        for(int x=0;x<map.size();x++) {
            totaal += map.get(x).chars().filter(ch -> ch == 'X').count();
        }
        return totaal.toString();
    }

    private static char peekNext() {
        if(guardDir == 0) {
            if(guardY == 0) return 'V'; else return map.get(guardY - 1).charAt(guardX);
        }
        if(guardDir == 1) {
            if(guardX == 129) return 'V'; else return map.get(guardY).charAt(guardX + 1);
        }
        if(guardDir == 2) {
            if(guardY == 129) return 'V'; else return map.get(guardY + 1).charAt(guardX);
        }
        if(guardDir == 3) {
            if(guardX == 0) return 'V'; else return map.get(guardY).charAt(guardX - 1);
        }
        return '?';
    }

    private static String puzzel2() {
        Long hits = 0L;
        for(int x=0;x<130;x++) {
            for(int y=0;y<130;y++) {
                readMap();
                if(map.get(y).charAt(x) == '#') {
                    continue;
                } else {
                    char[] row = map.get(y).toCharArray();
                    row[x] = '#';
                    map.set(y, new String(row));
                }
                int i = 0;
                while(true) {
//                    char[] row = map.get(guardY).toCharArray();
//                    row[guardX] = 'X';
//                    map.set(guardY, new String(row));
                    char next = peekNext();
                    if(next == 'V') break;
                    if(next == '#') {
                        guardDir++;
                        guardDir = guardDir % 4;
                    } else {
                        if(guardDir==0) guardY--;
                        if(guardDir==1) guardX++;
                        if(guardDir==2) guardY++;
                        if(guardDir==3) guardX--;
                    }
                    i++;
                    if (i > 20000) {
                        hits++;
                        break;
                    }
                }
            }
        }
        return hits.toString();
    }
}
