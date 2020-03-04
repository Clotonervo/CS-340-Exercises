
package spellcheck;

import java.io.IOException;
import java.net.URL;
import java.util.SortedMap;


public class Main {

	public static void main(String[] args) {
	
		try {
			String path = args[0];

			//Create dependencies for injection
			Dictionary dictionary = new Dictionary("dict.txt");
			URLFetcher urlFetcher = new URLFetcher();
			WordExtractor wordExtractor = new WordExtractor();

			//Inject dependencies
			SpellingChecker checker = new SpellingChecker();
			SortedMap<String, Integer> mistakes = checker.check(path, urlFetcher, dictionary, wordExtractor);
			System.out.println(mistakes);
		}
		catch (IOException e) {
			e.printStackTrace();
		}
	}
}

