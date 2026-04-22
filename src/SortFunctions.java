import java.util.Random;

/*
* This class acts like the utility class that supports the implementation of the paper as well as the
* performance analysis.
* It has function that generate the input for the experiments
*
* Implementation of Algorithm 2 of the paper- quickSortModified
*
* A method that partitions the input array for the hybrid sort using the median of three partitioning
* as used in the paper.
* swap method - for the algorithm implementation
*
* getMax - gets the maximum element of the array
* getMin - gets the minimum element of the array
*/

public class SortFunctions {

    public static int[] createArr(int n, int r) {
        int[] arr = new int[n];
        Random random = new Random();

        for (int i = 0; i < n; i++) {
            arr[i] = random.nextInt(r );
        }

        return arr;
    }

    public static int[] createSortedArr(int n ) {
        int[] arr = new int[n];

        for(int i = 0; i < n; i++) {
            arr[i] = i;
        }

        return arr;
    }

    public static void quickSortModified(int[] arr, int low, int high, int maxValue, int minValue, int threshold) {
        // loop with the (range + size > threshold) check
        if ((low < high) && (maxValue - minValue + high - low > threshold)) {
            int pivot = partition(arr, low, high);
            int midValue = arr[pivot];

            // recursive call for the left side
            quickSortModified(arr, low, pivot - 1, midValue, minValue, threshold);
            // recursive call for the right side
            quickSortModified(arr, pivot + 1, high, maxValue, midValue, threshold);
        }
    }

    // Median of three partitioning

    public static int partition(int[] arr, int low, int high) {
        int mid = low + (high - low) / 2;

        // Order low, mid, high
        if (arr[mid] < arr[low]) {
            swap(arr, mid, low);
        }
        if (arr[high] < arr[low]) {
            swap(arr, high, low);
        }
        if (arr[high] < arr[mid]) {
            swap(arr, high, mid);
        }

        // Use median as pivot
        // Move median (arr[mid]) to the beginning
        swap(arr, low, mid);

        int pivot = arr[low];

        int i = low;
        int j = high + 1;

        // Standard partitioning
        while (true) {

            // scan from left
            while (i < high && arr[++i] < pivot) {}

            // scan from right
            while (j > low && arr[--j] > pivot) {}

            if (i >= j) break;

            swap(arr, i, j);
        }

        // place pivot in correct position
        swap(arr, low, j);

        return j;
    }

    public static void swap(int[] arr, int i, int j) {
        int temp = arr[i];
        arr[i] = arr[j];
        arr[j] = temp;
    }

    // helper function to get max value of the int[]

    public static int getMax(int[] arr) {
        int max = arr[0];

        for(int i = 0 ; i < arr.length ; i++) {
            if(arr[i] > max){
                max = arr[i];
            }
        }
        return max;
    }

    // helper function to get min value of the int[]

    public static int getMin(int[] arr) {
        int min = arr[0];

        for(int i = 0 ; i < arr.length ; i++) {
            if(arr[i] < min){
                min = arr[i];
            }
        }
        return min;
    }
}

