package com.hotello.housekeeping.schedule.api.service;

import com.hotello.housekeeping.schedule.api.domain.Room;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import java.util.List;

/**
 * Service Interface for managing Room.
 */
public interface RoomService {

    /**
     * Save a room.
     *
     * @param room the entity to save
     * @return the persisted entity
     */
    Room save(Room room);

    /**
     *  Get all the rooms.
     *  
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    Page<Room> findAll(Pageable pageable);

    /**
     *  Get the "id" room.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    Room findOne(String id);

    /**
     *  Delete the "id" room.
     *
     *  @param id the id of the entity
     */
    void delete(String id);
}
