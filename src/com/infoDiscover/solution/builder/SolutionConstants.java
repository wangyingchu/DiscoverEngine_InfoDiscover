package com.infoDiscover.solution.builder;

/**
 * Created by sun.
 */
public class SolutionConstants {

    // space name
    public final static String SOLUTION_SPACE_NAME = "InfoDiscover_Solution";

    // fact type
    public final static String FACT_TYPE_SOLUTION_TEMPLATE = "ID_SOLUTION_TEMPLATE";

    // fact properties
    public final static String PROPERTY_STD_ID = "templateId";
    public final static String PROPERTY_STD_PREFIX = "prefix";
    public final static String PROPERTY_STD_FACT_DEFINITION = "fact";
    public final static String PROPERTY_STD_DIMENSION_DEFINITION = "dimension";
    public final static String PROPERTY_STD_RELATION_DEFINITION = "relation";
    public final static String PROPERTY_STD_CREATED_BY = "createdBy";
    public final static String PROPERTY_STD_CREATED_AT = "createdAt";
    public final static String PROPERTY_STD_MODIFIED_BY = "modifiedBy";
    public final static String PROPERTY_STD_MODIFIED_AT = "modifiedAt";

    public final static String[][] FACT_TYPE_PROPERTIES = {
            {PROPERTY_STD_ID, "String"},
            {PROPERTY_STD_PREFIX, "String"},
            {PROPERTY_STD_FACT_DEFINITION, "String"},
            {PROPERTY_STD_DIMENSION_DEFINITION, "String"},
            {PROPERTY_STD_RELATION_DEFINITION, "String"},
            {PROPERTY_STD_CREATED_BY, "String"},
            {PROPERTY_STD_CREATED_AT, "Date"},
            {PROPERTY_STD_MODIFIED_BY, "String"},
            {PROPERTY_STD_MODIFIED_AT, "Date"}
    };

    public final static String FACT_TYPE = "FACT";
    public final static String DIMENSION_TYPE = "DIMENSION";


    // json
    public final static String JSON_PREFIX = "prefix";
    public final static String JSON_FACTS = "facts";
    public final static String JSON_DIMENSIONS = "dimensions";
    public final static String JSON_PROPERTIES= "properties";
    public final static String JSON_PROPERTY_NAME = "propertyName";
    public final static String JSON_PROPERTY_VALUE = "propertyValue";
    public final static String JSON_PROPERTY_TYPE = "propertyType";
    public final static String JSON_TYPE = "type";
    public final static String JSON_TYPE_NAME = "typeName";

    // isUniqueKey
    public final static String JSON_IS_UNIQUE_KEY = "isUniqueKey";

    // relation mapping
    public final static String JSON_MAPPINGS = "mappings";
    public final static String JSON_RELATIONS = "relations";
    public final static String JSON_RELATION_TYPE_NAME = "relationTypeName";
    public final static String JSON_FACT_TO_DIMENSION_MAPPING = "factToDimension";
    public final static String JSON_FACT_TO_FACT_MAPPING = "factToFact";
    public final static String JSON_DIMENSION_TO_DIMENSION_MAPPING = "dimensionToDimension";
    public final static String JSON_FACT_TO_DATE_DIMENSION_MAPPING = "factToDateDimension";

    public final static String JSON_FROM_TYPE = "fromType";
    public final static String JSON_FROM_PROPERTY = "fromProperty";
    public final static String JSON_TO_TYPE = "toType";
    public final static String JSON_TO_PROPERTY = "toProperty";

}


