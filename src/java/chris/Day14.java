package chris;

import java.io.BufferedReader;
import java.io.FileReader;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

public class Day14 {
    public static void main(String[] args) {
        System.out.println(puzzel1());
        System.out.println(puzzel2());
    }

    private static String puzzel1() {
        try (BufferedReader bufferedReader = new BufferedReader(new FileReader("puzzel14.txt"))) {
            List<List<Character>> field = new ArrayList<>();
            String regel;
            while ((regel = bufferedReader.readLine()) != null) {

                List<Character> fill = new ArrayList<>();
                for (int x = 0; x < regel.length(); x++) {
                    fill.add(regel.charAt(x));
                }
                field.add(fill);
            }

            Long gewicht = 0L;

            for(int y=0; y < field.size();y++)
            {
                for (int x = 0; x< field.get(0).size();x++)
                {
                    if(field.get(y).get(x)=='O') {
                        int fall = 1;
                        while (y - fall >= 0 && field.get(y-fall).get(x) != '#' && field.get(y-fall).get(x) != 'O') {
                            fall++;
                        }
                        fall--;
                        if (y > 0) {
                            field.get(y).set(x, '.');
                            field.get(y - fall).set(x, 'O');
                        }
                        gewicht += (field.size() - (y-fall));
                    }
                }
            }

            return gewicht.toString();
        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }
    }

    private static String puzzel2() {
        try (BufferedReader bufferedReader = new BufferedReader(new FileReader("puzzel14.txt"))) {
            List<List<Character>> field = new ArrayList<>();
            String regel;
            while ((regel = bufferedReader.readLine()) != null) {

                List<Character> fill = new ArrayList<>();
                for (int x = 0; x < regel.length(); x++) {
                    fill.add(regel.charAt(x));
                }
                field.add(fill);
            }


            for(long it = 0; it < 1_000_000_000; it++) {
                // roll north
                for (int y = 0; y < field.size(); y++) {
                    for (int x = 0; x < field.get(0).size(); x++) {
                        if (field.get(y).get(x) == 'O') {
                            int fall = 1;
                            while (y - fall >= 0 && field.get(y - fall).get(x) != '#' && field.get(y - fall).get(x) != 'O') {
                                fall++;
                            }
                            fall--;
                            if (y > 0) {
                                field.get(y).set(x, '.');
                                field.get(y - fall).set(x, 'O');
                            }
                        }
                    }
                }
                // roll west
                for (int x = 0; x < field.get(0).size(); x++) {
                    for (int y = 0; y < field.size(); y++) {
                        if (field.get(y).get(x) == 'O') {
                            int fall = 1;
                            while (x - fall >= 0 && field.get(y).get(x - fall) != '#' && field.get(y).get(x - fall) != 'O') {
                                fall++;
                            }
                            fall--;
                            if (x > 0) {
                                field.get(y).set(x, '.');
                                field.get(y).set(x - fall, 'O');
                            }
                        }
                    }
                }

                // roll south
                for (int y = field.size() - 1; y >= 0; y--) {
                    for (int x = 0; x < field.get(0).size(); x++) {
                        if (field.get(y).get(x) == 'O') {
                            int fall = 1;
                            while (y + fall < (field.size() - 1) && field.get(y + fall).get(x) != '#' && field.get(y + fall).get(x) != 'O') {
                                fall++;
                            }
                            fall--;
                            if (y < (field.size() - 1)) {
                                field.get(y).set(x, '.');
                                field.get(y + fall).set(x, 'O');
                            }
                        }
                    }
                }

                // roll east
                for (int x =  field.get(0).size() - 1; x>=0; x--) {
                    for (int y = 0; y < field.size(); y++) {
                        if (field.get(y).get(x) == 'O') {
                            int fall = 1;
                            while (x + fall < (field.get(0).size() - 1) && field.get(y).get(x + fall) != '#' && field.get(y).get(x + fall) != 'O') {
                                fall++;
                            }
                            fall--;
                            if (x < field.size() - 1) {
                                field.get(y).set(x, '.');
                                field.get(y).set(x + fall, 'O');
                            }
                        }
                    }
                }
                Long gewicht = 0L;
                for (int y = 0; y < field.size(); y++) {
                    for (int x = 0; x < field.get(0).size(); x++) {
                        if(field.get(y).get(x) == 'O') {
                            gewicht += (field.size() - y);
                        }
                    }
                }
                // antwoord uit deze bak halen
                if (it % 10000L == 0)
                 System.out.println(it + " " + gewicht);
            }
            Long gewicht = 0L;
            for (int y = 0; y < field.size(); y++) {
                for (int x = 0; x < field.get(0).size(); x++) {
                    if(field.get(y).get(x) == 'O') {
                        gewicht += (field.size() - y);
                    }
                }
            }

            System.out.println("eindtijd " + LocalDateTime.now());

            return gewicht.toString();
        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }
    }


}
