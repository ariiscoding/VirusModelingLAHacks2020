package Hacks.LA;

public class Simulation extends Thread{
    private boolean canEnd (City.Stats stats) {
        int time = Time.getTime();
        if (time <= 0) { //run at least once
            return false;
        }

        IterationStats iStats = stats.iterationStats.get(time-1); //get last round's stats
        return iStats.getInfected() == 0 && iStats.getIncubated() == 0 && iStats.getHospitalized() == 0;
    }

    @Override
    public void run() {
        //initializing city, hospital, and stats
        City city = new City();
        Hospital hospital = city.hospital;
        City.Stats stats = city.stats;

        city.initialInfection();

        while(!canEnd(stats)) {
            stats.printStats();
            //main logic
            //loop through everyone
            city.loop();
            Time.incrementTime();
        }
    }
}
