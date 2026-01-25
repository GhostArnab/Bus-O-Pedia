package com.busreservation.config;

import com.busreservation.entity.Bus;
import com.busreservation.repository.BusRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

/**
 * Data loader configuration to initialize the database with sample bus routes.
 * This runs once when the application starts and only if the database is empty.
 */
@Configuration
public class DataLoader {

    @Bean
    CommandLineRunner initDatabase(BusRepository busRepository) {
        return args -> {
            // Only load data if database is empty
            if (busRepository.count() == 0) {
                List<Bus> buses = Arrays.asList(
                        // Delhi to Mumbai routes
                        createBus("DL-MH-001", "Delhi", "Mumbai", 1200.0,
                                LocalDateTime.now().plusDays(1).withHour(6).withMinute(0), 40),
                        createBus("DL-MH-002", "Delhi", "Mumbai", 1150.0,
                                LocalDateTime.now().plusDays(1).withHour(22).withMinute(30), 35),
                        createBus("DL-MH-003", "Delhi", "Mumbai", 1300.0,
                                LocalDateTime.now().plusDays(2).withHour(18).withMinute(0), 45),

                        // Delhi to Bangalore routes
                        createBus("DL-KA-001", "Delhi", "Bangalore", 1500.0,
                                LocalDateTime.now().plusDays(1).withHour(20).withMinute(0), 40),
                        createBus("DL-KA-002", "Delhi", "Bangalore", 1450.0,
                                LocalDateTime.now().plusDays(2).withHour(19).withMinute(30), 38),

                        // Mumbai to Bangalore routes
                        createBus("MH-KA-001", "Mumbai", "Bangalore", 900.0,
                                LocalDateTime.now().plusDays(1).withHour(21).withMinute(0), 42),
                        createBus("MH-KA-002", "Mumbai", "Bangalore", 950.0,
                                LocalDateTime.now().plusDays(2).withHour(20).withMinute(30), 40),

                        // Mumbai to Pune routes
                        createBus("MH-PN-001", "Mumbai", "Pune", 300.0,
                                LocalDateTime.now().plusDays(1).withHour(7).withMinute(0), 50),
                        createBus("MH-PN-002", "Mumbai", "Pune", 280.0,
                                LocalDateTime.now().plusDays(1).withHour(14).withMinute(30), 45),
                        createBus("MH-PN-003", "Mumbai", "Pune", 320.0,
                                LocalDateTime.now().plusDays(2).withHour(9).withMinute(0), 48),

                        // Delhi to Jaipur routes
                        createBus("DL-RJ-001", "Delhi", "Jaipur", 400.0,
                                LocalDateTime.now().plusDays(1).withHour(8).withMinute(0), 40),
                        createBus("DL-RJ-002", "Delhi", "Jaipur", 380.0,
                                LocalDateTime.now().plusDays(1).withHour(15).withMinute(30), 35),
                        createBus("DL-RJ-003", "Delhi", "Jaipur", 420.0,
                                LocalDateTime.now().plusDays(2).withHour(10).withMinute(0), 42),

                        // Bangalore to Chennai routes
                        createBus("KA-TN-001", "Bangalore", "Chennai", 600.0,
                                LocalDateTime.now().plusDays(1).withHour(22).withMinute(0), 45),
                        createBus("KA-TN-002", "Bangalore", "Chennai", 580.0,
                                LocalDateTime.now().plusDays(2).withHour(21).withMinute(30), 40),

                        // Mumbai to Goa routes
                        createBus("MH-GA-001", "Mumbai", "Goa", 700.0,
                                LocalDateTime.now().plusDays(1).withHour(19).withMinute(0), 38),
                        createBus("MH-GA-002", "Mumbai", "Goa", 750.0,
                                LocalDateTime.now().plusDays(2).withHour(18).withMinute(30), 40),

                        // Delhi to Agra routes
                        createBus("DL-UP-001", "Delhi", "Agra", 250.0,
                                LocalDateTime.now().plusDays(1).withHour(6).withMinute(30), 35),
                        createBus("DL-UP-002", "Delhi", "Agra", 280.0,
                                LocalDateTime.now().plusDays(1).withHour(16).withMinute(0), 38),

                        // Kolkata to Bhubaneswar routes
                        createBus("WB-OR-001", "Kolkata", "Bhubaneswar", 550.0,
                                LocalDateTime.now().plusDays(1).withHour(20).withMinute(0), 40),
                        createBus("WB-OR-002", "Kolkata", "Bhubaneswar", 530.0,
                                LocalDateTime.now().plusDays(2).withHour(19).withMinute(30), 42),

                        // Chennai to Hyderabad routes
                        createBus("TN-TG-001", "Chennai", "Hyderabad", 800.0,
                                LocalDateTime.now().plusDays(1).withHour(21).withMinute(0), 45),
                        createBus("TN-TG-002", "Chennai", "Hyderabad", 780.0,
                                LocalDateTime.now().plusDays(2).withHour(20).withMinute(30), 40));

                busRepository.saveAll(buses);
                System.out.println("✅ Database initialized with " + buses.size() + " bus routes!");
                System.out.println(
                        "📍 Cities covered: Delhi, Mumbai, Bangalore, Pune, Jaipur, Chennai, Goa, Agra, Kolkata, Bhubaneswar, Hyderabad");
            } else {
                System.out.println("ℹ️  Database already contains data. Skipping initialization.");
            }
        };
    }

    /**
     * Helper method to create a Bus object
     */
    private Bus createBus(String busNumber, String source, String destination,
            Double price, LocalDateTime departureTime, Integer totalSeats) {
        Bus bus = new Bus();
        bus.setBusNumber(busNumber);
        bus.setSource(source);
        bus.setDestination(destination);
        bus.setPrice(price);
        bus.setDepartureTime(departureTime);
        bus.setTotalSeats(totalSeats);
        return bus;
    }
}
