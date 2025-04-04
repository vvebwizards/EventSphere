package com.esprit.microservice.resourcemanagement.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ResourceUtilizationReport {
    @Schema(description = "Name of the resource")
    private String resourceName;
    @Schema(description = "Total number of bookings for the resource")
    private Long totalBookings;
    @Schema(description = "Total hours the resource has been booked")
    private Long  totalHoursBooked;


}
