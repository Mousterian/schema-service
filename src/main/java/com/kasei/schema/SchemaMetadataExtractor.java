package com.kasei.schema;

import org.springframework.jdbc.support.DatabaseMetaDataCallback;
import org.springframework.jdbc.support.MetaDataAccessException;

import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class SchemaMetadataExtractor implements DatabaseMetaDataCallback {

    private String schemaName;

    SchemaMetadataExtractor(String schemaName) {

        this.schemaName = schemaName;
    }

    @Override
    public Object processMetaData(DatabaseMetaData dbmd) throws SQLException, MetaDataAccessException {
        Schema schema = new Schema();
        schema.driverName = dbmd.getDriverName();
//        schema.schemaName = dbmd.getSchemas()

        ResultSet tables = dbmd.getTables(null, schemaName, null, null);
        List<String> tableNames = new ArrayList<>();
        while (tables.next()) {
            System.out.println("Found resultset row...");
            tableNames.add(tables.getString("TABLE_NAME"));
        }
        schema.tables = tableNames;
        return schema;
    }
}
