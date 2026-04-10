public class CountingSort {

    public static void sort(int[] array) {
        if (array == null || array.length == 0) return;

        countingSort(array, array.length);
    }

    private static void countingSort(int[] arr, int n) {
        int[] output = new int[n];

        int r = SortFunctions.getMax(arr);

        int[] count = new int[r + 1];

        // Frequency count
        for (int i = 0; i < n; i++) {
            count[arr[i]]++;
        }

        // Prefix sum
        for (int i = 1; i <= r; i++) {
            count[i] += count[i - 1];
        }

        // Build output array
        for (int i = n - 1; i >= 0; i--) {
            output[count[arr[i]] - 1] = arr[i];
            count[arr[i]] -= 1;
        }

        // Copy back to original array
        for (int i = 0; i < n; i++) {
            arr[i] = output[i];
        }
    }
}