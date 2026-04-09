public class QuickCountingSort {

    private static final int THRESHOLD = 1000;

    public static void sort(int[] arr, int max, int min) {
        if (arr == null || arr.length == 0) return;

        hybridSort(arr, 0, arr.length - 1, min, max);
    }

    private static void hybridSort(int[] arr, int low, int high, int min, int max) {

        if (low >= high) return;

        int size = high - low + 1;

        if ((max - min) + size <= THRESHOLD) {
            // Recompute real min/max for this partition
            int localMin = arr[low];
            int localMax = arr[low];

            for (int i = low + 1; i <= high; i++) {
                if (arr[i] < localMin) localMin = arr[i];
                if (arr[i] > localMax) localMax = arr[i];
            }

            countingSort(arr, low, high, localMin, localMax);
            return;
        }

        int pivot = SortFunctions.partition(arr, low, high);
        int midValue = arr[pivot];

        // Recurse on left and right
        hybridSort(arr, low, pivot - 1, min, midValue);
        hybridSort(arr, pivot + 1, high, midValue, max);
    }

    public static void countingSort(int[] arr, int low, int high, int min, int max) {
        int n = high - low + 1;
        int range = max - min + 1;

        int[] output = new int[n + 1];
        int[] count = new int[range];

        // Frequency count
        for (int i = low; i <= high; i++) {
            count[arr[i] - min]++;
        }

        // Cumulative count (prefix sums)
        for (int i = 1; i < range; i++) {
            count[i] += count[i - 1];
        }

        // Build output array
        for (int i = high; i >= low; i--) {
            output[count[arr[i] - min]] = arr[i];
            count[arr[i] - min]--;
        }

        // Copy back to original array
        for (int i = 0; i < n; i++) {
            arr[low + i] = output[i + 1];
        }
    }
}