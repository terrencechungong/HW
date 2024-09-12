package com.project1.trainReservation;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.project1.TrainReservationSystem;
import org.junit.runner.RunWith;
import com.pholser.junit.quickcheck.From;
import com.pholser.junit.quickcheck.Property;
import com.pholser.junit.quickcheck.generator.InRange;
import com.pholser.junit.quickcheck.runner.JUnitQuickcheck;

@RunWith(JUnitQuickcheck.class)
public class TrainReseravtionTest {

    // Reserve a bunch of random dates
    // reserve and search over and over and each search should yield the correct results using assertequals.
    // keep an array to check against (my own cache)


    // add to system
    // add to all related search keys i have
    // for all related search keys, search and the res list should be same as the local list i have
    @Property(trials = 100)
    public void testSearchReturnsCorrectResults(@From(SearchKeyGenerator.class) List<SearchKeyGenerator.SearchKey> searchKeys,
                                                @From(ReservationGenerator.class) List<ReservationGenerator.Reservation> reservations) {
        // TrainReservationSystem with a cache capacity of 10
        TrainReservationSystem system = new TrainReservationSystem(random.nextInt(1, 10));
        // equals
        String trainId = "Train" + random.nextInt(1, 3);
        for (ReservationGenerator.Reservation reservation : reservations) {
            system.reserveSeat(trainId, reservation.passengerName, reservation.seatNumber, reservation.date);
            // keep local list of relavent search keys
            List<SearchKeyGenerator.SearchKey> relaventKeys = new ArrayList<SearchKeyGenerator.SearchKey>();
            // add to rel search keys
            for (SearchKeyGenerator.SearchKey searchKey : searchKeys) {
                if (reservation.date.isAfter(searchKey.dateRange.getStartDate()) && reservation.date.isBefore(searchKey.dateRange.getEndDate())) {
                    relaventKeys.add(searchKey);
                }
            }



            // compare search results
        }


        // Add reservation to the system

        // Search for the reservation by SearchKey
        List<TrainReservationSystem.Reservation> results = system.searchReservations(searchKey);

        // Check if the reservation is correctly retrieved
        assertEquals(1, results.size());
        assertEquals(reservation, results.get(0));
    }



    // if the cache capacity is n and i search for each key (of n * 2 total keys) 1 after another, at the end, the first n shouldnt be
    // in there and the last n should be

// min frequency shouldnt be 1 after we reaccess stuff








}
