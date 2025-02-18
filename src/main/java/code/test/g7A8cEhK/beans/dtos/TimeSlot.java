package code.test.g7A8cEhK.beans.dtos;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

import java.time.Duration;
import java.time.LocalTime;

@Getter
@Builder
@ToString
public class TimeSlot {
    private LocalTime startTime;
    private LocalTime endTime;
    private Duration duration;
}
