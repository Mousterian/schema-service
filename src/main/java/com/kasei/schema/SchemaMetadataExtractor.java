package com.kasei.schema;

import com.kasei.schema.model.jdbc.Column;
import com.kasei.schema.model.jdbc.DataType;
import com.kasei.schema.model.jdbc.Schema;
import com.kasei.schema.model.jdbc.Table;
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
        schema.setDriverName(dbmd.getDriverName());
        // TO DO: should really call dbmd.getSchemas() and double check we are talking to the right schema
        schema.setSchemaName(schemaName);

        schema.setTables(getTables(dbmd));
        return schema;
    }

    private List<Table> getTables(DatabaseMetaData dbmd) throws SQLException {
        ResultSet tablesMetadata = dbmd.getTables(null, schemaName, null, null);
        List<Table> tables = new ArrayList<>();
        while (tablesMetadata.next()) {
            Table table = new Table();
            String tableName = tablesMetadata.getString("TABLE_NAME");
            table.setName(tableName);
            table.setColumns(getColumns(dbmd, schemaName, tableName));
            tables.add(table);
        }
        return tables;
    }

    private List<Column> getColumns(DatabaseMetaData dbmd, String schemaName, String tableName) throws SQLException {
        ResultSet columnsMetadata = dbmd.getColumns(null, schemaName, tableName, null);
        List<Column> columns = new ArrayList<>();
        while (columnsMetadata.next()) {
            Column column = new Column();
            column.setName(columnsMetadata.getString("COLUMN_NAME"));
            column.setType(DataType.fromString(columnsMetadata.getString("DATA_TYPE")));
            columns.add(column);
        }
        return columns;
    }
}
