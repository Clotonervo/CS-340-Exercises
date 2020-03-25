
package spellcheck;

import com.google.inject.Inject;

import java.io.File;
import java.io.IOException;
import java.util.Scanner;
import java.util.Set;
import java.util.TreeSet;


public class Dictionary implements DictionaryInterface{

	private Set<String> words;

	@Inject
	public Dictionary(@GuiceDemo.FileName String fileName) throws IOException {
        try (Scanner scanner = new Scanner(new File(fileName))) {
            words = new TreeSet<>();
            while (scanner.hasNextLine()) {
                String word = scanner.nextLine().trim();
                words.add(word);
            }
        }
	}

	public boolean isValidWord(String word) {
		return words.contains(word);
	}
}

