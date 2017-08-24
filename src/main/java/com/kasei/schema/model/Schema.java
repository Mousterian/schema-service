package com.kasei.schema.model;

import lombok.Data;

import java.util.List;

public @Data class Schema {
    private String driverName;
    private String schemaName;
    private List<Table> tables;
}
