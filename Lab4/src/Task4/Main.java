package Task4;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

public class Main
{
    public static void main(String[] args) throws IOException {
        String folderName = "./Lab4/Resources";

        List<String> words = Arrays.asList("polymorphism", "encapsulation");

        WordsCounter counter = new WordsCounter();
        Folder folder = Folder.getFolderFromDirectory(new File(folderName));

        for (String documentName : counter.getFilesWithWords(folder, words))
            System.out.println(documentName);
    }
}
