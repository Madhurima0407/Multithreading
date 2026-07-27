import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Driver {
  public static void main(String[] args) {
    // Create a sample binary tree
    Node root = new Node(1);
    root.left = new Node(2);
    root.right = new Node(3);
    root.left.left = new Node(4);
    root.left.right = new Node(5);
    root.right.left = new Node(6);
    root.right.right = new Node(7);

    // Create an ExecutorService that can grow as needed for recursive tasks
    ExecutorService executorService = Executors.newCachedThreadPool();

    // Create a TreeSizeCalculator instance
    TreeSizeCalculator calculator = new TreeSizeCalculator(root, executorService);

    try {
      // Calculate the size of the tree
      int size = calculator.calculateSize();
      System.out.println("Size of the tree: " + size);
    } catch (Exception e) {
      e.printStackTrace();
    } finally {
      // Shutdown the ExecutorService
      executorService.shutdown();
    }
  }
}
