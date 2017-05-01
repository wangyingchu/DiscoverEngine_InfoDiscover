package com.infoDiscover.solution.arch.demo.prepare;

import com.infoDiscover.common.util.FileUtil;
import com.infoDiscover.common.util.RandomUtil;
import com.infoDiscover.infoDiscoverEngine.dataMart.Dimension;
import com.infoDiscover.infoDiscoverEngine.util.exception.InfoDiscoveryEngineInfoExploreException;
import com.infoDiscover.infoDiscoverEngine.util.exception.InfoDiscoveryEngineRuntimeException;
import com.infoDiscover.solution.arch.progress.fact.RoleDimension;
import com.infoDiscover.solution.arch.progress.fact.UserDimension;
import com.infoDiscover.solution.arch.progress.manager.ProgressRelationManager;
import com.infoDiscover.solution.arch.progress.manager.RoleManager;
import com.infoDiscover.solution.arch.progress.manager.UserManager;
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

    public static void createUsers(String userFile) {
        logger.debug("Enter method createUsers with userFile: {}", userFile);

        List<String> list = FileUtil.read(userFile);

        for (String line : list) {
            String[] users = line.split(",");
            String userName = users[0];
            String userId = users[1];
            logger.debug("userId: " + userId.trim() + ", username: " + userName.trim());
            UserDimension user = new UserDimension(userId, userName);
            try {
                Dimension userDimension = new UserManager().createUserDimension(user);
                logger.debug("userFact id: " + userDimension.getId());
            } catch (InfoDiscoveryEngineRuntimeException e) {
                logger.error(e.getMessage());
            } catch (InfoDiscoveryEngineInfoExploreException e) {
                logger.error(e.getMessage());
            }
        }
        logger.debug("Exit method createUsers()...");
    }

    public static void createRoles(String roleFile) throws InfoDiscoveryEngineInfoExploreException {
        logger.debug("Enter method createRoles with roleFile: {}", roleFile);

        List<String> list = FileUtil.read(roleFile);

        for (String line : list) {
            String[] roles = line.split("-");
            String roleName = roles[0].trim();
            String roleId = roles[1].trim();
            String userIds = roles[2].trim();

            logger.debug("roleId: {}, roleName: {}, userIds: {}", roleId.trim(), roleName.trim(),
                    userIds);

            RoleDimension role = new RoleDimension(roleId, roleName);
            try {
                Dimension roleDimension = new RoleManager().createRoleDimension(role);
                logger.debug("roleFact id: {}", roleDimension.getId());
            } catch (InfoDiscoveryEngineRuntimeException e) {
                logger.error(e.getMessage());
            }

            ProgressRelationManager manager = new ProgressRelationManager();
            for (String userId : userIds.split(",")) {
                manager.attachUserToRole(roleId, userId.trim());
            }
        }

        logger.debug("Exit method createRoles()...");
    }


    public static Map<String, String[]> readRoleAndUsers(String roleFile) {
        logger.info("Enter method readRoleAndUsers with roleFile: {}", roleFile);
        List<String> list = FileUtil.read(roleFile);
        Map<String, String[]> map = new HashMap<>();
        for (String line : list) {
            String[] roles = line.split("-");
            String roleName = roles[0].trim();
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

    public static void main(String[] args) {
//         import users
//        String userFile = "/Users/sun/InfoDiscovery/Demodata/users.csv";
//        createUsers(userFile);
//
//         import roles
        String roleFile = "/Users/sun/InfoDiscovery/Demodata/roles.csv";
//        try {
//            createRoles(roleFile);
//        } catch (InfoDiscoveryEngineInfoExploreException e) {
//            e.printStackTrace();
//        }
        selectRandomUserFromRole(roleFile, "Proerty_Department");
    }
}
