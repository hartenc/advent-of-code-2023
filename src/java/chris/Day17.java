package chris;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;


public class Day17 {
    private static List<List<Integer>> field = new ArrayList<>();
    private static List<List<Integer>> lengtes = new ArrayList<>();
    private static List<Route> routes = new ArrayList<>();

    public static void main(String[] args) {
        System.out.println(puzzel1());
//        System.out.println(puzzel2());
    }

    // 0 = boven 1 = rechts 2=onder 3 = links
    private static String puzzel1() {
        try (BufferedReader bufferedReader = new BufferedReader(new FileReader("puzzel17.txt"))) {

            String regel;
            while ((regel = bufferedReader.readLine()) != null) {

                List<Integer> fill = new ArrayList<>();
                for (int x = 0; x < regel.length(); x++) {
                    fill.add(Integer.parseInt(regel.substring(x, x + 1)));
                }
                field.add(fill);
            }
            Route r1 = new Route();
            r1.route.add(new Pos(1,0, 3));
            r1.score += field.get(0).get(1);
            Route r2 = new Route();
            r2.route.add(new Pos(0,1, 2));
            r2.score += field.get(1).get(0);

            routes.add(r1);
            routes.add(r2);
            long it = 0;
            while(true) {
                it++;
                routes.sort((a, b) -> {
                    if(a.score != b.score) {
                        return a.score - b.score;
                    } else {
                        if (a.route.size() != b.route.size()) {
                            return b.route.size() - a.route.size();
                        } else {
                            return b.route.get(b.route.size()-1).x - a.route.get(a.route.size()-1).x;
                        }
                    }
                });
                calc(routes.get(0));
//                if(routes.size() > 10000)
//                    routes = routes.subList(0,10000);
                if(it % 10000 == 0) {
                    String br = "";
                }
                if(routes.get(0).route.get(routes.get(0).route.size()-1).x == field.size()-1 && routes.get(0).route.get(routes.get(0).route.size()-1).y == field.size()-1) {
                    break;
                }
            }
            routes.forEach(route -> {
                if (route.route.stream().anyMatch(pos -> pos.x==5 && pos.y==1)) {
                    String bk = ";";
                }
            });

            return "";
        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }
    }

    private static void calc(Route route) {
        int x = route.route.get(route.route.size()-1).x;
        int y = route.route.get(route.route.size()-1).y;
        List<Pos> pos = route.route;
        if (canGoRight(x, y, pos)) {
            routes.add(new Route(route, 3));
        }

        if (canGoDown(x, y, pos)) {
            routes.add(new Route(route, 2));
        }
        if (canGoUp(x, y, pos)) {
            routes.add(new Route(route, 0));
        }
        if (canGoLeft(x, y, pos)) {
            routes.add(new Route(route, 1));
        }

        routes.remove(0);
    }

    private static boolean canGoLeft(int x, int y, List<Pos> pos) {
        if (x == 0) return false;
        if (pos.stream().anyMatch(poss -> poss.x==x-1 && poss.y == y)) return false;
        if (pos.size() < 4) return true;
        List<Pos> last3 = pos.subList(pos.size() - 3, pos.size());
        if (last3.stream().allMatch(ps -> ps.y == y)) {
            if (last3.get(2).x == last3.get(1).x + 1) return false;
            if (last3.get(2).richting != last3.get(1).richting || last3.get(2).richting != last3.get(0).richting)
                return true;
            {
                if (last3.get(2).x == last3.get(1).x - 1 && last3.get(1).x == last3.get(0).x - 1) return false;
                else {
//                    if (vals.get(y).get(x - 1) > vals.get(y).get(x) + field.get(y).get(x)) return true;
//                    else return false;
                    return true;
                }
            }
        }
//        if (vals.get(y).get(x - 1) > vals.get(y).get(x) + field.get(y).get(x)) return true;
//        return false;
        return true;
    }

    private static boolean canGoUp(int x, int y, List<Pos> pos) {
        if (y == 0) return false;
        if (pos.stream().anyMatch(poss -> poss.x==x && poss.y == y-1)) return false;
        if (pos.size() < 4) return true;
        List<Pos> last3 = pos.subList(pos.size() - 3, pos.size());
        if (last3.stream().allMatch(ps -> ps.x == x)) {
            if (last3.get(2).y == last3.get(1).y + 1) return false;
            if (last3.get(2).richting != last3.get(1).richting || last3.get(2).richting != last3.get(0).richting)
                return true;
            {
                if (last3.get(2).y == last3.get(1).y - 1 && last3.get(1).y == last3.get(0).y - 1) return false;
                else {
//                    if (vals.get(y-1).get(x) > vals.get(y).get(x) + field.get(y).get(x)) return true;
//                    else return false;
                    return true;

                }
            }
        }
//        if (vals.get(y - 1).get(x) > vals.get(y).get(x) + field.get(y).get(x)) return true;
        return true;
    }

    private static boolean canGoRight(int x, int y, List<Pos> pos) {
        if (x == field.get(0).size() - 1) return false;
        if (pos.stream().anyMatch(poss -> poss.x==x+1 && poss.y == y)) return false;
        if (pos.size() < 4) return true;
        List<Pos> last3 = pos.subList(pos.size() - 3, pos.size());
        if (last3.stream().allMatch(ps -> ps.y == y)) {
            if (last3.get(2).x == last3.get(1).x - 1) return false;
            if (last3.get(2).richting != last3.get(1).richting || last3.get(2).richting != last3.get(0).richting)
                return true;
            {
                if (last3.get(2).x == last3.get(1).x + 1 && last3.get(1).x == last3.get(0).x + 1) return false;
                else {
//                    if (vals.get(y).get(x + 1) > vals.get(y).get(x) + field.get(y).get(x)) return true;
//                    else return false;
                    return true;
                }
            }
        }
//        if (vals.get(y).get(x + 1) > vals.get(y).get(x) + field.get(y).get(x)) return true;
        return true;
    }

    private static boolean canGoDown(int x, int y, List<Pos> pos) {
        if (y == field.size() - 1) return false;
        if (pos.stream().anyMatch(poss -> poss.x==x && poss.y == y+1)) return false;
        if (pos.size() < 4) return true;
        List<Pos> last3 = pos.subList(pos.size() - 3, pos.size());
        if (last3.stream().allMatch(ps -> ps.x == x)) {
            if (last3.get(2).y == last3.get(1).y - 1) return false;
            if (last3.get(2).richting != last3.get(1).richting || last3.get(2).richting != last3.get(0).richting)
                return true;
            {
                if (last3.get(2).y == last3.get(1).y + 1 && last3.get(1).y == last3.get(0).y + 1) return false;
                else {
//                    if (vals.get(y + 1).get(x) > vals.get(y).get(x) + field.get(y).get(x)) return true;
//                    else return false;
                    return true;
                }
            }
        }
//        if (vals.get(y + 1).get(x) > vals.get(y).get(x) + field.get(y).get(x)) return true;
        return true;
    }

    private static class Pos {
        public int x;
        public int y;
        public int richting;


        public Pos(int x, int y, int richting)
        {
            this.x=x;
            this.y =y;
            this.richting = richting;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            Pos pos = (Pos) o;
            return x == pos.x && y == pos.y && richting == pos.richting;
        }

        @Override
        public int hashCode() {
            return Objects.hash(x, y, richting);
        }
    }

    private static class Route {
        public int score;
        public List<Pos> route = new ArrayList<>();

        public Route() {

        }

        public Route(Route oudeRoute, int direction) {
            oudeRoute.route.forEach(r -> route.add(new Pos(r.x, r.y, r.richting)));
            Pos lastPos = route.get(route.size()-1);
            switch(direction) {
                case 0 -> {
                    route.add(new Pos(lastPos.x, lastPos.y-1, direction));
                    this.score += oudeRoute.score + + field.get(lastPos.x).get(lastPos.y-1);
                }
                case 1 -> {
                    route.add(new Pos(lastPos.x - 1, lastPos.y, direction));
                    this.score += oudeRoute.score + field.get(lastPos.x-1).get(lastPos.y);
                }
                case 2 -> {
                    route.add(new Pos(lastPos.x, lastPos.y+1, direction));
                    this.score += oudeRoute.score + field.get(lastPos.x).get(lastPos.y+1);
                }
                case 3 -> {
                    route.add(new Pos(lastPos.x + 1, lastPos.y, direction));
                    this.score = oudeRoute.score + field.get(lastPos.x+1).get(lastPos.y);
                }

            }
        }


    }

}
