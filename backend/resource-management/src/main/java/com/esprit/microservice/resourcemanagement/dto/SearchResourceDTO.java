package com.esprit.microservice.resourcemanagement.dto;

import com.esprit.microservice.resourcemanagement.entities.ResourceType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class SearchResourceDTO {
    private String location;
    private ResourceType type;
    private String priceRange;
    private boolean availability;


}
