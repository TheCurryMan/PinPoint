package TSP;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;

public class TSP_GA {

    public int[][] getTSP(int[][] positions)
    {

        // Initializes the Product List
        int[][] TSP = new int[positions.length][2];
        for(int r = 0; r < positions.length; r++)
            TourManager.addCity(new City(positions[r][0], positions[r][1]));

        Population pop = new Population(50, true);
        System.out.println("Initial distance: " + pop.getFittest().getDistance());
        Population mostFit = pop;
        // Evolve population for 100 generations
        for (int j = 0; j < 40; j++) {
            pop = GA.evolvePopulation(pop);
            for (int i = 0; i < 500; i++) {
                pop = GA.evolvePopulation(pop);
            }
            if (pop.getFittest().getDistance() < mostFit.getFittest().getDistance()) {
                mostFit = pop;
            }
        }

        // Print final results
        System.out.println("Finished");
        System.out.println("Final distance: " + mostFit.getFittest().getDistance());
        System.out.println("Solution:");
        System.out.println(mostFit.getFittest());



        ArrayList<City> tsp = new ArrayList<City>();
        for (int r = 0; r < mostFit.getFittest().tourSize(); r++)
        {
            City c = mostFit.getFittest().getCity(r);
            tsp.add(c);
            System.out.println("lol");

        }
        for(int i = 0; i < TSP.length; i++)
        {
            TSP[i][0] = tsp.get(i).getX();
            TSP[i][1] = tsp.get(i).getY();
        }
        return TSP;

    }


}