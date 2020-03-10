package spellcheck.factories;

import spellcheck.WordExtractorInterface;

import java.io.IOException;

public interface IWordExtractorFactory {
    WordExtractorInterface getWordExtractor() throws IOException;
}
