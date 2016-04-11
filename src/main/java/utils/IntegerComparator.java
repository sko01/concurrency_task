package utils;

import java.util.Comparator;
import java.util.Map;

public class IntegerComparator implements Comparator<Map.Entry<String, Integer>>{

    public int compare(Map.Entry<String, Integer> o1, Map.Entry<String, Integer> o2) {
        return o2.getValue() - o1.getValue();
    }
}
