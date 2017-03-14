package com.infoDiscover.solution.arch.demo.template.newProject.task;

import java.util.Date;

/**
 * Created by sun.
 */
public class Task1LaunchMaintainProject extends NewProjectBaseTask {
    // String departmentId = "BuildingAndEnvironment_Bureau";
    String ProjectAddress; // 项目地点
    String ProjectName; // 工程项目名称
    String projectbianhao; // 项目编号
    String JianSheDanwei; // 建设单位
    Date jianshekaishiriqi; // 建设开始日期
    Date jianshewangongriqi; // 建设完工日期
    String xiangmufuzeren; // 项目负责人
    String ProjectLeixing; // 项目类型
    double zongtouzie; // 总投资额


    public Task1LaunchMaintainProject(String progressId, String taskId, String taskName, String
            departmentId, String assignee, long startTime) {
        super(progressId, taskId, taskName, departmentId, assignee, startTime);
    }

    public String getProjectAddress() {
        return ProjectAddress;
    }

    public void setProjectAddress(String projectAddress) {
        ProjectAddress = projectAddress;
    }

    public String getProjectName() {
        return ProjectName;
    }

    public void setProjectName(String projectName) {
        ProjectName = projectName;
    }

    public String getProjectbianhao() {
        return projectbianhao;
    }

    public void setProjectbianhao(String projectbianhao) {
        this.projectbianhao = projectbianhao;
    }

    public String getJianSheDanwei() {
        return JianSheDanwei;
    }

    public void setJianSheDanwei(String jianSheDanwei) {
        JianSheDanwei = jianSheDanwei;
    }

    public Date getJianshekaishiriqi() {
        return jianshekaishiriqi;
    }

    public void setJianshekaishiriqi(Date jianshekaishiriqi) {
        this.jianshekaishiriqi = jianshekaishiriqi;
    }

    public Date getJianshewangongriqi() {
        return jianshewangongriqi;
    }

    public void setJianshewangongriqi(Date jianshewangongriqi) {
        this.jianshewangongriqi = jianshewangongriqi;
    }

    public String getXiangmufuzeren() {
        return xiangmufuzeren;
    }

    public void setXiangmufuzeren(String xiangmufuzeren) {
        this.xiangmufuzeren = xiangmufuzeren;
    }

    public String getProjectLeixing() {
        return ProjectLeixing;
    }

    public void setProjectLeixing(String projectLeixing) {
        ProjectLeixing = projectLeixing;
    }

    public double getZongtouzie() {
        return zongtouzie;
    }

    public void setZongtouzie(double zongtouzie) {
        this.zongtouzie = zongtouzie;
    }
}
