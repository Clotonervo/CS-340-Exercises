
package spellcheck;

import com.google.inject.Inject;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.SortedMap;
import java.util.TreeMap;


public class SpellingChecker {
    private String path;
    private DataRetreiver fetcher;
    private DictionaryInterface dictionary;
    private WordExtractorInterface extractor;

    @Inject
    public SpellingChecker(@GuiceDemo.PathName String path, DataRetreiver fetcher, DictionaryInterface dictionary, WordExtractorInterface extractor) {
        this.path = path;
        this.fetcher = fetcher;
        this.dictionary = dictionary;
        this.extractor = extractor;
    }

    //Only dependencies are from the Interfaces:
	public SortedMap<String, Integer> check() throws IOException {

		// download the document content
		//
		String content = fetcher.getData(path);

		// extract words from the content
		//
		List<String> words = extractor.extract(content);

		// find spelling mistakes
		//
		SortedMap<String, Integer> mistakes = new TreeMap<>();

        for (String word : words) {
            if (!dictionary.isValidWord(word)) {
                if (mistakes.containsKey(word)) {
                    int oldCount = mistakes.get(word);
                    mistakes.put(word, oldCount + 1);
                } else {
                    mistakes.put(word, 1);
                }
            }
        }

		return mistakes;
	}
}

