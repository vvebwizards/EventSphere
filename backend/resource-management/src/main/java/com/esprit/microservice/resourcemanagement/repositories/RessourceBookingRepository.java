package com.esprit.microservice.resourcemanagement.repositories;

import com.esprit.microservice.resourcemanagement.dto.ResourceUtilizationReport;
import com.esprit.microservice.resourcemanagement.entities.RessourceBooking;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Repository
public interface RessourceBookingRepository extends JpaRepository<RessourceBooking, UUID> {
    List<RessourceBooking> findByResourceIdAndStartTimeBetween(UUID resourceId, LocalDateTime start, LocalDateTime end);
    List<RessourceBooking> findByResourceIdAndStartTimeBeforeAndEndTimeAfter(UUID resourceId, LocalDateTime startTime , LocalDateTime endTime);
    @Query(value = """
    SELECT
    r.name AS resourceName,
    COUNT(*) AS totalBookings,
     CAST(ROUND(SUM(EXTRACT(EPOCH FROM (b.end_time - b.start_time))/3600)) AS BIGINT) AS totalHoursBooked
     FROM ressource_booking b
     JOIN resource r ON b.resource_id = r.id
     WHERE b.start_time BETWEEN :startDate AND :endDate
     GROUP BY r.name              
""", nativeQuery = true)
    List<ResourceUtilizationReport> getResourceUtilizationReport(
            @Param("startDate") LocalDateTime startDate,
            @Param("endDate") LocalDateTime endDate
    );

}
