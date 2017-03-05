package com.infoDiscover.app.base.progress.fact;

/**
 * Created by sun.
 */
public class ProgressFact {
    String progressId;
    String content;

    public ProgressFact(String progressId, String content) {
        this.progressId = progressId;
        this.content = content;
    }

    public String getProgressId() {
        return progressId;
    }

    public void setProgressId(String progressId) {
        this.progressId = progressId;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
