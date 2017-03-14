package com.infoDiscover.solution.arch.demo.template.maintainProject.task;

import com.infoDiscover.solution.arch.demo.template.maintainProject.task.MaintainProjectBaseTask;

/**
 * Created by sun.
 */
public class Task4Zaojiahetongshenpi extends MaintainProjectBaseTask {

    //String departmentId = "Area_Chief_Financial_Officer";
    private double zaojiazixunhetongjine; //　造价咨询合同金额
    private String zaojiazixundanweimingcheng; //造价咨询单位名称
    private String zaojiazixunhetongbianhao; //　造价咨询合同编号

    public Task4Zaojiahetongshenpi(String progressId, String taskId, String taskName, String
            departmentId, String assignee, long startTime) {
        super(progressId, taskId, taskName, departmentId, assignee, startTime);
    }

    public double getZaojiazixunhetongjine() {
        return zaojiazixunhetongjine;
    }

    public void setZaojiazixunhetongjine(double zaojiazixunhetongjine) {
        this.zaojiazixunhetongjine = zaojiazixunhetongjine;
    }

    public String getZaojiazixundanweimingcheng() {
        return zaojiazixundanweimingcheng;
    }

    public void setZaojiazixundanweimingcheng(String zaojiazixundanweimingcheng) {
        this.zaojiazixundanweimingcheng = zaojiazixundanweimingcheng;
    }

    public String getZaojiazixunhetongbianhao() {
        return zaojiazixunhetongbianhao;
    }

    public void setZaojiazixunhetongbianhao(String zaojiazixunhetongbianhao) {
        this.zaojiazixunhetongbianhao = zaojiazixunhetongbianhao;
    }
}
