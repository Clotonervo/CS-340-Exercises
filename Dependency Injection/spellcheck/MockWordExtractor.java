package spellcheck;

import java.util.ArrayList;
import java.util.List;

public class MockWordExtractor implements WordExtractorInterface {
    @Override
    public List<String> extract(String content) {
        List<String> list = new ArrayList<>();
        list.add("this");
        list.add("is");
        list.add("a");
        list.add("test");
        return list;
    }
}
