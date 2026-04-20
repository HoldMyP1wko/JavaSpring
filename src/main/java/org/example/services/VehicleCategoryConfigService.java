package org.example.services;

import org.example.models.CategoryConfig;
import org.example.repositories.VehicleCategoryConfigRepository;
import java.util.List;

public class VehicleCategoryConfigService {
    private final VehicleCategoryConfigRepository configRepository;

    public VehicleCategoryConfigService(VehicleCategoryConfigRepository configRepository) {
        this.configRepository = configRepository;
    }

    public List<CategoryConfig> getAllConfigs() {
        return configRepository.findAll();
    }

    public CategoryConfig getConfigByName(String name) {
        return configRepository.findByName(name);
    }
}