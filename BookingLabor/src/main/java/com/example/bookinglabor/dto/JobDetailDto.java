package com.example.bookinglabor.dto;

import lombok.Builder;
import lombok.Data;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder
public class JobDetailDto {

    private LocalDateTime createdOn;
    private LocalDateTime updatedOn;
    private List<BookingDto> bookings;

}
