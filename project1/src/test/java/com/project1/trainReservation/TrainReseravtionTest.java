package com.project1.trainReservation;

import java.util.*;

import com.project1.TrainReservationSystem;
import org.junit.runner.RunWith;
import com.pholser.junit.quickcheck.From;
import com.pholser.junit.quickcheck.Property;
import com.pholser.junit.quickcheck.generator.InRange;
import com.pholser.junit.quickcheck.runner.JUnitQuickcheck;

import static org.junit.Assert.*;

@RunWith(JUnitQuickcheck.class)
public class TrainReseravtionTest {

    // Reserve a bunch of random dates
    // reserve and search over and over and each search should yield the correct results using assertequals.
    // keep an array to check against (my own cache)


    // add to system
    // add to all related search keys i have
    // for all related search keys, search and the res list should be same as the local list i have
    @Property(trials = 20)
    public void testSearchReturnsCorrectResults(@From(SearchKeyGenerator.class) SearchKeyGenerator.SearchKeys searchKeysGen,
                                                @From(ReservationGenerator.class) ReservationGenerator.Reservations reservationsGen) {
        List<SearchKeyGenerator.SearchKey> searchKeys = searchKeysGen.searchKeys;
        List<ReservationGenerator.Reservation> reservations = reservationsGen.reservations;
        Random random = new Random();
        // TrainReservationSystem with a cache capacity of 10
        // equals
        for (ReservationGenerator.Reservation reservation : reservations) {
            TrainReservationSystem system = new TrainReservationSystem( random.nextInt(10) + 1);
            String trainId = "Train" +  random.nextInt(3) + 1;
            system.reserveSeat(trainId, reservation.passengerName, reservation.seatNumber, reservation.date);
            assertFalse(system.reserveSeat(trainId, reservation.passengerName, reservation.seatNumber, reservation.date));
            // keep local list of relavent search keys
            Map<SearchKeyGenerator.SearchKey, ArrayList<TrainReservationSystem.Reservation>> relaventKeys = new HashMap<>();
            // add to rel search keys
            for (SearchKeyGenerator.SearchKey searchKey : searchKeys) {
                if (reservation.date.isAfter(searchKey.dateRange.getStartDate()) && reservation.date.isBefore(searchKey.dateRange.getEndDate()) && searchKey.trainId.equals(trainId)) {
                    ArrayList<TrainReservationSystem.Reservation> reservationList = relaventKeys.computeIfAbsent(searchKey, k -> new ArrayList<TrainReservationSystem.Reservation>());
                    reservationList.add(new TrainReservationSystem.Reservation(reservation.passengerName, reservation.seatNumber, reservation.date));
                }
            }

            for (SearchKeyGenerator.SearchKey searchKey : relaventKeys.keySet()) {
                List<TrainReservationSystem. Reservation> l1 = relaventKeys.get(searchKey);
                List<TrainReservationSystem. Reservation> l2 = system.searchReservations(searchKey.trainId, searchKey.dateRange);
                assertEquals(true, TrainReseravtionTest.haveSameReservations(l1, l2));
                assertEquals(l1.size(), l2.size());
            }


            // compare search results
        }

    }



    // if the cache capacity is n and i search for each key (of n * 2 total keys) 1 after another, at the end, the first n shouldnt be
    // in there and the last n should be

// min frequency shouldnt be 1 after we reaccess stuff


    public static boolean haveSameReservations(List<TrainReservationSystem.Reservation> l1, List<TrainReservationSystem.Reservation> l2) {
        // First, check if the sizes are the same
        if (l1.size() != l2.size()) {
            return false;
        }
        l1.sort((r1, r2) -> {
            return r1.hashCode() - r2.hashCode();
        });

        l2.sort((r1, r2) -> {
            return r1.hashCode() - r2.hashCode();
        });

        // Compare the lists element by element
        for (int i = 0; i < l1.size(); i++) {
            if (!l1.get(i).equals(l2.get(i))) {
                return false;  // Return false if any pair of reservations don't match
            }
        }
        return true;
    }
}
