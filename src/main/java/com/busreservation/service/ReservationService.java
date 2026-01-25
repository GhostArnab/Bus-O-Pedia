package com.busreservation.service;

import com.busreservation.entity.Reservation;

import java.util.List;
import java.util.Optional;

/**
 * Service interface for Reservation-related operations.
 * Defines business logic methods for managing bus reservations.
 */
public interface ReservationService {

    /**
     * Create a new reservation (book a seat).
     * 
     * @param busId         the bus ID
     * @param passengerName the passenger name
     * @param seatNumber    the desired seat number
     * @return the created reservation
     */
    Reservation createReservation(Long busId, String passengerName, Integer seatNumber);

    /**
     * Cancel a reservation.
     * 
     * @param reservationId the reservation ID
     */
    void cancelReservation(Long reservationId);

    /**
     * Get a reservation by ID.
     * 
     * @param id the reservation ID
     * @return Optional containing the reservation if found
     */
    Optional<Reservation> getReservationById(Long id);

    /**
     * Get all reservations.
     * 
     * @return list of all reservations
     */
    List<Reservation> getAllReservations();

    /**
     * Get all reservations for a specific bus.
     * 
     * @param busId the bus ID
     * @return list of reservations
     */
    List<Reservation> getReservationsByBusId(Long busId);

    /**
     * Get reservations by passenger name.
     * 
     * @param passengerName the passenger name
     * @return list of reservations
     */
    List<Reservation> getReservationsByPassengerName(String passengerName);

    /**
     * Check if a seat is available for booking.
     * 
     * @param busId      the bus ID
     * @param seatNumber the seat number
     * @return true if available, false if already booked
     */
    boolean isSeatAvailable(Long busId, Integer seatNumber);

    /**
     * Get all reserved seat numbers for a bus.
     * 
     * @param busId the bus ID
     * @return list of reserved seat numbers
     */
    List<Integer> getReservedSeatNumbers(Long busId);

    /**
     * Get available seat numbers for a bus.
     * 
     * @param busId the bus ID
     * @return list of available seat numbers
     */
    List<Integer> getAvailableSeatNumbers(Long busId);

    /**
     * Get total number of reservations for a bus.
     * 
     * @param busId the bus ID
     * @return count of reservations
     */
    long getReservationCountByBus(Long busId);
}
