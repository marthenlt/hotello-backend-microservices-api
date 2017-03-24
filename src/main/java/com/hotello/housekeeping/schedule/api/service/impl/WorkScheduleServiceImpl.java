package com.hotello.housekeeping.schedule.api.service.impl;

import com.hotello.housekeeping.schedule.api.service.WorkScheduleService;
import com.hotello.housekeeping.schedule.api.domain.WorkSchedule;
import com.hotello.housekeeping.schedule.api.repository.WorkScheduleRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Service Implementation for managing WorkSchedule.
 */
@Service
public class WorkScheduleServiceImpl implements WorkScheduleService{

    private final Logger log = LoggerFactory.getLogger(WorkScheduleServiceImpl.class);
    
    private final WorkScheduleRepository workScheduleRepository;

    public WorkScheduleServiceImpl(WorkScheduleRepository workScheduleRepository) {
        this.workScheduleRepository = workScheduleRepository;
    }

    /**
     * Save a workSchedule.
     *
     * @param workSchedule the entity to save
     * @return the persisted entity
     */
    @Override
    public WorkSchedule save(WorkSchedule workSchedule) {
        log.debug("Request to save WorkSchedule : {}", workSchedule);
        WorkSchedule result = workScheduleRepository.save(workSchedule);
        return result;
    }

    /**
     *  Get all the workSchedules.
     *  
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    @Override
    public Page<WorkSchedule> findAll(Pageable pageable) {
        log.debug("Request to get all WorkSchedules");
        Page<WorkSchedule> result = workScheduleRepository.findAll(pageable);
        return result;
    }

    /**
     *  Get one workSchedule by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Override
    public WorkSchedule findOne(String id) {
        log.debug("Request to get WorkSchedule : {}", id);
        WorkSchedule workSchedule = workScheduleRepository.findOne(id);
        return workSchedule;
    }

    /**
     *  Delete the  workSchedule by id.
     *
     *  @param id the id of the entity
     */
    @Override
    public void delete(String id) {
        log.debug("Request to delete WorkSchedule : {}", id);
        workScheduleRepository.delete(id);
    }
}
