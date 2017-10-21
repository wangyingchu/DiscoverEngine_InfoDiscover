package com.businessExtension.arch.demo;

import com.infoDiscover.common.util.FileUtil;
import com.infoDiscover.common.util.RandomUtil;
import com.infoDiscover.infoDiscoverEngine.dataMart.Dimension;
import com.infoDiscover.infoDiscoverEngine.infoDiscoverBureau.InfoDiscoverSpace;
import com.infoDiscover.infoDiscoverEngine.util.exception.InfoDiscoveryEngineInfoExploreException;
import com.infoDiscover.infoDiscoverEngine.util.exception.InfoDiscoveryEngineRuntimeException;
import com.businessExtension.arch.progress.fact.RoleDimension;
import com.businessExtension.arch.progress.fact.UserDimension;
import com.businessExtension.arch.progress.manager.ProgressRelationManager;
import com.businessExtension.arch.progress.manager.RoleManager;
import com.businessExtension.arch.progress.manager.UserManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by sun.
 */
public class UserRoleDataImporter {

    private final static Logger logger = LoggerFactory.getLogger(UserRoleDataImporter.class);

    public static void createUsers(InfoDiscoverSpace ids, String userFile, String dimensionType) {
        logger.debug("Enter method createUsers with userFile: {}", userFile);

        List<String> list = FileUtil.readLinesIntoList(userFile);

        for (String line : list) {
            String[] users = line.split(",");
            String userName = users[0];
            String userId = users[1];
            logger.debug("userId: " + userId.trim() + ", username: " + userName.trim());
            UserDimension user = new UserDimension(userId, userName);
            try {
                Dimension userDimension = new UserManager().createUserDimension(ids, user,
                        dimensionType);
                logger.debug("userFact id: " + userDimension.getId());
            } catch (InfoDiscoveryEngineRuntimeException e) {
                logger.error(e.getMessage());
            } catch (InfoDiscoveryEngineInfoExploreException e) {
                logger.error(e.getMessage());
            }
        }
        logger.debug("Exit method createUsers()...");
    }

    public static void createRoles(InfoDiscoverSpace ids, String userDepartmentFile,
                                             String executiveDepartmentDimensionType, String userDimensionType, String relationType)
            throws InfoDiscoveryEngineInfoExploreException {
        logger.debug("Enter method createExecutiveDepartments with roleFile: {}", userDepartmentFile);

        List<String> list = FileUtil.readLinesIntoList(userDepartmentFile);

        for (String line : list) {
            String[] departments = line.split("-");
            String departmentName = departments[0].trim();
            String departmentId = departments[1].trim();
            String userIds = departments[2].trim();

            logger.debug("departmentId: {}, departmentName: {}, userIds: {}", departmentId.trim(),
                    departmentName.trim(), userIds);

            RoleDimension role = new RoleDimension(departmentId, departmentName);
            try {
                Dimension roleDimension = new RoleManager().createRoleDimension(ids, role,
                        executiveDepartmentDimensionType);
                logger.debug("roleFact id: {}", roleDimension.getId());


                ProgressRelationManager manager = new ProgressRelationManager(ids);
                UserManager userManager = new UserManager();
                for (String userId : userIds.split(",")) {
                    try {
                        Dimension user = userManager.getUserById(ids.getInformationExplorer(),
                                userId.trim(), userDimensionType);
                        manager.attachUserToRole(roleDimension, user, relationType);
                    } catch (InfoDiscoveryEngineRuntimeException e) {
                        e.printStackTrace();
                    }
                }
            } catch (Exception e) {
                logger.error(e.getMessage());
            }
        }

        logger.debug("Exit method createDepartments()...");
    }


    public static Map<String, String[]> readRoleAndUsers(String userRoleFile) {
        logger.info("Enter method readRoleAndUsers with roleFile: {}", userRoleFile);
        List<String> list = FileUtil.readLinesIntoList(userRoleFile);
        Map<String, String[]> map = new HashMap<>();
        for (String line : list) {
            String[] roles = line.split("-");
            String roleId = roles[1].trim();
            String userIds = roles[2].trim();
            map.put(roleId, userIds.split(","));
        }

        return map;
    }

    public static String selectRandomUserFromRole(String roleFile, String roleId) {
        logger.info("Random select a user from role: {}", roleId);
        Map<String, String[]> map = readRoleAndUsers(roleFile);
        String userId = "";
        if (map.containsKey(roleId)) {
            String[] users = map.get(roleId);
            if (users != null && users.length > 0) {
                int randomIndex = RandomUtil.generateRandomInRange(0, users.length - 1);
                userId = users[randomIndex];
            }
        } else {
            logger.error("RoleId does not have users");
        }

        return userId;
    }

    public static String getUserName(String userFile, String userId) {
        logger.debug("Enter method getUserName with userFile: {} and userId: {}", userFile, userId);

        List<String> list = FileUtil.readLinesIntoList(userFile);

        String userName = "";
        for (String line : list) {
            String[] users = line.split(",");
            userName = users[1];
            String userIdInFile = users[0];
            if(userId.equalsIgnoreCase(userIdInFile)){
                logger.debug("userId: " + userId.trim() + ", username: " + userName.trim());
                logger.debug("Exit method getUserName()...");
                return  userName;
            }
        }

        logger.debug("Exit method getUserName()...");
        return userName;
    }

    public static void main(String[] args) {
//         import users
//        String userFile = "/Users/sun/InfoDiscovery/Demodata/users.csv";
//        createUsers(userFile);
//
//         import roles
//        String roleFile = "/Users/sun/InfoDiscovery/Demodata/roles.csv";
//        try {
//            createRoles(roleFile);
//        } catch (InfoDiscoveryEngineInfoExploreException e) {
//            e.printStackTrace();
//        }
//        selectRandomUserFromRole(roleFile, "Proerty_Department");
    }
}
