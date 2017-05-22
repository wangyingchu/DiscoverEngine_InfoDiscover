package com.infoDiscover.solution.construction.supervision.sample;

import com.infoDiscover.common.util.FileUtil;
import com.infoDiscover.common.util.RandomUtil;
import com.infoDiscover.solution.construction.supervision.sample.SampleDataSet;
import org.apache.commons.collections.map.HashedMap;

import java.util.*;

/**
 * Created by sun.
 */
public class SampleFileUtil {

    public static Map<String, String> readStarter(String starterFile) {
        return readValuesFromFile(starterFile,",");
    }

    public static Map<String, String> readIssueReporterAndPhone(String issueReporterFile) {
        return readValuesFromFile(issueReporterFile,",");
    }

    public static String selectIssueReportPhone(String issueReporterFile, String issueReporter) {
        Map<String,String> map = readIssueReporterAndPhone(issueReporterFile);
        return map.get(issueReporter);
    }

    public static Map<String, String> readIssueAndDescription(String issueDescriptionFile) {
        return readValuesFromFile(issueDescriptionFile,",");
    }

    public static String selectIssueDescription(String issueDescriptionFile, String
            issueClassification) {
        Map<String, String> map = readIssueAndDescription(issueDescriptionFile);
        return map.get(issueClassification);
    }

    public static Map<String,String> readProjectAddressAndLongitude(String projectAddressFile) {
        if (projectAddressFile == null) {
            projectAddressFile = SampleDataSet.FILE_PROJECT_ADDRESS;
        }
        return readValuesFromFile(projectAddressFile,"_");
    }

    public static List<String> getProgressAddress(String projectAddressFile) {
        Map<String,String> map = readProjectAddressAndLongitude(projectAddressFile);
        Set<String> keySet = map.keySet();
        List<String> progressAddressList = new ArrayList<>();
        progressAddressList.addAll(keySet);
        return progressAddressList;
    }

    public static String selectLongitude(String projectAddressFile, String projectAddress) {
        Map<String,String> map = readProjectAddressAndLongitude(projectAddressFile);
        return map.get(projectAddress);
    }

    public static Map<String, String> readReconnaissanceCompany(String file) {
        if(file == null) {
            file = SampleDataSet.FILE_Reconnaissance_COMPANY;
        }
        return readValuesFromFile(file,"-");
    }

    public static Map<String, String> readConsultingCompany(String file) {
        if(file == null) {
            file = SampleDataSet.FILE_Consulting_COMPANY;
        }
        return readValuesFromFile(file,"-");
    }
    public static Map<String, String> readBudgetEstimateCompany(String file) {
        if(file == null) {
            file = SampleDataSet.FILE_BudgetEstimate_COMPANY;
        }
        return readValuesFromFile(file,"-");
    }

    public static Map<String, String> readBiddingAgentCompany(String file) {
        if(file == null) {
            file = SampleDataSet.FILE_BiddingAgent_COMPANY;
        }
        return readValuesFromFile(file,"-");
    }
    public static Map<String, String> readConstructionCompany(String file) {
        if(file == null) {
            file = SampleDataSet.FILE_Construction_COMPANY;
        }
        return readValuesFromFile(file,"-");
    }

    public static Map<String, String> readSupervisorCompany(String file) {
        if(file == null) {
            file = SampleDataSet.FILE_Supervisor_COMPANY;
        }
        return readValuesFromFile(file,"-");
    }

    public static Map<String, String> readDesignCompany(String file) {
        if(file == null) {
            file = SampleDataSet.FILE_Design_COMPANY;
        }
        return readValuesFromFile(file,"-");
    }

    public static Map<String, String> readCostConsultingCompany(String file) {
        if(file == null) {
            file = SampleDataSet.FILE_CostConsulting_COMPANY;
        }
        return readValuesFromFile(file,"-");
    }

    public static Map<String, String> readProjectLeader(String file) {
        if(file == null) {
            file = SampleDataSet.FILE_PROJECT_LEADER;
        }
        return readValuesFromFile(file,",");
    }

    public static Map<String, String> readBudgetAuditLeader(String file) {
        if(file == null) {
            file = SampleDataSet.FILE_BUDGET_AUDIT_LEADER;
        }
        return readValuesFromFile(file,",");
    }

    public static Map<String, String> readBudgetApproveLeader(String file) {
        if(file == null) {
            file = SampleDataSet.FILE_BUDGET_APPROVE_LEADER;
        }
        return readValuesFromFile(file,",");
    }

    public static Map<String, String> readDesignCompanyTechnicalLeader(String file) {
        if(file == null) {
            file = SampleDataSet.FILE_DESIGN_COMPANY_TECHNICAL_LEADER;
        }
        return readValuesFromFile(file,",");
    }

    public static Map<String, String> readSupervisionCompanyTechnicalLeader(String file) {
        if(file == null) {
            file = SampleDataSet.FILE_SUPERVISION_TECHINICAL_LEADER;
        }
        return readValuesFromFile(file,",");
    }

    public static Map<String, String> readSupervisionContactAuditLeader(String file) {
        if(file == null) {
            file = SampleDataSet.FILE_SUPERVISION_CONTACT_AUDIT_LEADER;
        }
        return readValuesFromFile(file,",");
    }

    public static List<String> readSupervisionContactPhone(String file) {
        if(file == null) {
            file = SampleDataSet.FILE_SUPERVISION_CONTRACT_PHONE;
        }
        return FileUtil.readLinesIntoList(file);
    }


    public static List<String> readSiteInspector(String file) {
        if(file == null) {
            file = SampleDataSet.FILE_SITE_INSPECTOR;
        }
        return FileUtil.readLinesIntoList(file);
    }

    public static List<String> readSiteInspectPlace(String file) {
        if(file == null) {
            file = SampleDataSet.FILE_SITE_INSPECT_PLACE;
        }
        return FileUtil.readLinesIntoList(file);
    }

    public static List<String> readFinalAcceptanceCompany(String file) {
        if(file == null) {
            file = SampleDataSet.FILE_FINAL_ACCEPTANCE_COMPANY;
        }
        return FileUtil.readLinesIntoList(file);
    }

    public static String readFinalAcceptanceMembers(String file) {
        if(file == null) {
            file = SampleDataSet.FILE_FINAL_ACCEPTANCE_MEMBERS;
        }
        return FileUtil.readLinesIntoList(file).get(0);
    }

    public static Map<String, String> readBiddingAgencyContractApprover(String file) {
        if(file == null) {
            file = SampleDataSet.FILE_BIDDING_AGENCY_CONTRACT_APPROVER;
        }
        return readValuesFromFile(file,",");
    }

    public static Map<String, String> readCostConsultingContractApprover(String file) {
        if(file == null) {
            file = SampleDataSet.FILE_COST_CONSULTING_CONTRACT_APPROVER;
        }
        return readValuesFromFile(file,",");
    }

    public static Map<String, String> readConstructionContactAndPhone(String file) {
        if(file == null) {
            file = SampleDataSet.FILE_CONSTRUCTION_CONTACT;
        }
        return readValuesFromFile(file,",");
    }

    public static Map<String, String> readProjectBudgetAuditLeader(String file) {
        if(file == null) {
            file = SampleDataSet.FILE_PROJECT_BUDGET_AUDIT_LEADER;
        }
        return readValuesFromFile(file,",");
    }
    public static Map<String, String> readProjectBudgetApproverLeader(String file) {
        if(file == null) {
            file = SampleDataSet.FILE_PROJECT_BUDGET_APPROVER_LEADER;
        }
        return readValuesFromFile(file,",");
    }
    public static Map<String, String> readConstructionContractApprover(String file) {
        if(file == null) {
            file = SampleDataSet.FILE_CONSTRUCTION_CONTRACT_APPROVER;
        }
        return readValuesFromFile(file,",");
    }

    public static Map<String, String> readSurveyContractApprover(String file) {
        if(file == null) {
            file = SampleDataSet.FILE_SURVEY_CONTRACT_APPROVER;
        }
        return readValuesFromFile(file,",");
    }

    public static Map<String, String> readConsultingContractApprover(String file) {
        if(file == null) {
            file = SampleDataSet.FILE_CONSULTING_CONTRACT_APPROVER;
        }
        return readValuesFromFile(file,",");
    }

    public static Map<String, String> readDesignContractApprover(String file) {
        if(file == null) {
            file = SampleDataSet.FILE_DESIGN_CONTRACT_APPROVER;
        }
        return readValuesFromFile(file,",");
    }

    public static Map<String, String> readSurveyDesignMember(String file) {
        if(file == null) {
            file = SampleDataSet.FILE_SURVEY_DESIGN_MEMBER;
        }
        return readValuesFromFile(file,",");
    }

    public static Map<String, String> readSurveryTechinicalMember(String file) {
        if(file == null) {
            file = SampleDataSet.FILE_SURVEY_TECHNICAL_MEMBER;
        }
        return readValuesFromFile(file,",");
    }

    public static Map<String, String> readFeasibleReportApprover(String file) {
        if(file == null) {
            file = SampleDataSet.FILE_FEASIBILITY_REPORT_APPROVER;
        }
        return readValuesFromFile(file,",");
    }

    public static Map<String, String> readValuesFromFile(String file, String separator){
        List<String> list = FileUtil.readLinesIntoList(file);
        Map<String, String> map = new HashedMap();
        for(String line : list) {
            String[] values = line.split(separator);
            String key = values[0].trim();
            String value = values[1].trim();
            map.put(key, value);
        }
        return map;
    }

    public static Map<String, String> readMaintenaceProjectTasks(String file) {
        if(file == null) {
            file = SampleDataSet.FILE_MAINTENACE_PROJECT_TASKS;
        }
        return readValuesFromFile(file,",");
    }

    public static Map<String, String> readNewProjectTasks(String file) {
        if(file == null) {
            file = SampleDataSet.FILE_NEW_PROJECT_TASKS;
        }
        return readValuesFromFile(file,",");
    }

    public static Map<String, String> getRandomValue(Map<String,String> map) {
        Map<String, String> randomMap = new HashMap<>();
        List<String> keyList = new ArrayList<>();
        keyList.addAll(map.keySet());
        int randomIndex = RandomUtil.generateRandomInRange(0, keyList.size());
        String key = keyList.get(randomIndex);
        String value = map.get(key);
        randomMap.put(key,value);
        return randomMap;
    }

    public static void main(String[] args) {
        Map<String, String> map = readProjectAddressAndLongitude(null);
        Map<String, String> randomValue = getRandomValue(map);
        String key = randomValue.keySet().iterator().next();
        String value  = randomValue.get(key);
        System.out.println("key: " + key);
        System.out.println("value: " + value);
        System.out.println("latitude: " + value.split(",")[0]);
        System.out.println("longitude: " + value.split(",")[1]);

        String address = "珠海加特精密工业有限公司-3栋";
        System.out.println(address.substring(0, address.indexOf("-")));

        List<String> phone = readSupervisionContactPhone(null);
        System.out.println("phone: " + phone);
    }
}
