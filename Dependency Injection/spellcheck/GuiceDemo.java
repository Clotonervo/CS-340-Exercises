package spellcheck;

import com.google.inject.AbstractModule;
import com.google.inject.Key;
import com.google.inject.Provides;

import javax.inject.Qualifier;
import java.lang.annotation.Retention;

import static java.lang.annotation.RetentionPolicy.RUNTIME;

public class GuiceDemo {
    @Qualifier
    @Retention(RUNTIME)
    @interface FileName {}

    @Qualifier
    @Retention(RUNTIME)
    @interface PathName {}


    static class SpellCheckerModule extends AbstractModule {
        private String path;

        public SpellCheckerModule(String path){
            this.path = path;
        }

        protected void configure() {
            bind(Key.get(String.class, FileName.class)).toInstance("dict.txt");
            bind(Key.get(String.class, PathName.class)).toInstance(path);
            bind(DictionaryInterface.class).to(Dictionary.class);
            bind(WordExtractorInterface.class).to(WordExtractor.class);
            bind(DataRetreiver.class).to(URLFetcher.class);
        }
    }

    static class SpellCheckerTestModule extends AbstractModule {
        private String path;

        public SpellCheckerTestModule(String path){
            this.path = path;
        }

        protected void configure() {
            bind(Key.get(String.class, FileName.class)).toInstance("testdict.txt");
            bind(Key.get(String.class, PathName.class)).toInstance(path);
            bind(DictionaryInterface.class).to(Dictionary.class);
            bind(WordExtractorInterface.class).to(MockWordExtractor.class);
            bind(DataRetreiver.class).to(MockDataRetriever.class);
        }
    }
}
