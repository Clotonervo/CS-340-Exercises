package spellcheck.factories;

import spellcheck.DataRetreiver;
import spellcheck.Dictionary;
import spellcheck.URLFetcher;

import java.io.IOException;

public class DataRetreiverFactory implements IDataRetreiverFactory {

    public DataRetreiverFactory() {}

    @Override
    public DataRetreiver getDataRetreiver() throws IOException {
        return new URLFetcher();
    }
}
