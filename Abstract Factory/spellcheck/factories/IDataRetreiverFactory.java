package spellcheck.factories;

import spellcheck.DataRetreiver;

import java.io.IOException;

public interface IDataRetreiverFactory {
    public DataRetreiver getDataRetreiver() throws IOException;
}
