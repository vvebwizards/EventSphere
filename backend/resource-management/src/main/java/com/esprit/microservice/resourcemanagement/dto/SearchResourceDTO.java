package com.esprit.microservice.resourcemanagement.dto;

import com.esprit.microservice.resourcemanagement.entities.ResourceType;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class SearchResourceDTO {
    @Schema
    private String location;
    @Schema
    private ResourceType type;
    @Schema
    private String priceRange;
    @Schema
    private Boolean  availability;


}
