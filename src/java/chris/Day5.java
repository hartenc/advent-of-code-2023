package chris;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.Stream;

public class Day5 {
    public static void main(String[] args) {
//        System.out.println(puzzel1());
        System.out.println(puzzel2());
    }

    private static String puzzel1() {
        try (BufferedReader bufferedReader = new BufferedReader(new FileReader("puzzel5.txt"))) {
            List<Long> seeds = new ArrayList<>();
            String regel;
            List<Range> soil = new ArrayList<>();
            List<Range> fert = new ArrayList<>();
            List<Range> water = new ArrayList<>();
            List<Range> light = new ArrayList<>();
            List<Range> temp = new ArrayList<>();
            List<Range> hum = new ArrayList<>();
            List<Range> ground = new ArrayList<>();
            int fase = 0;
            while ((regel = bufferedReader.readLine()) != null) {
                if (seeds.isEmpty()) {
                    String seedstr = regel.substring(7);
                    Stream.of(seedstr.split(" ")).map(String::trim).filter(seed -> !seed.isEmpty()).forEach(seed -> seeds.add(Long.parseLong(seed)));
                    continue;
                }
                if (regel.isEmpty()) continue;
                if (regel.startsWith("seed")) {
                    fase = 1;
                    continue;
                }
                if (regel.startsWith("soil")) {
                    fase = 2;
                    continue;
                }
                if (regel.startsWith("fertil")) {
                    fase = 3;
                    continue;
                }
                if (regel.startsWith("water")) {
                    fase = 4;
                    continue;
                }
                if (regel.startsWith("light")) {
                    fase = 5;
                    continue;
                }
                if (regel.startsWith("temper")) {
                    fase = 6;
                    continue;
                }
                if (regel.startsWith("humid")) {
                    fase = 7;
                    continue;
                }

                String[] waardes = regel.split(" ");
                Long target = Long.parseLong(waardes[0]);
                Long source = Long.parseLong(waardes[1]);
                Long r = Long.parseLong(waardes[2]);
                Range range = new Range(target, source, r);
                switch (fase) {
                    case 1 -> soil.add(range);
                    case 2 -> fert.add(range);
                    case 3 -> water.add(range);
                    case 4 -> light.add(range);
                    case 5 -> temp.add(range);
                    case 6 -> hum.add(range);
                    case 7 -> ground.add(range);
                }
            }
            ground.sort((a, b) -> {
                long dif = a.target - b.target;
                if (dif > 0) return 1;
                if (dif < 0) return -1;
                return 0;
            });
            AtomicLong lowestPos = new AtomicLong(Long.MAX_VALUE);
            seeds.forEach(seed -> {
                AtomicLong pos = new AtomicLong(seed);
                AtomicBoolean found = new AtomicBoolean();

                soil.forEach(sl -> {
                    if (found.get()) return;
                    if (pos.get() >= sl.source && pos.get() < (sl.source + sl.range)) {
                        pos.set(sl.target + (pos.get() - sl.source));
                        found.set(true);
                    }
                });

                found.set(false);
                fert.forEach(sl -> {
                    if (found.get()) return;
                    if (pos.get() >= sl.source && pos.get() < (sl.source + sl.range)) {
                        pos.set(sl.target + (pos.get() - sl.source));
                        found.set(true);
                    }
                });

                found.set(false);
                water.forEach(sl -> {
                    if (found.get()) return;
                    if (pos.get() >= sl.source && pos.get() < (sl.source + sl.range)) {
                        pos.set(sl.target + (pos.get() - sl.source));
                        found.set(true);
                    }
                });

                found.set(false);
                light.forEach(sl -> {
                    if (found.get()) return;
                    if (pos.get() >= sl.source && pos.get() < (sl.source + sl.range)) {
                        pos.set(sl.target + (pos.get() - sl.source));
                        found.set(true);
                    }
                });

                found.set(false);
                temp.forEach(sl -> {
                    if (found.get()) return;
                    if (pos.get() >= sl.source && pos.get() < (sl.source + sl.range)) {
                        pos.set(sl.target + (pos.get() - sl.source));
                        found.set(true);
                    }
                });

                found.set(false);
                hum.forEach(sl -> {
                    if (found.get()) return;
                    if (pos.get() >= sl.source && pos.get() < (sl.source + sl.range)) {
                        pos.set(sl.target + (pos.get() - sl.source));
                        found.set(true);
                    }
                });

                found.set(false);
                ground.forEach(sl -> {
                    if (found.get()) return;
                    if (pos.get() >= sl.source && pos.get() < (sl.source + sl.range)) {
                        pos.set(sl.target + (pos.get() - sl.source));
                        found.set(true);
                    }
                });
                if (pos.get() < lowestPos.get()) lowestPos.set(pos.get());
            });

            return lowestPos.toString();
        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }
    }

    private static String puzzel2() {
        try (BufferedReader bufferedReader = new BufferedReader(new FileReader("puzzel5.txt"))) {
            List<Long> seeds = new ArrayList<>();
            String regel;
            List<Range> soil = new ArrayList<>();
            List<Range> fert = new ArrayList<>();
            List<Range> water = new ArrayList<>();
            List<Range> light = new ArrayList<>();
            List<Range> temp = new ArrayList<>();
            List<Range> hum = new ArrayList<>();
            List<Range> ground = new ArrayList<>();
            int fase = 0;
            while ((regel = bufferedReader.readLine()) != null) {
                if (seeds.isEmpty()) {
                    String seedstr = regel.substring(7);
                    Stream.of(seedstr.split(" ")).map(String::trim).filter(seed -> !seed.isEmpty()).forEach(seed -> seeds.add(Long.parseLong(seed)));
                    continue;
                }
                if (regel.isEmpty()) continue;
                if (regel.startsWith("seed")) {
                    fase = 1;
                    continue;
                }
                if (regel.startsWith("soil")) {
                    fase = 2;
                    continue;
                }
                if (regel.startsWith("fertil")) {
                    fase = 3;
                    continue;
                }
                if (regel.startsWith("water")) {
                    fase = 4;
                    continue;
                }
                if (regel.startsWith("light")) {
                    fase = 5;
                    continue;
                }
                if (regel.startsWith("temper")) {
                    fase = 6;
                    continue;
                }
                if (regel.startsWith("humid")) {
                    fase = 7;
                    continue;
                }

                String[] waardes = regel.split(" ");
                Long target = Long.parseLong(waardes[0]);
                Long source = Long.parseLong(waardes[1]);
                Long r = Long.parseLong(waardes[2]);
                Range range = new Range(target, source, r);
                switch (fase) {
                    case 1 -> soil.add(range);
                    case 2 -> fert.add(range);
                    case 3 -> water.add(range);
                    case 4 -> light.add(range);
                    case 5 -> temp.add(range);
                    case 6 -> hum.add(range);
                    case 7 -> ground.add(range);
                }
            }
            ground.sort((a, b) -> {
                long dif = a.target - b.target;
                if (dif > 0) return 1;
                if (dif < 0) return -1;
                return 0;
            });
            AtomicLong lowestPos = new AtomicLong(Long.MAX_VALUE);

            for (int x = 0; x < seeds.size(); x += 2) {
                System.out.println(String.format("Serie %d, %d zaad, laagste nu %d", x, seeds.get(x+1), lowestPos.get()));
                for (int y = 0; y < seeds.get(x + 1); y++) {
                    AtomicLong pos = new AtomicLong(seeds.get(x) + y);

                    AtomicBoolean found = new AtomicBoolean();

                    soil.forEach(sl -> {
                        if (found.get()) return;
                        if (pos.get() >= sl.source && pos.get() < (sl.source + sl.range)) {
                            pos.set(sl.target + (pos.get() - sl.source));
                            found.set(true);
                        }
                    });

                    found.set(false);
                    fert.forEach(sl -> {
                        if (found.get()) return;
                        if (pos.get() >= sl.source && pos.get() < (sl.source + sl.range)) {
                            pos.set(sl.target + (pos.get() - sl.source));
                            found.set(true);
                        }
                    });

                    found.set(false);
                    water.forEach(sl -> {
                        if (found.get()) return;
                        if (pos.get() >= sl.source && pos.get() < (sl.source + sl.range)) {
                            pos.set(sl.target + (pos.get() - sl.source));
                            found.set(true);
                        }
                    });

                    found.set(false);
                    light.forEach(sl -> {
                        if (found.get()) return;
                        if (pos.get() >= sl.source && pos.get() < (sl.source + sl.range)) {
                            pos.set(sl.target + (pos.get() - sl.source));
                            found.set(true);
                        }
                    });

                    found.set(false);
                    temp.forEach(sl -> {
                        if (found.get()) return;
                        if (pos.get() >= sl.source && pos.get() < (sl.source + sl.range)) {
                            pos.set(sl.target + (pos.get() - sl.source));
                            found.set(true);
                        }
                    });

                    found.set(false);
                    hum.forEach(sl -> {
                        if (found.get()) return;
                        if (pos.get() >= sl.source && pos.get() < (sl.source + sl.range)) {
                            pos.set(sl.target + (pos.get() - sl.source));
                            found.set(true);
                        }
                    });

                    found.set(false);
                    ground.forEach(sl -> {
                        if (found.get()) return;
                        if (pos.get() >= sl.source && pos.get() < (sl.source + sl.range)) {
                            pos.set(sl.target + (pos.get() - sl.source));
                            found.set(true);
                        }
                    });
                    if (pos.get() < lowestPos.get()) lowestPos.set(pos.get());
                }
            }

            return lowestPos.toString();
        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }
    }

    private static class Range {
        Long target;
        Long source;
        Long range;

        public Range(Long target, Long source, Long range) {
            this.target = target;
            this.source = source;
            this.range = range;
        }
    }
}
