package Hacks.LA;

import java.util.*;

public class City {
    private List<Cluster> city;
    public Hospital hospital;
    public Stats stats;

    public City () {
        city = new ArrayList<>();
        for (int i = 0; i < Constants.NUMBER_OF_CLUSTERS; i++) {
            city.add(new Cluster());
        }
        stats = new Stats();
        hospital = new Hospital(stats.getTotalPopulation());
    }

    public void loop() {
        Queue<Person> interclusterMover = new ArrayDeque<>();
        for (Cluster cluster : city) {
            cluster.loop(this, interclusterMover);
        }

        while (!interclusterMover.isEmpty()) {
            for (Cluster cluster : city) {
                if (cluster.loopCleanQueue(this, interclusterMover)) {
                    return;
                }
            }
        }
    }


    class Stats {
        private int totalPopulation;
        List<IterationStats> iterationStats; //remember the stats of each round

        Stats () {
            totalPopulation = calcPopulation();
            iterationStats = new ArrayList<>();
        }

        public void count (Person person) {
            int time = Time.getTime();
            if (time >= iterationStats.size()) {
                iterationStats.add(new IterationStats());
            }
            iterationStats.get(time).count(person);
        }

        private int calcPopulation() {
            int n = 0;
            for (Cluster cluster : city) {
                n += cluster.getPopulation();
            }
            return n;
        }

        public int getTotalPopulation() {
            return totalPopulation;
        }
    }
}
