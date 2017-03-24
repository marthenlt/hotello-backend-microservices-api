package com.hotello.housekeeping.schedule.api.service;

import com.hotello.housekeeping.schedule.api.domain.WorkSchedule;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import java.util.List;

/**
 * Service Interface for managing WorkSchedule.
 */
public interface WorkScheduleService {

    /**
     * Save a workSchedule.
     *
     * @param workSchedule the entity to save
     * @return the persisted entity
     */
    WorkSchedule save(WorkSchedule workSchedule);

    /**
     *  Get all the workSchedules.
     *  
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    Page<WorkSchedule> findAll(Pageable pageable);

    /**
     *  Get the "id" workSchedule.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    WorkSchedule findOne(String id);

    /**
     *  Delete the "id" workSchedule.
     *
     *  @param id the id of the entity
     */
    void delete(String id);
}
