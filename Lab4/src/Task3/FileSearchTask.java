package Task3;

import java.util.Set;
import java.util.concurrent.RecursiveTask;

public class FileSearchTask extends RecursiveTask<Set<String>>
{
    private final Document document;

    public FileSearchTask(Document document)
    {
        super();
        this.document = document;
    }

    @Override
    protected Set<String> compute()
    {
        return WordsCounter.getUniqueWords(document);
    }
}
