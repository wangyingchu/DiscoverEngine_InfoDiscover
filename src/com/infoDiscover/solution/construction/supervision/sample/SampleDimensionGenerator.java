package com.infoDiscover.solution.construction.supervision.sample;

import com.infoDiscover.common.util.FileUtil;
import com.infoDiscover.infoDiscoverEngine.dataMart.DimensionType;
import com.infoDiscover.infoDiscoverEngine.dataMart.PropertyType;
import com.infoDiscover.infoDiscoverEngine.infoDiscoverBureau.InfoDiscoverSpace;
import com.infoDiscover.infoDiscoverEngine.util.exception.InfoDiscoveryEngineDataMartException;
import com.infoDiscover.infoDiscoverEngine.util.exception.InfoDiscoveryEngineInfoExploreException;
import com.infoDiscover.infoDiscoverEngine.util.exception.InfoDiscoveryEngineRuntimeException;
import com.infoDiscover.solution.arch.demo.UserRoleDataImporter;
import com.infoDiscover.solution.common.dimension.DimensionManager;
import com.infoDiscover.solution.construction.supervision.database.SupervisionSolutionConstants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by sun.
 */
public class SampleDimensionGenerator {
    private final static Logger logger = LoggerFactory.getLogger(SampleDimensionGenerator.class);

    private static String[][] DIMENSION_LIST_TO_CREATE = new String[][]{
            {SupervisionSolutionConstants.DIMENSION_ROLE_WITH_PREFIX, SampleDataSet.FILE_ROLE},
            {SupervisionSolutionConstants.DIMENSION_USER_WITH_PREFIX, SampleDataSet.FILE_USER},
            {SupervisionSolutionConstants.DIMENSION_CONSTRUCTIONTYPE_WITH_PREFIX, SampleDataSet
                    .FILE_DIMENSION_CONSTRUCTION_TYPE},
            {SupervisionSolutionConstants.DIMENSION_COMPANYCLASSIFICATION_WTIH_PREFIX,
                    SampleDataSet.FILE_DIMENSION_COMPANY_CLASSIFICATION},
            {SupervisionSolutionConstants.DIMENSION_ASSIGN_MODEL_WTIH_PREFIX, SampleDataSet
                    .FILE_DIMENSION_ASSIGN_MODEL},
            {SupervisionSolutionConstants.DIMENSION_EXECUTIVE_DEPARTMENT_WTIH_PREFIX,
                    SampleDataSet.FILE_DIMENSION_EXECUTIVE_DEPARTMENT},
            {SupervisionSolutionConstants.DIMENSION_GOVERNMENT_APPROVAL_AUTHORITY_WTIH_PREFIX,
                    SampleDataSet.FILE_DIMENSION_GOVERNMENT_APPROVAL_AUTHORITY},
            {SupervisionSolutionConstants.DIMENSION_ISSUE_CLASSIFICATION_WTIH_PREFIX,
                    SampleDataSet.FILE_DIMENSION_ISSUE_CLASSIFICATION},
            {SupervisionSolutionConstants.DIMENSION_LAND_PROPERTY_WTIH_PREFIX, SampleDataSet
                    .FILE_DIMENSION_LAND_PROPERTY},
            {SupervisionSolutionConstants.DIMENSION_ASSERT_FIRST_CLASSIFICATION_WTIH_PREFIX,
                    SampleDataSet.FILE_DIMENSION_ASSET_FIRST_CLASSIFICATION},
            {SupervisionSolutionConstants.DIMENSION_ASSET_SECOND_CLASSIFICATION_WTIH_PREFIX,
                    SampleDataSet.FILE_DIMENSION_ASSET_SECOND_CLASSIFICATION},
            {SupervisionSolutionConstants.DIMENSION_PROJECT_CLASSIFICATION_WTIH_PREFIX,
                    SampleDataSet.FILE_DIMENSION_PROJECT_CLASSIFICATION},
            {SupervisionSolutionConstants.DIMENSION_PROJECT_SITE_CLASSIFICATION_WTIH_PREFIX,
                    SampleDataSet.FILE_DIMENSION_PROJECT_SITE_CLASSIFICATION},
            {SupervisionSolutionConstants.DIMENSION_PROJECT_SCOPE_WTIH_PREFIX, SampleDataSet
                    .FILE_DIMENSION_PROJECT_SCOPE},
            {SupervisionSolutionConstants
                    .DIMENSION_PROJECT_CONSTRUCTION_CLASSIFICATION_WTIH_PREFIX, SampleDataSet
                    .FILE_DIMENSION_PROJECT_CONSTRUCTION_CLASSIFICATION}
    };

    private InfoDiscoverSpace ids;

    public SampleDimensionGenerator(InfoDiscoverSpace ids) {
        this.ids = ids;
    }

    public void createDimensionType() throws
            InfoDiscoveryEngineDataMartException, InfoDiscoveryEngineRuntimeException {
        for (String[] array : DIMENSION_LIST_TO_CREATE) {
            String dimensionTypeName = array[0];

            if (!ids.hasDimensionType(dimensionTypeName)) {

                DimensionType type = ids.addDimensionType(dimensionTypeName);

                if (dimensionTypeName.equalsIgnoreCase(SupervisionSolutionConstants
                        .DIMENSION_USER_WITH_PREFIX)) {
                    type.addTypeProperty("userId", PropertyType.STRING);
                    type.addTypeProperty("userName", PropertyType.STRING);
                } else if (dimensionTypeName.equalsIgnoreCase(SupervisionSolutionConstants
                        .DIMENSION_ROLE_WITH_PREFIX)) {
                    type.addTypeProperty("roleId", PropertyType.STRING);
                    type.addTypeProperty("roleName", PropertyType.STRING);
                } else {
                    type.addTypeProperty("dimensionId", PropertyType.STRING);
                    type.addTypeProperty("dimensionName", PropertyType.STRING);
                    if (dimensionTypeName.equalsIgnoreCase(SupervisionSolutionConstants
                            .DIMENSION_EXECUTIVE_DEPARTMENT_WTIH_PREFIX)) {
                        type.addTypeProperty("isAuthorityDepartment", PropertyType.BOOLEAN);
                    }
                }
            }

        }


    }

    public void createDimensionSampleData(InfoDiscoverSpace ids) throws
            InfoDiscoveryEngineRuntimeException {
        DimensionManager manager = new DimensionManager(this.ids);

        for (String[] array : DIMENSION_LIST_TO_CREATE) {
            String dimensionTypeName = array[0];
            String file = array[1];

            if (dimensionTypeName.equalsIgnoreCase(SupervisionSolutionConstants
                    .DIMENSION_USER_WITH_PREFIX)) {
                for (Map<String, Object> properties : getPropertiesFromLine(file, "userId",
                        "userName")) {
                    addMoreProperty(dimensionTypeName, properties);
                    manager.createDimension(dimensionTypeName, properties);
                }
            } else if (dimensionTypeName.equalsIgnoreCase(SupervisionSolutionConstants
                    .DIMENSION_ROLE_WITH_PREFIX)) {
                for (Map<String, Object> properties : getPropertiesFromLine(file, "roleId",
                        "roleName")) {
                    manager.createDimension(dimensionTypeName, properties);
                }
            } else {
                for (Map<String, Object> properties : getPropertiesFromLine(file, "dimensionId",
                        "dimensionName")) {
                    manager.createDimension(dimensionTypeName, properties);
                }
            }

        }
    }


    public void linkUsersToRole(InfoDiscoverSpace ids, String userRoleFile, String
            roleDimensionType, String userDimensionType, String relationType) throws
            InfoDiscoveryEngineInfoExploreException {

        UserRoleDataImporter.createRoles(ids, userRoleFile, roleDimensionType,
                userDimensionType, relationType);
    }

    public static void addMoreProperty(String dimensionType, Map<String, Object> properties) {
        if (dimensionType.equals(SupervisionSolutionConstants
                .DIMENSION_EXECUTIVE_DEPARTMENT_WTIH_PREFIX)) {
            String dimensionId = properties.get("dimensionId").toString();
            if (getGovernmentApprovalAuthority().contains(dimensionId)) {
                properties.put("isAuthorityDepartment", true);
            } else {
                properties.put("isAuthorityDepartment", false);
            }
        }
    }

    public static List<Map<String, Object>> getPropertiesFromLine(String file, String
            propertyIdName, String propertyDisplayName) {

        List<Map<String, Object>> propertyList = new ArrayList<>();

        List<String> lines = FileUtil.readLinesIntoList(file);
        for (String line : lines) {
            String[] values = line.split(",");
            String dimensionId = values[0];
            String dimensionName = values[1];
            Map<String, Object> map = new HashMap<>();
            map.put(propertyIdName, dimensionId);
            map.put(propertyDisplayName, dimensionName);

            propertyList.add(map);
        }

        return propertyList;
    }

    public static List<String> getGovernmentApprovalAuthority() {
        List<String> authorities = new ArrayList<>();

        List<String> lines = FileUtil.readLinesIntoList(SampleDataSet
                .FILE_DIMENSION_GOVERNMENT_APPROVAL_AUTHORITY);

        for (String line : lines) {
            String authorityId = line.split(",")[0];
            authorities.add(authorityId);
        }

        return authorities;
    }

}
