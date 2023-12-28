package chris;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Day25b {
    public static List<Connectie> connecties = new ArrayList<>();
    public static void main(String[] args) {
        System.out.println(puzzel1());
//        System.out.println(puzzel2());
    }

    private static long puzzel1() {
        try (BufferedReader bufferedReader = new BufferedReader(new FileReader("puzzel25.txt"))) {
            String regel;


            while ((regel = bufferedReader.readLine()) != null) {
                String[] parts = regel.split(": ");
                Arrays.stream(parts[1].split(" ")).forEach(b -> {
                    Connectie c = new Connectie(parts[0], b);
                    if (!connecties.contains(c))
                        connecties.add(c);

                });
            }

//            for(int x = 0; x< 1000000; x++) {
//                Connectie connectie1 = connecties.get((int)(Math.random()* connecties.size()));
//                Connectie connectie2 = connecties.get((int)(Math.random()* connecties.size()));
//                distance(connectie1.a, connectie2.b, new ArrayList<>());
//            }
//            connecties.sort((a,b) -> b.gebruik - a.gebruik);
            long it = 0L;
            while (true) {
                it++;
                if(it % 10000 == 0) System.out.println("Poging " + it);
                int remove1 = (int)(Math.random() * connecties.size());
                int remove2 = (int)(Math.random() * connecties.size());
                int remove3 = (int)(Math.random() * connecties.size());
                List<Connectie> deel1 = new ArrayList<>();
                List<Connectie> deel2 = new ArrayList<>();
                List<Connectie> cons = connecties.stream().collect(Collectors.toList());
                cons.remove(Math.min(cons.size()-1,remove3));
                cons.remove(Math.min(cons.size()-1,remove2));
                cons.remove(Math.min(cons.size()-1,remove1));
                while (true) {
                    Integer size = deel1.size();
                    cons.stream().filter(c -> !(deel1.contains(c) || deel2.contains(c))).forEach(connectie -> {
                        if (deel1.isEmpty()) {
                            deel1.add(connectie);
                            return;
                        }
                        if (deel1.stream().anyMatch(d1 -> d1.connect(connectie))) {
                            deel1.add(connectie);
                        }
                    });

                    cons.removeAll(deel1);
                    if (size == deel1.size()) break;
                }

                deel2.addAll(cons);
                if (!deel2.isEmpty()) return calc(deel1) * calc(deel2);
                if(connecties.isEmpty()) break;
            }


            return 0L;
        } catch (Exception e) {
            e.printStackTrace();
            return 0L;
        }
    }

    static boolean distance(String a, String b, List<Connectie> pad) {
        if(connecties.contains(new Connectie(a,b))) {
            pad.forEach(pd -> pd.gebruik++);
            return true;
        }
        List<Connectie> possible = connecties.stream().filter(con -> con.a.equals(a)).toList();

        for(Connectie c : possible) {
            if(pad.contains(c)) continue;
            pad.add(connecties.stream().filter(co -> co.a.equals(a) && co.b.equals(c.b)).findFirst().get());
            boolean result = distance(c.b, b, pad);
            if(result) return true;
        }
        return false;
    }

    private static long calc(List<Connectie> items) {
        List<String> a = items.stream().map(con -> con.a).toList();
        List<String> b = items.stream().map(con -> con.b).toList();
        return Stream.concat(a.stream(), b.stream()).distinct().count();
    }

    static class Connectie {
        private final String a;
        private final String b;
        public int gebruik = 0;

        Connectie(String a, String b) {
            this.a = a;
            this.b = b;
        }

        public boolean connect(Connectie andere) {
            return (andere.a.equals(b) || andere.b.equals(a) || andere.a.equals(a) || andere.b.equals(b));
        }

        @Override
        public boolean equals(Object b) {
            if (!(b instanceof Connectie)) return false;
            Connectie ander = (Connectie) b;
            if (this.a.equals(ander.a) && this.b.equals(ander.b)) return true;
            if (this.a.equals(ander.b) && this.b.equals(ander.a)) return true;
            return false;
        }
    }


}
