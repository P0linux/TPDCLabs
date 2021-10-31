package Task1;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.ForkJoinPool;

public class Main
{
    public static void main(String[] args) throws IOException
    {
        List<String> words = TextLoader.getWords("./Lab4/Resources/text.txt");

        System.out.printf("Number of words: %d\n\n", words.size());

        long currentTime = System.nanoTime();
        ForkJoinPool pool = new ForkJoinPool(Runtime.getRuntime().availableProcessors());
        HashMap<String, Integer> wordsCounts = pool.submit(new ForkJoinWordsCounter(words)).join();
        long currentTimeForkJoin = System.nanoTime() - currentTime;

        System.out.printf("ForkJoinPool execution time:  %d\n", currentTimeForkJoin/1_000_000);
        System.out.printf("Number of unique words: %d\n", wordsCounts.keySet().size());
        System.out.println();

        GetStatistic(wordsCounts);

        currentTime = System.nanoTime();
        wordsCounts = WordsCounter.getWordsCount(words);
        long currentTimeSimple = System.nanoTime() - currentTime;

        System.out.printf("Execution time (Single Thread): %d\n", currentTimeSimple/1_000_000);

        System.out.printf("SpeadUp = %.2f\n", (double) currentTimeSimple / currentTimeForkJoin);
    }

    private static void GetStatistic(HashMap<String, Integer> wordsCounts)
    {
        var mean = wordsCounts.values().stream().mapToDouble(i -> i).sum()
                / wordsCounts.values().stream().mapToDouble(i -> i).count();

        var variance = (wordsCounts.values().stream().mapToDouble(i -> Math.pow(i, 2)).sum()
                / wordsCounts.values().stream().mapToDouble(i -> i).count())
                - Math.pow(wordsCounts.values().stream().mapToDouble(i -> i).sum()
                        / wordsCounts.values().stream().mapToDouble(i -> i).count(), 2);

        System.out.printf("Mean length: %f\n", mean);
        System.out.printf("Variance length: %f\n", variance);
        System.out.println();
    }
}
