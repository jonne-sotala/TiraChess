package tirachess.util;

import java.util.ArrayList;
import java.util.HashMap;

import tirachess.datastructures.MyArrayList;
import tirachess.datastructures.MyHashMap;
import tirachess.domain.Evaluator;
import tirachess.domain.Position;

public class PerformanceTester {

    /**
     * The constructor for the class.
     */
    public PerformanceTester() {
    }

    /**
     * A method that compares the speed of the custom arraylist compared to the Java 
     * implementation when doing some common tasks. 
     */
    public void testMyArrayList() {
        // MyArrayList
        Long[] myTimes = new Long[10];
        for (int i = 0; i < 10; i++) {
            Long start = System.nanoTime();
            MyArrayList<Integer> myList = new MyArrayList<>();
            int temp = 0;
            for (int j = 0; j < 10000000; j++) {
                myList.add(temp);
                temp = myList.get(j) + 1;
            }
            myList.clear();
            Long end = System.nanoTime();
            myTimes[i] = end - start;
        }
        Long sumOfTime = 0L;
        for (int i = 2; i < 10; i++) {
            sumOfTime += myTimes[i];
        }
        Double averageTime = (sumOfTime / 8) / 1000000.0;
        System.out.println("MyArrayList took " + averageTime + "ms by average");
        // Java ArrayList
        Long[] javaTimes = new Long[10];
        for (int i = 0; i < 10; i++) {
            Long start = System.nanoTime();
            ArrayList<Integer> myList = new ArrayList<>();
            int temp = 0;
            for (int j = 0; j < 10000000; j++) {
                myList.add(temp);
                temp = myList.get(j) + 1;
            }
            myList.clear();
            Long end = System.nanoTime();
            javaTimes[i] = end - start;
        }
        Long sumOfTime2 = 0L;
        for (int i = 2; i < 10; i++) {
            sumOfTime2 += javaTimes[i];
        }
        Double averageTime2 = (sumOfTime2 / 8) / 1000000.0;
        System.out.println("Java ArrayList took " + averageTime2 + "ms by average");
    }

    /**
     * A method that compares the speed of the custom hashmap compared to the Java 
     * implementation when doing some common tasks. 
     */
    public void testMyHashMap() {
        // MyHashMap
        Long[] myTimes = new Long[10];
        for (int i = 0; i < 10; i++) {
            Long start = System.nanoTime();
            MyHashMap<Integer, String> myMap = new MyHashMap<>();
            for (int j = 0; j < 10000000; j++) {
                myMap.put(j, Integer.toString(j));
            }
            String string;
            for (int j = 0; j < 10000000; j++) {
                string = myMap.get(j);
            }
            Long end = System.nanoTime();
            myTimes[i] = end - start;
        }
        Long sumOfTime = 0L;
        for (int i = 2; i < 10; i++) {
            sumOfTime += myTimes[i];
        }
        Double averageTime = (sumOfTime / 8) / 1000000.0;
        System.out.println("MyHashMap took " + averageTime + "ms by average");
        // Java HashMap
        Long[] javaTimes = new Long[10];
        for (int i = 0; i < 10; i++) {
            Long start = System.nanoTime();
            HashMap<Integer, String> javaMap = new HashMap<>();
            for (int j = 0; j < 10000000; j++) {
                javaMap.put(j, Integer.toString(j));
            }
            String string;
            for (int j = 0; j < 10000000; j++) {
                string = javaMap.get(j);
            }
            Long end = System.nanoTime();
            javaTimes[i] = end - start;
        }
        Long sumOfTime2 = 0L;
        for (int i = 2; i < 10; i++) {
            sumOfTime2 += javaTimes[i];
        }
        Double averageTime2 = (sumOfTime2 / 8) / 1000000.0;
        System.out.println("Java HashMap took " + averageTime2 + "ms by average");
    }

    /**
     * A method that compares the alpha-beta pruning algorithm with a simple minmax algorithm. 
     * The test consists of three different positions. 
     */
    public void compareAlphaBetaMinMax() {
        Evaluator evaluator = new Evaluator();

        Position p = new Position();
        System.out.println("Position 1 (rnbqkbnr/pppppppp/8/8/8/8/PPPPPPPP/RNBQKBNR w KQkq - 0 1)");
        long start1 = System.nanoTime();
        double eval1 = evaluator.alphabeta(p, 3, Double.NEGATIVE_INFINITY, 
                                                 Double.POSITIVE_INFINITY);
        long end1 = System.nanoTime();
        double time1 = (end1 - start1) / 10000000.0;
        
        long start2 = System.nanoTime();
        double eval2 = evaluator.minmax(p, 3);
        long end2 = System.nanoTime();
        double time2 = (end2 - start2) / 10000000.0;

        System.out.println("Alpha-beta took " + time1 + "ms");
        System.out.println("Minmax took " + time2 + "ms");
        System.out.println();

        p.importFEN("2bqkbn1/2pppp2/np2N3/r3P1p1/p2N2B1/5Q2/PPPPKPP1/RNB2r2 w KQkq - 0 1");
        System.out.println("Position 2 (2bqkbn1/2pppp2/np2N3/r3P1p1/p2N2B1/5Q2/PPPPKPP1/RNB2r2 w KQkq - 0 1)");
        start1 = System.nanoTime();
        eval1 = evaluator.alphabeta(p, 3, Double.NEGATIVE_INFINITY, 
                                                 Double.POSITIVE_INFINITY);
        end1 = System.nanoTime();
        time1 = (end1 - start1) / 10000000.0;
        
        start2 = System.nanoTime();
        eval2 = evaluator.minmax(p, 3);
        end2 = System.nanoTime();
        time2 = (end2 - start2) / 10000000.0;

        System.out.println("Alpha-beta took " + time1 + "ms");
        System.out.println("Minmax took " + time2 + "ms");
        System.out.println();

        p.importFEN("r3k2r/p1ppqpb1/bn2pnp1/3PN3/1p2P3/2N2Q1p/PPPBBPPP/R3K2R w KQkq -");
        System.out.println("Position 3 (r3k2r/p1ppqpb1/bn2pnp1/3PN3/1p2P3/2N2Q1p/PPPBBPPP/R3K2R w KQkq -)");
        start1 = System.nanoTime();
        eval1 = evaluator.alphabeta(p, 3, Double.NEGATIVE_INFINITY, 
                                                 Double.POSITIVE_INFINITY);
        end1 = System.nanoTime();
        time1 = (end1 - start1) / 10000000.0;
        
        start2 = System.nanoTime();
        eval2 = evaluator.minmax(p, 3);
        end2 = System.nanoTime();
        time2 = (end2 - start2) / 10000000.0;

        System.out.println("Alpha-beta took " + time1 + "ms");
        System.out.println("Minmax took " + time2 + "ms");
    }
}
