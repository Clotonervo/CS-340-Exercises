package spellcheck.factories;

import spellcheck.WordExtractor;
import spellcheck.WordExtractorInterface;

import java.io.IOException;

public class WordExtractoryFactory implements IWordExtractorFactory {
    public WordExtractoryFactory() {}

    @Override
    public WordExtractorInterface getWordExtractor() throws IOException {
        return new WordExtractor();
    }
}
