package com.mousterian.schema.model.schemaform;

import lombok.Data;

import java.util.List;

public @Data class SchemaFormData {
    private Schema schema;
    // This is quite horrible, angular schema form wants a heterogenous array here,
    // with a string as the first value and Map<String,Object> for all subsequent values.
    List<Object> form;
    private Model model;
}
