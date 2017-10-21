package com.businessExtension.arch.demo.template.newProject.task;

import java.util.Date;

/**
 * Created by sun.
 */
public class Task10InvestmentEstimates extends NewProjectBaseTask {
    //String departmentId = "Engineering_Department";
    Date gaisuanwanchengriqi; // 概算编制完成日期
    String  baizhijishufuzeren; // 概算编制技术负责人
    double gongchengjiansheqitfei_gaisuan; // 工程建设其他费(概算)
    double zonggaisuanfei_gaisuan; // 总概算费
    double jianangongchengfei_gaisuan; // 建安工程费(概算)
    double yubeifei_gaisuan; // 预备费(概算)

    public Task10InvestmentEstimates(String progressId, String taskId, String taskName, String
            departmentId, String assignee, long startTime) {
        super(progressId, taskId, taskName, departmentId, assignee, startTime);
    }

    public Date getGaisuanwanchengriqi() {
        return gaisuanwanchengriqi;
    }

    public void setGaisuanwanchengriqi(Date gaisuanwanchengriqi) {
        this.gaisuanwanchengriqi = gaisuanwanchengriqi;
    }

    public String getBaizhijishufuzeren() {
        return baizhijishufuzeren;
    }

    public void setBaizhijishufuzeren(String baizhijishufuzeren) {
        this.baizhijishufuzeren = baizhijishufuzeren;
    }

    public double getGongchengjiansheqitfei_gaisuan() {
        return gongchengjiansheqitfei_gaisuan;
    }

    public void setGongchengjiansheqitfei_gaisuan(double gongchengjiansheqitfei_gaisuan) {
        this.gongchengjiansheqitfei_gaisuan = gongchengjiansheqitfei_gaisuan;
    }

    public double getZonggaisuanfei_gaisuan() {
        return zonggaisuanfei_gaisuan;
    }

    public void setZonggaisuanfei_gaisuan(double zonggaisuanfei_gaisuan) {
        this.zonggaisuanfei_gaisuan = zonggaisuanfei_gaisuan;
    }

    public double getJianangongchengfei_gaisuan() {
        return jianangongchengfei_gaisuan;
    }

    public void setJianangongchengfei_gaisuan(double jianangongchengfei_gaisuan) {
        this.jianangongchengfei_gaisuan = jianangongchengfei_gaisuan;
    }

    public double getYubeifei_gaisuan() {
        return yubeifei_gaisuan;
    }

    public void setYubeifei_gaisuan(double yubeifei_gaisuan) {
        this.yubeifei_gaisuan = yubeifei_gaisuan;
    }
}
