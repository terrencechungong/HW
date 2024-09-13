package com.project1.trainReservation;

import com.pholser.junit.quickcheck.generator.GenerationStatus;
import com.pholser.junit.quickcheck.generator.Generator;
import com.pholser.junit.quickcheck.random.SourceOfRandomness;
import com.project1.TrainReservationSystem;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;


public class ReservationGenerator extends Generator<ReservationGenerator.Reservations> {

    public class Reservations {
        public  List<ReservationGenerator.Reservation> reservations;

        public Reservations(List<ReservationGenerator.Reservation> reservations) {
            this.reservations = reservations;
        }
    }


    public static class Reservation {
        public String passengerName;
        public int seatNumber;
        public LocalDate date;

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
    }
        public ReservationGenerator(Class<ReservationGenerator.Reservations> type) {
            super(type);
        }

        @Override
        public ReservationGenerator.Reservations generate(SourceOfRandomness random, GenerationStatus status) {

            List<ReservationGenerator.Reservation> resis = new ArrayList<>();
            for (int i = 0; i < 200; i++) {
                String passengerName = "Passenger" + random.nextInt(1, 1000);
                int seatNumber = random.nextInt(1, 100);  // Seat numbers between 1 and 100
                LocalDate reservationDate = LocalDate.now().plusDays(random.nextInt(1, 19));  // Reservations within a year
                resis.add(new ReservationGenerator.Reservation(passengerName, seatNumber, reservationDate));
            }
            return new Reservations(resis);
        }
    }

