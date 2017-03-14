package com.infoDiscover.solution.arch.demo.template.maintainProject.task;

import java.util.Date;

/**
 * Created by sun.
 */
public class Task2ProjectStart extends MaintainProjectBaseTask {
//    String departmentId = "Proerty_Department";

    private String gongjuxinxi; // 工具备件信息
    private String projectfuzeren; // 项目负责人
    private String fenpaifangshi; // 分派方式
    private double dituxinxi_jingdu; // 地图信息_经度（坐标）
    private double dituxinxi_weidu; // 地图信息_纬度（坐标）
    private Date shangmenriqi; // 上门维修日期
    private String weixiudanwei; // 工程维修单位
    private String teshuyaoqiu; // 特殊要求
    private String dizhifanghao; // 项目地址房号

    public Task2ProjectStart(String progressId, String taskId, String taskName, String
            departmentId, String assignee, long startTime) {
        super(progressId, taskId, taskName, departmentId, assignee, startTime);
    }

    public String getGongjuxinxi() {
        return gongjuxinxi;
    }

    public void setGongjuxinxi(String gongjuxinxi) {
        this.gongjuxinxi = gongjuxinxi;
    }

    public String getProjectfuzeren() {
        return projectfuzeren;
    }

    public void setProjectfuzeren(String projectfuzeren) {
        this.projectfuzeren = projectfuzeren;
    }

    public String getFenpaifangshi() {
        return fenpaifangshi;
    }

    public void setFenpaifangshi(String fenpaifangshi) {
        this.fenpaifangshi = fenpaifangshi;
    }

    public double getDituxinxi_jingdu() {
        return dituxinxi_jingdu;
    }

    public void setDituxinxi_jingdu(double dituxinxi_jingdu) {
        this.dituxinxi_jingdu = dituxinxi_jingdu;
    }

    public double getDituxinxi_weidu() {
        return dituxinxi_weidu;
    }

    public void setDituxinxi_weidu(double dituxinxi_weidu) {
        this.dituxinxi_weidu = dituxinxi_weidu;
    }

    public Date getShangmenriqi() {
        return shangmenriqi;
    }

    public void setShangmenriqi(Date shangmenriqi) {
        this.shangmenriqi = shangmenriqi;
    }

    public String getWeixiudanwei() {
        return weixiudanwei;
    }

    public void setWeixiudanwei(String weixiudanwei) {
        this.weixiudanwei = weixiudanwei;
    }

    public String getTeshuyaoqiu() {
        return teshuyaoqiu;
    }

    public void setTeshuyaoqiu(String teshuyaoqiu) {
        this.teshuyaoqiu = teshuyaoqiu;
    }

    public String getDizhifanghao() {
        return dizhifanghao;
    }

    public void setDizhifanghao(String dizhifanghao) {
        this.dizhifanghao = dizhifanghao;
    }
}
