package chris;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class Day7 {
    public static void main(String[] args) {
        System.out.println(puzzel1());
        System.out.println(puzzel2());
    }

    private static String puzzel1() {
        try (BufferedReader bufferedReader = new BufferedReader(new FileReader("puzzel7.txt"))) {
            String regel;
            List<Hand> handen = new ArrayList<>();
            while ((regel = bufferedReader.readLine()) != null) {
                String[] delen = regel.split(" ");
                handen.add(new Hand(delen[0], Integer.parseInt(delen[1])));
            }
            handen.sort(handComparator);
            Long sum = 0L;
            for (int x = 0; x < handen.size(); x++) {
                sum += ((x + 1) * handen.get(x).bid);
            }

            return sum.toString();
        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }
    }

    private static String puzzel2() {
        try (BufferedReader bufferedReader = new BufferedReader(new FileReader("puzzel7.txt"))) {
            String regel;
            List<Hand> handen = new ArrayList<>();
            while ((regel = bufferedReader.readLine()) != null) {
                String[] delen = regel.split(" ");
                handen.add(new Hand(delen[0], Integer.parseInt(delen[1])));
            }
            handen.sort(handComparator2);
            Long sum = 0L;
            for (int x = 0; x < handen.size(); x++) {
                sum += ((x + 1) * handen.get(x).bid);
            }

            return sum.toString();
        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }
    }

    private static class Hand {
        public String hand;
        public Integer bid;

        public Hand(String hand, Integer bid) {
            this.hand = hand;
            this.bid = bid;
        }
    }

    private static Comparator<Hand> handComparator = (o1, o2) -> {
        if (getScore(o1) == getScore(o2)) {
            for (int x = 0; x < 5; x++) {
                if (tekenToWaarde(o1.hand.charAt(x)) > tekenToWaarde(o2.hand.charAt(x))) return 1;
                if (tekenToWaarde(o1.hand.charAt(x)) < tekenToWaarde(o2.hand.charAt(x))) return -1;
            }
            return 0;
        } else {
            return Integer.compare(getScore(o1), getScore(o2));
        }
    };

    private static Comparator<Hand> handComparator2 = (o1, o2) -> {
        if (getScore2(o1) == getScore2(o2)) {
            for (int x = 0; x < 5; x++) {
                if (tekenToWaarde2(o1.hand.charAt(x)) > tekenToWaarde2(o2.hand.charAt(x))) return 1;
                if (tekenToWaarde2(o1.hand.charAt(x)) < tekenToWaarde2(o2.hand.charAt(x))) return -1;
            }
            return 0;
        } else {
            return Integer.compare(getScore2(o1), getScore2(o2));
        }
    };

    private static int getScore(Hand h) {
        String hand = h.hand;
        Map<Character, Integer> tekenMap = new HashMap<>();
        for (int x = 0; x < 5; x++) {
            Character teken = hand.charAt(x);
            if (tekenMap.containsKey(teken)) {
                tekenMap.put(teken, tekenMap.get(teken) + 1);
            } else {
                tekenMap.put(teken, 1);
            }
        }
        Collection<Integer> aantallen = tekenMap.values();
        if (aantallen.contains(5)) return 9;
        if (aantallen.contains(4)) return 8;
        if (aantallen.contains(3) && aantallen.contains(2)) return 7;
        if (aantallen.contains(3)) return 6;
        long aantalTwees = aantallen.stream().filter(getal -> getal == 2).count();
        if (aantalTwees == 2) return 5;
        if (aantallen.contains(2)) return 4;
        return 3;
    }

    private static int getScore2(Hand h) {
        String hand = h.hand;
        if (hand.equals("53J5J")) {
            String stop="";
        }
        Map<Character, Integer> tekenMap = new HashMap<>();
        for (int x = 0; x < 5; x++) {
            Character teken = hand.charAt(x);
            if (tekenMap.containsKey(teken)) {
                tekenMap.put(teken, tekenMap.get(teken) + 1);
            } else {
                tekenMap.put(teken, 1);
            }
        }
        int aantalJ = 0;
        if (tekenMap.containsKey('J')) {
            aantalJ = tekenMap.get('J');
            tekenMap.remove('J');
        }
        if (aantalJ == 5) return 9;
        Collection<Integer> aantallen = tekenMap.values();
        List<Integer> sortedAantallen = aantallen.stream().sorted(Comparator.reverseOrder()).collect(Collectors.toList());
        sortedAantallen.set(0, sortedAantallen.get(0) + aantalJ);
        if (sortedAantallen.contains(5)) return 9;
        if (sortedAantallen.contains(4)) return 8;
        if (sortedAantallen.contains(3) && sortedAantallen.contains(2)) return 7;
        if (sortedAantallen.contains(3)) return 6;
        long aantalTwees = sortedAantallen.stream().filter(getal -> getal == 2).count();
        if (aantalTwees == 2) return 5;
        if (sortedAantallen.contains(2)) return 4;
        return 3;
    }

    private static int tekenToWaarde(Character teken) {
        switch (teken) {
            case 'A' -> {
                return 14;
            }
            case 'K' -> {
                return 13;
            }
            case 'Q' -> {
                return 12;
            }
            case 'J' -> {
                return 11;
            }
            case 'T' -> {
                return 10;
            }
            default -> {
                return Integer.parseInt(teken.toString());
            }
        }
    }

    private static int tekenToWaarde2(Character teken) {
        switch (teken) {
            case 'A' -> {
                return 14;
            }
            case 'K' -> {
                return 13;
            }
            case 'Q' -> {
                return 12;
            }
            case 'J' -> {
                return 0;
            }
            case 'T' -> {
                return 10;
            }
            default -> {
                return Integer.parseInt(teken.toString());
            }
        }
    }
}
