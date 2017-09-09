package com.mousterian.schema;


import com.mousterian.schema.model.jdbc.Schema;
import com.mousterian.schema.model.schemaform.SchemaFormData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.support.JdbcUtils;
import org.springframework.jdbc.support.MetaDataAccessException;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.sql.DataSource;

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
        SchemaFormData schemaFormData = null;
        System.out.print("Got schema: " + schema + ", and table: " + table);

        return schemaFormData;
    }

}
