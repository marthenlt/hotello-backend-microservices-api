package com.hotello.housekeeping.schedule.api.domain;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.io.Serializable;
import java.util.Objects;

/**
 * A Cleaner.
 */

@Document(collection = "cleaner")
public class Cleaner implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    private String id;

    @Field("cleaner_name")
    private String cleanerName;

    @Field("floor_preferance")
    private Integer floorPreferance;

    @Field("phone_no")
    private String phoneNo;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCleanerName() {
        return cleanerName;
    }

    public Cleaner cleanerName(String cleanerName) {
        this.cleanerName = cleanerName;
        return this;
    }

    public void setCleanerName(String cleanerName) {
        this.cleanerName = cleanerName;
    }

    public Integer getFloorPreferance() {
        return floorPreferance;
    }

    public Cleaner floorPreferance(Integer floorPreferance) {
        this.floorPreferance = floorPreferance;
        return this;
    }

    public void setFloorPreferance(Integer floorPreferance) {
        this.floorPreferance = floorPreferance;
    }

    public String getPhoneNo() {
        return phoneNo;
    }

    public Cleaner phoneNo(String phoneNo) {
        this.phoneNo = phoneNo;
        return this;
    }

    public void setPhoneNo(String phoneNo) {
        this.phoneNo = phoneNo;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Cleaner cleaner = (Cleaner) o;
        if (cleaner.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, cleaner.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "Cleaner{" +
            "id=" + id +
            ", cleanerName='" + cleanerName + "'" +
            ", floorPreferance='" + floorPreferance + "'" +
            ", phoneNo='" + phoneNo + "'" +
            '}';
    }
}
