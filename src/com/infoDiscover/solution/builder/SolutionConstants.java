package com.infoDiscover.solution.builder;

/**
 * Created by sun.
 */
public class SolutionConstants {

    // space name
    public final static String SOLUTION_SPACE_NAME = "InfoDiscover_Solution";

    // fact type
    public final static String FACT_TYPE_SOLUTION_TEMPLATE = "SOLUTION_TEMPLATE";

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


    // json
    public final static String JSON_PREFIX = "prefix";
    public final static String JSON_STD_FACTTYPE = "stdFactType";
    public final static String JSON_FACT = "fact";
    public final static String JSON_DIMENSION = "dimension";
    public final static String JSON_RELATION = "relation";

}


