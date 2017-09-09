package com.mousterian.schema.model.jdbc;

import java.sql.Types;

public enum DataType {

    INTEGER,
    BIGINT,
    BOOLEAN,
    DECIMAL,
    DOUBLE,
    TIME,
    DATE,
    VARCHAR,
    BLOB;

    public static DataType fromString(String dataType) {

        switch (Integer.parseInt(dataType)) {

            case Types.INTEGER:
                return INTEGER;

            case Types.BIGINT:
                return BIGINT;

            case Types.BOOLEAN:
                return BOOLEAN;

            case Types.DECIMAL:
                return DECIMAL;

            case Types.DOUBLE:
                return DOUBLE;

            case Types.TIME:
                return TIME;

            case Types.DATE:
                return DATE;

            case Types.VARCHAR:
                return VARCHAR;

            case Types.BLOB:
                return BLOB;

            default: {
                throw new RuntimeException("SQL Data Type " + dataType + " not supported.");
            }
        }
    }
}
