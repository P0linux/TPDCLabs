public class ConsumerThread implements Runnable
{
    private final static int fixLengthInterval = 10;
    private int taskCount = 0;

    private Observer observer;

    public ConsumerThread(Observer observer) {
        this.observer = observer;
    }

    @Override
    public void run()
    {
        while (!observer.isFinished())
        {
            Integer task = observer.getTask();

            if (task != null)
            {
                taskCount++;
                if (taskCount % fixLengthInterval == 0)
                    observer.fixQueueLength();

                try {
                    Thread.sleep(task.intValue());
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
