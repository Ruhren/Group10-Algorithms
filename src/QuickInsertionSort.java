import java.util.*;

public class QuickInsertionSort {
    // size at which to switch to insertion sort
    private static final int THRESHOLD = 15;

    public static void sort(int[] arr) {
        quickSort(arr, 0, arr.length - 1);
    }

    private static void quickSort(int[] arr, int lo, int hi) {
        // switch to insertion sort for small partitions
        if (hi - lo + 1 <= THRESHOLD) {
            insertionSort(arr, lo, hi);
            return;
        }

        if (lo < hi) {
            int pivotIndex = SortFunctions.partitionArr(arr, lo, hi);
            quickSort(arr, lo, pivotIndex - 1);
            quickSort(arr, pivotIndex + 1, hi);
        }
    }

    private static void insertionSort(int[] arr, int lo, int hi) {
        for (int i = lo + 1; i <= hi; i++) {
            int key = arr[i];
            int j = i - 1;

            while (j >= lo && arr[j] > key) {
                arr[j + 1] = arr[j];
                j--;
            }

            arr[j + 1] = key;
        }
    }
}