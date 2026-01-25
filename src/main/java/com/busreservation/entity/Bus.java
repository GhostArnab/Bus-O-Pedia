package com.busreservation.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * Entity class representing a Bus in the reservation system.
 * Maps to the 'buses' table in the database.
 */
@Entity
@Table(name = "buses")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Bus {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true, length = 50)
    private String busNumber;

    @Column(nullable = false, length = 100)
    private String source;

    @Column(nullable = false, length = 100)
    private String destination;

    @Column(nullable = false)
    private Double price;

    @Column(nullable = false)
    private LocalDateTime departureTime;

    @Column(nullable = false)
    private Integer totalSeats;

    /**
     * One-to-Many relationship with Reservation.
     * CascadeType.ALL ensures reservations are deleted when bus is deleted.
     * orphanRemoval ensures reservations are deleted when removed from the list.
     */
    @OneToMany(mappedBy = "bus", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Reservation> reservations = new ArrayList<>();

    /**
     * Convenience method to get available seats.
     * 
     * @return number of available seats
     */
    public Integer getAvailableSeats() {
        return totalSeats - (reservations != null ? reservations.size() : 0);
    }

    /**
     * Check if the bus has available seats.
     * 
     * @return true if seats are available, false otherwise
     */
    public boolean hasAvailableSeats() {
        return getAvailableSeats() > 0;
    }
}
