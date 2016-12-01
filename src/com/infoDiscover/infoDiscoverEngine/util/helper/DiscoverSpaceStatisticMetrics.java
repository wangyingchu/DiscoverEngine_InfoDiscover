package com.infoDiscover.infoDiscoverEngine.util.helper;

import java.util.ArrayList;
import java.util.List;

public class DiscoverSpaceStatisticMetrics {

    private long spaceDiskSize;
    private long spaceFullDataCount;
    private long spaceFactDataCount;
    private long spaceDimensionDataCount;
    private long spaceRelationDataCount;
    private String spaceLocation;
    private String spaceOwner;
    private List<DataTypeStatisticMetrics> factsStatisticMetrics;
    private List<DataTypeStatisticMetrics> dimensionsStatisticMetrics;
    private List<DataTypeStatisticMetrics> relationsStatisticMetrics;

    public DiscoverSpaceStatisticMetrics(){
        this.setFactsStatisticMetrics(new ArrayList<DataTypeStatisticMetrics>());
        this.setDimensionsStatisticMetrics(new ArrayList<DataTypeStatisticMetrics>());
        this.setRelationsStatisticMetrics(new ArrayList<DataTypeStatisticMetrics>());
    }

    public long getSpaceDiskSize() {
        return spaceDiskSize;
    }

    public void setSpaceDiskSize(long spaceDiskSize) {
        this.spaceDiskSize = spaceDiskSize;
    }

    public long getSpaceFullDataCount() {
        return spaceFullDataCount;
    }

    public void setSpaceFullDataCount(long spaceFullDataCount) {
        this.spaceFullDataCount = spaceFullDataCount;
    }

    public long getSpaceFactDataCount() {
        return spaceFactDataCount;
    }

    public void setSpaceFactDataCount(long spaceFactDataCount) {
        this.spaceFactDataCount = spaceFactDataCount;
    }

    public long getSpaceDimensionDataCount() {
        return spaceDimensionDataCount;
    }

    public void setSpaceDimensionDataCount(long spaceDimensionDataCount) {
        this.spaceDimensionDataCount = spaceDimensionDataCount;
    }

    public long getSpaceRelationDataCount() {
        return spaceRelationDataCount;
    }

    public void setSpaceRelationDataCount(long spaceRelationDataCount) {
        this.spaceRelationDataCount = spaceRelationDataCount;
    }

    public String getSpaceLocation() {
        return spaceLocation;
    }

    public void setSpaceLocation(String spaceLocation) {
        this.spaceLocation = spaceLocation;
    }

    public String getSpaceOwner() {
        return spaceOwner;
    }

    public void setSpaceOwner(String spaceOwner) {
        this.spaceOwner = spaceOwner;
    }

    public List<DataTypeStatisticMetrics> getFactsStatisticMetrics() {
        return factsStatisticMetrics;
    }

    public void setFactsStatisticMetrics(List<DataTypeStatisticMetrics> factsStatisticMetrics) {
        this.factsStatisticMetrics = factsStatisticMetrics;
    }

    public List<DataTypeStatisticMetrics> getDimensionsStatisticMetrics() {
        return dimensionsStatisticMetrics;
    }

    public void setDimensionsStatisticMetrics(List<DataTypeStatisticMetrics> dimensionsStatisticMetrics) {
        this.dimensionsStatisticMetrics = dimensionsStatisticMetrics;
    }

    public List<DataTypeStatisticMetrics> getRelationsStatisticMetrics() {
        return relationsStatisticMetrics;
    }

    public void setRelationsStatisticMetrics(List<DataTypeStatisticMetrics> relationsStatisticMetrics) {
        this.relationsStatisticMetrics = relationsStatisticMetrics;
    }

    public void addFactStatisticMetrics(DataTypeStatisticMetrics factStatisticMetrics){
        this.factsStatisticMetrics.add(factStatisticMetrics);
    }

    public void addDimensionStatisticMetrics(DataTypeStatisticMetrics dimensionStatisticMetrics){
        this.dimensionsStatisticMetrics.add(dimensionStatisticMetrics);
    }

    public void addRelationStatisticMetrics(DataTypeStatisticMetrics relationStatisticMetrics){
        this.relationsStatisticMetrics.add(relationStatisticMetrics);
    }
}
