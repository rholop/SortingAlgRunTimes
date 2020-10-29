/**
 * @author Fahmida Hamid
 * @author Rowan Holop
 * @version March 29 2020
 * This class implements several known sorting algorithms:
 *  insertionSort
 *  selectionSort
 *  mergeSort
 */

class SortingAlgorithms{
    /**
     * Insertion Sort
     * @param <T>
     * @param a: an array of T type objects
     */

    public static <T extends Comparable <? super T>> void insertionSort(T[] a)
    {
        int n = a.length;
        int i = 1;
        int j;
        while (i < n) 
        {
            j = i;
            while ((j  > 0) && (a[j-1].compareTo(a[j]) > 0)) 
            {
                T temp = a[j-1];
                a[j-1] = a[j];
                a[j] = temp;
                j--;
            }
            i++;
        }
    }

    /**
     * Bubble Sort
     * @param <T>
     * @param a: an array of T type objects
     */

    public static <T extends Comparable <? super T>> void bubbleSort(T[] a)
    {
        int n = a.length;
        boolean swapped = false;
        while (swapped) 
        {
            swapped = false;
            for (int i = 1; i < n; i++) 
            {
                if (a[i-1].compareTo(a[i]) > 0)
                {
                    T temp = a[i-1];
                    a[i-1] = a[i];
                    a[i] = temp;
                    swapped = true;
                }
            }
        }
    }

    /**
     * Selection Sort
     * @param <T>
     * @param a : an array of T type objects 
     */
    public static <T extends Comparable <? super T>> void selectionSort(T[] a)
    {
        for (int i = 0; i < a.length-1; i++)
        {
            int minIndex = i;
            for(int j = i+1; j < a.length; j++)
            {
                if(a[j].compareTo(a[minIndex]) < 0)
                {
                    minIndex = j;
                }
            }
            if(minIndex != i)
            {
                T temp = a[i];
                a[i] = a[minIndex];
                a[minIndex] = temp; 
            }
        }
    }

    /**
     * mergeSort
     * @param <T>
     * @param a : an array of T type objects
     * I took Weiss's implementation of mergeSort
     */
    public static <T extends Comparable <? super T>> void mergeSort(T[] a)
    {

        /* 
         * Creating a temporary array that will be 
         * used to store the the sorted sub-lists.
         */
        T[] temp = (T[]) new Comparable[a.length]; 

        mergeSort(a, temp, 0, a.length-1);

    }

    /**
     * mergeSort
     * @param <T>
     * @param a: given input
     * @param temp: used to copy/store the left and right sublist
     * @param p: left-most index
     * @param r: right-most index
     */
    public static <T extends Comparable <? super T>> void mergeSort(T[] a, T[] temp, int p , int r)
    {
        if(p < r)
        {
            int q = (p+r)/2;
            mergeSort( a, temp, p, q);
            mergeSort( a, temp, q+1, r);
            merge(a, temp, p, q+1, r);
        }
    }

    /**
     * merge: merges the two sorted sublists into one
     * @param <T>
     * @param a
     * @param tempArr
     * @param leftPos
     * @param rightPos
     * @param rightEnd
     */
    public static <T extends Comparable <? super T>> void merge(T[] a, T[] tempArr, int leftPos , int rightPos, int rightEnd)
    {

        int leftEnd = rightPos-1;
        int tempPos = leftPos;
        int numElements = rightEnd - leftPos + 1;

        while( leftPos <= leftEnd && rightPos <= rightEnd)
        {
            if(a[leftPos].compareTo(a[rightPos]) <= 0)

                tempArr[tempPos++] = a[leftPos++]; 
            else
                tempArr[tempPos++] = a[rightPos++];

        }

        /* copy the left over from the left subarray */
        while(leftPos <= leftEnd)
            tempArr[tempPos++] = a[leftPos++]; 

        /* copy the left over from the right subarray */
        while(rightPos <= rightEnd)
            tempArr[tempPos++] = a[rightPos++]; 

        /* copy the sorted data back to the original array */
        for(int i = 0; i < numElements; i++, rightEnd--)
            a[rightEnd] = tempArr[rightEnd];

    }

}

public class DemoSorting {

    public static <T extends Comparable <? super T>> void printData(T[] arr)
    {
        for(int i = 0; i < arr.length; i++)
        {
            System.out.print(arr[i] +  " ");
        }
        System.out.println();
    }

    public static void main(String[] args){
        Integer[] arr = RandomIntegerGenerator.generateRandomNumbers(20);
        //this is your unsorted array
        printData(arr);

        //making a copy (deep copy)
        Integer[] copiedArray1 = new Integer[20];
        System.out.println(arr.length + " this is array length should be 20");
        System.arraycopy(arr, 0, copiedArray1, 0, 20);

        long startTime = System.nanoTime();
        SortingAlgorithms.mergeSort(copiedArray1);
        //now, copiedArray1 has the elements in sorted order
        long stopTime = System.nanoTime();
        double runningTime =  (double)(stopTime - startTime)/1000000000;

        printData(copiedArray1);
        System.out.println("Running time: " + runningTime);
    }
}
