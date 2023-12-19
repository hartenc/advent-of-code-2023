package chris;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;


public class Day17 {
    private static List<List<Pos>> finish = new ArrayList<>();
    private static List<List<Integer>> field = new ArrayList<>();
    private static List<List<Pos>> vals = new ArrayList<>();
    private static List<List<Pos>> routes = new ArrayList<>();
    private static Integer kortsteRoute = Integer.MAX_VALUE;

    public static void main(String[] args) {
        System.out.println(puzzel1());
//        System.out.println(puzzel2());
    }

    // 0 = boven 1 = rechts 2=onder 3 = links
    private static String puzzel1() {
        int it = 0;
        try (BufferedReader bufferedReader = new BufferedReader(new FileReader("puzzel17a.txt"))) {

            String regel;
            while ((regel = bufferedReader.readLine()) != null) {

                List<Integer> fill = new ArrayList<>();
                for (int x = 0; x < regel.length(); x++) {
                    fill.add(Integer.parseInt(regel.substring(x, x + 1)));
                }
                field.add(fill);
//                vals.add(fill.stream().map(f -> new Pos(0,0,999999999,0,0,0,0)).collect(Collectors.toList()));
            }
//            vals.get(0).set(0, new Pos(0,0, field.get(0).get(0),0,0,0,0));

            Pos start = new Pos(0, 0, new ArrayList<>(), 1, field.get(0).get(0));
            start.trail.add(new Pos(0, 0, List.of(new Pos(0, 0, new ArrayList<>(), field.get(0).get(0))), field.get(0).get(0)));
            ArrayList<Pos> list = new ArrayList<>();
            list.add(start);
            routes.add(list);
            while (finish.size() < 1000 && routes.size() != 0) {
//                if (finish.size() % 1000 == 0) System.out.println("Finish " + finish.size());
                it++;

//                routes.removeIf(route -> route.size() > 25);
                routes.sort(Comparator.comparingInt(posA -> posA.get(posA.size() - 1).score)); // + (((field.size() - posA.get(posA.size()-1).x)+ (field.size() - posA.get(posA.size()-1).y))*5))
//                for(int i=0; i<Math.min(10, routes.size()); i++) {
//                    System.out.println(routes.get(i).stream().map(pos -> pos.x + " " + pos.y).collect(Collectors.joining("|")) + " = " + routes.get(i).get(routes.get(i).size()-1).score);
//                }
//                System.out.println();
//                if(routes.size() > 1000) routes = routes.subList(0, 1000);

                List<Pos> nieuweLijst = new ArrayList<>();
                for(int x=0;x<Math.min(100, routes.size()); x++) {
                    hardCopy(nieuweLijst, routes.get(x));

                    calc(nieuweLijst.get(nieuweLijst.size() - 1).x, nieuweLijst.get(nieuweLijst.size() - 1).y, nieuweLijst);
                }
                if (it % 10000 == 0) {
                    System.out.println("Still iterating at " + it + " with " + routes.size() + " routes");
                }
//                vals.forEach(col -> {
//                    col.forEach(col2 -> System.out.print(col2.toString() + ";"));
//                    System.out.println("");
//                });
//                System.out.println("");
            }
//            finish.forEach(fn ->
//            {
//                fn.get(0).score = fn.stream().map(ps -> field.get(ps.y).get(ps.x)).reduce(0, Integer::sum);
//
//            });
            finish.sort(Comparator.comparingInt(a -> a.get(a.size() - 1).score));
            vals.forEach(col -> {
                col.forEach(col2 -> System.out.print(col2.toString() + ";"));
                System.out.println("");
            });
            vals.forEach(col -> {
                col.forEach(col2 -> System.out.print(col2.score + ";"));
                System.out.println("");
            });
            System.out.println("");
            return "" + finish.get(0).get(finish.get(0).size() - 1).score;
        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }
    }

    private static void calc(int x, int y, List<Pos> pos) {
        if (x == field.get(0).size() - 1 && y == field.size() - 1) {
            List<Pos> res = new ArrayList<>();
            hardCopy(res, pos);
            finish.add(res);
            Integer lengte = res.stream().map(ps -> field.get(ps.y).get(ps.x)).reduce(0, Integer::sum);
            if (lengte < kortsteRoute) kortsteRoute = lengte;
            if (lengte < 105) {
                String bk = "";
            }
            System.out.println("EINDE: " + lengte);
            routes.remove(0);
            return;
        }
        if (x == 2 && y == 1) {
            String bk = "";
        }
        if (canGoRight(x, y, pos)) {
            List<Pos> newPos = new ArrayList<>();
            hardCopy(newPos, pos);
            newPos.add(new Pos(x + 1, y, newPos, 1, 0));
            newPos.get(newPos.size() - 1).score = newPos.stream().map(ps -> field.get(ps.y).get(ps.x)).reduce(0, Integer::sum);
//            if(vals.get(y).get(x+1) > newPos.get(newPos.size()-1).score) vals.get(y).set(x + 1, newPos.get(newPos.size()-1).score);
//            Optional<List<Pos>> routeMatch = routes.stream().filter(rt -> rt.stream().anyMatch(ps -> ps.containsXY(x +1 , y ))).findFirst();
//            if(routeMatch.isPresent()) {
//                if (routeMatch.get().stream().anyMatch(ps -> ps.x == x+1 && ps.y == y && ps.score > newPos.get(newPos.size()-1).score)) {
//                    routes.remove(routeMatch.get());
//                }
//            }
//            routeMatch.ifPresent(posList -> routes.remove(posList));
            routes.add(newPos);
        }

        if (canGoDown(x, y, pos)) {
            List<Pos> newPos = new ArrayList<>();
            hardCopy(newPos, pos);
            newPos.add(new Pos(x, y + 1, newPos, 2, 0));
            newPos.get(newPos.size() - 1).score = newPos.stream().map(ps -> field.get(ps.y).get(ps.x)).reduce(0, Integer::sum);
//            if(vals.get(y+1).get(x) > newPos.get(newPos.size()-1).score) vals.get(y + 1).set(x, newPos.get(newPos.size()-1).score);
//            Optional<List<Pos>> routeMatch = routes.stream().filter(rt -> rt.stream().anyMatch(ps -> ps.containsXY(x , y+1 ))).findFirst();
//            if(routeMatch.isPresent()) {
//                if (routeMatch.get().stream().anyMatch(ps -> ps.x == x && ps.y == y+1 && ps.score > newPos.get(newPos.size()-1).score)) {
//                    routes.remove(routeMatch.get());
//                }
//            }
//            routeMatch.ifPresent(posList -> routes.remove(posList));
            routes.add(newPos);
        }
        if (canGoUp(x, y, pos)) {
            List<Pos> newPos = new ArrayList<>();
            hardCopy(newPos, pos);
            newPos.add(new Pos(x, y - 1, newPos, 0, 0));
            newPos.get(newPos.size() - 1).score = newPos.stream().map(ps -> field.get(ps.y).get(ps.x)).reduce(0, Integer::sum);
//            if(vals.get(y-1).get(x) > newPos.get(newPos.size()-1).score) vals.get(y-1).set(x, newPos.get(newPos.size()-1).score);
//            Optional<List<Pos>> routeMatch = routes.stream().filter(rt -> rt.stream().anyMatch(ps -> ps.containsXY(x , y-1 ))).findFirst();
//            if(routeMatch.isPresent()) {
//                if (routeMatch.get().stream().anyMatch(ps -> ps.x == x && ps.y == y-1 && ps.score > newPos.get(newPos.size()-1).score)) {
//                    routes.remove(routeMatch.get());
//                }
//            }
//            routeMatch.ifPresent(posList -> routes.remove(posList));
            routes.add(newPos);
        }
        if (canGoLeft(x, y, pos)) {
            List<Pos> newPos = new ArrayList<>();
            hardCopy(newPos, pos);
            newPos.add(new Pos(x - 1, y, newPos, 3, 0));
            newPos.get(newPos.size() - 1).score = newPos.stream().map(ps -> field.get(ps.y).get(ps.x)).reduce(0, Integer::sum);
//            if(vals.get(y).get(x-1) > newPos.get(newPos.size()-1).score) vals.get(y).set(x-1, newPos.get(newPos.size()-1).score);
//            Optional<List<Pos>> routeMatch = routes.stream().filter(rt -> rt.stream().anyMatch(ps -> ps.containsXY(x -1 , y ))).findFirst();
//            if(routeMatch.isPresent()) {
//                if (routeMatch.get().stream().anyMatch(ps -> ps.x == x-1 && ps.y == y && ps.score > newPos.get(newPos.size()-1).score)) {
//                    routes.remove(routeMatch.get());
//                }
//            }
//            routeMatch.ifPresent(posList -> routes.remove(posList));
            routes.add(newPos);
        }

        routes.remove(0);
    }

    private static void hardCopy(List<Pos> dest, List<Pos> src) {
        src.forEach(rw -> {
            dest.add(new Pos(rw.x, rw.y, hardCopyTrail(rw.trail), rw.richting, rw.score));
        });
    }

    private static List<Pos> hardCopyTrail(List<Pos> pos) {
        ArrayList<Pos> a = new ArrayList<>();
        pos.forEach(it -> {
            a.add(new Pos(it.x, it.y, null, it.richting));
        });
        return a;
    }


    private static boolean canGoLeft(int x, int y, List<Pos> pos) {
        if (x == 0) return false;
        if (pos.stream().anyMatch(poss -> poss.containsXY(x - 1, y))) return false;
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
        if (pos.stream().anyMatch(poss -> poss.containsXY(x, y - 1))) return false;
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
        if (pos.stream().anyMatch(poss -> poss.containsXY(x + 1, y))) return false;
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
        if (pos.stream().anyMatch(poss -> poss.containsXY(x, y + 1))) return false;
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
        public List<Pos> trail;
        public int richting;
        public int up;
        public int down;
        public int left;
        public int right;
        public Integer score;

        public Pos(int x, int y, Integer score, int up, int left, int down, int right) {
            this.x = x;
            this.y = y;
            this.score = score;
            this.up = up;
            this.left = left;
            this.down = down;
            this.right = right;
        }

        public Pos(int x, int y, List<Pos> trail, int richting) {
            this.x = x;
            this.y = y;
            this.trail = trail;
            this.richting = richting;
        }

        public Pos(int x, int y, List<Pos> trail, int richting, Integer score) {
            this.x = x;
            this.y = y;
            this.trail = trail;
            this.richting = richting;
            this.score = score;
        }


        public boolean containsXY(int x, int y) {
            return trail.stream().anyMatch(pos -> pos.x == x && pos.y == y);
        }
    }


}
