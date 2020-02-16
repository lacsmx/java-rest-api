package com.lacsmx.chargingsessionstore.models;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * Summary to report total, start and end metrics for Charging Sessions
 *
 * @author CS Luis Date: Jan 18, 2020
 * */

@ApiModel(description = "Details of Summary Entity. ")
public class Summary {
    @ApiModelProperty(notes = "Total number of charging session updates for the last minute ")
    private long totalCount;
    @ApiModelProperty(notes = "Total number of starting charging session updates for the last minute ")
    private long startedCount;
    @ApiModelProperty(notes = "Total number of stopped charging session updates for the last minute ")
    private long stoppedCount;

    public Summary(long totalCount, long startedCount, long stoppedCount) {
        this.totalCount = totalCount;
        this.startedCount = startedCount;
        this.stoppedCount = stoppedCount;
    }

    public long getTotalCount() {
        return totalCount;
    }

    public void setTotalCount(long totalCount) {
        this.totalCount = totalCount;
    }

    public long getStartedCount() {
        return startedCount;
    }

    public void setStartedCount(long startedCount) {
        this.startedCount = startedCount;
    }

    public long getStoppedCount() {
        return stoppedCount;
    }

    public void setStoppedCount(long stoppedCount) {
        this.stoppedCount = stoppedCount;
    }

    @Override
    public String toString() {
        return "Summary{" +
                "totalCount=" + totalCount +
                ", startedCount=" + startedCount +
                ", stoppedCount=" + stoppedCount +
                '}';
    }
}
