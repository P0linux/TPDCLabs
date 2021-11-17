import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

public class Observer
{
    private BlockingQueue<Integer> queue;
    private FailureCounter failureCounter;
    private LengthCounter lengthCounter;

    private boolean isFinished = false;

    public Observer(int queueCapacity)
    {
        queue = new ArrayBlockingQueue<Integer>(queueCapacity);
        failureCounter = new FailureCounter();
        lengthCounter = new LengthCounter();
    }

    public synchronized void addTask(Integer task)
    {
        boolean result = queue.offer(task);
        failureCounter.defineSuccess(result);
    }

    public synchronized void fixQueueLength()
    {
        this.lengthCounter.addLength(this.queue.size());
    }

    public synchronized Integer getTask()
    {
        return queue.poll();
    }

    public synchronized Result getResult()
    {
        Result result = new Result();

        result.setQueueLength(lengthCounter.getAverageLength());
        result.setFailureProbability(failureCounter.getFailureProbability());

        return result;
    }

    public synchronized boolean isFinished()
    {
        return isFinished && queue.isEmpty();
    }

    public synchronized void setFinished()
    {
        isFinished = true;
    }
}
