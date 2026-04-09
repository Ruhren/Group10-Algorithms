/*
 * This class performs all the experiments for the all the analysis of the proposed counting sort algorithm
 * The experiments involved :
 * Experiment 1 replicates the table 1 (Time taken to sort an array using classical counting sort , unordered vs ordered input array)
 * Experiment 2 replicates the table 2 (Time take to sort array with counting sort with and without pre-processing)
 * Experiment 3 replicates the table 3 (Running times in ms for quicksort, quicksort with insertion sort, and quicksort with counting sort)
 * */

public class Experiment {

    private static final int THRESHOLD = 1000;
    private static final int RUNS = 10;

    // Helper function to check if array is sorted
    public static boolean isSorted(int[] arr) {
        for (int i = 1; i < arr.length; i++) {
            if (arr[i - 1] > arr[i]) {
                return false;
            }
        }
        return true;
    }

    // Helper function to warm up JVM
    private static void warmup(int iterations, int n, int r) {
        for (int i = 0; i < iterations; i++) {
            // warm up for random array
            int[] randomArr = SortFunctions.createArr(n, r);
            int[] randomArr1 = randomArr.clone();
            int[] randomArr2 = randomArr.clone();

            int max = SortFunctions.getMax(randomArr);
            int min = SortFunctions.getMin(randomArr);

            QuickSort.sort(randomArr);
            QuickInsertionSort.sort(randomArr1);
            QuickCountingSort.sort(randomArr2, max, min);

            // warm up for sorted array
            int[] sortedArr = SortFunctions.createSortedArr(n);
            int[] sortedArr1 = sortedArr.clone();
            int[] sortedArr2 = sortedArr.clone();

            int sortedMax = SortFunctions.getMax(sortedArr);
            int sortedMin = SortFunctions.getMin(sortedArr);

            QuickSort.sort(sortedArr);
            QuickInsertionSort.sort(sortedArr1);
            QuickCountingSort.sort(sortedArr2, sortedMax, sortedMin);
        }
    }

    // Timer for classical counting sort
    public static long timeCountingSort(int[] array) {
        int maxVal = SortFunctions.getMax(array);
        int minVal = SortFunctions.getMin(array);

        long start = System.nanoTime();
        QuickCountingSort.countingSort(array, 0, array.length - 1, minVal, maxVal);
        long end = System.nanoTime();

        if (!isSorted(array)) {
            throw new RuntimeException("Array is not sorted correctly.");
        }

        return end - start;
    }

    // Timer for quickSortModified
    public static long timeQuickSort(int[] array) {
        int maxVal = SortFunctions.getMax(array);
        int minVal = SortFunctions.getMin(array);

        long start = System.nanoTime();
        SortFunctions.quickSortModified(array,0,array.length -1, maxVal, minVal, THRESHOLD);
        long end = System.nanoTime();

        return end - start;
    }

    // Timer for classic quickSort
    public static long TimerQuickSort(int[] array){
        long start = System.nanoTime();
        QuickSort.sort(array);
        long end = System.nanoTime();

        return  end - start;
    }

    // Timer for quickSortModified and insertionSort
     public static long TimerQuickInsertionSort(int[] array){
        long start = System.nanoTime();
        QuickInsertionSort.sort(array);
        long end = System.nanoTime();

        return end - start;
    }

    // Timer for proposed algorithm
    public static long TimerQuickCountingSort(int[] array){
        int maxVal = SortFunctions.getMax(array);
        int minVal = SortFunctions.getMin(array);

        long start = System.nanoTime();
        QuickCountingSort.sort(array, maxVal, minVal);
        long end = System.nanoTime();

        return end - start;
    }

    /*
     * Experiment 1 : Measuring performance comparison in ms Over Random(T1) and sorted Inputs(T2)
     *
     * For the input array n = r, n is the no. of inputs and r is the range
     * Timing is calculated, a Random independent array with n inputs and r range s.t. n = r will be input 1st
     * T is indicating element for if T1 is calculated or T2
     * Later for T2, another sorted input array in the form {0,1,2,....,n-1} , here to n = r will be input
     *
     * */

    public static void experiment1() {
        System.out.println("Performance Comparison in ms Over Random (T1) and Sorted Inputs (T2)");

        int[] nValues = {1000000, 2000000, 3000000};

        warmup(5, nValues[2], nValues[2]);

        for (int n : nValues) {
            System.out.println("n:" + n + "       r:" + n);

            long totalT1 = 0;
            long totalT2 = 0;

            for (int rep = 0; rep < RUNS; rep++) {
                int[] randomArr = SortFunctions.createArr(n, n);
                int[] sortedArr = SortFunctions.createSortedArr(n);

                // alternating the calling between the randomArr and sortedArr to avoid bias

                if (rep % 2 == 0) {
                    totalT1 += timeCountingSort(randomArr);
                    totalT2 += timeCountingSort(sortedArr);
                } else {
                    totalT2 += timeCountingSort(sortedArr);
                    totalT1 += timeCountingSort(randomArr);
                }
            }

            double avgT1 = (totalT1 / (double) RUNS) / 1_000_000.0;
            double avgT2 = (totalT2 / (double) RUNS) / 1_000_000.0;

            System.out.printf("T1 : %.2f ms\n", avgT1);
            System.out.printf("T2 : %.2f ms\n", avgT2);
            System.out.println();
        }
    }

    public static void experiment2() {
        System.out.println("Running Times in ms for Counting Sort with and without Preprocessing");
        int r = 1000000;
        int[] nValues = {1000, 2000, 3000};

        warmup(5, nValues[2], r);

        for (int n : nValues){
            System.out.println("n:" + n + "       r:" + r);

            long totalPreprocessT1 = 0;
            long totalCountingT1 = 0;
            long totalT2 = 0;

            for (int rep = 0; rep < RUNS; rep++) {
                int[] randomArr = SortFunctions.createArr(n, r);
                int[] randomArr1 = randomArr.clone();

                // alternating the calling between arrays to avoid bias

                if (rep % 2 == 0) {
                    totalT2 += timeCountingSort(randomArr);           // counting sort without pre-processing
                    totalPreprocessT1 += timeQuickSort(randomArr1);   // modified quick sort as preprocessing step
                    totalCountingT1 += timeCountingSort(randomArr1);  // counting after preprocessing step
                } else {
                    totalPreprocessT1 += timeQuickSort(randomArr1);   // modified quick sort as preprocessing step
                    totalCountingT1 += timeCountingSort(randomArr1);  // counting after preprocessing step
                    totalT2 += timeCountingSort(randomArr);           // counting sort without pre-processing
                }
            }

            double avgPreprocessT1 = (totalPreprocessT1 / (double) RUNS) / 1_000_000.0;
            double avgCountingT1 = (totalCountingT1 / (double) RUNS) / 1_000_000.0;
            double avgT2 = (totalT2 / (double) RUNS) / 1_000_000.0;

            System.out.printf("T1 : %.2f ms + %.2f ms\n", avgPreprocessT1, avgCountingT1);
            System.out.printf("T2 : %.2f ms\n", avgT2);
            System.out.println();
        }
    }

    public static void experiment3(){
        System.out.println("Running Times in ms for Quicksort, Quicksort with Insertion Sort, and Quicksort with Counting Sort");

        int[] nValues = {1000000,2000000};

        warmup(5, nValues[1], nValues[1]);

        for(int n : nValues) {
            System.out.println("n:" + n + "       r:" + n);

            long totalT1 = 0;
            long totalT2 = 0;
            long totalT3 = 0;

            for (int rep = 0; rep < RUNS; rep++) {
                int[] randomArr = SortFunctions.createArr(n, n);
                int[] randomArr1 = randomArr.clone();
                int[] randomArr2 = randomArr.clone();

                // alternating the calling between arrays to avoid bias

                if (rep % 3 == 0) {
                    totalT1 += TimerQuickSort(randomArr);               // classic quick sort
                    totalT2 += TimerQuickInsertionSort(randomArr1);     // modified quick sort with insertion sort
                    totalT3 += TimerQuickCountingSort(randomArr2);           // proposed algorithm
                } else if (rep % 3 == 1) {
                    totalT2 += TimerQuickInsertionSort(randomArr1);     // modified quick sort with insertion sort
                    totalT3 += TimerQuickCountingSort(randomArr2);           // proposed algorithm
                    totalT1 += TimerQuickSort(randomArr);               // classical quick sort

                } else {
                    totalT3 += TimerQuickCountingSort(randomArr2);           // proposed algorithm
                    totalT1 += TimerQuickSort(randomArr);               // classical quick sort
                    totalT2 += TimerQuickInsertionSort(randomArr1);     // modified quick sort with insertion sort
                }
            }

            double avgT1 = (totalT1 / (double) RUNS) / 1_000_000.0;
            double avgT2 = (totalT2 / (double) RUNS) / 1_000_000.0;
            double avgT3 = (totalT3 / (double) RUNS) / 1_000_000.0;

            System.out.printf("T1 : %.2f ms\n", avgT1);
            System.out.printf("T2 : %.2f ms\n", avgT2);
            System.out.printf("T3 : %.2f ms\n", avgT3);
            System.out.println();
        }
    }

    public static void main(String[] args) {
        experiment1();
        experiment2();
        experiment3();
    }
}

