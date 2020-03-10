package spellcheck.factories;

import spellcheck.Dictionary;
import spellcheck.DictionaryInterface;

import java.io.IOException;

public class DictionaryFactory implements IDictionaryFactory {
    public DictionaryFactory() {}

    @Override
    public DictionaryInterface getDictionary(String path) throws IOException {
        return new Dictionary(path);
    }
}
