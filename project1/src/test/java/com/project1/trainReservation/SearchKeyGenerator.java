package com.project1.trainReservation;

import com.pholser.junit.quickcheck.generator.GenerationStatus;
import com.pholser.junit.quickcheck.generator.Generator;
import com.pholser.junit.quickcheck.random.SourceOfRandomness;
import com.project1.DateRange;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class SearchKeyGenerator extends Generator<SearchKeyGenerator.SearchKeys> {


    public class SearchKeys {
        public  List<SearchKeyGenerator.SearchKey> searchKeys;

        public SearchKeys(List<SearchKey> keys) {
            this.searchKeys = keys;
        }
    }


    public class SearchKey {
        public final String trainId;
        public final DateRange dateRange;

        public SearchKey(String trainId, DateRange dateRange) {
            this.trainId = trainId;
            this.dateRange = dateRange;
        }
    }

    public SearchKeyGenerator(Class<SearchKeyGenerator.SearchKeys> type) {
        super(type);
    }

    @Override
    public SearchKeys generate(SourceOfRandomness random, GenerationStatus status) {
        List<SearchKey> keys = new ArrayList<>();
        Random rand = new Random();
        for (int i = 0; i < 200; i++) {
            String trainId = "Train" +  rand.nextInt(3) + 1;
            DateRange dateRange = new DateRangeGenerator().generate(random, status);  // Random date range
            keys.add(new SearchKey(trainId, dateRange));
        }
        return new SearchKeys(keys);
    }
}
