package com.springdata.jpa.models;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.springdata.jpa.constants.AppTables.AuditModelTable;
import jakarta.persistence.*;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;
import java.time.OffsetDateTime;

@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
public abstract class AuditModel<U> {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = AuditModelTable.ID)
    private Long id;

    @CreatedBy
    @Column(name = AuditModelTable.CREATED_BY)
    protected U createdBy;

    @CreatedDate
    @Column(name = AuditModelTable.CREATED_DATE)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy hh:mm:ss")
    protected LocalDateTime createdDate;

    @LastModifiedBy
    @Column(name = AuditModelTable.LAST_MODIFIED_BY)
    protected U lastModifiedBy;

    @LastModifiedDate
    @Column(name = AuditModelTable.LAST_MODIFIED_DATE)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy hh:mm:ss")
    protected LocalDateTime lastModifiedDate;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public U getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(U createdBy) {
        this.createdBy = createdBy;
    }

    @JsonFormat(shape = JsonFormat.Shape.NUMBER, pattern = "s")
    public Long getCreatedDateTimeStamp() {
        if(createdDate == null) {
            return 0L;
        }
        return this.createdDate.toEpochSecond(OffsetDateTime.now().getOffset());
    }

    public void setCreatedDate(LocalDateTime createdDate) {
        this.createdDate = createdDate;
    }

    public U getLastModifiedBy() {
        return lastModifiedBy;
    }

    public void setLastModifiedBy(U lastModifiedBy) {
        this.lastModifiedBy = lastModifiedBy;
    }

    @JsonFormat(shape = JsonFormat.Shape.NUMBER, pattern = "s")
    public Long getLastModifiedDateTimeStamp() {
        if(lastModifiedDate == null){
            return 0L;
        }
        return this.lastModifiedDate.toEpochSecond(OffsetDateTime.now().getOffset());
    }

    public void setLastModifiedDate(LocalDateTime lastModifiedDate) {
        this.lastModifiedDate = lastModifiedDate;
    }

}
