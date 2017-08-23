package com.kasei.schema;

import lombok.Data;

import java.util.List;

public @Data class Schema {
    public String driverName;
    public String schemaName;
    public List<String> tables;
}
