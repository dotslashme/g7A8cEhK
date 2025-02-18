package code.test.g7A8cEhK.services;

import code.test.g7A8cEhK.beans.Booking;
import code.test.g7A8cEhK.beans.dtos.TimeSlot;
import code.test.g7A8cEhK.mappers.BookingMapper;
import code.test.g7A8cEhK.repositories.BookingRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
public class BookingService {
    private final BookingRepository bookingRepository;
    private final BookingServiceUtil bookingServiceUtil = new BookingServiceUtil();

    @Value("${g7A8cEhK.workingHours.start}")
    private LocalTime workingTimeStart;

    @Value("${g7A8cEhK.workingHours.end}")
    private LocalTime workingTimeEnd;

    public BookingService(BookingRepository bookingRepository) {
        this.bookingRepository = bookingRepository;
    }

    public Booking create(Booking booking) {
        if (bookingServiceUtil.isWithinWorkingHours(booking, workingTimeStart, workingTimeEnd)
                && bookingServiceUtil.isAtLeastFifteenMinutes(booking)) {
            return bookingRepository.save(booking);
        }
        return null;
    }

    public List<code.test.g7A8cEhK.beans.dtos.Booking> get() {
        getFreeSlots(Duration.ofMinutes(60L));
        return BookingMapper.toBookingDto(bookingRepository.findAll());
    }

    public List<TimeSlot> getFreeTimeSlots(Long minutes) {
        return getFreeSlots(Duration.ofMinutes(minutes));
    }


    private List<TimeSlot> getFreeSlots(Duration duration) {
        List<Booking> bookings = bookingRepository.findAll();
        List<Booking> sortedBookings = bookingServiceUtil.sortByStartTime(bookings);
        List<TimeSlot> timeSlots = new ArrayList<>();

        //@todo: Need to split free time into chunks with duration

        for (int i = 0; i < sortedBookings.size(); i++) {
            if (i == 0) { // First
                timeSlots.add(
                        bookingServiceUtil.createTimeSlot(
                                workingTimeStart,
                                sortedBookings.get(i).getStartTime()
                        ));
            } else if (i == sortedBookings.size() - 1) { // Last
                timeSlots.add(
                        bookingServiceUtil.createTimeSlot(
                                sortedBookings.get(i).getEndTime(),
                                workingTimeEnd
                        ));
            } else {
                timeSlots.add(
                        bookingServiceUtil.createTimeSlot(
                                sortedBookings.get(i - 1).getEndTime(),
                                sortedBookings.get(i).getStartTime()
                        ));
            }
        }

        return timeSlots;
    }

}
