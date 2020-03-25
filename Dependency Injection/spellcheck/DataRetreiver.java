package spellcheck;

import java.io.IOException;
import java.net.URL;

public interface DataRetreiver {
    String getData(String string) throws IOException;
}
