package MultiThreading.MergeSortRecursive;

import java.util.Arrays;
import java.util.concurrent.RecursiveAction;

public class MergeSortTask extends RecursiveAction {

  private static final int THRESHOLD = 1000; // Threshold for switching to sequential sort

  private int[] arr;
  private int left;
  private int right;

  public MergeSortTask(int[] arr, int left, int right) {
    this.arr = arr;
    this.left = left;
    this.right = right;
  }

  @Override
  protected void compute() {
    if (left >= right) {
      return;
    }

    // For small arrays, sort sequentially
    if (right - left < THRESHOLD) {
      Arrays.sort(arr, left, right + 1);
      return;
    }

    int mid = left + (right - left) / 2;
    MergeSortTask leftTask = new MergeSortTask(arr, left, mid);
    MergeSortTask rightTask = new MergeSortTask(arr, mid + 1, right);
    invokeAll(leftTask, rightTask); // Executes multiple ForkJoinTasks in parallel, Waits until all tasks
                                    // complete.Internally performs the equivalent of fork() followed by join().
    merge(left, mid, right);
  }

  private void merge(int left, int mid, int right) {
    int[] temp = new int[right - left + 1];
    int i = left, j = mid + 1, k = 0;

    while (i <= mid && j <= right) {
      if (arr[i] <= arr[j]) {
        temp[k++] = arr[i++];
      } else {
        temp[k++] = arr[j++];
      }
    }

    while (i <= mid) {
      temp[k++] = arr[i++];
    }

    while (j <= right) {
      temp[k++] = arr[j++];
    }

    System.arraycopy(temp, 0, arr, left, temp.length);
  }

}
