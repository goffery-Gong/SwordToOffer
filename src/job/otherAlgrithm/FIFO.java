package job.otherAlgrithm;

import java.util.LinkedHashMap;
import java.util.Map.Entry;

/**
 * FIFO算法的java实现
 * 本质上还是使用LinkedHashMap，将AccessOrder置位false
 *
 * @author LingLong.gzw
 * @create 2019-08-31
 */
public class FIFO<K, V> {
    private final int MAX_CACHE_SIZE;
    private final float DEFAULT_LOAD_FACTOR = 0.75f;
    LinkedHashMap<K, V> map;

    public FIFO(int cacheSize) {
        MAX_CACHE_SIZE = cacheSize;
        int capacity = (int)Math.ceil(MAX_CACHE_SIZE / DEFAULT_LOAD_FACTOR) + 1;
        //第三个参数设置为true，代表linkedlist按访问顺序排序，可作为LRU缓存
        //第三个参数设置为false，代表按插入顺序排序，可作为FIFO缓存
        map = new LinkedHashMap<K, V>(capacity, DEFAULT_LOAD_FACTOR, false) {
            @Override
            protected boolean removeEldestEntry(Entry eldest) {
                return size() > MAX_CACHE_SIZE;
            }
        };
    }

    public synchronized void put(K key, V value){
        map.put(key, value);
    }

    public synchronized V get(K key){
        return map.get(key);
    }

    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        for (Entry<K, V> entry : map.entrySet()) {
            stringBuilder.append(String.format("%s: %s  ", entry.getKey(), entry.getValue()));
        }
        return stringBuilder.toString();
    }

    public static void main(String[] args) {
        FIFO<Integer, Integer> lru1 = new FIFO<>(5);
        lru1.put(1, 1);
        lru1.put(2, 2);
        lru1.put(3, 3);
        System.out.println(lru1);
        lru1.get(1);
        System.out.println(lru1);
        lru1.put(4, 4);
        lru1.put(5, 5);
        lru1.put(6, 6);
        System.out.println(lru1);
    }
}
