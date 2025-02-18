package code.test.g7A8cEhK.services;

import code.test.g7A8cEhK.beans.Booking;
import code.test.g7A8cEhK.beans.dtos.TimeSlot;

import java.time.Duration;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import static java.time.temporal.ChronoUnit.MINUTES;

public class BookingServiceUtil {

    List<Booking> sortByStartTime(List<Booking> bookings) {
        List<Booking> sortedBookings = new ArrayList<>(bookings);
        sortedBookings.sort(new Comparator<Booking>() {
            @Override
            public int compare(Booking booking, Booking t1) {
                return booking.getStartTime().compareTo(t1.getStartTime());
            }
        });

        return sortedBookings;
    }

    boolean isWithinWorkingHours(Booking booking, LocalTime workingTimeStart, LocalTime workingTimeEnd) {
        return !booking.getStartTime().isBefore(workingTimeStart) && !booking.getEndTime().isAfter(workingTimeEnd);
    }

    boolean isAtLeastFifteenMinutes(Booking booking) {
        final Duration minTime = Duration.ofMinutes(15L);
        final Duration bookingDuration = Duration.between(booking.getStartTime(), booking.getEndTime());
        return bookingDuration.compareTo(minTime) >= 0;
    }

    TimeSlot createTimeSlot(LocalTime startTime, LocalTime endTime) {
        return TimeSlot
                .builder()
                .startTime(startTime)
                .endTime(endTime)
                .duration(Duration.between(startTime.truncatedTo(MINUTES), endTime.truncatedTo(MINUTES)))
                .build();
    }
}
