package TSP;

import java.io.*;
import java.util.Scanner;
import java.util.Random;


/*
 * City.java Models a city
 */

public class City
{
    int x;
    int y;
    Random rand;

    public City(int[][] products)
    {
        rand = new Random();
        this.y = rand.nextInt(products.length);
        this.x = rand.nextInt(products[0].length);

    }
    // Constructs a city at chosen x, y location
    public City( int x, int y )
    {
        this.x = x;
        this.y = y;
    }


    // Gets city's x coordinate
    public int getX()
    {
        return this.x;
    }


    // Gets city's y coordinate
    public int getY()
    {
        return this.y;
    }


    // Gets the distance to given city
    public double distanceTo( City city )
    {
        int xDistance = Math.abs( getX() - city.getX() );
        int yDistance = Math.abs( getY() - city.getY() );
        double distance = Math.sqrt( ( xDistance * xDistance ) + ( yDistance * yDistance ) );

        return distance;
    }


    @Override
    public String toString()
    {
        return getX() + ", " + getY();
    }
}