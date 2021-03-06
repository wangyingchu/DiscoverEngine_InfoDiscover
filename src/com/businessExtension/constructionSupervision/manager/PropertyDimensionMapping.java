package com.businessExtension.constructionSupervision.manager;

import com.businessExtension.constructionSupervision.constants.DatabaseConstants;
import com.businessExtension.constructionSupervision.constants.JsonConstants;

/**
 * Created by sun.
 */
public class PropertyDimensionMapping {

    public static final String[] CONSTRUCTION_TYPE_MAPPING = {
            JsonConstants.JSON_CONSTRUCTION_TYPE,
            DatabaseConstants.DIMENSION_CONSTRUCTION_TYPE_WITH_PREFIX,
            DatabaseConstants.RELATION_CONSTRUCTION_TYPE_WITH_PREFIX};

    public static final String[] ASSIGN_MODEL_MAPPING_MAPPING = {
            JsonConstants.JSON_ASSIGN_MODEL,
            DatabaseConstants.DIMENSION_ASSIGN_MODEL_WITH_PREFIX,
            DatabaseConstants.RELATION_ASSIGN_MODEL_WITH_PREFIX
    };

    public static final String[] ISSUE_CLASSIFICATION_MAPPING = {
            JsonConstants.JSON_ISSUE_CLASSIFICATION,
            DatabaseConstants.DIMENSION_ISSUE_CLASSIFICATION_WITH_PREFIX,
            DatabaseConstants.RELATION_ISSUE_CLASSIFICATION_WITH_PREFIX
    };

    public static final String[] LAND_PROPERTY_MAPPING = {
            JsonConstants.JSON_LAND_PROPERTY,
            DatabaseConstants.DIMENSION_LAND_PROPERTY_WITH_PREFIX,
            DatabaseConstants.RELATION_LAND_PROPERTY_WITH_PREFIX
    };

    public static final String[] PROJECT_CLASSIFICATION_MAPPING = {
            JsonConstants.JSON_PROJECT_CLASSIFICATION,
            DatabaseConstants.DIMENSION_PROJECT_CLASSIFICATION_WITH_PREFIX,
            DatabaseConstants.RELATION_PROJECT_CLASSIFICATION_WITH_PREFIX
    };

    public static final String[] PROJECT_SITE_CLASSIFICATION_MAPPING = {
            JsonConstants.JSON_PROJECT_SITE_CLASSIFICATION,
            DatabaseConstants.DIMENSION_PROJECT_SITE_CLASSIFICATION_WITH_PREFIX,
            DatabaseConstants.RELATION_PROJECT_SITE_CLASSIFICATION_WITH_PREFIX
    };

    public static final String[] PROJECT_SCOPE_MAPPING = {
            JsonConstants.JSON_PROJECT_SCOPE,
            DatabaseConstants.DIMENSION_PROJECT_SCOPE_WITH_PREFIX,
            DatabaseConstants.RELATION_PROJECT_SCOPE_WITH_PREFIX
    };

    public static final String[] PROJECT_CONSTRUCTION_CLASSIFICATION_MAPPING = {
            JsonConstants.JSON_PROJECT_CONSTRUCTION_CLASSIFICATION,
            DatabaseConstants.DIMENSION_PROJECT_CONSTRUCTION_CLASSIFICATION_WITH_PREFIX,
            DatabaseConstants.RELATION_PROJECT_CONSTRUCTION_CLASSIFICATION_WITH_PREFIX
    };

    public static final String[] PROJECT_TYPE_MAPPING = {
            JsonConstants.JSON_PROJECT_TYPE,
            DatabaseConstants.DIMENSION_PROJECT_CONSTRUCTION_CLASSIFICATION_WITH_PREFIX,
            DatabaseConstants.RELATION_PROJECT_CONSTRUCTION_CLASSIFICATION_WITH_PREFIX
    };

    public static final String[] PROJECT_ADDRESS_MAPPING = {
            JsonConstants.JSON_PROJECT_ADDRESS,
            DatabaseConstants.DIMENSION_PROJECT_ADDRESS_WITH_PREFIX,
            DatabaseConstants.RELATION_PROJECT_ADDRESS_AT_WITH_PREFIX
    };

    public static final String[][] allDimensionMappings = {
            CONSTRUCTION_TYPE_MAPPING, ASSIGN_MODEL_MAPPING_MAPPING,
            ISSUE_CLASSIFICATION_MAPPING, LAND_PROPERTY_MAPPING,
            PROJECT_CLASSIFICATION_MAPPING, PROJECT_SITE_CLASSIFICATION_MAPPING,
            PROJECT_SCOPE_MAPPING, PROJECT_CONSTRUCTION_CLASSIFICATION_MAPPING,
            PROJECT_TYPE_MAPPING, PROJECT_ADDRESS_MAPPING};

}
