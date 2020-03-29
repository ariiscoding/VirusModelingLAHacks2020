package Hacks.LA;

import java.util.ArrayList;
import java.util.List;

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


    class Stats {
        private int totalPopulation;

        Stats () {
            totalPopulation = calcPopulation();
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
