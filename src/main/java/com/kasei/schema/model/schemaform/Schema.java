package com.kasei.schema.model.schemaform;

import lombok.Data;

import java.util.List;
import java.util.Map;

public @Data class Schema {
    private String type = "object";
    private String title;
    private Map<String,Map<String,Object>> properties;
    private List<String> required;
}
