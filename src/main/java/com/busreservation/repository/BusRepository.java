package com.busreservation.repository;

import com.busreservation.entity.Bus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

/**
 * Repository interface for Bus entity.
 * Provides CRUD operations and custom query methods.
 */
@Repository
public interface BusRepository extends JpaRepository<Bus, Long> {

    /**
     * Find a bus by its unique bus number.
     * 
     * @param busNumber the bus number to search for
     * @return Optional containing the bus if found
     */
    Optional<Bus> findByBusNumber(String busNumber);

    /**
     * Find all buses traveling from source to destination.
     * 
     * @param source      the departure location
     * @param destination the arrival location
     * @return list of matching buses
     */
    List<Bus> findBySourceAndDestination(String source, String destination);

    /**
     * Find buses by source, destination, and departure time after a given date.
     * Useful for searching available buses for a specific route on a specific date.
     * 
     * @param source      the departure location
     * @param destination the arrival location
     * @param afterTime   the minimum departure time
     * @return list of matching buses
     */
    List<Bus> findBySourceAndDestinationAndDepartureTimeAfter(
            String source,
            String destination,
            LocalDateTime afterTime);

    /**
     * Find all buses departing after a specific time.
     * 
     * @param departureTime the minimum departure time
     * @return list of buses
     */
    List<Bus> findByDepartureTimeAfter(LocalDateTime departureTime);

    /**
     * Custom query to find buses with available seats for a specific route.
     * 
     * @param source      the departure location
     * @param destination the arrival location
     * @return list of buses with available seats
     */
    @Query("SELECT b FROM Bus b WHERE b.source = :source AND b.destination = :destination " +
            "AND SIZE(b.reservations) < b.totalSeats")
    List<Bus> findAvailableBusesByRoute(
            @Param("source") String source,
            @Param("destination") String destination);

    /**
     * Check if a bus number already exists.
     * 
     * @param busNumber the bus number to check
     * @return true if exists, false otherwise
     */
    boolean existsByBusNumber(String busNumber);
}
