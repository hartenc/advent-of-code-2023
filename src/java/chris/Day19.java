package chris;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Day19 {
    private static Map<String, List<Instructie>> dataMap = new HashMap<>();
    private static List<Item> items = new ArrayList<>();
    private static List<Item> goed = new ArrayList<>();
    public static void main(String[] args) {
        System.out.println(puzzel1());
//        System.out.println(puzzel2());
    }

    private static String puzzel1() {



        try (BufferedReader bufferedReader = new BufferedReader(new FileReader("puzzel19.txt"))) {
            String regel;
            boolean data = true;
            while ((regel = bufferedReader.readLine()) != null) {
                if(regel.isEmpty()) {
                    data = false;
                    continue;
                }
                if(data) {
                    // lees data
                    String naam = regel.split("\\{")[0];
                    String instructies[] = regel.split("\\{")[1].split(",");
                    List<Instructie> instructieList = new ArrayList<>();
                    for(int x=0;x<instructies.length -1;x++) {
                        Character teken = instructies[x].charAt(0);
                        Character operator = instructies[x].charAt(1);
                        Integer vergelijk = Integer.parseInt(instructies[x].substring(2).split(":")[0]);
                        String target = instructies[x].substring(2).split(":")[1];
                        Instructie i = new Instructie(teken, operator, vergelijk, target);
                        instructieList.add(i);
                    }
                    instructieList.add(new Instructie('X','X',0,instructies[instructies.length-1].replace('}',' ').trim()));
                    dataMap.put(naam, instructieList);
                } else {

                       // lees items
                       String regelStripped = regel.substring(1, regel.length()-1);
                       String[] delen = regelStripped.split(",");
                       items.add(new Item(Integer.parseInt(delen[0].substring(2)),Integer.parseInt(delen[1].substring(2)),Integer.parseInt(delen[2].substring(2)),Integer.parseInt(delen[3].substring(2))));

                }
            }
            dataMap.put("A", List.of(new Instructie('A',null,null,null)));
            dataMap.put("R", List.of(new Instructie('R',null,null,null)));
            items.forEach(item -> {
                evaluate(item, dataMap.get("in"));
            });
            return goed.stream().map(a -> Long.parseLong(""+(a.x+a.m+a.a+a.s))).reduce(0L, (a, b) -> a+b).toString();
        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }
    }

    private static boolean evaluate(Item i, List<Instructie> instructies) {
        for(int x=0;x<instructies.size();x++)
        {
            Instructie instructie = instructies.get(x);
            if(instructie.teken=='A') {
                goed.add(i);
                return true;
            }
            if(instructie.teken=='R') {
                return true;
            }
           if(instructie.teken=='X') {
               boolean result = evaluate(i, dataMap.get(instructie.target));
               if(result) return true;
           }
           if(instructie.teken=='x') {
               if (instructie.operator == '<') {
                   if(i.x < instructie.vergelijk) {
                       boolean result = evaluate(i, dataMap.get(instructie.target));
                       if(result) return true;
                   }
               } else {
                   if(i.x > instructie.vergelijk) {
                       boolean result = evaluate(i, dataMap.get(instructie.target));
                       if(result) return true;
                   }
               }
           }
            if(instructie.teken=='m') {
                if (instructie.operator == '<') {
                    if(i.m < instructie.vergelijk) {
                        boolean result = evaluate(i, dataMap.get(instructie.target));
                        if(result) return true;
                    }
                } else {
                    if(i.m > instructie.vergelijk) {
                        boolean result = evaluate(i, dataMap.get(instructie.target));
                        if(result) return true;
                    }
                }
            }
            if(instructie.teken=='a') {
                if (instructie.operator == '<') {
                    if(i.a < instructie.vergelijk) {
                        boolean result = evaluate(i, dataMap.get(instructie.target));
                        if(result) return true;
                    }
                } else {
                    if(i.a > instructie.vergelijk) {
                        boolean result = evaluate(i, dataMap.get(instructie.target));
                        if(result) return true;
                    }
                }
            }
            if(instructie.teken=='s') {
                if (instructie.operator == '<') {
                    if(i.s < instructie.vergelijk) {
                        boolean result = evaluate(i, dataMap.get(instructie.target));
                        if(result) return true;
                    }
                } else {
                    if(i.s > instructie.vergelijk) {
                        boolean result = evaluate(i, dataMap.get(instructie.target));
                        if(result) return true;
                    }
                }
            }
        }
        return true;
    }

    private static class Instructie {
        private final Character teken;
        private final Character operator;
        private final Integer vergelijk;
        private final String target;

        private Instructie(Character teken, Character operator, Integer vergelijk, String target) {
            this.teken = teken;
            this.operator = operator;
            this.vergelijk = vergelijk;
            this.target = target;
        }
    }
    private static class Item {
        public final Integer x;
        public final Integer m;
        public final Integer a;
        public final Integer s;

        private Item(Integer x, Integer m, Integer a, Integer s) {
            this.x = x;
            this.m = m;
            this.a = a;
            this.s = s;
        }
    }
}
