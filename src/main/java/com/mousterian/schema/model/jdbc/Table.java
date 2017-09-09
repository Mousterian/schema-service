package com.mousterian.schema.model.jdbc;

import lombok.Data;

import java.util.List;

public @Data class Table {

    private String name;

    private List<Column> columns;
}
