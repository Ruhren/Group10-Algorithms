public class QuickCountingSort {
    // combined value range and partition size
    private static final int THRESHOLD = 1000;

    public static void sort(int[] arr) {
        if (arr == null || arr.length == 0) {
            return;
        }

        // find initial min and max values
        int min = arr[0];
        int max = arr[0];

        for (int i = 1; i < arr.length; i++) {
            if (arr[i] < min) {
                min = arr[i];
            }

            if (arr[i] > max) {
                max = arr[i];
            }
        }

        // preprocessing step: modified quicksort
        quickSortModified(arr, 0, arr.length - 1, max, min);
    }

    private static void quickSortModified(int[] arr, int lo, int hi, int max, int min) {
        // switch to counting sort when combined range and partition size reach the threshold
        if ((max - min + (hi - lo)) <= THRESHOLD) {
            countingSort(arr, lo, hi, min, max);
            return;
        }

        if (lo < hi) {
            int pivotIndex = SortFunctions.partitionArr(arr, lo, hi);
            int mid = arr[pivotIndex];

            // narrow the value range
            // left side: all numbers now between min and mid
            quickSortModified(arr, lo, pivotIndex - 1, mid, min);
            // right side: all numbers now between mid and max
            quickSortModified(arr, pivotIndex + 1, hi, max, mid);
        }
    }

    private static void countingSort(int[] arr, int lo, int hi, int min, int max) {
        int n = hi - lo + 1;
        int range = max - min + 1;

        int[] count = new int[range];
        int[] output = new int[n];

        // frequency count with offset
        for (int i = lo; i <= hi; i++) {
            count[arr[i] - min]++;
        }

        // prefix sum
        for (int i = 1; i < range; i++) {
            count[i] += count[i - 1];
        }

        // construct output array
        for (int i = hi; i >= lo; i--) {
            output[count[arr[i] - min] - 1] = arr[i];
            count[arr[i] - min]--;
        }

        // copy back to specific section of the original array
        for (int i = 0; i < n; i++) {
            arr[lo + i] = output[i];
        }
    }
}