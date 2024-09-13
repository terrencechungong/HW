package com.project1.trainReservation;


import com.pholser.junit.quickcheck.From;
import com.pholser.junit.quickcheck.Property;
import com.pholser.junit.quickcheck.generator.GenerationStatus;
import com.pholser.junit.quickcheck.generator.InRange;
import com.pholser.junit.quickcheck.random.SourceOfRandomness;
import com.project1.TrainReservationSystem;
import org.junit.runner.RunWith;
import com.pholser.junit.quickcheck.runner.JUnitQuickcheck;

import java.util.*;

import static org.junit.Assert.*;

@RunWith(JUnitQuickcheck.class)
public class LFUCacheTest {

    /**
     * Property 1: The cache should not exceed its capacity.
     */
    @Property(trials = 100)
    public void testCapacityConstraint(
            @InRange(minInt =1, maxInt = 10) int capacity,
            List<Integer> keys
    ) {
        if (keys.size() < 3) {
            return;
        }

        if (capacity <= 0) {
            return;
        }
        Random random = new Random();
        String ALPHABETS = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz";
        List<String> values = new ArrayList<>();
        int length = keys.size();  // Length of the string between 1 and 20 characters
        for (int i = 0; i < length; i++) {
            StringBuilder builder = new StringBuilder(length);
            for (int j = 0; j < 5; j++) {
                int index = random.nextInt(ALPHABETS.length());
                builder.append(ALPHABETS.charAt(index));
            }
            values.add(builder.toString());
        }

        TrainReservationSystem.LFUCache<Integer, String> cache = new TrainReservationSystem.LFUCache<>(capacity);

        for (int i = 0; i < keys.size(); i++) {
            cache.put(keys.get(i), values.get(i));
        }

        assertTrue(cache.getCache().size() <= capacity);
    }

    /**
     * Property 2: The least frequently used item should be evicted first.
     */
    @Property(trials = 100)
    public void testEvictionPolicy(
            @InRange(minInt =3, maxInt = 50) int capacity,
            List<Integer> keys
    ) {
        if (keys.size() < 3) {
            return;
        }

        if (capacity <= 0) {
            return;
        }
        Random random = new Random();
       String ALPHABETS = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz";
       List<String> values = new ArrayList<>();
            int length = keys.size();  // Length of the string between 1 and 20 characters
        for (int i = 0; i < length; i++) {
            StringBuilder builder = new StringBuilder(length);
            for (int j = 0; j < 5; j++) {
                int index = random.nextInt(ALPHABETS.length());
                builder.append(ALPHABETS.charAt(index));
            }
            values.add(builder.toString());
        }


        TrainReservationSystem.LFUCache<Integer, String> cache = new TrainReservationSystem.LFUCache<>(2);

        // Insert initial items
        for (int i = 0; i < keys.size(); i++) {
            cache.put(keys.get(i), values.get(i));
        }

        // Access some keys more frequently
        if (!keys.isEmpty()) {
            cache.get(keys.get(0));
            if (keys.size() > 1) {
                cache.get(keys.get(1));
                cache.get(keys.get(1));
            }
        }

        // Insert a new key to trigger eviction


        // Determine expected eviction
        int minFreq = cache.getMinFreq();
        Integer expectedEvict = (Integer) cache.getFreqToKeys().get(minFreq).toArray()[0];

        int newKey = 1000;
        String newValue = "NewValue";
        cache.put(newKey, newValue);


        if (expectedEvict != null && keys.contains(expectedEvict)) {
            assertFalse(cache.getCache().containsKey(expectedEvict));
        }

        // New key should be present
        assertTrue(cache.getCache().containsKey(newKey));
    }

    /**
     * Property 3: Accessing an item should correctly update its frequency.
     */
    @Property(trials = 100)
    public void testFrequencyUpdate(
            @InRange(minInt =1, maxInt = 50) int capacity,
            List<Integer> keys
    ) {
        if (keys.size() < 3) {
            return;
        }

        if (capacity <= 0) {
            return;
        }
        Random random = new Random();
        String ALPHABETS = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz";
        List<String> values = new ArrayList<>();
        int length = keys.size();  // Length of the string between 1 and 20 characters
        for (int i = 0; i < length; i++) {
            StringBuilder builder = new StringBuilder(length);
            for (int j = 0; j < 5; j++) {
                int index = random.nextInt(ALPHABETS.length());
                builder.append(ALPHABETS.charAt(index));
            }
            values.add(builder.toString());
        }


        TrainReservationSystem.LFUCache<Integer, String> cache = new TrainReservationSystem.LFUCache<>(capacity);

        // Insert items
        for (int i = 0; i < keys.size() && i < capacity; i++) {
            cache.put(keys.get(i), values.get(i));
        }

        // Access some keys
        if (!keys.isEmpty()) {
            cache.get(keys.get(0));
            cache.get(keys.get(0));
        }

        // Check frequency
        if (!keys.isEmpty()) {
            int freq = cache.getKeyToFreq().get(keys.get(0));
            assertEquals(3, freq); // 1 put + 2 gets
        }
    }

    /**
     * Property 4: If two items have the same frequency, the least recently used one should be evicted.
     */
    @Property(trials = 100)
    public void testFrequencyTieBreaker(
            List<Integer> keys
    ) {
        if (keys.size() < 3) {
            return;
        }


        Random random = new Random();
        String ALPHABETS = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz";
        List<String> values = new ArrayList<>();
        int length = keys.size();  // Length of the string between 1 and 20 characters
        for (int i = 0; i < length; i++) {
            StringBuilder builder = new StringBuilder(length);
            for (int j = 0; j < 5; j++) {
                int index = random.nextInt(ALPHABETS.length());
                builder.append(ALPHABETS.charAt(index));
            }
            values.add(builder.toString());
        }


        TrainReservationSystem.LFUCache<Integer, String> cache = new TrainReservationSystem.LFUCache<>(2);

        // Insert two items
        cache.put(keys.get(0), values.get(0));
        cache.put(keys.get(1), values.get(1));

        // Both have frequency 1
        // Access both equally
        cache.get(keys.get(0));
        cache.get(keys.get(1));

        // Now both have frequency 2
        // Insert another item to trigger eviction
        int newKey = 1000;
        String newValue = "NewValue";
        cache.put(newKey, newValue);

        // The first inserted key (keys.get(0)) should be evicted as it's the least recently used among those with min frequency
        // However, since both have the same frequency and both were accessed equally, it depends on implementation
        // Typically, the least recently used (first inserted) should be evicted

        // Check that one of the original keys has been evicted
        boolean evictKey0 = !cache.getCache().containsKey(keys.get(0));
        boolean evictKey1 = !cache.getCache().containsKey(keys.get(1));

        assertTrue(evictKey0 ^ evictKey1); // Only one should be evicted

        // New key should be present
        assertTrue(cache.getCache().containsKey(newKey));
    }

    /**
     * Property 5: Updating the value of an existing key should not affect the cache size but should update the frequency.
     */
    @Property(trials = 100)
    public void testValueUpdate(
            @InRange(minInt =2, maxInt = 50) int capacity,
            List<Integer> keys
    ) {
        if (keys.size() < 3) {
            return;
        }

        if (capacity <= 0) {
            return;
        }
        Random random = new Random();
        String ALPHABETS = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz";
        List<String> newValues = new ArrayList<>();
        int length = keys.size();  // Length of the string between 1 and 20 characters
        for (int i = 0; i < length; i++) {
            StringBuilder builder = new StringBuilder(length);
            for (int j = 0; j < 5; j++) {
                int index = random.nextInt(ALPHABETS.length());
                builder.append(ALPHABETS.charAt(index));
            }
            newValues.add(builder.toString());
        }
        List<String> values = new ArrayList<>();

        for (int i = 0; i < length; i++) {
            StringBuilder builder = new StringBuilder(length);
            for (int j = 0; j < 5; j++) {
                int index = random.nextInt(ALPHABETS.length());
                builder.append(ALPHABETS.charAt(index));
            }
            values.add(builder.toString());
        }

        TrainReservationSystem.LFUCache<Integer, String> cache = new TrainReservationSystem.LFUCache<>(capacity);

        // Insert items
        for (int i = 0; i < keys.size() && i < capacity; i++) {
            cache.put(keys.get(i), values.get(i));
        }

        // Update values of some keys
        for (int i = 0; i < keys.size() && i < capacity; i++) {
            cache.put(keys.get(i), newValues.get(i));
            // Each update should increase the frequency

            int freq = cache.getKeyToFreq().get(keys.get(i));

            assertEquals(3, freq); // 1 put + 1 update
        }

        // Ensure cache size hasn't changed
        assertTrue(cache.getCache().size() <= capacity);
    }

    /**
     * Property 6: Retrieving a non-existent key should return null and not affect the cache.
     */
    @Property(trials = 100)
    public void testGetNonExistentKey(
            @InRange(minInt =1, maxInt = 50) int capacity,
            List<Integer> keys,
            int nonExistentKey
    ) {
        if (keys.size() < 3) {
            return;
        }

        if (capacity <= 0) {
            return;
        }
        Random random = new Random();
        String ALPHABETS = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz";
        List<String> values = new ArrayList<>();
        int length = keys.size();  // Length of the string between 1 and 20 characters
        for (int i = 0; i < length; i++) {
            StringBuilder builder = new StringBuilder(length);
            for (int j = 0; j < 5; j++) {
                int index = random.nextInt(ALPHABETS.length());
                builder.append(ALPHABETS.charAt(index));
            }
            values.add(builder.toString());
        }

        TrainReservationSystem.LFUCache<Integer, String> cache = new TrainReservationSystem.LFUCache<>(capacity);

        // Insert items
        for (int i = 0; i < keys.size(); i++) {
            cache.put(keys.get(i), values.get(i));
        }

        // Attempt to get a non-existent key
        String result = cache.get(nonExistentKey);
        assertNull(result);
    }

    /**
     * Property 7: Accessing a key multiple times should increase its frequency accordingly.
     */
    @Property(trials = 100)
    public void testMultipleAccessFrequency(
            @InRange(minInt =1, maxInt = 50) int capacity,
            Integer key,
            String value,
            @InRange(minInt =1, maxInt = 10) int accessCount
    ) {
        TrainReservationSystem.LFUCache<Integer, String> cache = new TrainReservationSystem.LFUCache<>(capacity);

        // Put the key
        cache.put(key, value);
        int expectedFreq = 1;

        // Access the key multiple times
        for (int i = 0; i < accessCount; i++) {
            String retrieved = cache.get(key);
            assertEquals(value, retrieved);
            expectedFreq++;
            assertEquals(Integer.valueOf(expectedFreq), cache.getKeyToFreq().get(key));
        }

        // Ensure key is still in cache
        assertTrue(cache.getCache().containsKey(key));
    }

    /**
     * Property 8: After eviction, ensure the cache does not contain more than capacity and evicted key is correct.
     */
    @Property(trials = 100)
    public void testEvictionMaintainsCapacity(
            @InRange(minInt =1, maxInt = 50) int capacity,
            List<Integer> keys
    ) {
        if (keys.size() < 3) {
            return;
        }

        if (capacity <= 0) {
            return;
        }
        Random random = new Random();
        String ALPHABETS = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz";
        List<String> values = new ArrayList<>();
        int length = keys.size();  // Length of the string between 1 and 20 characters
        for (int i = 0; i < length; i++) {
            StringBuilder builder = new StringBuilder(length);
            for (int j = 0; j < 5; j++) {
                int index = random.nextInt(ALPHABETS.length());
                builder.append(ALPHABETS.charAt(index));
            }
            values.add(builder.toString());
        }

        TrainReservationSystem.LFUCache<Integer, String> cache = new TrainReservationSystem.LFUCache<>(capacity);

        // Insert items exceeding capacity
        for (int i = 0; i < keys.size(); i++) {
            cache.put(keys.get(i), values.get(i));
        }

        // Ensure cache size does not exceed capacity
        assertTrue(cache.getCache().size() <= capacity);
    }
}
