package Hacks.LA;

import java.sql.SQLOutput;
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
        //This is part of a flow-through function to iterate through and operate on each person in our simulation.
        //It has to be a flow-through function because of our decision to encapsulate most classes for safety.
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
        //Handles the initial infection of citizens
        int[] count = new int[] {0};
        while (count[0] < Constants.INITIAL_INFECTED) {
            for (Cluster cluster : city) {
                cluster.initialInfection(count);
            }
        }
    }


    class Stats {
        //a class to collect, store, and report statistics about our simulation

        private int totalPopulation;
        List<IterationStats> iterationStats; //remember the stats of each round
        Queue<List<Coordinate>> coordinates;

        Stats () {
            totalPopulation = calcPopulation();
            iterationStats = new ArrayList<>();
            coordinates = new ArrayDeque<>();
        }

        public void finalReport() {
            IterationStats iStats = iterationStats.get(iterationStats.size()-1);
            double percentHealthy = (double)(iStats.getHealthy()) / (double)(totalPopulation);
            double percentSurvived = (double)(iStats.getImmune())/(double)(totalPopulation);
            double percentDeceased = (double)(iStats.getDeceased())/(double)(totalPopulation);
            double percentInfectedTotal = percentDeceased + percentSurvived;

            System.out.println("\n \n");
            System.out.println("------------------------------------");
            System.out.println("Total time elapsed (" + Constants.TIME_UNIT + "): " + Time.getTime());
            System.out.println("Percent of population not infected: " + percentHealthy);
            System.out.println("Percent infected (total): " + percentInfectedTotal);
            System.out.println("Percent survived after infection: " + percentSurvived);
            System.out.println("Percent deceased: " + percentDeceased);
            System.out.println("------------------------------------");
            System.out.println("\n \n");
        }

        public void recordCoordinates() {
            //this is to collect data to support animation.
            //it records x, y, and state of each person *in unchanged order* every iteration

            List<Coordinate> list = new ArrayList<>();
            for (int i = 0; i < city.size(); i++) {
                //coordinates.add(city.get(i).recordCoordinates(i, Constants.CLUSTER_SCALING));
                city.get(i).recordCoordinates(i, Constants.CLUSTER_SCALING, list);
            }
            coordinates.add(list);
        }

        public void survey() {
            //this is to collect summary statistics for each iteration

            IterationStats iStats = new IterationStats();

            for (Cluster cluster : city) {
                cluster.survey(iStats);
            }

            printStats(iStats);
            iterationStats.add(iStats);
        }

        public void initialCityInfo() {

            System.out.println("");
            System.out.println("-----------------------------------------------------------------------------");
            System.out.println("Total population: " + totalPopulation);
            System.out.println("There are " + city.size() + " clusters in this simulation.");
            System.out.println("Each cluster is " + Constants.CLUSTER_X_LENGTH + " by " + Constants.CLUSTER_Y_LENGTH + ".");
            System.out.println("The hospital has the capacity of " + hospital.getCapacity() + " in this simulation.");
            System.out.println("The wait time for patients who show symptoms to be admitted to hospital is " + Constants.WAIT_BEFORE_HOSPITALIZATION + " " + Constants.TIME_UNIT + ".");
            System.out.println("-----------------------------------------------------------------------------");
            System.out.println("");
        }

        public void printStats(IterationStats iStats) {
            //this is the code for initial reporting. I added another function for initial reporting so this is no longer needed.

            System.out.println("Current time (" + Constants.TIME_UNIT +"): " + iStats.getTime());
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
            //This is an older version of "survey". It was integrated in "loop" function
            //but I decided to deprecate it because the logic was too complex and
            //might not handle "interclusterMover" correcly

            int time = Time.getTime();
            if (time >= iterationStats.size()) {
                iterationStats.add(new IterationStats());
            }
            iterationStats.get(iterationStats.size()-1).count(person);
        }

        private int calcPopulation() {
            //Calculate initial population and store it in Stats object that should
            //be initiated with City class
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

