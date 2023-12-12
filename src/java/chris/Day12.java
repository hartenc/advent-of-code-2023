package chris;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.Collectors;

public class Day12 {
    private static List<String> matches = new ArrayList<>();
    private static List<Integer> nummers = new ArrayList<>();
    public static void main(String[] args) {
        System.out.println(puzzel1());
//        System.out.println(puzzel2());
    }

    private static String puzzel1() {

        try (BufferedReader bufferedReader = new BufferedReader(new FileReader("puzzel12a.txt"))) {
            List<Puzzel> puzzels = new ArrayList<>();
            String regel;
            while ((regel = bufferedReader.readLine()) != null) {
                String puzzel = regel.split(" ")[0];
                List<Integer> aantallen = Arrays.stream(regel.split(" ")[1].split(",")).map(Integer::parseInt).collect(Collectors.toList());
                puzzels.add(new Puzzel(puzzel, aantallen));
            }
            AtomicLong total = new AtomicLong();
            puzzels.forEach(puzzel -> {
                AtomicLong aantallen = new AtomicLong();
                solve(puzzel, 0, 0, aantallen);
                matches.clear();
                nummers.clear();
                total.addAndGet(aantallen.get());
            });
            return total.toString();
        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }
    }

    private static void solve(Puzzel puzzel, int startGetal, int startIndex, AtomicLong huidigeWaarde) {

        Integer lengte = puzzel.aantallen.get(startGetal);
        for (int i = startIndex; i < puzzel.code.length(); i++) {
            if(nummers.size() > startGetal) nummers.remove(nummers.size()-1);
            if (i + lengte > puzzel.code.length()) {
                return;
            }
            String substr = puzzel.code.substring(i, i + lengte);
            int match = 0;
            for (int x = 0; x < substr.length(); x++) {
                if (substr.charAt(x) == '#' || substr.charAt(x) == '?') {
                    match++;
                } else {
                    break;
                }
            }
            if (match == substr.length() && (i + lengte == puzzel.code.length() || puzzel.code.charAt(i+lengte) != '#')) {
                if(nummers.size() <= startGetal) nummers.add(i);
                if((startGetal + 1) == puzzel.aantallen.size()) {
                    String nrCode = nummers.stream().map(nr -> nr.toString()).collect(Collectors.joining("-"));
                    if(!matches.contains(nrCode)) {
                        matches.add(nrCode);
                        huidigeWaarde.incrementAndGet();
                    }
                    nummers.remove(nummers.size()-1);
                    return;
                }
                solve(puzzel, startGetal + 1, i + lengte + 1, huidigeWaarde);
            } else {
                solve(puzzel, startGetal, i + 1, huidigeWaarde);
            }

        }
    }

    private static class Puzzel {
        public String code;
        public List<Integer> aantallen;

        public Puzzel(String code, List<Integer> aantallen) {
            this.code = code;
            this.aantallen = aantallen;
        }
    }
}
