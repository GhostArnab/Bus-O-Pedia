package com.busreservation.service;

import com.busreservation.entity.Bus;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

/**
 * Service interface for Bus-related operations.
 * Defines business logic methods for managing buses in the reservation system.
 */
public interface BusService {

    /**
     * Create a new bus.
     * 
     * @param bus the bus entity to create
     * @return the created bus
     */
    Bus createBus(Bus bus);

    /**
     * Update an existing bus.
     * 
     * @param id  the bus ID
     * @param bus the updated bus data
     * @return the updated bus
     */
    Bus updateBus(Long id, Bus bus);

    /**
     * Delete a bus by ID.
     * 
     * @param id the bus ID
     */
    void deleteBus(Long id);

    /**
     * Get a bus by ID.
     * 
     * @param id the bus ID
     * @return Optional containing the bus if found
     */
    Optional<Bus> getBusById(Long id);

    /**
     * Get a bus by its unique bus number.
     * 
     * @param busNumber the bus number
     * @return Optional containing the bus if found
     */
    Optional<Bus> getBusByNumber(String busNumber);

    /**
     * Get all buses.
     * 
     * @return list of all buses
     */
    List<Bus> getAllBuses();

    /**
     * Search buses by source and destination.
     * 
     * @param source      the departure location
     * @param destination the arrival location
     * @return list of matching buses
     */
    List<Bus> searchBuses(String source, String destination);

    /**
     * Search available buses by route (buses with available seats).
     * 
     * @param source      the departure location
     * @param destination the arrival location
     * @return list of buses with available seats
     */
    List<Bus> searchAvailableBuses(String source, String destination);

    /**
     * Search buses by route and departure date.
     * 
     * @param source        the departure location
     * @param destination   the arrival location
     * @param departureDate the departure date
     * @return list of matching buses
     */
    List<Bus> searchBusesByRouteAndDate(String source, String destination, LocalDateTime departureDate);

    /**
     * Get upcoming buses (departing after current time).
     * 
     * @return list of upcoming buses
     */
    List<Bus> getUpcomingBuses();

    /**
     * Check if a bus number already exists.
     * 
     * @param busNumber the bus number to check
     * @return true if exists, false otherwise
     */
    boolean busNumberExists(String busNumber);

    /**
     * Get available seats count for a bus.
     * 
     * @param busId the bus ID
     * @return number of available seats
     */
    Integer getAvailableSeats(Long busId);
}
