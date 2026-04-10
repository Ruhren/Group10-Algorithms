public class QuickInsertionSort {
    // size at which to switch to insertion sort
    private static final int THRESHOLD = 10;

    public static void sort(int[] arr) {
        if (arr == null || arr.length == 0) return;

        // preprocessing step: modified quicksort to partition the array
        // this will stop partitioning once it hits the THRESHOLD
        SortFunctions.quickSortModified(arr, 0, arr.length - 1, SortFunctions.getMax(arr), SortFunctions.getMin(arr), THRESHOLD);

        // then sort partitions with insertionSort
        insertionSort(arr, 0, arr.length - 1);
    }

    private static void insertionSort(int[] arr, int low, int high) {
        for (int i = low + 1; i <= high; i++) {
            int key = arr[i];
            int j = i - 1;

            while (j >= low && arr[j] > key) {
                arr[j + 1] = arr[j];
                j--;
            }

            arr[j + 1] = key;
        }
    }
}