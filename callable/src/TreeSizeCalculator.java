import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;

public class TreeSizeCalculator {
  private final Node root;
  private final ExecutorService executorService;

  public TreeSizeCalculator(Node root, ExecutorService executorService) {
    this.root = root;
    this.executorService = executorService;
  }

  public int calculateSize() throws Exception {
    if (root == null)
      return 0;

    return calculateSizeRecursive(root);
  }

  private int calculateSizeRecursive(Node node) throws Exception {
    if (node == null) {
      return 0;
    }

    // Submit tasks for left and right subtrees
    Future<Integer> leftFuture = executorService.submit(() -> calculateSizeRecursive(node.left));
    Future<Integer> rightFuture = executorService.submit(() -> calculateSizeRecursive(node.right));

    // Wait for the results and combine them
    int leftSize = leftFuture.get();
    int rightSize = rightFuture.get();

    return 1 + leftSize + rightSize; // Count the current node
  }

}
