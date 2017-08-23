package com.kasei.schema;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.support.JdbcUtils;
import org.springframework.jdbc.support.MetaDataAccessException;
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

}
