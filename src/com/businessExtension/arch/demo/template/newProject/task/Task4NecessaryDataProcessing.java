package com.businessExtension.arch.demo.template.newProject.task;

import java.util.Date;

/**
 * Created by sun.
 */
public class Task4NecessaryDataProcessing extends NewProjectBaseTask {
    // String departmentId = "Engineering_Department";
    Date jianyishupifuriqi; // 建议书批复日期
    String keyanbaogaobianhao; // 可研报告编号
    Date tudihetongriqi; // 土地合同批复日期
    String huanpingbianhao; // 环评文件编号
    String tudihetong; // 土地合同编号
    String shenpiren; // 主管部门审批人
    String guihuaxuanzhi; // 规划选址编号
    String jianyishubianha; // 建议书编号
    String zhuguanbumen; // 政府主管部门名称
    Date guihuaxuanzhiriqi; // 规划选址批复日期
    Date keyanbaogaoriqi; // 可研报告批复日期

    public Task4NecessaryDataProcessing(String progressId, String taskId, String taskName, String
            departmentId, String assignee, long startTime) {
        super(progressId, taskId, taskName, departmentId, assignee, startTime);
    }

    public Date getJianyishupifuriqi() {
        return jianyishupifuriqi;
    }

    public void setJianyishupifuriqi(Date jianyishupifuriqi) {
        this.jianyishupifuriqi = jianyishupifuriqi;
    }

    public String getKeyanbaogaobianhao() {
        return keyanbaogaobianhao;
    }

    public void setKeyanbaogaobianhao(String keyanbaogaobianhao) {
        this.keyanbaogaobianhao = keyanbaogaobianhao;
    }

    public Date getTudihetongriqi() {
        return tudihetongriqi;
    }

    public void setTudihetongriqi(Date tudihetongriqi) {
        this.tudihetongriqi = tudihetongriqi;
    }

    public String getHuanpingbianhao() {
        return huanpingbianhao;
    }

    public void setHuanpingbianhao(String huanpingbianhao) {
        this.huanpingbianhao = huanpingbianhao;
    }

    public String getTudihetong() {
        return tudihetong;
    }

    public void setTudihetong(String tudihetong) {
        this.tudihetong = tudihetong;
    }

    public String getShenpiren() {
        return shenpiren;
    }

    public void setShenpiren(String shenpiren) {
        this.shenpiren = shenpiren;
    }

    public String getGuihuaxuanzhi() {
        return guihuaxuanzhi;
    }

    public void setGuihuaxuanzhi(String guihuaxuanzhi) {
        this.guihuaxuanzhi = guihuaxuanzhi;
    }

    public String getJianyishubianha() {
        return jianyishubianha;
    }

    public void setJianyishubianha(String jianyishubianha) {
        this.jianyishubianha = jianyishubianha;
    }

    public String getZhuguanbumen() {
        return zhuguanbumen;
    }

    public void setZhuguanbumen(String zhuguanbumen) {
        this.zhuguanbumen = zhuguanbumen;
    }

    public Date getGuihuaxuanzhiriqi() {
        return guihuaxuanzhiriqi;
    }

    public void setGuihuaxuanzhiriqi(Date guihuaxuanzhiriqi) {
        this.guihuaxuanzhiriqi = guihuaxuanzhiriqi;
    }

    public Date getKeyanbaogaoriqi() {
        return keyanbaogaoriqi;
    }

    public void setKeyanbaogaoriqi(Date keyanbaogaoriqi) {
        this.keyanbaogaoriqi = keyanbaogaoriqi;
    }
}
