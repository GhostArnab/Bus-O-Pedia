package com.busreservation.controller;

import com.busreservation.entity.Bus;
import com.busreservation.entity.Reservation;
import com.busreservation.service.BusService;
import com.busreservation.service.ReservationService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

/**
 * Controller for managing reservation operations.
 * Handles all web requests related to bus seat bookings.
 */
@Controller
@RequestMapping("/reservations")
@RequiredArgsConstructor
@Slf4j
public class ReservationController {

    private final ReservationService reservationService;
    private final BusService busService;

    /**
     * Display all reservations.
     */
    @GetMapping
    public String listReservations(Model model) {
        log.info("Fetching all reservations");
        List<Reservation> reservations = reservationService.getAllReservations();
        model.addAttribute("reservations", reservations);
        model.addAttribute("pageTitle", "All Reservations");
        return "reservations/list";
    }

    /**
     * Display booking form for a specific bus.
     */
    @GetMapping("/book/{busId}")
    public String bookingForm(@PathVariable Long busId, Model model, RedirectAttributes redirectAttributes) {
        log.info("Displaying booking form for bus ID: {}", busId);

        return busService.getBusById(busId)
                .map(bus -> {
                    List<Integer> availableSeats = reservationService.getAvailableSeatNumbers(busId);
                    List<Integer> reservedSeats = reservationService.getReservedSeatNumbers(busId);

                    if (availableSeats.isEmpty()) {
                        redirectAttributes.addFlashAttribute("error", "No seats available on this bus!");
                        return "redirect:/buses/" + busId;
                    }

                    model.addAttribute("bus", bus);
                    model.addAttribute("availableSeats", availableSeats);
                    model.addAttribute("reservedSeats", reservedSeats);
                    model.addAttribute("pageTitle", "Book Seat");
                    return "reservations/booking-form";
                })
                .orElseGet(() -> {
                    redirectAttributes.addFlashAttribute("error", "Bus not found with ID: " + busId);
                    return "redirect:/buses";
                });
    }

    /**
     * Process booking request.
     */
    @PostMapping("/book/{busId}")
    public String createBooking(
            @PathVariable Long busId,
            @RequestParam String passengerName,
            @RequestParam Integer seatNumber,
            RedirectAttributes redirectAttributes) {

        log.info("Creating reservation for passenger {} on bus ID {} for seat {}",
                passengerName, busId, seatNumber);

        try {
            Reservation reservation = reservationService.createReservation(busId, passengerName, seatNumber);
            redirectAttributes.addFlashAttribute("success", "Seat booked successfully!");
            return "redirect:/reservations/confirmation/" + reservation.getId();
        } catch (IllegalArgumentException e) {
            redirectAttributes.addFlashAttribute("error", e.getMessage());
            return "redirect:/reservations/book/" + busId;
        }
    }

    /**
     * Display booking confirmation page.
     */
    @GetMapping("/confirmation/{reservationId}")
    public String bookingConfirmation(@PathVariable Long reservationId, Model model,
            RedirectAttributes redirectAttributes) {
        log.info("Displaying confirmation for reservation ID: {}", reservationId);

        return reservationService.getReservationById(reservationId)
                .map(reservation -> {
                    model.addAttribute("reservation", reservation);
                    model.addAttribute("pageTitle", "Booking Confirmation");
                    return "reservations/confirmation";
                })
                .orElseGet(() -> {
                    redirectAttributes.addFlashAttribute("error", "Reservation not found with ID: " + reservationId);
                    return "redirect:/reservations";
                });
    }

    /**
     * Search reservations by passenger name.
     */
    @GetMapping("/search")
    public String searchReservations(@RequestParam String passengerName, Model model) {
        log.info("Searching reservations for passenger: {}", passengerName);

        List<Reservation> reservations = reservationService.getReservationsByPassengerName(passengerName);
        model.addAttribute("reservations", reservations);
        model.addAttribute("passengerName", passengerName);
        model.addAttribute("pageTitle", "Search Results");

        if (reservations.isEmpty()) {
            model.addAttribute("message", "No reservations found for passenger: " + passengerName);
        }

        return "reservations/list";
    }

    /**
     * View reservation details.
     */
    @GetMapping("/{id}")
    public String viewReservation(@PathVariable Long id, Model model, RedirectAttributes redirectAttributes) {
        log.info("Fetching reservation details for ID: {}", id);

        return reservationService.getReservationById(id)
                .map(reservation -> {
                    model.addAttribute("reservation", reservation);
                    model.addAttribute("pageTitle", "Reservation Details");
                    return "reservations/details";
                })
                .orElseGet(() -> {
                    redirectAttributes.addFlashAttribute("error", "Reservation not found with ID: " + id);
                    return "redirect:/reservations";
                });
    }

    /**
     * Cancel a reservation.
     */
    @PostMapping("/cancel/{id}")
    public String cancelReservation(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        log.info("Cancelling reservation with ID: {}", id);

        try {
            reservationService.cancelReservation(id);
            redirectAttributes.addFlashAttribute("success", "Reservation cancelled successfully!");
        } catch (IllegalArgumentException e) {
            redirectAttributes.addFlashAttribute("error", e.getMessage());
        }

        return "redirect:/reservations";
    }
}
