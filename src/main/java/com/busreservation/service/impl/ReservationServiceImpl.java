package com.busreservation.service.impl;

import com.busreservation.entity.Bus;
import com.busreservation.entity.Reservation;
import com.busreservation.repository.BusRepository;
import com.busreservation.repository.ReservationRepository;
import com.busreservation.service.ReservationService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 * Implementation of ReservationService interface.
 * Handles all business logic for reservation/booking operations.
 */
@Service
@RequiredArgsConstructor
@Slf4j
@Transactional
public class ReservationServiceImpl implements ReservationService {

    private final ReservationRepository reservationRepository;
    private final BusRepository busRepository;

    @Override
    public Reservation createReservation(Long busId, String passengerName, Integer seatNumber) {
        log.info("Creating reservation for passenger {} on bus ID {} for seat {}",
                passengerName, busId, seatNumber);

        // Fetch the bus
        Bus bus = busRepository.findById(busId)
                .orElseThrow(() -> new IllegalArgumentException("Bus not found with ID: " + busId));

        // Validate passenger name
        if (passengerName == null || passengerName.trim().isEmpty()) {
            throw new IllegalArgumentException("Passenger name cannot be empty");
        }

        // Validate seat number
        if (seatNumber == null || seatNumber <= 0) {
            throw new IllegalArgumentException("Invalid seat number");
        }

        if (seatNumber > bus.getTotalSeats()) {
            throw new IllegalArgumentException("Seat number exceeds total seats available in the bus");
        }

        // Check if bus has departed
        if (bus.getDepartureTime().isBefore(LocalDateTime.now())) {
            throw new IllegalArgumentException("Cannot book seat for a bus that has already departed");
        }

        // Check if seat is already booked
        if (reservationRepository.existsByBusAndSeatNumber(bus, seatNumber)) {
            throw new IllegalArgumentException("Seat number " + seatNumber + " is already booked");
        }

        // Check if bus has available seats
        if (!bus.hasAvailableSeats()) {
            throw new IllegalArgumentException("No seats available on this bus");
        }

        // Create reservation
        Reservation reservation = new Reservation();
        reservation.setPassengerName(passengerName.trim());
        reservation.setSeatNumber(seatNumber);
        reservation.setReservationDate(LocalDateTime.now());
        reservation.setBus(bus);

        Reservation savedReservation = reservationRepository.save(reservation);
        log.info("Reservation created successfully with ID: {}", savedReservation.getId());
        return savedReservation;
    }

    @Override
    public void cancelReservation(Long reservationId) {
        log.info("Cancelling reservation with ID: {}", reservationId);

        Reservation reservation = reservationRepository.findById(reservationId)
                .orElseThrow(() -> new IllegalArgumentException("Reservation not found with ID: " + reservationId));

        // Check if bus has already departed
        if (reservation.getBus().getDepartureTime().isBefore(LocalDateTime.now())) {
            throw new IllegalArgumentException("Cannot cancel reservation for a bus that has already departed");
        }

        reservationRepository.delete(reservation);
        log.info("Reservation cancelled successfully with ID: {}", reservationId);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Reservation> getReservationById(Long id) {
        log.debug("Fetching reservation with ID: {}", id);
        return reservationRepository.findById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Reservation> getAllReservations() {
        log.debug("Fetching all reservations");
        return reservationRepository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public List<Reservation> getReservationsByBusId(Long busId) {
        log.debug("Fetching reservations for bus ID: {}", busId);

        // Verify bus exists
        if (!busRepository.existsById(busId)) {
            throw new IllegalArgumentException("Bus not found with ID: " + busId);
        }

        return reservationRepository.findByBusId(busId);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Reservation> getReservationsByPassengerName(String passengerName) {
        log.debug("Fetching reservations for passenger: {}", passengerName);

        if (passengerName == null || passengerName.trim().isEmpty()) {
            throw new IllegalArgumentException("Passenger name cannot be empty");
        }

        return reservationRepository.findByPassengerNameIgnoreCase(passengerName.trim());
    }

    @Override
    @Transactional(readOnly = true)
    public boolean isSeatAvailable(Long busId, Integer seatNumber) {
        Bus bus = busRepository.findById(busId)
                .orElseThrow(() -> new IllegalArgumentException("Bus not found with ID: " + busId));

        return !reservationRepository.existsByBusAndSeatNumber(bus, seatNumber);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Integer> getReservedSeatNumbers(Long busId) {
        log.debug("Fetching reserved seat numbers for bus ID: {}", busId);

        // Verify bus exists
        if (!busRepository.existsById(busId)) {
            throw new IllegalArgumentException("Bus not found with ID: " + busId);
        }

        return reservationRepository.findReservedSeatNumbersByBusId(busId);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Integer> getAvailableSeatNumbers(Long busId) {
        log.debug("Fetching available seat numbers for bus ID: {}", busId);

        Bus bus = busRepository.findById(busId)
                .orElseThrow(() -> new IllegalArgumentException("Bus not found with ID: " + busId));

        List<Integer> reservedSeats = reservationRepository.findReservedSeatNumbersByBusId(busId);

        // Generate list of all seat numbers and filter out reserved ones
        return IntStream.rangeClosed(1, bus.getTotalSeats())
                .boxed()
                .filter(seat -> !reservedSeats.contains(seat))
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public long getReservationCountByBus(Long busId) {
        Bus bus = busRepository.findById(busId)
                .orElseThrow(() -> new IllegalArgumentException("Bus not found with ID: " + busId));

        return reservationRepository.countByBus(bus);
    }
}
