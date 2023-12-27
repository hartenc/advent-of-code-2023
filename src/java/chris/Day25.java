package chris;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicLong;

public class Day25 {
    private static Map<String, String[]> connections = new HashMap<>();
    private static AtomicLong l = new AtomicLong();

    public static void main(String[] args) {
        System.out.println(puzzel1());
//        System.out.println(puzzel2());
    }

    private static String puzzel1() {
        try (BufferedReader bufferedReader = new BufferedReader(new FileReader("puzzel25.txt"))) {
            String regel;
            List<String> com1 = new ArrayList<>();
            List<String> com2 = new ArrayList<>();

            while ((regel = bufferedReader.readLine()) != null) {
                String[] parts = regel.split(": ");
                connections.put(parts[0], parts[1].split(" "));
            }
            connections.forEach((nm, ct) -> {
                l.set(0);
                com1.add(nm);
                connections.forEach((naam, conn) -> {
                    if (Arrays.stream(conn).anyMatch(str -> com1.contains(str))) {
                        addDeeperConnections(naam, connections.get(naam), com1);
                    }
                });
                com1.clear();
                System.out.println("@ " + nm + ": "+l);
            });
//            com2.add("rsh");
//            connections.forEach((naam, conn) -> {
//                if (Arrays.stream(conn).anyMatch(str -> com2.contains(str))) {
//                    addDeeperConnections(naam, connections.get(naam), com2);
//                }
//            });
//            connections.forEach((naam, conn) -> {
//                List<String> passed;
//
//            });

            return "";
        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }
    }

    private static void addConnections(String naam, String[] con, List<String> left, List<String> right) {
        if (!left.contains(naam) || Arrays.stream(con).anyMatch(str -> left.stream().anyMatch(s -> s.equals(str)))) {
            if (!left.contains(naam)) left.add(naam);
            for (int x = 0; x < con.length; x++) {
                addDeeperConnections(con[x], connections.get(con[x]), left);
            }
        } else if (!right.contains(naam) || Arrays.stream(con).anyMatch(str -> right.stream().anyMatch(s -> s.equals(str)))) {
            if (!right.contains(naam)) right.add(naam);
            for (int x = 0; x < con.length; x++) {
                addDeeperConnections(con[x], connections.get(con[x]), right);
            }
        }
    }

    private static void addDeeperConnections(String naam, String[] con, List<String> target) {
        l.incrementAndGet();
        if (!target.subList(1, target.size()).contains(naam)) target.add(naam);
        if (!connections.containsKey(naam)) return;
        for (int x = 0; x < con.length; x++) {
            String[] c = connections.get(con[x]);
            addDeeperConnections(con[x], c, target);
        }
    }


}
