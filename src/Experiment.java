

/*
 * This class performs all the experiments for the all the analysis of the proposed counting sort algorithm
 * The experiments involved :
 * Experiment 1 helps replicates the table 1 (Time taken to sort an array using classical counting sort , unordered vs ordered input array)
 * Experiment 3 , Experiment 4 replicates the table 2 (Time take to sort array with counting sort with and without pre-processing)
 * Experiment 5 , Experiment 6 , Experiment 7 replicates the table 3 (Running times in ms for quicksort, quicksort with insertion sort, and quicksort with counting sort)
 * */

import java.util.Arrays;

public class Experiment {

    private static final int THRESHOLD = 1000;

    //helper function to check if its sorted
    public static boolean isSorted(int[] arr) {
        for (int i = 1; i < arr.length; i++) {
            if (arr[i - 1] > arr[i]) {
                return false;
            }
        }
        return true;
    }
//Timer for classical counting sort
    static public long timeCountingSortNs(int[] array) {
        long start = System.nanoTime();
        QuickCountingSort.countingSort(array, 0, array.length - 1, SortFunctions.getMin(array),SortFunctions.getMax(array));
        long end = System.nanoTime();

        if (!isSorted(array)) {
            throw new RuntimeException("Array is not sorted correctly.");
        }

        return end - start;
    }

    //Timer for quickSortModified

    static public long timeQuickSortNs(int[] array) {

        int maxVal = SortFunctions.getMax(array);
        int minVal = SortFunctions.getMin(array);

        long start = System.nanoTime();
        SortFunctions.quickSortModified(array,0,array.length -1,maxVal,minVal,THRESHOLD);
        long end = System.nanoTime();


        return end - start;
    }



    static public void Timer2(int[] array, int n,int T){




        int maxVal = SortFunctions.getMax(array);
        int minVal = SortFunctions.getMin(array);

        long start1 =  System.nanoTime();
        SortFunctions.quickSortModified(array,0,n-1,maxVal,minVal,THRESHOLD);
        long end1 = System.nanoTime();
        // time calculated
        long durationNs1 = end1 - start1;
        double durationMs1 = durationNs1 / 1_000_000.0;

        long start2 =  System.nanoTime();
        QuickCountingSort.countingSort(array,0,n-1, SortFunctions.getMin(array),SortFunctions.getMax(array));
        long end2 = System.nanoTime();
        long durationNs2 = end2 - start2;
        double durationMs2 = durationNs2 / 1_000_000.0;

        System.out.println("T" + T + " : " + durationMs1 + " ms + " + durationMs2 + " ms");


    }

    // timer for just quicksort
    static public long TimerQuicksort(int[] array){
        long start = System.nanoTime();

        QuickSort.sort(array); //may need to change according to implementation
        long end = System.nanoTime();
        return  end - start;

    }

    // timer for quicksort_modified and insertionsort
    static public long TimerQuickInsertionsort(int[] array){


        long start = System.nanoTime();
        QuickInsertionSort.sort(array);

        long end = System.nanoTime();
        return end - start;



    }

    // timer for proposed algorithm

    static public long TimerProposedAlgo(int[] array){
        int maxVal = SortFunctions.getMax(array);
        int minVal = SortFunctions.getMin(array);

        long start = System.nanoTime();

        QuickCountingSort.sort(array,maxVal,minVal);

        long end = System.nanoTime();
        return end - start;

    }

    /*
     * Experiment 1 : Measuring performance comparison in ms Over Random(T1) and sorted Inputs(T2)
     *
     * Here for the input array n =r , n is the no.of inputs and r is the range
     * The timing is calculated , a Random independent array with n inputs and r range st. n = r will be input 1st
     * T here is indicating element for if T1 is calculated or T2
     * Later for T2 , another sorted input array in the form {0,1,2,....,n-1} , here to n = r will be input
     *
     * */



    static public void experiment3(){
        System.out.println("Running Times in ms for Quicksort, Quicksort with Insertion Sort, and Quicksort with Counting Sort");

        int[] n = {1000000,2000000};
        for(int i = 0 ; i < 2 ; i++){
            int[] array1 = SortFunctions.createArr(n[i],n[i]);

            int[] a1 = array1.clone();
            int[] a2 = array1.clone();
            int[] a3 = array1.clone();

            System.out.println("n:"+n[i]+"       r:"+ n);
            TimerQuicksort(a1);
            TimerQuickInsertionsort(a2);
            TimerProposedAlgo(a3);

            System.out.println();

        }
    }

    static public void experiment_3(){
        System.out.println("Running Times in ms for Quicksort, Quicksort with Insertion Sort, and Quicksort with Counting Sort");

        int[] n = {1000000,2000000};
        int warmups = 2;
        int runs = 10;

        for(int N : n){

            System.out.println("n:"+N+"       r:"+N);

            //warms up the JVM

            for (int i = 0; i < warmups; i++) {
                int[] warmRandom = SortFunctions.createArr(N, N);
                int[] warmRandom1 = warmRandom.clone();
                int[] warmRandom2 = warmRandom.clone();

                int warmMax = SortFunctions.getMax(warmRandom);
                int warmMin = SortFunctions.getMin(warmRandom);

                QuickSort.sort(warmRandom);
                QuickInsertionSort.sort(warmRandom1);
                QuickCountingSort.sort(warmRandom2,warmMax,warmMin);
            }

            long totalT1 = 0;
            long totalT2 = 0;
            long totalT3 = 0;

            for (int rep = 0; rep < runs; rep++) {
                int[] randomArr = SortFunctions.createArr(N, N);
                int[] randomArr1 = randomArr.clone();
                int[] randomArr2 = randomArr.clone();
                int max = SortFunctions.getMax(randomArr);
                int min = SortFunctions.getMin(randomArr);

                //alternating the calling between the randomArr and sortedArr to avoid bias

                if (rep % 3 == 0) {
                    totalT1 += TimerQuicksort(randomArr);//classical quick sort

                    totalT2 += TimerQuickInsertionsort(randomArr1); // running the modified quick sort with insersion sort
                    totalT3 += TimerProposedAlgo(randomArr2); // proposed algorithm
                } else if (rep % 3 == 1) {


                    totalT2 += TimerQuickInsertionsort(randomArr1); // running the modified quick sort with insersion sort
                    totalT3 += TimerProposedAlgo(randomArr2); // proposed algorithm
                    totalT1 += TimerQuicksort(randomArr);//classical quick sort

                } else {

                    totalT3 += TimerProposedAlgo(randomArr2); // proposed algorithm
                    totalT1 += TimerQuicksort(randomArr);//classical quick sort
                    totalT2 += TimerQuickInsertionsort(randomArr1); // running the modified quick sort with insersion sort
                }
            }

            double avgT1 = (totalT1 / (double) runs) / 1_000_000.0;
            double avgT2 = (totalT2 / (double) runs) / 1_000_000.0;
            double avgT3 = (totalT3 / (double) runs) / 1_000_000.0;

            System.out.println("T1 : " + avgT1  + " ms");
            System.out.println("T2 : " + avgT2 + " ms");
            System.out.println("T3 : " + avgT3 + " ms");

            System.out.println();



        }
    }

    static public void experiment2(){
        System.out.println("Running Times in ms for Counting Sort with and\n" +
                "without Preprocessing");
        int r =1000000;
        int[] n = {1000,2000,3000};

        for(int i = 0 ; i < 3 ; i++){

            System.out.println("n:"+n[i]+"       r:"+r);
            //T1
            int[] array1 = SortFunctions.createArr(n[i],r);
            Timer2(array1,n[i],1);
            //Timer1(array1,n[i],2);

            System.out.println();

        }
    }

    static public void experiment_2() {
        System.out.println("Running Times in ms for Counting Sort with and without Preprocessing");
        int r =1000000;
        int[] n = {1000,2000,3000};
        int warmups = 2;
        int runs = 10;

        for (int N : n){
            System.out.println("n:"+N+"       r:"+r);

            //warms up the JVM

            for (int i = 0; i < warmups; i++) {
                int[] warmRandom = SortFunctions.createArr(N, r);
                int[] warmRandom1 = warmRandom.clone();

                int warmMax = SortFunctions.getMax(warmRandom);
                int warmMin = SortFunctions.getMin(warmRandom);

                QuickCountingSort.countingSort(warmRandom, 0, warmRandom.length - 1,warmMin, warmMax);
                SortFunctions.quickSortModified(warmRandom1,0,warmRandom1.length -1,warmMax,warmMin,THRESHOLD);
                QuickCountingSort.countingSort(warmRandom1, 0, warmRandom1.length - 1,warmMin, warmMax);
            }

            long totalT1 = 0;
            long totalT2 = 0;
            long totalT3 = 0;

            for (int rep = 0; rep < runs; rep++) {
                int[] randomArr = SortFunctions.createArr(N, r);
                int[] randomArr1 = randomArr.clone();

                //alternating the calling between the randomArr and sortedArr to avoid bias

                if (rep % 2 == 0) {
                    totalT1 += timeCountingSortNs(randomArr);//counting sort wihtout pre-processing

                    totalT2 += timeQuickSortNs(randomArr1); // running the modified quick sort as preprocessing step
                    totalT3 += timeCountingSortNs(randomArr1); // counting after preprocessing step
                } else {
                    totalT2 += timeQuickSortNs(randomArr1); // running the modified quick sort as preprocessing step
                    totalT3 += timeCountingSortNs(randomArr1); // counting after preprocessing step
                    totalT1 += timeCountingSortNs(randomArr);
                }
            }

            double avgT1 = (totalT1 / (double) runs) / 1_000_000.0;
            double avgT2 = (totalT2 / (double) runs) / 1_000_000.0;
            double avgT3 = (totalT3 / (double) runs) / 1_000_000.0;

            System.out.println("T1 : " + avgT2 + " ms + "+ avgT3 + " ms");
            System.out.println("T2 : " + avgT1 + " ms");

            System.out.println();



        }
    }



    static public void experiment1() {
        System.out.println("performance comparison in ms Over Random(T1) and sorted Inputs(T2)\n");

        int[] nValues = {1000000, 2000000, 3000000};
        int warmups = 3;
        int runs = 10;

        for (int n : nValues) {
            System.out.println("n:" + n + "       r:" + n);

            //warms up the JVM

            for (int i = 0; i < warmups; i++) {
                int[] warmRandom = SortFunctions.createArr(n, n);
                int[] warmSorted = SortFunctions.createSortedArr(n);

                QuickCountingSort.countingSort(warmRandom, 0, warmRandom.length - 1 ,SortFunctions.getMin(warmRandom), SortFunctions.getMax(warmRandom));
                QuickCountingSort.countingSort(warmSorted, 0, warmSorted.length - 1, SortFunctions.getMin(warmSorted),SortFunctions.getMax(warmSorted));
            }

            long totalT1 = 0;
            long totalT2 = 0;

            for (int rep = 0; rep < runs; rep++) {
                int[] randomArr = SortFunctions.createArr(n, n);
                int[] sortedArr = SortFunctions.createSortedArr(n);

                //alternating the calling between the randomArr and sortedArr to avoid bias

                if (rep % 2 == 0) {
                    totalT1 += timeCountingSortNs(randomArr);
                    totalT2 += timeCountingSortNs(sortedArr);
                } else {
                    totalT2 += timeCountingSortNs(sortedArr);
                    totalT1 += timeCountingSortNs(randomArr);
                }
            }

            double avgT1 = (totalT1 / (double) runs) / 1_000_000.0;
            double avgT2 = (totalT2 / (double) runs) / 1_000_000.0;

            System.out.println("T1 : " + avgT1 + " ms");
            System.out.println("T2 : " + avgT2 + " ms");
            System.out.println();
        }
    }

    public static void main(String[] args){


        //experiment1();
        //experiment_2();
        experiment_3();



    }
}

