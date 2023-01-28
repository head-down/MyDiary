package cqwu.edu.diary.common.utils;

import java.util.HashMap;
import java.util.HashSet;

public class MapUtil {

    public static <K, V> HashMap<K, V> newHashMapWithExpectedSize(int expectedSize) {
        return new HashMap<>(capacity(expectedSize));
    }

    public static <T>HashSet<T> newHashSetWithExpectedSize(int expectedSize){
        return new HashSet<>(capacity(expectedSize));
    }

    /**
     * 根据map中期望的元素个数获取合适的大小
     * @param expectedSize 期望的大小
     * @return 合适的map大小
     */
    public static int capacity(int expectedSize) {
        if (expectedSize < 3) {
            return expectedSize + 1;
        } else {
            return expectedSize < 1073741824 ? (int)((float)expectedSize / 0.75F + 1.0F) : 2147483647;
        }
    }


}
