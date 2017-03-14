package com.infoDiscover.solution.arch.demo.template.newProject.task;

import java.util.Date;

/**
 * Created by sun.
 */
public class Task3ProjectDevelopmentCompany extends NewProjectBaseTask {
    // String departmentId = "General_Manager";
    Date lixianghetongqiandingriqi; // 立项编制合同签订时间
    String lixiangdanwei; // 立项编制单位名称
    double lixianghetongjine; // 立项编制合同金额
    String lixiangzixunfuzeren; // 咨询负责人
    String lixianghetongbianhao; // 立项编制合同编号
    Date lixianghetongkaishiriqi; // 立项编制合同开始日期
    Date lixianghetongjieshuriqi; // 立项编制合同结束日期

    public Task3ProjectDevelopmentCompany(String progressId, String taskId, String taskName, String
            departmentId, String assignee, long startTime) {
        super(progressId, taskId, taskName, departmentId, assignee, startTime);
    }

    public Date getLixianghetongqiandingriqi() {
        return lixianghetongqiandingriqi;
    }

    public void setLixianghetongqiandingriqi(Date lixianghetongqiandingriqi) {
        this.lixianghetongqiandingriqi = lixianghetongqiandingriqi;
    }

    public String getLixiangdanwei() {
        return lixiangdanwei;
    }

    public void setLixiangdanwei(String lixiangdanwei) {
        this.lixiangdanwei = lixiangdanwei;
    }

    public double getLixianghetongjine() {
        return lixianghetongjine;
    }

    public void setLixianghetongjine(double lixianghetongjine) {
        this.lixianghetongjine = lixianghetongjine;
    }

    public String getLixiangzixunfuzeren() {
        return lixiangzixunfuzeren;
    }

    public void setLixiangzixunfuzeren(String lixiangzixunfuzeren) {
        this.lixiangzixunfuzeren = lixiangzixunfuzeren;
    }

    public String getLixianghetongbianhao() {
        return lixianghetongbianhao;
    }

    public void setLixianghetongbianhao(String lixianghetongbianhao) {
        this.lixianghetongbianhao = lixianghetongbianhao;
    }

    public Date getLixianghetongkaishiriqi() {
        return lixianghetongkaishiriqi;
    }

    public void setLixianghetongkaishiriqi(Date lixianghetongkaishiriqi) {
        this.lixianghetongkaishiriqi = lixianghetongkaishiriqi;
    }

    public Date getLixianghetongjieshuriqi() {
        return lixianghetongjieshuriqi;
    }

    public void setLixianghetongjieshuriqi(Date lixianghetongjieshuriqi) {
        this.lixianghetongjieshuriqi = lixianghetongjieshuriqi;
    }
}
