package com.codestorykh.model;

import jakarta.persistence.Column;
import jakarta.persistence.MappedSuperclass;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.Date;

@Getter
@Setter
@ToString
@MappedSuperclass
public class BaseEntity {

    @Column(name = "created_by")
    private String createdBy;

    @Column(name = "created_at", insertable = true, updatable = false)
    private Date createdAt ;

    @Column(name = "updated_by")
    private String updatedBy;

    @Column(name = "updated_at", insertable = false, updatable = true)
    private Date updatedAt;
}
