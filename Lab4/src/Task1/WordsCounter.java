package Task1;

import java.util.HashMap;
import java.util.List;

public class WordsCounter
{
    public static HashMap<String, Integer> getWordsCount(List<String> words)
    {
        HashMap<String, Integer> wordsCounts = new HashMap<>();

        for (String word : words) wordsCounts.putIfAbsent(word, word.length());

        return wordsCounts;
    }
}
