
package spellcheck;

import spellcheck.factories.*;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.SortedMap;
import java.util.TreeMap;


public class SpellingChecker {

    //Only dependencies are from the Interfaces:
	public SortedMap<String, Integer> check(String path, IDataRetreiverFactory fetcherFactory, IDictionaryFactory dictionaryFactory, IWordExtractorFactory extractorFactory) throws IOException {

        DictionaryInterface dictionary = dictionaryFactory.getDictionary("dict.txt");
        DataRetreiver fetcher = fetcherFactory.getDataRetreiver();
        WordExtractorInterface extractor = extractorFactory.getWordExtractor();

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

