package code.test.g7A8cEhK.mappers;


import code.test.g7A8cEhK.beans.Booking;

import java.util.List;
import java.util.stream.Collectors;

public class BookingMapper {

    public static code.test.g7A8cEhK.beans.dtos.Booking toBookingDto(Booking booking) {
        return code.test.g7A8cEhK.beans.dtos.Booking
                .builder()
                .startTime(booking.getStartTime())
                .endTime(booking.getEndTime())
                .build();
    }

    public static List<code.test.g7A8cEhK.beans.dtos.Booking> toBookingDto(List<Booking> bookings) {
        return bookings.stream().map(BookingMapper::toBookingDto).collect(Collectors.toList());
    }
}
