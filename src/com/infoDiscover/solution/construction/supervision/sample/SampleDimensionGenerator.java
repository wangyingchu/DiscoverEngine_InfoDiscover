package com.infoDiscover.solution.construction.supervision.sample;

import com.infoDiscover.common.util.FileUtil;
import com.infoDiscover.infoDiscoverEngine.dataMart.Dimension;
import com.infoDiscover.infoDiscoverEngine.dataMart.DimensionType;
import com.infoDiscover.infoDiscoverEngine.dataMart.PropertyType;
import com.infoDiscover.infoDiscoverEngine.dataWarehouse.ExploreParameters;
import com.infoDiscover.infoDiscoverEngine.dataWarehouse.InformationFiltering.EqualFilteringItem;
import com.infoDiscover.infoDiscoverEngine.infoDiscoverBureau.InfoDiscoverSpace;
import com.infoDiscover.infoDiscoverEngine.util.exception.InfoDiscoveryEngineDataMartException;
import com.infoDiscover.infoDiscoverEngine.util.exception.InfoDiscoveryEngineInfoExploreException;
import com.infoDiscover.infoDiscoverEngine.util.exception.InfoDiscoveryEngineRuntimeException;
import com.infoDiscover.solution.arch.progress.manager.ProgressRelationManager;
import com.infoDiscover.solution.arch.progress.manager.UserManager;
import com.infoDiscover.solution.common.dimension.DimensionManager;
import com.infoDiscover.solution.common.executor.QueryExecutor;
import com.infoDiscover.solution.common.util.Constants;
import com.infoDiscover.solution.construction.supervision.database.SupervisionSolutionConstants;
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
            {SupervisionSolutionConstants.DIMENSION_ROLE_WITH_PREFIX, SampleDataSet
                    .FILE_DEPARTMENT},
            {SupervisionSolutionConstants.DIMENSION_USER_WITH_PREFIX, SampleDataSet.FILE_USER},
            {SupervisionSolutionConstants.DIMENSION_CONSTRUCTION_TYPE_WITH_PREFIX, SampleDataSet
                    .FILE_DIMENSION_CONSTRUCTION_TYPE},
            {SupervisionSolutionConstants.DIMENSION_COMPANY_CLASSIFICATION_WITH_PREFIX,
                    SampleDataSet.FILE_DIMENSION_COMPANY_CLASSIFICATION},
            {SupervisionSolutionConstants.DIMENSION_ASSIGN_MODEL_WITH_PREFIX, SampleDataSet
                    .FILE_DIMENSION_ASSIGN_MODEL},
            {SupervisionSolutionConstants.DIMENSION_EXECUTIVE_DEPARTMENT_WITH_PREFIX,
                    SampleDataSet.FILE_DIMENSION_EXECUTIVE_DEPARTMENT},
            {SupervisionSolutionConstants.DIMENSION_GOVERNMENT_APPROVAL_AUTHORITY_WITH_PREFIX,
                    SampleDataSet.FILE_DIMENSION_GOVERNMENT_APPROVAL_AUTHORITY},
            {SupervisionSolutionConstants.DIMENSION_ISSUE_CLASSIFICATION_WITH_PREFIX,
                    SampleDataSet.FILE_DIMENSION_ISSUE_CLASSIFICATION},
            {SupervisionSolutionConstants.DIMENSION_LAND_PROPERTY_WITH_PREFIX, SampleDataSet
                    .FILE_DIMENSION_LAND_PROPERTY},
            {SupervisionSolutionConstants.DIMENSION_ASSET_FIRST_CLASSIFICATION_WITH_PREFIX,
                    SampleDataSet.FILE_DIMENSION_ASSET_FIRST_CLASSIFICATION},
            {SupervisionSolutionConstants.DIMENSION_ASSET_SECOND_CLASSIFICATION_WITH_PREFIX,
                    SampleDataSet.FILE_DIMENSION_ASSET_SECOND_CLASSIFICATION},
            {SupervisionSolutionConstants.DIMENSION_PROJECT_CLASSIFICATION_WITH_PREFIX,
                    SampleDataSet.FILE_DIMENSION_PROJECT_CLASSIFICATION},
            {SupervisionSolutionConstants.DIMENSION_PROJECT_SITE_CLASSIFICATION_WITH_PREFIX,
                    SampleDataSet.FILE_DIMENSION_PROJECT_SITE_CLASSIFICATION},
            {SupervisionSolutionConstants.DIMENSION_PROJECT_SCOPE_WITH_PREFIX, SampleDataSet
                    .FILE_DIMENSION_PROJECT_SCOPE},
            {SupervisionSolutionConstants
                    .DIMENSION_PROJECT_CONSTRUCTION_CLASSIFICATION_WITH_PREFIX, SampleDataSet
                    .FILE_DIMENSION_PROJECT_CONSTRUCTION_CLASSIFICATION},
            {SupervisionSolutionConstants
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

                if (dimensionTypeName.equalsIgnoreCase(SupervisionSolutionConstants
                        .DIMENSION_USER_WITH_PREFIX)) {
                    type.addTypeProperty("userId", PropertyType.STRING);
                    type.addTypeProperty("userName", PropertyType.STRING);
                } else if (dimensionTypeName.equalsIgnoreCase(SupervisionSolutionConstants
                        .DIMENSION_ROLE_WITH_PREFIX)) {
                    type.addTypeProperty("roleId", PropertyType.STRING);
                    type.addTypeProperty("roleName", PropertyType.STRING);
                } else {
                    type.addTypeProperty(Constants.DIMENSION_ID, PropertyType.STRING);
                    type.addTypeProperty(Constants.DIMENSION_NAME, PropertyType.STRING);
                    if (dimensionTypeName.equalsIgnoreCase(SupervisionSolutionConstants
                            .DIMENSION_EXECUTIVE_DEPARTMENT_WITH_PREFIX)) {
                        type.addTypeProperty("isAuthorityDepartment", PropertyType.BOOLEAN);
                    }
                }
            }
        }

        // create external user dimension type
        if (!ids.hasDimensionType(SupervisionSolutionConstants
                .DIMENSION_EXTERNAL_USER_WITH_PREFIX)) {
            DimensionType type = ids.addDimensionType(SupervisionSolutionConstants
                    .DIMENSION_EXTERNAL_USER_WITH_PREFIX);
            type.addTypeProperty("userId", PropertyType.STRING);
            type.addTypeProperty("userName", PropertyType.STRING);
        }

        // create third-party company
        if (!ids.hasDimensionType(SupervisionSolutionConstants.DIMENSION_COMPANY_WITH_PREFIX)) {
            DimensionType type = ids.addDimensionType(SupervisionSolutionConstants
                    .DIMENSION_COMPANY_WITH_PREFIX);
            type.addTypeProperty("companyId", PropertyType.STRING);
            type.addTypeProperty("companyName", PropertyType.STRING);
        }

        // create project address
        if (!ids.hasDimensionType(SupervisionSolutionConstants.DIMENSION_PROJECT_ADDRESS_WITH_PREFIX)) {
            DimensionType type = ids.addDimensionType(SupervisionSolutionConstants
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

            if (dimensionTypeName.equalsIgnoreCase(SupervisionSolutionConstants
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
            } else if (dimensionTypeName.equalsIgnoreCase(SupervisionSolutionConstants
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

        List<String> list = FileUtil.read(userDepartmentFile);

        for (String line : list) {
            String[] departments = line.split("-");
            String departmentName = departments[0].trim();
            String departmentId = departments[1].trim();
            String userIds = departments[2].trim();

            logger.debug("departmentId: {}, departmentName: {}, userIds: {}", departmentId.trim(),
                    departmentName.trim(), userIds);

            ExploreParameters ep = new ExploreParameters();
            ep.setType(executiveDepartmentDimensionType);
            ep.setDefaultFilteringItem(new EqualFilteringItem(Constants.DIMENSION_ID,
                    departmentId));

            try {
                Dimension department = QueryExecutor.executeDimensionQuery(ids
                        .getInformationExplorer(), ep);
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
            } catch (InfoDiscoveryEngineRuntimeException e) {
                logger.error("Failed to get execute department: {}", e.getMessage());
            }

        }

        logger.debug("Exit method linkUsersToExecutiveDepartment()...");
    }

    public static void addMoreProperty(String dimensionType, Map<String, Object> properties) {
        if (dimensionType.equals(SupervisionSolutionConstants
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

        DimensionManager dimensionManager = new  DimensionManager(ids);
        ProgressRelationManager relationManager = new ProgressRelationManager(ids);

        for (String[] array : DIMENSION_COMPANY_AND_USER_TO_CREATE) {
            String companyClassificationName = array[0];
            String file = array[1];

            // get company classification dimension
            Dimension companyClassification = getCompanyClassification(ids, companyClassificationName);
            if (companyClassification != null) {
                List<String> list = FileUtil.readLinesIntoList(file);
                for (String line : list) {
                    String companyName = line.split("-")[0];
                    String userName = line.split("-")[1];

                    // check if the company is already existed
                    Dimension company = getCompany(ids, companyName);
                    if (company == null) {
                        // create the company
                        Map<String, Object> props = new HashMap<>();
                        props.put("companyName", companyName);
                        try {
                            company = dimensionManager.createDimension(SupervisionSolutionConstants
                                    .DIMENSION_COMPANY_WITH_PREFIX, props);
                        } catch (InfoDiscoveryEngineRuntimeException e) {
                            logger.error("Failed to create company: {}", companyName);
                        }
                    }

                    // check if the user is already existed
                    Dimension externalUser = getExternalUser(ids, userName);
                    if (externalUser == null) {
                        // create the external user
                        Map<String, Object> props = new HashedMap();
                        props.put("userName", userName);
                        try {
                            externalUser = dimensionManager.createDimension
                                    (SupervisionSolutionConstants
                                            .DIMENSION_EXTERNAL_USER_WITH_PREFIX, props);
                        } catch (InfoDiscoveryEngineRuntimeException e) {
                            logger.error("Failed to create external user: {}", userName);
                        }
                    }

                    // add external user as member of company
                    if(externalUser != null && company != null) {
                        relationManager.addExternalUserToCompany(externalUser, company,
                                SupervisionSolutionConstants
                                        .RELATION_IS_MEMBER_OF_COMPANY_WITH_PREFIX);
                    }

                    // link company to companyClassification
                    if(company != null && companyClassification != null) {
                        relationManager.addCompanyToClassification(company,
                                companyClassification, SupervisionSolutionConstants
                                        .RELATION_IS_COMPANY_CLASSIFIACTION_WITH_PREFIX);
                    }

                }
            }
        }
    }

    private Dimension getCompanyClassification(InfoDiscoverSpace ids, String
            companyClassificationName) {
        ExploreParameters ep = new ExploreParameters();
        ep.setType(SupervisionSolutionConstants.DIMENSION_COMPANY_CLASSIFICATION_WITH_PREFIX);
        ep.setDefaultFilteringItem(new EqualFilteringItem(Constants.DIMENSION_NAME,
                companyClassificationName));

        try {
            return QueryExecutor.executeDimensionQuery(ids
                    .getInformationExplorer(), ep);
        } catch (InfoDiscoveryEngineRuntimeException e) {
            logger.info("Failed to get companyClassification with companyClassificationName: {}",
                    companyClassificationName);
        } catch (InfoDiscoveryEngineInfoExploreException e) {
            logger.info("Failed to get companyClassification with companyClassificationName: {}",
                    companyClassificationName);
        }

        return null;
    }

    private Dimension getCompany(InfoDiscoverSpace ids, String
            companyName) {
        ExploreParameters ep = new ExploreParameters();
        ep.setType(SupervisionSolutionConstants.DIMENSION_COMPANY_WITH_PREFIX);
        ep.setDefaultFilteringItem(new EqualFilteringItem("companyName",
                companyName));

        try {
            return QueryExecutor.executeDimensionQuery(ids
                    .getInformationExplorer(), ep);
        } catch (InfoDiscoveryEngineRuntimeException e) {
            logger.info("Failed to get company with companyName: {}",
                    companyName);
        } catch (InfoDiscoveryEngineInfoExploreException e) {
            logger.info("Failed to get company with companyName: {}",
                    companyName);
        }

        return null;
    }

    private Dimension getExternalUser(InfoDiscoverSpace ids, String
            userName) {
        ExploreParameters ep = new ExploreParameters();
        ep.setType(SupervisionSolutionConstants.DIMENSION_EXTERNAL_USER_WITH_PREFIX);
        ep.setDefaultFilteringItem(new EqualFilteringItem("userName",
                userName));

        try {
            return QueryExecutor.executeDimensionQuery(ids
                    .getInformationExplorer(), ep);
        } catch (InfoDiscoveryEngineRuntimeException e) {
            logger.info("Failed to get externalUser with userName: {}",
                    userName);
        } catch (InfoDiscoveryEngineInfoExploreException e) {
            logger.info("Failed to get externalUser with userName: {}",
                    userName);
        }

        return null;
    }

    public void createProjectAddressAndRoad(InfoDiscoverSpace ids) {

        DimensionManager dimensionManager = new  DimensionManager(ids);
        ProgressRelationManager relationManager = new ProgressRelationManager(ids);

        List<String> list = FileUtil.readLinesIntoList(SampleDataSet.FILE_PROJECT_ADDRESS);

        for(String line: list) {
            String projectAddress = line.split("_")[0];
            String road = line.split("_")[2];

            // check if project address is existed
            Dimension projectAddressDimension = getProjectAddress(ids, projectAddress);
            if (projectAddressDimension == null) {
                Map<String, Object> props = new HashedMap();
                props.put(Constants.DIMENSION_NAME, projectAddress);
                try {
                    projectAddressDimension = dimensionManager.createDimension
                            (SupervisionSolutionConstants.DIMENSION_PROJECT_ADDRESS_WITH_PREFIX, props);
                } catch (InfoDiscoveryEngineRuntimeException e) {
                    logger.error("Failed to create projectAddress: {}", projectAddress);
                }
            }

            // check if road is existed
            Dimension roadDimension = getRoad(ids, road);
            if(roadDimension == null) {
                Map<String, Object> props = new HashMap<>();
                props.put(Constants.DIMENSION_NAME, road);
                try {
                    roadDimension = dimensionManager.createDimension
                            (SupervisionSolutionConstants.DIMENSION_ROAD_WITH_PREFIX, props);
                } catch (InfoDiscoveryEngineRuntimeException e) {
                    logger.error("Failed to create road: {}", road);
                }
            }

            if(projectAddressDimension != null && roadDimension != null) {
                relationManager.addProjectAddressToRoad(projectAddressDimension, roadDimension,
                        SupervisionSolutionConstants.RELATION_LOCATED_AT_ROAD_WITH_PREFIX);
            }
        }

    }

    private Dimension getProjectAddress(InfoDiscoverSpace ids, String
            projectAddress) {
        ExploreParameters ep = new ExploreParameters();
        ep.setType(SupervisionSolutionConstants.DIMENSION_PROJECT_ADDRESS_WITH_PREFIX);
        ep.setDefaultFilteringItem(new EqualFilteringItem(Constants.DIMENSION_NAME,
                projectAddress));

        try {
            return QueryExecutor.executeDimensionQuery(ids
                    .getInformationExplorer(), ep);
        } catch (InfoDiscoveryEngineRuntimeException e) {
            logger.info("Failed to get projectAddress with projectAddress: {}",
                    projectAddress);
        } catch (InfoDiscoveryEngineInfoExploreException e) {
            logger.info("Failed to get projectAddress with projectAddress: {}",
                    projectAddress);
        }

        return null;
    }

    private Dimension getRoad(InfoDiscoverSpace ids, String
            roadName) {
        ExploreParameters ep = new ExploreParameters();
        ep.setType(SupervisionSolutionConstants.DIMENSION_ROAD_WITH_PREFIX);
        ep.setDefaultFilteringItem(new EqualFilteringItem(Constants.DIMENSION_NAME,
                roadName));

        try {
            return QueryExecutor.executeDimensionQuery(ids
                    .getInformationExplorer(), ep);
        } catch (InfoDiscoveryEngineRuntimeException e) {
            logger.info("Failed to get road with roadName: {}",
                    roadName);
        } catch (InfoDiscoveryEngineInfoExploreException e) {
            logger.info("Failed to get road with roadName: {}",
                    roadName);
        }

        return null;
    }
}
