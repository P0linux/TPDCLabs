import java.util.Random;

public class ProducerThread implements Runnable
{
    private Random random = new Random();
    private Observer observer;

    private static final int nextTaskWaitTime = 10;
    private static final int probabilityWeight = 5;
    private static final int taskCount = 1000;
    private static final int maxTaskTime = 500;

    public ProducerThread(Observer observer)
    {
        this.observer = observer;
    }

    @Override
    public void run()
    {
        for (int i = 0; i < taskCount; i++)
        {
            int taskTime = random.nextInt(maxTaskTime);
            observer.addTask(taskTime);

            try {
                int additionalTime = random.nextInt(probabilityWeight);
                Thread.sleep(nextTaskWaitTime + additionalTime);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        observer.setFinished();
    }
}
