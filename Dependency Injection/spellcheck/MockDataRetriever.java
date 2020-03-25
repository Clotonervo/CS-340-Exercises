package spellcheck;


import java.io.IOException;

public class MockDataRetriever implements DataRetreiver {

    @Override
    public String getData(String string) throws IOException {
        return "The brown lazy program jumped over the fox or something like that";
    }
}
