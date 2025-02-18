package code.test.g7A8cEhK.controllers;

import code.test.g7A8cEhK.beans.Booking;
import code.test.g7A8cEhK.beans.dtos.TimeSlot;
import code.test.g7A8cEhK.services.BookingService;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping(path = "/v1/booking")
public class BookingController {
    private final BookingService bookingService;

    public BookingController(BookingService bookingService) {
        this.bookingService = bookingService;
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public Booking addBooking(@Valid @RequestBody Booking booking) {
        log.info("Payload {}", booking.toString());
        return bookingService.create(booking);
    }

    @GetMapping(path = "/{minutes}", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<TimeSlot> listAvailableTimeSlots(@PathVariable("minutes") Long minutes) {
        return bookingService.getFreeTimeSlots(minutes);
    }

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public List<code.test.g7A8cEhK.beans.dtos.Booking> listBookings() {
        return bookingService.get();
    }
}
