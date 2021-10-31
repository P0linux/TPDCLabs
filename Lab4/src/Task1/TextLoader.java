package Task1;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;

public class TextLoader
{
    public static List<String> getWords(String fileName) throws IOException
    {
        StringBuilder contentBuilder = new StringBuilder();
        try (Stream<String> stream = Files.lines(Paths.get(fileName))) {
            stream.forEach(s -> contentBuilder.append(s).append("\n"));
        }
        return TextLoader.getWordsList(contentBuilder.toString());
    }

    private static List<String> getWordsList(String text)
    {
        return Arrays.asList(text.trim().split("(\\s|\\p{Punct})+"));
    }
}
