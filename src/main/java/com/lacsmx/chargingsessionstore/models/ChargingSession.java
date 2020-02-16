package com.lacsmx.chargingsessionstore.models;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.lacsmx.chargingsessionstore.enums.StatusEnum;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.UUID;

/**
 * StationId is required to create a Charging Session
 *
 * @author CS Luis Date: Jan 18, 2020
 * */

@ApiModel(description = "Details of Charging Session Entity. ")
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ChargingSession {
    @ApiModelProperty(notes = "The internal generated ID session")
    private UUID id;

    @ApiModelProperty(notes = "The station ID provided")
    @NotNull(message="stationId cannot be missing or empty")
    @NotEmpty(message="stationId cannot be missing or empty")
    private String stationId;

    @ApiModelProperty(notes = "Time when the charging session started")
    private LocalDateTime startedAt;
    @ApiModelProperty(notes = "Time when the charging session finished")
    private LocalDateTime stoppedAt;
    @ApiModelProperty(notes = "Current Status of the charging session")
    private StatusEnum status;

    public ChargingSession(String stationId, LocalDateTime startedAt, LocalDateTime stoppedAt, StatusEnum status) {
        this.stationId = stationId;
        this.startedAt = startedAt;
        this.stoppedAt = stoppedAt;
        this.status = status;
        id = UUID.randomUUID();
    }

    public ChargingSession(String stationId) {
        this.stationId = stationId;
        id = UUID.randomUUID();
        startedAt = LocalDateTime.now();
        status = StatusEnum.IN_PROGRESS;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getStationId() {
        return stationId;
    }

    public void setStationId(String stationId) {
        this.stationId = stationId;
    }

    public LocalDateTime getStartedAt() {
        return startedAt;
    }

    public void setStartedAt(LocalDateTime startedAt) {
        this.startedAt = startedAt;
    }

    public LocalDateTime getStoppedAt() {
        return stoppedAt;
    }

    public void setStoppedAt(LocalDateTime stoppedAt) {
        this.stoppedAt = stoppedAt;
    }

    public StatusEnum getStatus() {
        return status;
    }

    public void setStatus(StatusEnum status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "ChargingSession{" +
                "id=" + id +
                ", stationId='" + stationId + '\'' +
                ", startedAt=" + startedAt +
                ", stoppedAt=" + stoppedAt +
                ", status=" + status +
                '}';
    }
}
