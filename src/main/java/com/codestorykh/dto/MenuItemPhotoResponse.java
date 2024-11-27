package com.codestorykh.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

@Getter
@Setter
@ToString
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MenuItemPhotoResponse {

    private Long id;
    @JsonProperty("file_type")
    private String fileType;
    @JsonProperty("file_format")
    private String fileFormat;
    @JsonProperty("file_size")
    private double fileSize;
    @JsonProperty("file_name")
    private String fileName;
    @JsonProperty("small_url")
    private String smallUrl;
    @JsonProperty("medium_url")
    private String mediumUrl;
    @JsonProperty("large_url")
    private String largeUrl;
    @JsonProperty("uploaded_by")
    private String uploadedBy;
    @JsonProperty("status")
    private String status;
}
