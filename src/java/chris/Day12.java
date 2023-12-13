package chris;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class Day12 {
    private static List<String> matches = new ArrayList<>();
    private static List<Integer> nummers = new ArrayList<>();
    public static void main(String[] args) {
//        System.out.println(puzzel1());
        System.out.println(puzzel2());
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
                System.out.println(aantallen.get());
                total.addAndGet(aantallen.get());
            });
            return total.toString();
        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }
    }

    private static String puzzel2() {

        try (BufferedReader bufferedReader = new BufferedReader(new FileReader("puzzel12.txt"))) {
            List<Deel> delen = new ArrayList<>();
            String regel;
            while ((regel = bufferedReader.readLine()) != null) {
                String puzzel = regel.split(" ")[0];
                List<Integer> aantallen = Arrays.stream(regel.split(" ")[1].split(",")).map(Integer::parseInt).collect(Collectors.toList());
                String longPuzzel = IntStream.range(0,5).mapToObj(i -> puzzel).collect(Collectors.joining("?")) + ".";
                List<Integer> longList = new ArrayList<>();
                for (int x=0;x<5;x++) {
                    longList.addAll(aantallen);
                }
                delen.add(new Deel(longPuzzel, longList));
            }
            AtomicLong total = new AtomicLong();
            delen.forEach(deel -> total.addAndGet(deel.mogelijk()));
            return total.toString();
        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }
    }

    private static void solve(Puzzel puzzel, int startGetal, int startIndex, AtomicLong huidigeWaarde) {
        while(startGetal < nummers.size()) nummers.remove(nummers.size()-1);
        Integer lengte = puzzel.aantallen.get(startGetal);
        for (int i = startIndex; i < puzzel.code.length(); i++) {
            if(startGetal==0) {
                nummers.clear();
                String brea="";
            }
//            if(nummers.size() > startGetal) nummers.remove(nummers.size()-1);
            if (i + lengte > puzzel.code.length()) {
                //nummers.remove(nummers.size()-1);
                return;
            }
            String substr = puzzel.code.substring(i, i + lengte);
            int match = 0;
            if (i > 0 && puzzel.code.charAt(i-1) == '#') match = -999;
            for (int x = 0; x < substr.length(); x++) {
                if (substr.charAt(x) == '#' || substr.charAt(x) == '?') {
                    match++;
                } else {
                    break;
                }
            }
            if (match == substr.length() && (i + lengte == puzzel.code.length() || puzzel.code.charAt(i+lengte) != '#')) {
                if(nummers.size() <= startGetal)
                    nummers.add(i);
                if((startGetal + 1) == puzzel.aantallen.size()) {
                    String pre = puzzel.code.substring(0, nummers.get(0));
                    String post = puzzel.code.substring(nummers.get(nummers.size()-1) + lengte);
                    String betweens = "";
                    for (int n=0; n < nummers.size() - 1; n++) {
                        betweens += puzzel.code.substring(nummers.get(n) + puzzel.aantallen.get(n), nummers.get(n+1));
                    }
                    if(!pre.contains("#") && !post.contains("#") && !betweens.contains("#")) {
                        String nrCode = nummers.stream().map(nr -> nr.toString()).collect(Collectors.joining("-"));
                        if (!matches.contains(nrCode)) {
                            matches.add(nrCode);
                            huidigeWaarde.incrementAndGet();
                        }
                    }
                    nummers.remove(nummers.size()-1);
                    solve(puzzel, startGetal, i + 1, huidigeWaarde);
                    return;
                }
                solve(puzzel, startGetal + 1, i + lengte + 1, huidigeWaarde);
            } //else {
                solve(puzzel, startGetal, i + 1, huidigeWaarde);
            //}

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

    private static class Deel {
        private final String land;
        private final int landId;

        private final List<Integer> nummers;
        private final int nummerId;

        private final int groep;

        private final HashMap<Deel, Long> cache;

        public Deel(String grond, List<Integer> nummers) {
            this(grond, nummers, 0, 0, 0, new HashMap<>());
        }

        private Deel(String grond, List<Integer> nummers, int groep, int landId, int nummerId, HashMap<Deel, Long> cache) {
            this.land = grond;
            this.nummers = nummers;
            this.groep = groep;
            this.landId = landId;
            this.nummerId = nummerId;
            this.cache = cache;
        }

        public long mogelijk() {
            if (cache.containsKey(this)) {
                return cache.get(this);
            }
            long waarde = 0;
            if (landId == land.length()) {
                if (groep == 0 && nummerId == nummers.size()) {
                    waarde = 1;
                } else {
                    waarde = 0;
                }
            } else {
                if (land.charAt(landId) == '.') {
                    waarde = leeg();
                } else if (land.charAt(landId) == '#') {
                    waarde = hekje();
                } else if (land.charAt(landId) == '?') {
                    waarde = hekje() + leeg();
                }
            }
            cache.put(this, waarde);
            return waarde;
        }

        private long hekje() {
            if (nummerId == nummers.size() || groep >= nummers.get(nummerId)) {
                return 0;
            } else {
                return new Deel(land, nummers, groep + 1, landId + 1, nummerId, cache).mogelijk();
            }
        }

        private long leeg() {
            if (groep > 0) {
                if (nummers.get(nummerId) == groep) {
                    return new Deel(land, nummers, 0, landId + 1, nummerId + 1, cache).mogelijk();
                } else {
                    return 0;
                }
            } else {
                return new Deel(land, nummers, 0, landId + 1, nummerId, cache).mogelijk();
            }
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (!(o instanceof Deel)) return false;
            Deel deel = (Deel) o;
            if (landId != deel.landId) return false;
            if (nummerId != deel.nummerId) return false;
            return groep == deel.groep;
        }

        @Override
        public int hashCode() {
            int result = landId;
            result = 31 * result + nummerId;
            result = 31 * result + groep;
            return result;
        }
    }

}
