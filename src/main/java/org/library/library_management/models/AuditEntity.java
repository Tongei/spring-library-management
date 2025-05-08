package org.library.library_management.models;

import jakarta.persistence.EntityListeners;
import jakarta.persistence.MappedSuperclass;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@EntityListeners(AuditingEntityListener.class)
@MappedSuperclass
public abstract class AuditEntity {
    @CreatedBy
    private String creator;
    @LastModifiedBy
    private String lastModifiedBy;
    @CreatedDate
    private LocalDateTime issuedAt;
    @LastModifiedDate
    private LocalDateTime lastModifiedAt;
}
