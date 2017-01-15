package AStar;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;
import java.util.Comparator;


public class Astar {
    public static final int DIAGONAL_COST = 14;
    public static final int V_H_COST = 10;
    public int[][] optimalPath;
    public int[][] optimalCoordinates;

    static class Cell {
        int heuristicCost = 0; // Heuristic cost
        int finalCost = 0; // G+H
        int i, j;
        Cell parent;

        Cell(int i, int j) {
            this.i = i;
            this.j = j;
        }

        @Override
        public String toString() {
            return "[" + this.i + ", " + this.j + "]";
        }

        public int getX() {
            return i;
        }

        public int getY() {
            return j;
        }
    }

    // Blocked cells are just null Cell values in grid
    static Cell[][] grid = new Cell[5][5];

    static PriorityQueue<Cell> open;

    static boolean closed[][];
    static int startI, startJ;
    static int endI, endJ;

    public static void setBlocked(int i, int j) {
        grid[i][j] = null;
    }

    public static void setStartCell(int i, int j) {
        startI = i;
        startJ = j;
    }

    public static void setEndCell(int i, int j) {
        endI = i;
        endJ = j;
    }

    static void checkAndUpdateCost(Cell current, Cell t, int cost) {
        if (t == null || closed[t.i][t.j])
            return;
        int t_final_cost = t.heuristicCost + cost;

        boolean inOpen = open.contains(t);
        if (!inOpen || t_final_cost < t.finalCost) {
            t.finalCost = t_final_cost;
            t.parent = current;
            if (!inOpen)
                open.add(t);
        }
    }

    public static void AStar() {

        // add the start location to open list.
        open.add(grid[startI][startJ]);

        Cell current;

        while (true) {
            current = open.poll();
            if (current == null)
                break;
            closed[current.i][current.j] = true;

            if (current.equals(grid[endI][endJ])) {
                return;
            }

            Cell t;
            if (current.i - 1 >= 0) {
                t = grid[current.i - 1][current.j];
                checkAndUpdateCost(current, t, current.finalCost + V_H_COST);

                if (current.j - 1 >= 0) {
                    t = grid[current.i - 1][current.j - 1];
                    checkAndUpdateCost(current, t, current.finalCost + DIAGONAL_COST);
                }

                if (current.j + 1 < grid[0].length) {
                    t = grid[current.i - 1][current.j + 1];
                    checkAndUpdateCost(current, t, current.finalCost + DIAGONAL_COST);
                }
            }

            if (current.j - 1 >= 0) {
                t = grid[current.i][current.j - 1];
                checkAndUpdateCost(current, t, current.finalCost + V_H_COST);
            }

            if (current.j + 1 < grid[0].length) {
                t = grid[current.i][current.j + 1];
                checkAndUpdateCost(current, t, current.finalCost + V_H_COST);
            }

            if (current.i + 1 < grid.length) {
                t = grid[current.i + 1][current.j];
                checkAndUpdateCost(current, t, current.finalCost + V_H_COST);

                if (current.j - 1 >= 0) {
                    t = grid[current.i + 1][current.j - 1];
                    checkAndUpdateCost(current, t, current.finalCost + DIAGONAL_COST);
                }

                if (current.j + 1 < grid[0].length) {
                    t = grid[current.i + 1][current.j + 1];
                    checkAndUpdateCost(current, t, current.finalCost + DIAGONAL_COST);
                }
            }
        }
    }

    // StringLengthComparator.java

    static class FinalCostComparator implements Comparator<Cell> {



        @Override
        public int compare(Cell c1, Cell c2) {
            if (c1.finalCost < c2.finalCost) {
                return -1;
            } else if (c1.finalCost > c2.finalCost) {
                return 1;
            } else {
                return 0;
            }
        }
    }

        public static ArrayList<Cell> test(int x, int y, int si, int sj, int ei, int ej, ArrayList<Cell> blocked) {
            ArrayList<Cell> path = new ArrayList<Cell>();

            // Reset
            grid = new Cell[x][y];
            closed = new boolean[x][y];
            Comparator<Cell> comparator = new FinalCostComparator();
            open = new PriorityQueue<Cell>(100, comparator);
            // Set start position
            setStartCell(si, sj); // Setting to 0,0 by default. Will be useful for
            // the UI part

            // Set End Location
            setEndCell(ei, ej);

            for (int i = 0; i < x; ++i) {
                for (int j = 0; j < y; ++j) {
                    grid[i][j] = new Cell(i, j);
                    grid[i][j].heuristicCost = Math.abs(i - endI) + Math.abs(j - endJ);
                    // System.out.print(grid[i][j].heuristicCost+" ");
                }
                // System.out.println();
            }
            grid[si][sj].finalCost = 0;

		/*
		 * Set blocked cells. Simply set the cell values to null for blocked
		 * cells.
		 */
            for (int i = 0; i < blocked.size(); ++i) {
                setBlocked(blocked.get(i).getY(), blocked.get(i).getX());
            }

            // Display initial map
            // System.out.println("Grid: ");
            // for (int i = 0; i < x; ++i) {
            // for (int j = 0; j < y; ++j) {
            // if (i == si && j == sj)
            // System.out.print("SO "); // Source
            // else if (i == ei && j == ej)
            // System.out.print("DE "); // Destination
            // else if (grid[i][j] != null)
            // System.out.printf("%-3d ", 0);
            // else
            // System.out.print("BL ");
            // }
            // System.out.println();
            // }
            // System.out.println();

            AStar();
            // System.out.println("\nScores for cells: ");
            // for (int i = 0; i < x; ++i) {
            // for (int j = 0; j < x; ++j) {
            // if (grid[i][j] != null)
            // System.out.printf("%-3d ", grid[i][j].finalCost);
            // else
            // System.out.print("BL ");
            // }
            // System.out.println();
            // }
            // System.out.println();

                if (closed[endI][endJ]) {
                    // Trace back the path
                    System.out.println("Path: ");
                    Cell current = grid[endI][endJ];
                    path.add(current);
                    System.out.print(current);
                    while (current.parent != null) {
                        System.out.print(" -> " + current.parent);
                        current = current.parent;
                        path.add(current);
                    }
                    System.out.println();
                } else
                    System.out.println("No possible path");

        return path;

        }






        /*
         * Params : tCase = test case No. x, y = Board's dimensions si, sj = start
         * location's x and y coordinates ei, ej = end location's x and y
         * coordinates int[][] blocked = array containing inaccessible cell
         * coordinates
         */
        public static void main(String[] args) throws Exception {

            int[][] bitmap = new int[][]{
                    {1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1,
                            1, 1, 1, 1, 1, 1},
                    {1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
                            0, 0, 0, 0, 0, 1},
                    {1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
                            0, 0, 0, 0, 0, 1},
                    {1, 0, 0, 1, 1, 0, 0, 1, 1, 0, 0, 1, 1, 0, 0, 1, 1, 0, 0, 1, 1, 0, 0, 1, 1, 0, 0, 1, 1, 0, 0, 1, 1, 0,
                            0, 1, 1, 0, 0, 1},
                    {1, 0, 0, 1, 1, 0, 0, 1, 1, 0, 0, 1, 1, 0, 0, 1, 1, 0, 0, 1, 1, 0, 0, 1, 1, 0, 0, 1, 1, 0, 0, 1, 1, 0,
                            0, 1, 1, 0, 0, 1},
                    {1, 0, 0, 1, 1, 0, 0, 1, 1, 0, 0, 1, 1, 0, 0, 1, 1, 0, 0, 1, 1, 0, 0, 1, 1, 0, 0, 1, 1, 0, 0, 1, 1, 0,
                            0, 1, 1, 0, 0, 1},
                    {1, 0, 0, 1, 1, 0, 0, 1, 1, 0, 0, 1, 1, 0, 0, 1, 1, 0, 0, 1, 1, 0, 0, 1, 1, 0, 0, 1, 1, 0, 0, 1, 1, 0,
                            0, 1, 1, 0, 0, 1},
                    {1, 0, 0, 1, 1, 0, 0, 1, 1, 0, 0, 1, 1, 0, 0, 1, 1, 0, 0, 1, 1, 0, 0, 1, 1, 0, 0, 1, 1, 0, 0, 1, 1, 0,
                            0, 1, 1, 0, 0, 1},
                    {1, 0, 0, 1, 1, 0, 0, 1, 1, 0, 0, 1, 1, 0, 0, 1, 1, 0, 0, 1, 1, 0, 0, 1, 1, 0, 0, 1, 1, 0, 0, 1, 1, 0,
                            0, 1, 1, 0, 0, 1},
                    {1, 0, 0, 1, 1, 0, 0, 1, 1, 0, 0, 1, 1, 0, 0, 1, 1, 0, 0, 1, 1, 0, 0, 1, 1, 0, 0, 1, 1, 0, 0, 1, 1, 0,
                            0, 1, 1, 0, 0, 1},
                    {1, 0, 0, 1, 1, 0, 0, 1, 1, 0, 0, 1, 1, 0, 0, 1, 1, 0, 0, 1, 1, 0, 0, 1, 1, 0, 0, 1, 1, 0, 0, 1, 1, 0,
                            0, 1, 1, 0, 0, 1},
                    {1, 0, 0, 1, 1, 0, 0, 1, 1, 0, 0, 1, 1, 0, 0, 1, 1, 0, 0, 1, 1, 0, 0, 1, 1, 0, 0, 1, 1, 0, 0, 1, 1, 0,
                            0, 1, 1, 0, 0, 1},
                    {1, 0, 0, 1, 1, 0, 0, 1, 1, 0, 0, 1, 1, 0, 0, 1, 1, 0, 0, 1, 1, 0, 0, 1, 1, 0, 0, 1, 1, 0, 0, 1, 1, 0,
                            0, 1, 1, 0, 0, 1},
                    {1, 0, 0, 1, 1, 0, 0, 1, 1, 0, 0, 1, 1, 0, 0, 1, 1, 0, 0, 1, 1, 0, 0, 1, 1, 0, 0, 1, 1, 0, 0, 1, 1, 0,
                            0, 1, 1, 0, 0, 1},
                    {1, 0, 0, 1, 1, 0, 0, 1, 1, 0, 0, 1, 1, 0, 0, 1, 1, 0, 0, 1, 1, 0, 0, 1, 1, 0, 0, 1, 1, 0, 0, 1, 1, 0,
                            0, 1, 1, 0, 0, 1},
                    {1, 0, 0, 1, 1, 0, 0, 1, 1, 0, 0, 1, 1, 0, 0, 1, 1, 0, 0, 1, 1, 0, 0, 1, 1, 0, 0, 1, 1, 0, 0, 1, 1, 0,
                            0, 1, 1, 0, 0, 1},
                    {1, 0, 0, 1, 1, 0, 0, 1, 1, 0, 0, 1, 1, 0, 0, 1, 1, 0, 0, 1, 1, 0, 0, 1, 1, 0, 0, 1, 1, 0, 0, 1, 1, 0,
                            0, 1, 1, 0, 0, 1},
                    {1, 0, 0, 1, 1, 0, 0, 1, 1, 0, 0, 1, 1, 0, 0, 1, 1, 0, 0, 1, 1, 0, 0, 1, 1, 0, 0, 1, 1, 0, 0, 1, 1, 0,
                            0, 1, 1, 0, 0, 1},
                    {1, 0, 0, 1, 1, 0, 0, 1, 1, 0, 0, 1, 1, 0, 0, 1, 1, 0, 0, 1, 1, 0, 0, 1, 1, 0, 0, 1, 1, 0, 0, 1, 1, 0,
                            0, 1, 1, 0, 0, 1},
                    {1, 0, 0, 1, 1, 0, 0, 1, 1, 0, 0, 1, 1, 0, 0, 1, 1, 0, 0, 1, 1, 0, 0, 1, 1, 0, 0, 1, 1, 0, 0, 1, 1, 0,
                            0, 1, 1, 0, 0, 1},
                    {1, 0, 0, 1, 1, 0, 0, 1, 1, 0, 0, 1, 1, 0, 0, 1, 1, 0, 0, 1, 1, 0, 0, 1, 1, 0, 0, 1, 1, 0, 0, 1, 1, 0,
                            0, 1, 1, 0, 0, 1},
                    {1, 0, 0, 1, 1, 0, 0, 1, 1, 0, 0, 1, 1, 0, 0, 1, 1, 0, 0, 1, 1, 0, 0, 1, 1, 0, 0, 1, 1, 0, 0, 1, 1, 0,
                            0, 1, 1, 0, 0, 1},
                    {1, 0, 0, 1, 1, 0, 0, 1, 1, 0, 0, 1, 1, 0, 0, 1, 1, 0, 0, 1, 1, 0, 0, 1, 1, 0, 0, 1, 1, 0, 0, 1, 1, 0,
                            0, 1, 1, 0, 0, 1},
                    {1, 0, 0, 1, 1, 0, 0, 1, 1, 0, 0, 1, 1, 0, 0, 1, 1, 0, 0, 1, 1, 0, 0, 1, 1, 0, 0, 1, 1, 0, 0, 1, 1, 0,
                            0, 1, 1, 0, 0, 1},
                    {1, 0, 0, 1, 1, 0, 0, 1, 1, 0, 0, 1, 1, 0, 0, 1, 1, 0, 0, 1, 1, 0, 0, 1, 1, 0, 0, 1, 1, 0, 0, 1, 1, 0,
                            0, 1, 1, 0, 0, 1},
                    {1, 0, 0, 1, 1, 0, 0, 1, 1, 0, 0, 1, 1, 0, 0, 1, 1, 0, 0, 1, 1, 0, 0, 1, 1, 0, 0, 1, 1, 0, 0, 1, 1, 0,
                            0, 1, 1, 0, 0, 1},
                    {1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
                            0, 0, 0, 0, 0, 1},
                    {1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
                            0, 0, 0, 0, 0, 1},
                    {1, 0, 0, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1,
                            1, 1, 1, 0, 0, 1},
                    {1, 0, 0, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1,
                            1, 1, 1, 0, 0, 1},
                    {1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
                            0, 0, 0, 0, 0, 1},
                    {1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
                            0, 0, 0, 0, 0, 1},
                    {1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
                            0, 0, 0, 0, 0, 1},
                    {1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 0, 0,
                            0, 1, 1, 1, 1, 1}};
            ArrayList<Cell> node = new ArrayList<Cell>();
            Cell temp;
            for (int r = 0; r < bitmap.length; r++) {
                for (int c = 0; c < bitmap[0].length; c++) {
                    if (bitmap[r][c] == 1) {
                        temp = new Cell(c, r);
                        node.add(temp);
                    }
                }

            }
            try {
                FileReader fileReader = new FileReader("optimalpath.txt");
                BufferedReader br = new BufferedReader(fileReader);
                String xstart = br.readLine();
                String ystart = br.readLine();
                String xend = "";
                while ((xend = br.readLine()) != null) {
                    String yend = br.readLine();
                    test(34, 40, Integer.parseInt(xend), Integer.parseInt(yend), Integer.parseInt(xstart),
                            Integer.parseInt(ystart), node);
                    xstart = xend;
                    ystart = yend;
                }

                br.close();
            } catch (FileNotFoundException ex) {
                ex.printStackTrace();
            } catch (IOException ex) {
                ex.printStackTrace();
            }


        }

    public int[][] getOptimalPath(byte[][] map, int[][] path)
    {
        ArrayList<Cell> node = new ArrayList<Cell>();
        Cell temp;
        for (int r = 0; r < map.length; r++) {
            for (int c = 0; c < map[0].length; c++) {
                if (map[r][c] != 0) {
                    temp = new Cell(c, r);
                    node.add(temp);
                }
            }

        }

        ArrayList<Cell> allPath = new ArrayList<Cell>();
        ArrayList<Cell> tempPath = new ArrayList<Cell>();
        int xstart = path[0][0];
        int ystart = path[0][1];
        for(int r = 1; r < path.length;r++)
        {
            int xend = path[r][0];
            int yend = path[r][1];
            tempPath = test(map.length, map[0].length, xend, yend, xstart,
                    ystart, node);
            for(Cell i:tempPath)
            {
                allPath.add(i);
            }
            xstart = xend;
            ystart = yend;
        }
        int[][] ret = new int[allPath.size()][2];
        for(int i = 0; i < ret.length; i++)
        {
            ret[i][0] = allPath.get(i).getX();
            ret[i][1] = allPath.get(i).getY();
        }
        return ret;
    }


    }
