package chris;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicLong;

public class Day13 {
    private static AtomicBoolean smudge = new AtomicBoolean();
    public static void main(String[] args) {
        System.out.println(puzzel1());
        System.out.println(puzzel2());
    }

    private static String puzzel1() {
        try (BufferedReader bufferedReader = new BufferedReader(new FileReader("puzzel13.txt"))) {
            List<List<Character>> field = new ArrayList<>();
            String regel;
            AtomicLong totaal =new AtomicLong();
            while ((regel = bufferedReader.readLine()) != null) {
                if(!regel.isEmpty()) {
                    List<Character> fill = new ArrayList<>();
                    for (int x = 0; x < regel.length(); x++) {
                        fill.add(regel.charAt(x));
                    }
                    field.add(fill);
                } else {
                    // vind perfecte horizontale spiegels
                    int grootstePerfect = 0;
                    for(int x=0; x< field.size() -1;x++) {
                        boolean hMatch = getHMatch(field, x, x + 1);
                        if (hMatch) {
                            boolean perfect = true;
                            for (int i= x;i> 0; i--) {
                                if (x + (x-i) + 2 == field.size()) break;
                                if (!(getHMatch(field, i - 1, x + (x-i) + 2))) {
                                    perfect = false;
                                    break;
                                }
                            }
                            if(perfect) {
                                totaal.addAndGet(100 * (x + 1));
                                break;
                            }
                        }
                    }
                    // vind perfecte verticale spiegels
                    for(int y=0; y< field.get(0).size() -1;y++) {
                        boolean vMatch = getVMatch(field, y, y + 1);
                        if (vMatch) {
                            boolean perfect = true;
                            for (int i= y;i> 0; i--) {
                                if (y + (y-i) + 2 == field.get(0).size()) break;
                                if (!(getVMatch(field, i - 1, y + (y-i) + 2))) {
                                    perfect = false;
                                    break;
                                }
                            }
                            if(perfect) {
                                totaal.addAndGet(y + 1);
                                break;
                            }
                        }
                    }
                    field = new ArrayList<>();
                }
            }

            return totaal.toString();
        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }
    }

    private static String puzzel2() {
        try (BufferedReader bufferedReader = new BufferedReader(new FileReader("puzzel13.txt"))) {
            List<List<Character>> field = new ArrayList<>();
            String regel;
            AtomicLong totaal =new AtomicLong();
            while ((regel = bufferedReader.readLine()) != null) {
                if(!regel.isEmpty()) {
                    List<Character> fill = new ArrayList<>();
                    for (int x = 0; x < regel.length(); x++) {
                        fill.add(regel.charAt(x));
                    }
                    field.add(fill);
                } else {
                    // vind perfecte horizontale spiegels
                    int grootstePerfect = 0;
                    for(int x=0; x< field.size() -1;x++) {
                        boolean hMatch = getHMatchS(field, x, x + 1);
                        if (hMatch) {
                            boolean perfect = true;
                            for (int i= x;i> 0; i--) {
                                if (x + (x-i) + 2 == field.size()) break;
                                if (!(getHMatchS(field, i - 1, x + (x-i) + 2))) {
                                    perfect = false;
                                    smudge.set(false);
                                    break;
                                }
                            }
                            if(perfect && smudge.get()) {
                                smudge.set(false);
                                totaal.addAndGet(100 * (x + 1));
                                break;
                            }
                        } else {
                            smudge.set(false);
                        }
                    }
                    // vind perfecte verticale spiegels
                    for(int y=0; y< field.get(0).size() -1;y++) {
                        boolean vMatch = getVMatchS(field, y, y + 1);
                        if (vMatch) {
                            boolean perfect = true;
                            for (int i= y;i> 0; i--) {
                                if (y + (y-i) + 2 == field.get(0).size()) break;
                                if (!(getVMatchS(field, i - 1, y + (y-i) + 2))) {
                                    perfect = false;
                                    smudge.set(false);
                                    break;
                                }
                            }
                            if(perfect && smudge.get()) {
                                smudge.set(false);
                                totaal.addAndGet(y + 1);
                                break;
                            }
                        } else {
                            smudge.set(false);
                        }
                    }
                    field = new ArrayList<>();
                }
            }

            return totaal.toString();
        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }
    }

    private static boolean getHMatch(List<List<Character>> field, int x, int x2) {
        int match = 0;
        for(int y=0;y<field.get(x).size();y++) {
            if(field.get(x).get(y).equals(field.get(x2).get(y))) match++;
        }
        return match == field.get(x).size();
    }

    private static boolean getVMatch(List<List<Character>> field, int y, int y2) {
        int match = 0;
        for(int x=0;x<field.size();x++) {
            if(field.get(x).get(y).equals(field.get(x).get(y2))) match++;
        }
        return match == field.size();
    }

    private static boolean getHMatchS(List<List<Character>> field, int x, int x2) {
        int match = 0;
        for(int y=0;y<field.get(x).size();y++) {
            if(field.get(x).get(y).equals(field.get(x2).get(y))) match++;
        }
        if (match == field.get(x).size() - 1 && !smudge.get()) {
            smudge.set(true);
            return true;
        }
        return match == field.get(x).size();
    }

    private static boolean getVMatchS(List<List<Character>> field, int y, int y2) {
        int match = 0;
        for(int x=0;x<field.size();x++) {
            if(field.get(x).get(y).equals(field.get(x).get(y2))) match++;
        }
        if (match == field.size() - 1 && !smudge.get()) {
            smudge.set(true);
            return true;
        }
        return match == field.size();
    }
}
