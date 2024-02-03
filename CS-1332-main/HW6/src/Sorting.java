import java.util.Comparator;
import java.util.List;
import java.util.LinkedList;
/**
 * Your implementation of various sorting algorithms.
 *
 * @author JhaDeya Rhymes
 * @version 1.0
 * @userid jrhymes7
 * @GTID 903588553
 *
 * Collaborators: LIST ALL COLLABORATORS YOU WORKED WITH HERE
 *
 * Resources: LIST ALL NON-COURSE RESOURCES YOU CONSULTED HERE
 */
public class Sorting {

    /**
     * Implement selection sort.
     *
     * It should be:
     * in-place
     * unstable
     * not adaptive
     *
     * Have a worst case running time of:
     * O(n^2)
     *
     * And a best case running time of:
     * O(n^2)
     *
     * @param <T>        data type to sort
     * @param arr        the array that must be sorted after the method runs
     * @param comparator the Comparator used to compare the data in arr
     * @throws java.lang.IllegalArgumentException if the array or comparator is
     *                                            null
     */
    public static <T> void selectionSort(T[] arr, Comparator<T> comparator) {
        if (arr == null || comparator == null) {
            throw new IllegalArgumentException("Array or comparator is null.");
        }
        for (int i = 0; i < arr.length - 1; i++) {
            int min = i;
            for (int j = i + 1; j < arr.length; j++) {
                if (comparator.compare(arr[j], arr[min]) < 0) {
                    min = j;
                }
            }
            selectionHelper(arr, min, i);
        }
    }
    private static <T> void selectionHelper(T[] arr, int i, int j) {
        T tmp = arr[i];
        arr[i] = arr[j];
        arr[j] = tmp;
    }

    /**
     * Implement insertion sort.
     *
     * It should be:
     * in-place
     * stable
     * adaptive
     *
     * Have a worst case running time of:
     * O(n^2)
     *
     * And a best case running time of:
     * O(n)
     *
     * @param <T>        data type to sort
     * @param arr        the array that must be sorted after the method runs
     * @param comparator the Comparator used to compare the data in arr
     * @throws java.lang.IllegalArgumentException if the array or comparator is
     *                                            null
     */
    public static <T> void insertionSort(T[] arr, Comparator<T> comparator) {
        if (arr == null || comparator == null) {
            throw new IllegalArgumentException("Array or comparator can't be null.");
        }
        for (int i = 1; i < arr.length; i++) {
            T tmp = arr[i];
            int j = i - 1;
            while (j >= 0 && comparator.compare(arr[j], tmp) > 0) {
                arr[j + 1] = arr[j--];
            }
            arr[j + 1] = tmp;
        }
    }

    /**
     * Implement bubble sort.
     *
     * It should be:
     * in-place
     * stable
     * adaptive
     *
     * Have a worst case running time of:
     * O(n^2)
     *
     * And a best case running time of:
     * O(n)
     *
     * NOTE: See pdf for last swapped optimization for bubble sort. You
     * MUST implement bubble sort with this optimization
     *
     * @param <T>        data type to sort
     * @param arr        the array that must be sorted after the method runs
     * @param comparator the Comparator used to compare the data in arr
     * @throws java.lang.IllegalArgumentException if the array or comparator is
     *                                            null
     */
    public static <T> void bubbleSort(T[] arr, Comparator<T> comparator) {
        if (arr == null || comparator == null) {
            throw new IllegalArgumentException("Array or comparator is null.");
        }
        boolean swap = true;
        int j = 1;
        while (swap) {
            swap = false;
            for (int i = 0; i < arr.length - j; i++) {
                if (comparator.compare(arr[i], arr[i + 1]) > 0) {
                    T tmp = arr[i];
                    arr[i] = arr[i + 1];
                    arr[i + 1] = tmp;
                    swap = true;
                }
            }
            j++;
        }
    }


    /**
     * Implement merge sort.
     *
     * It should be:
     * out-of-place
     * stable
     * not adaptive
     *
     * Have a worst case running time of:
     * O(n log n)
     *
     * And a best case running time of:
     * O(n log n)
     *
     * You can create more arrays to run merge sort, but at the end, everything
     * should be merged back into the original T[] which was passed in.
     *
     * When splitting the array, if there is an odd number of elements, put the
     * extra data on the right side.
     *
     * Hint: If two data are equal when merging, think about which subarray
     * you should pull from first
     *
     * @param <T>        data type to sort
     * @param arr        the array to be sorted
     * @param comparator the Comparator used to compare the data in arr
     * @throws java.lang.IllegalArgumentException if the array or comparator is
     *                                            null
     */
    public static <T> void mergeSort(T[] arr, Comparator<T> comparator) {
        if (arr == null || comparator == null) {
            throw new IllegalArgumentException("Array and/or comparator can't be null.");
        }
        if (arr.length > 1) {
            T[] l = (T[]) new Object[arr.length / 2];
            T[] r = (T[]) new Object[arr.length - l.length];
            for (int i = 0; i < l.length; i++) {
                l[i] = arr[i];
            }
            for (int i = 0; i < r.length; i++) {
                r[i] = arr[i + l.length];
            }
            mergeSort(l, comparator);
            mergeSort(r, comparator);
            mergeHelper(arr, comparator, l, r);
        }
    }
    private static <T> void mergeHelper(T[] arr, Comparator<T> comparator, T[] leftArr, T[] rightArr) {
        int i = 0;
        int j = 0;
        for (int n = 0; n < arr.length; n++) {
            if (j >= rightArr.length || (i < leftArr.length
                    && comparator.compare(leftArr[i], rightArr[j]) <= 0)) {
                arr[n] = leftArr[i++];
            } else {
                arr[n] = rightArr[j++];
            }
        }
    }
    /**
     * Implement LSD (least significant digit) radix sort.
     *
     * Make sure you code the algorithm as you have been taught it in class.
     * There are several versions of this algorithm and you may not get full
     * credit if you do not implement the one we have taught you!
     *
     * Remember you CANNOT convert the ints to strings at any point in your
     * code! Doing so may result in a 0 for the implementation.
     *
     * It should be:
     * out-of-place
     * stable
     * not adaptive
     *
     * Have a worst case running time of:
     * O(kn)
     *
     * And a best case running time of:
     * O(kn)
     *
     * You are allowed to make an initial O(n) passthrough of the array to
     * determine the number of iterations you need.
     *
     * At no point should you find yourself needing a way to exponentiate a
     * number; any such method would be non-O(1). Think about how how you can
     * get each power of BASE naturally and efficiently as the algorithm
     * progresses through each digit.
     *
     * Refer to the PDF for more information on LSD Radix Sort.
     *
     * You may use ArrayList or LinkedList if you wish, but it may only be
     * used inside radix sort and any radix sort helpers. Do NOT use these
     * classes with other sorts. However, be sure the List implementation you
     * choose allows for stability while being as efficient as possible.
     *
     * Do NOT use anything from the Math class except Math.abs().
     *
     * @param arr the array to be sorted
     * @throws java.lang.IllegalArgumentException if the array is null
     */
    public static void lsdRadixSort(int[] arr) {
        if (arr == null) {
            throw new IllegalArgumentException("Array is null.");
        }
        LinkedList<Integer>[] bucket = new LinkedList[19];
        for (int i = 0; i < 19; i++) {
            bucket[i] = new LinkedList<>();
        }
        int m = 10;
        int d = 1;
        boolean prog = true;
        while (prog) {
            prog = false;
            for (int n : arr) {
                int b = n / d;
                if (b / 10 != 0) {
                    prog = true;
                }
                if (bucket[b % m + 9] == null) {
                    bucket[b % m + 9] = new LinkedList<>();
                }
                bucket[b % m + 9].add(n);
            }
            int index = 0;
            for (LinkedList<Integer> b : bucket) {
                if (b != null) {
                    for (int n : b) {
                        arr[index++] = n;
                    }
                    b.clear();
                }
            }
            d *= 10;
        }
    }

    /**
     * Implement heap sort.
     *
     * It should be:
     * out-of-place
     * unstable
     * not adaptive
     *
     * Have a worst case running time of:
     * O(n log n)
     *
     * And a best case running time of:
     * O(n log n)
     *
     * Use java.util.PriorityQueue as the heap. Note that in this
     * PriorityQueue implementation, elements are removed from smallest
     * element to largest element.
     *
     * Initialize the PriorityQueue using its build heap constructor (look at
     * the different constructors of java.util.PriorityQueue).
     *
     * Return an int array with a capacity equal to the size of the list. The
     * returned array should have the elements in the list in sorted order.
     *
     * @param data the data to sort
     * @return the array with length equal to the size of the input list that
     * holds the elements from the list is sorted order
     * @throws java.lang.IllegalArgumentException if the data is null
     */
    public static int[] heapSort(List<Integer> data) {
        int i = data.size();
        int[] arr = new int[i];

        for (int a = 0; a < i; a++) {
            arr[a] = data.get(a);
        }
        for (int j = i / 2 - 1; j >= 0; j--) {
            heapHelper(arr, i, j);
        }
        for (int j = i - 1; j >= 0; j--) {
            int temp = arr[0];
            arr[0] = arr[j];
            arr[j] = temp;

            heapHelper(arr, j, 0);
        }
        return arr;
    }
    private static void heapHelper(int[] arr, int n, int i) {
        int largest = i;
        int l = 2 * i + 1;
        int r = 2 * i + 2;

        if (l < n && arr[l] > arr[largest]) {
            largest = l;
        }
        if (r < n && arr[r] > arr[largest]) {
            largest = r;
        }
        if (largest != i) {
            int swap = arr[i];
            arr[i] = arr[largest];
            arr[largest] = swap;

            heapHelper(arr, n, largest);
        }
    }
}