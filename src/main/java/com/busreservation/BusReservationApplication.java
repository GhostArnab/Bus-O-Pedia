package com.busreservation;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Main Spring Boot Application class for Bus Reservation System.
 * 
 * @SpringBootApplication is a convenience annotation that combines:
 *                        - @Configuration: Tags the class as a source of bean
 *                        definitions
 *                        - @EnableAutoConfiguration: Enables Spring Boot's
 *                        auto-configuration
 *                        - @ComponentScan: Scans for components in the current
 *                        package and sub-packages
 */
@SpringBootApplication
public class BusReservationApplication {

    /**
     * Main method - entry point of the application.
     * 
     * @param args command line arguments
     */
    public static void main(String[] args) {
        SpringApplication.run(BusReservationApplication.class, args);
        System.out.println("\n========================================");
        System.out.println("🚌 Bus Reservation System Started!");
        System.out.println("========================================");
        System.out.println("Application running at: http://localhost:8080");
        System.out.println("========================================\n");
    }
}
