package com.hotello.housekeeping.schedule.api.service;

import com.hotello.housekeeping.schedule.api.domain.Cleaner;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import java.util.List;

/**
 * Service Interface for managing Cleaner.
 */
public interface CleanerService {

    /**
     * Save a cleaner.
     *
     * @param cleaner the entity to save
     * @return the persisted entity
     */
    Cleaner save(Cleaner cleaner);

    /**
     *  Get all the cleaners.
     *  
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    Page<Cleaner> findAll(Pageable pageable);

    /**
     *  Get the "id" cleaner.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    Cleaner findOne(String id);

    /**
     *  Delete the "id" cleaner.
     *
     *  @param id the id of the entity
     */
    void delete(String id);
}
