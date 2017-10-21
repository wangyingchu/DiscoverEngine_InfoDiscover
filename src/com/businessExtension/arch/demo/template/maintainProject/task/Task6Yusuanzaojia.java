package com.businessExtension.arch.demo.template.maintainProject.task;

import java.util.Date;

/**
 * Created by sun.
 */
public class Task6Yusuanzaojia extends MaintainProjectBaseTask {

    //String departmentId = "Engineering_Department";
    private Date zaojiawanchengshijian; //预算造价完成日期 DATE
    private String zaojiazixundanweimingcheng; //造价咨询单位名称　STRING
    private String yusuanzaojiafuzeren; //预算造价联系人　STRING
    private double yusuanzaojiajine; //预算造价总金额　DOUBLE

    public Task6Yusuanzaojia(String progressId, String taskId, String taskName, String
            departmentId, String assignee, long startTime) {
        super(progressId, taskId, taskName, departmentId, assignee, startTime);
    }

    public Date getZaojiawanchengshijian() {
        return zaojiawanchengshijian;
    }

    public void setZaojiawanchengshijian(Date zaojiawanchengshijian) {
        this.zaojiawanchengshijian = zaojiawanchengshijian;
    }

    public String getZaojiazixundanweimingcheng() {
        return zaojiazixundanweimingcheng;
    }

    public void setZaojiazixundanweimingcheng(String zaojiazixundanweimingcheng) {
        this.zaojiazixundanweimingcheng = zaojiazixundanweimingcheng;
    }

    public String getYusuanzaojiafuzeren() {
        return yusuanzaojiafuzeren;
    }

    public void setYusuanzaojiafuzeren(String yusuanzaojiafuzeren) {
        this.yusuanzaojiafuzeren = yusuanzaojiafuzeren;
    }

    public double getYusuanzaojiajine() {
        return yusuanzaojiajine;
    }

    public void setYusuanzaojiajine(double yusuanzaojiajine) {
        this.yusuanzaojiajine = yusuanzaojiajine;
    }
}
