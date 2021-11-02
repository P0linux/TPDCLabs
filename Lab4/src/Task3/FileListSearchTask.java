package Task3;

import java.io.File;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import java.util.concurrent.RecursiveTask;

public class FileListSearchTask extends RecursiveTask<Set<String>>
{
    private final List<Document> files;

    public FileListSearchTask(List<Document> files)
    {
        super();
        this.files = files;
    }

    @Override
    protected Set<String> compute()
    {
        Set<String> result;
        List<RecursiveTask<Set<String>>> forks = new LinkedList<>();

        for (var file : files)
        {
            FileSearchTask task = new FileSearchTask(file);
            forks.add(task);
            task.fork();
        }

        if (forks.size() == 0) return new HashSet<>();

        result = forks.get(0).join();
        forks.remove(0);
        for (RecursiveTask<Set<String>> task : forks) {
            result.retainAll(task.join());
        }

        return result;
    }
}
