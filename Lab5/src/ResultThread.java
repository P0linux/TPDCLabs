public class ResultThread implements Runnable
{
    private Observer observer;
    private int smoNumber;

    public ResultThread(Observer observer, int smoNumber) {
        this.observer = observer;
        this.smoNumber = smoNumber;
    }

    @Override
    public void run()
    {
        while (!observer.isFinished())
        {
            Result result = observer.getResult();

            if (result != null) System.out.format("SMO %d averageQueueLength = %s, failureProbability = %s\n\r"
                    , smoNumber, result.getQueueLength(), result.getFailureProbability());

            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
