package chris;

import java.io.BufferedReader;
import java.io.FileReader;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Formatter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class Day8 {
    public static void main(String[] args) {
        System.out.println(puzzel1());
        System.out.println(puzzel2());
    }

    private static String puzzel1() {
        String route = null;
        Map<String, Node> routes = new HashMap<>();
        try (BufferedReader bufferedReader = new BufferedReader(new FileReader("puzzel8.txt"))) {
            String regel;
            while ((regel = bufferedReader.readLine()) != null) {
                if (route == null) {
                    route = regel;
                    continue;
                }
                if (regel.isEmpty()) continue;
                String naam = regel.split(" =")[0];
                String[] paden = regel.split("\\(");
                String pad1 = paden[1].split(",")[0];
                String pad2 = paden[1].split(", ")[1].substring(0,3);
                routes.put(naam, new Node(pad1, pad2));
            }
            Node currentNode = routes.get("AAA");
            Integer lengte = 0;
            while(true) {
                lengte++;
                Character pad = route.charAt((lengte-1) % route.length());
                if ((currentNode.links.equals("ZZZ") && pad.equals('L')) || (currentNode.rechts.equals("ZZZ") && pad.equals('R'))) break;
                switch(pad) {
                    case 'L' -> currentNode = routes.get(currentNode.links);
                    case 'R' -> currentNode = routes.get(currentNode.rechts);
                }
            }
            return lengte.toString();
        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }
    }

    private static String puzzel2() {
        String route = null;
        Map<String, Node> routes = new HashMap<>();
        try (BufferedReader bufferedReader = new BufferedReader(new FileReader("puzzel8.txt"))) {
            String regel;
            List<Node> paths = new ArrayList<>();
            while ((regel = bufferedReader.readLine()) != null) {
                if (route == null) {
                    route = regel;
                    continue;
                }
                if (regel.isEmpty()) continue;
                String naam = regel.split(" =")[0];
                String[] paden = regel.split("\\(");
                String pad1 = paden[1].split(",")[0];
                String pad2 = paden[1].split(", ")[1].substring(0,3);
                Node node = new Node(naam, pad1, pad2);
                routes.put(naam, node);
                if(naam.charAt(2) == 'A') paths.add(node);
            }

            Long lengte = 0L;
            while(true) {
                lengte++;
                Integer pos = (int)((lengte - 1) % (long)route.length());
                Character pad = route.charAt(pos);
                for(int x=0; x<paths.size();x++) {
                    switch (pad) {
                        case 'L' -> paths.set(x, routes.get(paths.get(x).links));
                        case 'R' -> paths.set(x, routes.get(paths.get(x).rechts));
                    }
                }
                if(paths.stream().filter(path -> path.naam.charAt(2) == 'Z').count() == paths.size()) break;
            }
            return lengte.toString();
        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }
    }

    private static class Node {
        public Node(String naam, String links, String rechts) {
            this.naam = naam;
            this.links = links;
            this.rechts = rechts;
        }

        public String naam;
        public String links;
        public String rechts;

        public Node(String links, String rechts) {
            this.links = links;
            this.rechts = rechts;
        }
    }

}
