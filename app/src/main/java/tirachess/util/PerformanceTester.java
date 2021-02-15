package tirachess.util;

import java.util.ArrayList;

import tirachess.datastructures.MyArrayList;

public class PerformanceTester {

    public PerformanceTester() {
    }

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
        System.out.println("MyArrayList took " + averageTime + "ms");
        // Java ArrayList
        Long[] times = new Long[10];
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
            times[i] = end - start;
        }
        Long sumOfTime2 = 0L;
        for (int i = 2; i < 10; i++) {
            sumOfTime2 += times[i];
        }
        Double averageTime2 = (sumOfTime2 / 8) / 1000000.0;
        System.out.println("Java ArrayList took " + averageTime2 + "ms");
    }
}
