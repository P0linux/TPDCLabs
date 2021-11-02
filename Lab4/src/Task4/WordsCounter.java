package Task4;

import java.util.List;
import java.util.concurrent.ForkJoinPool;

public class WordsCounter
{
    private final ForkJoinPool pool =
            new ForkJoinPool(Runtime.getRuntime().availableProcessors());

    public List<String> getFilesWithWords(Folder folder, List<String> searchedWords){
        return pool.invoke(new FolderSearchTask(folder, searchedWords));
    }

    public static Long countOccurrencesInFile(Document document, List<String> words)
    {
        long count = 0;

        for (String line : document.getLinesFromDocument())
        {
            var wordsInLine = line.trim().split("(\\s|\\p{Punct})+");

            for (String word : wordsInLine)
            {
                if (words.contains(word))
                    count = count + 1;
            }
        }

        return count;
    }
}
