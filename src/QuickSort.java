public class QuickSort {
    public static void sort(int[] arr) {
        if (arr == null || arr.length == 0) return;

        quickSort(arr, 0, arr.length - 1);
    }

    private static void quickSort(int[] arr, int low, int high) {
        if (high <= low) {
            return;
        }

        int j = SortFunctions.partition(arr, low, high);
        quickSort(arr, low, j - 1);
        quickSort(arr, j + 1, high);
    }
}