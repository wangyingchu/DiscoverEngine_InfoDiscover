package com.infoDiscover.common.util;

import com.infoDiscover.solution.arch.demo.template.maintainProject.task.*;
import com.infoDiscover.solution.arch.demo.template.maintainProject.task.Task2ProjectStart;
import com.infoDiscover.solution.arch.demo.template.newProject.task.*;
import com.sun.org.apache.regexp.internal.RE;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import sun.rmi.runtime.Log;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by sun.
 */
public class ReflectHelper {

    private final static Logger logger = LogManager.getLogger(ReflectHelper.class);

    public static List<String> getFields(Class clazz) {
        List<String> list = new ArrayList<>();

        Field[] fields = clazz.getDeclaredFields();
        for (Field field : fields) {
            String name = field.getName();
            logger.debug("fieldName: " + name);
            list.add(name);
        }

        return list;
    }

    public static void main(String[] args) {

//        String maintainTasks = "/Users/sun/InfoDiscovery/Code/DiscoverEngine_InfoDiscover/src/com" +
//                "/infoDiscover/solution/arch/demo/template/maintainProject/task";

//        Class[] classes = new Class[11];
//        classes[0] = Task1WeixiuShenqing.class;
//        classes[1] = Task2ProjectStart.class;
//        classes[2] = Task3KanchaBaojia.class;
//        classes[3] = Task4Zaojiahetongshenpi.class;
//        classes[4] = Task5Zaojiazixunqianding.class;
//        classes[5] = Task6Yusuanzaojia.class;
//        classes[6] = Task7Yusuanzaojiashenpi.class;
//        classes[7] = Task8Shigongdanweiqianyue.class;
//        classes[8] = Task9Shigong.class;
//        classes[9] = Task10Jungongyanshou.class;
//        classes[10] = Task11ProjectEnd.class;

        Class[] classes = new Class[20];
        classes[0] = Task1LaunchMaintainProject.class;
        classes[1] = Task2ProjectStart.class;
        classes[2] = Task3ProjectDevelopmentCompany.class;
        classes[3] = Task4NecessaryDataProcessing.class;
        classes[4] = Task5SurveyResults.class;
        classes[5] = Task6TenderAgentSigned.class;
        classes[6] = Task7InvestigationCompanySigned.class;
        classes[7] = Task8DesignCompanySigned.class;
        classes[8] = Task9DesignCompanyResults.class;
        classes[9] = Task10Jungongyanshou.class;
        classes[10] = Task10InvestmentEstimates.class;
        classes[11] = Task11BudgetApproval.class;
        classes[12] = Task12CostConsultingCompanySigned.class;
        classes[13] = Task13BudgetCost.class;
        classes[14] = Task14BudgetCostApproval.class;
        classes[15] = Task15BonstructionCompanySigned.class;
        classes[16] = Task16Construction.class;
        classes[17] = Task17MonthProgressReport.class;
        classes[18] = Task18CompletionAcceptance.class;
        classes[19] = Task19ProjectClosure.class;

        StringBuilder json = new StringBuilder();
        for(Class clazz: classes) {
            List<String> list = getFields(clazz);
            StringBuilder sb = new StringBuilder();
            sb.append("{");
            sb.append("\"type\": \"Task\",");
            sb.append("\"progressId\": \"maintain001\",");
            sb.append("\"taskId\": \"apply001\",");
            sb.append("\"departmentId\": \"Proerty_Department\",");
            sb.append("\"assignee\": \"baiwenbo\",");
            sb.append("\"type\": \"Task\",");
            sb.append("\"startTime\": \"1000000\",");
            sb.append("\"endTime\": \"20000000000\",");


            for (String name : list) {
                sb.append("\"");
                sb.append(name);
                sb.append("\":\"\",");
            }
            String result = sb.toString().substring(0, sb.toString().length() - 1) + "}";

            json.append(result + ",");
        }

        System.out.println("json: " + json);

    }
}
