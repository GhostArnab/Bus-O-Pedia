package com.busreservation.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * Entity class representing a Reservation in the bus reservation system.
 * Maps to the 'reservations' table in the database.
 */
@Entity
@Table(name = "reservations")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Reservation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 100)
    private String passengerName;

    @Column(nullable = false)
    private Integer seatNumber;

    @Column(nullable = false)
    private LocalDateTime reservationDate;

    /**
     * Many-to-One relationship with Bus.
     * FetchType.LAZY for performance optimization.
     * A reservation must be associated with a bus.
     */
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "bus_id", nullable = false)
    private Bus bus;

    /**
     * Lifecycle callback to set reservation date automatically before persisting.
     */
    @PrePersist
    protected void onCreate() {
        if (reservationDate == null) {
            reservationDate = LocalDateTime.now();
        }
    }
}
