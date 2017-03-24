package com.hotello.housekeeping.schedule.api.domain;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.List;
import java.util.Objects;

/**
 * A WorkSchedule.
 */

@Document(collection = "work_schedule")
public class WorkSchedule implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    private String id;

    @Field("schedule_date")
    private LocalDate scheduleDate;

    @Field("rooms")
    private List<Room> rooms;

    @Field("cleaners")
    private List<Cleaner> cleaners;

    @Field("desc")
    private String desc;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public LocalDate getScheduleDate() {
        return scheduleDate;
    }

    public WorkSchedule scheduleDate(LocalDate scheduleDate) {
        this.scheduleDate = scheduleDate;
        return this;
    }

    public void setScheduleDate(LocalDate scheduleDate) {
        this.scheduleDate = scheduleDate;
    }

    public List<Room> getRooms() {
        return rooms;
    }

    public void setRooms(List<Room> rooms) {
        this.rooms = rooms;
    }

    public List<Cleaner> getCleaners() {
        return cleaners;
    }

    public void setCleaners(List<Cleaner> cleaners) {
        this.cleaners = cleaners;
    }

    public String getDesc() {
        return desc;
    }

    public WorkSchedule desc(String desc) {
        this.desc = desc;
        return this;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        WorkSchedule workSchedule = (WorkSchedule) o;
        if (workSchedule.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, workSchedule.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

//    @Override
//    public String toString() {
//        return "WorkSchedule{" +
//            "id=" + id +
//            ", scheduleDate='" + scheduleDate + "'" +
//            ", rooms='" + rooms.iterator().toString() + "'" +
//            ", cleaners='" + cleaners.iterator().toString() + "'" +
//            ", desc='" + desc + "'" +
//            '}';
//    }

    @Override
    public String toString() {
        return "WorkSchedule{" +
                "id='" + id + '\'' +
                ", scheduleDate=" + scheduleDate +
                ", rooms=" + rooms +
                ", cleaners=" + cleaners +
                ", desc='" + desc + '\'' +
                '}';
    }
}
