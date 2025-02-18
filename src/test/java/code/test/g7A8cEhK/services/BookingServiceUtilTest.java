package code.test.g7A8cEhK.services;

import code.test.g7A8cEhK.beans.Booking;
import code.test.g7A8cEhK.beans.dtos.TimeSlot;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.Duration;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class BookingServiceUtilTest {

    private BookingServiceUtil bookingServiceUtil;

    @BeforeEach
    void setUp() {
        this.bookingServiceUtil = new BookingServiceUtil();
    }

    @Test
    void sortByStartTime() {
        final List<Booking> unsorted = List.of(
                Booking
                        .builder()
                        .startTime(LocalTime.now().plusMinutes(100L).truncatedTo(ChronoUnit.MINUTES))
                        .endTime(LocalTime.now().plusMinutes(115L).truncatedTo(ChronoUnit.MINUTES))
                        .build(),
                Booking
                        .builder()
                        .startTime(LocalTime.now().minusMinutes(15L).truncatedTo(ChronoUnit.MINUTES))
                        .endTime(LocalTime.now().plusMinutes(45L).truncatedTo(ChronoUnit.MINUTES))
                        .build()
        );

        assert (unsorted.get(0).getStartTime().isAfter(unsorted.get(1).getStartTime()));
        assert (unsorted.get(0).getEndTime().isAfter(unsorted.get(1).getEndTime()));

        final List<Booking> sorted = bookingServiceUtil.sortByStartTime(unsorted);
        assert (sorted.get(0).getStartTime().isBefore(sorted.get(1).getStartTime()));
        assert (sorted.get(0).getEndTime().isBefore(sorted.get(1).getEndTime()));
    }

    @Test
    void isWithinWorkingHours() {
        final LocalTime workingHoursStartTime = LocalTime.of(8, 0);
        final LocalTime workingHoursEndTime = LocalTime.of(10, 0); // Kort arbetsdag

        final Booking validBooking = Booking
                .builder()
                .startTime(LocalTime.of(8, 0))
                .endTime(LocalTime.of(10, 0))
                .build();
        final Booking invalidBooking = Booking
                .builder()
                .startTime(LocalTime.of(8, 0))
                .endTime(LocalTime.of(22, 0)) // Lååångt möte
                .build();

        assertTrue(bookingServiceUtil.isWithinWorkingHours(validBooking, workingHoursStartTime, workingHoursEndTime));
        assertFalse(bookingServiceUtil.isWithinWorkingHours(invalidBooking, workingHoursStartTime, workingHoursEndTime));
    }

    @Test
    void isAtLeastFifteenMinutes() {

        final Booking validBooking = Booking
                .builder()
                .startTime(LocalTime.now().truncatedTo(ChronoUnit.MINUTES))
                .endTime(LocalTime.now().plusMinutes(16).truncatedTo(ChronoUnit.MINUTES))
                .build();
        final Booking invalidBooking = Booking
                .builder()
                .startTime(LocalTime.now().truncatedTo(ChronoUnit.MINUTES))
                .endTime(LocalTime.now().plusMinutes(10).truncatedTo(ChronoUnit.MINUTES))

                .build();

        assertTrue(bookingServiceUtil.isAtLeastFifteenMinutes(validBooking));
        assertFalse(bookingServiceUtil.isAtLeastFifteenMinutes(invalidBooking));
    }

    @Test
    void createTimeSlot() {
        final LocalTime startTime = LocalTime.of(10, 0);
        final LocalTime endTime = LocalTime.of(12, 0);
        final TimeSlot expected = TimeSlot
                .builder()
                .startTime(startTime)
                .endTime(endTime)
                .duration(Duration.ofHours(2L))
                .build();

        assertEquals(expected.getStartTime(), bookingServiceUtil.createTimeSlot(startTime, endTime).getStartTime());
        assertEquals(expected.getEndTime(), bookingServiceUtil.createTimeSlot(startTime, endTime).getEndTime());
        assertEquals(expected.getDuration(), bookingServiceUtil.createTimeSlot(startTime, endTime).getDuration());
    }
}