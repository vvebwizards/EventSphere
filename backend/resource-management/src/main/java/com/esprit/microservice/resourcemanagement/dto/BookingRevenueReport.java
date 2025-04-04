package com.esprit.microservice.resourcemanagement.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class BookingRevenueReport {

    private String ressourceName ;
    private Double bookingPourcentage ;
    private Double totalRevenue ;
}
