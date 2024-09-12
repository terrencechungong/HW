package com.project1.trainReservation;

import com.pholser.junit.quickcheck.generator.GenerationStatus;
import com.pholser.junit.quickcheck.generator.Generator;
import com.pholser.junit.quickcheck.random.SourceOfRandomness;
import com.project1.DateRange;
import com.project1.TrainReservationSystem;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class SearchKeyGenerator extends Generator<List<SearchKeyGenerator.SearchKey>> {

    public class SearchKey {
        public final String trainId;
        public final DateRange dateRange;

        public SearchKey(String trainId, DateRange dateRange) {
            this.trainId = trainId;
            this.dateRange = dateRange;
        }
    }

    public SearchKeyGenerator(Class<List<SearchKeyGenerator.SearchKey>> type) {
        super(type);
    }

    @Override
    public List<SearchKey> generate(SourceOfRandomness random, GenerationStatus status) {
        List<SearchKey> keys = new ArrayList<>();
        for (int i = 0; i < 20; i++) {
            String trainId = "Train" + random.nextInt(1, 3);  // Random train ID
            DateRange dateRange = new DateRangeGenerator().generate(random, status);  // Random date range
            keys.add(new SearchKey(trainId, dateRange));
        }
        return keys;
    }
}
