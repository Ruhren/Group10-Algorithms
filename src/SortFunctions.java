import java.util.Random;

public class SortFunctions {

    public static int[] createArr(int n, int r) {
        int[] arr = new int[n];
        Random random = new Random();

        for (int i = 0; i < n; i++) {
            arr[i] = random.nextInt(r );
        }

        return arr;
    }

    public static int[] createSortedArr(int n ){
        int[] arr = new int[n];

        for(int i = 0; i < n; i++){
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
    //median-of-three partioning

    public static int partition(int[] arr, int low, int high) {
        int i = low;
        int j = high + 1;
        int pivot = arr[low]; // use first element as pivot

        while (true) {
            // find item on low to swap
            while (i < high && arr[++i] <= pivot) {
                if (i == high) {
                    break;
                }
            }

            // find item on high to swap
            while (j > low && pivot <= arr[--j]) {
                if (j == low) {
                    break;
                }
            }

            // check if pointers cross
            if (i >= j) {
                break;
            }

            swap(arr, i, j);
        }
        // put pivot into position
        swap(arr, low, j);

        return j; // return index of item now known to be in place
    }

//    public static int partition(int[] arr, int low, int high) {
//        int i = low;
//        int j = high + 1;
//        int pivot = arr[low]; // use first element as pivot
//
//        while (true) {
//            // find item on low to swap
//            while (i < high && arr[++i] <= pivot) {
//                if (i == high) {
//                    break;
//                }
//            }
//
//            // find item on high to swap
//            while (j > low && pivot <= arr[--j]) {
//                if (j == low) {
//                    break;
//                }
//            }
//
//            // check if pointers cross
//            if (i >= j) {
//                break;
//            }
//
//            swap(arr, i, j);
//        }
//        // put pivot into position
//        swap(arr, low, j);
//
//        return j; // return index of item now known to be in place
//    }

    private static void swap(int[] arr, int i, int j) {
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

