package Hacks.LA;

import java.util.concurrent.Callable;

public class Main {

    public static void main(String[] args) {
        //demo code
        Simulation simulation = new Simulation();
        simulation.setPriority(2); //set a higher priority for this thread
        simulation.run();
    }
}
