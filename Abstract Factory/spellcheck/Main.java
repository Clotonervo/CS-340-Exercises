
package spellcheck;

import spellcheck.factories.*;

import java.io.IOException;
import java.net.URL;
import java.util.SortedMap;
import java.util.TreeMap;


public class Main {

	public static void main(String[] args) {
		IDataRetreiverFactory dataRetreiverFactory = new DataRetreiverFactory();
		IDictionaryFactory dictionaryFactory = new DictionaryFactory();
		IWordExtractorFactory wordExtractoryFactory = new WordExtractoryFactory();
	
		try {
			String path = args[0];

			// Pass in factories as abstract classes
			SpellingChecker checker = new SpellingChecker();
			SortedMap<String, Integer> mistakes = checker.check(path, dataRetreiverFactory, dictionaryFactory, wordExtractoryFactory);
			System.out.println(mistakes);

			SortedMap<String, Integer> expected = new TreeMap<>();

			//Unit tests
			expected.put("be", 1);
			expected.put("doesn", 1);
			expected.put("funtion", 1);
			expected.put("t", 2);
			assert expected.equals(mistakes);
		}
		catch (IOException e) {
			e.printStackTrace();
		}
	}
}

