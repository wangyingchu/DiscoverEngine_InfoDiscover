package com.infoDiscover.solution.arch.progress.fact;

/**
 * Created by sun.
 */
abstract public class ClassificationDimension {
    private String classificationId;
    private String classificationName;

    public ClassificationDimension(String classificationId, String classificationName) {
        this.classificationId = classificationId;
        this.classificationName = classificationName;
    }

    public String getClassificationId() {
        return classificationId;
    }

    public void setClassificationId(String classificationId) {
        this.classificationId = classificationId;
    }

    public String getClassificationName() {
        return classificationName;
    }

    public void setClassificationName(String classificationName) {
        this.classificationName = classificationName;
    }
}
