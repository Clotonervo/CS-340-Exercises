package spellcheck.factories;

import spellcheck.DictionaryInterface;

import java.io.IOException;

public interface IDictionaryFactory {
    DictionaryInterface getDictionary(String path) throws IOException;
}
