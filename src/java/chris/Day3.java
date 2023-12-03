package chris;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

public class Day3 {
    public static void main(String[] args) {
        System.out.println(puzzel1());
        System.out.println(puzzel2());
    }

    private static String puzzel1() {
        try (BufferedReader bufferedReader = new BufferedReader(new FileReader("puzzel3.txt"))) {
            List<Number> numbers = new ArrayList<>();
            List<Teken> tekens = new ArrayList<>();
            String regel;
            int it = 0;
            while ((regel = bufferedReader.readLine()) != null) {
                String number = "";
                Number newNumber = new Number();
                for (int i = 0; i < regel.length(); i++) {
                    if (regel.charAt(i) > 47 && regel.charAt(i) < 58) {
                        if (number.length() == 0) newNumber.startX = i;
                        number = number + regel.charAt(i);
                    } else {
                        if (number.length() > 0) {
                            newNumber.endX = i - 1;
                            newNumber.y = it;
                            newNumber.value = Integer.parseInt(number);
                            numbers.add(newNumber);
                            newNumber = new Number();
                            number = "";
                        }
                        if (regel.charAt(i) != '.') {
                            tekens.add(new Teken(i, it));
                        }
                    }
                }
                if (number.length() > 0) {
                    newNumber.endX = 139;
                    newNumber.y = it;
                    newNumber.value = Integer.parseInt(number);
                    numbers.add(newNumber);
                }
                it++;
            }
            List<Integer> nummers = new ArrayList<>();
            tekens.forEach(teken -> {

                numbers.forEach(nummer -> {
                    if ((teken.x >= nummer.startX - 1 && teken.x <= nummer.endX + 1) && Math.abs(teken.y - nummer.y) <= 1) {
                        if (!nummer.matched)
                            nummers.add(nummer.value);
                        nummer.matched = true;
                    }
                });
            });
            return nummers.stream().reduce(0, Integer::sum).toString();
        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }

    }

    private static String puzzel2() {
        try (BufferedReader bufferedReader = new BufferedReader(new FileReader("puzzel3.txt"))) {
            List<Number> numbers = new ArrayList<>();
            List<Teken> tekens = new ArrayList<>();
            String regel;
            int it = 0;
            while ((regel = bufferedReader.readLine()) != null) {
                String number = "";
                Number newNumber = new Number();
                for (int i = 0; i < regel.length(); i++) {
                    if (regel.charAt(i) > 47 && regel.charAt(i) < 58) {
                        if (number.length() == 0) newNumber.startX = i;
                        number = number + regel.charAt(i);
                    } else {
                        if (number.length() > 0) {
                            newNumber.endX = i - 1;
                            newNumber.y = it;
                            newNumber.value = Integer.parseInt(number);
                            numbers.add(newNumber);
                            newNumber = new Number();
                            number = "";
                        }
                        if (regel.charAt(i) == '*') {
                            tekens.add(new Teken(i, it));
                        }
                    }
                }
                if (number.length() > 0) {
                    newNumber.endX = 139;
                    newNumber.y = it;
                    newNumber.value = Integer.parseInt(number);
                    numbers.add(newNumber);
                }
                it++;
            }
            List<Integer> nummers = new ArrayList<>();
            tekens.forEach(teken -> {
                AtomicInteger touching = new AtomicInteger();
                AtomicInteger sum = new AtomicInteger();
                numbers.forEach(nummer -> {
                    if ((teken.x >= nummer.startX - 1 && teken.x <= nummer.endX + 1) && Math.abs(teken.y - nummer.y) <= 1) {
                        touching.incrementAndGet();
                        if(sum.get() == 0) {
                            sum.set(nummer.value);
                        } else {
                            sum.set(sum.get() * nummer.value);
                        }
                    }
                });
                if (touching.get() == 2) nummers.add(sum.get());
            });
            return nummers.stream().reduce(0, Integer::sum).toString();
        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }

    }

    private static class Number {
        public int startX;
        public int endX;
        public int y;
        public int value;
        public boolean matched;
    }

    private static class Teken {
        public int x;
        public int y;

        public Teken(int x, int y) {
            this.x = x;
            this.y = y;
        }
    }
}

