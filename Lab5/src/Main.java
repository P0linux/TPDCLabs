import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;

public class Main
{
    private static int smoCount = 10;

    public static void main(String[] args) throws InterruptedException
    {
        ExecutorService smoService = Executors.newFixedThreadPool(smoCount);
        List<Callable<Result>> SMOs = new ArrayList<>();

        for(int i = 0; i < smoCount; i++)
        {
            SMOs.add(new SmoHandlerThread(i + 1));
        }

        List<Future<Result>> futureResults = smoService.invokeAll(SMOs);

        smoService.shutdown();

        List<Result> results = new ArrayList<>();
        for (Future<Result> future : futureResults)
        {
            try {
                Result result = future.get();
                if(result != null) results.add(result);
            } catch (ExecutionException e) {
                e.printStackTrace();
            }
        }

        double averageLengthOfQueue = results.stream()
                .mapToDouble(Result::getQueueLength)
                .average()
                .orElse(0);

        double probabilityOfFailure = results.stream()
                .mapToDouble(Result::getFailureProbability)
                .average()
                .orElse(0);


        System.out.println("Result:  queueLength: " + averageLengthOfQueue);
        System.out.println("Result:  failureProbability : " + probabilityOfFailure);
    }
}
