package com.couchbase.todolite;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.couchbase.todolite.TaskActivity;
import com.example.acetehspah.bitmapdraw.PixelGridView;
import com.example.acetehspah.bitmapdraw.drawbitmap;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import AStar.Astar;
import TSP.TSP_GA;


/**
 * Created by acetehspah on 1/14/17.
 */

public class Home extends AppCompatActivity {

    drawbitmap drawbitview;
//    int[][] path = {{33,33},{32,34},{31,35},{30,36},{29, 37},{28, 37},{27,37},{26,37},{25,37},
//            {24,37},{23,37},{22,37},{21,37},{20,37},{19,37},{18,37},{17,37}};
    int[][] points = new int[][]{{34,33}, {24,37}, {22,33}, {17, 37}, {9,34}, {1,33}, {27,20},
            {24,26}, {10,21}, {7,18}, {20,18}, {27,15}, {16,14}, {1,11}, {6,9}, {4,5}, {22,2}, {31,16}};
    int [][] tspPoints;
    int[][] allPath;
    String[] allNames = {"", "One", "Two", "Three", "Four", "Five", "Six", "Seven", "Eight", "Nine",
            "Ten", "Eleven", "Twelve", "Thirteen", "Fourteen", "Sixteen", "Seventeen", "Checkout", ""};
    ArrayList<String> names;
    private byte[][] map = new byte[][]{
            {1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1},
            {1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1},
            {1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1},
            {1,0,0,1,1,0,0,1,1,0,0,1,1,0,0,1,1,0,0,1,1,0,0,1,1,0,0,1,1,0,0,1,1,0,0,1,1,0,0,1},
            {1,0,0,1,1,0,0,1,1,0,0,1,1,0,0,1,1,0,0,1,1,0,0,1,1,0,0,1,1,0,0,1,1,0,0,1,1,0,0,1},
            {1,0,0,1,1,0,0,1,1,0,0,1,1,0,0,1,1,0,0,1,1,0,0,1,1,0,0,1,1,0,0,1,1,0,0,1,1,0,0,1},
            {1,0,0,1,1,0,0,1,1,0,0,1,1,0,0,1,1,0,0,1,1,0,0,1,1,0,0,1,1,0,0,1,1,0,0,1,1,0,0,1},
            {1,0,0,1,1,0,0,1,1,0,0,1,1,0,0,1,1,0,0,1,1,0,0,1,1,0,0,1,1,0,0,1,1,0,0,1,1,0,0,1},
            {1,0,0,1,1,0,0,1,1,0,0,1,1,0,0,1,1,0,0,1,1,0,0,1,1,0,0,1,1,0,0,1,1,0,0,1,1,0,0,1},
            {1,0,0,1,1,0,0,1,1,0,0,1,1,0,0,1,1,0,0,1,1,0,0,1,1,0,0,1,1,0,0,1,1,0,0,1,1,0,0,1},
            {1,0,0,1,1,0,0,1,1,0,0,1,1,0,0,1,1,0,0,1,1,0,0,1,1,0,0,1,1,0,0,1,1,0,0,1,1,0,0,1},
            {1,0,0,1,1,0,0,1,1,0,0,1,1,0,0,1,1,0,0,1,1,0,0,1,1,0,0,1,1,0,0,1,1,0,0,1,1,0,0,1},
            {1,0,0,1,1,0,0,1,1,0,0,1,1,0,0,1,1,0,0,1,1,0,0,1,1,0,0,1,1,0,0,1,1,0,0,1,1,0,0,1},
            {1,0,0,1,1,0,0,1,1,0,0,1,1,0,0,1,1,0,0,1,1,0,0,1,1,0,0,1,1,0,0,1,1,0,0,1,1,0,0,1},
            {1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1},
            {1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1},
            {1,0,0,1,1,0,0,1,1,0,0,1,1,0,0,1,1,0,0,1,1,0,0,1,1,0,0,1,1,0,0,1,1,0,0,1,1,0,0,1},
            {1,0,0,1,1,0,0,1,1,0,0,1,1,0,0,1,1,0,0,1,1,0,0,1,1,0,0,1,1,0,0,1,1,0,0,1,1,0,0,1},
            {1,0,0,1,1,0,0,1,1,0,0,1,1,0,0,1,1,0,0,1,1,0,0,1,1,0,0,1,1,0,0,1,1,0,0,1,1,0,0,1},
            {1,0,0,1,1,0,0,1,1,0,0,1,1,0,0,1,1,0,0,1,1,0,0,1,1,0,0,1,1,0,0,1,1,0,0,1,1,0,0,1},
            {1,0,0,1,1,0,0,1,1,0,0,1,1,0,0,1,1,0,0,1,1,0,0,1,1,0,0,1,1,0,0,1,1,0,0,1,1,0,0,1},
            {1,0,0,1,1,0,0,1,1,0,0,1,1,0,0,1,1,0,0,1,1,0,0,1,1,0,0,1,1,0,0,1,1,0,0,1,1,0,0,1},
            {1,0,0,1,1,0,0,1,1,0,0,1,1,0,0,1,1,0,0,1,1,0,0,1,1,0,0,1,1,0,0,1,1,0,0,1,1,0,0,1},
            {1,0,0,1,1,0,0,1,1,0,0,1,1,0,0,1,1,0,0,1,1,0,0,1,1,0,0,1,1,0,0,1,1,0,0,1,1,0,0,1},
            {1,0,0,1,1,0,0,1,1,0,0,1,1,0,0,1,1,0,0,1,1,0,0,1,1,0,0,1,1,0,0,1,1,0,0,1,1,0,0,1},
            {1,0,0,1,1,0,0,1,1,0,0,1,1,0,0,1,1,0,0,1,1,0,0,1,1,0,0,1,1,0,0,1,1,0,0,1,1,0,0,1},
            {1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1},
            {1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1},
            {1,0,0,1,1,1,1,1,1,0,0,1,1,1,1,1,1,1,1,1,1,0,0,1,1,1,1,1,1,0,0,1,1,1,1,1,1,0,0,1},
            {1,0,0,1,1,1,1,1,1,0,0,1,1,1,1,1,1,1,1,1,1,0,0,1,1,1,1,1,1,0,0,1,1,1,1,1,1,0,0,1},
            {1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1},
            {1,0,0,0,0,0,0,0,0,0,2,0,0,0,0,2,0,0,0,0,2,0,0,0,0,2,0,0,0,0,0,0,0,0,0,0,0,0,0,1},
            {1,0,0,0,0,0,0,0,0,0,2,2,0,0,0,2,2,0,0,0,2,2,0,0,0,2,2,0,0,0,0,0,0,0,0,0,0,0,0,1},
            {1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1},
            {1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,2,2,0,2,2,1,1,1,1}};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //Reader optimalPath = new Reader();
        //points = optimalPath.getOptimalPath();
        String[] temp;
        Map<String, String> store = TaskActivity.hm;
        int[][] pointUnsorted = new int[store.size()+2][2];
        String[] namesUnsorted = new String[store.size()+2];
        namesUnsorted[0] = "";
        pointUnsorted[0] = new int[]{34, 33};
        int index = 1;
        for(Map.Entry<String, String> entry:store.entrySet()){
            temp = entry.getValue().split(",");
            pointUnsorted[index] = new int[]{Integer.parseInt(temp[0]), Integer.parseInt(temp[1])};
            namesUnsorted[index] = entry.getKey();
            System.out.print(entry.getKey() + "gg");
            System.out.println(store.get(entry.getKey()));
            index++;
        }
        pointUnsorted[pointUnsorted.length-1] = new int[]{31, 16};
        namesUnsorted[namesUnsorted.length-1] = "Checkout";



        Astar star = new Astar();
        TSP_GA tsp = new TSP_GA();
        tspPoints = tsp.getTSP(pointUnsorted); //points
        allPath = star.getOptimalPath(map, tspPoints);
        int[][] tempPoints = tspPoints.clone();
        String[] sortedNames = new String[namesUnsorted.length];
        int pos = 1;
        int[] coord;
        for(int i = 1; i<namesUnsorted.length-1; i++){
            temp = store.get(namesUnsorted[i]).split(",");
            coord = new int[]{Integer.parseInt(temp[0]), Integer.parseInt(temp[1])};
            for(int j = 1; j<tempPoints.length-1; j++){
                if(tempPoints[j][0] == coord[0] && tempPoints[j][1] == coord[1])
                {
                    pos = j;
                    tempPoints[j] = new int[]{0,0};
                    break;
                }
            }
            sortedNames[pos] = namesUnsorted[i];
        }
        sortedNames[0] = "";
        sortedNames[sortedNames.length-1] = "Checkout";
        names = new ArrayList<>();
        for(int i = 0; i < sortedNames.length; i++)
        {
            System.out.print(sortedNames[i]);
            names.add(sortedNames[i]);
        }
        names.add("");
        drawbitview = new drawbitmap(this);
        PixelGridView pixelGrid = new PixelGridView(this);
//        pixelGrid.setPath(path);
        for(int i = 0; i < tspPoints.length; i++){

            System.out.println(tspPoints[i][0] + " " + tspPoints[i][1]);
        }
        pixelGrid.setPoints(tspPoints);
        pixelGrid.setMap(map);
        pixelGrid.setPathEntire(allPath);
        pixelGrid.setAllNames(names);
        pixelGrid.setHome(this);
        setContentView(pixelGrid);
    }
}
