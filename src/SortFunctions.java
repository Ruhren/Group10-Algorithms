import java.util.Random;

public class SortFunctions {

    public static int[] createArr(int n) {
        int[] arr = new int[n];
        Random random = new Random();

        for (int i = 0; i < n; i++) {
            arr[i] = random.nextInt(n + 1);
        }

        return arr;
    }

    public static int[] createArrRange(int n, int r) {
        int[] arr = new int[n];
        Random random = new Random();

        for (int i = 0; i < n; i++) {
            arr[i] = random.nextInt(r + 1);
        }

        return arr;
    }

    private static void swap(int[] a, int i, int j) {
        int temp = a[i];
        a[i] = a[j];
        a[j] = temp;
    }

    public static int partitionArr(int[] a, int lo, int hi) {
        int i = lo;
        int j = hi + 1;
        int pivot = a[lo]; // use first element as pivot

        while (true) {
            // find item on lo to swap
            while (a[++i] <= pivot) {
                if (i == hi) {
                    break;
                }
            }

            // find item on hi to swap
            while (pivot <= a[--j]) {
                if (j == lo) {
                    break;
                }
            }

            // check if pointers cross
            if (i >= j) {
                break;
            }

            swap(a, i, j);
        }
        // put pivot into position
        swap(a, lo, j);

        return j; // return index of item now known to be in place
    }
}

