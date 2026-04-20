package org.example.models;

import java.util.List;

public class AttributeConfig {
    private String name;
    private String type;
    private List<String> allowedValues;

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public String getType() { return type; }
    public void setType(String type) { this.type = type; }
    public List<String> getAllowedValues() { return allowedValues; }
    public void setAllowedValues(List<String> allowedValues) { this.allowedValues = allowedValues; }
}