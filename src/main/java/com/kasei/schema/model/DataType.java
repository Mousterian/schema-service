package com.kasei.schema.model;

import java.sql.Types;

public enum DataType {

    INTEGER,
    BIGINT,
    BOOLEAN,
    DECIMAL,
    DOUBLE,
    TIME,
    DATE,
    VARCHAR;

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

            default: {
                throw new RuntimeException("SQL Data Type " + dataType + " not supported.");
            }
        }
    }
}
