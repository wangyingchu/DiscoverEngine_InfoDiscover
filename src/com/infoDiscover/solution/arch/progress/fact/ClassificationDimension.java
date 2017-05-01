package com.infoDiscover.solution.arch.progress.fact;

/**
 * Created by sun.
 */
abstract public class ClassificationDimension {
    private String classificationId;
    private String classificationName;
    private String description;

    public ClassificationDimension(String classificationId, String classificationName, String
            description) {
        this.classificationId = classificationId;
        this.classificationName = classificationName;
        this.description = description;
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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
