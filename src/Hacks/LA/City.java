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

    public void initialInfection() {
        int[] count = new int[] {0};
        while (count[0] < Constants.INITIAL_INFECTED) {
            for (Cluster cluster : city) {
                cluster.initialInfection(count);
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

        public void survey() {
            IterationStats iStats = new IterationStats();

            for (Cluster cluster : city) {
                cluster.survey(iStats);
            }

            iterationStats.add(iStats);
            printStats(iStats);
        }

        public void printStats(IterationStats iStats) {
            if (iterationStats.isEmpty()) {
                System.out.println("Population: " + totalPopulation);
            }

            System.out.println("Current time: " + iStats.getTime());
            System.out.println("Healthy population: " + iStats.getHealthy());
            System.out.println("Incubated: " + iStats.getIncubated());
            System.out.println("Infected: " + iStats.getInfected());
            System.out.println("Hospitalized: " + iStats.getHospitalized());
            System.out.println("Immune: " + iStats.getImmune());
            System.out.printf("Deceased: " + iStats.getDeceased());
            System.out.println();
            System.out.println();
        }

        @Deprecated
        public void count (Person person) {
            int time = Time.getTime();
            if (time >= iterationStats.size()) {
                iterationStats.add(new IterationStats());
            }
            iterationStats.get(iterationStats.size()-1).count(person);
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

