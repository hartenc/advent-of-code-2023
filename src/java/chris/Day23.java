package chris;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class Day23 {
    private static List<List<Character>> field = new ArrayList<>();
//    private static List<Route> routes = new ArrayList<>();
    private static List<Integer> finished = new ArrayList<>();
    private static List<List<Integer>> distance = new ArrayList<>();
    private static int startX = 0;
    private static int endX = 0;

//    public static void main(String[] args) {
//        System.out.println(puzzel1());
//        System.out.println(puzzel2());
//    }
//
//    private static String puzzel1() {
//        try (BufferedReader bufferedReader = new BufferedReader(new FileReader("puzzel23.txt"))) {
//
//            String regel;
//
//            while ((regel = bufferedReader.readLine()) != null) {
//
//                List<Character> fill = new ArrayList<>();
//                for (int x = 0; x < regel.length(); x++) {
//                    fill.add(regel.charAt(x));
//                    if(field.size()==0 && (regel.charAt(x)=='.')) {
//                        startX = x;
//                    }
//                    if(field.size()==22 && regel.charAt(x)=='.') {
//                        endX = x;
//                    }
//
//                }
//                field.add(fill);
//                distance.add(IntStream.range(0,141).map(i -> 0).mapToObj(i -> new Integer(i)).collect(Collectors.toList()));
//            }
//            List<Pos> start = new ArrayList<>();
//            start.add(new Pos(startX,0));
//            routes.add(new Route(start));
//            long it=0L;
//            while(routes.size() > 0) {
//                it++;
//                if(it % 1000 == 0) {
//                    String b = "";
//                }
//                routes.sort((a,b) -> {
//                    if (a.route.size() != b.route.size()) {
//                        return b.route.size() - a.route.size();
//                    }
//                    return 0;
//                });
//
//                calc(true);
//            }
//
//            return "" + (finished.stream().sorted(Comparator.reverseOrder()).findFirst().get() - 1);
//        } catch (Exception e) {
//            e.printStackTrace();
//            return "";
//        }
//
//
//    }
//
//    private static String puzzel2() {
//        field = new ArrayList<>();
//        routes = new ArrayList<>();
//        finished = new ArrayList<>();
//
//        try (BufferedReader bufferedReader = new BufferedReader(new FileReader("puzzel23.txt"))) {
//
//            String regel;
//
//            while ((regel = bufferedReader.readLine()) != null) {
//
//                List<Character> fill = new ArrayList<>();
//                for (int x = 0; x < regel.length(); x++) {
//                    fill.add(regel.charAt(x));
//                    if(field.size()==0 && (regel.charAt(x)=='.')) {
//                        startX = x;
//                    }
//                    if(field.size()==140 && regel.charAt(x)=='.') {
//                        endX = x;
//                    }
//
//                }
//                field.add(fill);
//                distance.add(IntStream.range(0,141).map(i -> 0).mapToObj(i -> new Integer(i)).collect(Collectors.toList()));
//            }
//            List<Pos> start = new ArrayList<>();
//            start.add(new Pos(startX,0));
//            routes.add(new Route(start));
//            long it=0L;
//            while(routes.size() > 0) {
//                it++;
//                if(it % 1000 == 0) {
//                    System.out.println("Still alive, at  " + it + " iterations and routes " + routes.size());
//                }
//                routes.sort((a,b) -> {
//                    if (a.route.size() != b.route.size()) {
//                        return b.route.size() - a.route.size();
//                    }
//                    return 0;
//                });
//
//                calc(false);
//            }
//
//            return "" + (finished.stream().sorted(Comparator.reverseOrder()).findFirst().get() - 1);
//        } catch (Exception e) {
//            e.printStackTrace();
//            return "";
//        }
//
//
//    }
//
//    private static void calc(boolean slopeProblem) {
//        Route first = routes.get(0);
////        for(int y=0;y<field.size();y++) {
////            for (int x=0; x<field.get(0).size(); x++) {
////                AtomicInteger ax = new AtomicInteger(x);
////                AtomicInteger ay = new AtomicInteger(y);
////                System.out.print(field.get(y).get(x) != '.' && field.get(y).get(x) != 'O' ? field.get(y).get(x) : routes.stream().map(r -> r.route).anyMatch(pos -> pos.contains(new Pos(ax.get(),ay.get()))) ? '█' : '.');
////            }
////            System.out.println();
////        }
////        System.out.println("Route over " + routes.size());
////        System.out.println();
//        Pos lastPos = first.route.getLast();
//        if(lastPos.x == endX && lastPos.y == field.size()-1) {finished.add(first.route.size()); routes.removeFirst();return;}
//        if(canUp(lastPos, first.route,slopeProblem)) {
//            Route r = new Route(first.route);
//            r.route.add(new Pos(lastPos.x, lastPos.y-1));
//            routes.add(r);
//        }
//        if(canDown(lastPos, first.route,slopeProblem)) {
//            Route r = new Route(first.route);
//            r.route.add(new Pos(lastPos.x, lastPos.y+1));
//            routes.add(r);
//        }
//        if(canLeft(lastPos, first.route,slopeProblem)) {
//            Route r = new Route(first.route);
//            r.route.add(new Pos(lastPos.x-1, lastPos.y));
//            routes.add(r);
//        }
//        if(canRight(lastPos, first.route,slopeProblem)) {
//            Route r = new Route(first.route);
//            r.route.add(new Pos(lastPos.x+1, lastPos.y));
//            routes.add(r);
//        }
//
//        routes.removeFirst();
//    }
//
//    private static boolean canUp(Pos p, List<Pos> previous, boolean slopeProblem) {
//        if (p.y==0) return false;
//        Character cur = field.get(p.y).get(p.x);
//        if(slopeProblem && (cur == '>' || cur == '<' || cur == 'v')) return false;
////        if(distance.get(p.y - 1).get(p.x) > previous.size()) return false; else distance.get(p.y-1).set(p.x, previous.size()+1);
//        Pos newPos = new Pos(p.x, p.y-1);
//        if(field.get(newPos.y).get(newPos.x) == '#') return false;
//        if(previous.contains(newPos)) return false;
//        return true;
//    }
//
//    private static boolean canDown(Pos p, List<Pos> previous, boolean slopeProblem) {
//        if (p.y==field.size()) return false;
//        Character cur = field.get(p.y).get(p.x);
//        if(slopeProblem && (cur == '>' || cur == '<' || cur == '^')) return false;
////        if(distance.get(p.y + 1).get(p.x) > previous.size()) return false; else distance.get(p.y+1).set(p.x, previous.size()+1);
//        Pos newPos = new Pos(p.x, p.y+1);
//        if(field.get(newPos.y).get(newPos.x) == '#') return false;
//        if(previous.contains(newPos)) return false;
//        return true;
//    }
//
//    private static boolean canLeft(Pos p, List<Pos> previous, boolean slopeProblem) {
//        if (p.y==0) return false;
//        Character cur = field.get(p.y).get(p.x);
//        if(slopeProblem && (cur == '>' || cur == '^' || cur == 'v')) return false;
////        if(distance.get(p.y).get(p.x - 1) > previous.size()) return false; else distance.get(p.y-1).set(p.x, previous.size()+1);
//        Pos newPos = new Pos(p.x-1, p.y);
//        if(field.get(newPos.y).get(newPos.x) == '#') return false;
//        if(previous.contains(newPos)) return false;
//        return true;
//    }
//
//    private static boolean canRight(Pos p, List<Pos> previous, boolean slopeProblem) {
//        if (p.x==field.size()) return false;
//        Character cur = field.get(p.y).get(p.x);
//        if(slopeProblem && (cur == 'v' || cur == '<' || cur == '^')) return false;
////        if(distance.get(p.y).get(p.x + 1) > previous.size()) return false; else distance.get(p.y+1).set(p.x, previous.size()+1);
//        Pos newPos = new Pos(p.x+1, p.y);
//        if(field.get(newPos.y).get(newPos.x) == '#') return false;
//        if(previous.contains(newPos)) return false;
//        return true;
//    }
//
//    private static class Pos {
//        public int x;
//        public int y;
//
//        public Pos(int x, int y) {
//            this.x = x;
//            this.y = y;
//        }
//
//        @Override
//        public boolean equals(Object o) {
//            if (this == o) return true;
//            if (o == null || getClass() != o.getClass()) return false;
//            Pos pos = (Pos) o;
//            return x == pos.x && y == pos.y;
//        }
//
//        @Override
//        public int hashCode() {
//            return Objects.hash(x, y);
//        }
//    }
//
//    private static class Route {
//        public List<Pos> route = new ArrayList<>();
//
//        public Route(List<Pos> r) {
//            r.forEach(p -> this.route.add(new Pos(p.x, p.y)));
//        }
//
//    }

}
