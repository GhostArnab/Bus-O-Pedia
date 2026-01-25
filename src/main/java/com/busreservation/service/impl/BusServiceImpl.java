package com.busreservation.service.impl;

import com.busreservation.entity.Bus;
import com.busreservation.repository.BusRepository;
import com.busreservation.service.BusService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

/**
 * Implementation of BusService interface.
 * Handles all business logic for bus management operations.
 */
@Service
@RequiredArgsConstructor
@Slf4j
@Transactional
public class BusServiceImpl implements BusService {

    private final BusRepository busRepository;

    @Override
    public Bus createBus(Bus bus) {
        log.info("Creating new bus with number: {}", bus.getBusNumber());

        // Validate bus number uniqueness
        if (busRepository.existsByBusNumber(bus.getBusNumber())) {
            throw new IllegalArgumentException("Bus number " + bus.getBusNumber() + " already exists");
        }

        // Validate business rules
        validateBus(bus);

        Bus savedBus = busRepository.save(bus);
        log.info("Bus created successfully with ID: {}", savedBus.getId());
        return savedBus;
    }

    @Override
    public Bus updateBus(Long id, Bus bus) {
        log.info("Updating bus with ID: {}", id);

        Bus existingBus = busRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Bus not found with ID: " + id));

        // Check if bus number is being changed and if new number already exists
        if (!existingBus.getBusNumber().equals(bus.getBusNumber())
                && busRepository.existsByBusNumber(bus.getBusNumber())) {
            throw new IllegalArgumentException("Bus number " + bus.getBusNumber() + " already exists");
        }

        // Validate business rules
        validateBus(bus);

        // Update fields
        existingBus.setBusNumber(bus.getBusNumber());
        existingBus.setSource(bus.getSource());
        existingBus.setDestination(bus.getDestination());
        existingBus.setPrice(bus.getPrice());
        existingBus.setDepartureTime(bus.getDepartureTime());
        existingBus.setTotalSeats(bus.getTotalSeats());

        Bus updatedBus = busRepository.save(existingBus);
        log.info("Bus updated successfully with ID: {}", updatedBus.getId());
        return updatedBus;
    }

    @Override
    public void deleteBus(Long id) {
        log.info("Deleting bus with ID: {}", id);

        if (!busRepository.existsById(id)) {
            throw new IllegalArgumentException("Bus not found with ID: " + id);
        }

        busRepository.deleteById(id);
        log.info("Bus deleted successfully with ID: {}", id);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Bus> getBusById(Long id) {
        log.debug("Fetching bus with ID: {}", id);
        return busRepository.findById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Bus> getBusByNumber(String busNumber) {
        log.debug("Fetching bus with number: {}", busNumber);
        return busRepository.findByBusNumber(busNumber);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Bus> getAllBuses() {
        log.debug("Fetching all buses");
        return busRepository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public List<Bus> searchBuses(String source, String destination) {
        log.info("Searching buses from {} to {}", source, destination);
        return busRepository.findBySourceAndDestination(source, destination);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Bus> searchAvailableBuses(String source, String destination) {
        log.info("Searching available buses from {} to {}", source, destination);
        return busRepository.findAvailableBusesByRoute(source, destination);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Bus> searchBusesByRouteAndDate(String source, String destination, LocalDateTime departureDate) {
        log.info("Searching buses from {} to {} departing after {}", source, destination, departureDate);
        return busRepository.findBySourceAndDestinationAndDepartureTimeAfter(source, destination, departureDate);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Bus> getUpcomingBuses() {
        log.debug("Fetching upcoming buses");
        return busRepository.findByDepartureTimeAfter(LocalDateTime.now());
    }

    @Override
    @Transactional(readOnly = true)
    public boolean busNumberExists(String busNumber) {
        return busRepository.existsByBusNumber(busNumber);
    }

    @Override
    @Transactional(readOnly = true)
    public Integer getAvailableSeats(Long busId) {
        Bus bus = busRepository.findById(busId)
                .orElseThrow(() -> new IllegalArgumentException("Bus not found with ID: " + busId));
        return bus.getAvailableSeats();
    }

    /**
     * Validate bus entity according to business rules.
     * 
     * @param bus the bus to validate
     */
    private void validateBus(Bus bus) {
        if (bus.getBusNumber() == null || bus.getBusNumber().trim().isEmpty()) {
            throw new IllegalArgumentException("Bus number cannot be empty");
        }

        if (bus.getSource() == null || bus.getSource().trim().isEmpty()) {
            throw new IllegalArgumentException("Source location cannot be empty");
        }

        if (bus.getDestination() == null || bus.getDestination().trim().isEmpty()) {
            throw new IllegalArgumentException("Destination location cannot be empty");
        }

        if (bus.getSource().equalsIgnoreCase(bus.getDestination())) {
            throw new IllegalArgumentException("Source and destination cannot be the same");
        }

        if (bus.getPrice() == null || bus.getPrice() <= 0) {
            throw new IllegalArgumentException("Price must be greater than zero");
        }

        if (bus.getDepartureTime() == null) {
            throw new IllegalArgumentException("Departure time cannot be null");
        }

        if (bus.getTotalSeats() == null || bus.getTotalSeats() <= 0) {
            throw new IllegalArgumentException("Total seats must be greater than zero");
        }

        if (bus.getTotalSeats() > 100) {
            throw new IllegalArgumentException("Total seats cannot exceed 100");
        }
    }
}
