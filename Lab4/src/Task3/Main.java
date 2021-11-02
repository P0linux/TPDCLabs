package Task3;

import java.io.File;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

public class Main
{
    public static void main(String[] args) throws IOException
    {
        String firstFileName = "./Lab4/Resources/common1.txt";
        String secondFileName = "./Lab4/Resources/common2.txt";

        File firstFile = new File(firstFileName);
        File secondFile = new File(secondFileName);

        List<Document> documents = new LinkedList<>();
        documents.add(Document.getDocumentFromFile(firstFile));
        documents.add(Document.getDocumentFromFile(secondFile));

        WordsCounter counter = new WordsCounter();

        System.out.println(counter.getCommonWords(documents));
    }
}
