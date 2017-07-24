package com.infoDiscover.solution.builder;

/**
 * Created by sun.
 */
public class SolutionConstants {

    // space name
    public static final String SOLUTION_SPACE_NAME = "InfoDiscover_Solution";

    // fact type
    public static final String FACT_TYPE_SOLUTION_TEMPLATE = "ID_SOLUTION_TEMPLATE";

    // solution template file name
    public static final String SOLUTION_TEMPLATE_FILE_NAME = "SolutionTemplateDefinition";
    public static final String SOLUTION_TEMPLATE_SOLUTION_DEFINITION = SOLUTION_TEMPLATE_FILE_NAME + "_SOLUTION_DEFINITION" + ".json";
    public static final String SOLUTION_TEMPLATE_FACT_TYPE_FILE_NAME = SOLUTION_TEMPLATE_FILE_NAME + "_FACT_TYPES" + ".json";
    public static final String SOLUTION_TEMPLATE_DIMENSION_TYPE_FILE_NAME = SOLUTION_TEMPLATE_FILE_NAME + "_DIMENSION_TYPES" + ".json";
    public static final String SOLUTION_TEMPLATE_RELATION_TYPE_FILE_NAME = SOLUTION_TEMPLATE_FILE_NAME + "_RELATION_TYPES" + ".json";
    public static final String SOLUTION_TEMPLATE_FACT_TO_FACT_FILE_NAME = SOLUTION_TEMPLATE_FILE_NAME + "_FACT_TO_FACT_MAPPINGS" + ".json";
    public static final String SOLUTION_TEMPLATE_FACT_TO_DIMENSION_FILE_NAME = SOLUTION_TEMPLATE_FILE_NAME + "_FACT_TO_DIMENSION_MAPPINGS" + ".json";
    public static final String SOLUTION_TEMPLATE_DIMENSION_TO_DIMENSION_FILE_NAME = SOLUTION_TEMPLATE_FILE_NAME + "_DIMENSION_TO_DIMENSION_MAPPINGS" + ".json";
    public static final String SOLUTION_TEMPLATE_DIMENSION_TO_FACT_FILE_NAME = SOLUTION_TEMPLATE_FILE_NAME + "_DIMENSION_TO_FACT_MAPPINGS" + ".json";
    public static final String SOLUTION_TEMPLATE_SOLUTION_TYPE_PROPERTY_TYPE_FILE_NAME = SOLUTION_TEMPLATE_FILE_NAME + "_SOLUTION_TYPE_PROPERTY_TYPE" + ".json";
    public static final String SOLUTION_TEMPLATE_FACT_TO_DATE_FILE_NAME = SOLUTION_TEMPLATE_FILE_NAME + "_FACT_TO_DATE_MAPPINGS" + ".json";
    public static final String SOLUTION_TEMPLATE_DIMENSION_TO_DATE_FILE_NAME = SOLUTION_TEMPLATE_FILE_NAME + "_DIMENSION_TO_DATE_MAPPINGS" + ".json";
    public static final String SOLUTION_TEMPLATE_FACT_DUPLICATE_COPY_FILE_NAME = SOLUTION_TEMPLATE_FILE_NAME + "_FACT_DUPLICATE_COPY_MAPPING" + ".json";
    public static final String SOLUTION_TEMPLATE_DIMENSION_DUPLICATE_COPY_FILE_NAME = SOLUTION_TEMPLATE_FILE_NAME + "_DIMENSION_DUPLICATE_COPY_MAPPING" + ".json";

    public static final String SOLUTION_TEMPLATE_JSON_FILE = SOLUTION_TEMPLATE_FILE_NAME + ".json";

    // fact properties
    public static final String PROPERTY_STD_ID = "templateId";
    public static final String PROPERTY_STD_PREFIX = "prefix";
    public static final String PROPERTY_STD_FACT_DEFINITION = "fact";
    public static final String PROPERTY_STD_DIMENSION_DEFINITION = "dimension";
    public static final String PROPERTY_STD_RELATION_DEFINITION = "relation";
    public static final String PROPERTY_STD_CREATED_BY = "createdBy";
    public static final String PROPERTY_STD_CREATED_AT = "createdAt";
    public static final String PROPERTY_STD_MODIFIED_BY = "modifiedBy";
    public static final String PROPERTY_STD_MODIFIED_AT = "modifiedAt";

    public static final String[][] FACT_TYPE_PROPERTIES = {
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

    public static final String FACT_TYPE = "FACT";
    public static final String DIMENSION_TYPE = "DIMENSION";


    // json
    public static final String JSON_PREFIX = "prefix";
    public static final String JSON_FACTS = "facts";
    public static final String JSON_DIMENSIONS = "dimensions";
    public static final String JSON_PROPERTIES = "properties";
    public static final String JSON_PROPERTY_NAME = "propertyName";
    public static final String JSON_PROPERTY_VALUE = "propertyValue";
    public static final String JSON_PROPERTY_TYPE = "propertyType";
    public static final String JSON_TYPE = "type";
    public static final String JSON_TYPE_NAME = "typeName";

    // isUniqueKey
    public static final String JSON_IS_UNIQUE_KEY = "isUniqueKey";

    // relation mapping
    public static final String JSON_MAPPINGS = "mappings";
    public static final String JSON_RELATIONS = "relations";
    public static final String JSON_RELATION_TYPE_NAME = "relationTypeName";
    public static final String JSON_FACT_TO_DIMENSION_MAPPING = "factToDimension";
    public static final String JSON_DIMENSION_TO_FACT_MAPPING = "dimensionToFact";
    public static final String JSON_FACT_TO_FACT_MAPPING = "factToFact";
    public static final String JSON_DIMENSION_TO_DIMENSION_MAPPING = "dimensionToDimension";
    public static final String JSON_FACT_TO_DATE_DIMENSION_MAPPING = "factToDateDimension";
    public static final String JSON_DIMENSION_TO_DATE_DIMENSION_MAPPING = "dimensionToDateDimension";
    public static final String JSON_FACT_DUPLICATE_COPY_MAPPING = "factDuplicateCopy";
    public static final String JSON_DIMENSION_DUPLICATE_COPY_MAPPING = "dimensionDuplicateCopy";

    public static final String JSON_FROM_TYPE = "fromType";
    public static final String JSON_FROM_PROPERTY = "fromProperty";
    public static final String JSON_FROM_PRIMARY_KEY = "fromPrimaryKey";
    public static final String JSON_TO_TYPE = "toType";
    public static final String JSON_TO_PROPERTY = "toProperty";
    public static final String JSON_TO_PRIMARY_KEY = "toPrimaryKey";

}


