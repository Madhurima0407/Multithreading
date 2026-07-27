import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class MergeSorterClient {
  public static void main(String[] args) throws InterruptedException, ExecutionException {
        ExecutorService executorService = Executors.newCachedThreadPool();
        //NumberGenerator numberGenerator = new NumberGenerator(10);
        //executorService.submit(numberGenerator);
        
     
        List<Integer> list = List.of(7, 3, 4, 1, 9, 8, 2, 6);
        long startTime = System.currentTimeMillis(); 
        System.out.println("Start:" + startTime);
        MergeSorter mergeSorter = new MergeSorter(list, executorService);
        Future<List<Integer>> outputFuture  =  executorService.submit(mergeSorter); // new thread.

        System.out.println("output: " + outputFuture.get()); // main thread. -- WAIT

        
        
        //System.out.println("Result: " + response.get());
        long endTime = System.currentTimeMillis(); 
        System.out.println("End:" + endTime);
        System.out.println("Total time:" + (endTime - startTime));
        executorService.shutdown();
    }

}
