package chris;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.*;
import java.util.stream.Collectors;

public class Day20 {
    private static Map<String, Module> moduleMap = new HashMap<>();
    private static List<Execution> executionList = new ArrayList<>();
    private static Long lowPulses = 0L;
    private static Long highPulses = 0L;
    private static Long lowPulseToRx = 0L;

    public static void main(String[] args) {
        System.out.println(puzzel1());
//        System.out.println(puzzel2());
    }

    private static Long puzzel1() {
        try (BufferedReader bufferedReader = new BufferedReader(new FileReader("puzzel20.txt"))) {

            String regel;
            while ((regel = bufferedReader.readLine()) != null) {
                String[] regelParts = regel.split(" -> ");
                String[] targets = regelParts[1].split(", ");
                if (regelParts[0].equals("broadcaster")) {
                    moduleMap.put(regelParts[0], new Broadcaster(regelParts[0], Arrays.asList(targets)));
                }
                if (regelParts[0].charAt(0) == '%') {
                    moduleMap.put(regelParts[0].substring(1), new FlipFlop(regelParts[0].substring(1), Arrays.asList(targets)));
                }
                if (regelParts[0].charAt(0) == '&') {
                    moduleMap.put(regelParts[0].substring(1), new Conjunction(regelParts[0].substring(1), Arrays.asList(targets)));
                }
            }
            moduleMap.forEach((nm, md) -> {
                if (md instanceof Conjunction) ((Conjunction) md).fillInputs();
                ;
            });
            for (int x = 0; x < 1000; x++) {
                executionList.add(new Execution(moduleMap.get("broadcaster"), "button", false));

                while (!executionList.isEmpty()) {
                    if (executionList.get(0).withPulse) highPulses++;
                    else lowPulses++;
                    if (executionList.get(0).module == null) {
                        executionList.remove(0);
                    } else {
                        executionList.get(0).module.execute(executionList.get(0).from, executionList.get(0).withPulse);
                    }
                }
            }
            return (highPulses * lowPulses);
        } catch (Exception e) {
            e.printStackTrace();
            return 0L;
        }
    }




    private static void resetModelMap() {
        moduleMap = new HashMap<>();
        try (BufferedReader bufferedReader = new BufferedReader(new FileReader("puzzel20.txt"))) {

            String regel;
            while ((regel = bufferedReader.readLine()) != null) {
                String[] regelParts = regel.split(" -> ");
                String[] targets = regelParts[1].split(", ");
                if (regelParts[0].equals("broadcaster")) {
                    moduleMap.put(regelParts[0], new Broadcaster(regelParts[0], Arrays.asList(targets)));
                }
                if (regelParts[0].charAt(0) == '%') {
                    moduleMap.put(regelParts[0].substring(1), new FlipFlop(regelParts[0].substring(1), Arrays.asList(targets)));
                }
                if (regelParts[0].charAt(0) == '&') {
                    moduleMap.put(regelParts[0].substring(1), new Conjunction(regelParts[0].substring(1), Arrays.asList(targets)));
                }
            }
            moduleMap.forEach((nm, md) -> {
                if (md instanceof Conjunction) ((Conjunction) md).fillInputs();
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

private static class Execution {
    public Module module;
    public String from;
    public boolean withPulse;

    public Execution(Module m, String from, boolean b) {
        module = m;
        this.from = from;
        withPulse = b;
    }
}

private static abstract class Module {
    public String naam;
    public List<String> targets;

    public Module(String naam, List<String> targets) {
        this.naam = naam;
        this.targets = targets;
    }

    public abstract void execute(String from, boolean input);
}

private static class Broadcaster extends Module {
    public Broadcaster(String naam, List<String> targets) {
        super(naam, targets);
    }

    @Override
    public void execute(String from, boolean input) {
        executionList.remove(0);
        if (input == true) throw new IllegalStateException("Broadcaster krijgt positief signaal");
        targets.forEach(target -> {
            executionList.add(new Execution(moduleMap.get(target), naam, input));
        });
    }
}

private static class FlipFlop extends Module {
    public boolean state = false;

    public FlipFlop(String naam, List<String> targets) {
        super(naam, targets);
    }

    @Override
    public void execute(String from, boolean input) {
        executionList.remove(0);
        if (input) return;
        state = !state;
        targets.forEach(target -> {
            executionList.add(new Execution(moduleMap.get(target), naam, state));
        });
    }
}

private static class Conjunction extends Module {
    Map<String, Boolean> inputs = new HashMap<>();

    public Conjunction(String naam, List<String> targets) {
        super(naam, targets);
    }

    public void fillInputs() {
        moduleMap.forEach((nm, module) -> {
            if (module.targets.contains(naam)) inputs.put(nm, false);
        });
    }

    @Override
    public void execute(String from, boolean input) {
        executionList.remove(0);
        inputs.put(from, input);
        boolean toSend = !inputs.values().stream().allMatch(bl -> bl);
        if (naam.equals("hp") && toSend) lowPulseToRx++;
        targets.forEach(target -> {
            executionList.add(new Execution(moduleMap.get(target), naam, toSend));
        });
    }
}
}
