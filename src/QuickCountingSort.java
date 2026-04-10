public class QuickCountingSort {

    private static final int THRESHOLD = 1000;

    public static class SortResults {
        public double preprocessingTime = 0;
        public double countingSortTime = 0;
        public double totalTime = 0;
    }

    public static SortResults sortWithTimers(int[] arr) {
        SortResults results = new SortResults();
        if (arr == null || arr.length == 0) return results;

        int min = SortFunctions.getMin(arr);
        int max = SortFunctions.getMax(arr);

        hybridSort(arr, 0, arr.length - 1, max, min, results);

        results.totalTime = results.preprocessingTime + results.countingSortTime;
        return results;
    }

    private static void hybridSort(int[] arr, int low, int high, int maxValue, int minValue, SortResults res) {
        // Base case: range and size is small enough for cache
        if ((maxValue - minValue) + (high - low) <= THRESHOLD) {
            if (low < high) {
                long startCount = System.nanoTime();
                subPartitionCountingSort(arr, low, high, minValue, maxValue);
                long endCount = System.nanoTime();
                res.countingSortTime += (endCount - startCount) / 1_000_000.0;
            }
            return;
        }

        // Preprocessing step: partitioning
        long startPre = System.nanoTime();
        int pivotIndex = SortFunctions.partition(arr, low, high);
        int midValue = arr[pivotIndex];
        long endPre = System.nanoTime();
        res.preprocessingTime += (endPre - startPre) / 1_000_000.0;

        // Recursive calls to left and right sides
        if (low < pivotIndex - 1) {
            hybridSort(arr, low, pivotIndex - 1, midValue, minValue, res);
        }
        if (pivotIndex + 1 < high) {
            hybridSort(arr, pivotIndex + 1, high, maxValue, midValue, res);
        }
    }

    public static void subPartitionCountingSort(int[] arr, int low, int high, int min, int max) {
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