public class QuickSort {
    public static void sort(int[] arr) {
        if (arr == null || arr.length <= 1) {
            return;
        }
        sortArr(arr, 0, arr.length - 1);
    }

    private static void sortArr(int[] a, int lo, int hi) {
        if (hi <= lo) {
            return;
        }

        int j = SortFunctions.partitionArr(a, lo, hi);
        sortArr(a, lo, j - 1);
        sortArr(a, j + 1, hi);
    }
}