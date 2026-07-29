package MultiThreading.QuickSort;

import java.util.Arrays;

public class QuickSorterClient {

  public static void main(String[] args) {
    int[] arr = { 7, 3, 4, 1, 9, 8, 2, 6 };
    QuickSorter quickSorter = new QuickSorter(arr, 0, arr.length - 1);
    quickSorter.invoke();
    System.out.println(Arrays.toString(arr));
  }
}
