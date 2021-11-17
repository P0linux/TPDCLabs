import java.util.ArrayList;
import java.util.List;

public class LengthCounter
{
    private List<Integer> lenths = new ArrayList<>();

    public synchronized void addLength(int length)
    {
        lenths.add(length);
    }

    public synchronized double getAverageLength()
    {
        return lenths.stream().mapToDouble(Integer::doubleValue).sum() / lenths.size();
    }
}
