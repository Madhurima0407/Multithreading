package MultiThreading.QuickSort;

import java.util.concurrent.RecursiveAction;

public class QuickSorter extends RecursiveAction {

  private int[] arr;
  private int low;
  private int high;

  public QuickSorter(int[] arr, int low, int high) {
    this.arr = arr;
    this.low = low;
    this.high = high;
  }

  @Override
  protected void compute() {
    if (low >= high) {
      return;
    }

    int pivot = partition(arr, low, high);
    QuickSorter leftSorter = new QuickSorter(arr, low, pivot - 1);
    QuickSorter rightSorter = new QuickSorter(arr, pivot + 1, high);
    invokeAll(leftSorter, rightSorter);
  }

  public static int partition(int[] arr, int low, int high) {
    int pivot = arr[low];
    int left = low + 1, right = high;
    while (left <= right) {
      if (arr[left] <= pivot) {
        left++;
      } else if (arr[right] > pivot) {
        right--;
      } else {
        swap(arr, left, right);
      }
    }

    swap(arr, low, right);
    return right;
  }

  public static void swap(int[] arr, int i, int j) {
    int temp = arr[i];
    arr[i] = arr[j];
    arr[j] = temp;
  }

}
