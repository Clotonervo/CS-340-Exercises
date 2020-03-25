
package spellcheck;

import com.google.inject.Guice;
import com.google.inject.Injector;

import java.io.IOException;
import java.net.URL;
import java.util.SortedMap;


public class Main {

	public static void main(String[] args) {
	
		try {
			String path = args[0];

			//Create dependencies for injection
			Injector injector = Guice.createInjector(
					new GuiceDemo.SpellCheckerModule(path)
					);

			//Inject dependencies
			SpellingChecker checker = injector.getInstance(SpellingChecker.class);
			SortedMap<String, Integer> mistakes = checker.check();
			System.out.println(mistakes);
		}
		catch (IOException e) {
			e.printStackTrace();
		}
	}
}

