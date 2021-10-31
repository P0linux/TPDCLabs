package Task1;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.ForkJoinTask;
import java.util.concurrent.RecursiveTask;

public class ForkJoinWordsCounter extends RecursiveTask<HashMap<String, Integer>>
{
    private final List<String> words;
    private final int THRESHOLD = 20000;
    private final HashMap<String, Integer> wordsCounts = new HashMap<>();

    public ForkJoinWordsCounter(List<String> words) {
        this.words = words;
    }

    @Override
    protected HashMap<String, Integer> compute() {
        if (this.words.size() > THRESHOLD) {
            ForkJoinTask.invokeAll(createSubtask()).stream()
                    .map(ForkJoinTask::join)
                    .flatMap(map -> map.entrySet().stream())
                    .forEach(entry -> this.wordsCounts.putIfAbsent(entry.getKey(), entry.getValue()));
            return this.wordsCounts;
        } else {
            return getWordsCount(words);
        }
    }

    private Collection<ForkJoinWordsCounter> createSubtask()
    {
        List<ForkJoinWordsCounter> tasks = new ArrayList<>();

        tasks.add(new ForkJoinWordsCounter(words.subList(0, words.size() / 2)));
        tasks.add(new ForkJoinWordsCounter(words.subList(words.size() / 2, words.size())));

        return tasks;
    }

    private HashMap<String, Integer> getWordsCount(List<String> words)
    {
        for (String word : words) wordsCounts.putIfAbsent(word, word.length());

        return wordsCounts;
    }
}
