package com.esprit.microservice.resourcemanagement.services;

import com.esprit.microservice.resourcemanagement.entities.Resource;

import java.util.List;
import java.util.UUID;

public interface IResourceService {

    public List<Resource> getAllResources();
    public Resource getResourceById(UUID id);
    public Resource createResource(Resource resource);
    public Resource updateResource(UUID id, Resource resourceDetails);
    public void deleteResource(UUID id);
}
