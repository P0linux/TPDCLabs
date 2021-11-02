package Task3;

import java.io.File;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.ForkJoinPool;

public class WordsCounter
{
    private final ForkJoinPool pool = new ForkJoinPool(Runtime.getRuntime().availableProcessors());

    public Set<String> getCommonWords(List<Document> documents)
    {
        return pool.invoke(new FileListSearchTask(documents));
    }

    public static Set<String> getUniqueWords(Document document)
    {
        Set<String> allWords = new HashSet<>();

        for (String line : document.getLinesFromDocument())
        {
            var wordsInLine= line.trim().split("(\\s|\\p{Punct})+");
            allWords.addAll(Arrays.asList(wordsInLine));
        }

        return allWords;
    }
}
