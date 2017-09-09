package com.mousterian.schema.model.jdbc;

import lombok.Data;

public @Data class Column {
    public String name;
    public DataType type;
}
