package com.project1.trainReservation;

import com.pholser.junit.quickcheck.generator.GenerationStatus;
import com.pholser.junit.quickcheck.generator.Generator;
import com.pholser.junit.quickcheck.random.SourceOfRandomness;
import com.project1.DateRange;

import java.time.LocalDate;

public class DateRangeGenerator extends Generator<DateRange> {

    public DateRangeGenerator() {
        super(DateRange.class);
    }

    @Override
    public DateRange generate(SourceOfRandomness random, GenerationStatus status) {
        LocalDate startDate = LocalDate.now().plusDays(random.nextInt(1, 10));
        LocalDate endDate = startDate.plusDays(random.nextInt(11, 20));
        return new DateRange(startDate, endDate);
    }
}
