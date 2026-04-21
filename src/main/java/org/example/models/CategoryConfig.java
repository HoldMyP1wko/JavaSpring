package org.example.models;

import lombok.Getter;
import lombok.Setter;

import java.util.Map;

@Setter
@Getter
public class CategoryConfig {

    private String category;
    private Map<String, String> attributes;

}