package com.mousterian.schema;


import com.mousterian.schema.model.jdbc.Column;
import com.mousterian.schema.model.jdbc.DataType;
import com.mousterian.schema.model.jdbc.Schema;
import com.mousterian.schema.model.jdbc.Table;
import com.mousterian.schema.model.schemaform.Model;
import com.mousterian.schema.model.schemaform.SchemaFormData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.support.JdbcUtils;
import org.springframework.jdbc.support.MetaDataAccessException;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.sql.DataSource;
import java.util.*;

@RestController
public class SchemaController {


    private DataSource dataSource;

    @Autowired
    public SchemaController(DataSource dataSource){

        this.dataSource = dataSource;
    }

    @RequestMapping("/schema")
    public Schema getSchema(@RequestParam(value="schemaName", defaultValue="test") String schemaName) {
        SchemaMetadataExtractor extractor = new SchemaMetadataExtractor(schemaName);
        Schema schema = null;
        try {
            schema = (Schema)JdbcUtils.extractDatabaseMetaData(dataSource, extractor);
        } catch (MetaDataAccessException e) {
            e.printStackTrace();
        }
        return schema;
    }

    @RequestMapping("/schemaformdata/{schema}/{table}")
    public SchemaFormData getSchemaFormData(@PathVariable String schema, @PathVariable String table) {
        SchemaFormData schemaFormData = new SchemaFormData();
        // this is a bit crap, but required as URL path variables get normalised to lower case
        // but H2 wants uppercase for table names
        String h2SchemaName = schema.toUpperCase();
        String h2TableName = table.toUpperCase();
        System.out.print("Got schema: " + schema + ", and table: " + table);
        Schema jdbcSchema = getSchema(h2SchemaName);

        schemaFormData.setSchema(populateJsonSchema(jdbcSchema, h2TableName));

        schemaFormData.setForm(populateJsonForm(jdbcSchema, h2TableName));

        schemaFormData.setModel(new Model());

        return schemaFormData;
    }

    private List<Object> populateJsonForm(Schema jdbcSchema, String tableName) {
        List<Object> form = new ArrayList<>();
        // it's weird, this doesn't match to any id in the schema,
        // but without it Angular Schema Form doesn't work
        // (and the default "*" renders the whole form twice!)
        form.add("test");
        for (Table table : jdbcSchema.getTables()) {
            if (table.getName().equals(tableName)) {
                // TO DO: determine whether this really is Map<String,String> and not Map<String,Object>

                for (Column column : table.getColumns()){
                    Map<String,String> formField = new LinkedHashMap<>();
                    formField.put("key",column.getName());
                    form.add(formField);
                }

                Map<String,String> submit = new LinkedHashMap<>();
                submit.put("type","submit");
                submit.put("style","btn-info");
                submit.put("title", "Save");
                form.add(submit);
            }
        }
        return form;
    }

    // TO DO: this is horrible to read, perhaps json schema and jdbc schema classes should be in separate controllers?
    private com.mousterian.schema.model.schemaform.Schema populateJsonSchema(Schema jdbcSchema, String tableName) {
        com.mousterian.schema.model.schemaform.Schema jsonSchema = new com.mousterian.schema.model.schemaform.Schema();

        for (Table table : jdbcSchema.getTables()) {
            if (table.getName().equals(tableName)) {
                jsonSchema.setTitle(tableName);

                List<String> required = new ArrayList<>();
                Map<String,Map<String,Object>> properties = new LinkedHashMap<>();

                for (Column column : table.getColumns()) {
                    Map<String,Object> fieldProperties = new LinkedHashMap<>();
                    // TO DO: pull these keys out into a constants class / enum
                    fieldProperties.put("title", column.getName());
                    fieldProperties.put("type", getJsonSchemaTypeForJdbcType(column.getType()));

                    // TO DO: when we add nullable support this should be conditional
                    required.add(column.getName());

                    properties.put(column.getName(), fieldProperties);
                }
                jsonSchema.setProperties(properties);
                jsonSchema.setRequired(required);
            }
        }
        return jsonSchema;
    }

    private String getJsonSchemaTypeForJdbcType(DataType type) {
        switch (type) {
            case INTEGER:
            case BIGINT:
                return "integer";
            case BOOLEAN:
                return "boolean";
            case DECIMAL:
            case DOUBLE:
                return "number";
            case TIME:
            case DATE:
                return "object";
            case VARCHAR:
                return "string";
            case BLOB:
                return "blob";
            default:
                throw new RuntimeException("SQL Data Type " + type + " not mapped to any JSON schema type.");
        }
    }

}
