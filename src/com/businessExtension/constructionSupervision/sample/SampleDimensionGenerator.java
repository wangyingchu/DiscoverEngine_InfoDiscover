package com.businessExtension.constructionSupervision.sample;

import com.infoDiscover.common.util.FileUtil;
import com.infoDiscover.infoDiscoverEngine.dataMart.Dimension;
import com.infoDiscover.infoDiscoverEngine.dataMart.DimensionType;
import com.infoDiscover.infoDiscoverEngine.dataMart.PropertyType;
import com.infoDiscover.infoDiscoverEngine.infoDiscoverBureau.InfoDiscoverSpace;
import com.infoDiscover.infoDiscoverEngine.util.exception.InfoDiscoveryEngineDataMartException;
import com.infoDiscover.infoDiscoverEngine.util.exception.InfoDiscoveryEngineInfoExploreException;
import com.infoDiscover.infoDiscoverEngine.util.exception.InfoDiscoveryEngineRuntimeException;
import com.businessExtension.arch.progress.manager.ProgressRelationManager;
import com.businessExtension.arch.progress.manager.UserManager;
import com.infoDiscover.solution.common.dimension.DimensionManager;
import com.businessExtension.constructionSupervision.constants.Constants;
import com.businessExtension.constructionSupervision.constants.DatabaseConstants;
import org.apache.commons.collections.map.HashedMap;
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
            {DatabaseConstants.DIMENSION_ROLE_WITH_PREFIX, SampleDataSet
                    .FILE_DEPARTMENT},
            {DatabaseConstants.DIMENSION_USER_WITH_PREFIX, SampleDataSet.FILE_USER},
            {DatabaseConstants.DIMENSION_CONSTRUCTION_TYPE_WITH_PREFIX, SampleDataSet
                    .FILE_DIMENSION_CONSTRUCTION_TYPE},
            {DatabaseConstants.DIMENSION_COMPANY_CLASSIFICATION_WITH_PREFIX,
                    SampleDataSet.FILE_DIMENSION_COMPANY_CLASSIFICATION},
            {DatabaseConstants.DIMENSION_ASSIGN_MODEL_WITH_PREFIX, SampleDataSet
                    .FILE_DIMENSION_ASSIGN_MODEL},
            {DatabaseConstants.DIMENSION_EXECUTIVE_DEPARTMENT_WITH_PREFIX,
                    SampleDataSet.FILE_DIMENSION_EXECUTIVE_DEPARTMENT},
            {DatabaseConstants.DIMENSION_GOVERNMENT_APPROVAL_AUTHORITY_WITH_PREFIX,
                    SampleDataSet.FILE_DIMENSION_GOVERNMENT_APPROVAL_AUTHORITY},
            {DatabaseConstants.DIMENSION_ISSUE_CLASSIFICATION_WITH_PREFIX,
                    SampleDataSet.FILE_DIMENSION_ISSUE_CLASSIFICATION},
            {DatabaseConstants.DIMENSION_LAND_PROPERTY_WITH_PREFIX, SampleDataSet
                    .FILE_DIMENSION_LAND_PROPERTY},
//            {SupervisionSolutionConstants.DIMENSION_ASSET_FIRST_CLASSIFICATION_WITH_PREFIX,
//                    SampleDataSet.FILE_DIMENSION_ASSET_FIRST_CLASSIFICATION},
//            {SupervisionSolutionConstants.DIMENSION_ASSET_SECOND_CLASSIFICATION_WITH_PREFIX,
//                    SampleDataSet.FILE_DIMENSION_ASSET_SECOND_CLASSIFICATION},
            {DatabaseConstants.DIMENSION_PROJECT_CLASSIFICATION_WITH_PREFIX,
                    SampleDataSet.FILE_DIMENSION_PROJECT_CLASSIFICATION},
            {DatabaseConstants.DIMENSION_PROJECT_SITE_CLASSIFICATION_WITH_PREFIX,
                    SampleDataSet.FILE_DIMENSION_PROJECT_SITE_CLASSIFICATION},
            {DatabaseConstants.DIMENSION_PROJECT_SCOPE_WITH_PREFIX, SampleDataSet
                    .FILE_DIMENSION_PROJECT_SCOPE},
            {DatabaseConstants
                    .DIMENSION_PROJECT_CONSTRUCTION_CLASSIFICATION_WITH_PREFIX, SampleDataSet
                    .FILE_DIMENSION_PROJECT_CONSTRUCTION_CLASSIFICATION},
            {DatabaseConstants
                    .DIMENSION_ROAD_WITH_PREFIX, SampleDataSet.FILE_DIMENSION_ROAD}
    };

    private static String[][] DIMENSION_COMPANY_AND_USER_TO_CREATE = new String[][]{
            {"勘察单位", SampleDataSet.FILE_Reconnaissance_COMPANY},
            {"设计单位", SampleDataSet.FILE_Design_COMPANY},
            {"招标代理单位", SampleDataSet.FILE_BiddingAgent_COMPANY},
            {"造价咨询单位", SampleDataSet.FILE_CostConsulting_COMPANY},
            {"监理单位", SampleDataSet.FILE_Supervisor_COMPANY},
            {"施工单位", SampleDataSet.FILE_Construction_COMPANY},
            {"咨询单位", SampleDataSet.FILE_Consulting_COMPANY}
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

                if (dimensionTypeName.equalsIgnoreCase(DatabaseConstants
                        .DIMENSION_USER_WITH_PREFIX)) {
                    type.addTypeProperty("userId", PropertyType.STRING);
                    type.addTypeProperty("userName", PropertyType.STRING);
                } else if (dimensionTypeName.equalsIgnoreCase(DatabaseConstants
                        .DIMENSION_ROLE_WITH_PREFIX)) {
                    type.addTypeProperty("roleId", PropertyType.STRING);
                    type.addTypeProperty("roleName", PropertyType.STRING);
                } else {
                    type.addTypeProperty(Constants.DIMENSION_ID, PropertyType.STRING);
                    type.addTypeProperty(Constants.DIMENSION_NAME, PropertyType.STRING);
                    if (dimensionTypeName.equalsIgnoreCase(DatabaseConstants
                            .DIMENSION_EXECUTIVE_DEPARTMENT_WITH_PREFIX)) {
                        type.addTypeProperty("isAuthorityDepartment", PropertyType.BOOLEAN);
                    }
                }
            }
        }

        // create external user dimension type
        if (!ids.hasDimensionType(DatabaseConstants
                .DIMENSION_EXTERNAL_USER_WITH_PREFIX)) {
            DimensionType type = ids.addDimensionType(DatabaseConstants
                    .DIMENSION_EXTERNAL_USER_WITH_PREFIX);
            type.addTypeProperty("userId", PropertyType.STRING);
            type.addTypeProperty("userName", PropertyType.STRING);
        }

        // create third-party company
        if (!ids.hasDimensionType(DatabaseConstants.DIMENSION_COMPANY_WITH_PREFIX)) {
            DimensionType type = ids.addDimensionType(DatabaseConstants
                    .DIMENSION_COMPANY_WITH_PREFIX);
            type.addTypeProperty("companyId", PropertyType.STRING);
            type.addTypeProperty("companyName", PropertyType.STRING);
        }

        // create project address
        if (!ids.hasDimensionType(DatabaseConstants
                .DIMENSION_PROJECT_ADDRESS_WITH_PREFIX)) {
            DimensionType type = ids.addDimensionType(DatabaseConstants
                    .DIMENSION_PROJECT_ADDRESS_WITH_PREFIX);
            type.addTypeProperty(Constants.DIMENSION_ID, PropertyType.STRING);
            type.addTypeProperty(Constants.DIMENSION_NAME, PropertyType.STRING);
        }
    }

    public static Map<String, List<String>> dimensionCache = new HashedMap();

    public void createDimensionSampleData(InfoDiscoverSpace ids) throws
            InfoDiscoveryEngineRuntimeException {
        DimensionManager manager = new DimensionManager(this.ids);

        for (String[] array : DIMENSION_LIST_TO_CREATE) {
            String dimensionTypeName = array[0];
            String file = array[1];

            if (dimensionTypeName.equalsIgnoreCase(DatabaseConstants
                    .DIMENSION_USER_WITH_PREFIX)) {
                List<String> dimensionRIDList = new ArrayList<>();
                for (Map<String, Object> properties : getPropertiesFromLine(file, "userId",
                        "userName")) {
                    addMoreProperty(dimensionTypeName, properties);
                    Dimension dimension = manager.createDimension(dimensionTypeName, properties);
                    dimensionRIDList.add(dimension.getProperty("userId").getPropertyValue()
                            .toString());
                }
                dimensionCache.put(dimensionTypeName, dimensionRIDList);
            } else if (dimensionTypeName.equalsIgnoreCase(DatabaseConstants
                    .DIMENSION_ROLE_WITH_PREFIX)) {
                List<String> dimensionRIDList = new ArrayList<>();
                for (Map<String, Object> properties : getPropertiesFromLine(file, "roleId",
                        "roleName")) {
                    Dimension dimension = manager.createDimension(dimensionTypeName, properties);
                    dimensionRIDList.add(dimension.getProperty("roleId").getPropertyValue()
                            .toString());
                }
                dimensionCache.put(dimensionTypeName, dimensionRIDList);
            } else {
                List<String> dimensionRIDList = new ArrayList<>();
                for (Map<String, Object> properties : getPropertiesFromLine(file, Constants
                                .DIMENSION_ID,
                        Constants.DIMENSION_NAME)) {
                    Dimension dimension = manager.createDimension(dimensionTypeName, properties);
                    dimensionRIDList.add(dimension.getProperty(Constants.DIMENSION_NAME)
                            .getPropertyValue()
                            .toString());
                }
                dimensionCache.put(dimensionTypeName, dimensionRIDList);
            }
        }
    }

    public void linkUsersToExecutiveDepartment(InfoDiscoverSpace ids, String userDepartmentFile,
                                               String
                                                       executiveDepartmentDimensionType, String
                                                       userDimensionType, String relationType)
            throws
            InfoDiscoveryEngineInfoExploreException {

        logger.debug("Enter method linkUsersToExecutiveDepartment with roleFile: {}",
                userDepartmentFile);

        List<String> list = FileUtil.readLinesIntoList(userDepartmentFile);

        for (String line : list) {
            String[] departments = line.split("-");
            String departmentName = departments[0].trim();
            String departmentId = departments[1].trim();
            String userIds = departments[2].trim();

            logger.debug("departmentId: {}, departmentName: {}, userIds: {}", departmentId.trim(),
                    departmentName.trim(), userIds);

            Dimension department = getDepartment(executiveDepartmentDimensionType, departmentId);

            ProgressRelationManager manager = new ProgressRelationManager(ids);
            UserManager userManager = new UserManager();
            for (String userId : userIds.split(",")) {
                try {
                    Dimension user = userManager.getUserById(ids.getInformationExplorer(),
                            userId.trim(), userDimensionType);
                    manager.attachUserToExecuteDepartment(department, user, relationType);
                } catch (InfoDiscoveryEngineRuntimeException e) {
                    logger.error("Failed to link user: {} to department: {}", userId,
                            departmentId);
                }
            }
        }

        logger.debug("Exit method linkUsersToExecutiveDepartment()...");
    }

    public static void addMoreProperty(String dimensionType, Map<String, Object> properties) {
        if (dimensionType.equals(DatabaseConstants
                .DIMENSION_EXECUTIVE_DEPARTMENT_WITH_PREFIX)) {
            String dimensionId = properties.get(Constants.DIMENSION_ID).toString();
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

    public void createCompanyAndUsers(InfoDiscoverSpace ids) {

        DimensionManager dimensionManager = new DimensionManager(ids);
        ProgressRelationManager relationManager = new ProgressRelationManager(ids);

        for (String[] array : DIMENSION_COMPANY_AND_USER_TO_CREATE) {
            String companyClassificationName = array[0];
            String file = array[1];

            // get company classification dimension
            Dimension companyClassification = getCompanyClassification(companyClassificationName);
            if (companyClassification != null) {
                List<String> list = FileUtil.readLinesIntoList(file);
                for (String line : list) {
                    String companyName = line.split("-")[0];
                    String userName = line.split("-")[1];

                    // check if the company is already existed
                    Dimension company = getCompany(companyName);
                    if (company == null) {
                        // create the company
                        Map<String, Object> props = new HashMap<>();
                        props.put("companyName", companyName);
                        company = dimensionManager.createDimension(DatabaseConstants
                                .DIMENSION_COMPANY_WITH_PREFIX, props);
                    }

                    // check if the user is already existed
                    Dimension externalUser = getExternalUser(userName);
                    if (externalUser == null) {
                        // create the external user
                        Map<String, Object> props = new HashedMap();
                        props.put("userName", userName);
                        externalUser = dimensionManager.createDimension
                                (DatabaseConstants
                                        .DIMENSION_EXTERNAL_USER_WITH_PREFIX, props);
                    }

                    // add external user as member of company
                    if (externalUser != null && company != null) {
                        relationManager.addExternalUserToCompany(externalUser, company,
                                DatabaseConstants
                                        .RELATION_IS_MEMBER_OF_COMPANY_WITH_PREFIX);
                    }

                    // link company to companyClassification
                    if (company != null && companyClassification != null) {
                        relationManager.addCompanyToClassification(company,
                                companyClassification, DatabaseConstants
                                        .RELATION_IS_COMPANY_CLASSIFICATION_WITH_PREFIX);
                    }

                }
            }
        }
    }

    public void createProjectAddressAndRoad(InfoDiscoverSpace ids) {

        DimensionManager dimensionManager = new DimensionManager(ids);
        ProgressRelationManager relationManager = new ProgressRelationManager(ids);

        List<String> list = FileUtil.readLinesIntoList(SampleDataSet.FILE_PROJECT_ADDRESS);

        for (String line : list) {
            String projectAddress = line.split("_")[0];
            String road = line.split("_")[2];

            // check if project address is existed
            Dimension projectAddressDimension = getProjectAddress(projectAddress);
            if (projectAddressDimension == null) {
                Map<String, Object> props = new HashedMap();
                props.put(Constants.DIMENSION_NAME, projectAddress);
                projectAddressDimension = dimensionManager.createDimension
                        (DatabaseConstants.DIMENSION_PROJECT_ADDRESS_WITH_PREFIX,
                                props);
            }

            // check if road is existed
            Dimension roadDimension = getRoad(road);
            if (roadDimension == null) {
                Map<String, Object> props = new HashMap<>();
                props.put(Constants.DIMENSION_NAME, road);
                    roadDimension = dimensionManager.createDimension
                            (DatabaseConstants.DIMENSION_ROAD_WITH_PREFIX, props);
            }

            if (projectAddressDimension != null && roadDimension != null) {
                relationManager.addProjectAddressToRoad(projectAddressDimension, roadDimension,
                        DatabaseConstants.RELATION_LOCATED_AT_ROAD_WITH_PREFIX);
            }
        }

    }

    private Dimension getProjectAddress(String projectAddress) {
        return new DimensionManager(ids).getDimension(
                DatabaseConstants.DIMENSION_PROJECT_ADDRESS_WITH_PREFIX,
                Constants.DIMENSION_NAME, projectAddress);
    }

    private Dimension getRoad(String roadName) {
        return new DimensionManager(ids).getDimension(DatabaseConstants.DIMENSION_ROAD_WITH_PREFIX, Constants.DIMENSION_NAME,
                roadName);
    }

    public Dimension getUser(String userId) {
        return new DimensionManager(ids).getDimension(DatabaseConstants.DIMENSION_USER_WITH_PREFIX, "userId",
                userId);
    }

    public Dimension getDepartment(String dimensionType, String departmentId) {
        return new DimensionManager(ids).getDimension(dimensionType, Constants.DIMENSION_ID, departmentId);
    }

    private Dimension getCompanyClassification(String companyClassificationName) {
        return new DimensionManager(ids).getDimension(DatabaseConstants.DIMENSION_COMPANY_CLASSIFICATION_WITH_PREFIX,
                Constants.DIMENSION_NAME, companyClassificationName);
    }

    private Dimension getCompany(String companyName) {
        return new DimensionManager(ids).getDimension(DatabaseConstants.DIMENSION_COMPANY_WITH_PREFIX,
                DatabaseConstants.PROPERTY_COMPANY_NAME, companyName);
    }

    private Dimension getExternalUser(String userName) {
        return new DimensionManager(ids).getDimension(DatabaseConstants.DIMENSION_EXTERNAL_USER_WITH_PREFIX,
                DatabaseConstants.PROPERTY_USER_NAME, userName);
    }

}
