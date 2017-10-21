package com.businessExtension.arch.progress.fact;

/**
 * Created by sun.
 */
public class AssetFact {
    private String assetId;
    private String firstClassification;
    private String secondClassification;
    private String assetName;
    private String assetAddress;

    public AssetFact(String assetId, String firstClassification, String secondClassification,
                     String assetName, String assetAddress) {
        this.assetId = assetId;
        this.firstClassification = firstClassification;
        this.secondClassification = secondClassification;
        this.assetName = assetName;
        this.assetAddress = assetAddress;
    }

    public String getAssetId() {
        return assetId;
    }

    public void setAssetId(String assetId) {
        this.assetId = assetId;
    }

    public String getFirstClassification() {
        return firstClassification;
    }

    public void setFirstClassification(String firstClassification) {
        this.firstClassification = firstClassification;
    }

    public String getSecondClassification() {
        return secondClassification;
    }

    public void setSecondClassification(String secondClassification) {
        this.secondClassification = secondClassification;
    }

    public String getAssetName() {
        return assetName;
    }

    public void setAssetName(String assetName) {
        this.assetName = assetName;
    }

    public String getAssetAddress() {
        return assetAddress;
    }

    public void setAssetAddress(String assetAddress) {
        this.assetAddress = assetAddress;
    }
}
