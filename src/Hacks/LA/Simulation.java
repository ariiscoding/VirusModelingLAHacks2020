package Hacks.LA;

import javax.swing.*;
import java.util.List;
import java.util.Queue;

public class Simulation extends Thread{
    //this class handles the main logic of simulation

    private boolean canEnd (City.Stats stats) {
        //Determines if the simulation can end

        int time = Time.getTime();
        if (time <= Constants.MIN_ITERATION) {
            return false;
        }

        IterationStats iStats = stats.iterationStats.get(time-1); //get last round's stats
        return iStats.getInfected() == 0 && iStats.getIncubated() == 0 && iStats.getHospitalized() == 0;
    }

    private static void initPanel(Queue<List<Coordinate>> coordinates) {
        //Initiate animation
        //Not functional yet

        MyPanel p = new MyPanel(coordinates);
        Thread panelThread = new Thread(p);
        JFrame frame = new JFrame();

        int width = Constants.CLUSTER_X_LENGTH * Constants.CLUSTER_PER_ROW;
        int length = 1 + (Constants.NUMBER_OF_CLUSTERS/Constants.CLUSTER_PER_ROW);
        frame.setSize(500, 800);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
        frame.setTitle("Coronavirus Simulation");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        panelThread.start();
    }

    @Override
    public void run() {
        Constants.check();
        //initializing city, hospital, and stats
        City city = new City();
        Hospital hospital = city.hospital;
        City.Stats stats = city.stats;

        city.initialInfection();

        //Initial reporting
        Virus.getInfo();
        stats.initialCityInfo();

        while(!canEnd(stats)) {
            stats.survey();
            if (Constants.GRAPH_BACKEND) {
                stats.recordCoordinates();
            }
            //main logic
            //loop through everyone
            city.loop();
            Time.incrementTime();
        }

        stats.finalReport();

        if (Constants.GRAPH_BACKEND) {
            //animation
            initPanel(stats.coordinates);
        }
    }
}

