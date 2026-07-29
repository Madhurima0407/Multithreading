package MultiThreading.MergeSortRecursive;

import java.util.Arrays;
import java.util.concurrent.ForkJoinPool;

public class MergeSorterDriver {
  public static void main(String[] args) {

    int[] arr = { 8, 3, 7, 4, 9, 2, 6, 5 };

    ForkJoinPool pool = new ForkJoinPool();

    pool.invoke(new MergeSortTask(arr, 0, arr.length - 1));

    System.out.println(Arrays.toString(arr));
  }
}
