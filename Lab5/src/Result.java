public class Result
{
    private double failureProbability;
    private double queueLength;

    public double getFailureProbability()
    {
        return failureProbability;
    }

    public void setFailureProbability(double probability)
    {
        this.failureProbability = probability;
    }

    public double getQueueLength()
    {
        return queueLength;
    }

    public void setQueueLength(double length)
    {
        this.queueLength = length;
    }
}
