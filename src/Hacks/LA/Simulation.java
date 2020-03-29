package Hacks.LA;

public class Simulation extends Thread{
    @Override
    public void run() {
        //initializing city, hospital, and stats
        City city = new City();
        Hospital hospital = city.hospital;
        City.Stats stats = city.stats;

        System.out.println(stats.getTotalPopulation());
    }
}
