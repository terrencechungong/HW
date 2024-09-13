package com.project1;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.LinkedHashMap;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.Objects;

public class TrainReservationSystem {
    private final Map<String, List<Reservation>> trainReservations;
    private final LFUCache<SearchKey, List<Reservation>> searchCache;

    public TrainReservationSystem(int cacheCapacity) {
        this.trainReservations = new LinkedHashMap<>();
        this.searchCache = new LFUCache<>(cacheCapacity);
    }

    /**
     * Reserve a seat on a train under a passenger on a specific date
     * @param trainId the train on which the seat will be reserved
     * @param passengerName the name the reservation is under
     * @param seatNumber on the train
     * @param date of reservation
     * @return true if the reservation succeeded, false if seat is already reserved
     */
    public boolean reserveSeat(String trainId, String passengerName, int seatNumber, LocalDate date) {
        List<Reservation> reservations = trainReservations.computeIfAbsent(trainId, k -> new ArrayList<>());

        for (Reservation reservation : reservations) {
            // seat is already reserved
            if (reservation.seatNumber == seatNumber && reservation.date.equals(date)) {
                return false;
            }
        }

        Reservation newReservation = new Reservation(passengerName, seatNumber, date);
        reservations.add(newReservation);
        trainReservations.put(trainId, reservations);
        updateCache(newReservation);
        return true;
    }

    private void updateCache(Reservation newReservation) {
        for (SearchKey key : searchCache.cache.keySet()) {
            if (newReservation.date.isAfter(key.dateRange.getStartDate()) && newReservation.date.isBefore(key.dateRange.getEndDate())) {
                List<Reservation> copy = new ArrayList<>(searchCache.cache.get(key));
                copy.add(newReservation);
                searchCache.put(key, copy);
            }
        }
    }


    /**
     * @param trainId to search on for reserved seats
     * @param dateRange date range is inclusive of start and end dates
     * @return list of reservations on a given train in a date range
     */
    public List<Reservation> searchReservations(String trainId, DateRange dateRange) {
        SearchKey key = new SearchKey(trainId, dateRange);

        if (searchCache.get(key) != null) {
            return searchCache.get(key);
        }

        List<Reservation> result = new ArrayList<>();
        List<Reservation> reservations = trainReservations.get(trainId);

        if (reservations != null) {
            for (Reservation reservation : reservations) {
                // if date is within the range, add reservation to result
                if (reservation.date.isAfter(dateRange.getStartDate()) && reservation.date.isBefore(dateRange.getEndDate())) {
                    result.add(reservation);
                }
            }
        }

        searchCache.put(key, result);

        return result;
    }

    public static class Reservation {
        String passengerName;
        int seatNumber;
        LocalDate date;

        public Reservation(String passengerName, int seatNumber, LocalDate date) {
            this.passengerName = passengerName;
            this.seatNumber = seatNumber;
            this.date = date;
        }

        @Override
        public String toString() {
            return "Reservation{" +
                    "passengerName='" + passengerName + '\'' +
                    ", seatNumber=" + seatNumber +
                    ", date=" + date +
                    '}';
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            Reservation that = (Reservation) o;
            return seatNumber == that.seatNumber &&
                    passengerName.equals(that.passengerName) &&
                    date.equals(that.date);
        }
        @Override
        public int hashCode() {
            return Objects.hash(passengerName, seatNumber, date);
        }
    }

    private static class SearchKey {
        private final String trainId;
        private final DateRange dateRange;

        public SearchKey(String trainId, DateRange dateRange) {
            this.trainId = trainId;
            this.dateRange = dateRange;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            SearchKey searchKey = (SearchKey) o;
            return trainId.equals(searchKey.trainId) && dateRange.equals(searchKey.dateRange);
        }

        @Override
        public int hashCode() {
            return Objects.hash(trainId, dateRange);
        }
    }

    public static class LFUCache<K, V> {
        private final int capacity;
        private int minFreq;
        final Map<K, V> cache;
        final Map<K, Integer> keyToFreq;
        private final Map<Integer, LinkedHashSet<K>> freqToKeys;

        /**
         * cache maps keys to their values
         * keyToFreq maps a key to the number of times it is retrieved
         * freqToKeys maps a retrieval frequency x to keys that have been retrieved x times
         * This is improves cache eviction efficiency since we need to remove the 
         * least retrieved, oldest key
         * @param capacity
         */
        public LFUCache(int capacity) {
            this.capacity = capacity;
            this.minFreq = 0;
            this.cache = new HashMap<>();
            this.keyToFreq = new HashMap<>();
            this.freqToKeys = new HashMap<>();
        }

        /**
         * Retrieves value corresponding to key in LFU cache and increments
         * the retrieval frequency by 1 for that key
         * @param key to be searched to get corresponding value
         * @return value corresponding to key
         */
        public V get(K key) {
            if (!cache.containsKey(key)) return null;

            int freq = keyToFreq.get(key);
            //update frequency for that key
            keyToFreq.put(key, freq + 1);

            freqToKeys.get(freq).remove(key);
            //if the set corresponding to the new frequency doesn't exist, create it then add the key
            freqToKeys.computeIfAbsent(freq + 1, k -> new LinkedHashSet<>()).add(key);

            if (freqToKeys.get(freq).isEmpty()) {
                freqToKeys.remove(freq);
                if (freq == minFreq) {
                    minFreq += 1;
                }
            }

            return cache.get(key);
        }

        /**
         * Puts a new key value pair in the cache or updates existing key value pair,
         * evicting a key if cache is at capacity before putting
         * @param key the key to be added/updated
         * @param value corresponding value
         */
        public void put(K key, V value) {
            if (capacity <= 0) return;

            if (cache.containsKey(key)) {
                //no need to update existing value if it's the same
                if (get(key).equals(value)) {
                    return;
                }
                cache.put(key, value);
                
                //update frequency since key was accessed
                int freq = keyToFreq.get(key);
                keyToFreq.put(key, freq + 1);
                freqToKeys.get(freq).remove(key);
                freqToKeys.computeIfAbsent(freq + 1, k -> new LinkedHashSet<>()).add(key);
                if (freqToKeys.get(freq).isEmpty()) {
                    freqToKeys.remove(freq);
                    if (freq == minFreq) {
                        minFreq += 1;
                    }
                }
                return;
            }

            if (cache.size() == capacity) {
                // get first key in the set corresponding the minFreq (the least recently added)
                K evict = freqToKeys.get(minFreq).iterator().next();
                // remove the key from above and the corresponding set if it is now empty
                freqToKeys.get(minFreq).remove(evict);
                if (freqToKeys.get(minFreq).isEmpty()) {
                    freqToKeys.remove(minFreq);
                }
                cache.remove(evict);
                keyToFreq.remove(evict);

            }
            // insert new key and corresponding frequency of 1
            cache.put(key, value);
            keyToFreq.put(key, 1);
            minFreq = 1;
            //if the set corresponding to frequency 1 doesn't exist, create it then add the key
            freqToKeys.computeIfAbsent(1, k -> new LinkedHashSet<>()).add(key);
        }

        public int getCapacity() {
            return capacity;
        }

        public int getMinFreq() {
            return minFreq;
        }

        public Map<K, V> getCache() {
            return cache;
        }

        public Map<K, Integer> getKeyToFreq() {
            return keyToFreq;
        }

        public Map<Integer, LinkedHashSet<K>> getFreqToKeys() {
            return freqToKeys;
        }
    }
}
