package com.esprit.microservice.resourcemanagement.services;

import com.esprit.microservice.resourcemanagement.entities.Resource;
import com.esprit.microservice.resourcemanagement.repositories.ResourceRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
@AllArgsConstructor
public class ResourceService implements IResourceService{

    private ResourceRepository resourceRepository;
    @Override
    public List<Resource> getAllResources() {
        return resourceRepository.findAll();
    }

    @Override
    public Resource getResourceById(UUID id) {
        return resourceRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Resource not found with ID: " + id));
    }

    @Override
    @Transactional
    public Resource createResource(Resource resource) {
        return resourceRepository.save(resource);
    }

    @Override
    public Resource updateResource(UUID id, Resource resourceDetails) {
        Resource resource = getResourceById(id);
        resource.setName(resourceDetails.getName());
        resource.setType(resourceDetails.getType());
        resource.setDescription(resourceDetails.getDescription());
        resource.setAvailable(resourceDetails.isAvailable());
        resource.setCostPerHour(resourceDetails.getCostPerHour());
        resource.setLocation(resourceDetails.getLocation());
        resource.setLastBookedDate(resourceDetails.getLastBookedDate());
        return resourceRepository.save(resource);
    }

    @Override
    public void deleteResource(UUID id) {
        if (!resourceRepository.existsById(id)) {
            throw new EntityNotFoundException("Resource not found with ID: " + id);
        }
        resourceRepository.deleteById(id);

    }
}
