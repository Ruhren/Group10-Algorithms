public class QuickCountingSort {
    // combined value range and partition size
    private static final int THRESHOLD = 1000;

    public static void sort(int[] arr, int max, int min) {
        if (arr == null || arr.length == 0) {
            return;
        }

        // preprocessing step: modified quicksort to partition the array
        // this will stop partitioning once it hits the THRESHOLD
        SortFunctions.quickSortModified(arr, 0, arr.length - 1, max, min, THRESHOLD);

        // then sort partitions individually
        finishSorting(arr, 0, arr.length - 1, max, min);
    }

    private static void finishSorting(int[] arr, int low, int high, int max, int min) {
        if (low >= high) {
            return;
        }

        // if partition reaches the threshold, it is skipped
        if ((max - min + (high - low)) <= THRESHOLD) {
            // sort only these partitions
            countingSort(arr, low, high, max);
        } else {
            // find the pivot index from the previous partition
            int pivot = SortFunctions.partition(arr, low, high);
            int midValue = arr[pivot];

            // search left and right sides using pivot as new max / min
            finishSorting(arr, low, pivot - 1, midValue, min);
            finishSorting(arr, pivot + 1, high, max, midValue);
        }
    }

    private static void countingSort(int[] arr, int low, int high, int max) {
        int n = high - low + 1;

        // note: max = range

        int[] output = new int[n + 1];
        int[] count = new int[max + 1];

        // initialise count array to 0
        for (int i = 0; i <= max; i++) {
            count[i] = 0;
        }

        // frequency count
        for (int i = low; i <= high; i++) {
            count[arr[i]]++;
        }

        // cumulative count
        for (int i = 1; i <= max; i++) {
            count[i] += count[i - 1];
        }

        // construct output array
        for (int i = high; i >= low; i--) {
            output[count[arr[i]]] = arr[i];
            count[arr[i]] -= 1;
        }

        // copy back to original array
        for (int i = 0; i < n; i++) {
            arr[low + i] = output[i + 1];
        }
    }
}