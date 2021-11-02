package Task4;

import java.util.Collections;
import java.util.List;
import java.util.concurrent.RecursiveTask;

public class FileSearchTask extends RecursiveTask<List<String>>
{
    private final Document document;
    private final List<String> words;

    public FileSearchTask(Document document, List<String> words)
    {
        super();
        this.document = document;
        this.words = words;
    }

    @Override
    protected List<String> compute()
    {
        return WordsCounter.countOccurrencesInFile(document, words) == 0
                ? null
                : Collections.singletonList(document.fileName);
    }
}
