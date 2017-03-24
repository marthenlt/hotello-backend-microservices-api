package com.hotello.housekeeping.schedule.api.domain;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.io.Serializable;
import java.util.Objects;

/**
 * A Room.
 */

@Document(collection = "room")
public class Room implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    private String id;

    @Field("room_no")
    private String roomNo;

    @Field("description")
    private String description;

    @Field("room_type")
    private String roomType;

    @Field("floor_no")
    private Integer floorNo;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getRoomNo() {
        return roomNo;
    }

    public Room roomNo(String roomNo) {
        this.roomNo = roomNo;
        return this;
    }

    public void setRoomNo(String roomNo) {
        this.roomNo = roomNo;
    }

    public String getDescription() {
        return description;
    }

    public Room description(String description) {
        this.description = description;
        return this;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getRoomType() {
        return roomType;
    }

    public Room roomType(String roomType) {
        this.roomType = roomType;
        return this;
    }

    public void setRoomType(String roomType) {
        this.roomType = roomType;
    }

    public Integer getFloorNo() {
        return floorNo;
    }

    public Room floorNo(Integer floorNo) {
        this.floorNo = floorNo;
        return this;
    }

    public void setFloorNo(Integer floorNo) {
        this.floorNo = floorNo;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Room room = (Room) o;
        if (room.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, room.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "Room{" +
            "id=" + id +
            ", roomNo='" + roomNo + "'" +
            ", description='" + description + "'" +
            ", roomType='" + roomType + "'" +
            ", floorNo='" + floorNo + "'" +
            '}';
    }
}
