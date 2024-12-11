package chris2024;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class Day3 {
    public static void main(String[] args) {
        System.out.println(puzzel1());
//        System.out.println(puzzel2());
    }

    private static String puzzel1() {
        Long totaal = 0L;
        try (BufferedReader bufferedReader = new BufferedReader(new FileReader("puzzel3.txt"))) {
            String regel;
            boolean match=true;
            while ((regel = bufferedReader.readLine()) != null) {
                List<String> allMatches = new ArrayList<String>();
                Matcher m = Pattern.compile("(mul\\(\\d{1,3},\\d{1,3}\\)|do\\(\\)|don't\\(\\))")
                        .matcher(regel);

                while (m.find()) {
                    if(m.group().startsWith("do(")) match = true;
                    if(m.group().startsWith("don't(")) match = false;
                    if(m.group().startsWith("mul") && match) {
                        String nums = m.group().substring(4, m.group().length() - 1);
                        totaal += Long.parseLong(nums.split(",")[0]) * Long.parseLong(nums.split(",")[1]);
                    }
                }
            }

            return totaal.toString();
        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }
    }

//    private static String puzzel2() {
//        Long totaal = 0L;
//        List<List<Integer>> correct = new ArrayList<>();
//        try (BufferedReader bufferedReader = new BufferedReader(new FileReader("puzzel2.txt"))) {
//            String regel;
//            while ((regel = bufferedReader.readLine()) != null) {
//                List<Integer> parts = Arrays.stream(regel.split(" ")).map(Integer::parseInt).collect(Collectors.toList());
//                if (checkCorrect(parts)) {
//                    totaal++;
//                    correct.add(parts);
//                } else {
//                    for(int x=0; x< parts.size(); x++) {
//                        List<Integer> newParts = new ArrayList<>(parts);
//                        newParts.remove(x);
//                        if (checkCorrect(newParts)) {
//                            totaal++;
//                            correct.add(newParts);
//                            break;
//                        }
//                    }
//                }
//            }
//            Long totaal2 = 0L;
//            for(int x=0;x< correct.size();x++) {
//                if(checkCorrect(correct.get(x))) totaal2++;
//            }
//            System.out.println(totaal2);
//
//            return totaal.toString();
//        } catch (Exception e) {
//            e.printStackTrace();
//            return "";
//        }
//    }
    }
