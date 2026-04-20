package org.example.models;


import org.example.services.VehicleCategoryConfigService;

import java.util.Map;

public class VehicleValidator {
    private final VehicleCategoryConfigService configService;

    public VehicleValidator(VehicleCategoryConfigService configService) {
        this.configService = configService;
    }

    public boolean validate(Vehicle vehicle) throws IllegalArgumentException {
        if (vehicle.getBrand() == null || vehicle.getBrand().trim().isEmpty()) throw new IllegalArgumentException("Marka nie może być pusta");
        if (vehicle.getModel() == null || vehicle.getModel().trim().isEmpty()) throw new IllegalArgumentException("Model nie może być pusty.");
        if (vehicle.getYear() < 1900 || vehicle.getYear() > 2027) throw new IllegalArgumentException("Niepoprawny rok produkcji");
        if (vehicle.getPrice() < 0) throw new IllegalArgumentException("Cena nie może być ujemna.");

        CategoryConfig config = configService.getConfigByName(vehicle.getCategory());
        if (config == null) throw new IllegalArgumentException(
                "Nieznana kategoria pojazdu: " + vehicle.getCategory());

        Map<String, Object> actualAttrs = vehicle.getAdditionalAttributes();
        Map<String, String> expectedAttrs = config.getAttributes();

        if (expectedAttrs != null) {
            for (Map.Entry<String, String> entry : expectedAttrs.entrySet()) {
                String expectedAttrName = entry.getKey();
                String expectedAttrType = entry.getValue();

                if (!actualAttrs.containsKey(expectedAttrName)) {
                    throw new IllegalArgumentException("Brak wymaganego atrybutu: " + expectedAttrName);
                }

                Object actualValue = actualAttrs.get(expectedAttrName);

                switch (expectedAttrType.toLowerCase()) {
                    case "integer":
                    case "int":
                        if (!(actualValue instanceof Integer)) {
                            throw new IllegalArgumentException("Atrybut " + expectedAttrName + " musi być liczbą całkowitą.");
                        }
                        break;
                    case "number":
                    case "double":
                        if (!(actualValue instanceof Double)) {
                            throw new IllegalArgumentException("Atrybut " + expectedAttrName + " musi być liczbą zmiennoprzecinkową.");
                        }
                        break;
                    case "boolean":
                        if (!(actualValue instanceof Boolean)) {
                            throw new IllegalArgumentException("Atrybut " + expectedAttrName + " musi być wartością logiczną (true/false).");
                        }
                        break;
                    case "string":
                        if (!(actualValue instanceof String)) {
                            throw new IllegalArgumentException("Atrybut " + expectedAttrName + " musi być tekstem.");
                        }
                        break;
                }
            }
        }

        return true;
    }
}