package org.example.repositories;

import org.example.models.CategoryConfig;

import java.util.List;

public interface VehicleCategoryConfigRepository {
    List<CategoryConfig> findAll();
    CategoryConfig findByName(String name);
}
