package spellcheck;

import com.google.inject.Guice;
import com.google.inject.Injector;
import org.junit.Assert;
import org.junit.Test;

import java.io.IOException;
import java.util.SortedMap;


public class SpellCheckerTest {
    @Test
    public void testSomething() throws IOException {
        String path = "https://pastebin.com/raw/t6AZ5kx3";

        //Create dependencies for injection
        Injector injector = Guice.createInjector(
                new GuiceDemo.SpellCheckerTestModule(path)
        );

        //Inject dependencies
        SpellingChecker checker = injector.getInstance(SpellingChecker.class);
        SortedMap<String, Integer> mistakes = checker.check();
        System.out.println(mistakes);
        Assert.assertEquals(mistakes.size(), 2);
        Assert.assertNull(mistakes.get("program"));

    }
}