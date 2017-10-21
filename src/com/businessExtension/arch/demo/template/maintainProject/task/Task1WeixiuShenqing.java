package com.businessExtension.arch.demo.template.maintainProject.task;

import java.util.Date;

/**
 * Created by sun.
 */
public class Task1WeixiuShenqing extends MaintainProjectBaseTask {
    //String departmentId = "Proerty_Department";

    private boolean shifouqiangxiu = false; // 是否报修
    private String projectName; //工程项目名称
    private String wentifenlei; //故障问题分类
    private String projectAddress; //项目地址
    private Date yujishangmenkanchashijian; //预计上门勘察时间
    private String didianfenlei; //维修地点分类
    private String gongzhongfenlei; //工种分类
    private String baoxiurendianhua;//报修人电话
    private String projectType;//项目分类
    private String buweifenlei;//维修部位分类
    private String guzhangmiaoshu;//故障描述
    private String baoxiuren;//报修人

    public Task1WeixiuShenqing(String progressId, String taskId, String taskName, String
            departmentId, String assignee, long startTime) {
        super(progressId, taskId, taskName, departmentId, assignee, startTime);
    }

    public boolean isShifouqiangxiu() {
        return shifouqiangxiu;
    }

    public void setShifouqiangxiu(boolean shifouqiangxiu) {
        this.shifouqiangxiu = shifouqiangxiu;
    }

    public String getProjectName() {
        return projectName;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }

    public String getWentifenlei() {
        return wentifenlei;
    }

    public void setWentifenlei(String wentifenlei) {
        this.wentifenlei = wentifenlei;
    }

    public String getProjectAddress() {
        return projectAddress;
    }

    public void setProjectAddress(String projectAddress) {
        this.projectAddress = projectAddress;
    }

    public Date getYujishangmenkanchashijian() {
        return yujishangmenkanchashijian;
    }

    public void setYujishangmenkanchashijian(Date yujishangmenkanchashijian) {
        this.yujishangmenkanchashijian = yujishangmenkanchashijian;
    }

    public String getDidianfenlei() {
        return didianfenlei;
    }

    public void setDidianfenlei(String didianfenlei) {
        this.didianfenlei = didianfenlei;
    }

    public String getGongzhongfenlei() {
        return gongzhongfenlei;
    }

    public void setGongzhongfenlei(String gongzhongfenlei) {
        this.gongzhongfenlei = gongzhongfenlei;
    }

    public String getBaoxiurendianhua() {
        return baoxiurendianhua;
    }

    public void setBaoxiurendianhua(String baoxiurendianhua) {
        this.baoxiurendianhua = baoxiurendianhua;
    }

    public String getProjectType() {
        return projectType;
    }

    public void setProjectType(String projectType) {
        this.projectType = projectType;
    }

    public String getBuweifenlei() {
        return buweifenlei;
    }

    public void setBuweifenlei(String buweifenlei) {
        this.buweifenlei = buweifenlei;
    }

    public String getGuzhangmiaoshu() {
        return guzhangmiaoshu;
    }

    public void setGuzhangmiaoshu(String guzhangmiaoshu) {
        this.guzhangmiaoshu = guzhangmiaoshu;
    }

    public String getBaoxiuren() {
        return baoxiuren;
    }

    public void setBaoxiuren(String baoxiuren) {
        this.baoxiuren = baoxiuren;
    }
}
