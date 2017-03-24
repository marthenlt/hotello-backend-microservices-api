package com.hotello.housekeeping.schedule.api.service.impl;

import com.hotello.housekeeping.schedule.api.service.CleanerService;
import com.hotello.housekeeping.schedule.api.domain.Cleaner;
import com.hotello.housekeeping.schedule.api.repository.CleanerRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Service Implementation for managing Cleaner.
 */
@Service
public class CleanerServiceImpl implements CleanerService{

    private final Logger log = LoggerFactory.getLogger(CleanerServiceImpl.class);
    
    private final CleanerRepository cleanerRepository;

    public CleanerServiceImpl(CleanerRepository cleanerRepository) {
        this.cleanerRepository = cleanerRepository;
    }

    /**
     * Save a cleaner.
     *
     * @param cleaner the entity to save
     * @return the persisted entity
     */
    @Override
    public Cleaner save(Cleaner cleaner) {
        log.debug("Request to save Cleaner : {}", cleaner);
        Cleaner result = cleanerRepository.save(cleaner);
        return result;
    }

    /**
     *  Get all the cleaners.
     *  
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    @Override
    public Page<Cleaner> findAll(Pageable pageable) {
        log.debug("Request to get all Cleaners");
        Page<Cleaner> result = cleanerRepository.findAll(pageable);
        return result;
    }

    /**
     *  Get one cleaner by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Override
    public Cleaner findOne(String id) {
        log.debug("Request to get Cleaner : {}", id);
        Cleaner cleaner = cleanerRepository.findOne(id);
        return cleaner;
    }

    /**
     *  Delete the  cleaner by id.
     *
     *  @param id the id of the entity
     */
    @Override
    public void delete(String id) {
        log.debug("Request to delete Cleaner : {}", id);
        cleanerRepository.delete(id);
    }
}
