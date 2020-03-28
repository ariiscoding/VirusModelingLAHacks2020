package Hacks.LA;

import java.util.ArrayList;
import java.util.List;

public class City {
    List<Cluster> city;

    public City () {
        city = new ArrayList<>();
        for (int i = 0; i < Constants.NUMBER_OF_CLUSTERS; i++) {
            city.add(new Cluster());
        }
    }
}
