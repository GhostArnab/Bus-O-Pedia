package com.busreservation.repository;

import com.busreservation.entity.Bus;
import com.busreservation.entity.Reservation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Repository interface for Reservation entity.
 * Provides CRUD operations and custom query methods.
 */
@Repository
public interface ReservationRepository extends JpaRepository<Reservation, Long> {

    /**
     * Find all reservations for a specific bus.
     * 
     * @param bus the bus entity
     * @return list of reservations
     */
    List<Reservation> findByBus(Bus bus);

    /**
     * Find all reservations for a specific bus by bus ID.
     * 
     * @param busId the bus ID
     * @return list of reservations
     */
    List<Reservation> findByBusId(Long busId);

    /**
     * Find reservations by passenger name.
     * Useful for searching a passenger's booking history.
     * 
     * @param passengerName the passenger name
     * @return list of reservations
     */
    List<Reservation> findByPassengerName(String passengerName);

    /**
     * Find reservations by passenger name (case-insensitive).
     * 
     * @param passengerName the passenger name
     * @return list of reservations
     */
    List<Reservation> findByPassengerNameIgnoreCase(String passengerName);

    /**
     * Check if a seat is already reserved for a specific bus.
     * 
     * @param bus        the bus entity
     * @param seatNumber the seat number to check
     * @return true if seat is reserved, false otherwise
     */
    boolean existsByBusAndSeatNumber(Bus bus, Integer seatNumber);

    /**
     * Find a reservation by bus and seat number.
     * 
     * @param bus        the bus entity
     * @param seatNumber the seat number
     * @return Optional containing the reservation if found
     */
    Optional<Reservation> findByBusAndSeatNumber(Bus bus, Integer seatNumber);

    /**
     * Count total reservations for a specific bus.
     * 
     * @param bus the bus entity
     * @return count of reservations
     */
    long countByBus(Bus bus);

    /**
     * Get all reserved seat numbers for a specific bus.
     * 
     * @param busId the bus ID
     * @return list of reserved seat numbers
     */
    @Query("SELECT r.seatNumber FROM Reservation r WHERE r.bus.id = :busId ORDER BY r.seatNumber")
    List<Integer> findReservedSeatNumbersByBusId(@Param("busId") Long busId);
}
