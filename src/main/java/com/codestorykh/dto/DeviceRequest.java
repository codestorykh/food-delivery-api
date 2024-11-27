package com.codestorykh.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class DeviceRequest {

    @JsonProperty("device_id")
    private String deviceId;
    @JsonProperty("device_type")
    private String deviceType;
    @JsonProperty("device_model")
    private String deviceModel;
    @JsonProperty("os_version")
    private String osVersion;
    @JsonProperty("app_version")
    private String appVersion;
    @JsonProperty("trust_device")
    private boolean trustDevice;
}
