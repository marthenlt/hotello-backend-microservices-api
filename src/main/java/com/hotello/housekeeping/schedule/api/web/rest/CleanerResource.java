package com.hotello.housekeeping.schedule.api.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.hotello.housekeeping.schedule.api.domain.Cleaner;
import com.hotello.housekeeping.schedule.api.service.CleanerService;
import com.hotello.housekeeping.schedule.api.web.rest.util.HeaderUtil;
import com.hotello.housekeeping.schedule.api.web.rest.util.PaginationUtil;
import io.swagger.annotations.ApiParam;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing Cleaner.
 */
@RestController
@RequestMapping("/api")
public class CleanerResource {

    private final Logger log = LoggerFactory.getLogger(CleanerResource.class);

    private static final String ENTITY_NAME = "cleaner";
        
    private final CleanerService cleanerService;

    public CleanerResource(CleanerService cleanerService) {
        this.cleanerService = cleanerService;
    }

    /**
     * POST  /cleaners : Create a new cleaner.
     *
     * @param cleaner the cleaner to create
     * @return the ResponseEntity with status 201 (Created) and with body the new cleaner, or with status 400 (Bad Request) if the cleaner has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/cleaners")
    @Timed
    public ResponseEntity<Cleaner> createCleaner(@RequestBody Cleaner cleaner) throws URISyntaxException {
        log.debug("REST request to save Cleaner : {}", cleaner);
        if (cleaner.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new cleaner cannot already have an ID")).body(null);
        }
        Cleaner result = cleanerService.save(cleaner);
        return ResponseEntity.created(new URI("/api/cleaners/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /cleaners : Updates an existing cleaner.
     *
     * @param cleaner the cleaner to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated cleaner,
     * or with status 400 (Bad Request) if the cleaner is not valid,
     * or with status 500 (Internal Server Error) if the cleaner couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/cleaners")
    @Timed
    public ResponseEntity<Cleaner> updateCleaner(@RequestBody Cleaner cleaner) throws URISyntaxException {
        log.debug("REST request to update Cleaner : {}", cleaner);
        if (cleaner.getId() == null) {
            return createCleaner(cleaner);
        }
        Cleaner result = cleanerService.save(cleaner);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, cleaner.getId().toString()))
            .body(result);
    }

    /**
     * GET  /cleaners : get all the cleaners.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of cleaners in body
     * @throws URISyntaxException if there is an error to generate the pagination HTTP headers
     */
    @GetMapping("/cleaners")
    @Timed
    public ResponseEntity<List<Cleaner>> getAllCleaners(@ApiParam Pageable pageable) {
        log.debug("REST request to get a page of Cleaners");
        Page<Cleaner> page = cleanerService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/cleaners");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /cleaners/:id : get the "id" cleaner.
     *
     * @param id the id of the cleaner to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the cleaner, or with status 404 (Not Found)
     */
    @GetMapping("/cleaners/{id}")
    @Timed
    public ResponseEntity<Cleaner> getCleaner(@PathVariable String id) {
        log.debug("REST request to get Cleaner : {}", id);
        Cleaner cleaner = cleanerService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(cleaner));
    }

    /**
     * DELETE  /cleaners/:id : delete the "id" cleaner.
     *
     * @param id the id of the cleaner to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/cleaners/{id}")
    @Timed
    public ResponseEntity<Void> deleteCleaner(@PathVariable String id) {
        log.debug("REST request to delete Cleaner : {}", id);
        cleanerService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

}
