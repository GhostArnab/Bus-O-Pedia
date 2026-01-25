package com.busreservation.controller;

import com.busreservation.entity.Bus;
import com.busreservation.service.BusService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.time.LocalDateTime;
import java.util.List;

/**
 * Controller for managing bus operations.
 * Handles all web requests related to bus management and search.
 */
@Controller
@RequestMapping("/buses")
@RequiredArgsConstructor
@Slf4j
public class BusController {

    private final BusService busService;

    /**
     * Display list of all buses.
     */
    @GetMapping
    public String listBuses(Model model) {
        log.info("Fetching all buses");
        List<Bus> buses = busService.getAllBuses();
        model.addAttribute("buses", buses);
        model.addAttribute("pageTitle", "All Buses");
        return "buses/list";
    }

    /**
     * Display upcoming buses (not departed yet).
     */
    @GetMapping("/upcoming")
    public String upcomingBuses(Model model) {
        log.info("Fetching upcoming buses");
        List<Bus> buses = busService.getUpcomingBuses();
        model.addAttribute("buses", buses);
        model.addAttribute("pageTitle", "Upcoming Buses");
        return "buses/list";
    }

    /**
     * Display bus search page.
     */
    @GetMapping("/search")
    public String searchPage(Model model) {
        model.addAttribute("pageTitle", "Search Buses");
        return "buses/search";
    }

    /**
     * Search buses by source and destination.
     */
    @PostMapping("/search")
    public String searchBuses(
            @RequestParam String source,
            @RequestParam String destination,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime departureDate,
            Model model) {

        log.info("Searching buses from {} to {}", source, destination);

        List<Bus> buses;
        if (departureDate != null) {
            buses = busService.searchBusesByRouteAndDate(source, destination, departureDate);
        } else {
            buses = busService.searchAvailableBuses(source, destination);
        }

        model.addAttribute("buses", buses);
        model.addAttribute("source", source);
        model.addAttribute("destination", destination);
        model.addAttribute("pageTitle", "Search Results");

        if (buses.isEmpty()) {
            model.addAttribute("message", "No buses found for the selected route.");
        }

        return "buses/search-results";
    }

    /**
     * Display bus details.
     */
    @GetMapping("/{id}")
    public String viewBus(@PathVariable Long id, Model model, RedirectAttributes redirectAttributes) {
        log.info("Fetching bus details for ID: {}", id);

        return busService.getBusById(id)
                .map(bus -> {
                    model.addAttribute("bus", bus);
                    model.addAttribute("pageTitle", "Bus Details");
                    return "buses/details";
                })
                .orElseGet(() -> {
                    redirectAttributes.addFlashAttribute("error", "Bus not found with ID: " + id);
                    return "redirect:/buses";
                });
    }

    /**
     * Display form to add a new bus.
     */
    @GetMapping("/add")
    public String addBusForm(Model model) {
        model.addAttribute("bus", new Bus());
        model.addAttribute("pageTitle", "Add New Bus");
        return "buses/form";
    }

    /**
     * Save a new bus.
     */
    @PostMapping("/add")
    public String addBus(@ModelAttribute Bus bus, RedirectAttributes redirectAttributes) {
        log.info("Adding new bus: {}", bus.getBusNumber());

        try {
            busService.createBus(bus);
            redirectAttributes.addFlashAttribute("success", "Bus added successfully!");
            return "redirect:/buses";
        } catch (IllegalArgumentException e) {
            redirectAttributes.addFlashAttribute("error", e.getMessage());
            return "redirect:/buses/add";
        }
    }

    /**
     * Display form to edit a bus.
     */
    @GetMapping("/edit/{id}")
    public String editBusForm(@PathVariable Long id, Model model, RedirectAttributes redirectAttributes) {
        return busService.getBusById(id)
                .map(bus -> {
                    model.addAttribute("bus", bus);
                    model.addAttribute("pageTitle", "Edit Bus");
                    return "buses/form";
                })
                .orElseGet(() -> {
                    redirectAttributes.addFlashAttribute("error", "Bus not found with ID: " + id);
                    return "redirect:/buses";
                });
    }

    /**
     * Update an existing bus.
     */
    @PostMapping("/edit/{id}")
    public String updateBus(@PathVariable Long id, @ModelAttribute Bus bus, RedirectAttributes redirectAttributes) {
        log.info("Updating bus with ID: {}", id);

        try {
            busService.updateBus(id, bus);
            redirectAttributes.addFlashAttribute("success", "Bus updated successfully!");
            return "redirect:/buses/" + id;
        } catch (IllegalArgumentException e) {
            redirectAttributes.addFlashAttribute("error", e.getMessage());
            return "redirect:/buses/edit/" + id;
        }
    }

    /**
     * Delete a bus.
     */
    @PostMapping("/delete/{id}")
    public String deleteBus(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        log.info("Deleting bus with ID: {}", id);

        try {
            busService.deleteBus(id);
            redirectAttributes.addFlashAttribute("success", "Bus deleted successfully!");
        } catch (IllegalArgumentException e) {
            redirectAttributes.addFlashAttribute("error", e.getMessage());
        }

        return "redirect:/buses";
    }
}
