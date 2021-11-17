import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class SmoHandlerThread implements Callable<Result>
{
    private final static int queueCapacity = 10;
    private final static int deviceCount = 10;
    private final static int secondsToWait = 180;

    private int smoNumber;

    public SmoHandlerThread(int smoNumber) {
        this.smoNumber = smoNumber;
    }

    @Override
    public Result call() throws Exception
    {
        Observer observer = new Observer(queueCapacity);

        ExecutorService taskService = Executors.newFixedThreadPool(deviceCount + 1);
        ExecutorService resultService = Executors.newSingleThreadExecutor();

        resultService.submit(new ResultThread(observer, smoNumber));
        taskService.submit(new ProducerThread(observer));

        for (int i = 0; i < deviceCount; i++)
            taskService.submit(new ConsumerThread(observer));

        resultService.shutdown();
        taskService.shutdown();

        try {
            taskService.awaitTermination(secondsToWait, TimeUnit.SECONDS);
        } catch (InterruptedException ex) {
            System.err.println(ex.getMessage());
        }
        resultService.shutdownNow();
        taskService.shutdownNow();

        return observer.getResult();
    }
}
