package code.test.g7A8cEhK.beans.dtos;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

import java.io.Serializable;
import java.time.LocalTime;

@Getter
@Builder
@ToString
public class Booking implements Serializable {

    private LocalTime startTime;

    private LocalTime endTime;
}
