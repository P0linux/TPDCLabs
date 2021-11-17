import java.util.concurrent.atomic.AtomicInteger;

public class FailureCounter
{
    private AtomicInteger successCount = new AtomicInteger(0);
    private AtomicInteger failureCount = new AtomicInteger(0);

    public synchronized void defineSuccess(boolean IsSuccess)
    {
        if (IsSuccess) successCount.incrementAndGet();
        else failureCount.incrementAndGet();
    }

    public synchronized double getFailureProbability()
    {
        return failureCount.doubleValue()/(failureCount.doubleValue() + successCount.doubleValue());
    }
}
